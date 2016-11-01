var mongoose = require('mongoose');

var MenuSchema = new mongoose.Schema({
    title: String,
    description: String,
    price: Number,
    availableAt: Date,
    post: {type: mongoose.Schema.Types.ObjectId, ref: 'Restaurant'}
});

mongoose.model('Menu', MenuSchema);