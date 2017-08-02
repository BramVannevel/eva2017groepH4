import angular from 'angular';
import _ from 'lodash';

const beheerFactory = angular.module('app.beheerFactory', [])


.factory('beheerFactory', ($q, $http, ALLERGENEN_API_ENDPOINT, CATEGORIEEN_API_ENDPOINT, GERECHTEN_API_ENDPOINT) => {

    ////////////////////////// ALLERGENEN BEHEER //////////////////////////
    //GET
    function getAllergenen($scope) {
        $http.get(ALLERGENEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.allergenen = response.allergenen;
        });
    }
    //POST
    function createAllergeen($scope) {
        if (!$scope.allergeenNaam) { return; }
        $http.post(ALLERGENEN_API_ENDPOINT.url + '/', {
            naam: $scope.allergeenNaam
        }).success(response => {
            getAllergenen($scope);
            $scope.allergeenNaam = '';
        });
    }
    //DEL
    function deleteAllergeen($scope, allergeen) {
        $http.delete(ALLERGENEN_API_ENDPOINT.url + `/${allergeen._id}`).success(response => {
            getAllergenen($scope);
        });
    }

    ////////////////////////// CATEGORIEEN BEHEER //////////////////////////
    //GET
    function getCategorieen($scope) {
        $http.get(CATEGORIEEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.categorieen = response.categorieen;
        });
    }
    //POST
    function createCategorie($scope) {
        if (!$scope.categorieNaam) { return; }

        $http.post(CATEGORIEEN_API_ENDPOINT.url + '/', {
            naam: $scope.categorieNaam,
        }).success(response => {
            getCategorieen($scope);
            $scope.categorieNaam = '';
        });
    }
    //DEL
    function deleteCategorie($scope, categorie) {
        $http.delete(CATEGORIEEN_API_ENDPOINT.url + `/${categorie._id}`).success(response => {
            getCategorieen($scope);
        });
    }

    ////////////////////////// GERECHTEN BEHEER //////////////////////////

    //GET (ZOWEL ALLE ALLERGENEN, ALS CATEGORIEEN ALS GERECHTEN OPHALEN EN IN VARIABELEN STOPPEN)
    function getGerechten($scope) {
        $http.get(GERECHTEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.gerechten = response.gerechten;
        });
        $http.get(CATEGORIEEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.categorieen = response.categorieen;
        });
        $http.get(ALLERGENEN_API_ENDPOINT.url + '/list').success(response => {
            $scope.allergenen = response.allergenen;
        });
    }
    //POST
    function createGerecht($scope) {
        if (!$scope.gerechtNaam || !$scope.gerechtPrijs || !$scope.selectedCategorie) { return; }
        $http.post(GERECHTEN_API_ENDPOINT.url + '/', {
            naam: $scope.gerechtNaam,
            prijs: $scope.gerechtPrijs,
            categorie: $scope.selectedCategorie, //{ naam: $scope.selectedCategorie }, --> we maken er geen object meer van want krijgen het object al door van ng-value (in html) nu (voorheen kregen we String categorie.naam)
            bestelbaar: $scope.gerecht.bestelbaar,
            allergenen: $scope.selection //stringsArrayNaarObjectMapper($scope.selectedAllergenen) (we maken nu gebruik van het object, dus hoeven niet te mappen --> was slechte code)
        }).success(response => {
            getGerechten($scope);
            $scope.gerechtNaam = '';
            $scope.gerechtPrijs = '';
            $scope.selectedCategorie = '';
            //$scope.selectedAllergenen = ''; --> voor allergenen met selection dropdown
            uncheckCheckboxes($scope); //Voor allergenen met checkboxes
            $scope.gerecht.bestelbaar = 'Nee';
        });
    }

    ////////// ALLERGENEN ZONDER DROPDOWN, MAAR MET IMAGES, MET DROPDOWN IS ONDERSTAANDE CODE NIET NODIG //////////

    //Hulp functie om checkboxes unchecked te maken na submit
    function uncheckCheckboxes($scope) {
        angular.forEach($scope.allergenen, function(item, key) {
            if (item.selected == true) {
                item.selected = false;
            }
        });
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //----- NIET LANGER NODIG, FOUT WAS DAT ng-value="allergeen.naam" WAS EN ng-value="allergeen" MOET ZIJN ZODAT WE HET ECHTE OBJECT TERUGKRIJGEN EN NIET ENKEL DE NAAM ALS STRING -----

    //HULP METHOD OM ALLERGEEN OBJECTEN IN HET EMBEDDED DOCUMENT "ALLERGENEN" VAN HET DOCUMENT "GERECHT" TE PUSHEN
    //dit doen we omdat we van de select list in gerechtbeheer.html een array van strings (namen van allergenen) terugkrijgen, maar we een array van objecten moeten persisteren.
    //We mappen dus bv ["eieren", "lactose"] naar {naam: eieren, selected: true}, {naam: lactose, selected: true}
    /*
    function stringsArrayNaarObjectMapper(allergenenArray) {
        var objectenArray = [];
        //enkel de strings mappen, bestaande objecten niet meer (worden nu eigenlijk verwijderd door allergenen: null maar misschien toch goede extra
        if (allergenenArray.length > 0) {
            angular.forEach(allergenenArray, function(allergeen, index) {
                if (typeof allergeen === 'string' || allergeen instanceof String)
                    allergeen = { naam: allergeen, selected: true }
                objectenArray.push(allergeen);
            });
            return objectenArray;
        }
    }
*/
    //DEL
    function deleteGerecht($scope, gerecht) {
        $http.delete(GERECHTEN_API_ENDPOINT.url + `/${gerecht._id}`).success(response => {
            getGerechten($scope);
        });
    }
    //UPDATE
    function updateGerecht($scope, gerecht) {
            $http.put(GERECHTEN_API_ENDPOINT.url + `/${gerecht._id}`, {
                prijs: gerecht.updatedPrijs,
                naam: gerecht.updatedNaam,
                allergenen: gerecht.updatedAllergenen,
                bestelbaar: gerecht.updatedBestelbaar
            }).success(response => {
                getGerechten($scope);
                gerecht.isEditing = false;
            });
    }

    return {
        getAllergenen,
        getCategorieen,
        getGerechten,
        createAllergeen,
        createCategorie,
        createGerecht,
        deleteAllergeen,
        deleteCategorie,
        deleteGerecht,
        updateGerecht
    };

});

export default beheerFactory;
