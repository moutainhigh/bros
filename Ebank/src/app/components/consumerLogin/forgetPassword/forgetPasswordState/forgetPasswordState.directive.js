/**
 * @name forgetPasswordstateProgress
 * @description
 * 忘记吗密码流程状态
 */
 (function() {
    'use strict';

    angular.module('EBankProject').directive('forgetpasswordstate', forgetpasswordstate);

    function forgetpasswordstate() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerLogin/forgetPassword/forgetPasswordState/forgetPasswordState.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

            var progressLength = $('.forgetpassword').children().length;
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
