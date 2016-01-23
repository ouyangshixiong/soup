'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaPot', function ($resource, DateUtils) {
        return $resource('api/yaPots/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.potBuyDate = DateUtils.convertLocaleDateFromServer(data.potBuyDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.potBuyDate = DateUtils.convertLocaleDateToServer(data.potBuyDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.potBuyDate = DateUtils.convertLocaleDateToServer(data.potBuyDate);
                    return angular.toJson(data);
                }
            }
        });
    });
