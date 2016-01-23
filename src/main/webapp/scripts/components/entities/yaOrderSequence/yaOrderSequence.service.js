'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaOrderSequence', function ($resource, DateUtils) {
        return $resource('api/yaOrderSequences/:id', {}, {
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
