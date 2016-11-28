var mongoose = require('mongoose');

var SensorReportSchema = new mongoose.Schema({

    time: Date,
    occupancy: Number
});

mongoose.model('SensorReport', SensorReportSchema);

SensorReportSchema.methods.findOnDate = function(cb){
  return this.model('SensorReport').find({ type: this.time }, cb);
};
