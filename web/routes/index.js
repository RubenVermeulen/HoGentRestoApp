var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var passport = require('passport');
var jwt = require('express-jwt');

// models
var User = mongoose.model('User');
var Restaurant = mongoose.model('Restaurant');

// middlewares
var auth = jwt({
    secret: 'SECRET',
    userProperty: 'payload'
});

var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

router.get('/', function(req, res) {
    return res.render('index');
});

router.post('/register', function(req, res, next) {
    if (!req.body.username || !req.body.password) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    var user = new User();

    user.username = req.body.username;

    user.setPassword(req.body.password);

    user.save(function (err) {
        if (err) {
            return next(err);
        }

        return res.json({token: user.generateJWT()});
    });
});

router.post('/login', function(req, res, next) {
    if ( ! req.body.username || ! req.body.password) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    // middleware
    passport.authenticate('local', function(err, user, info) {
        if (err) {
            return next(err);
        }

        if (user) {
            return res.json({token: user.generateJWT()});
        }
        else {
            return res.status(401).json(info);
        }
    })(req, res, next);
});

// Restaurants

router.param('restaurant', function(req, res, next, id) {
    var query = Restaurant.findById(id);

    query.exec(function(err, restaurant) {
        if (err) {
            return next(err);
        }

        if ( ! restaurant) {
            return next(new Error('can\'t find restaurant'));
        }

        req.restaurant = restaurant;

        return next();
    });
});

router.get('/restaurants', function(req, res, next) {

    Restaurant.find(function(err, restaurants) {
       if (err) {
           return next(err);
       }

       res.json(restaurants)
    });

});

router.get('/restaurants/:restaurant', function(req, res, next) {

    res.json(req.restaurant);

});

router.post('/restaurants', function(req, res, next) {

    var restaurant = new Restaurant(req.body);

    restaurant.save(function(err, restaurant) {
        if (err) {
            return next(err);
        }

        res.json(restaurant);
    });

});

router.put('/restaurants/:restaurant', function(req, res, next) {

    var restaurant = req.restaurant;
    var body = req.body;

    restaurant.name = body.hasOwnProperty('name') ? body.name : restaurant.name;
    restaurant.address = body.hasOwnProperty('address') ? body.address : restaurant.address;
    restaurant.coordinates.lat = body.hasOwnProperty('lat') ? body.lat : restaurant.coordinates.lat;
    restaurant.coordinates.long = body.hasOwnProperty('long') ? body.long : restaurant.coordinates.long;
    restaurant.openingHours = body.hasOwnProperty('openingHours') ? body.openingHours : restaurant.openingHours;

    restaurant.save(function(err, restaurant) {
        if (err) {
            return next(err);
        }

        res.json(restaurant);
    });

});

router.delete('/restaurants/:restaurant', function(req, res, next) {

    req.restaurant.remove(function(err, restaurant) {
        if (err) {
            return next(err);
        }

        res.json(restaurant);
    });

});

module.exports = router;
