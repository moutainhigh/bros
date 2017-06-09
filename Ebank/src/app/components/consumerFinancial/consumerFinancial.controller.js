(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('consumerFinancialController', consumerFinancialController);

    consumerFinancialController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name productdetailController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 理财页面控制器
     */
    function consumerFinancialController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';
        vm.financialproductcode = '0001';
        //初始化方法
        init();

        function init(){
            queryloanList();//查询贷款list
        }

        /**
         * 查询理财产品
         * @memberof consumerFinancialController
         * @function queryloanList
         * @description 查询理财产品方法
         */
        function queryloanList(){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:""                                  // 服务名称
                },
                reqBody:{
                   financialproductcode: vm.financialproductcode//理财模块码
                }
            };
            var promise = UserService.queryfinanciaList(params);  //查询我的贷款信息
            promise.then(function(data) {

                vm.financialList = data.respData;
                vm.pageTotal = '' + Math.ceil(parseInt(data.pageInfo['totalCount']) / parseInt(vm.pageSize));
                vm.page = {
                    'currentPage': vm.currentPage,
                    'page-size': vm.pageSize,
                    'total': vm.pageTotal
                };

            }).catch(function(error) {
                CommonService.showError(error);
            });
        }

    }
})();
