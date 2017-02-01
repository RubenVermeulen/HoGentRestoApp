var mongoose = require('mongoose');

var ForecastSchema = new mongoose.Schema({
    forecastunit: [{type: mongoose.Schema.Types.ObjectId, ref: 'ForecastUnit'}]
});

mongoose.model('Forecast', ForecastSchema);
