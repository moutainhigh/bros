
/**
 * @name productView
 * @description
 * 产品视图
 */
(function(){
    'use strict';

    angular
        .module('EBankProject')
        .directive('productView', productView);

     /**
     * @memberof directbank
     * @ngdoc directive
     * @name productView
     * @description
     *
     *
     * @attr {object} product 账户信息
     * @example
     *   Usage:
     *    <account-management accountList="accountList" ></account-management>
     */

    function productView($compile) {
        var directive = {
            restrict: 'E',
            scope:{
            	list: '=',
                compare: '&'
            },
            templateUrl:'app/directives/productView/productViewDir.html'
        };
        return directive;
    }

})();
