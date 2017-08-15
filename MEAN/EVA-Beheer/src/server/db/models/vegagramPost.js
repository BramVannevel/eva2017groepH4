var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var VegagramPostSchema = new Schema({
    imageName: String,
    user: {
       ref: 'User',
       type: Schema.Types.ObjectId
   }//De user geassoscieerd met deze post
});

module.exports = mongoose.model('VegagramPost', VegagramPostSchema);
