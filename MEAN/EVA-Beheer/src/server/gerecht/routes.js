var mongoose = require('mongoose');
var Gerecht = require('../db/models/gerecht');
var express = require('express');
var router = express.Router();
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');

//ALLE GERECHTEN OPHALEN
router.get('/list', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
        Gerecht.find(function(err, results) {
            if (err) { console.log(err); }

            res.send({ gerechten: results });
        });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//JSON ROUTE VOOR ANDROID APP (haalt alle gerechten op uit databank)
router.get('/gerechtList', function(req, res){
    Gerecht.find(function(err, results){
        if(err){console.log(err);}
        res.json(results);
    });
});

//GERECHT TOEVOEGEN
router.post('/', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var gerecht = new Gerecht(req.body);
    gerecht.save(function(err) {
        if (err) { console.log(err); }

        res.send('Gerecht toegevoegd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//GERECHT VERWIJDEREN
router.delete('/:id', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Gerecht.remove({ _id: mongoose.Types.ObjectId(id) }, function(err) {
        if (err) { console.log(err); }

        res.send('Gerecht verwijderd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//GERECHT WIJZIGEN
router.put('/:id', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Gerecht.update({ _id: mongoose.Types.ObjectId(id) }, {
        $set: { naam: req.body.naam, omschrijving: req.body.omschrijving, categorie: req.body.categorie, allergenen: req.body.allergenen }
    }, function(err) {
        if (err) { console.log(err); }

        res.send('Gerecht updated');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

module.exports = router;
