
/**
 * @name transferProduce
 * @description
 * 转账产品指令
 */
(function(){
    'use strict';

    angular
        .module('EBankProject')
        .directive('transferProduct', transferProduce);

     /**
     * @memberof directbank
     * @ngdoc directive
     * @name primeProduct
     * @description
     *
     *
     * @attr {object} product 产品信息
     * @attr {theme} 主题 red orange
     * @example
     *   Usage:
     *    <transfer-product compare="vm.addcar();" product="product" ></transfer-product>
     */

    function transferProduce($compile) {
        var directive = {
            restrict: 'E',
            scope:{
                product: '=',
                compare: '&'
            },
            templateUrl:'app/directives/transferService/transferProduct.html',
            controller:transferProductController,
            controllerAs: 'vm'
        };
        return directive;

        function transferProductController($state,CONFIG){
            var vm=this;
            vm.imgMouseover = imgMouseover;
            vm.imgMouseLeave = imgMouseLeave;
            vm.goproductdetail = goproductdetail;
            function goproductdetail(product){
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(product));

                $state.go('productdetail');
            }
            function imgMouseover(){
                vm.mouseFlag = true;
            }
            function imgMouseLeave(){
                vm.mouseFlag = false;
            }
        }
    }

})();
