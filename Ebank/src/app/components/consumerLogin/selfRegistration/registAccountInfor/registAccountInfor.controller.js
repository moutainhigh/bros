(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('selfRegistrationController', selfRegistrationController);

    selfRegistrationController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  selfRegistrationController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function selfRegistrationController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;                               // 返回登录
        vm.register = register;                                 // 提交用户注册信息方法
        vm.showAgreement = showAgreement;                       // 显示相关协议

        init();


        function init(){
            var previousStateData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            if(previousStateData != null){
                vm.model.accountNo = previousStateData.accountNo;
                vm.model.cardNumberType = previousStateData.cardNumberType;
                vm.model.cardPwd = previousStateData.cardPwd;
                vm.model.custName = previousStateData.custName;
                vm.model.idType = previousStateData.idType;
                vm.model.certNo = previousStateData.certNo;
                vm.model.iphone = previousStateData.iphone;
                vm.model.email = previousStateData.email;
            }
        }
        /**
         * 返回登录页
         * @memberof selfRegistrationController
         * @function backLogin
         * @description 返回登录页
         */
        function backLogin(){
            CommonService.toLogin();
        }


        /**
         * 提交用户注册信息方法
         * @memberof selfRegistrationController
         * @function register
         * @description 提交用户注册信息描述
         */
        function register() {
            var file = vm.regForm.file;
            var params = {
                type:"file"
            }
            FileService.fileUpload(file,params);
            return;

            if (vm.regForm.$valid) {  // 验证通过
                var params = {
                    accountNo: vm.model.accountNo,
                    cardNumberType: vm.model.cardNumberType,
                    cardPwd: vm.model.cardPwd,
                    custName: vm.model.custName,
                    idType: vm.model.idType,
                    certNo: vm.model.certNo,
                    iphone: vm.model.iphone,
                    email: vm.model.email,
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));
                $state.go('setAccountInfoPwd');
            }
        }

        /**
         * 显示相关协议方法
         * @memberof selfRegistrationController
         * @function showAgreement
         * @description 显示相关协议描述
         */
        function showAgreement() {
            ModalService.showModal({
                templateUrl: 'app/layout/modalBlock/registerAgreementModal/registerAgreement.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }
    }

})();
