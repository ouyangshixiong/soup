'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaDistrict', function ($resource, DateUtils) {
        return $resource('api/yaDistricts/:id', {}, {
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
