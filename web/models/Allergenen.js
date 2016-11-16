var mongoose = require('mongoose');

var AllergeenSchema = new mongoose.Schema({
  naam: String,
  post: {type: mongoose.Schema.Types.ObjectId, ref: 'Menu'}
});

mongoose.model('Allergeen', AllergeenSchema);

/*AllergeenSchema.pre('remove', function (next) {
    var allergeen = this;
    allergeen.model('Menus').update(
        { allergenen: {$in: allergeen.menus}},
        { $pull: { allergeen: allergeen._id } },
        { multi: true },
        next
     );
});
*/
