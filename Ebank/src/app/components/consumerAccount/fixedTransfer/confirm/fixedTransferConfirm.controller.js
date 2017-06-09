(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedTransferConfrmController', fixedTransferConfrmController);

    fixedTransferConfrmController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedTransferConfrmController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function fixedTransferConfrmController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;                               // 返回登录
        vm.fixedTranserSussess = fixedTranserSussess;           // 跳转到确认页面

        init();


        function init(){

        }

        /**
         * 跳转到确认页面
         * @memberof fixedTransferConfrmController
         * @function fixedTranserConfirm
         * @description 返回登录页
         */ 
        function fixedTranserSussess(){
            $state.go('fixedTransferSuccess');
        }

        /**
         * 返回录入页
         * @memberof fixedTransferConfrmController
         * @function backLogin
         * @description 返回录入页
         */
        function backLogin(){
            $state.go('fixedTransfer');
        }

    }

})();
