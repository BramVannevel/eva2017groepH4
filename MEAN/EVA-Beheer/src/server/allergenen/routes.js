var mongoose = require('mongoose');
var Allergeen = require('../db/models/allergeen');
var express = require('express');
var router = express.Router();
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');


//ALLE ALLERGENEN OPHALEN
router.get('/list', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
        Allergeen.find(function(err, results) {
            if (err) { console.log(err); }

            res.send({ allergenen: results });
        });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//JSON ROUTE VOOR ANDROID APP (haalt allergenen op en returned JSON)
router.get('/allergeenList', function(req, res){
    Allergeen.find(function(err, results){
        if(err){console.log(err);}
        res.json(results);
    });
});

//EEN ALLERGEEN TOEVOEGEN
router.post('/', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var allergeen = new Allergeen(req.body);
    allergeen.save(function(err) {
        if (err) { console.log(err); }

        res.send('Allergeen toegevoegd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//EEN ALLERGEEN VERWIJDEREN
router.delete('/:id', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Allergeen.remove({ _id: mongoose.Types.ObjectId(id) }, function(err) {
        if (err) { console.log(err); }

        res.send('Allergeen verwijderd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

module.exports = router;
