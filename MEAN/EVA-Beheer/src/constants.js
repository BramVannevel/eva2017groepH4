import angular from 'angular';
const serverUrl = 'http://127.0.0.1:8080'; //voor Development
//const serverUrl = 'https://evabeheer.herokuapp.com'; // voor Productie

const appConstants = angular.module('app.appConstants', [])


.constant('AUTH_EVENTS', {
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
})

.constant('USER_API_ENDPOINT', {
    url: serverUrl + '/user'
})

.constant('RESTAURANTS_API_ENDPOINT', {
    url: serverUrl + '/restaurants'
})

.constant('CATEGORIEEN_API_ENDPOINT', {
        url: serverUrl + '/categorieen'
})

.constant('GERECHTEN_API_ENDPOINT', {
        url: serverUrl + '/gerechten'
})

.constant('CHALLENGES_API_ENDPOINT',{
  url: serverUrl + '/challenges'
});

export default appConstants;
