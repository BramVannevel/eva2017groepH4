var express = require("express");
var app = express();
var bodyParser = require('body-parser');
var routes = require('./src/server/routes');
var path = require('path');


var PORT = process.env.PORT || 3000;

app.use(bodyParser.json());

routes(app);

// Used for production build
app.use(express.static(path.join(__dirname, 'public')));

app.all("/*", function(req, res) {
    res.set('Content-Type', 'text/html')
        .sendFile(__dirname + '/public/index.html');
});


app.listen(PORT, function() {
    console.log("Server running on " + PORT);
});