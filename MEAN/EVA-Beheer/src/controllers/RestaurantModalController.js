import angular from 'angular';

const restaurantModalController = angular.module('app.restaurantModalController', [])

.controller('restaurantModalController', function($scope, $uibModalInstance) {
  console.log('In restaurantModalController');

  $scope.cancelModal = function(){
    console.log("cancel clicked in modal");
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function(){
    console.log("Save clicked in Modal");
    //De scope van de modal doorgeven aan restaurantsController
    console.log("RestaurantModalController, restaurantNaam: " + $scope.restaurantNaam);
    $uibModalInstance.close($scope);
  };

});

export default restaurantModalController;
