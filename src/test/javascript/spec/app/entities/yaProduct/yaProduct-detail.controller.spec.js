'use strict';

describe('Controller Tests', function() {

    describe('YaProduct Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaProduct, MockYaOrder, MockYaSubProduct, MockYaBanner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaProduct = jasmine.createSpy('MockYaProduct');
            MockYaOrder = jasmine.createSpy('MockYaOrder');
            MockYaSubProduct = jasmine.createSpy('MockYaSubProduct');
            MockYaBanner = jasmine.createSpy('MockYaBanner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaProduct': MockYaProduct,
                'YaOrder': MockYaOrder,
                'YaSubProduct': MockYaSubProduct,
                'YaBanner': MockYaBanner
            };
            createController = function() {
                $injector.get('$controller')("YaProductDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaProductUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
