(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('addPlus', addPlus);

    function addPlus() {
        return function(price) {
            var result = '';
            if (parseFloat(price) > 0) {
                result = '+' + price;
            } else {
                result = price;
            }
            return result;
        };
    }
})();
