(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedTransferSuccessController', fixedTransferSuccessController);

    fixedTransferSuccessController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedTransferSuccessController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function fixedTransferSuccessController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;               // 返回登录页

        init();

        function init(){
            
        }

        /**
         * 返回登录页
         * @memberof selfRegistrationController
         * @function backLogin
         * @description 返回登录页
         */
        function backLogin(){
            //$state.go('setAccountInfoPwd');
            CommonService.toLogin();
        }
    }

})();
