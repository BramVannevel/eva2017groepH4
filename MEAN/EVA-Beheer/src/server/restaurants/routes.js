var mongoose = require('mongoose');
var Restaurant = require('../db/models/restaurant');
var express = require('express');
var router = express.Router();
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');

//ALLE RESTAURANTS OPHALEN
router.get('/list', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);

        //Only available for admins
        if(decoded.role === 'admin') {
            Restaurant.find(function(err, results) {
                if (err) { console.log(err); }
                res.send({ restaurants: results });
            });
      } else {return res.status(403).send({ success: false, msg: 'Je moet een admin zijn voor deze actie.' });}
    } else {
        return res.status(401).send({ success: false, msg: 'Geen token meegegeven.' });
    }
});

//EEN RESTAURANT TOEVOEGEN
router.post('/', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var menu = new Restaurant(req.body);
    menu.save(function(err) {
        if (err) { console.log(err); }

        res.send('Restaurant toegevoegd');
    });
     } else {
        return res.status(403).send({ success: false, msg: 'Geen token meegegeven.' });
    }
});


//EEN RESTAURANT VERWIJDEREN
router.delete('/:id', passport.authenticate('jwt', { session: false }),function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Restaurant.remove({ _id: mongoose.Types.ObjectId(id) }, function(err) {
        if (err) { console.log(err); }

        res.send('Restaurant verwijderd');
    });
     } else {
        return res.status(403).send({ success: false, msg: 'Geen token meegegeven.' });
    }
});

module.exports = router;
