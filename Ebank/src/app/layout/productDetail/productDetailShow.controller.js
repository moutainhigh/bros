(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('productDetailShowController', productDetailShowController);

    productDetailShowController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ModalService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name productDetailShowController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 弹出框产品展示
     */
    function productDetailShowController($rootScope, UserService, CommonService,ModalService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.productrec = $rootScope.productrec;
        vm.addcar = addcar;

        /**
         * 添加购物车
         * @memberof productDetailShowController
         * @function addcar
         * @description 添加购物车
         */
        vm.dataCompare = [];
        function addcar(){
            var product={
                prodCode:vm.productrec.productCode,
                bankName:vm.productrec.productname,
                prodName:vm.productrec.productdesc,
                bankBalance:vm.model.bankBalance
            }
            vm.dataCompare = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_CAR_DATA));
            if (vm.dataCompare == null) {
                vm.dataCompare = [];
            }else{
                vm.dataCompare.splice(0, 0, product);
            }

            sessionStorage.setItem(CONFIG.SESSION.SESSION_CAR_DATA, JSON.stringify(vm.dataCompare));
            $rootScope.$broadcast('compareMessage', vm.dataCompare);
            ModalService.closeModal();
         }
    }
})();
