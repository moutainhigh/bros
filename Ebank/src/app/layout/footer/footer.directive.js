/**
 * Created by lenovo on 2015/2/11.
 */
/**
 * @name HEADER
 * @description
 * FOOTER
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('cfooter', cfooter);

    function cfooter() {
        var directive = {
            restrict: 'A',
            replace: true,
            templateUrl: 'app/layout/footer/footer.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

        }
    }
})();
