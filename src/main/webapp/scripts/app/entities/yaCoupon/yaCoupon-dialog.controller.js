'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaCouponDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaCoupon', 'YaCouponOrder', 'YaBanner',
        function($scope, $stateParams, $uibModalInstance, entity, YaCoupon, YaCouponOrder, YaBanner) {

        $scope.yaCoupon = entity;
        $scope.yacouponorders = YaCouponOrder.query();
        $scope.yabanners = YaBanner.query();
        $scope.load = function(id) {
            YaCoupon.get({id : id}, function(result) {
                $scope.yaCoupon = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaCouponUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaCoupon.id != null) {
                YaCoupon.update($scope.yaCoupon, onSaveSuccess, onSaveError);
            } else {
                YaCoupon.save($scope.yaCoupon, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForOnShelveDate = {};

        $scope.datePickerForOnShelveDate.status = {
            opened: false
        };

        $scope.datePickerForOnShelveDateOpen = function($event) {
            $scope.datePickerForOnShelveDate.status.opened = true;
        };
        $scope.datePickerForOffShelveDate = {};

        $scope.datePickerForOffShelveDate.status = {
            opened: false
        };

        $scope.datePickerForOffShelveDateOpen = function($event) {
            $scope.datePickerForOffShelveDate.status.opened = true;
        };
}]);
