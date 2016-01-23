'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaBuilding', {
                parent: 'entity',
                url: '/yaBuildings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaBuildings'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaBuilding/yaBuildings.html',
                        controller: 'YaBuildingController'
                    }
                },
                resolve: {
                }
            })
            .state('yaBuilding.detail', {
                parent: 'entity',
                url: '/yaBuilding/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaBuilding'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaBuilding/yaBuilding-detail.html',
                        controller: 'YaBuildingDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaBuilding', function($stateParams, YaBuilding) {
                        return YaBuilding.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaBuilding.new', {
                parent: 'yaBuilding',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaBuilding/yaBuilding-dialog.html',
                        controller: 'YaBuildingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    buildingCode: null,
                                    buildingName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaBuilding', null, { reload: true });
                    }, function() {
                        $state.go('yaBuilding');
                    })
                }]
            })
            .state('yaBuilding.edit', {
                parent: 'yaBuilding',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaBuilding/yaBuilding-dialog.html',
                        controller: 'YaBuildingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaBuilding', function(YaBuilding) {
                                return YaBuilding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaBuilding', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaBuilding.delete', {
                parent: 'yaBuilding',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaBuilding/yaBuilding-delete-dialog.html',
                        controller: 'YaBuildingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaBuilding', function(YaBuilding) {
                                return YaBuilding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaBuilding', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
