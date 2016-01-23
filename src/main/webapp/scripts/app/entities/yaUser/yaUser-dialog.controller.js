'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaUserDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaUser', 'YaPot', 'YaOrder', 'YaCouponOrder', 'YaAddress',
        function($scope, $stateParams, $uibModalInstance, entity, YaUser, YaPot, YaOrder, YaCouponOrder, YaAddress) {

        $scope.yaUser = entity;
        $scope.yapots = YaPot.query();
        $scope.yaorders = YaOrder.query();
        $scope.yacouponorders = YaCouponOrder.query();
        $scope.yaaddresss = YaAddress.query();
        $scope.load = function(id) {
            YaUser.get({id : id}, function(result) {
                $scope.yaUser = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaUserUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaUser.id != null) {
                YaUser.update($scope.yaUser, onSaveSuccess, onSaveError);
            } else {
                YaUser.save($scope.yaUser, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForAuthTokenUpdateTime = {};

        $scope.datePickerForAuthTokenUpdateTime.status = {
            opened: false
        };

        $scope.datePickerForAuthTokenUpdateTimeOpen = function($event) {
            $scope.datePickerForAuthTokenUpdateTime.status.opened = true;
        };
        $scope.datePickerForSmLastCheckDate = {};

        $scope.datePickerForSmLastCheckDate.status = {
            opened: false
        };

        $scope.datePickerForSmLastCheckDateOpen = function($event) {
            $scope.datePickerForSmLastCheckDate.status.opened = true;
        };
        $scope.datePickerForSmExpireTime = {};

        $scope.datePickerForSmExpireTime.status = {
            opened: false
        };

        $scope.datePickerForSmExpireTimeOpen = function($event) {
            $scope.datePickerForSmExpireTime.status.opened = true;
        };
}]);
