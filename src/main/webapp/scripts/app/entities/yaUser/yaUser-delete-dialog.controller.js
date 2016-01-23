'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaUserDeleteController', function($scope, $uibModalInstance, entity, YaUser) {

        $scope.yaUser = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
