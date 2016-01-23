'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaUserDetailController', function ($scope, $rootScope, $stateParams, entity, YaUser, YaPot, YaOrder, YaCouponOrder, YaAddress) {
        $scope.yaUser = entity;
        $scope.load = function (id) {
            YaUser.get({id: id}, function(result) {
                $scope.yaUser = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaUserUpdate', function(event, result) {
            $scope.yaUser = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
