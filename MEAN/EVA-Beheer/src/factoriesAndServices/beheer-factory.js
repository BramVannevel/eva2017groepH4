import angular from 'angular';
import _ from 'lodash';

const beheerFactory = angular.module('app.beheerFactory', [])


.factory('beheerFactory', ($q, $http, CATEGORIEEN_API_ENDPOINT, GERECHTEN_API_ENDPOINT) => {

    ////////////////////////// CATEGORIEEN BEHEER //////////////////////////
    //GET
    function getCategorieen($scope) {
        $http.get(CATEGORIEEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.categorieen = response.categorieen;
        });
    }
    //POST
    function createCategorie(modalScope, $scope) {
        if (!modalScope.categorieNaam) { return; }

        $http.post(CATEGORIEEN_API_ENDPOINT.url + '/', {
            naam: modalScope.categorieNaam,
        }).success(response => {
            getCategorieen($scope);
        });
    }
    //DEL
    function deleteCategorie($scope, categorie) {
        $http.delete(CATEGORIEEN_API_ENDPOINT.url + `/${categorie._id}`).success(response => {
            getCategorieen($scope);
        });
    }

    ////////////////////////// GERECHTEN BEHEER //////////////////////////

    //GET (ZOWEL ALLE ALLERGENEN ALS GERECHTEN OPHALEN EN IN VARIABELEN STOPPEN)
    function getGerechten($scope) {
        $http.get(GERECHTEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.gerechten = response.gerechten;
        });
        $http.get(CATEGORIEEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.categorieen = response.categorieen;
        });
    }

    //POST
    function createGerecht(modalScope, $scope) {
        $http.post(GERECHTEN_API_ENDPOINT.url + '/', {
            naam: modalScope.gerechtNaam,
            omschrijving: modalScope.gerechtOmschrijving,
            categorie: modalScope.selectedCategorie,
            allergenen: modalScope.selection
        }).success(response => {
            getGerechten($scope);
        });
    }

    //DEL
    function deleteGerecht($scope, gerecht) {
        $http.delete(GERECHTEN_API_ENDPOINT.url + `/${gerecht._id}`).success(response => {
            getGerechten($scope);
        });
    }
    //UPDATE
    function updateGerecht($scope, updatedGerecht) {
            $http.put(GERECHTEN_API_ENDPOINT.url + `/${updatedGerecht._id}`, {
                naam: updatedGerecht.naam,
                omschrijving: updatedGerecht.omschrijving,
                categorie: updatedGerecht.categorie,
                allergenen: updatedGerecht.allergenen
            }).success(response => {
                getGerechten($scope);
            });
    }

    return {
        getCategorieen,
        getGerechten,
        createCategorie,
        createGerecht,
        deleteCategorie,
        deleteGerecht,
        updateGerecht
    };

});

export default beheerFactory;
