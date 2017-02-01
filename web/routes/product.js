var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Product = mongoose.model('Product');
var Menu = mongoose.model('Menu');

// middlewares
var auth = jwt({
    secret: 'SECRET',
    userProperty: 'payload'
});

// routes
router.param('product', function (req, res, next, id) {
    var query = Product.findById(id);

    query.exec(function (err, product) {
        if (err) {
            return next(err);
        }

        if (!product) {
            return next(new Error('can\'t find product'));
        }

        req.product = product;

        return next();
    });
});

router.get('/', function(req, res, next) {

    Product.find(function(err, products) {
        if (err) {
            return next(err);
        }

        return res.json(products);
    });

});

router.get('/:product', function(req, res, next) {

    return res.json(req.product);

});

router.post('/', auth, function(req, res, next) {

    var body = req.body;

    if (!body.description && body.description === '') {
        return res.status(400).json({message: "Please fill in all fields"});
    }
    // Check if product already exist
    var descr = '^' + body.description + '$';
    var query = Product.findOne( { 'description' : { $regex : new RegExp(descr, 'i') } } );


    query.exec(function(err, product){
        if(err){
            return next(err);
        }

        if(product){
            return res.status(400).json({message: "Er bestaat reeds een product met deze naam."});
        }

        var product = new Product(req.body);

        product.save(function(err, product) {
            if (err) {
                return next(err);
            }

            return res.json(product);
        });
    });

});

router.put('/:product', auth, function(req, res, next) {

    var body = req.body;
    var product = req.product;

    if (!body.description && !body.allergens) {
        return res.status(400).json({message: "Please fill in all fields"});
    }

    product.description = body.description;

    if (!body.allergens || body.allergens.length < 1 || body.allergens == ''){
        product.allergens = [];
    }
    else {
        product.allergens = body.allergens;
    }

    product.save(function(err, product) {
        if (err) {
            return next(err);
        }

        return res.json(product);
    });

});

router.delete('/:product', auth, function(req, res, next) {

    Menu.find({product: req.product._id}, function(err, menus) {
        if (err) {
            return next(err);
        }

        if (menus.length !== 0) {
            return res.status(400).json({message: 'Product wordt gebruikt in andere menu\'s'});
        }

        req.product.remove(function(err, product) {
            if (err) {
                return next(err);
            }

            return res.json(product);
        });
    });

});

module.exports = router;
