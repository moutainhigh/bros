(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('intercept', intercept);
    /**
    *   性别
    */
    function intercept() {
        return function(input) {
            var inputFile = input;
            var reg = /\[(.*?)\]/gi;
            var tmp = inputFile.match(reg);
            if (tmp) {
                for (var i = 0; i < tmp.length; i++) {
                    // alert(tmp[i]); // 保留中括号
                    // alert(tmp[i].replace(reg, "$1")); // 不保留中括号
                    inputFile = tmp[i].replace(reg, "$1");
                }
            }
            return inputFile;
        };
    }
})();
