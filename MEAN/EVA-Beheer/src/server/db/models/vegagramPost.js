var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var VegagramPostSchema = new Schema({
    imageName: String,
    posted: Date,
    likes: Number,
    isPublic: Boolean,
    user: {
       ref: 'User',
       type: Schema.Types.ObjectId
   }//De user geassoscieerd met deze post
});

module.exports = mongoose.model('VegagramPost', VegagramPostSchema);
