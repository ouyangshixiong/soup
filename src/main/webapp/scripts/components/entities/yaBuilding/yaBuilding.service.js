'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaBuilding', function ($resource, DateUtils) {
        return $resource('api/yaBuildings/:id', {}, {
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
