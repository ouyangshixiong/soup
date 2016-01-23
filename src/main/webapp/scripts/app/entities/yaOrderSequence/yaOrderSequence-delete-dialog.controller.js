'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaOrderSequenceDeleteController', function($scope, $uibModalInstance, entity, YaOrderSequence) {

        $scope.yaOrderSequence = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaOrderSequence.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
