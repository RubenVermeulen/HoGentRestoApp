var mongoose = require('mongoose');

var ForecastUnitSchema = new mongoose.Schema({

    time: Date,
    occupancy: Number
});

mongoose.model('ForecastUnit', ForecastUnitSchema);
