var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');

// models
var Restaurant = mongoose.model('Restaurant');
var Occupancy = mongoose.model('Occupancy');

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
router.get('/:restaurant/occupancies', function(req, res, next) {

    Occupancy.find({restaurantId: req.restaurant._id}, function(err, occupancies) {
        return res.json(occupancies);
    });

});

router.get('/:restaurant/occupancies/generate', function(req, res, next) {

    var restaurant = req.restaurant;
    var occupancies = [];
    var date,
        occupancy,
        i;

    var addSeconds = function(date) {
        date.setSeconds(date.getSeconds() + 30);
    };

    var addOccupancy = function(min, max, date) {
        occupancy = new Occupancy({
            percentage: Math.random() * (max - min) + min,
            createdAt: date.toISOString(),
            restaurantId: restaurant._id
        });

        occupancies.push(occupancy);
    };

    // 11u30 - 12u00 (percentage between: 0 - 10
    date = new Date();
    date.setHours(11);
    date.setMinutes(30);
    date.setSeconds(0);

    for (i = 0; i < 60; i++) {
        addSeconds(date);
        addOccupancy(0, 10, date);
    }

    // 12u00 - 12u15 (percentage between: 5 - 20
    for (i = 0; i < 30; i++) {
        addSeconds(date);
        addOccupancy(5, 20, date);
    }

    // 12u15 - 12u30 (percentage between: 25 - 50

    for (i = 0; i < 30; i++) {
        addSeconds(date);
        addOccupancy(25, 50, date);
    }

    // 12u30 - 12u45 (percentage between: 60 - 90
    for (var i = 0; i < 30; i++) {
        addSeconds(date);
        addOccupancy(60, 90, date);
    }

    // 12u45 - 13u00 (percentage between: 50 - 90)
    for (i = 0; i < 30; i++) {
        addSeconds(date);
        addOccupancy(50, 90, date);
    }

    // 13u00 - 13u15 (percentage between: 35 - 50
    for (i = 0; i < 30; i++) {
        addSeconds(date);
        addOccupancy(35, 50, date);
    }

    // 13u15 - 13u30 (percentages between: 15 - 30
    for (i = 0; i < 30; i++) {
        addSeconds(date);
        addOccupancy(15, 30, date);
    }

    // Save
    Occupancy.create(occupancies, function(err, occupancies) {
        if (err) {
            return next(err);
        }

        return res.json(occupancies);
    });
});

module.exports = router;
