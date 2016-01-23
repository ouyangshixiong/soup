'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaBannerController', function ($scope, $state, YaBanner) {

        $scope.yaBanners = [];
        $scope.loadAll = function() {
            YaBanner.query(function(result) {
               $scope.yaBanners = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaBanner = {
                bannerPictures: null,
                bannerName: null,
                updateDate: null,
                id: null
            };
        };
    });
