var mongoose = require('mongoose');

var PredictionSchema = new mongoose.Schema({
    predictionunit: [{type: mongoose.Schema.Types.ObjectId, ref: 'PredictionUnit'}]
});

mongoose.model('Prediction', PredictionSchema);
