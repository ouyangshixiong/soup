'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yaQuestion', {
                parent: 'entity',
                url: '/yaQuestions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaQuestions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaQuestion/yaQuestions.html',
                        controller: 'YaQuestionController'
                    }
                },
                resolve: {
                }
            })
            .state('yaQuestion.detail', {
                parent: 'entity',
                url: '/yaQuestion/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'YaQuestion'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yaQuestion/yaQuestion-detail.html',
                        controller: 'YaQuestionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'YaQuestion', function($stateParams, YaQuestion) {
                        return YaQuestion.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yaQuestion.new', {
                parent: 'yaQuestion',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaQuestion/yaQuestion-dialog.html',
                        controller: 'YaQuestionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    yaQ: null,
                                    yaA: null,
                                    priority: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yaQuestion', null, { reload: true });
                    }, function() {
                        $state.go('yaQuestion');
                    })
                }]
            })
            .state('yaQuestion.edit', {
                parent: 'yaQuestion',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaQuestion/yaQuestion-dialog.html',
                        controller: 'YaQuestionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YaQuestion', function(YaQuestion) {
                                return YaQuestion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaQuestion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yaQuestion.delete', {
                parent: 'yaQuestion',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yaQuestion/yaQuestion-delete-dialog.html',
                        controller: 'YaQuestionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YaQuestion', function(YaQuestion) {
                                return YaQuestion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yaQuestion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
