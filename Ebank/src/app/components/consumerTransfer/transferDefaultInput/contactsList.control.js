(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('contactsListController', contactsListController);

    contactsListController.$inject = ['$rootScope', '$scope', 'CommonService', 'UserService', '$state', 'CONFIG','ModalService'];

    /**
     * @memberOf directbank
     * @ngdoc controller
     * @name contactsListController
     * @description 联系人
     * @param {service} CommonService  系统公共服务
     * @param {service} userService    系统用户相关服务
     */
     function contactsListController($rootScope, $scope, CommonService, userService, $state, CONFIG, ModalService) {
        var vm = this;
        vm.model = {};
        vm.showPanel = true;
        vm.model['cardType'] = 'true';
        vm.contactsList = [];


        vm.selectContacts = selectContacts;

        init();

        /**
         * 页面初始化函数
         * @function init
         * @describe 获取银行列表、获取城市列表
         * @memberof contactsListController
         */
         function init() {

         getContactsList();

     }
        /**
         * 获取银行列表
         * @function getContactsList
         * @memberof contactsListController
         */
         function getContactsList() {
            var promise = CommonService.getContactsList();

            promise.then(function(data) {
                vm.contactsList = data.respData;
                $rootScope['contactsList'] = data.respData;

                return CommonService.getLocalBank();
            }).then(function(data) {
                vm.localBank = data;
            }).catch(function(error) {
                CommonService.showError(error);
            });
        }




        /**
         * 选择银行
         * @function selectContacts
         * @memberof contactsListController
         * @param  {string} code 银行代号
         * @return {object}      选择的银行信息
         */
         function selectContacts(code) {

            vm.showPanel = !vm.showPanel;
            if (code) {
                 var result = vm.contactsList.filter(function(item) {
                    if (item.bankCode === code) {
                        return true;
                    }
                });

                vm.selectContacts = result[0];
                $rootScope.$broadcast('contactsMessage', vm.selectContacts);                      //广播所选取的银行信息，供输入页面使用
                ModalService.closeModal();                                                  //关闭选择银行页面
            }
        }



    }
})();
