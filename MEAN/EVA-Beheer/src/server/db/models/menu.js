var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var MenuSchema = new Schema({
  	datum: Date,
	gerecht: {
		naam: String,
    	prijs: Number,
    	categorie: {
       		 naam: String
    	},
    	allergenen: [{
       		naam: String,
        	selected: Boolean
    	}]
	}	
});

module.exports = mongoose.model('Menu', MenuSchema);