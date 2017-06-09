(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('sidebar',sidebar);

    function sidebar() {
        var directive = {
            restrict: 'EA',
            templateUrl: 'app/layout/sideBar/sideBar.html',
            link:linkFunc
        };

        return directive;

        function linkFunc(scope,el,attrs) {

        }

    }
})();
