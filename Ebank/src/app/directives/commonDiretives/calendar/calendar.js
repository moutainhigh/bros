 (function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('uiCalendar', uiCalendar);

    function uiCalendar() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/directives/commonDiretives/calendar/calendar.html',
            scope: {
                'time': '='
            },
            link: linkFunc,
            controller: uiCalendarCtrl,
            controllerAs: 'vm'
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('time', function(time) {
                scope.time = time;
            });
        }

        function uiCalendarCtrl($scope) {
            var vm = this;
            vm.minDate = new Date(2013, 0, 1);
            vm.maxDate = new Date();

            vm.today = function() {
                $scope.time = new Date();
            };

            vm.clear = function() {
                $scope.time = null;
            };

            vm.open = function($event) {
                vm.status.opened = true;
            };

            vm.setDate = function(year, month, day) {
                $scope.time = new Date(year, month, day);
            };

            vm.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
            };

            vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'yyyyMMdd', 'dd.MM.yyyy', 'shortDate'];
            vm.format = vm.formats[1];

            vm.status = {
                opened: false
            };

            var tomorrow = new Date();
            tomorrow.setDate(tomorrow.getDate() + 1);
            var afterTomorrow = new Date();
            afterTomorrow.setDate(tomorrow.getDate() + 2);
            vm.events =
                [{
                    date: tomorrow,
                    status: 'full'
                }, {
                    date: afterTomorrow,
                    status: 'partially'
                }];

            vm.getDayClass = function(date, mode) {
                if (mode === 'day') {
                    var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                    for (var i = 0; i < vm.events.length; i++) {
                        var currentDay = new Date(vm.events[i].date).setHours(0, 0, 0, 0);

                        if (dayToCheck === currentDay) {
                            return vm.events[i].status;
                        }
                    }
                }

                return '';
            };
        }
    }
})();
