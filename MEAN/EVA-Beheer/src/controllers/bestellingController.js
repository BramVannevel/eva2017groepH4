import angular from 'angular';

const bestellingController = angular.module('app.bestellingController', [])

.controller('bestellingController', function($scope, bestellingFactory) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');

    //haalt de bestellingenlijst op bij het opstarten van de pagina
    bestellingFactory.getBestellingen($scope);

    const { deleteBestelling, getBestellingen } = bestellingFactory;
    $scope.deleteBestelling = _.partial(deleteBestelling, $scope);
})


export default bestellingController;
