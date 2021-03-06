import angular from 'angular';
import _ from 'lodash';

const restaurantsFactory = angular.module('app.restaurantsFactory', [])
    .factory('restaurantsFactory', ($q, $http, RESTAURANTS_API_ENDPOINT, USER_API_ENDPOINT, AuthService, $state) => {

        function getRestaurants($scope) {
            $http.get(RESTAURANTS_API_ENDPOINT.url + '/list').success(response => {
                $scope.restaurants = response.restaurants;
            });
        }

        //VOEG RESTAURANT TOE
        //modalScope bevat de ingevoerde gegevens, $scope bevat de huidige tabel waaraan het restaurant zal toegevoegd worden.
        function createRestaurant(modalScope, $scope, params) {
            $http.post(RESTAURANTS_API_ENDPOINT.url + '/', {
                naam: modalScope.restaurantNaam,
                telefoon: modalScope.restaurantTelefoon,
                adres: {
                  straat: modalScope.restaurantStraat,
                  huisnummer: modalScope.restaurantHuisnummer,
                  stad: modalScope.restaurantStad,
                  postcode: modalScope.restaurantPostcode
                }
            }).success(response => {
              console.log(response);
                getRestaurants($scope); //nadat we een restaurant hebben toegevoegd, alle restaurants opnieuw opvragen en weergeven.
            });
        }

        //WIJZIG RESTAURANT
        function updateRestaurant($scope, updatedRestaurant) {
                    $http.put(RESTAURANTS_API_ENDPOINT.url + `/${updatedRestaurant._id}`, {
                       naam: updatedRestaurant.naam,
                       adres: {
                     		 straat: updatedRestaurant.adres.straat,
                         huisnummer: updatedRestaurant.adres.huisnummer,
                         stad: updatedRestaurant.adres.stad,
                         postcode: updatedRestaurant.adres.postcode
                     	},
                       telefoon: updatedRestaurant.telefoon
                     }).success(response => {
                        getRestaurants($scope);
                    });
        }

        //VERWIJDER RESTAURANT
        function deleteRestaurant($scope, restaurantToDelete) {
            $http.delete(RESTAURANTS_API_ENDPOINT.url + `/${restaurantToDelete._id}`).success(response => {
                getRestaurants($scope);
            });
        }

        return {
            getRestaurants,
            createRestaurant,
            deleteRestaurant,
            updateRestaurant
        };
    });

export default restaurantsFactory;
