'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaUser', {
                parent: 'entity',
                url: '/yaUsers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaUsers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaUser/yaUsers.html',
                        controller: 'YaUserController'
                    }
                },
                resolve: {
                }
            })
            .state('yaUser.detail', {
                parent: 'entity',
                url: '/yaUser/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaUser'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaUser/yaUser-detail.html',
                        controller: 'YaUserDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaUser', function($stateParams, YaUser) {
                        return YaUser.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaUser.new', {
                parent: 'yaUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaUser/yaUser-dialog.html',
                        controller: 'YaUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    mobileNumber: null,
                                    facebook: null,
                                    email: null,
                                    password: null,
                                    gender: null,
                                    loginStatus: null,
                                    hasPrivatePot: null,
                                    authToken: null,
                                    authTokenUpdateTime: null,
                                    smUsedCount: null,
                                    smLastCheckDate: null,
                                    smLastContent: null,
                                    smExpireTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaUser', null, { reload: true });
                    }, function() {
                        $state.go('yaUser');
                    })
                }]
            })
            .state('yaUser.edit', {
                parent: 'yaUser',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaUser/yaUser-dialog.html',
                        controller: 'YaUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaUser', function(YaUser) {
                                return YaUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaUser.delete', {
                parent: 'yaUser',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaUser/yaUser-delete-dialog.html',
                        controller: 'YaUserDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaUser', function(YaUser) {
                                return YaUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
