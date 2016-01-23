'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaBuildingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaBuilding', 'YaDistrict',
        function($scope, $stateParams, $uibModalInstance, entity, YaBuilding, YaDistrict) {

        $scope.yaBuilding = entity;
        $scope.yadistricts = YaDistrict.query();
        $scope.load = function(id) {
            YaBuilding.get({id : id}, function(result) {
                $scope.yaBuilding = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaBuildingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaBuilding.id != null) {
                YaBuilding.update($scope.yaBuilding, onSaveSuccess, onSaveError);
            } else {
                YaBuilding.save($scope.yaBuilding, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
