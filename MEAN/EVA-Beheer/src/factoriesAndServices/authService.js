import angular from 'angular';

const authServiceEnFactory = angular.module('app.authServiceEnFactory', [])

.service('AuthService', function($q, $http, USER_API_ENDPOINT) {
    var LOCAL_TOKEN_KEY = 'yourTokenKey';
    var isAuthenticated = false;
    var authToken;

    function loadUserCredentials() {
        var token = window.localStorage.getItem(LOCAL_TOKEN_KEY);
        if (token) {
            useCredentials(token);
        }
    }

    function storeUserCredentials(token) {
        window.localStorage.setItem(LOCAL_TOKEN_KEY, token);
        useCredentials(token);
    }

    function useCredentials(token) {
        //Ingelogd blijven on page refresh
        isAuthenticated = true;
        authToken = token;

        //Instellen dat er default een header met het token wordt meegegeven bij HTTP calls uit angular.
        $http.defaults.headers.common.Authorization = authToken;
    }

    function destroyUserCredentials() {
        authToken = undefined;
        isAuthenticated = false;
        $http.defaults.headers.common.Authorization = undefined;
        window.localStorage.removeItem(LOCAL_TOKEN_KEY);
    }

    var register = function(user) {
        return $q(function(resolve, reject) {
            $http.post(USER_API_ENDPOINT.url + '/signup', user).then(function(result) {
                if (result.data.success) {
                    resolve(result.data.msg);
                } else {
                    reject(result.data.msg);
                }
            });
        });
    };

    var login = function(user) {
        return $q(function(resolve, reject) {
            $http.post(USER_API_ENDPOINT.url + '/authenticate', user).then(function(result) {
                if (result.data.success) {
                  //Store token first so we can retrieve the user's info
                  storeUserCredentials(result.data.token);
                  resolve(result.data.msg);

                  //Check if admin and decide if user can get into the application
                  getUserInfo().then(function(responseUser) {
                      console.log('The user trying to access the webapp is: ' + responseUser.name + ' with role: ' + responseUser.role);
                    if(responseUser.role === 'admin') {
                      //isAuthenticated true zetten hier zodat nu pas doorgegaan wordt naar de applicatie.
                      isAuthenticated = true;
                    } else {
                      isAuthenticated = false;
                      destroyUserCredentials();
                    }
                  });
                } else {
                    reject(result.data.msg);
                }
            });
        });
    };

    //returns the user, allows us to check for the role in config.js
    var getUserInfo = function() {
        return $q(function(resolve, reject) {
          $http.get(USER_API_ENDPOINT.url + '/userinfo').then(function(response) {
            if(response.data.success) {
                return resolve(response.data.user);
            } else {
              reject(result.data.msg);
            }
          });
        });
    };

    var logout = function() {
        destroyUserCredentials();
    };

    loadUserCredentials();

    return {
        login: login,
        register: register,
        logout: logout,
        isAuthenticated: function() { return isAuthenticated; },
        getUserInfo: getUserInfo
    };
})

.factory('AuthInterceptor', function($rootScope, $q, AUTH_EVENTS) {
    return {
        responseError: function(response) {
            $rootScope.$broadcast({
                401: AUTH_EVENTS.notAuthenticated,
                403: AUTH_EVENTS.notAuthorized
            }[response.status], response);
            return $q.reject(response);
        }
    };
})

.config(function($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
});

export default authServiceEnFactory;
