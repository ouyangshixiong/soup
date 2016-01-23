'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaAddressDetailController', function ($scope, $rootScope, $stateParams, entity, YaAddress, YaUser) {
        $scope.yaAddress = entity;
        $scope.load = function (id) {
            YaAddress.get({id: id}, function(result) {
                $scope.yaAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaAddressUpdate', function(event, result) {
            $scope.yaAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
