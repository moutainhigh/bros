(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('getAccountInfoController', getAccountInfoController);

    getAccountInfoController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  getAccountInfoController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function getAccountInfoController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;               // 返回登录页

        init();

        function init(){
            var confirmData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM));
            if(confirmData != null){
                vm.model.custName = confirmData.custName;
            }
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
