'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaBannerDetailController', function ($scope, $rootScope, $stateParams, entity, YaBanner, YaProduct, YaCoupon) {
        $scope.yaBanner = entity;
        $scope.load = function (id) {
            YaBanner.get({id: id}, function(result) {
                $scope.yaBanner = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaBannerUpdate', function(event, result) {
            $scope.yaBanner = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
