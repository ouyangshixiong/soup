'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaBanner', {
                parent: 'entity',
                url: '/yaBanners',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaBanners'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaBanner/yaBanners.html',
                        controller: 'YaBannerController'
                    }
                },
                resolve: {
                }
            })
            .state('yaBanner.detail', {
                parent: 'entity',
                url: '/yaBanner/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaBanner'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaBanner/yaBanner-detail.html',
                        controller: 'YaBannerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaBanner', function($stateParams, YaBanner) {
                        return YaBanner.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaBanner.new', {
                parent: 'yaBanner',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaBanner/yaBanner-dialog.html',
                        controller: 'YaBannerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    bannerPictures: null,
                                    bannerName: null,
                                    updateDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaBanner', null, { reload: true });
                    }, function() {
                        $state.go('yaBanner');
                    })
                }]
            })
            .state('yaBanner.edit', {
                parent: 'yaBanner',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaBanner/yaBanner-dialog.html',
                        controller: 'YaBannerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaBanner', function(YaBanner) {
                                return YaBanner.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaBanner', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaBanner.delete', {
                parent: 'yaBanner',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaBanner/yaBanner-delete-dialog.html',
                        controller: 'YaBannerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaBanner', function(YaBanner) {
                                return YaBanner.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaBanner', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
