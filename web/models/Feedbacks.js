var mongoose = require('mongoose');

var FeedbackSchema = new mongoose.Schema({
    comfortScore: Number,
    foodScore: Number,
    trafficScore: Number,
    description: String
});

mongoose.model('Feedback', FeedbackSchema);
