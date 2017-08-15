/*
Source: https://www.codementor.io/tips/9172397814/setup-file-uploading-in-an-express-js-application-using-multer-js
*/
  var mongoose = require('mongoose');
  var config = require('../db/db');
  var express = require('express');
  var passport = require('passport');
  var jwt = require('jwt-simple');
  var VegagramPost = require('../db/models/vegagramPost');
  var multer = require('multer'),
	   bodyParser = require('body-parser'),
	   path = require('path');

var router = express.Router();

/**
* Post een afbeelding naar de server (in de map uploads)
* Multipart form data gebruiken met als form-data
* Key: fileToUpload, Value: binary file
* Saved de imageName samen met het userId van de user die de upload deed in het VegagramPost database model
*/
router.post('/', passport.authenticate('jwt', { session: false }), multer({ dest: './src/server/vegagram/uploads/'}).single('fileToUpload'), function(req,res){
  var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
        var userId = decoded._id;

        var vegagramPost = new VegagramPost({imageName: req.file.filename, user: userId});
        vegagramPost.save(function(err) {
            if (err) { console.log(err); }

            res.send('Post saved, filename: ' + req.file.filename);
        });
        } else {
            return res.status(403).send({ success: false, msg: 'No token provided.' });
        }
});

/**
* Haal een afbeelding op aan de hand van de naam die multer eraan gaf (id)
* Geeft een binary terug. Append .jpg om de file te kunnen openen.
*/
router.get('/uploads/:id', function(req, res){
		var id = req.params.id;
		console.log(id);
		res.sendFile('./uploads/'+ id, { root : __dirname});
});

module.exports = router;
