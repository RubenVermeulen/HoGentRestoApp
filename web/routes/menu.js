var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Menu = mongoose.model('Menu');

// middlewares
var auth = jwt({
    secret: 'SECRET',
    userProperty: 'payload'
});

// Restaurants

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

router.get('/', function(req, res, next) {

    Menu.find(function(err, menu) {
        if (err) {
            return next(err);
        }

        res.json(menu)
    });

});

router.get('/:menu', function(req, res, next) {

    res.json(req.menu);

});

router.post('/', function(req, res, next) {

    if (!req.body.title || !req.body.description || !req.body.price || !req.body.availableAt) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    var menu = new Menu(req.body);

    menu.save(function(err, menu) {
        if (err) {
            return next(err);
        }

        res.json(menu);
    });

});

router.put('/:menu', function(req, res, next) {

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

router.delete('/:menu', function(req, res, next) {

    req.menu.remove(function(err, menu) {
        if (err) {
            return next(err);
        }

        res.json(menu);
    });

});

module.exports = router;
