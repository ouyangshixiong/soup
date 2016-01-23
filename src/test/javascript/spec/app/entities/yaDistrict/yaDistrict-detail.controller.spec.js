'use strict';

describe('Controller Tests', function() {

    describe('YaDistrict Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYaDistrict, MockYaBuilding;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYaDistrict = jasmine.createSpy('MockYaDistrict');
            MockYaBuilding = jasmine.createSpy('MockYaBuilding');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YaDistrict': MockYaDistrict,
                'YaBuilding': MockYaBuilding
            };
            createController = function() {
                $injector.get('$controller')("YaDistrictDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterLiquibaseBugApp:yaDistrictUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
