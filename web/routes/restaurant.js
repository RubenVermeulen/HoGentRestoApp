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

    res.json(req.restaurant);

});

router.post('/', function(req, res, next) {

    if (!req.body.name || !req.body.address || !req.body.coordinates || !req.body.openingHours) {
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
