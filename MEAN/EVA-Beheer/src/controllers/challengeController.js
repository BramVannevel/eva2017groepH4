import angular from 'angular';

const challengeController = angular.module('app.challengeController', [])

.controller('challengeController', function($scope, challengeFactory, beheerFactory, restaurantsFactory, $uibModal) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');
    $scope.imgDetail = require('../img/detail.png');
    $scope.clearFilter = require('../img/clearFilter.png');

    challengeFactory.getChallenges($scope);

    //pagination
    $scope.currentPage = 1;
    $scope.pageSize = 5;

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

    $scope.filterChallengesOpTitel = function(challenge) {
        if ($scope.filterTitel) {
            if (challenge.titel.toLowerCase().includes($scope.filterTitel.toLowerCase()))
                return challenge;
        } else {
            return challenge;
        }
    };

    $scope.filterChallengesOpDag = function(challenge) {
        if ($scope.filterDag) {
          console.log($scope.filterDag);
          console.log(challenge.dag.toString());
            if (challenge.dag.toString().includes($scope.filterDag))
                return challenge;
        } else {
            return challenge;
        }
    };

    $scope.eraseFilter = () => {
        $scope.filterTitel = '';
        $scope.filterDag = '';
    }

    const { deleteChallenge, getChallenges, updateChallenge, createChallenge } = challengeFactory;

    $scope.deleteChallenge = _.partial(deleteChallenge, $scope);
    $scope.createChallenge = _.partial(createChallenge, $scope);
    $scope.updateChallenge = _.partial(updateChallenge, $scope);
});

export default challengeController;
