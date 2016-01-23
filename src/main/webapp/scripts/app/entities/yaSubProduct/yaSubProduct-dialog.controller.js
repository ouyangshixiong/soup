'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaSubProductDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaSubProduct', 'YaProduct',
        function($scope, $stateParams, $uibModalInstance, entity, YaSubProduct, YaProduct) {

        $scope.yaSubProduct = entity;
        $scope.yaproducts = YaProduct.query();
        $scope.load = function(id) {
            YaSubProduct.get({id : id}, function(result) {
                $scope.yaSubProduct = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaSubProductUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaSubProduct.id != null) {
                YaSubProduct.update($scope.yaSubProduct, onSaveSuccess, onSaveError);
            } else {
                YaSubProduct.save($scope.yaSubProduct, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
