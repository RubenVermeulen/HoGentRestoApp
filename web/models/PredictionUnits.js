var mongoose = require('mongoose');

var PredictionUnitSchema = new mongoose.Schema({

    time: Date,
    occupancy: Number
});

mongoose.model('PredictionUnit', PredictionUnitSchema);
