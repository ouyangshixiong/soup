'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaBuildingDeleteController', function($scope, $uibModalInstance, entity, YaBuilding) {

        $scope.yaBuilding = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaBuilding.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
