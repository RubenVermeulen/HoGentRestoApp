var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Restaurant = mongoose.model('Restaurant');
var Menu = mongoose.model('Menu');

// middlewares
var auth = jwt({
    secret: 'SECRET',
    userProperty: 'payload'
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

router.post('/', function(req, res, next) {

    if (!req.body.name || !req.body.address || !req.body.coordinates || !req.body.openingHours || !req.body.urlImage) {
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

router.put('/:restaurant', function(req, res, next) {

    var restaurant = req.restaurant;
    var body = req.body;

    restaurant.name = body.hasOwnProperty('name') ? body.name : restaurant.name;
    restaurant.address = body.hasOwnProperty('address') ? body.address : restaurant.address;
    restaurant.coordinates.lat = body.hasOwnProperty('lat') ? body.lat : restaurant.coordinates.lat;
    restaurant.coordinates.long = body.hasOwnProperty('long') ? body.long : restaurant.coordinates.long;
    restaurant.openingHours = body.hasOwnProperty('openingHours') ? body.openingHours : restaurant.openingHours;
    restaurant.urlImage = body.hasOwnProperty('urlImage') ? body.urlImage : restaurant.urlImage;

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

// Menus
// ----------------------------------------
//
// All routes concerning menus.
//

router.param('menu', function(req, res, next, id) {
    var query = Menu.findById(id);

    query.exec(function(err, menu) {
        if (err) {
            return next(err);
        }

        if ( ! menu) {
            return next(new Error('can\'t find menu'));
        }

        req.menu = menu;

        return next();
    });
});

router.get('/:restaurant/menus', function(req, res, next) {

    req.restaurant.populate('menus', function(err, post) {
        if (err) {
            return next(err);
        }

        res.json(req.restaurant.menus);
    });

});

router.get('/:restaurant/menus/:menu', function(req, res, next) {

    res.json(req.menu);

});

router.post('/:restaurant/menus', function(req, res, next) {

    if (!req.body.title || !req.body.description || !req.body.price || !req.body.availableAt) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    var menu = new Menu(req.body);

    menu.save(function(err, menu) {
        if (err) {
            return next(err);
        }

        req.restaurant.menus.push(menu);
        req.restaurant.save(function(err, restaurant) {
            if (err) {
                return next(err);
            }

            res.json(menu);
        });
    });

});

router.put('/:restaurant/menus/:menu', function(req, res, next) {

    var menu = req.menu;
    var body = req.body;

    menu.title = body.hasOwnProperty('title') ? body.title : menu.title;
    menu.description = body.hasOwnProperty('description') ? body.description : menu.description;
    menu.price = body.hasOwnProperty('price') ? body.price : menu.price;
    menu.availableAt = body.hasOwnProperty('availableAt') ? body.availableAt : menu.availableAt;

    menu.save(function(err, menu) {
        if (err) {
            return next(err);
        }

        res.json(menu);
    });

});

router.delete('/:restaurant/menus/:menu', function(req, res, next) {

    req.menu.remove(function(err, menu) {
        if (err) {
            return next(err);
        }

        res.json(menu);
    });

});

module.exports = router;
