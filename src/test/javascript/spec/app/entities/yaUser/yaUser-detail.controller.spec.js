'use strict';

describe('Controller Tests', function() {

    describe('YaUser Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaUser, MockYaPot, MockYaOrder, MockYaCouponOrder, MockYaAddress;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaUser = jasmine.createSpy('MockYaUser');
            MockYaPot = jasmine.createSpy('MockYaPot');
            MockYaOrder = jasmine.createSpy('MockYaOrder');
            MockYaCouponOrder = jasmine.createSpy('MockYaCouponOrder');
            MockYaAddress = jasmine.createSpy('MockYaAddress');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaUser': MockYaUser,
                'YaPot': MockYaPot,
                'YaOrder': MockYaOrder,
                'YaCouponOrder': MockYaCouponOrder,
                'YaAddress': MockYaAddress
            };
            createController = function() {
                $injector.get('$controller')("YaUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
