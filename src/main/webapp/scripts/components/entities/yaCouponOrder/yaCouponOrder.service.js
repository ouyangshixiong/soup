'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaCouponOrder', function ($resource, DateUtils) {
        return $resource('api/yaCouponOrders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.couponOrderDate = DateUtils.convertLocaleDateFromServer(data.couponOrderDate);
                    data.couponExpireDate = DateUtils.convertLocaleDateFromServer(data.couponExpireDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.couponOrderDate = DateUtils.convertLocaleDateToServer(data.couponOrderDate);
                    data.couponExpireDate = DateUtils.convertLocaleDateToServer(data.couponExpireDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.couponOrderDate = DateUtils.convertLocaleDateToServer(data.couponOrderDate);
                    data.couponExpireDate = DateUtils.convertLocaleDateToServer(data.couponExpireDate);
                    return angular.toJson(data);
                }
            }
        });
    });
