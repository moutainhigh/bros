/**
 * @name customTab
 * @description
 * 页签切换指令
 */

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('customTab', customTab);

    function customTab() {
        var directive = {
            restrict: 'EA',
            scope: {
                items: '=',
                index: '=',
                itemWidth : '@'
            },
            templateUrl: 'app/directives/commonDiretives/tabs/tab.html',
            transclude:true,
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attrs) {
            if(scope.itemWidth){
                scope.width = scope.itemWidth;
            } else {
                scope.width = 100 / scope.items.length +'%';
            }

            scope.select = function(index) {
                scope.index = index;
            };
        }
    }
})();
