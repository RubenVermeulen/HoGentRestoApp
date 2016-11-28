var schedule = require('node-schedule');

var SensorReport = mongoose.model('SensorReport');

var rule = new schedule.RecurrenceRule();
rule.second = 42;

var j = schedule.scheduleJob(rule, function(){

  var vandaag = new Date();
  var dagvdweek = vandaag.getDate();

  var reportTotNuToe = SensorReport.findOnThisDayOfTheWeek();

  





});
