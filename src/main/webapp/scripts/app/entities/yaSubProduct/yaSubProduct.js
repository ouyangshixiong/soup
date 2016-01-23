'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaSubProduct', {
                parent: 'entity',
                url: '/yaSubProducts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaSubProducts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaSubProduct/yaSubProducts.html',
                        controller: 'YaSubProductController'
                    }
                },
                resolve: {
                }
            })
            .state('yaSubProduct.detail', {
                parent: 'entity',
                url: '/yaSubProduct/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaSubProduct'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaSubProduct/yaSubProduct-detail.html',
                        controller: 'YaSubProductDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaSubProduct', function($stateParams, YaSubProduct) {
                        return YaSubProduct.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaSubProduct.new', {
                parent: 'yaSubProduct',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaSubProduct/yaSubProduct-dialog.html',
                        controller: 'YaSubProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    serialProductId: null,
                                    capacity: null,
                                    price: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaSubProduct', null, { reload: true });
                    }, function() {
                        $state.go('yaSubProduct');
                    })
                }]
            })
            .state('yaSubProduct.edit', {
                parent: 'yaSubProduct',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaSubProduct/yaSubProduct-dialog.html',
                        controller: 'YaSubProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaSubProduct', function(YaSubProduct) {
                                return YaSubProduct.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaSubProduct', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaSubProduct.delete', {
                parent: 'yaSubProduct',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaSubProduct/yaSubProduct-delete-dialog.html',
                        controller: 'YaSubProductDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaSubProduct', function(YaSubProduct) {
                                return YaSubProduct.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaSubProduct', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
