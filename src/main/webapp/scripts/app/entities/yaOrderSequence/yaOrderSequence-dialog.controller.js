'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaOrderSequenceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaOrderSequence',
        function($scope, $stateParams, $uibModalInstance, entity, YaOrderSequence) {

        $scope.yaOrderSequence = entity;
        $scope.load = function(id) {
            YaOrderSequence.get({id : id}, function(result) {
                $scope.yaOrderSequence = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaOrderSequenceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaOrderSequence.id != null) {
                YaOrderSequence.update($scope.yaOrderSequence, onSaveSuccess, onSaveError);
            } else {
                YaOrderSequence.save($scope.yaOrderSequence, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
