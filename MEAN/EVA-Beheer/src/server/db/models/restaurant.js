var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var RestaurantSchema = new Schema({
  naam: String,
	adres: {
		straat: String,
    huisnummer: Number,
    stad: String,
    postcode: Number
	},
  telefoon: Number
});

module.exports = mongoose.model('Restaurant', RestaurantSchema);
