'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaProductDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaProduct', 'YaOrder', 'YaSubProduct', 'YaBanner',
        function($scope, $stateParams, $uibModalInstance, entity, YaProduct, YaOrder, YaSubProduct, YaBanner) {

        $scope.yaProduct = entity;
        $scope.yaorders = YaOrder.query();
        $scope.yasubproducts = YaSubProduct.query();
        $scope.yabanners = YaBanner.query();
        $scope.load = function(id) {
            YaProduct.get({id : id}, function(result) {
                $scope.yaProduct = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaProductUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaProduct.id != null) {
                YaProduct.update($scope.yaProduct, onSaveSuccess, onSaveError);
            } else {
                YaProduct.save($scope.yaProduct, onSaveSuccess, onSaveError);
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
