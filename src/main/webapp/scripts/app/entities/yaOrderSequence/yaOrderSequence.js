'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaOrderSequence', {
                parent: 'entity',
                url: '/yaOrderSequences',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaOrderSequences'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaOrderSequence/yaOrderSequences.html',
                        controller: 'YaOrderSequenceController'
                    }
                },
                resolve: {
                }
            })
            .state('yaOrderSequence.detail', {
                parent: 'entity',
                url: '/yaOrderSequence/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaOrderSequence'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaOrderSequence/yaOrderSequence-detail.html',
                        controller: 'YaOrderSequenceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaOrderSequence', function($stateParams, YaOrderSequence) {
                        return YaOrderSequence.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaOrderSequence.new', {
                parent: 'yaOrderSequence',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaOrderSequence/yaOrderSequence-dialog.html',
                        controller: 'YaOrderSequenceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaOrderSequence', null, { reload: true });
                    }, function() {
                        $state.go('yaOrderSequence');
                    })
                }]
            })
            .state('yaOrderSequence.edit', {
                parent: 'yaOrderSequence',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaOrderSequence/yaOrderSequence-dialog.html',
                        controller: 'YaOrderSequenceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaOrderSequence', function(YaOrderSequence) {
                                return YaOrderSequence.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaOrderSequence', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaOrderSequence.delete', {
                parent: 'yaOrderSequence',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaOrderSequence/yaOrderSequence-delete-dialog.html',
                        controller: 'YaOrderSequenceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaOrderSequence', function(YaOrderSequence) {
                                return YaOrderSequence.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaOrderSequence', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
