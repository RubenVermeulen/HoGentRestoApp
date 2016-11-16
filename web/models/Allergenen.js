var mongoose = require('mongoose');

var AllergeenSchema = new mongoose.Schema({
  naam: String
});

mongoose.model('Allergeen', AllergeenSchema);
