(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('currencyFormat', currencyFormat);

    currencyFormat.$inject = ['$filter'];

    function currencyFormat($filter) {
        return function(number) {
            var num = parseInt(number);

            if(!isNaN(num)) {
                if (num < 10000) {
                    number = $filter('number')(number, '0');
                } else if (num >= 10000) {
                    number = parseInt(num / 10000) + '万';
                }
            }

            return number;
        };
    }
})();
