(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('TransferDefaultSuccController', TransferDefaultSuccController);

    TransferDefaultSuccController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name TransferDefaultSuccController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function TransferDefaultSuccController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm = this;

        //method
        vm.backTranfer = backTranfer; //返回转账主页

        //value

        vm.model = {};

        init();

        function init(){
            var confirmData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM));
            if(confirmData != null){
                vm.model.custName = confirmData.custName;
                vm.model.payamount = confirmData.payamount;
            }
        }



      /**
         * 返回转账页面
         * @memberof TransferDefaultInputController
         * @function backTranfer
         * @description 返回转账页面
         */
         function backTranfer(){
            $state.go('accountManagement');

        }

    }
})();
