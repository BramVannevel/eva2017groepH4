import angular from 'angular';

const beheerControllers = angular.module('app.beheerControllers', [])

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

.controller('gerechtController', function($scope, beheerFactory, $uibModal) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');
    $scope.imgDetail = require('../img/detail.png');
    $scope.clearFilter = require('../img/clearFilter.png');

    beheerFactory.getGerechten($scope);

    var $ctrl = this;
    $ctrl.animationsEnabled = true;

    $scope.openAddGerechtModal = function() {
      let modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        controller: 'gerechtModalController',
        controllerAs: '$ctrl',
        template: require('../modals/gerechtModal.html'),
        size: 'lg',
        resolve: {
          getCategorieen: function() {
            return beheerFactory.getCategorieen($scope);
          }
        }
      });
      modalInstance.result.then(function(modalScope) {
          createGerecht(modalScope, $scope);
      });
    };

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

    //OM BIJ EDIT DE VOORHEEN GESELECTEERDE ALLERGENEN VAN EEN GERECHT IN TE LADEN
    $scope.geselecteerd = (allergeenNaam, gerecht) => {
      var isVoorheenGeselecteerd=false;
      angular.forEach(gerecht.allergenen, function(item,key){
        if(allergeenNaam == item.naam)
          isVoorheenGeselecteerd = true;
      });
      return isVoorheenGeselecteerd;
    }

    // helper method om selected allergenen te krijgen
    $scope.selectedAllergenen = function selectedAllergenen() {
        return filterFilter($scope.allergenen, { selected: true });
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    const { createGerecht, deleteGerecht, updateGerecht } = beheerFactory;

    $scope.createGerecht = _.partial(createGerecht, $scope);
    $scope.deleteGerecht = _.partial(deleteGerecht, $scope);
    $scope.updateGerecht = _.partial(updateGerecht, $scope);
})



export default beheerControllers;
