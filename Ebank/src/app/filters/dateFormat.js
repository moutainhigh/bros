/**
 * Created by macbook on 16/2/18.
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('dateFormat', dateFormat);
    /**
     * 日期格式化过滤器
     * @memberof EBankProject
     * @function dateFormat
     * example:
     * <div ng-bind="'ASASSdsff21312D20160302SSDAasfdsf'|dateFormat:{symbol:''}"></div>
     * @description 日期格式化过滤器
     * data  传入的数据
     * format 格式化参数(不区分大小写)
     * 格式:symbol
     *     年 yyyy
     *     月 mm
     *     日 dd
     *     连接符号可自定义 :
     *          dd-mm-yyyy  dd/mm/yyyy dd:mm:yyyy .....
     *     顺序颠倒:
     *          dd-mm-yyyy  mm-dd-yyyy  yyyy-mm-dd   .....
     *     可以缺省配置:
     *          dd-mm   yyyy   dd  dd-mm   .....
     *     不区分大小写:
     *          Dd-mm  YYYY    MM-dd-yyYy ....
     *     可以不设置任何格式:
     *          {symbol:''}为默认显示
     */

    function dateFormat() {
        return function(data, format) {

            var str = data || '';
            var regexp = /[1-2][09]\d{2}[01][1-9][0-3][0-9]/;
            var result = null;
            var yyyy = '',
                MM = '',
                dd = '';
            var fullYear, fullMonth, fullDate = null;

            if (str.length > 0) {

                var dateNum = str.match(regexp);
                dateNum = dateNum.toString();
                var dateArr = dateNum.split('');

                for (var i = 0; i < dateArr.length; i++) {
                    if (i < 4) {
                        yyyy += dateArr[i];
                    } else if (i >= 4 && i < 6) {
                        MM += dateArr[i];
                    } else {
                        dd += dateArr[i];
                    }
                }

                fullYear = format.symbol.toLowerCase().replace(/yyyy/, function(word) {
                    return yyyy || '';
                });
                fullMonth = fullYear.replace(/mm/, function(word) {
                    return MM || '';
                });
                fullDate = fullMonth.replace(/dd/, function(word) {
                    return dd || '';
                });
                result = fullDate || dateNum;
            }

            return result;
        };
    }
})();
