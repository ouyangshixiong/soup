'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaPotDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaPot', 'YaUser', 'YaOrder',
        function($scope, $stateParams, $uibModalInstance, entity, YaPot, YaUser, YaOrder) {

        $scope.yaPot = entity;
        $scope.yausers = YaUser.query();
        $scope.yaorders = YaOrder.query();
        $scope.load = function(id) {
            YaPot.get({id : id}, function(result) {
                $scope.yaPot = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaPotUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaPot.id != null) {
                YaPot.update($scope.yaPot, onSaveSuccess, onSaveError);
            } else {
                YaPot.save($scope.yaPot, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForPotBuyDate = {};

        $scope.datePickerForPotBuyDate.status = {
            opened: false
        };

        $scope.datePickerForPotBuyDateOpen = function($event) {
            $scope.datePickerForPotBuyDate.status.opened = true;
        };
}]);
