var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Restaurant = mongoose.model('Restaurant');
var Menu = mongoose.model('Menu');

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

    var body = req.body;

    if (!body.title || !body.description || !body.price || !body.availableAt) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    var menu = new Menu(req.body);

    menu.save(function(err, menu) {
        if (err) {
            return next(err);
        }

        req.restaurant.menus.push(menu);
        req.restaurant.save(function(err) {
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

    if (!body.title || !body.description || !body.price || !body.availableAt) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    menu.title = body.title;
    menu.description = body.description;
    menu.price = body.price;
    menu.availableAt = body.availableAt;

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