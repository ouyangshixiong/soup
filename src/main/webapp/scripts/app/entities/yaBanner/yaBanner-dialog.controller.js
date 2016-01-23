'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaBannerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaBanner', 'YaProduct', 'YaCoupon',
        function($scope, $stateParams, $uibModalInstance, entity, YaBanner, YaProduct, YaCoupon) {

        $scope.yaBanner = entity;
        $scope.yaproducts = YaProduct.query();
        $scope.yacoupons = YaCoupon.query();
        $scope.load = function(id) {
            YaBanner.get({id : id}, function(result) {
                $scope.yaBanner = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaBannerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaBanner.id != null) {
                YaBanner.update($scope.yaBanner, onSaveSuccess, onSaveError);
            } else {
                YaBanner.save($scope.yaBanner, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForUpdateDate = {};

        $scope.datePickerForUpdateDate.status = {
            opened: false
        };

        $scope.datePickerForUpdateDateOpen = function($event) {
            $scope.datePickerForUpdateDate.status.opened = true;
        };
}]);
