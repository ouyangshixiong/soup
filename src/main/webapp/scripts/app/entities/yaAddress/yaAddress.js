'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaAddress', {
                parent: 'entity',
                url: '/yaAddresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaAddresss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaAddress/yaAddresss.html',
                        controller: 'YaAddressController'
                    }
                },
                resolve: {
                }
            })
            .state('yaAddress.detail', {
                parent: 'entity',
                url: '/yaAddress/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaAddress'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaAddress/yaAddress-detail.html',
                        controller: 'YaAddressDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaAddress', function($stateParams, YaAddress) {
                        return YaAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaAddress.new', {
                parent: 'yaAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaAddress/yaAddress-dialog.html',
                        controller: 'YaAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    receiverName: null,
                                    receiverMobile: null,
                                    receiverDistrict: null,
                                    receiverBuilding: null,
                                    receiverFloor: null,
                                    receiverCompany: null,
                                    isDefault: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaAddress', null, { reload: true });
                    }, function() {
                        $state.go('yaAddress');
                    })
                }]
            })
            .state('yaAddress.edit', {
                parent: 'yaAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaAddress/yaAddress-dialog.html',
                        controller: 'YaAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaAddress', function(YaAddress) {
                                return YaAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaAddress.delete', {
                parent: 'yaAddress',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaAddress/yaAddress-delete-dialog.html',
                        controller: 'YaAddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaAddress', function(YaAddress) {
                                return YaAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
