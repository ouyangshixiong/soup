'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaOrderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'YaOrder', 'YaUser', 'YaAddress', 'YaPot', 'YaCouponOrder', 'YaProduct',
        function($scope, $stateParams, $uibModalInstance, $q, entity, YaOrder, YaUser, YaAddress, YaPot, YaCouponOrder, YaProduct) {

        $scope.yaOrder = entity;
        $scope.yausers = YaUser.query();
        $scope.yaaddresss = YaAddress.query({filter: 'yaorder-is-null'});
        $q.all([$scope.yaOrder.$promise, $scope.yaaddresss.$promise]).then(function() {
            if (!$scope.yaOrder.yaAddress || !$scope.yaOrder.yaAddress.id) {
                return $q.reject();
            }
            return YaAddress.get({id : $scope.yaOrder.yaAddress.id}).$promise;
        }).then(function(yaAddress) {
            $scope.yaaddresss.push(yaAddress);
        });
        $scope.yapots = YaPot.query();
        $scope.yacouponorders = YaCouponOrder.query({filter: 'yaorder-is-null'});
        $q.all([$scope.yaOrder.$promise, $scope.yacouponorders.$promise]).then(function() {
            if (!$scope.yaOrder.yaCouponOrder || !$scope.yaOrder.yaCouponOrder.id) {
                return $q.reject();
            }
            return YaCouponOrder.get({id : $scope.yaOrder.yaCouponOrder.id}).$promise;
        }).then(function(yaCouponOrder) {
            $scope.yacouponorders.push(yaCouponOrder);
        });
        $scope.yaproducts = YaProduct.query();
        $scope.load = function(id) {
            YaOrder.get({id : id}, function(result) {
                $scope.yaOrder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaOrderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaOrder.id != null) {
                YaOrder.update($scope.yaOrder, onSaveSuccess, onSaveError);
            } else {
                YaOrder.save($scope.yaOrder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForOrderDate = {};

        $scope.datePickerForOrderDate.status = {
            opened: false
        };

        $scope.datePickerForOrderDateOpen = function($event) {
            $scope.datePickerForOrderDate.status.opened = true;
        };
        $scope.datePickerForDeliveryDate = {};

        $scope.datePickerForDeliveryDate.status = {
            opened: false
        };

        $scope.datePickerForDeliveryDateOpen = function($event) {
            $scope.datePickerForDeliveryDate.status.opened = true;
        };
}]);
