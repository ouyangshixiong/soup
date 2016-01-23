'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaOrder', function ($resource, DateUtils) {
        return $resource('api/yaOrders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.orderDate = DateUtils.convertLocaleDateFromServer(data.orderDate);
                    data.deliveryDate = DateUtils.convertLocaleDateFromServer(data.deliveryDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.orderDate = DateUtils.convertLocaleDateToServer(data.orderDate);
                    data.deliveryDate = DateUtils.convertLocaleDateToServer(data.deliveryDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.orderDate = DateUtils.convertLocaleDateToServer(data.orderDate);
                    data.deliveryDate = DateUtils.convertLocaleDateToServer(data.deliveryDate);
                    return angular.toJson(data);
                }
            }
        });
    });
