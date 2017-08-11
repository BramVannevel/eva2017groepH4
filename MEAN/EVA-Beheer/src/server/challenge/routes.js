var mongoose = require('mongoose');
var Challenge = require('../db/models/challenge');
var express = require('express');
var router = express.Router();
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');

//ALLE CHALLENGE OPHALEN
router.get('/list', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
        Challenge.find(function(err, results) {
            if (err) { console.log(err); }

            res.send({ challenges: results });
        });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//TOEVOEGEN CHALLENGE
router.post('/', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var challenge = new Challenge(req.body);
    challenge.save(function(err) {
        if (err) { console.log(err); }

        res.send('Challenge toegevoegd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//CHALLENGE WIJZIGEN
router.put('/:id', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Challenge.update({ _id: mongoose.Types.ObjectId(id) }, {
        $set: {
          titel: req.body.titel,
          omschrijving: req.body.omschrijving,
          dag: req.body.dag,
          restaurant: req.body.restaurant,
          gerecht: req.body.gerecht,
          rewared: req.body.reward
        }
    }, function(err) {
        if (err) { console.log(err); }

        res.send('Gerecht updated');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//DELETE EEN CHALLENGE
router.delete('/:id', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Challenge.remove({ _id: mongoose.Types.ObjectId(id) }, function(err) {
        if (err) { console.log(err); }

        res.send('Challenge verwijderd');
    });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

module.exports = router;
