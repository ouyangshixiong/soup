'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaCouponOrderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaCouponOrder', 'YaUser', 'YaCoupon',
        function($scope, $stateParams, $uibModalInstance, entity, YaCouponOrder, YaUser, YaCoupon) {

        $scope.yaCouponOrder = entity;
        $scope.yausers = YaUser.query();
        $scope.yacoupons = YaCoupon.query();
        $scope.load = function(id) {
            YaCouponOrder.get({id : id}, function(result) {
                $scope.yaCouponOrder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaCouponOrderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaCouponOrder.id != null) {
                YaCouponOrder.update($scope.yaCouponOrder, onSaveSuccess, onSaveError);
            } else {
                YaCouponOrder.save($scope.yaCouponOrder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCouponOrderDate = {};

        $scope.datePickerForCouponOrderDate.status = {
            opened: false
        };

        $scope.datePickerForCouponOrderDateOpen = function($event) {
            $scope.datePickerForCouponOrderDate.status.opened = true;
        };
        $scope.datePickerForCouponExpireDate = {};

        $scope.datePickerForCouponExpireDate.status = {
            opened: false
        };

        $scope.datePickerForCouponExpireDateOpen = function($event) {
            $scope.datePickerForCouponExpireDate.status.opened = true;
        };
}]);
