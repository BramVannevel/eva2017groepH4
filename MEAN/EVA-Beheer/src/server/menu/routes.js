var mongoose = require('mongoose');
var Menu = require('../db/models/menu');
var express = require('express');
var router = express.Router();
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');

//ALLE GERECHTEN DIE OP HET MENU STAAN OPHALEN
router.get('/list', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);

        //Only available for admins
        if(decoded.role === 'admin') {
            Menu.find(function(err, results) {
                if (err) { console.log(err); }
                res.send({ menu: results });
            });
      } else {return res.status(403).send({ success: false, msg: 'You have to be an admin to access this place' });}
    } else {
        return res.status(401).send({ success: false, msg: 'No token provided.' });
    }
});

//JSON ROUTE VOOR ANDROID APP
router.get('/menuList', function(req, res){
    Menu.find(function(err, results){
        if(err){console.log(err);}
        res.json(results);
    });
});


//EEN GERECHT TOEVOEGEN AAN HET MENU
router.post('/', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var menu = new Menu(req.body);
    menu.save(function(err) {
        if (err) { console.log(err); }

        res.send('Menu item toegevoegd');
    });
     } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});


//EEN GERECHT VAN HET MENU VERWIJDEREN
router.delete('/:id', passport.authenticate('jwt', { session: false }),function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
    var id = req.params.id;
    Menu.remove({ _id: mongoose.Types.ObjectId(id) }, function(err) {
        if (err) { console.log(err); }

        res.send('Menu item verwijderd');
    });
     } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

module.exports = router;
