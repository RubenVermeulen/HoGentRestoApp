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

// routes
router.get('/:restaurant/menus', function(req, res, next) {

    req.restaurant.populate({
        path: 'menus',
        model: 'Menu',
        populate: {
            path: 'product',
            model: 'Product'
        }
    }, function(err, restaurant) {
        if (err) {
            return next(err);
        }

        return res.json(restaurant.menus);
    })

});

router.get('/:restaurant/menus/week', function(req, res, next) {

    var today = new Date(),
        start = new Date(),
        end = new Date(),
        offsetStart,
        offsetEnd;

    if (today.getDay() > 0 && today.getDay() < 6) {
        start = new Date(today);
        end.setDate(today.getDate() + 6 - today.getDay())
    }
    else {
        if (today.getDay() === 0) {
            offsetStart = 1;
            offsetEnd = 6;
        }
        else {
            offsetStart = 1;
            offsetEnd = 7;
        }

        start.setDate(today.getDate() + offsetStart);
        end.setDate(today.getDate() + offsetEnd);
    }

    Menu.find({availableAt: {$gte: start, $lt: end}}, function(err, menus) {
        if (err) {
            return next(err);
        }

        return res.json(menus);
    });

});

router.get('/:restaurant/menus/:menu', function(req, res, next) {

    req.menu.populate('product', function(err, menu) {
        if (err) {
            return next(err);
        }

        return res.json(menu);
    });

});

router.post('/:restaurant/menus', auth, function(req, res, next) {

    var body = req.body;

    if (!body.title || !body.price || !body.availableAt || !body.product) {
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

router.put('/:restaurant/menus/:menu', auth, function(req, res, next) {

    var menu = req.menu;
    var body = req.body;

    if (!body.title || !body.price || !body.availableAt || !body.product) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    menu.title = body.title;
    menu.description = body.description;
    menu.price = body.price;
    menu.availableAt = body.availableAt;
    menu.product = body.product;

    menu.save(function(err, menu) {
        if (err) {
            return next(err);
        }

        res.json(menu);
    });

});

router.delete('/:restaurant/menus/:menu', auth, function(req, res, next) {

    req.restaurant.menus.pull(req.menu._id);
    req.restaurant.save(function(err) {
        if (err) {
            return next(err);
        }
    });

    req.menu.remove(function(err, menu) {
        if (err) {
            return next(err);
        }

        res.json(menu);
    });

});

module.exports = router;
