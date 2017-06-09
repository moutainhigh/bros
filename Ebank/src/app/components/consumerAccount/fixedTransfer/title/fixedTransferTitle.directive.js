/**
 * @name registerProgress
 * @description
 * 活转定流程
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('rfixedtransfertitle', rfixedTransferTitleDirective);

    function rfixedTransferTitleDirective() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerAccount/fixedTransfer/title/fixedTransferTitle.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            var progressLength = $('.register').children().length;
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
