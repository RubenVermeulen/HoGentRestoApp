var schedule = require('node-schedule');

var SensorReport = mongoose.model('SensorReport');
var ForeCast = mongoose.model('ForeCast');
var ForecastUnit = mongoose.model('ForeCastUnit');

var rule = new schedule.RecurrenceRule();
rule.second = 30;

var j = schedule.scheduleJob(rule, function(){

  var today = new Date();
  var dayOfWeek = vandaag.getDate();

  var reportsTodayTillNow = SensorReport.findReportsOfThisDay();
  var reportsOfThisWeekDay = SensorReport.findAllReportsWithCurrentDayOfTheWeek();

  var reports1weekgeleden = [];
  var reports2wekengeleden = [];
  var reports3wekengeleden = [];
  var reports4wekengeleden = [];
  var reports5wekengeleden = [];


  //reports van die bepaalde weekdagen classificeren in bepaalde dagen (tot 5 weken terug)
  for (i = 0; i < reportsOfThisWeekDay.length; i++) {

    switch (reportsOfThisWeekDay[i].time.getDate()) {
      case today.getDate-7*1:
      reports1weekgeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate-7*2:
      reports2wekengeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate-7*3:
      reports3wekengeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate-7*4:
      reports4wekengeleden.push(reportsOfThisWeekDay[i]);
      break;
      case today.getDate-7*5:
      reports5wekengeleden.push(reportsOfThisWeekDay[i]);
      break;

    }
  }

  //dictionaries aanmaken met key= tijdstip en de value= drukte

  var reportsTodayTillNowDic = {};
  for(i = 0; i< reportsTodayTillNow.length;i++){
    reportsTodayTillNowDic[reportsTodayTillNow[i].time] = reportsTodayTillNow[i].occupancy;
  }

  var reports1weekgeledendic = {};
  for(i= 0; i<reports1weekgeleden.length;i++){
    reports1weekgeledendic[reports1weekgeleden[i].time]= reports1weekgeleden[i].occupancy;
  }

  var reports2wekengeledendic = {};
  for(i= 0; i<reports2wekengeleden.length;i++){
    reports2wekengeledendic[reports2wekengeleden[i].time]= reports2wekengeleden[i].occupancy;
  }

  var reports3wekengeledendic = {};
  for(i= 0; i<reports3wekengeleden.length;i++){
    reports3wekengeledendic[reports3wekengeleden[i].time]= reports3wekengeleden[i].occupancy;
  }

  var reports4wekengeledendic = {};
  for(i= 0; i<reports4wekengeleden.length;i++){
    reports4wekengeledendic[reports4wekengeleden[i].time]= reports4wekengeleden[i].occupancy;
  }

  var reports5wekengeledendic = {};
  for(i= 0; i<reports3wekengeleden.length;i++){
    reports5wekengeledendic[reports5wekengeleden[i].time]= reports5wekengeleden[i].occupancy;
  }


  var aantalReports = Object.keys(reports1weekgeledendic).length



  var gewogengemiddeldePerTijdslot = {};

  //gewogengemiddelde met wegingsfactoren gebaseerd op fibonacci
  //key= tijdstip en de value= drukte
  for(var key in reports1weekgeledendic){
    gewogengemiddeldePerTijdslot[key] = (144*reports1weekgeledendic[key] + 89*reports2wekengeledendic[key] +
                55*reports3wekengeledendic[key] + 34*reports4wekengeledendic[key] + 21*reports5wekengeledendic[key])/343
      }

  var voorspelling = {};


  //de voorspellingdictionary opvullen met data van vandaag
  for(var key in reportsTodayTillNowDic){
    voorspelling[key] = reportsTodayTillNowDic[key];
  }



    var aantalNodigeVoorspellingen = Object.keys(gewogengemiddeldePerTijdslot).length - Object.keys(reportsTodayTillNowDic).length ;

  var vorigData = reportsTodayTillNow[reportsTodayTillNow.length -1].occupancy;

    for (i=0; i<aantalNodigeVoorspellingen; i++){

      //het gemiddelde van nu in functie van aantal nodigevoorspellingen en i
      var gemiddeldeNu =  gewogengemiddeldePerTijdslot[reportsTodayTillNow[reportsTodayTillNow.length + i].time];

      var gemiddeldeVorig = gewogengemiddeldePerTijdslot[reportsTodayTillNow[reportsTodayTillNow.length -1 + i].time];




      var voorspellingNu = gemiddeldeNu * (vorigData / gemiddeldeVorig);

      vorigData = voorspellingNu;

      voorspelling[i] = voorspellingNu;


  }



















});
