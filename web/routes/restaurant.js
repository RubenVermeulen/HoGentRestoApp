var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Restaurant = mongoose.model('Restaurant');

// middlewares
var auth = jwt({
    secret: 'SECRET',
    userProperty: 'payload'
});

// params
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

// routes
router.get('/', function(req, res, next) {

    Restaurant.find(function(err, restaurants) {
        if (err) {
            return next(err);
        }

        res.json(restaurants)
    });

});

router.get('/:restaurant', function(req, res, next) {

    req.restaurant.populate('menus', function(err, post) {
        if (err) {
            return next(err);
        }

        res.json(req.restaurant);
    });

});

router.post('/', auth, function(req, res, next) {

    var body = req.body;

    if (!body.name || !body.address ||
        !body.coordinates.lat || !body.coordinates.long ||
        !body.openingHours || !body.urlImage) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    var restaurant = new Restaurant(req.body);

    restaurant.save(function(err, restaurant) {
        if (err) {
            return next(err);
        }

        res.json(restaurant);
    });

});

router.put('/:restaurant', auth, function(req, res, next) {

    var restaurant = req.restaurant;
    var body = req.body;

    if (!body.name || !body.address ||
        !body.coordinates.lat || !body.coordinates.long ||
        !body.openingHours || !body.urlImage) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    restaurant.name = body.name;
    restaurant.address = body.address;
    restaurant.coordinates.lat = body.coordinates.lat;
    restaurant.coordinates.long = body.coordinates.long;
    restaurant.openingHours = body.openingHours;
    restaurant.urlImage = body.urlImage;

    restaurant.save(function(err, restaurant) {
        if (err) {
            return next(err);
        }

        res.json(restaurant);
    });

});

router.delete('/:restaurant', function(req, res, next) {

    req.restaurant.remove(function(err, restaurant) {
        if (err) {
            return next(err);
        }

        res.json(restaurant);
    });

});

module.exports = router;
