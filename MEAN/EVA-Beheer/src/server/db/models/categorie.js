var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CategorieSchema = new Schema({
    naam: String,
});

module.exports = mongoose.model('Categorie', CategorieSchema);