'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


