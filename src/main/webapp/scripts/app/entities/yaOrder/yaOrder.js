'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaOrder', {
                parent: 'entity',
                url: '/yaOrders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaOrders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaOrder/yaOrders.html',
                        controller: 'YaOrderController'
                    }
                },
                resolve: {
                }
            })
            .state('yaOrder.detail', {
                parent: 'entity',
                url: '/yaOrder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaOrder'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaOrder/yaOrder-detail.html',
                        controller: 'YaOrderDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaOrder', function($stateParams, YaOrder) {
                        return YaOrder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaOrder.new', {
                parent: 'yaOrder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaOrder/yaOrder-dialog.html',
                        controller: 'YaOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    orderId: null,
                                    useCoupon: null,
                                    orderDate: null,
                                    productAmount: null,
                                    orderTotalPrice: null,
                                    orderStatus: null,
                                    orderStatusChangeTime: null,
                                    deliveryDate: null,
                                    deliveryPeriod: null,
                                    deliveryManName: null,
                                    deliveryPhone: null,
                                    potReturnMethod: null,
                                    hasPrivatePot: null,
                                    usePrivatePot: null,
                                    parentOrderId: null,
                                    isParentOrder: false,
                                    orderRealPrice: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaOrder', null, { reload: true });
                    }, function() {
                        $state.go('yaOrder');
                    })
                }]
            })
            .state('yaOrder.edit', {
                parent: 'yaOrder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaOrder/yaOrder-dialog.html',
                        controller: 'YaOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaOrder', function(YaOrder) {
                                return YaOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaOrder.delete', {
                parent: 'yaOrder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaOrder/yaOrder-delete-dialog.html',
                        controller: 'YaOrderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaOrder', function(YaOrder) {
                                return YaOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
