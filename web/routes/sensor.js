var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Restaurant = mongoose.model('Restaurant');
var Sensor = mongoose.model('Sensor');
var SensorReport = mongoose.model('SensorReport');

// middlewares
var auth = jwt({
    secret: 'SECRET',
    userProperty: 'payload'
});

// utils
var util = require('../utils/forecastAlgorithm');

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

router.param('sensor', function(req, res, next, id) {
    var query = Sensor.findById(id);

    query.exec(function(err, sensor) {
        if (err) {
            return next(err);
        }

        if ( ! sensor) {
            return next(new Error('can\'t find sensor'));
        }

        req.sensor = sensor;

        return next();
    });
});

/* get all selfservices from resto */

router.get('/:restaurant/sensors/selfservice', function(req, res, next) {

    req.restaurant.populate({
        path: 'sensorsSelfservice',
        model: 'Sensor'
    }, function(err, restaurant) {
        if (err) {
            return next(err);
        }

        return res.json(restaurant.sensorsSelfservice);
    })

});


/* create selfservice sensor - create sensor en wijs toe aan correct resto */

router.post('/:restaurant/sensors/selfservice', function(req, res, next) {

    if (!req.body.description) {
        return res.status(400).json({message: 'Please fill out a description'});
    }

    var sensor = new Sensor(req.body);

    sensor.save(function(err, sensor) {
        if (err) {
            return next(err);
        }

        req.restaurant.sensorsSelfservice.push(sensor);
        req.restaurant.save(function(err) {
            if (err) {
                return next(err);
            }

            res.json(sensor);
        });

    });
});

/* delete selfservice sensor */

router.delete('/:restaurant/sensors/selfservice/:sensor', function(req, res, next) {

    req.restaurant.sensorsSelfservice.pull(req.sensor._id);
    req.restaurant.save(function(err) {
        if (err) {
            return next(err);
        }
    });

    req.sensor.remove(function(err, sensor) {
        if (err) {
            return next(err);
        }

        res.json(sensor);
    });

});


// ----------------------------------------
// routes for sensor data
//-----------------------------------------



/* get latest report selfservice v/e resto */

router.get('/:restaurant/sensors/selfservice/latest', function(req, res, next){

    req.restaurant.populate({
        path: 'sensorsSelfservice',
        model: 'Sensor'
    }, function(err, restaurant) {
        if (err) {
            return next(err);
        }

        restaurant.sensorsSelfservice[0].populate('reports', function(err, post) {
           if (err) {
                return next(err);
           }

            var report = req.restaurant.sensorsSelfservice[0].reports.sort(function(a,b){
                return Date.parse(a.time) < Date.parse(b.time);
            });
            res.json(report[0]);
        });
    })

});

/* get sensor */

router.get('/:restaurant/sensors/:sensor', function(req, res, next) {

    req.sensor.populate('reports', function(err, post) {
        if (err) {
            return next(err);
        }

        res.json(req.sensor);
    });

});

/* get all reports from sensor */

router.get('/:restaurant/sensors/:sensor/reports', function(req, res, next) {

   req.sensor.populate('reports', function(err, post) {
      if (err) {
           return next(err);
      }

      res.json(req.sensor.reports);
   });

});

/* create report for sensor - raspPi gebruikt dit */

router.post('/:restaurant/sensors/:sensor/reports', function(req, res, next) {

    if (!req.body.time || !req.body.occupancy) {
        return res.status(400).json({message: 'Report incomplete'});
    }

    var report = new SensorReport();

    report.time = new Date(parseInt(req.body.time)*1000); //convert unix timestamp to Date
    report.occupancy = parseFloat(req.body.occupancy);

    report.save(function(err, report) {
        if (err) {
            return next(err);
        }

        req.sensor.reports.push(report);
        req.restaurant.occupation = report.occupancy;
        req.restaurant.save();
        req.sensor.save(function(err, sensor) {
            if (err) {
                return next(err);
            }

            util.updateForecasts();

            res.json(sensor);
        });

    });

});

router.post('/:restaurant/sensors/:sensor/reportset', function(req, res, next) {

    var reports = req.body.reportSet;

    for(var i=0;i<reports.length;i++){
        var report = new SensorReport();

        report.time = new Date(parseInt(reports[i].time)*1000); //convert unix timestamp to Date
        report.occupancy = parseFloat(reports[i].occupancy);

        report.save(function(err, report) {
            if (err) {
                return next(err);
            }

            req.sensor.reports.push(report);
            req.sensor.save(function(err, sensor) {
                if (err) {
                    return next(err);
                }
            });
        });
    }

    res.json(req.sensor);
});


module.exports = router;
