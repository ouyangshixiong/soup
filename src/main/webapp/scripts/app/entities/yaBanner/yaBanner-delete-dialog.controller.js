'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaBannerDeleteController', function($scope, $uibModalInstance, entity, YaBanner) {

        $scope.yaBanner = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaBanner.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
