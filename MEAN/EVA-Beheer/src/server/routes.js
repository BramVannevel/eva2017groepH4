var restaurantRoutes = require('./restaurants/routes');
var userRoutes = require('./user/routes');
var categorieenRoutes = require('./categorieen/routes');
var gerechtenRoutes = require('./gerecht/routes');
var bestellingenRoutes = require('./bestellingen/routes');

var bodyParser = require('body-parser');
var cors = require('cors');

module.exports = function routes(app) {
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({ extended: true }));

    app.use(cors());

//AANGEVEN WAAR DE ROUTES TE VINDEN ZIJN VOOR ELKE STATE
    app.use('/user', userRoutes);
    app.use('/restaurants', restaurantRoutes);
    app.use('/categorieen', categorieenRoutes);
    app.use('/gerechten', gerechtenRoutes);
    app.use('/bestellingen', bestellingenRoutes);
};
