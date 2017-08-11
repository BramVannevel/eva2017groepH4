import angular from 'angular';

const challengeModalController = angular.module('app.challengeModalController', [])

.controller('challengeModalController', function($scope, $uibModalInstance, getRestaurants, getGerechten, $timeout, $q) {

  $scope.restaurants = getRestaurants;
  $scope.gerechten = getGerechten;

    $scope.restaurantSearch = function(query) {
        var results = query ? $scope.restaurants.filter($scope.restaurantFilter(query)) : $scope.restaurants;
        console.log(results);
        return $q.when(results);
    };

    $scope.restaurantFilter = function(query) {
        var lowercaseQuery = query.toLowerCase();
        return $scope.filterFn = function(restaurant) {
            return (restaurant.naam.toLowerCase().includes($scope.filterNaam.toLowerCase()));
        };
    };

  $scope.cancelModal = function() {
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function() {
      if (!$scope.gerechtNaam || !$scope.selectedCategorie) { return; }
      $uibModalInstance.close($scope);
  };

});

export default challengeModalController;
