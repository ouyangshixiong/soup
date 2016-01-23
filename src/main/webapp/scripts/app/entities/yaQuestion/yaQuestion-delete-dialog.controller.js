'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaQuestionDeleteController', function($scope, $uibModalInstance, entity, YaQuestion) {

        $scope.yaQuestion = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaQuestion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
