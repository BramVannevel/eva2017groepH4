import angular from 'angular';

const challengeModalController = angular.module('app.challengeModalController', [])

.controller('challengeModalController', function($scope, $uibModalInstance, getRestaurants, getGerechten) {

  $scope.restaurants = getRestaurants;
  $scope.gerechten = getGerechten;

  $scope.cancelModal = function() {
    console.log('Vauit modalcontroller ' + $scope.categorieen);
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function() {
      if (!$scope.gerechtNaam || !$scope.selectedCategorie) { return; }
      $uibModalInstance.close($scope);
  };

});

export default challengeModalController;
