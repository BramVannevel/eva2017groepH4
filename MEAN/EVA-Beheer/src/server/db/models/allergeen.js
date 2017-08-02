var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var AllergeenSchema = new Schema({
    naam: String,
    selected: Boolean
});

module.exports = mongoose.model('Allergeen', AllergeenSchema);