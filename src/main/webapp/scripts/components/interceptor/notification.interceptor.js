 'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-jhipsterLiquibaseBugApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-jhipsterLiquibaseBugApp-params')});
                }
                return response;
            }
        };
    });
