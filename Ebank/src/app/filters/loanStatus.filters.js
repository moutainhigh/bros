(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('loanStatus', loanStatus);
    /**
    *   性别
    */
    function loanStatus() {
        return function(loanStatus) {
            var result = loanStatus;
             switch (loanStatus) {
                    case '0': //正常
                        result = '正常';
                        break;
                    case '1': //逾期
                        result = '逾期';
                        break;
                    case '2': //呆滞
                        result = '呆滞';
                        break;
                    case '3': //呆账
                        result = '呆账';
                        break;
                }

            return result;
        };
    }
})();
