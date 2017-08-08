const bestellingFactory = angular.module('app.bestellingFactory', [])

.factory('bestellingFactory', ($q, $http, BESTELLINGEN_API_ENDPOINT) => {

  //GET
  function getBestellingen($scope) {
      $http.get(BESTELLINGEN_API_ENDPOINT.url + '/list').success(response => {
          $scope.bestellingen = response.bestellingen;
      });
  }

  //DEL
  function deleteBestelling($scope, bestelling) {
      $http.delete(BESTELLINGEN_API_ENDPOINT.url + `/${bestelling._id}`).success(response => {
          getBestellingen($scope);
      });
  }

  return {
      getBestellingen,
      deleteBestelling
  };

  });

  export default bestellingFactory;
