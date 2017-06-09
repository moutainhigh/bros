(function() {
    'use strict';

    /**
     * 账号格式化过滤器
     * @memberof EBankProject
     * @function splitNumberFilter
     * example:
     * ng-bind="(vm.model.payaccount| splitNumberFilter)"
     * @description 账号格式化过滤器
     * input  传入的数据
     */
    angular
        .module('EBankProject')
        .filter('splitNumberFilter', splitNumberFilter);

    function splitNumberFilter() {
        return function(content) {
            return content ? content.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1  ") : content;
        };
    }
})();



// app.filter('splitNumberFilter', [function() {
//       return function(content) {
//         return content ? content.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ") : content;
//       }
// }]);
