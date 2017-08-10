import angular from 'angular';

const gerechtDetailModalController = angular.module('app.gerechtDetailModalController', [])

.controller('gerechtDetailModalController', function($scope, $uibModalInstance, getCategorieen, getChosenGerecht, $window, $mdSelect) {
  $scope.allergenen = [{naam: 'Gluten'}, {naam: 'Ei'}, {naam: 'Lupine'}, {naam: 'Melk'},
   {naam: 'Mosterd'}, {naam: 'Noten'}, {naam: 'Pinda\'s'}, {naam: 'Schaaldieren'}, {naam: 'Selderij'},
    {naam: 'Sesamzaad'}, {naam: 'Soja'}, {naam: 'Vis'}, {naam: 'Weekdieren'}, {naam: 'Zwaveldioxide'}];

  $scope.categorieen = getCategorieen;
  $scope.gerecht = getChosenGerecht;
  var updatedSelectedAllergens = [];

  $window.addEventListener('dblclick', function() {
      $mdSelect.hide();
  });

  // geselecteerde allergenen
  $scope.selection = [];
  // de allergenen opvolgen voor verandering
  //if toegevoegd, fout TypeError: Cannot read property nv of undefined is verdwenen, ons object moet eerst bestaan voor er gewatched wordt.
  $scope.$watch('allergenen|filter:{selected:true}', function(nv) {
      if (nv) {
          $scope.selection = nv.map(function(allergeen) {
              return allergeen;
          });
      }
  }, true);

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

      $uibModalInstance.close(updatedGerecht);
    } else {
      return;
    }
  };
});

export default gerechtDetailModalController;
