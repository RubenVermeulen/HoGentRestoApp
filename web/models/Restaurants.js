var mongoose = require('mongoose');

var RestaurantSchema = new mongoose.Schema({
    name: String,
    address: String,
    coordinates: {
        lat: {type: Number, default: 0},
        long: {type: Number, default: 0}
    },
    openingHours: String,
    menus: [{type: mongoose.Schema.Types.ObjectId, ref: 'Menu'}],
    urlImage: String,
    feedbacks: [{type: mongoose.Schema.Types.ObjectId, ref: 'Feedback'}],
    sensorsSelfservice: [{type: mongoose.Schema.Types.ObjectId,ref: 'Sensor'}],
    sensorsZaal: [{type: mongoose.Schema.Types.ObjectId,ref: 'Sensor'}],
    occupation: Number,
    prediction: [{type: mongoose.Schema.Types.ObjectId, ref: 'Prediction'}]
});

mongoose.model('Restaurant', RestaurantSchema);
