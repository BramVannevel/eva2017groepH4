var menuRoutes = require('./menu/routes');
var userRoutes = require('./user/routes');
var allergenenRoutes = require('./allergenen/routes');
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
    app.use('/menu', menuRoutes);
    app.use('/allergenen', allergenenRoutes);
    app.use('/allergenen', allergenenRoutes);
    app.use('/categorieen', categorieenRoutes);
    app.use('/gerechten', gerechtenRoutes);
    app.use('/bestellingen', bestellingenRoutes);
};
