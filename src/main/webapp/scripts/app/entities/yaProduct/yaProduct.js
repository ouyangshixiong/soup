'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaProduct', {
                parent: 'entity',
                url: '/yaProducts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaProducts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaProduct/yaProducts.html',
                        controller: 'YaProductController'
                    }
                },
                resolve: {
                }
            })
            .state('yaProduct.detail', {
                parent: 'entity',
                url: '/yaProduct/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaProduct'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaProduct/yaProduct-detail.html',
                        controller: 'YaProductDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaProduct', function($stateParams, YaProduct) {
                        return YaProduct.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaProduct.new', {
                parent: 'yaProduct',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaProduct/yaProduct-dialog.html',
                        controller: 'YaProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    productName: null,
                                    productPictures: null,
                                    orginalPrice: null,
                                    discountPrice: null,
                                    membershipPrice: null,
                                    isSerial: null,
                                    keywords: null,
                                    productDiscription: null,
                                    productAmount: null,
                                    productInventory: null,
                                    onShelveDate: null,
                                    offShelveDate: null,
                                    productStatus: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaProduct', null, { reload: true });
                    }, function() {
                        $state.go('yaProduct');
                    })
                }]
            })
            .state('yaProduct.edit', {
                parent: 'yaProduct',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaProduct/yaProduct-dialog.html',
                        controller: 'YaProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaProduct', function(YaProduct) {
                                return YaProduct.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaProduct', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaProduct.delete', {
                parent: 'yaProduct',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaProduct/yaProduct-delete-dialog.html',
                        controller: 'YaProductDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaProduct', function(YaProduct) {
                                return YaProduct.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaProduct', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
