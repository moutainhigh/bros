(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('forgetPasswordController', forgetPasswordController);

    forgetPasswordController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name forgetPasswordController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function forgetPasswordController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm                = this;
        vm.model              = {};
        //method
        vm.toSetLoginPassword = toSetLoginPassword;             // 提交密码重置验证信息
        vm.backLogin          = backLogin;                               // 返回登录

        //value

        init();


        function init(){
            var previousStateData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            if(previousStateData != null){
                vm.model.account        = previousStateData.account;
                vm.model.cardtype       = previousStateData.cardtype;
                vm.model.cardpassword   = previousStateData.cardpassword;
                vm.model.custName       = previousStateData.custName;
                vm.model.idcardtype     = previousStateData.idcardtype;
                vm.model.certNo         = previousStateData.certNo;
                vm.model.moblie         = previousStateData.moblie;
                vm.model.email          = previousStateData.email;

            }
        }


        /**
         * 提交密码重置验证信息方法
         * @memberof ResetPasswordController
         * @function toSetLoginPassword
         * @description 提交密码重置验证信息描述
         */
         function toSetLoginPassword() {

          if (vm.regForm.$valid) {
             var params = {
                    account: vm.model.account,
                    cardtype: vm.model.cardtype,
                    cardpassword: vm.model.cardpassword,
                    custName: vm.model.custName,
                    idcardtype: vm.model.idcardtype,
                    certNo: vm.model.certNo,
                    moblie: vm.model.moblie,
                    email: vm.model.email,
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));
           $state.go('setPassword');
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
