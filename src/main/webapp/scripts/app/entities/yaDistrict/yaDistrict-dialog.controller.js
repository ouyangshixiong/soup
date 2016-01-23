'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaDistrictDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaDistrict', 'YaBuilding',
        function($scope, $stateParams, $uibModalInstance, entity, YaDistrict, YaBuilding) {

        $scope.yaDistrict = entity;
        $scope.yabuildings = YaBuilding.query();
        $scope.load = function(id) {
            YaDistrict.get({id : id}, function(result) {
                $scope.yaDistrict = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaDistrictUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaDistrict.id != null) {
                YaDistrict.update($scope.yaDistrict, onSaveSuccess, onSaveError);
            } else {
                YaDistrict.save($scope.yaDistrict, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
