var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Restaurant = mongoose.model('Restaurant');
var Feedback = mongoose.model('Feedback');

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

router.param('feedback', function(req, res, next, id) {
    var query = Feedback.findById(id);

    query.exec(function(err, feedback) {
        if (err) {
            return next(err);
        }

        if ( ! feedback) {
            return next(new Error('can\'t find feedback'));
        }

        req.feedback = feedback;

        return next();
    });
});

// routes
router.get('/:restaurant/feedback', function(req, res, next) {

    req.restaurant.populate('feedbacks', function(err, post) {
        if (err) {
            return next(err);
        }

        res.json(req.restaurant.feedbacks);
    });

});

router.get('/:restaurant/feedback/:feedback', function(req, res, next) {

    req.restaurant.populate('feedbacks', function(err, post) {
        if (err) {
            return next(err);
        }

        res.json(req.feedback);
    });

});


router.post('/:restaurant/feedback', auth, function(req, res, next) {

    var body = req.body;

    if (!body.comfortScore || !body.foodScore || !body.trafficScore || !body.description) {
        return res.status(400).json({message: 'Please fill out all fields'});
    }

    var feedback = new Feedback(req.body);

    req.restaurant.feedbacks.push(feedback);

    req.restaurant.save(function(err) {
        if (err) {
            return next(err);
        }
    });

    feedback.save(function(err, feedback) {
        if (err) {
            return next(err);
        }

        res.json(feedback);
    });

});

router.delete('/:restaurant/feedback/:feedback', auth, function(req, res, next) {

    req.restaurant.feedbacks.pull(req.feedback._id);
    req.restaurant.save(function(err) {
        if (err) {
            return next(err);
        }
    });

    req.feedback.remove(function(err, feedback) {
        if (err) {
            return next(err);
        }

        res.json(feedback);
    });

});

module.exports = router;