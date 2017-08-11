import angular from 'angular';

const challengeController = angular.module('app.challengeController', [])

.controller('challengeController', function($scope, challengeFactory) {
    //IMAGES
    $scope.imgDelete = require('../img/delete.png');
    $scope.imgDetail = require('../img/detail.png');

    challengeFactory.getChallenges($scope);

    const { deleteChallenge, getChallenges, updateChallenge, createChallenge } = challengeFactory;

    $scope.deleteChallenge = _.partial(deleteChallenge, $scope);
    $scope.createChallenge = _.partial(createChallenge, $scope, params);
    $scope.updateChallenge = _.partial(updateChallenge, $scope);
});

export default challengeController;
