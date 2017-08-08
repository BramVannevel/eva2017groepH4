import angular from 'angular';

const restaurantDetailModalController = angular.module('app.restaurantDetailModalController', [])

.controller('restaurantDetailModalController', function($scope, $uibModalInstance, getChosenRestaurant) {
  //Now we can use the restaurant in the html of this modal.
  $scope.restaurant = getChosenRestaurant;

  $scope.cancelModal = function() {
    console.log("Ongewijzigd huisnummer: " + $scope.restaurant.adres.huisnummer);
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function(updatedRestaurant) {
  if(updatedRestaurant !== undefined) {
  //Waar niks veranderd is, gebruik de oorspronkelijke waarde
      updatedRestaurant._id = $scope.restaurant._id;
      if(!updatedRestaurant.naam)
        updatedRestaurant.naam = $scope.restaurant.naam;
       if (!updatedRestaurant.telefoon)
        updatedRestaurant.telefoon = $scope.restaurant.telefoon;
      if (!updatedRestaurant.adres.straat)
        updatedRestaurant.adres.straat = $scope.restaurant.adres.straat;
      if (!updatedRestaurant.adres.huisnummer)
        updatedRestaurant.adres.huisnummer = $scope.restaurant.adres.huisnummer;
      if (!updatedRestaurant.adres.stad)
        updatedRestaurant.adres.stad = $scope.restaurant.adres.stad;
      if (!updatedRestaurant.adres.postcode)
        updatedRestaurant.adres.postcode = $scope.restaurant.adres.postcode;

      console.log("Gewijzigd huisnummer: " + updatedRestaurant.adres.huisnummer);
      console.log("Naam: " + updatedRestaurant.naam);

      $uibModalInstance.close(updatedRestaurant);
    } else {
      return;
    }
  };
});

export default restaurantDetailModalController;
