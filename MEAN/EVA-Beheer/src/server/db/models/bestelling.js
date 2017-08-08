var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var BestellingSchema = new Schema({
  datum: Date,
  gebruiker: String,
	gerechten:
  [
      {
		    naam: String,
        prijs: Number
	     }
  ]
});

module.exports = mongoose.model('Bestelling', BestellingSchema);
