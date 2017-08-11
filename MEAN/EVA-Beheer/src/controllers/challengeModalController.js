import angular from 'angular';

const challengeModalController = angular.module('app.challengeModalController', [])

.controller('challengeModalController', function($scope, $uibModalInstance, getRestaurants, getGerechten, $q, $window, $mdSelect) {

  $scope.restaurants = getRestaurants;
  $scope.gerechten = getGerechten;
  $scope.dagen = [];

  var lowEnd = 1;
  var highEnd = 21;
  while(lowEnd <= highEnd){
    $scope.dagen.push(lowEnd++);
 }

    $scope.getSelectedDay = function() {
      if ($scope.dag !== undefined) {
        return "Dag " + $scope.dag;
      } else {
        return "Selecteer een dag";
      }
    }

    //Hide mdSelect on double click (won't close otherwise because we work inside a modal)
    $window.addEventListener('dblclick', function() {
        $mdSelect.hide();
    });

    $scope.restaurantSearch = function(query) {
        var results = query ? $scope.restaurants.filter($scope.restaurantFilter(query)) : $scope.restaurants;
        return $q.when(results);
    };

    $scope.restaurantFilter = function(query) {
        var lowercaseQuery = query.toLowerCase();
        return $scope.filterFn = function(restaurant) {
            return (restaurant.naam.toLowerCase().includes($scope.filterRestaurantNaam.toLowerCase()));
        };
    };

    $scope.gerechtSearch = function(query) {
        var results = query ? $scope.gerechten.filter($scope.gerechtFilter(query)) : $scope.gerechten;
        return $q.when(results);
    };

    $scope.gerechtFilter = function(query) {
        var lowercaseQuery = query.toLowerCase();
        return $scope.filterFn = function(gerecht) {
            return (gerecht.naam.toLowerCase().includes($scope.filterGerechtNaam.toLowerCase()));
        };
    };

  $scope.cancelModal = function() {
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function() {
      if (!$scope.titel || !$scope.omschrijving || !$scope.dag) { return; }
      $uibModalInstance.close($scope);
  };

});

export default challengeModalController;
