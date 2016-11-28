var mongoose = require('mongoose');

var OccupancySchema = new mongoose.Schema({
    percentage: String,
    createdAt: Date,
    restaurantId: {type: mongoose.Schema.Types.ObjectId, ref: 'Restaurant'}
});

mongoose.model('Occupancy', OccupancySchema);
