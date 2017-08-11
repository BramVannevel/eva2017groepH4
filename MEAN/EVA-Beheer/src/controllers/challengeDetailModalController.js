import angular from 'angular';

const challengeDetailModalController = angular.module('app.challengeDetailModalController', [])

.controller('challengeDetailModalController', function($scope, $uibModalInstance, getChosenChallenge, getRestaurants, getGerechten, $q, $window, $mdSelect) {

  $scope.challenge = getChosenChallenge;
  $scope.restaurants = getRestaurants;
  $scope.gerechten = getGerechten;
  $scope.dagen = [];

  var lowEnd = 1;
  var highEnd = 21;
  while(lowEnd <= highEnd){
    $scope.dagen.push(lowEnd++);
  }

    $scope.getSelectedDay = function() {
      if ($scope.updatedChallenge.dag === undefined) {
        return "Dag " + $scope.challenge.dag;
      } else if ($scope.updatedChallenge.dag !== undefined) {
        return "Dag " + $scope.updatedChallenge.dag;
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

  $scope.ok = function(updatedChallenge) {
    if(updatedChallenge !== undefined) {
        updatedChallenge._id = $scope.challenge._id;
        if(!updatedChallenge.titel)
          updatedChallenge.titel = $scope.challenge.titel;
        if(!updatedChallenge.omschrijving)
          updatedChallenge.omschrijving = $scope.challenge.omschrijving;
        if(!updatedChallenge.dag)
          updatedChallenge.dag = $scope.challenge.dag;
        if(!updatedChallenge.restaurant)
          updatedChallenge.restaurant = $scope.challenge.restaurant;
        if(!updatedChallenge.gerecht)
            updatedChallenge.gerecht = $scope.challenge.gerecht;
        if(!updatedChallenge.reward)
            updatedChallenge.reward = $scope.challenge.reward;

        $uibModalInstance.close(updatedChallenge);
      } else {
        return;
      }
  };
});

export default challengeDetailModalController;
