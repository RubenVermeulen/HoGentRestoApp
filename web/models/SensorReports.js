var mongoose = require('mongoose');

var SensorReportSchema = new mongoose.Schema({

    time: Date,
    occupancy: Number
});

mongoose.model('SensorReport', SensorReportSchema);

SensorReportSchema.methods.findAllReportsWithCurrentDayOfTheWeek = function(){
var vandaag = new Date();

  return this.model('SensorReport').aggregate([{$match: {time : {$dayOfWeek: vandaag.getDay()} }} ] ).sort({time:1});
};

SensorReportSchema.methods.findReportsOfThisDay = function(){
  var vandaag = new Date();
  vandaag.setHours(0);
  vandaag.setMinutes(0);

  return this.model('SensorReport').aggregate([{$match: {time : {$gte: new Date()} }} ] ).sort({time:1});

};
