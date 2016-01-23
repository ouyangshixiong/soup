'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaUserController', function ($scope, $state, YaUser) {

        $scope.yaUsers = [];
        $scope.loadAll = function() {
            YaUser.query(function(result) {
               $scope.yaUsers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaUser = {
                name: null,
                mobileNumber: null,
                facebook: null,
                email: null,
                password: null,
                gender: null,
                loginStatus: null,
                hasPrivatePot: null,
                authToken: null,
                authTokenUpdateTime: null,
                smUsedCount: null,
                smLastCheckDate: null,
                smLastContent: null,
                smExpireTime: null,
                id: null
            };
        };
    });
