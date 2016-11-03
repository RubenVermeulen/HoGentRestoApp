var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var jwt = require('express-jwt');

// models
var Restaurant = mongoose.model('Restaurant');
var Sensor = mongoose.model('Sensor');

// middlewares
var auth = jwt({
    secret: 'SECRET',
    userProperty: 'payload'
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


router.get('/:restaurant/sensors', function(req, res, next) {

    Sensor.find(function(err, sensors) {
        if (err) {
            return next(err);
        }
        res.json(sensors)
    });
});


router.post('/:restaurant/sensors', function(req, res, next) {

    if (!req.body.description) {
        return res.status(400).json({message: 'Please fill out a description'});
    }

    var sensor = new Sensor(req.body);

    sensor.save(function(err, sensor) {
        if (err) {
            return next(err);
        }

        res.json(sensor);
    });
});

router.delete('/:restaurant/sensor/:sensor', function(req, res, next) {

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


router.get('/:restaurant/sensors/:sensor', function(req, res, next) {

    req.sensor.populate('reports', function(err, post) {
        if (err) {
            return next(err);
        }

        res.json(req.sensor);
    });

});

router.post('/:restaurant/sensors/:sensor/report ', function(req, res, next) {

    if (!req.body.timestamp || !req.body.occupancy) {
        return res.status(400).json({message: 'Report incomplete'});
    }

    var report = new SensorReport();

    report.time = new Date(req.body.timestamp); //convert unix timestamp to Date
    report.occupancy = req.body.occupancy;

    report.save(function(err, report) {
        if (err) {
            return next(err);
        }

        req.sensor.reports.push(report);
        req.sensor.save(function(err, sensor) {
            if (err) {
                return next(err);
            }

            res.json(sensor);
        });
    });

});

module.exports = router;
