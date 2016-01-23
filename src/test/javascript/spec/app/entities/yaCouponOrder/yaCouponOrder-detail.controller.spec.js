'use strict';

describe('Controller Tests', function() {

    describe('YaCouponOrder Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaCouponOrder, MockYaUser, MockYaCoupon;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaCouponOrder = jasmine.createSpy('MockYaCouponOrder');
            MockYaUser = jasmine.createSpy('MockYaUser');
            MockYaCoupon = jasmine.createSpy('MockYaCoupon');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaCouponOrder': MockYaCouponOrder,
                'YaUser': MockYaUser,
                'YaCoupon': MockYaCoupon
            };
            createController = function() {
                $injector.get('$controller')("YaCouponOrderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaCouponOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
