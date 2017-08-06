var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var RestaurantSchema = new Schema({
  naam: String,
	adres: {
		straat: String,
    huisnummer: String,
    stad: String,
    postcode: String
	},
  telefoon: String
});

module.exports = mongoose.model('Restaurant', RestaurantSchema);
