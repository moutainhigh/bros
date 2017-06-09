
/**
 * @name accountManagement
 * @description
 * 我的账户指令
 */
(function(){
    'use strict';

    angular
        .module('EBankProject')
        .directive('accountManagement', accountManagement);

     /**
     * @memberof directbank
     * @ngdoc directive
     * @name accountManagement
     * @description
     *
     *
     * @attr {object} product 账户信息
     * @example
     *   Usage:
     *    <account-management accountList="accountList" ></account-management>
     */

    function accountManagement($compile) {
        var directive = {
            restrict: 'E',
            scope:{
            	list: '=',
                shelfcodelist: '=',
                compare: '&'
            },
            templateUrl:'app/directives/accountManagementDir/accountManagementDir.html',
            controller: getSubAccountManageController,
            controllerAs: 'vm'
        };
        return directive;
        
        /**
         * 获取子账户信息控制器-getSubAccountManageController
         */
        function getSubAccountManageController($scope, UserService, CommonService,CONFIG){
            var vm = this;
            vm.getSubAccount = getSubAccount;                   // 获取子账户
        
            vm.SD0101 = CONFIG.CONSTANT.SHELFCODE.SD0101;       // 借记卡货架                      
            vm.SD0102 = CONFIG.CONSTANT.SHELFCODE.SD0102;       // 定期一本货架                      
            vm.SD0103 = CONFIG.CONSTANT.SHELFCODE.SD0103;       // 活期一本货架                      
            vm.SD0104 = CONFIG.CONSTANT.SHELFCODE.SD0104;       // 存单卡货架


            vm.ACCTYPE01 = CONFIG.CONSTANT.ACCTYPE.ACCTYPE01;                                          // 借记卡账户
            vm.ACCTYPE02 = CONFIG.CONSTANT.ACCTYPE.ACCTYPE02;                                          // 定期一本账户
            vm.ACCTYPE03 = CONFIG.CONSTANT.ACCTYPE.ACCTYPE03;                                          // 活期一本账户
            vm.ACCTYPE04 = CONFIG.CONSTANT.ACCTYPE.ACCTYPE04;                                          // 存单账户

            function getSubAccount(){
                debugger;
                var params = {
                    reqHead:{
                        flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                        tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                        stePcode:"01",                                  // 交易步骤
                        serviceName:""                                  // 服务名称
                    },
                    reqBody:{
                       
                    }
                };
                var promise = UserService.getSubAccountListService(params);
                  
                promise.then(function(data) {
                	vm.subAccountList = data.returnList;
                }).catch(function(error) {
                    CommonService.showError(error);
                });
            }
        }
    }

})();
