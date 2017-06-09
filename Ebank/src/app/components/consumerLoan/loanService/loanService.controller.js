(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('loanServiceController', loanServiceController);

    loanServiceController.$inject = ['$rootScope', 'UserService','CommonService','ModalService', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name loanServiceController
     * @param  {service} UserService      用户服务
     * @param  {service} CommonService    公用服务
     * @description
     * 贷款首页控制器
     */
    function loanServiceController($rootScope, UserService,CommonService, ModalService, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';
        vm.type = CONFIG.CONSTANT.CONSUMER_LOAN.LOANSERVICE;
        vm.setShoppingcar=setShoppingcar;
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

        init();//初始化方法
        function init(){
            productDetail();
        }

        /**
         * 查询产品信息方法
         * @memberof settlementShoppingCarController
         * @function productDetail
         * @description 查询产品信息
         */
        function productDetail(){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:""                                  // 服务名称
                },
                reqBody:{
                   productTpye: 'loanProduct'//产品标志
                }
            };
            var promise = UserService.getproductDetail(params);  //查询贷款产品列表
            promise.then(function(data) {

                vm.productDetail = data.respData;
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
         * @memberof loanServiceController
         * @function DoCtrlPagingAct
         * @description 翻页列表控制
         */
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = parseInt(page);
            vm.currentPage = parseInt(page);
            productDetail();
        }

        /**
         * 显示弹出录入页面
         * @memberof selfRegistrationController
         * @function showAgreement
         * @description 显示录入项
         */
        function setShoppingcar(product) {
            sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(product));
            ModalService.showModal({
                templateUrl: 'app/components/consumerLoan/loanApplication/loanApplication.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal',
                controller:'loanApplicationCarController',
                controllerAs:'vm'
            });
        }
    }

})();
