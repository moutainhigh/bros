(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('currencyC', currencyC);
    //币种
    function currencyC() {
        return function(currencyC) {
            var result = '';
             switch (currencyC) {
                    case '01': //人民币
                        result = '人民币';
                        break;
                    case 'CNY': //人民币
                    result = '人民币';
                    break;
                }
            return result;
        };
    }
})();
