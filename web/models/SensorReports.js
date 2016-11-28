var mongoose = require('mongoose');

var SensorReportSchema = new mongoose.Schema({

    time: Date,
    occupancy: Number
});

mongoose.model('SensorReport', SensorReportSchema);

  return this.model('SensorReport').find( this.time.getDay() , cb);
};
