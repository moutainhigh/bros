/**
 * @name transferDefaultStateProgress
 * @description
 *  转账流程状态
 */
 (function() {
    'use strict';

    angular.module('EBankProject').directive('transferdefaultstate', transferdefaultstate);

    function transferdefaultstate() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerTransfer/transferDefaultState/transferDefaultState.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

            var progressLength = $('.transferdefault').children().length;
            var activeArray = [],
            finishArray = [];
            for (var i = 0; i < progressLength; i++) {
                activeArray.push(false);
                if (i + 1 < scope.flow) {
                    finishArray[i] = true;
                } else {
                    finishArray[i] = false;
                }
            }
            activeArray[scope.flow - 1] = true;
            scope.finishs = finishArray;
            scope.actives = activeArray;
        }
    }

})();
