var schedule = require('node-schedule');

var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');


var SensorReport = mongoose.model('SensorReport');
var ForeCast = mongoose.model('ForeCast');
var ForecastUnit = mongoose.model('ForeCastUnit');

var rule = new schedule.RecurrenceRule();
rule.second = 30;

var j = schedule.scheduleJob(rule, function(){

  var today = new Date();
  var dayOfWeek = vandaag.getDate();

  //bevat een rij van sensorreports van vandaag (tijdstip,drukte)
  var reportsTodayTillNow = SensorReport.findReportsOfThisDay();

  //alle sensorreports van die bepaalde weekdag (alle reports vd maandagen bv)
  var reportsOfThisWeekDay = SensorReport.findAllReportsWithCurrentDayOfTheWeek();


//variabelen aanmaken om de reports per dag te vinden
  var reports1weekgeleden = [];
  var reports2wekengeleden = [];
  var reports3wekengeleden = [];
  var reports4wekengeleden = [];
  var reports5wekengeleden = [];


  //reports van die bepaalde weekdagen classificeren in bepaalde dagen (tot 5 weken terug)
  for (i = 0; i < reportsOfThisWeekDay.length; i++) {

    switch (reportsOfThisWeekDay[i].time.getDate()) {
      case today.getDate()-7*1:
      reports1weekgeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate()-7*2:
      reports2wekengeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate()-7*3:
      reports3wekengeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate()-7*4:
      reports4wekengeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate()-7*5:
      reports5wekengeleden.push(reportsOfThisWeekDay[i]);
      break;

    }
  }

  function compare(a,b) {
  if (a.time < b.time)
    return -1;
  if (a.time > b.time)
    return 1;
  return 0;
}

reports1weekgeleden.sort(compare);
reports2wekengeleden.sort(compare);
reports3wekengeleden.sort(compare);
reports4wekengeleden.sort(compare);
reports5wekengeleden.sort(compare);


  var gewogengemiddeldePerTijdslot = [];

  //gewogengemiddelde met wegingsfactoren gebaseerd op fibonacci

  for(var i= 0; i<reports1weekgeleden.length ; i++){
    gewogengemiddeldePerTijdslot[i] = (144*reports1weekgeleden[i].occupancy + 89*reports2wekengeleden[i].occupancy +
                55*reports3wekengeleden[i].occupancy + 34*reports4wekengeleden[i].occupancy + 21*reports5wekengeleden[i].occupancy)/343;
                }

  var voorspelling = [];


  //de voorspellingdictionary opvullen met data van vandaag
  for(var i=0; i< reportsTodayTillNow.length;i++){
    voorspelling[i] = reportsTodayTillNow[i].occupancy;
  }

  var aantalMetingenVandaag = reportsTodayTillNow.length;

  var schalingsfactor= (reportsTodayTillNow[aantalMetingenVandaag -1 ]/gewogengemiddeldePerTijdslot[aantalMetingenVandaag -1]+reportsTodayTillNow[aantalMetingenVandaag -2 ]/gewogengemiddeldePerTijdslot[aantalMetingenVandaag -2])/2;

  var aantalNodigeVoorspellingen = gewogengemiddeldePerTijdslot.length - reportsTodayTillNow.length;

  for (var i = 0; i<aantalNodigeVoorspellingen;i++){
    voorspelling[aantalMetingenVandaag + i]= schalingsfactor * gewogengemiddeldePerTijdslot[aantalMetingenVandaag+i];
  }

  var tijden;

  var twelveOClock = reportsTodayTillNow[0].time;

  for (var i = 0; i++; i<voorspelling.length){
      var match = twelveOClock + 30*i;
      var query = ForeCastUnit.findOne({ 'time': match });

      query.exec(function(err, foreCastUnit){
          if(err){
              return next(err);
          }
          if(foreCastUnit){
              foreCastUnit.occupancy = voorspelling[i];
          }
          if(!foreCastUnit){
              var foreCastUnit = new foreCastUnit({
                  time : match,
                  occupancy : voorspelling[i]
              });
          }

          foreCastUnit.save(function(err, foreCastUnit) {
              if (err) {
                  return next(err);
              }

              return;
          });

  });
}
});
