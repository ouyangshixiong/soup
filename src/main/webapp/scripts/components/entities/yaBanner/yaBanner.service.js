'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaBanner', function ($resource, DateUtils) {
        return $resource('api/yaBanners/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
