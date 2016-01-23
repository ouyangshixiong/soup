'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaCouponOrder', {
                parent: 'entity',
                url: '/yaCouponOrders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaCouponOrders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaCouponOrder/yaCouponOrders.html',
                        controller: 'YaCouponOrderController'
                    }
                },
                resolve: {
                }
            })
            .state('yaCouponOrder.detail', {
                parent: 'entity',
                url: '/yaCouponOrder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaCouponOrder'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaCouponOrder/yaCouponOrder-detail.html',
                        controller: 'YaCouponOrderDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaCouponOrder', function($stateParams, YaCouponOrder) {
                        return YaCouponOrder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaCouponOrder.new', {
                parent: 'yaCouponOrder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaCouponOrder/yaCouponOrder-dialog.html',
                        controller: 'YaCouponOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    couponOrderId: null,
                                    couponOrderDate: null,
                                    couponExpireDate: null,
                                    couponNeedPay: false,
                                    couponOrderPrice: null,
                                    couponOrderStatus: null,
                                    orderStatusChangeTime: null,
                                    parentcouponOrderId: null,
                                    isParentCouponOrder: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaCouponOrder', null, { reload: true });
                    }, function() {
                        $state.go('yaCouponOrder');
                    })
                }]
            })
            .state('yaCouponOrder.edit', {
                parent: 'yaCouponOrder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaCouponOrder/yaCouponOrder-dialog.html',
                        controller: 'YaCouponOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaCouponOrder', function(YaCouponOrder) {
                                return YaCouponOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaCouponOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaCouponOrder.delete', {
                parent: 'yaCouponOrder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaCouponOrder/yaCouponOrder-delete-dialog.html',
                        controller: 'YaCouponOrderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaCouponOrder', function(YaCouponOrder) {
                                return YaCouponOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaCouponOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
