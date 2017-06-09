(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('degree', degree);
    /**
    *   学历
    */
    function degree() {
        return function(degree) {
            var result = degree;
             switch (degree) {
                    case '1': //大专以下
                        result = '大专以下';
                        break;
                    case '2': //本科
                        result = '本科';
                        break;
                    case '3': //硕士
                        result = '硕士';
                        break;
                    case '4': //博士
                        result = '博士';
                        break;
                    case '5': //海归
                        result = '海归';
                        break;
                    case '6': //其他
                        result = '其他';
                        break;
                }

            return result;
        };
    }
})();
