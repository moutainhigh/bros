(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedTransferController', fixedTransferController);

    fixedTransferController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedTransferController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function fixedTransferController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;                               // 返回登录
        vm.fixedTranserConfirm = fixedTranserConfirm;           // 跳转到确认页面

        init();


        function init(){

        }

        /**
         * 跳转到确认页面
         * @memberof fixedTransferController
         * @function fixedTranserConfirm
         * @description 返回登录页
         */ 
        function fixedTranserConfirm(){
            $state.go('fixedTransferConfirm');
        }

        /**
         * 返回登录页
         * @memberof fixedTransferController
         * @function backLogin
         * @description 返回登录页
         */
        function backLogin(){
            CommonService.toLogin();
        }

    }

})();
