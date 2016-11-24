var mongoose = require('mongoose');

var MenuSchema = new mongoose.Schema({
  title: String,
  price: Number,
  availableAt: Date,
  product: {type: mongoose.Schema.Types.ObjectId, ref: 'Product'}
});

mongoose.model('Menu', MenuSchema);
