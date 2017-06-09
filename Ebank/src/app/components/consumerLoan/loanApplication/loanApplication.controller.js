(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('loanApplicationCarController', loanApplicationCarController);

    loanApplicationCarController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ModalService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name loanApplicationCarController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 贷款申请加入购物车控制器
     */
    function loanApplicationCarController($rootScope, UserService, CommonService, ModalService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        //method
        vm.setShopcar = setShopcar; // 加入购物车方法
        vm.backLoanService = backLoanService;//返回贷款service
        vm.productname = $state.params['productname'];

        init();

        var productList;
        function init(){
            productList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
        }
        /**
         * 加入购物车方法
         * @memberof settlementShoppingCarController
         * @function requestCarList
         * @description 加入购物车方法
         */
        function setShopcar(){

            var car={
            'carCode': vm.productname,//产品编码
            'carPice':vm.model.money,//金额
            'carName': vm.productname//产品名称
            };//从界面取值
            sessionStorage.setItem(CONFIG.SESSION.SESSION_CAR_DATA,car);

            if ($rootScope.$broadcast.carList == undefined) {
                vm.dataCompare = [];
            }else{
                vm.dataCompare;
            }

            var exist = $.inArray(car, vm.dataCompare);
            var compareCount = vm.dataCompare.length;

            if (exist >= 0) {

                alert('请勿重复添加!');


                return true;
            } else {
                vm.dataCompare.splice(0, 0, car);
                $rootScope.$broadcast('carList', vm.dataCompare);

            }
        }
        /**
         * 关闭
         * @memberof settlementShoppingCarController
         * @function backLoanService
         * @description 关闭弹出框
         */
        function backLoanService(){
            ModalService.closeModal();
        }
    }
})();
