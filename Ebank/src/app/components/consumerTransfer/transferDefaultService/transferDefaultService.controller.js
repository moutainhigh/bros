(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('TransferDefaultServiceController', TransferDefaultServiceController);

    TransferDefaultServiceController.$inject = ['$rootScope', 'CommonService', 'CONFIG', '$scope', '$timeout','$state','UserService'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name TransferController
     * @param  {service} CommonService    公用服务
     * @param  {service} $state           通用服务
     * @description
     * 首页控制器
     */
     function TransferDefaultServiceController($rootScope, CommonService, CONFIG, $scope, $timeout,$state,UserService) {
        var vm = this;
        vm.model = {};

        //method
        vm.backTransferDefaultInput = backTransferDefaultInput;        //  返回转账信息录入界面
        vm.totransferDefaultSucc = totransferDefaultSucc;               //发送转账交易
        vm.getMobileValidateCode = getMobileValidateCode;               //短信验证码


        init();

        // 初始化页面
        function init(){

         var previousStateData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
         if(previousStateData != null){

                vm.model.payaccount     = previousStateData.payaccount;//付款账号
                vm.model.payamount      = previousStateData.payamount;//付款金额
                vm.model.collectaccount = previousStateData.collectaccount;//收款账号
                vm.model.custName       = previousStateData.custName;//收款姓名
                vm.model.payaccountbank = previousStateData.payaccountbank;//收款账号
                vm.model.bankNo         = previousStateData.bankNo;//开户网点
                vm.model.mobile         = previousStateData.mobile;//短信通知手机号
                vm.model.transferuse    = previousStateData.transferuse;//转账用途
            }
        }





         /**
         * 返回转账页面
         * @memberof TransferDefaultServiceController
         * @function backTransferDefaultInput
         * @description 返回转账页面
         */
         function backTransferDefaultInput(){
            $state.go('transferDefaultInput');

        }
        /**
         * 发送转账页面
         * @memberof TransferDefaultServiceController
         * @function totransferDefaultSucc
         * @description 发送转账页面
         */

         function totransferDefaultSucc(){
            if (vm.transForm.$valid) {  // 验证通过
                 var params = {
                    payaccount: vm.model.payaccount,
                    payamount: vm.model.payamount,
                    collectaccount: vm.model.collectaccount,
                    custName: vm.model.custName,
                    payaccountbank: vm.model.payaccountbank,
                    bankNo: vm.model.bankNo,
                    mobile: vm.model.mobile,
                    transferuse: vm.model.transferuse

                };

                var promise = UserService.transfer(params);

                promise.then(function(data) {
                    if (data.returnCode == CONFIG.CORRECT_CODE) {
                        sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM,JSON.stringify(data));
                        $state.go('transferDefaultSucc');
                    }
                }).catch(function(error) {
                    CommonService.showError(error);
                });
            }

        }

        /**
         * @memberof TransferDefaultServiceController
         * @function getMobileValidateCode
         * @return {object}      短信验证码
         */
         function getMobileValidateCode() {
            vm.validateCode = true;





           /* if(vm.bankAcctCode) {
                userService.getMobileValidateCode({
                    mobileNo: vm.model.mobileNo,
                    vrfyTxCode: CONFIG.API.BIND_BANK_CARD_PHONE
                }).then(function(data) {
                    vm.validateCode = true;
                }).catch(function(error) {
                    vm.validateCode = false;
                    CommonService.showError(error);
                });
            } else {
                if(vm.regForm.txt_cardNo.$valid && vm.regForm.slt_provCode.$valid) {
                    userService.verifyBankcard({
                        bankCode: vm.selectedBank.bankCode,
                        provCode: vm.model.provCode,
                        bankAcct: vm.model.cardNo,
                        bankMobileNo: vm.model.mobileNo
                    }).then(function(data) {
                        vm.bankAcctCode = data.respData.bankAcctCode;
                        return userService.getMobileValidateCode({
                            mobileNo: vm.model.mobileNo,
                            vrfyTxCode: CONFIG.API.BIND_BANK_CARD_PHONE
                        });
                    }).then(function(data) {
                        vm.validateCode = true;
                    }).catch(function(error) {
                        vm.validateCode = false;
                        CommonService.showError(error);
                    });
                }
            }*/
        }
    }

})();
