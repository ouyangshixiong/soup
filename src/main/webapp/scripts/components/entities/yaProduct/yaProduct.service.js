'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaProduct', function ($resource, DateUtils) {
        return $resource('api/yaProducts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.onShelveDate = DateUtils.convertLocaleDateFromServer(data.onShelveDate);
                    data.offShelveDate = DateUtils.convertLocaleDateFromServer(data.offShelveDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.onShelveDate = DateUtils.convertLocaleDateToServer(data.onShelveDate);
                    data.offShelveDate = DateUtils.convertLocaleDateToServer(data.offShelveDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.onShelveDate = DateUtils.convertLocaleDateToServer(data.onShelveDate);
                    data.offShelveDate = DateUtils.convertLocaleDateToServer(data.offShelveDate);
                    return angular.toJson(data);
                }
            }
        });
    });
