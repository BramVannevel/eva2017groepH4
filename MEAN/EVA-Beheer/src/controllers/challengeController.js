import angular from 'angular';

const challengeController = angular.module('app.challengeController', [])

.controller('challengeController', function($scope, challengeFactory, beheerFactory, restaurantsFactory, $uibModal) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');
    $scope.imgDetail = require('../img/detail.png');

    challengeFactory.getChallenges($scope);

    //Ophalen zodat $scope.restaurants en $scope.gerechten bestaan om mee te geven aan modal
    restaurantsFactory.getRestaurants($scope);
    beheerFactory.getGerechten($scope);

    var $ctrl = this;
    $ctrl.animationsEnabled = true;

    $scope.openAddChallengeModal = function() {
      let modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        controller: 'challengeModalController',
        controllerAs: '$ctrl',
        template: require('../modals/challengeModal.html'),
        size: 'lg',
        resolve: {
          getGerechten: function() {
            return $scope.gerechten;
          },
          getRestaurants: function() {
            return $scope.restaurants;
          }
        }
      });
      modalInstance.result.then(function(modalScope) {
          createChallenge(modalScope, $scope);
      });
    };

    $scope.openChallengeDetailModal = function(challenge) {
      let modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        controller: 'challengeDetailModalController',
        controllerAs: '$ctrl',
        template: require('../modals/challengeDetailModal.html'),
        size: 'lg',
        resolve: {
          getChosenChallenge: function() {
            return challenge;
          },
          getGerechten: function() {
            return $scope.gerechten;
          },
          getRestaurants: function() {
            return $scope.restaurants;
          }
        }
      });
      modalInstance.result.then(function(updatedChallenge) {
          updateChallenge($scope, updatedChallenge);
      });
    }

    const { deleteChallenge, getChallenges, updateChallenge, createChallenge } = challengeFactory;

    $scope.deleteChallenge = _.partial(deleteChallenge, $scope);
    $scope.createChallenge = _.partial(createChallenge, $scope);
    $scope.updateChallenge = _.partial(updateChallenge, $scope);
});

export default challengeController;
