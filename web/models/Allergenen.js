var mongoose = require('mongoose');

var AllergeenSchema = new mongoose.Schema({
  naam: String,
  post: {type: mongoose.Schema.Types.ObjectId, ref: 'Menu'}
});

mongoose.model('Allergeen', AllergeenSchema);
