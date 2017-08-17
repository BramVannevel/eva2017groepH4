import angular from 'angular';

const categorieModalController = angular.module('app.categorieModalController', [])

.controller('categorieModalController', function($scope, $uibModalInstance) {
  $scope.cancelModal = function() {
    $uibModalInstance.dismiss('Cancel');
  };

  $scope.ok = function() {
      $uibModalInstance.close($scope);
  };

});

export default categorieModalController;
