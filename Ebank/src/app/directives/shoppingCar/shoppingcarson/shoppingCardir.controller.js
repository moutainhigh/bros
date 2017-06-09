(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('shoppingCarController', shoppingCarController);

    shoppingCarController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name shoppingCarController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 我的贷款主页面控制器
     */
    function shoppingCarController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
        vm.clicktype = clicktype;
        //初始化方法
        init();

        function init(){
            showchushihua();//初始化
        }

        /**
         * 查询商品组合产品
         * @memberof shoppingCarController
         * @function clicktype
         * @description 查询商品组合产品
         */
        function clicktype(productname){
            $scope.producttj.show = !$scope.producttj.show;
            var params = {
                productTpye: productname//产品标志
            };
            var promise = UserService.getproductDetail(params);  //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {

                vm.productCarList = data.respData;


            }).catch(function(error) {
                CommonService.showError(error);
            });
        }
         /**
         * 购物车结算界面推荐产品初始化
         * @memberof settlementShoppingCarController
         * @function showchushihua
         * @description 初始化
         */
        function showchushihua(){

            $scope.producttj={
                show:false
            };
        }
    }
})();
