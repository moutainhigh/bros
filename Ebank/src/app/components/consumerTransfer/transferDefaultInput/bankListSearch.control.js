(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('bankListSearchController', bankListSearchController);

    bankListSearchController.$inject = ['$rootScope', '$scope', 'CommonService', 'UserService', '$state', 'CONFIG','ModalService'];

    /**
     * @memberOf directbank
     * @ngdoc controller
     * @name bankListSearchController
     * @description 银行卡
     * @param {service} CommonService  系统公共服务
     * @param {service} userService    系统用户相关服务
     */
     function bankListSearchController($rootScope, $scope, CommonService, userService, $state, CONFIG, ModalService) {
        var vm = this;
        vm.model = {};
        vm.showPanel = true;
        vm.showMore = true;
        vm.validateCode = false;                // 他行卡需要获取验证码
        vm.showLimit = 5;
        vm.model['cardType'] = 'true';
        vm.bankList = [];

        vm.showMore = showMore;
        vm.selectBank = selectBank;

        init();

        /**
         * 页面初始化函数
         * @function init
         * @describe 获取银行列表、获取城市列表
         * @memberof bankListSearchController
         */
         function init() {
            getBanklist();
        }
        /**
         * 获取银行列表
         * @function getBanklist
         * @memberof bankListSearchController
         */
         function getBanklist() {
            var promise = CommonService.getBankList();

            promise.then(function(data) {
                vm.bankList = data.respData;
                $rootScope['bankList'] = data.respData;

                return CommonService.getLocalBank();
            }).then(function(data) {
                vm.localBank = data;
            }).catch(function(error) {
                CommonService.showError(error);
            });
        }



        /**
         * 显示更多银行
         * @function showMore
         * @memberof bankListSearchController
         */
         function showMore() {
            vm.showLimit = 200;
            vm.showMore = false;
        }

        /**
         * 选择银行
         * @function selectBank
         * @memberof bankListSearchController
         * @param  {string} code 银行代号
         * @return {object}      选择的银行信息
         */
         function selectBank(code) {

            vm.showPanel = !vm.showPanel;
            if (code) {
                var result = vm.bankList.filter(function(item) {
                    if (item.bankCode === code) {
                        return true;
                    }
                });

                vm.selectedBank = result[0];
                $rootScope.$broadcast('bankAddressMessage', vm.selectedBank);                      //广播所选取的银行信息，供输入页面使用
                ModalService.closeModal();                                                  //关闭选择银行页面
            }
        }

    }
})();
