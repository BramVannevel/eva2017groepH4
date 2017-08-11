var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ChallengeSchema = new Schema({
  titel: { type: String, required : true},
  omschrijving: { type: String, required : true},
  dag: { type: Number, required : true},
	restaurant : {
    naam: String,
  	adres: {
  		straat: String,
      huisnummer: String,
      stad: String,
      postcode: String
  	},
    telefoon: String
  },
  gerecht : {
      naam: String,
      omschrijving: String,
      categorie: {
          naam: String
      },
      allergenen: [{
          naam: String,
          selected: Boolean
      }]
  },
  reward : String
});

module.exports = mongoose.model('Challenge', ChallengeSchema);
