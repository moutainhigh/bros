/**
 * @name registerProgress
 * @description
 * 推荐产品、浏览记录状态
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('loanhead', loanhead);
    loanhead.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies'];

    function loanhead(UserService, CommonService, $state, $rootScope, $cookies) {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerLoan/loanhead/loanhead.html',
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
