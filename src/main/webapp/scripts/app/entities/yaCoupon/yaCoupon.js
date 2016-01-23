'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaCoupon', {
                parent: 'entity',
                url: '/yaCoupons',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaCoupons'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaCoupon/yaCoupons.html',
                        controller: 'YaCouponController'
                    }
                },
                resolve: {
                }
            })
            .state('yaCoupon.detail', {
                parent: 'entity',
                url: '/yaCoupon/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaCoupon'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaCoupon/yaCoupon-detail.html',
                        controller: 'YaCouponDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaCoupon', function($stateParams, YaCoupon) {
                        return YaCoupon.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaCoupon.new', {
                parent: 'yaCoupon',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaCoupon/yaCoupon-dialog.html',
                        controller: 'YaCouponDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    couponName: null,
                                    couponType: null,
                                    couponPictures: null,
                                    price: null,
                                    keywords: null,
                                    couponDiscription: null,
                                    couponAmount: null,
                                    couponInventory: null,
                                    onShelveDate: null,
                                    offShelveDate: null,
                                    couponStatus: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaCoupon', null, { reload: true });
                    }, function() {
                        $state.go('yaCoupon');
                    })
                }]
            })
            .state('yaCoupon.edit', {
                parent: 'yaCoupon',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaCoupon/yaCoupon-dialog.html',
                        controller: 'YaCouponDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaCoupon', function(YaCoupon) {
                                return YaCoupon.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaCoupon', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaCoupon.delete', {
                parent: 'yaCoupon',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaCoupon/yaCoupon-delete-dialog.html',
                        controller: 'YaCouponDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaCoupon', function(YaCoupon) {
                                return YaCoupon.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaCoupon', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
