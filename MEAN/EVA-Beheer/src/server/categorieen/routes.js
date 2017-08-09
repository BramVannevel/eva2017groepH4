var mongoose = require('mongoose');
var Categorie = require('../db/models/categorie');
var express = require('express');
var router = express.Router();
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');

//ALLE CATEGORIEEN OPHALEN
router.get('/list', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
        Categorie.find(function(err, results) {
            if (err) { console.log(err); }

            res.send({ categorieen: results });
        });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//JSON ROUTE VOOR ANDROID APP
router.get('/categorieList', function(req, res){
    Categorie.find(function(err, results){
        if(err){console.log(err);}
        res.json(results);
    });
});

//TOEVOEGEN CATEGORIE
router.post('/', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var categorie = new Categorie(req.body);
    categorie.save(function(err) {
        if (err) { console.log(err); }

        res.send('Categorie toegevoegd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//VERWIJDEREN CATEGORIE
router.delete('/:id', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Categorie.remove({ _id: mongoose.Types.ObjectId(id) }, function(err) {
        if (err) { console.log(err); }

        res.send('Categorie verwijderd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

module.exports = router;
