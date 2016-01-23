'use strict';

describe('Controller Tests', function() {

    describe('YaSubProduct Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaSubProduct, MockYaProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaSubProduct = jasmine.createSpy('MockYaSubProduct');
            MockYaProduct = jasmine.createSpy('MockYaProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaSubProduct': MockYaSubProduct,
                'YaProduct': MockYaProduct
            };
            createController = function() {
                $injector.get('$controller')("YaSubProductDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaSubProductUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
