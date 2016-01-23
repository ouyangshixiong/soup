'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaPotDeleteController', function($scope, $uibModalInstance, entity, YaPot) {

        $scope.yaPot = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaPot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
