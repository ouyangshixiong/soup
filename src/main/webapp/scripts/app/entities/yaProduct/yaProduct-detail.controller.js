'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaProductDetailController', function ($scope, $rootScope, $stateParams, entity, YaProduct, YaOrder, YaSubProduct, YaBanner) {
        $scope.yaProduct = entity;
        $scope.load = function (id) {
            YaProduct.get({id: id}, function(result) {
                $scope.yaProduct = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaProductUpdate', function(event, result) {
            $scope.yaProduct = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
