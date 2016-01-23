'use strict';

describe('Controller Tests', function() {

    describe('YaOrderSequence Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaOrderSequence;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaOrderSequence = jasmine.createSpy('MockYaOrderSequence');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaOrderSequence': MockYaOrderSequence
            };
            createController = function() {
                $injector.get('$controller')("YaOrderSequenceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaOrderSequenceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
