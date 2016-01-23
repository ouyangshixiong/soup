'use strict';

describe('Controller Tests', function() {

    describe('YaAddress Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaAddress, MockYaUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaAddress = jasmine.createSpy('MockYaAddress');
            MockYaUser = jasmine.createSpy('MockYaUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaAddress': MockYaAddress,
                'YaUser': MockYaUser
            };
            createController = function() {
                $injector.get('$controller')("YaAddressDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaAddressUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
