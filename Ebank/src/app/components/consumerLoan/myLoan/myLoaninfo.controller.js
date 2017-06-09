(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('myLoaninfoController', myLoaninfoController);

    myLoaninfoController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name myLoaninfoController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 我的贷款信息控制器
     */
    function myLoaninfoController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;


        vm.loannum = $state.params['loannum'];

        init();//初始化

        function init(){
            queryloandetail();
        }


        /**根据贷款编号查询贷款信息
         * @memberof myLoaninfoController
         * @function queryloandetail
         * @description 根据贷款编号查询贷款信息
         */
         function queryloandetail() {
             var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:""                                  // 服务名称
                },
                reqBody:{
                   loannum: vm.loannum,//贷款编号
                    startdate: '',//查询开始日期，界面获取，没有输入默认为空
                    enddate: ''//查询结束日期，界面获取，没有输入默认为空
                }
            };
            var promise = UserService.queryloandetail(params);  //查询我的贷款信息
            promise.then(function(data) {

                vm.alldata = data;
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
