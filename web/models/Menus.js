var mongoose = require('mongoose');

var MenuSchema = new mongoose.Schema({
    title: String,
    description: String,
    price: Number,
    availableAt: Date
});

mongoose.model('Menu', MenuSchema);