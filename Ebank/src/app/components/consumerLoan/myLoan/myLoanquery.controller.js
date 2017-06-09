(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('myLoanqueryController', myLoanqueryController);

    myLoanqueryController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name myLoanqueryController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 我的贷款信息查询控制器
     */
    function myLoanqueryController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';
        vm.loannum = $state.params['loannum'];
        vm.loanstart = $state.params['loanstart'];
        vm.loanend = $state.params['loanend'];
        vm.startserch=startserch;
        init();//初始化

        function init(){
            startserch2();
        }

        /**日期不为空时，判断日期大小
         * @memberof myLoanqueryController
         * @function startserch
         * @description 日期不为空时，判断日期大小
         */
        function startserch(){
            alert(vm.startDate);
            if (vm.loanstart) {}
        }
        /**根据贷款编号和日期查询贷款信息
         * @memberof myLoanqueryController
         * @function startserch
         * @description 根据贷款编号和日期查询贷款信息
         */
         function startserch2() {
            var params = {
                loannum: vm.loannum,//贷款编号
                startdate: vm.startDate,//查询开始日期，界面获取，没有输入默认为空
                enddate: vm.endDate//查询结束日期，界面获取，没有输入默认为空
            };
            var promise = UserService.queryloandetail(params);  //查询我的贷款信息
            promise.then(function(data) {

                vm.alldata = data;
                vm.loanqueryList = data.respData;
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
