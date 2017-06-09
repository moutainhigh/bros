(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('paymentMethodTypeId', paymentMethodTypeId);

    function paymentMethodTypeId() {
        return function(paymentMethodTypeId) {
            var result = '';
            switch (paymentMethodTypeId) {
                case 'OUR_DEBIT_CARD': //本行借记卡
                    result = '本行借记卡';
                    break;
                case 'OTHER_DEBIT_CARD': //他行借记卡
                    result = '他行借记卡';
                    break;
                case 'PUBLIC_CURRENT_ACCOUNT': //对公活期户
                    result = '对公活期户';
                    break;
                case 'PRIVATE_CURRENT_ACCOUNT': //对私活期户
                    result = '对私活期户';
                    break;
                case 'CAPAY': //现金
                    result = '现金';
                    break;
            }
            return result;

        };
    }
})();
