import angular from 'angular';

const gerechtModalController = angular.module('app.gerechtModalController', [])

.controller('gerechtModalController', function($scope, $uibModalInstance, getCategorieen) {
  $scope.allergenen = [{naam: 'Gluten'}, {naam: 'Ei'}, {naam: 'Lupine'}, {naam: 'Melk'},
   {naam: 'Mosterd'}, {naam: 'Noten'}, {naam: 'Pinda\'s'}, {naam: 'Schaaldieren'}, {naam: 'Selderij'},
    {naam: 'Sesamzaad'}, {naam: 'Soja'}, {naam: 'Vis'}, {naam: 'Weekdieren'}, {naam: 'Zwaveldioxide'}];

  $scope.categorieen = getCategorieen;

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

  $scope.cancelModal = function() {
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function() {
      $uibModalInstance.close($scope);
  };

});

export default gerechtModalController;
