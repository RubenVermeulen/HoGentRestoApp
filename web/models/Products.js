var mongoose = require('mongoose');

var ProductSchema = new mongoose.Schema({
    description: String,
    allergens: [String]
});

mongoose.model('Product', ProductSchema);
