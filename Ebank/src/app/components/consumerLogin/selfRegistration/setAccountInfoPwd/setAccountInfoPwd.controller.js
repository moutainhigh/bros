(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('setAccountInfoPwdController', setAccountInfoPwdController);

    setAccountInfoPwdController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  setAccountInfoPwdController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function setAccountInfoPwdController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG) {
        var vm = this;
        vm.model = {};

        //method
        vm.backRegistAccountInforPage = backRegistAccountInforPage;               // 返回用户信息填写
        vm.nextGetAccountInfoPage = nextGetAccountInfoPage;                       // 跳转注册成功页面

        init();

        // 初始化页面
        function init(){
            var previousStateData = localStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT);
            localStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT,previousStateData);
        }

        /**
         *
         * @memberof setAccountInfoPwdController
         * @function backRegistAccountInforPage
         * @description 返回登录页
         */
        function backRegistAccountInforPage(){
             $state.go('selfRegistration');
        }

        /**
         *
         * @memberof setAccountInfoPwdController
         * @function nextGetAccountInfoPage
         * @description 跳转注册成功页面
         */
        function nextGetAccountInfoPage(){
            if (vm.setPwdForm.$valid) {  // 验证通过
                 var params = {
                    entryPswd: vm.model.entryPswd,
                    PswdTemp: vm.model.PswdTemp,
                    customerNickname: vm.model.customerNickname,
                    privacyIssues: vm.model.privacyIssues,
                    questionAnswer: vm.model.questionAnswer
                };

                var promise = UserService.register(params);

                promise.then(function(data) {
                    if (data.hostReturnCode == CONFIG.CORRECT_CODE) {
                        sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM,JSON.stringify(data));
                        $state.go('getAccountInfo');
                    }
                }).catch(function(error) {
                    CommonService.showError(error);
                });
            }

        }

    }

})();
