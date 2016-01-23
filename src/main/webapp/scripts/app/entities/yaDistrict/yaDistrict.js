'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaDistrict', {
                parent: 'entity',
                url: '/yaDistricts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaDistricts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaDistrict/yaDistricts.html',
                        controller: 'YaDistrictController'
                    }
                },
                resolve: {
                }
            })
            .state('yaDistrict.detail', {
                parent: 'entity',
                url: '/yaDistrict/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaDistrict'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaDistrict/yaDistrict-detail.html',
                        controller: 'YaDistrictDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaDistrict', function($stateParams, YaDistrict) {
                        return YaDistrict.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaDistrict.new', {
                parent: 'yaDistrict',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaDistrict/yaDistrict-dialog.html',
                        controller: 'YaDistrictDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    districtCode: null,
                                    districtName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaDistrict', null, { reload: true });
                    }, function() {
                        $state.go('yaDistrict');
                    })
                }]
            })
            .state('yaDistrict.edit', {
                parent: 'yaDistrict',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaDistrict/yaDistrict-dialog.html',
                        controller: 'YaDistrictDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaDistrict', function(YaDistrict) {
                                return YaDistrict.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaDistrict', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaDistrict.delete', {
                parent: 'yaDistrict',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaDistrict/yaDistrict-delete-dialog.html',
                        controller: 'YaDistrictDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaDistrict', function(YaDistrict) {
                                return YaDistrict.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaDistrict', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
