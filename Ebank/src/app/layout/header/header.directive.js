/**
 * @name HEADER
 * @description
 * HEADER
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('cheader', cheader);

    cheader.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies'];


    function cheader(UserService, CommonService, $state, $rootScope, $cookies) {
        var directive = {
            restrict: 'A',
            replace: true,
            templateUrl: 'app/layout/header/header.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

        }
    }
})();
