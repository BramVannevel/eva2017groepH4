import angular from 'angular';
const serverUrl = 'http://127.0.0.1:8080'; //voor Development
//const serverUrl = 'https://restobeheerapp.herokuapp.com'; // voor Productie

const appConstants = angular.module('app.appConstants', [])


.constant('AUTH_EVENTS', {
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
})

.constant('USER_API_ENDPOINT', {
    url: serverUrl + '/user'
})

.constant('MENU_API_ENDPOINT', {
    url: serverUrl + '/menu'
})

.constant('ALLERGENEN_API_ENDPOINT', {
    url: serverUrl + '/allergenen'
})

.constant('CATEGORIEEN_API_ENDPOINT', {
        url: serverUrl + '/categorieen'
})

.constant('GERECHTEN_API_ENDPOINT', {
        url: serverUrl + '/gerechten'
})

.constant('BESTELLINGEN_API_ENDPOINT',{
  url: serverUrl + '/bestellingen'
});

export default appConstants;
