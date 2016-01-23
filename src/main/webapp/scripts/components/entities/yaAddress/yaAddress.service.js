'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaAddress', function ($resource, DateUtils) {
        return $resource('api/yaAddresss/:id', {}, {
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
