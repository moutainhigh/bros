(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('setPasswordSuccController', setPasswordSuccController);

    setPasswordSuccController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name setPasswordSuccController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function setPasswordSuccController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm = this;

        //method
        vm.backLogin = backLogin; //返回登录页

        //value

        vm.model = {};

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
