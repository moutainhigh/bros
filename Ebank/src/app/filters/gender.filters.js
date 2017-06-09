(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('gender', gender);
    /**
    *   性别
    */
    function gender() {
        return function(gender) {
            var result = gender;
             switch (gender) {
                    case '1': //男
                        result = '男';
                        break;
                    case '2': //女
                        result = '女';
                        break;
                    case '3': //不详
                        result = '不详';
                        break;
                }

            return result;
        };
    }
})();
