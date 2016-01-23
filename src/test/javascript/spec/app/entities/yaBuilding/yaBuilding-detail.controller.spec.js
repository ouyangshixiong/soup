'use strict';

describe('Controller Tests', function() {

    describe('YaBuilding Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaBuilding, MockYaDistrict;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaBuilding = jasmine.createSpy('MockYaBuilding');
            MockYaDistrict = jasmine.createSpy('MockYaDistrict');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaBuilding': MockYaBuilding,
                'YaDistrict': MockYaDistrict
            };
            createController = function() {
                $injector.get('$controller')("YaBuildingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaBuildingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
