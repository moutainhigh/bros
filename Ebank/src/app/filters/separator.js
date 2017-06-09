(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('separator', separator);

    function separator() {
        return function(str, sepObj) {
            str = str || '';
            var newStr = '';
            if(str.indexOf('****') != -1) {
                newStr = str;
            } else {
                for (var i = 0; i < str.length; i++) {
                    if (i !== 0 && i % sepObj.num === 0) {
                        newStr += sepObj.symbol;
                    }
                    newStr += str[i];
                }
            }

            return newStr;
        };
    }
})();
