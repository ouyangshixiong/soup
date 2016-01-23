'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaSubProductDetailController', function ($scope, $rootScope, $stateParams, entity, YaSubProduct, YaProduct) {
        $scope.yaSubProduct = entity;
        $scope.load = function (id) {
            YaSubProduct.get({id: id}, function(result) {
                $scope.yaSubProduct = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaSubProductUpdate', function(event, result) {
            $scope.yaSubProduct = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
