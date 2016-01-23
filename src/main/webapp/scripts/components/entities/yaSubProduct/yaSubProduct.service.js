'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaSubProduct', function ($resource, DateUtils) {
        return $resource('api/yaSubProducts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
