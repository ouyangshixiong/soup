'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaPot', {
                parent: 'entity',
                url: '/yaPots',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaPots'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaPot/yaPots.html',
                        controller: 'YaPotController'
                    }
                },
                resolve: {
                }
            })
            .state('yaPot.detail', {
                parent: 'entity',
                url: '/yaPot/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaPot'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaPot/yaPot-detail.html',
                        controller: 'YaPotDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaPot', function($stateParams, YaPot) {
                        return YaPot.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaPot.new', {
                parent: 'yaPot',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaPot/yaPot-dialog.html',
                        controller: 'YaPotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    isPrivatePot: false,
                                    potQrcode: null,
                                    potLabel: null,
                                    potStatus: null,
                                    potColor: null,
                                    potCapacity: null,
                                    potBuyDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaPot', null, { reload: true });
                    }, function() {
                        $state.go('yaPot');
                    })
                }]
            })
            .state('yaPot.edit', {
                parent: 'yaPot',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaPot/yaPot-dialog.html',
                        controller: 'YaPotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaPot', function(YaPot) {
                                return YaPot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaPot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaPot.delete', {
                parent: 'yaPot',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaPot/yaPot-delete-dialog.html',
                        controller: 'YaPotDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaPot', function(YaPot) {
                                return YaPot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaPot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
