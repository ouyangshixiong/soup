'use strict';

describe('Controller Tests', function() {

    describe('YaCoupon Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaCoupon, MockYaCouponOrder, MockYaBanner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaCoupon = jasmine.createSpy('MockYaCoupon');
            MockYaCouponOrder = jasmine.createSpy('MockYaCouponOrder');
            MockYaBanner = jasmine.createSpy('MockYaBanner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaCoupon': MockYaCoupon,
                'YaCouponOrder': MockYaCouponOrder,
                'YaBanner': MockYaBanner
            };
            createController = function() {
                $injector.get('$controller')("YaCouponDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaCouponUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
