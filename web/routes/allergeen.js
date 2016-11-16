var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Restaurant = mongoose.model('Restaurant');
var Menu = mongoose.model('Menu');
var Allergeen = mongoose.model('Allergeen');

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




router.param('allergeen', function(req, res, next, id) {
  var query = Allergeen.findById(id);

  query.exec(function(err, allergeen) {
    if (err) {
      return next(err);
    }

    if ( ! allergeen) {
      return next(new Error('can\'t find allergeen'));
    }

    req.allergeen = allergeen;

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

router.get('/:restaurant/menus/:menu/allergenen', function(req, res, next) {

  req.menu.populate('allergenen', function(err, post) {
    if (err) {
      return next(err);
    }

    res.json(req.menu.allergenen);
  });

});

router.post('/:restaurant/menus/:menu/allergenen', function(req, res, next) {

  if (!req.body.naam) {
    return res.status(400).json({message: 'Please fill out all fields'});
  }

  var allergeen = new Allergeen(req.body);

  req.menu.allergenen.push(allergeen);

  req.menu.save();
  allergeen.save(function(err, allergeen) {
    if (err) {
      return next(err);
    }

    res.json(allergeen);
  });

});

router.get('/:restaurant/menus/:menu/allergenen/:allergeen', function(req, res, next) {



  res.json(req.allergeen);


});

router.delete('/:restaurant/menus/:menu/allergenen/:allergeen', function(req, res, next) {

  var i = req.menu.allergenen.indexOf(req.allergeen);
  if(i != -1) {
  	req.menu.allergenen.splice(i, 1);
  }


  req.allergeen.remove(function(err, allergeen) {
    if (err) {
      return next(err);
    }


    res.json(allergeen);

  });

});

module.exports = router;
