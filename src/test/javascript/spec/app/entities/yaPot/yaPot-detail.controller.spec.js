'use strict';

describe('Controller Tests', function() {

    describe('YaPot Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaPot, MockYaUser, MockYaOrder;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaPot = jasmine.createSpy('MockYaPot');
            MockYaUser = jasmine.createSpy('MockYaUser');
            MockYaOrder = jasmine.createSpy('MockYaOrder');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaPot': MockYaPot,
                'YaUser': MockYaUser,
                'YaOrder': MockYaOrder
            };
            createController = function() {
                $injector.get('$controller')("YaPotDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaPotUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
