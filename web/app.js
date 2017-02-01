var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var mongoose = require('mongoose');
var passport = require('passport');

require('./models/Users');
require('./models/Restaurants');
require('./models/Menus');
require('./models/Occupancies');
require('./models/Products');
require('./models/Sensors');
require('./models/SensorReports');

require('./config/passport');

mongoose.connect('mongodb://localhost/hogent');

// routes
var routes = require('./routes/index');
var auth = require('./routes/auth');
var restaurant = require('./routes/restaurant');
var menu = require('./routes/menu');
var occupancy = require('./routes/occupancy');
var product = require('./routes/product');
var sensorReport = require('./routes/sensor');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(passport.initialize());

app.use('/', routes);
app.use('/', auth);
app.use('/restaurants', restaurant);
app.use('/restaurants', menu);
app.use('/restaurants', occupancy);
app.use('/products', product);
app.use('/restaurants', sensorReport);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;
