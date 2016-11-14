var mongoose = require('mongoose');

var FeedbackSchema = new mongoose.Schema({
    comfortScore: Number,
    eetScore: Number,
    drukteScore: Number,
    description: String
});

mongoose.model('Feedback', FeedbackSchema);
