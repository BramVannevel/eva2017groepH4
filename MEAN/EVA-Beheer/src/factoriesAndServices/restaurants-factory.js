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
            //if (!$scope.menuItemDatum || !$scope.selectedGerecht) { return; } //als inputfield leeg is, niks toevoegen.

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

        /*
        //WIJZIG RESTAURANT
                function updateMenuItem($scope, menuItem) {
                    $http.put(MENU_API_ENDPOINT.url + `/${menuItem._id}`, { prijs: menuItem.updatedPrijs, omschrijving: menuItem.updatedOmschrijving, allergenen: $scope.selection }).success(response => {
                        getMenuItems($scope);
                        menuItem.isEditing = false;
                        uncheckCheckboxes($scope);
                    });
                }
        */

        //VERWIJDER RESTAURANT
        function deleteRestaurant($scope, restaurantToDelete) {
            $http.delete(RESTAURANTS_API_ENDPOINT.url + `/${restaurantToDelete._id}`).success(response => {
                getRestaurants($scope);
            });
        }

        return {
            getRestaurants,
            createRestaurant,
            deleteRestaurant
            //updateMenuItem
        };
    });

export default restaurantsFactory;
