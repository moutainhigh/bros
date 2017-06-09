(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('consumerLoandirective', consumerLoandirective);

    consumerLoandirective.$inject = ['$compile','CONFIG'];

    function consumerLoandirective($compile,CONFIG) {
        var directive = {
            restrict: 'AE',
            scope: {
                type: '=',
                list:'=',
                product:'='
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('type', function(type) {
                switch (type) {
                    case CONFIG.CONSTANT.CONSUMER_LOAN.LOANSERVICE://贷款申请
                        el.html('<loan-servicedir product="product"></loan-servicedir>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.CONSUMER_LOAN.MYLOAN: //我的贷款
                        el.html('<my-loandir product="product"></my-loandir>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.CONSUMER_LOAN.PROGRESS: //贷款进度
                        el.html('<notcompletedorder-item list="list"></notcompletedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.CONSUMER_LOAN.PERSONDATA: //个人资料
                        el.html('<notcompletedorder-item list="list"></notcompletedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                }
            });
        }
    }
})();
