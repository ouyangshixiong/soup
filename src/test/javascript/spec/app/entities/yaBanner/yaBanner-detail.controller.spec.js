'use strict';

describe('Controller Tests', function() {

    describe('YaBanner Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaBanner, MockYaProduct, MockYaCoupon;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaBanner = jasmine.createSpy('MockYaBanner');
            MockYaProduct = jasmine.createSpy('MockYaProduct');
            MockYaCoupon = jasmine.createSpy('MockYaCoupon');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaBanner': MockYaBanner,
                'YaProduct': MockYaProduct,
                'YaCoupon': MockYaCoupon
            };
            createController = function() {
                $injector.get('$controller')("YaBannerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaBannerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
