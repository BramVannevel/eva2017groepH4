var mongoose = require('mongoose');
var Bestelling = require('../db/models/bestelling');
var express = require('express');
var router = express.Router();
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');

//ALLE BESTELLINGEN OPHALEN
router.get('/list', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
        Bestelling.find(function(err, results) {
            if (err) { console.log(err); }

            res.send({ bestellingen: results });
        });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//JSON ROUTE VOOR ANDROID APP (haalt bestellingen op die door de gebruiker van de app geplaatst zijn)
router.get('/bestellingList/:username', function(req, res){
  var gebruikersnaam = req.params.username;
    Bestelling.find({gebruiker:gebruikersnaam},function(err, results){
        if(err){console.log(err);}
        res.json(results);
    });
});

//POST VOOR ANDROID APP (ontvangt bestelling in JSON-formaat en schrijft die weg naar de databank)
router.post('/', function(req, res) {
    var bestelling = new Bestelling(req.body);
    bestelling.save(function(err) {
        if (err) { console.log(err); }

        res.send('Bestelling toegevoegd');
    });
});

//DELETE EEN BESTELLING
router.delete('/:id', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Bestelling.remove({ _id: mongoose.Types.ObjectId(id) }, function(err) {
        if (err) { console.log(err); }

        res.send('Bestelling verwijderd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

module.exports = router;
