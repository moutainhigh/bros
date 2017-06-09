(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('cardCiper', cardCiper);

    function cardCiper() {
        return function(cardNo) {
            if (cardNo && cardNo.length > 4) {
                return cardNo.substr(0, 4) + ' **** **** ' + cardNo.substr(-4);
            } else {
                return cardNo;
            }
        };
    }
})();
