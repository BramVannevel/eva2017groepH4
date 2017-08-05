import _ from 'lodash';
import angular from 'angular';

const restaurantsController = angular.module('app.restaurantsController', [])

.controller('restaurantsController', function($scope, restaurantsFactory, $uibModal) {
    //For modal
    var $ctrl = this;
    $ctrl.animationsEnabled = true;

    //Images
    $scope.imgDelete = require('../img/delete.png');
    $scope.arrow = require('../img/arrow.png');
    $scope.clearFilter = require('../img/clearFilter.png');

    let params = {
        createHasInput: false
    };

    //BIJ OPSTARTEN PAGINA, HAAL ALLE RESTAURANTS
    restaurantsFactory.getRestaurants($scope);

    //modal configuration
    //Door controllerAs '$ctrl' kunnen we in de html van de modal aan de scope van deze controller met $ctrl.
    $scope.openModal = function() {
      var modalInstance = $uibModal.open({
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

    //Filter tabel met restaurants
    $scope.filterRestaurants = function(restaurant) {
        if ($scope.filterNaam) {
            if (restaurant.naam.includes($scope.filterNaam))
                return restaurant;
        } else {
            //indien geen restaurantNaam opgegeven, return alle restaurants
            return restaurant;
        }
    };
    //MAAK FILTER LEEG
    $scope.eraseFilter = () => {
        $scope.filterNaam = '';
    };

    //Table actions
    const { createRestaurant, deleteRestaurant } = restaurantsFactory;
    $scope.deleteRestaurant = _.partial(deleteRestaurant, $scope);
    $scope.createRestaurant = _.partial(createRestaurant, $scope, params);

});

export default restaurantsController;
