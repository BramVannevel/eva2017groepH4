var mongoose = require('mongoose');
mongoURI = 'mongodb://localhost/eva'; //lokaal draaiende databank voor development
mongoose.connect( mongoURI || process.env.MONGOLAB_URI); //gebruikt de environment variable op heroku indien in productie (live)
//mongoose.connect(mongodb:'mongodb://dennis:restoapp@ds119368.mlab.com:19368/hogentresto':19368/hogentresto || mongoURI);
module.exports = {
    'secret': 'evaappsecret',
    'database': mongoURI || process.env.MONGOLAB_URI
};
