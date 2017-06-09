/**
 * @name registerProgress
 * @description
 * 推荐产品、浏览记录状态
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('browsehistory', browsehistory);
    browsehistory.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies'];

    function browsehistory(UserService, CommonService, $state, $rootScope, $cookies) {
        var directive = {
            restrict: 'EA',
            templateUrl: 'app/layout/modalBlock/browseHistory/browseHistory.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope,el,attrs) {

        }
    }

})();
