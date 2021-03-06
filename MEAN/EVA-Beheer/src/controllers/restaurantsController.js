import _ from 'lodash';
import angular from 'angular';

const restaurantsController = angular.module('app.restaurantsController', [])

.controller('restaurantsController', function($scope, restaurantsFactory, $uibModal) {
    //For modal
    var $ctrl = this;
    $ctrl.animationsEnabled = true;

    //Images
    $scope.imgDelete = require('../img/delete.png');
    $scope.imgDetail = require('../img/detail.png');
    $scope.clearFilter = require('../img/clearFilter.png');

    let params = {
        createHasInput: false
    };

    //BIJ OPSTARTEN PAGINA, HAAL ALLE RESTAURANTS
    restaurantsFactory.getRestaurants($scope);

    //modal configuration voor het toevoegen van een restaurant
    //Door controllerAs '$ctrl' kunnen we in de html van de modal aan de scope van deze controller met $ctrl.
    $scope.openAddRestaurantModal = function() {
      let modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        controller: 'restaurantModalController',
        controllerAs: '$ctrl',
        template: require('../modals/restaurantModal.html'),
        size: 'lg',
        resolve: {
            //pass data to the modal's controller
        }
      });
      //De geretourneerde gegevens van modal dmv zijn scope doorgeven
      modalInstance.result.then(function(modalScope) {
          createRestaurant(modalScope, $scope);
      });
    };

    $scope.openRestaurantDetailModal = function(restaurant) {
      let modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        controller: 'restaurantDetailModalController',
        controllerAs: '$ctrl',
        template: require('../modals/restaurantDetailModal.html'),
        size: 'lg',
        resolve: {
            //pass data to the modal's controller
            getChosenRestaurant: function() {
              return restaurant;
            }
        }
      });
      modalInstance.result.then(function(updatedRestaurant) {
          updateRestaurant($scope, updatedRestaurant);
      });
    }

    //pagination
    $scope.currentPage = 1;
    $scope.pageSize = 5;

    //////////////////// CLICK HANDLERS ////////////////////

    //Cancel
    $scope.onCancelClick = (menuItem) => {
        menuItem.isEditing = false;
    };

    //////////////////// FILTERS ////////////////////

    $scope.filterRestaurantsOpNaam = function(restaurant) {
        if ($scope.filterNaam) {
            if (restaurant.naam.toLowerCase().includes($scope.filterNaam.toLowerCase()))
                return restaurant;
        } else {
            //indien geen restaurantNaam opgegeven, return alle restaurants
            return restaurant;
        }
    };

    $scope.filterRestaurantsOpNaam = function(restaurant) {
        if ($scope.filterNaam) {
            if (restaurant.naam.toLowerCase().includes($scope.filterNaam.toLowerCase()))
                return restaurant;
        } else {
            //indien geen restaurantNaam opgegeven, return alle restaurants
            return restaurant;
        }
    };

    $scope.filterRestaurantsOpStad = function(restaurant) {
      if ($scope.filterStad) {
          if (restaurant.adres.stad.toLowerCase().includes($scope.filterStad.toLowerCase()))
              return restaurant;
      } else {
              return restaurant;
      }
    }

    //MAAK FILTER LEEG
    $scope.eraseFilter = () => {
        $scope.filterNaam = '';
        $scope.filterStad = '';
    };

    // Actions
    const { createRestaurant, deleteRestaurant, updateRestaurant } = restaurantsFactory;
    $scope.deleteRestaurant = _.partial(deleteRestaurant, $scope);
    $scope.createRestaurant = _.partial(createRestaurant, $scope, params);
    $scope.updateRestaurant = _.partial(updateRestaurant, $scope);
});

export default restaurantsController;
