(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('myLoanController', myLoanController);

    myLoanController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name myLoanController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 我的贷款主页面控制器
     */
    function myLoanController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';
        vm.type = CONFIG.CONSTANT.CONSUMER_LOAN.MYLOAN;
        vm.tabIndex = 0;
        vm.items = [{
            value: '贷款申请'
        }, {
            value: '贷款申请'
        }, {
            value: '贷款申请'
        }, {
            value: '贷款申请'
        }];
        //method

        // vm.productname = $state.params['productname'];
        //初始化方法
        init();

        function init(){
            queryloanList();//查询贷款list
        }

        /**
         * 查询我的贷款信息方法
         * @memberof myLoanController
         * @function queryloanList
         * @description 查询我的贷款信息方法
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
                   custId: 'A00001'//客户号，后期从公共变量里取值
                }
            };
            var promise = UserService.queryloanList(params);  //查询我的贷款信息
            promise.then(function(data) {

                vm.loanList = data.respData;
                for (var i = 0; i < vm.loanList.length; i++) {
                    if(vm.loanList[i].loanflag=="01"){
                        vm.loanList[i].loanflag="正常";
                    }else if(vm.loanList[i].loanflag=="02"){
                        vm.loanList[i].loanflag="逾期";
                    }else if(vm.loanList[i].loanflag=="03"){
                        vm.loanList[i].loanflag="呆滞";
                    }else if(vm.loanList[i].loanflag=="04"){
                        vm.loanList[i].loanflag="呆账";
                    }else if(vm.loanList[i].loanflag=="05"){
                        vm.loanList[i].loanflag="未知";
                    }
                }

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
        /**
         * @memberof myLoanController
         * @function DoCtrlPagingAct
         * @description 翻页列表控制
         */
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = parseInt(page);
            vm.currentPage = parseInt(page);
            queryloanList();
        }
    }
})();
