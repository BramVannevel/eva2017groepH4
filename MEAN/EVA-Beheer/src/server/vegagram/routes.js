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
fs = require('fs');
var appRoot = require('app-root-path');

var router = express.Router();

/**
* Add een vegagramPost
* Upload onder andere een afbeelding naar de server (in de map public/vegagramUploads) --> in public zodat het niet mee gebundeld wordt door webpack.
* Anders kan de applicatie er niet meer aan om afbeeldingen op te slaan en op te halen.
* Multipart form data gebruiken met als form-data:
* Key: fileToUpload, Value: binary file
* Saved de imageName samen met het userId van de user die de upload deed in het VegagramPost database model
*/
router.post('/', passport.authenticate('jwt', { session: false }), multer({ dest: './public/vegagramUploads/'}).single('fileToUpload'), function(req,res){
  var token = getToken(req.headers);
  if (token) {
    var decoded = jwt.decode(token, config.secret);
    var userId = decoded._id;

    var vegagramPost = new VegagramPost({
      imageName: req.file.filename,
      posted: req.body.posted,
      likes: req.body.likes,
      isPublic: req.body.isPublic,
      user: userId
    });

    vegagramPost.save(function(err) {
      if (err) { console.log(err); }
      res.send('Post saved, image name: ' + req.file.filename);
    });
  } else {
    return res.status(403).send({ success: false, msg: 'No token provided.' });
  }
});

/**
* Returned alle vegagramPosts van de ingelogde gebruiker
* Hieruit kunnen onder andere de ids van de uploaded images verzamelt worden en vervolgens
* kan daarmee een call gemaakt worden om de binaries terug te krijgen via het endpoint /uploads/:id
*/
router.get('/useruploads', passport.authenticate('jwt', { session: false }), function(req, res) {
  var token = getToken(req.headers);
  if (token) {
    var decoded = jwt.decode(token, config.secret);
    var userId = decoded._id;

    VegagramPost.find({user:userId}).populate('user').exec(function(err, results) {
      if (err) { console.log(err); }
      res.send({ posts: results });
    });
  } else {
    return res.status(403).send({ success: false, msg: 'No token provided.' });
  }
});

/**
* Returned alle public vegagramPosts van alle users
*/
router.get('/uploads', passport.authenticate('jwt', { session: false }), function(req, res) {
  var token = getToken(req.headers);
  if (token) {
    var decoded = jwt.decode(token, config.secret);

    VegagramPost.find({isPublic: {$eq: true}}).exec(function(err, results) {
      if (err) { console.log(err); }

      res.send({ posts: results });
    });
  } else {
    return res.status(403).send({ success: false, msg: 'No token provided.' });
  }
});

/**
* Haal een afbeelding van een VegagramPost op aan de hand van de naam ervan (die multer eraan gaf (id))
* Geeft een binary terug. Append .jpg om de file te kunnen openen.
*/
router.get('/uploads/:id', passport.authenticate('jwt', { session: false }), function(req, res){
  var token = getToken(req.headers);
  if (token) {
    var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    console.log(id);
    res.sendFile('./public/vegagramUploads/'+ id, { root : '' + appRoot});
  } else {
    return res.status(403).send({ success: false, msg: 'No token provided.' });
  }
});

/**
* Verwijderd een vegagramPost samen met de bijhorende afbeelding op de server in de 'uploads' folder
*/
router.delete('/:id', passport.authenticate('jwt', { session: false }),function(req, res) {
  var token = getToken(req.headers);
  if (token) {
    var decoded = jwt.decode(token, config.secret);
      var id = req.params.id;

      VegagramPost.findOne({_id: mongoose.Types.ObjectId(id)}, function(err, post) {
        if(err) {return;}
        fs.unlink('./public/vegagramUploads/' + post.imageName, function(err) {
          if(err) throw err;
          post.remove(function (err) {});
          res.send('Post deleted and image deleted from server');
        });
      });
  } else {
    return res.status(403).send({ success: false, msg: 'Geen token meegegeven.' });
  }
});

module.exports = router;
