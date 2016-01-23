'use strict';

angular.module('jhipsterLiquibaseBugApp').controller('YaQuestionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'YaQuestion',
        function($scope, $stateParams, $uibModalInstance, entity, YaQuestion) {

        $scope.yaQuestion = entity;
        $scope.load = function(id) {
            YaQuestion.get({id : id}, function(result) {
                $scope.yaQuestion = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterLiquibaseBugApp:yaQuestionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yaQuestion.id != null) {
                YaQuestion.update($scope.yaQuestion, onSaveSuccess, onSaveError);
            } else {
                YaQuestion.save($scope.yaQuestion, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
