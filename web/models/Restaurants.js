var mongoose = require('mongoose');

var RestaurantSchema = new mongoose.Schema({
    name: String,
    address: String,
    coordinates: {
        lat: {type: Number, default: 0},
        long: {type: Number, default: 0}
    },
    openingHours: String,
    menus: [{type: mongoose.Schema.Types.ObjectId, ref: 'Menu'}]
});

mongoose.model('Restaurant', RestaurantSchema);