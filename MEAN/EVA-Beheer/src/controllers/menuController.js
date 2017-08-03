import _ from 'lodash';
import angular from 'angular';

const menuController = angular.module('app.menuController', [])

.controller('menuController', function($scope, menuFactory) {

    //Images
    $scope.imgDelete = require('../img/delete.png');
    $scope.arrow = require('../img/arrow.png');
    $scope.clearFilter = require('../img/clearFilter.png');

    let params = {
        createHasInput: false
    };

    //BIJ OPSTARTEN PAGINA, HAAL ALLE GERECHTEN OP HET MENU OP
    menuFactory.getMenuItems($scope);

    //pagination
    $scope.currentPage = 1;
    $scope.pageSize = 5;

    //////////////////// CLICK HANDLERS ////////////////////

    //Cancel
    $scope.onCancelClick = (menuItem) => {
        menuItem.isEditing = false;
    };

    //////////////////// FILTERS ////////////////////

    //Filter gerechten
    //filterCategorie bevat de naam van de geselecteerde categorie
    //In de ng-repeat overlopen we alle gerechten en filteren met deze methode zodat we enkel die gerechten tonen met een categorie gelijk aan de geselecteerde categorie.
    //indien niks geselecteerd is in de dropdown om te filteren returnen we gewoon alle gerechten aan de gebruiker.
    $scope.filterGerechten = function(gerecht) {
        if ($scope.filterCategorie) {
            return gerecht.categorie.naam === $scope.filterCategorie;
        } else {
            return gerecht;
        }
    };

    //Filter tabel met menu
    $scope.filterMenu = function(menuItem) {
        if ($scope.filterDatum) {
            //Eerst nog eens een Date type van maken, ze zitten zo wel al in databank, maar denk dat ze ergens naar String geparsed worden door angular bij het ophalen.
            //Daarom kregen we eerst een fout op de getTime functie. Ze werd gecalled op een String en niet op een Date object.
            let filterdatum = new Date($scope.filterDatum);
            let menuItemDatum = new Date(menuItem.datum);
            //kijken of de datum gelijk is aan de gewenste filter datum, zoja return dit item en check het volgende (ng-repeat called deze functie voor alle items)
            if (menuItemDatum.getTime() == filterdatum.getTime())
                return menuItem;
        } else {
            //indien geen filterdatum opgegeven, return alle items
            return menuItem;
        }
    };
    //MAAK FILTER LEEG
    $scope.eraseFilter = () => {
        $scope.filterDatum = null;
    };


    const { createMenuItem, deleteMenuItem } = menuFactory;
    $scope.deleteMenuItem = _.partial(deleteMenuItem, $scope);
    $scope.createMenuItem = _.partial(createMenuItem, $scope, params);

});

export default menuController;
