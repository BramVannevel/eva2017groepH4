import angular from 'angular';

const gerechtDetailModalController = angular.module('app.gerechtDetailModalController', [])

.controller('gerechtDetailModalController', function($scope, $uibModalInstance, getCategorieen, getChosenGerecht, $window, $mdSelect) {
  $scope.allergenen = [{naam: 'Gluten'}, {naam: 'Ei'}, {naam: 'Lupine'}, {naam: 'Melk'},
   {naam: 'Mosterd'}, {naam: 'Noten'}, {naam: 'Pinda\'s'}, {naam: 'Schaaldieren'}, {naam: 'Selderij'},
    {naam: 'Sesamzaad'}, {naam: 'Soja'}, {naam: 'Vis'}, {naam: 'Weekdieren'}, {naam: 'Zwaveldioxide'}];

  $scope.categorieen = getCategorieen;
  $scope.gerecht = getChosenGerecht;
  var updatedSelectedAllergens = [];

  //Hide mdSelect on double click (won't close otherwise because we work inside a modal)
  $window.addEventListener('dblclick', function() {
      $mdSelect.hide();
  });

  //OM BIJ EDIT DE VOORHEEN GESELECTEERDE ALLERGENEN VAN EEN GERECHT IN TE LADEN
  $scope.checkAlreadySelectedAllergens = (allergeen, gerecht) => {
      var isAlreadySelected = false;
      angular.forEach(gerecht.allergenen, function(item,key){
        if(allergeen.naam == item.naam)
          isAlreadySelected = true;
      });
      return isAlreadySelected;
    };

  $scope.cancelModal = function() {
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function(updatedGerecht) {
    if(updatedGerecht !== undefined) {
        updatedGerecht._id = $scope.gerecht._id;
        if(!updatedGerecht.naam)
          updatedGerecht.naam = $scope.gerecht.naam;
        if(!updatedGerecht.categorie)
          updatedGerecht.categorie = $scope.gerecht.categorie;
        if(!updatedGerecht.allergenen)
          updatedGerecht.allergenen = $scope.gerecht.allergenen;
        if(!updatedGerecht.omschrijving)
          updatedGerecht.omschrijving = $scope.gerecht.omschrijving;

        $uibModalInstance.close(updatedGerecht);
      } else {
        return;
      }
  };
});

export default gerechtDetailModalController;
