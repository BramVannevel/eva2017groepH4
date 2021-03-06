import angular from 'angular';

const beheerControllers = angular.module('app.beheerControllers', [])

////////////////////////// CATEGORIE CONTROLLER //////////////////////////

.controller('categorieController', function($scope, beheerFactory, $uibModal) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');
    $scope.clearFilter = require('../img/clearFilter.png');

    //pagination
    $scope.currentPage = 1;
    $scope.pageSize = 5;

    var $ctrl = this;
    $ctrl.animationsEnabled = true;

    beheerFactory.getCategorieen($scope);

    $scope.openAddCategorieModal = function() {
      let modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        controller: 'categorieModalController',
        controllerAs: '$ctrl',
        template: require('../modals/categorieModal.html'),
        size: 'lg',
        resolve: {
        }
      });
      modalInstance.result.then(function(modalScope) {
          createCategorie(modalScope, $scope);
      });
    };

    $scope.filterCategorieenOpNaam = function(categorie) {
        if ($scope.filterNaam) {
            if (categorie.naam.toLowerCase().includes($scope.filterNaam.toLowerCase()))
                return categorie;
        } else {
            return categorie;
        }
    };

    $scope.eraseFilter = () => {
        $scope.filterNaam = '';
    }

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
    beheerFactory.getCategorieen($scope);

    //pagination
    $scope.currentPage = 1;
    $scope.pageSize = 5;

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
            return $scope.categorieen;
          }
        }
      });
      modalInstance.result.then(function(modalScope) {
          createGerecht(modalScope, $scope);
      });
    };

    $scope.openGerechtDetailModal = function(gerecht) {
      let modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        controller: 'gerechtDetailModalController',
        controllerAs: '$ctrl',
        template: require('../modals/gerechtDetailModal.html'),
        size: 'lg',
        resolve: {
          getCategorieen: function() {
            return $scope.categorieen;
          },
          getChosenGerecht: function() {
            return gerecht;
          }
        }
      });
      modalInstance.result.then(function(updatedGerecht) {
          updateGerecht($scope, updatedGerecht);
      });
    };

    $scope.filterGerechtenOpNaam = function(gerecht) {
        if ($scope.filterNaam) {
            if (gerecht.naam.toLowerCase().includes($scope.filterNaam.toLowerCase()))
                return gerecht;
        } else {
            return gerecht;
        }
    };

    $scope.filterGerechtenOpCategorie = function(gerecht) {
        if ($scope.filterCategorie) {
            return gerecht.categorie.naam === $scope.filterCategorie;
        }else {
            return gerecht;
        }
    };

    $scope.eraseFilter = () => {
        $scope.filterCategorie = '';
        $scope.filterNaam = '';
    }

    const { createGerecht, deleteGerecht, updateGerecht } = beheerFactory;

    $scope.createGerecht = _.partial(createGerecht, $scope);
    $scope.deleteGerecht = _.partial(deleteGerecht, $scope);
    $scope.updateGerecht = _.partial(updateGerecht, $scope);
})



export default beheerControllers;
