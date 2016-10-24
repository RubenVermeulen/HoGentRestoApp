var mongoose = require('mongoose');

var RestaurantSchema = new mongoose.Schema({
    name: String,
    adress: String,
    openingHours: String
});

mongoose.model('Restaurant', RestaurantSchema);