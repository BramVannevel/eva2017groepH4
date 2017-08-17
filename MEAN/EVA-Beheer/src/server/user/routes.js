var express = require("express");
var app = express();
var mongoose = require('mongoose');
var User = require('../db/models/user');
var router = express.Router();
var morgan = require('morgan');
var passport = require('passport');
var config = require('../db/db');
var jwt = require('jwt-simple');


app.use(morgan('dev'));
app.use(passport.initialize());


// pass passport for configuration
require('../db/passport')(passport);

//REGISTREER EEN GEBRUIKER (bv. eenmalig gebruiken voor Admin aan te maken) (POST http://localhost:8080/user/signup --> BODY =  name: Admin   password: ...)
/**
* Required xform params: name, password
* Optional xform params: role (defaulted to 'user' in scheme)
*/
router.post('/signup', function(req, res) {
    if (!req.body.name || !req.body.password) {
        res.json({ success: false, msg: 'Please pass name and password.' });
    } else {
        var newUser = new User({
            name: req.body.name,
            password: req.body.password
        });
        //If a role is passed (normally to create an admin, add it explicitly to the object)
        if(req.body.role) {
          newUser['role'] = req.body.role
        };
        // save the user
        newUser.save(function(err) {
            if (err) {
                return res.json({ success: false, msg: 'Username already exists. ' + err });
            }
            res.json({ success: true, msg: 'Successful created new user.' });
        });
    }
});

//AUTHENTICATIE VOOR LOGIN (POST http://localhost:8080/user/authenticate)
router.post('/authenticate', function(req, res) {
    User.findOne({
        name: req.body.name
    }, function(err, user) {
        if (err) throw err;

        if (!user) {
            res.send({ success: false, msg: 'Authentication failed. User not found.' });
        } else {
            // check if password matches and if user has the role admin
            user.comparePassword(req.body.password, function(err, isMatch) {
                if (isMatch && !err) {
                    var token = jwt.encode(user, config.secret);
                    res.json({ success: true, token: 'JWT ' + token });
                } else {
                    res.send({ success: false, msg: 'Authentication failed. Wrong password.' });
                }
            });
        }
    });
});

// route to information about the user (GET http://localhost:8080/user/userinfo)
router.get('/userinfo', passport.authenticate('jwt', { session: false }), function(req, res) {
    var token = getToken(req.headers);
    if (token) {
        var decoded = jwt.decode(token, config.secret);
        User.findOne({
            name: decoded.name
        }, function(err, user) {
            if (err) throw err;
            if (!user) {
                return res.status(403).send({ success: false, msg: 'Authentication failed. User not found.' });
            } else {
                res.json({ success: true, msg: 'Welcome in the member area ' + user.name + '!', user: decoded });
            }
        });
    } else {
        return res.status(403).send({ success: false, msg: 'No token provided.' });
    }
});

//HET JSON WEB TOKEN OPHALEN UIT DE HEADERS VAN DE INKOMENDE HTTP REQUESTS
getToken = function(headers) {
    if (headers && headers.authorization) {
        var parted = headers.authorization.split(' ');
        if (parted.length === 2) {
            return parted[1];
        } else {
            return null;
        }
    } else {
        return null;
    }
};

module.exports = router;
