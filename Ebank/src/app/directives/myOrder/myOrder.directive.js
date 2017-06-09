(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('financialTradeItem', financialTradeItem);

    financialTradeItem.$inject = ['$compile','CONFIG'];

    function financialTradeItem($compile,CONFIG) {
        var directive = {
            restrict: 'AE',
            scope: {
                type: '=',
                list:'='
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('type', function(type) {
                switch (type) {
                    case CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT://已完成订单
                        el.html('<completedorder-item list="list"></completedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.TRANS_CATEGORY.DEPOSIT: //未完成订单
                        el.html('<notcompletedorder-item list="list"></notcompletedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                }
            });
        }
    }
})();
