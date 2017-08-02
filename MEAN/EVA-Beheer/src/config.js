import angular from 'angular';
import uiRouter from 'angular-ui-router';
import menuFactory from 'factories/menu-factory';
import menuController from 'controllers/menuController';
import authServiceEnFactory from 'auth/authService';
import beheerFactory from 'factories/beheer-factory';
import authControllers from 'auth/authControllers';
import beheerControllers from 'controllers/beheerControllers';
import bestellingController from 'controllers/bestellingController';
import bestellingFactory from 'factories/bestelling-factory';
import appConstants from 'auth/constants';
import uiBootstrap from 'angular-ui-bootstrap';
import ngAnimate from 'angular-animate';
import material from 'angular-material';

const app = angular.module('app', [ngAnimate, material, uiBootstrap, authServiceEnFactory.name, authControllers.name,
    beheerFactory.name, beheerControllers.name, appConstants.name, uiRouter, menuController.name, menuFactory.name, bestellingController.name, bestellingFactory.name
]);

app.config(($stateProvider, $urlRouterProvider, $locationProvider, $mdDateLocaleProvider) => {

//DATUM IN <md-datepicker> WEERGEVEN ALS DD/MMMM/YYYY I.P.V. DE DEFAULT MM/DD/YYYY
  $mdDateLocaleProvider.formatDate = function(date) {
    if(date !== null && date != undefined) {
      if(date.getMonthName == undefined) {
        date.getMonthName = function() {
          var monthNames = [ "Januari", "Februari", "Maart", "April", "Mei", "Juni",
          "Juli", "Augustus", "September", "Oktober", "November", "December" ];
          return monthNames[this.getMonth()];
        }
      }
      var day = date.getDate();
      var monthIndex = date.getMonth();
      var year = date.getFullYear();
      return day + ' ' + date.getMonthName() + ' ' + year;
    }else if(date === undefined || date === null){
      //ALS DATE NULL GEZET IS DOOR eraseFilter OF UNDEFINED IS, RETURN NULL AAN DE DATEPICKER ZODAT JE GEEN UNDEFINED KRIJGT
      return null;
    }
  };

    $urlRouterProvider.otherwise('/menu');

    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });

    $stateProvider
        .state('login', {
            url: '/login',
            template: require('auth/login/login.html'),
            controller: 'LoginCtrl',
        })
        .state('menu', {
            url: '/menu',
            template: require('menu/menu.html'),
            controller: 'menuController'
        })
        .state('allergenen', {
            url: '/allergenen',
            template: require('extraBeheer/allergenen.html'),
            controller: 'allergeenController'
        })
        .state('categorieen', {
            url: '/categorieen',
            template: require('extraBeheer/categorieen.html'),
            controller: 'categorieController'
        })
        .state('gerechten', {
            url: '/gerechten',
            template: require('extraBeheer/gerechtBeheer.html'),
            controller: 'gerechtController'
        })
        .state('bestellingen', {
            url: '/bestellingen',
            template: require('bestellingen/bestellingen.html'),
            controller: 'bestellingController'
        });

    app.run(function($rootScope, $state, AuthService, AUTH_EVENTS) {
        $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
          if(!authService.isAuthenticated()) {
                if (toState.name !== 'login') {
                    event.preventDefault();
                    $state.go('login');
                }
            }
        });
    });

});

export default app;
