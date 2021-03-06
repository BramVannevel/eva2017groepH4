import angular from 'angular';
import uiRouter from 'angular-ui-router';
import restaurantsFactory from 'factoriesAndServices/restaurants-factory';
import restaurantsController from 'controllers/restaurantsController';
import authServiceEnFactory from 'factoriesAndServices/authService';
import beheerFactory from 'factoriesAndServices/beheer-factory';
import authControllers from 'controllers/authControllers';
import beheerControllers from 'controllers/beheerControllers';
import challengeController from 'controllers/challengeController';
import challengeFactory from 'factoriesAndServices/challenge-factory';
import paginationFilter from 'filters/paginationFilter';
import restaurantModalController from 'controllers/RestaurantModalController';
import restaurantDetailModalController from 'controllers/restaurantDetailModalController';
import gerechtModalController from 'controllers/gerechtModalController';
import gerechtDetailModalController from 'controllers/gerechtDetailModalController';
import categorieModalController from 'controllers/categorieModalController';
import challengeModalController from 'controllers/challengeModalController';
import challengeDetailModalController from 'controllers/challengeDetailModalController';
import appConstants from 'constants';
import uiBootstrap from 'angular-ui-bootstrap';
import ngAnimate from 'angular-animate';
import material from 'angular-material';

const app = angular.module('app', [ngAnimate, material, uiBootstrap, authServiceEnFactory.name, authControllers.name,
    beheerFactory.name, beheerControllers.name, appConstants.name, uiRouter, restaurantsController.name, restaurantsFactory.name, challengeController.name, challengeFactory.name,
    paginationFilter.name, restaurantModalController.name, restaurantDetailModalController.name, gerechtModalController.name, categorieModalController.name, gerechtDetailModalController.name,
    challengeModalController.name, challengeDetailModalController.name
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

    $urlRouterProvider.otherwise('/restaurants');

    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });

    $stateProvider
        .state('login', {
            url: '/login',
            template: require('pages/login.html'),
            controller: 'LoginCtrl',
        })
        .state('restaurants', {
            url: '/restaurants',
            template: require('pages/restaurants.html'),
            controller: 'restaurantsController'
        })
        .state('allergenen', {
            url: '/allergenen',
            template: require('pages/allergenen.html'),
            controller: 'allergeenController'
        })
        .state('categorieen', {
            url: '/categorieen',
            template: require('pages/categorieen.html'),
            controller: 'categorieController'
        })
        .state('gerechten', {
            url: '/gerechten',
            template: require('pages/gerechtBeheer.html'),
            controller: 'gerechtController'
        })
        .state('challenges', {
            url: '/challenges',
            template: require('pages/challenges.html'),
            controller: 'challengeController'
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
