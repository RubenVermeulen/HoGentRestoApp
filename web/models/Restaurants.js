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
    sensorsSelfservice: [{type: mongoose.Schema.Types.ObjectId,ref: 'Sensor'}],
    sensorsZaal: [{type: mongoose.Schema.Types.ObjectId,ref: 'Sensor'}],
    occupancy: {type: Number, default: 0},
    prediction: [{type: mongoose.Schema.Types.ObjectId, ref: 'Prediction'}]
});

mongoose.model('Restaurant', RestaurantSchema);
