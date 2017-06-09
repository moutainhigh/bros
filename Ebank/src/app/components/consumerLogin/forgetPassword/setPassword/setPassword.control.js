(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('setPasswordController', setPasswordController);

    setPasswordController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name setPasswordController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function setPasswordController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm = this;
        vm.model = {};
        //method
        vm.tosetPasswordSucc = tosetPasswordSucc;             //设置密码
        vm.goBack = goBack;                                   //返回上一页
        //value

        init();

        // 初始化页面
        function init(){
            var previousStateData = localStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT);
            localStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT,previousStateData);
        }


        /**
         *
         * @memberof setPasswordController
         * @function tosetPasswordSucc
         * @description  密码设置成功后跳转
         */
         function tosetPasswordSucc() {

            if (vm.regForm.$valid) {
                var params = {
                    entryPswd: vm.model.entryPswd,
                    reentryPswd: vm.model.reentryPswd,
                    name: vm.model.name,
                    question: vm.model.question,
                    answer: vm.model.answer
                };

                var promise = UserService.forgetPassword(params);

                promise.then(function(data) {
                    if (data.returnCode == CONFIG.CORRECT_CODE) {
                        sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM,JSON.stringify(data));
                       $state.go('setPasswordSucc');
                    }
                }).catch(function(error) {
                    CommonService.showError(error);
                });
            }

        }



        /**
         *
         * @memberof setPasswordController
         * @function goBack
         * @description 返回上一页
         */
         function goBack() {

           $state.go('forgetPassword');

       }
   }
})();
