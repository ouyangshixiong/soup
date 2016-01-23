'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaAddressDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaAddress', 'YaUser',
        function($scope, $stateParams, $uibModalInstance, entity, YaAddress, YaUser) {

        $scope.yaAddress = entity;
        $scope.yausers = YaUser.query();
        $scope.load = function(id) {
            YaAddress.get({id : id}, function(result) {
                $scope.yaAddress = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaAddressUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaAddress.id != null) {
                YaAddress.update($scope.yaAddress, onSaveSuccess, onSaveError);
            } else {
                YaAddress.save($scope.yaAddress, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
