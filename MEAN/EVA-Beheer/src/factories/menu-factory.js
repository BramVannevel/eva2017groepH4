import angular from 'angular';
import _ from 'lodash';

const menuFactory = angular.module('app.menuFactory', [])
    .factory('menuFactory', ($q, $http, MENU_API_ENDPOINT, USER_API_ENDPOINT, GERECHTEN_API_ENDPOINT, CATEGORIEEN_API_ENDPOINT, AuthService, $state) => {

        function getMenuItems($scope) {
            $http.get(MENU_API_ENDPOINT.url + '/list').success(response => {
                $scope.menu = response.menu;
            });
            $http.get(GERECHTEN_API_ENDPOINT.url + '/list').success(response => {
                $scope.gerechten = response.gerechten;
            });
            $http.get(CATEGORIEEN_API_ENDPOINT.url + '/list').success(response => {
                $scope.categorieen = response.categorieen;
            });
        }

        //create
        function createMenuItem($scope, params) {
            if (!$scope.menuItemDatum || !$scope.selectedGerecht) { return; } //als inputfield leeg is, niks toevoegen.

            $http.post(MENU_API_ENDPOINT.url + '/', {
                datum: $scope.menuItemDatum,
                gerecht: $scope.selectedGerecht
            }).success(response => {
                getMenuItems($scope); //nadat we een menu item hebben toegevoegd, alle menu items opnieuw opvragen en weergeven.
            });
        }
        /*
        //update
                function updateMenuItem($scope, menuItem) {
                    $http.put(MENU_API_ENDPOINT.url + `/${menuItem._id}`, { prijs: menuItem.updatedPrijs, omschrijving: menuItem.updatedOmschrijving, allergenen: $scope.selection }).success(response => {
                        getMenuItems($scope);
                        menuItem.isEditing = false;
                        uncheckCheckboxes($scope);
                    });
                }
        */
        function deleteMenuItem($scope, menuItemToDelete) {
            $http.delete(MENU_API_ENDPOINT.url + `/${menuItemToDelete._id}`).success(response => {
                getMenuItems($scope);
            });
        }


        //FILTERS


        return {
            getMenuItems,
            createMenuItem,
            deleteMenuItem
            //updateMenuItem
        };
    });

export default menuFactory;