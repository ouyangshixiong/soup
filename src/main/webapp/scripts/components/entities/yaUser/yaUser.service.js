'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('YaUser', function ($resource, DateUtils) {
        return $resource('api/yaUsers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.authTokenUpdateTime = DateUtils.convertLocaleDateFromServer(data.authTokenUpdateTime);
                    data.smLastCheckDate = DateUtils.convertLocaleDateFromServer(data.smLastCheckDate);
                    data.smExpireTime = DateUtils.convertLocaleDateFromServer(data.smExpireTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.authTokenUpdateTime = DateUtils.convertLocaleDateToServer(data.authTokenUpdateTime);
                    data.smLastCheckDate = DateUtils.convertLocaleDateToServer(data.smLastCheckDate);
                    data.smExpireTime = DateUtils.convertLocaleDateToServer(data.smExpireTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.authTokenUpdateTime = DateUtils.convertLocaleDateToServer(data.authTokenUpdateTime);
                    data.smLastCheckDate = DateUtils.convertLocaleDateToServer(data.smLastCheckDate);
                    data.smExpireTime = DateUtils.convertLocaleDateToServer(data.smExpireTime);
                    return angular.toJson(data);
                }
            }
        });
    });
