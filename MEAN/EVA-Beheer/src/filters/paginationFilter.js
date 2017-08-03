import angular from 'angular';

const paginationFilter = angular.module('app.paginationFilter', [])

//To be called in the ng-repeat statement in the html
.filter('paginationFilter', ['$filter', function($filter) {
    return function(data, start) {
      return data.slice(start);
    };
}]);

export default paginationFilter;
