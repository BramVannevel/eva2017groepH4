/*
Source: https://www.codementor.io/tips/9172397814/setup-file-uploading-in-an-express-js-application-using-multer-js
*/
  var mongoose = require('mongoose');
  var config = require('../db/db');
  var express = require('express');
  var passport = require('passport');
  var jwt = require('jwt-simple');
  var multer = require('multer'),
	   bodyParser = require('body-parser'),
	   path = require('path');

var router = express.Router();

/**
* Post een afbeelding naar de server (in de map uploads)
* Multipart form data gebruiken met als form-data
* Key: fileToUpload, Value: binary file
*/
router.post('/', multer({ dest: './src/server/vegagram/uploads/'}).single('fileToUpload'), function(req,res){
	console.log(req.body);
	 console.log(req.file.filename)
	 res.send({filename:req.file.filename})
	res.status(204).end();
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
