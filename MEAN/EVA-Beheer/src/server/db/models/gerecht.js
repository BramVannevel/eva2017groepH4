var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var GerechtSchema = new Schema({
    naam: String,
    omschrijving: String,
    categorie: {
        naam: String
    },
    allergenen: [{
        naam: String,
        selected: Boolean
    }]
});

module.exports = mongoose.model('Gerecht', GerechtSchema);
