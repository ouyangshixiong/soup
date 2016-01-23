'use strict';

describe('Controller Tests', function() {

    describe('YaOrder Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaOrder, MockYaUser, MockYaAddress, MockYaPot, MockYaCouponOrder, MockYaProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaOrder = jasmine.createSpy('MockYaOrder');
            MockYaUser = jasmine.createSpy('MockYaUser');
            MockYaAddress = jasmine.createSpy('MockYaAddress');
            MockYaPot = jasmine.createSpy('MockYaPot');
            MockYaCouponOrder = jasmine.createSpy('MockYaCouponOrder');
            MockYaProduct = jasmine.createSpy('MockYaProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaOrder': MockYaOrder,
                'YaUser': MockYaUser,
                'YaAddress': MockYaAddress,
                'YaPot': MockYaPot,
                'YaCouponOrder': MockYaCouponOrder,
                'YaProduct': MockYaProduct
            };
            createController = function() {
                $injector.get('$controller')("YaOrderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
