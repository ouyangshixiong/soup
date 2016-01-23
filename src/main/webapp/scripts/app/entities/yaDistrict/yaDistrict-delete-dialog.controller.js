'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaDistrictDeleteController', function($scope, $uibModalInstance, entity, YaDistrict) {

        $scope.yaDistrict = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaDistrict.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
