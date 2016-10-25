var mongoose = require('mongoose');

var RestaurantSchema = new mongoose.Schema({
    name: String,
    address: String,
    coordinates: {
        lat: String,
        long: String
    },
    openingHours: String
});

mongoose.model('Restaurant', RestaurantSchema);