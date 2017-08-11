const challengeFactory = angular.module('app.challengeFactory', [])

.factory('challengeFactory', ($q, $http, CHALLENGES_API_ENDPOINT) => {

  //GET
  function getChallenges($scope) {
    $http.get(CHALLENGES_API_ENDPOINT.url + '/list').success(response => {
      $scope.challenges = response.challenges;
    });
  }

  //POST
  function createChallenge(modalScope, $scope, params) {
    $http.post(CHALLENGES_API_ENDPOINT.url + '/', {
      titel: modalScope.titel,
      omschrijving: modalScope.omschrijving,
      dag: modalScope.dag,
      restaurant: modalScope.restaurant,
      gerecht: modalScope.gerecht,
      reward: modalScope.reward
    }).success(response => {
      console.log(response);
      getChallenges($scope);
    });
  }

  //PUT
  function updateChallenge($scope, updatedChallenge) {
    $http.put(CHALLENGES_API_ENDPOINT.url + `/${updatedChallenge._id}`, {
      titel: updatedChallenge.titel,
      omschrijving: updatedChallenge.omschrijving,
      dag: updatedChallenge.dag,
      restaurant: updatedChallenge.restaurant,
      gerecht: updatedChallenge.gerecht,
      reward: updatedChallenge.reward
    }).success(response => {
      getChallenges($scope);
    });
  }

  //DEL
  function deleteChallenge($scope, challenge) {
    $http.delete(CHALLENGES_API_ENDPOINT.url + `/${challenge._id}`).success(response => {
      getChallenges($scope);
    });
  }

  return {
    getChallenges,
    deleteChallenge,
    createChallenge,
    updateChallenge
  };

});

export default challengeFactory;
