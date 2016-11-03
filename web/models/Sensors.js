var mongoose = require('mongoose');

var SensorSchema = new mongoose.Schema({
    description: String,
    reports: [{type: mongoose.Schema.Types.ObjectId, ref: 'SensorReport'}]
});

mongoose.model('Sensor', SensorSchema);
