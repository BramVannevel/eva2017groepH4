import angular from 'angular';

const authControllers = angular.module('app.authControllers', [])

.controller('LoginCtrl', function($scope, AuthService, $state, $location) {
    $scope.user = {
        name: '',
        password: ''
    };
    $scope.login = function() {
        AuthService.login($scope.user).then(function(msg) {
          // CHECK VOOR ADMIN ROLE
          AuthService.getUserInfo($scope).then(function(responseUser) {
            $scope.responseUser = responseUser;
            console.log('The user trying to access the webapp is: ' + responseUser);
              if(responseUser.role === 'admin') {
                  $state.go('menu');
              } else {
                console.log({
                  title: 'You are not an admin'
                });
              }
          });
        }, function(errMsg) {
            console.log({
                title: 'Login failed!',
                template: errMsg
            });
        });
    };
})

.controller('RegisterCtrl', function($scope, AuthService, $state) {
    $scope.user = {
        name: '',
        password: ''
    };

    $scope.signup = function() {
        AuthService.register($scope.user).then(function(msg) {
            $state.go('login');
            console.log({
                title: 'Register success!',
                template: msg
            });
        }, function(errMsg) {
            console.log({
                title: 'Register failed!',
                template: errMsg
            });
        });
    };
})

.controller('NavController', function($scope, $location, AuthService, $state, USER_API_ENDPOINT) {
    $scope.AuthenticationService = AuthService;

    $scope.isActive = function(viewLocation) {
        return viewLocation === $location.path();
    };
    $scope.destroySession = function() {
        AuthService.logout();
    };

    $scope.logout = function() {
        AuthService.logout();
        $state.go('login');
    };
})

.controller('AppCtrl', function($scope, $state, AuthService, AUTH_EVENTS) {
    $scope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
        AuthService.logout();
        $state.go('login');
        console.log({
            title: 'Session Lost!',
            template: 'Sorry, You have to login again.'
        });
    });
    $scope.$on(AUTH_EVENTS.notAuthorized, function(event) {
      AuthService.logout();
      $state.go('login');
      console.log({
          title: 'Not authorized!',
          template: 'Unfortunately you have to be an admin to access this place.'
      });
    });
});

export default authControllers;
