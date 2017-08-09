var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var GerechtSchema = new Schema({
    naam: String,
    prijs: Number,
    categorie: {
        naam: String
    },
    bestelbaar: String,
    allergenen: [{
        naam: String,
        selected: Boolean
    }]
});

module.exports = mongoose.model('Gerecht', GerechtSchema);