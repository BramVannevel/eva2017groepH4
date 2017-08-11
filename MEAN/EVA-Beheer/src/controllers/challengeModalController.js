import angular from 'angular';

const challengeModalController = angular.module('app.challengeModalController', [])

.controller('challengeModalController', function($scope, $uibModalInstance, getRestaurants, getGerechten, $timeout, $q) {

  //$scope.restaurants = getRestaurants;
  $scope.gerechten = getGerechten;

/////////////////////////////////////////////////////////////////////////

    $scope.restaurants = [
      {naam: 'Resto1'},
      {naam: 'resto2'},
      {naam: 'Test1'},
      {naam: 'NogEenResto'},
      {naam: 'YetAnother'},
      {naam: 'ShouldScroll'}
    ];


    $scope.querySearch = function(query) {
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
     /////////////////////////////////////////////////////////////////////////

/*
 //Zoekt naar een restaurant op basis van wat is ingetypt
$scope.querySearch = function(query) {
  var restaurants = [{naam: 'Rest1'}, {naam: 'rest2'}];
    //console.log('RESTAURANTS: ' + getRestaurants);
    var results = query ? restaurants.filter($scope.createFilterFor(query)) : [];
    return $q.when(results);
};

     //Zoekt naar een restaurant op basis van lowercase
    $scope.createFilterFor = function(query) {
        var lowercaseQuery = angular.lowercase(query);

        return function filterFn(restaurant) {
            return (restaurant.value.indexOf(lowercaseQuery) === 0);
        };
    }
    */

  $scope.cancelModal = function() {
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function() {
      if (!$scope.gerechtNaam || !$scope.selectedCategorie) { return; }
      $uibModalInstance.close($scope);
  };

});

export default challengeModalController;
