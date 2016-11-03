var mongoose = require('mongoose');

var SensorReportSchema = new mongoose.Schema({

    time: Date,
    occupancy: Number
});

mongoose.model('SensorReport', SensorReportSchema);
