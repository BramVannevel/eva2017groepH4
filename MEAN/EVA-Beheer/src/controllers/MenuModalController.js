import angular from 'angular';

const menuModalController = angular.module('app.menuModalController', [])

.controller('menuModalController', function($scope, $uibModalInstance) {
  console.log('In menuModalController');

  $scope.cancelModal = function(){
    console.log("cancelmodal");
    $uibModalInstance.dismiss('close');
  };

  $scope.ok = function(){
    $uibModalInstance.close('save');
  };

});

export default menuModalController;
