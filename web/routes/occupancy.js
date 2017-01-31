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

router.get('/:restaurant/forecast', function(req, res, next) {

    var dataset = {"recommendedHour":"1480939080",
        "times":[
        {"time": "1480935750" , "occupancy": "0.07"},
        {"time": "1480935780" , "occupancy": "0.07"},
        {"time": "1480935810" , "occupancy": "0.09"},
        {"time": "1480935840" , "occupancy": "0.09"},
        {"time": "1480935870" , "occupancy": "0.09"},
        {"time": "1480935900" , "occupancy": "0.1"},
        {"time": "1480935930" , "occupancy": "0.09"},
        {"time": "1480935780" , "occupancy": "0.11"},
        {"time": "1480935990" , "occupancy": "0.13"},
        {"time": "1480936020" , "occupancy": "0.13"},
        {"time": "1480936050" , "occupancy": "0.14"},
        {"time": "1480936080" , "occupancy": "0.15"},
        {"time": "1480936110" , "occupancy": "0.15"},
        {"time": "1480936140" , "occupancy": "0.15"},
        {"time": "1480936170" , "occupancy": "0.17"},
        {"time": "1480936200" , "occupancy": "0.17"},
        {"time": "1480936230" , "occupancy": "0.18"},
        {"time": "1480936260" , "occupancy": "0.2"},
        {"time": "1480936290" , "occupancy": "0.23"},
        {"time": "1480936320" , "occupancy": "0.24"},
        {"time": "1480936350" , "occupancy": "0.23"},
        {"time": "1480936380" , "occupancy": "0.24"},
        {"time": "1480936410" , "occupancy": "0.25"},
        {"time": "1480936440" , "occupancy": "0.27"},
        {"time": "1480936470" , "occupancy": "0.29"},
        {"time": "1480936500" , "occupancy": "0.29"},
        {"time": "1480936530" , "occupancy": "0.31"},
        {"time": "1480936560" , "occupancy": "0.32"},
        {"time": "1480936590" , "occupancy": "0.36"},
        {"time": "1480936620" , "occupancy": "0.36"},
        {"time": "1480936650" , "occupancy": "0.37"},
        {"time": "1480936680" , "occupancy": "0.4"},
        {"time": "1480936710" , "occupancy": "0.43"},
        {"time": "1480936740" , "occupancy": "0.41"},
        {"time": "1480936770" , "occupancy": "0.45"},
        {"time": "1480936800" , "occupancy": "0.45"},
        {"time": "1480936830" , "occupancy": "0.47"},
        {"time": "1480936860" , "occupancy": "0.48"},
        {"time": "1480936890" , "occupancy": "0.5"},
        {"time": "1480936920" , "occupancy": "0.54"},
        {"time": "1480936950" , "occupancy": "0.52"},
        {"time": "1480936980" , "occupancy": "0.57"},
        {"time": "1480937010" , "occupancy": "0.59"},
        {"time": "1480937040" , "occupancy": "0.58"},
        {"time": "1480937070" , "occupancy": "0.59"},
        {"time": "1480937100" , "occupancy": "0.63"},
        {"time": "1480937130" , "occupancy": "0.66"},
        {"time": "1480937160" , "occupancy": "0.65"},
        {"time": "1480937190" , "occupancy": "0.68"},
        {"time": "1480937220" , "occupancy": "0.68"},
        {"time": "1480937250" , "occupancy": "0.71"},
        {"time": "1480937280" , "occupancy": "0.72"},
        {"time": "1480937310" , "occupancy": "0.74"},
        {"time": "1480937340" , "occupancy": "0.75"},
        {"time": "1480937370" , "occupancy": "0.76"},
        {"time": "1480937400" , "occupancy": "0.78"},
        {"time": "1480937430" , "occupancy": "0.81"},
        {"time": "1480937460" , "occupancy": "0.78"},
        {"time": "1480937490" , "occupancy": "0.82"},
        {"time": "1480937520" , "occupancy": "0.81"},
        {"time": "1480937550" , "occupancy": "0.84"},
        {"time": "1480937580" , "occupancy": "0.85"},
        {"time": "1480937610" , "occupancy": "0.87"},
        {"time": "1480937640" , "occupancy": "0.83"},
        {"time": "1480937670" , "occupancy": "0.8"},
        {"time": "1480937700" , "occupancy": "0.84"},
        {"time": "1480937730" , "occupancy": "0.86"},
        {"time": "1480937760" , "occupancy": "0.87"},
        {"time": "1480937790" , "occupancy": "0.88"},
        {"time": "1480937820" , "occupancy": "0.84"},
        {"time": "1480937850" , "occupancy": "0.85"},
        {"time": "1480937880" , "occupancy": "0.85"},
        {"time": "1480937910" , "occupancy": "0.87"},
        {"time": "1480937940" , "occupancy": "0.86"},
        {"time": "1480937970" , "occupancy": "0.88"},
        {"time": "1480938000" , "occupancy": "0.78"},
        {"time": "1480938030" , "occupancy": "0.83"},
        {"time": "1480938060" , "occupancy": "0.8"},
        {"time": "1480938090" , "occupancy": "0.81"},
        {"time": "1480938120" , "occupancy": "0.83"},
        {"time": "1480938150" , "occupancy": "0.83"},
        {"time": "1480938180" , "occupancy": "0.85"},
        {"time": "1480938210" , "occupancy": "0.79"},
        {"time": "1480938240" , "occupancy": "0.79"},
        {"time": "1480938270" , "occupancy": "0.8"},
        {"time": "1480938300" , "occupancy": "0.8"},
        {"time": "1480938330" , "occupancy": "0.84"},
        {"time": "1480938360" , "occupancy": "0.73"},
        {"time": "1480938390" , "occupancy": "0.74"},
        {"time": "1480938420" , "occupancy": "0.74"},
        {"time": "1480938450" , "occupancy": "0.71"},
        {"time": "1480938480" , "occupancy": "0.69"},
        {"time": "1480938510" , "occupancy": "0.64"},
        {"time": "1480938540" , "occupancy": "0.67"},
        {"time": "1480938570" , "occupancy": "0.66"},
        {"time": "1480938600" , "occupancy": "0.6"},
        {"time": "1480938630" , "occupancy": "0.61"},
        {"time": "1480938660" , "occupancy": "0.61"},
        {"time": "1480938690" , "occupancy": "0.57"},
        {"time": "1480938720" , "occupancy": "0.54"},
        {"time": "1480938750" , "occupancy": "0.58"},
        {"time": "1480938780" , "occupancy": "0.52"},
        {"time": "1480938810" , "occupancy": "0.52"},
        {"time": "1480938840" , "occupancy": "0.49"},
        {"time": "1480938870" , "occupancy": "0.49"},
        {"time": "1480938900" , "occupancy": "0.45"},
        {"time": "1480938930" , "occupancy": "0.45"},
        {"time": "1480938960" , "occupancy": "0.45"},
        {"time": "1480938990" , "occupancy": "0.44"},
        {"time": "1480939020" , "occupancy": "0.4"},
        {"time": "1480939050" , "occupancy": "0.4"},
        {"time": "1480939080" , "occupancy": "0.33"},
        {"time": "1480939110" , "occupancy": "0.37"},
        {"time": "1480939140" , "occupancy": "0.36"},
        {"time": "1480939170" , "occupancy": "0.32"},
        {"time": "1480939200" , "occupancy": "0.33"},
        {"time": "1480939230" , "occupancy": "0.31"},
        {"time": "1480939260" , "occupancy": "0.28"},
        {"time": "1480939290" , "occupancy": "0.28"},
        {"time": "1480939320" , "occupancy": "0.24"},
        {"time": "1480939350" , "occupancy": "0.24"},
        {"time": "1480939380" , "occupancy": "0.23"},
        {"time": "1480939410" , "occupancy": "0.22"},
        {"time": "1480939440" , "occupancy": "0.21"},
        {"time": "1480939470" , "occupancy": "0.21"},
        {"time": "1480939500" , "occupancy": "0.19"},
        {"time": "1480939530" , "occupancy": "0.18"},
        {"time": "1480939560" , "occupancy": "0.18"},
        {"time": "1480939590" , "occupancy": "0.16"},
        {"time": "1480939620" , "occupancy": "0.15"},
        {"time": "1480939650" , "occupancy": "0.16"},
        {"time": "1480939680" , "occupancy": "0.14"},
        {"time": "1480939710" , "occupancy": "0.13"},
        {"time": "1480939740" , "occupancy": "0.11"},
        {"time": "1480939770" , "occupancy": "0.12"},
        {"time": "1480939800" , "occupancy": "0.11"},
        {"time": "1480939830" , "occupancy": "0.1"},
        {"time": "1480939860" , "occupancy": "0.08"},
        {"time": "1480939890" , "occupancy": "0.1"},
        {"time": "1480939920" , "occupancy": "0.08"},
        {"time": "1480939950" , "occupancy": "0.08"},
        {"time": "1480939980" , "occupancy": "0.07"},
        {"time": "1480940010" , "occupancy": "0.07"},
        {"time": "1480940040" , "occupancy": "0.06"},
        {"time": "1480940070" , "occupancy": "0.06"},
        {"time": "1480940100" , "occupancy": "0.05"},
        {"time": "1480940130" , "occupancy": "0.04"},
        {"time": "1480940160" , "occupancy": "0.05"},
        {"time": "1480940190" , "occupancy": "0.04"},
        {"time": "1480940220" , "occupancy": "0.03"},
        {"time": "1480940250" , "occupancy": "0.03"},
        {"time": "1480940280" , "occupancy": "0.04"},
        {"time": "1480940310" , "occupancy": "0.03"},
        {"time": "1480940340" , "occupancy": "0.03"},
        {"time": "1480940370" , "occupancy": "0.03"},
        {"time": "1480940400" , "occupancy": "0.03"},
        {"time": "1480940430" , "occupancy": "0.02"},
        {"time": "1480940460" , "occupancy": "0.02"},
        {"time": "1480940490" , "occupancy": "0.02"},
        {"time": "1480940520" , "occupancy": "0.02"},
        {"time": "1480940550" , "occupancy": "0.01"},
        {"time": "1480940580" , "occupancy": "0.01"},
        {"time": "1480940610" , "occupancy": "0.01"},
        {"time": "1480940640" , "occupancy": "0.02"},
        {"time": "1480940670" , "occupancy": "0.01"},
        {"time": "1480940700" , "occupancy": "0.01"},
        {"time": "1480940730" , "occupancy": "0.01"},
        {"time": "1480940760" , "occupancy": "0.01"},
        {"time": "1480940790" , "occupancy": "0.01"},
        {"time": "1480940820" , "occupancy": "0.01"},
        {"time": "1480940850" , "occupancy": "0.01"},
        {"time": "1480940880" , "occupancy": "0"},
        {"time": "1480940910" , "occupancy": "0"},
        {"time": "1480940940" , "occupancy": "0.01"},
        {"time": "1480940970" , "occupancy": "0"},
        {"time": "1480941000" , "occupancy": "0"}]}
    ;

    return res.json(dataset);

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
