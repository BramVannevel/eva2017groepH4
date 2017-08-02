import angular from 'angular';

const beheerControllers = angular.module('app.beheerControllers', [])


////////////////////////// ALLERGEEN CONTROLLER //////////////////////////

.controller('allergeenController', function($scope, beheerFactory) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');

    //haalt de allergenenlijst op bij het opstarten van de pagina
    beheerFactory.getAllergenen($scope);

    const { createAllergeen, deleteAllergeen, getAllergenen } = beheerFactory;

    $scope.createAllergeen = _.partial(createAllergeen, $scope);
    $scope.deleteAllergeen = _.partial(deleteAllergeen, $scope);
})

////////////////////////// CATEGORIE CONTROLLER //////////////////////////

.controller('categorieController', function($scope, beheerFactory) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');

    beheerFactory.getCategorieen($scope);

    const { createCategorie, deleteCategorie } = beheerFactory;

    $scope.createCategorie = _.partial(createCategorie, $scope);
    $scope.deleteCategorie = _.partial(deleteCategorie, $scope);
})

////////////////////////// GERECHTEN CONTROLLER //////////////////////////

.controller('gerechtController', function($scope, beheerFactory) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');
    $scope.imgSave = require('../img/save.png');
    $scope.imgEdit = require('../img/edit.png');
    $scope.imgCancel = require('../img/cancel.png');

    beheerFactory.getGerechten($scope);

    //afbeelding inladen
    $scope.afbeeldingBestelbaar = require('../img/categorie/bestelbaar.png');
    //Afbeelding pijl inladen
    $scope.arrow = require('../img/arrow.png');
    $scope.clearFilter = require('../img/clearFilter.png');



    //FILTER
        $scope.filterGerechten = function(gerecht) {
            if ($scope.filterCategorie) {
                return gerecht.categorie.naam === $scope.filterCategorie;
            }else if($scope.filterNaam){
              return gerecht.naam === $scope.filterNaam;
            }else{
                return gerecht;
            }
        };
        //MAAK FILTER LEEG
        $scope.eraseFilter = () => {
            $scope.filterCategorie = '';
            $scope.filterNaam = '';
        }


    //////CLICK HANDLERS//////
    //Edit
    $scope.onEditClick = (gerecht) => {
        gerecht.isEditing = true;
        gerecht.updatedPrijs = gerecht.prijs;
        gerecht.updatedNaam = gerecht.naam;
        gerecht.updatedBestelbaar = gerecht.bestelbaar;
        //gerecht.updatedAllergenen = gerecht.allergenen;

    }
    //OM BIJ EDIT DE VOORHEEN GESELECTEERDE ALLERGENEN VAN EEN GERECHT IN TE LADEN
    $scope.geselecteerd = (allergeenNaam, gerecht) => {
      var isVoorheenGeselecteerd=false;
      angular.forEach(gerecht.allergenen, function(item,key){
        if(allergeenNaam == item.naam)
          isVoorheenGeselecteerd = true;
      });
      return isVoorheenGeselecteerd;
}

    //Cancel
    $scope.onCancelClick = (menuItem) => {
        menuItem.isEditing = false;
    };

    ////////// ALLERGENEN ZONDER DROPDOWN, MAAR MET IMAGES, MET DROPDOWN IS ONDERSTAANDE CODE NIET NODIG //////////
    // geselecteerde allergenen
    $scope.selection = [];


    // helper method om selected allergenen te krijgen
    $scope.selectedAllergenen = function selectedAllergenen() {
        return filterFilter($scope.allergenen, { selected: true });
    };

    // de allergenen opvolgen voor verandering
    //if toegevoegd, fout TypeError: Cannot read property nv of undefined is verdwenen, ons object moet eerst bestaan voor er gewatched wordt.
    $scope.$watch('allergenen|filter:{selected:true}', function(nv) {
        if (nv) {
            $scope.selection = nv.map(function(allergeen) {
                return allergeen;
            });
        }
    }, true);

    //IMAGES REQUIRING ZODAT WEBPACK KAN RESOLVEN
    $scope.loadImage = function(image) {
        return require('../img/allergenen/' + image + '.png');
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    const { createGerecht, deleteGerecht, updateGerecht } = beheerFactory;

    $scope.createGerecht = _.partial(createGerecht, $scope);
    $scope.deleteGerecht = _.partial(deleteGerecht, $scope);
    $scope.updateGerecht = _.partial(updateGerecht, $scope);
})



export default beheerControllers;
