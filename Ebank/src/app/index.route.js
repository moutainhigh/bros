(function() {
    'use strict';

    angular
        .module('EBankProject')
        .config(routerConfig);

    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider) {
        var cpUrl = 'app/components/';

        consumerLoginRoute();       //平台登录体系路由

        consumerAccountRoute();     //账户体系路由

        consumerTransferRoute();    //平台转账体系路由

        consumerAuthRoute();        //平台授权体系路由

        consumerBranchRoute();      //平台机构体系路由

        consumerCustomerRoute();    //平台客户体系路由

        consumerFinancialRoute();   //平台理财体系路由

        consumerNetbankRoute();     //平台理财体系路由

        consumerSecurityRoute();    //平台安全体系路由

        consumerTellerferRoute();   //平台柜员体系路由

        consumerCarRoute();         //购物车流程

        consumerLoanRoute();         //平台贷款体系路由



        //平台登录体系
        /**
         * url:路由名称
         * templateUrl 路由URL
         * controller 控制器
         * loginState true--登录 false--非登入
         * simplePage true--显示hideTopMenu头部 false--显示hideFooter头部
         */
        function consumerLoginRoute() {
             // Index页
            $stateProvider.state('index', {
                url: '/index',
                templateUrl: cpUrl + 'home/home.html',
                controller: 'HomeController as vm',
                loginState: true,
                simplePage: false
            })

            // login登录
            .state('login', {
                url: '/login',
                templateUrl: cpUrl + 'consumerLogin/login/login.html',
                controller: 'LoginController as vm',
                loginState: false,
                simplePage: true
            })

            // selfRegistration 自助注册-填写账户信息
            .state('selfRegistration', {
                url: '/selfRegistration',
                templateUrl: cpUrl + 'consumerLogin/selfRegistration/registAccountInfor/registAccountInfor.html',
                controller: 'selfRegistrationController as vm',
                loginState: false,
                simplePage: true
            })

            // selfRegistration 自助注册-设置密码
            .state('setAccountInfoPwd', {
                url: '/setAccountInfoPwd',
                templateUrl: cpUrl + 'consumerLogin/selfRegistration/setAccountInfoPwd/setAccountInfoPwd.html',
                controller: 'setAccountInfoPwdController as vm',
                loginState: false,
                simplePage: true
            })

             // selfRegistration 自助注册-注册成功
            .state('getAccountInfo', {
                url: '/getAccountInfo',
                templateUrl: cpUrl + 'consumerLogin/selfRegistration/getAccountInfo/getAccountInfo.html',
                controller: 'getAccountInfoController as vm',
                loginState: false,
                simplePage: true
            })

            // forgetPassword 密码找回
            .state('forgetPassword', {
                url: '/forgetPassword',
                templateUrl: cpUrl + 'consumerLogin/forgetPassword/forgetPassword/forgetPassword.html',
                controller: 'forgetPasswordController as vm',
                loginState: false,
                simplePage: true
            })

             // setPassword 密码找回
            .state('setPassword', {
                url: '/setPassword',
                templateUrl: cpUrl + 'consumerLogin/forgetPassword/setPassword/setPassword.html',
                controller: 'setPasswordController as vm',
                loginState: false,
                simplePage: true
            })
            // setPassword 密码找回
            .state('setPasswordSucc', {
                url: '/setPasswordSucc',
                templateUrl: cpUrl + 'consumerLogin/forgetPassword/setPasswordSucc/setPasswordSucc.html',
                controller: 'setPasswordSuccController as vm',
                loginState: false,
                simplePage: true
            })
        }

        //账户体系路由
        function consumerAccountRoute(){
            // accountManagement 账户首页
            $stateProvider.state('accountManagement', {
                url: '/accountManagement',
                templateUrl: cpUrl + 'consumerAccount/accountManagement/accountManagement.html',
                controller: 'accountManagementController as vm',
                loginState: true,
                simplePage: false

            })

            

            // 产品视图
            .state('productView', {
                url: '/productView',
                templateUrl: cpUrl + 'consumerAccount/productView/productView.html',
                controller: 'productViewController as vm',
                loginState: false,
                simplePage: true
            })

            // fixedTransfer 活转定录入
            .state('fixedTransfer', {
                url: '/fixedTransfer',
                templateUrl: cpUrl + 'consumerAccount/fixedTransfer/input/fixedTransfer.html',
                controller: 'fixedTransferController as vm',
                loginState: false,
                simplePage: true
            })


            // fixedTransferConfirm 活转定确认
            .state('fixedTransferConfirm', {
                url: '/fixedTransferConfirm',
                templateUrl: cpUrl + 'consumerAccount/fixedTransfer/confirm/fixedTransferConfirm.html',
                controller: 'fixedTransferConfrmController as vm',
                loginState: false,
                simplePage: true
            })

             // fixedTransferSuccess 活转定成功
            .state('fixedTransferSuccess', {
                url: '/fixedTransferSuccess',
                templateUrl: cpUrl + 'consumerAccount/fixedTransfer/success/fixedTransferSuccess.html',
                controller: 'fixedTransferSuccessController as vm',
                loginState: false,
                simplePage: true
            })

        }


        //平台转账体系路由
        function consumerTransferRoute(){
             // transferService 转账首页
            $stateProvider.state('transferService', {
                url: '/transferService',
                templateUrl: cpUrl + 'consumerTransfer/transferService/transferService.html',
                controller: 'TransferController as vm',
                loginState: true,
                simplePage: false
            })

            // $stateProvider.state('transferDefaultService', {
            //     url: '/transferDefaultService',
            //     templateUrl: cpUrl + 'consumerTransfer/transferDefaultService/transferDefaultService.html',
            //     controller: 'TransferDefaultServiceController as vm',
            //     loginState: false,
            //     simplePage: false
            // })

             //默认转账 录入信息
            .state('transferDefaultInput', {
                url: '/transferDefaultInput',
                templateUrl: cpUrl + 'consumerTransfer/transferDefaultInput/transferDefaultInput.html',
                controller: 'TransferDefaultInputController as vm',
                loginState: true,
                simplePage: false
            })

            //默认确认转账
            .state('transferDefaultService', {
                url: '/transferDefaultService',
                templateUrl: cpUrl + 'consumerTransfer/transferDefaultService/transferDefaultService.html',
                controller: 'TransferDefaultServiceController as vm',
                loginState: true,
                simplePage: false
            })

            //转账状态页面
            .state('transferDefaultSucc', {
                url: '/transferDefaultSucc',
                templateUrl: cpUrl + 'consumerTransfer/transferDefaultSucc/transferDefaultSucc.html',
                controller: 'TransferDefaultSuccController as vm',
                loginState: true,
                simplePage: false
            })
        }


        //平台授权体系路由
        function consumerAuthRoute(){

        }

        //平台机构体系路由
        function consumerBranchRoute(){

        }

        //平台客户体系路由
        function consumerCustomerRoute(){
            //客户总览基本信息和客户关系信息
            $stateProvider.state('personOverview', {
                url: '/personOverview',
                templateUrl: cpUrl + 'personOverview/personOverview.html',
                controller: 'personOverviewController as vm',
                loginState: true,
                simplePage: false
            })
        }

        //平台理财体系路由
        function consumerFinancialRoute(){

            $stateProvider.state('consumerFinancial', {
                url: '/consumerFinancial',
                templateUrl: cpUrl + 'consumerFinancial/consumerFinancial.html',
                controller: 'consumerFinancialController as vm',
                loginState: true,
                simplePage: false
            })
        }

        //平台理财体系路由
        function consumerNetbankRoute(){

        }

        //平台安全体系路由
        function consumerSecurityRoute(){

        }

        //平台柜员体系路由
        function consumerTellerferRoute(){

        }

        //购物车流程
        function consumerCarRoute(){
            // shoppingCar 购物车
            $stateProvider.state('shoppingCar', {
                url: '/shoppingCar',
                templateUrl: cpUrl + 'consumerCar/shoppingCar/shoppingCar.html',
                controller: 'shoppingCarController as vm',
                loginState: true,
                simplePage: false

            })
            // settlementShoppingCar 支付页面
            .state('settlementShoppingCar', {
                 url: '/settlementShoppingCar',
                templateUrl: cpUrl + 'consumerCar/settlementShoppingCar/settlementShoppingCar.html',
                controller: 'settlementShoppingCarController as vm',
                loginState: true,
                simplePage: false
            })
            // demand 需求
            .state('demand', {
                 url: '/demand',
                templateUrl: cpUrl + 'consumerCar/demand/demand.html',
                controller: 'demandController as vm',
                loginState: true,
                simplePage: false
            })
            // myOrder 我的订单
            .state('myOrder', {
                 url: '/myOrder',
                templateUrl: cpUrl + 'consumerCar/myOrder/myOrder.html',
                controller: 'myOrderController as vm',
                loginState: true,
                simplePage: false
            })
            // quotedPrice 报价
            .state('quotedPrice', {
                 url: '/quotedPrice',
                templateUrl: cpUrl + 'consumerCar/quotedPrice/quotedPrice.html',
                controller: 'quotedPriceController as vm',
                loginState: true,
                simplePage: false
            })
             // quotedPriceDetail 报价详情
            .state('quotedPriceDetail', {
                 url: '/quotedPriceDetail',
                templateUrl: cpUrl +'consumerCar/quotedPriceDetail/quotedPriceDetail.html',
                controller: 'quotedPriceDetailController as vm',
                loginState: true,
                simplePage: false
            })
             // productdetail 产品详情
            .state('productdetail', {
                 url: '/productdetail/:productId',
                templateUrl: cpUrl + 'consumerFinancial/productdetails/productdetail.html',
                controller: 'productdetailController as vm',
                loginState: true,
                simplePage: false
            })

            // 定活宝产品调研项
            .state('templateProduct', {
                 url: '/templateProduct',
                templateUrl: cpUrl + 'templatePage/fixedClown/fixedClown.html',
                controller: 'fixedClownController as vm',
                loginState: true,
                simplePage: false
            })
            // 促销列表展示
            .state('discoverPromotions', {
                 url: '/discoverPromotions',
                templateUrl: cpUrl + 'consumerTransfer/transferService/discoverPromotionsList.html',
                controller: 'discoverPromotionsListController as vm',
                params:{
                    discoverPromotionsListFlag:null
                },
                loginState: true,
                simplePage: false
            })
        }

        //贷款体系路由
        function consumerLoanRoute(){
            // loanService 贷款首页
            $stateProvider.state('loanService', {
                url: '/loan',
                templateUrl: cpUrl + 'consumerLoan/loanService/loanService.html',
                controller: 'loanServiceController as vm',
                loginState: true,
                simplePage: false

            })
             // loanapplication 贷款产品加入购物车
            .state('loanapplication', {
                 url: '/loanapplication/:productname',
                templateUrl: cpUrl + 'consumerLoan/loanApplication/loanApplication.html',
                controller: 'loanApplicationCarController as vm',
                loginState: true,
                simplePage: false
            })
            // mayloan 我的贷款主页面
            .state('mayloan', {
                 url: '/mayloan',
                templateUrl: cpUrl + 'consumerLoan/myLoan/myLoan.html',
                controller: 'myLoanController as vm',
                loginState: true,
                simplePage: false
            })
            // myloaninfo 我的贷款贷款信息界面
            .state('myloaninfo', {
                 url: '/myloaninfo/:loannum',
                templateUrl: cpUrl + 'consumerLoan/myLoan/myLoaninfo.html',
                controller: 'myLoaninfoController as vm',
                loginState: true,
                simplePage: false
            })
            // myloanquery 我的贷款还款计划
            .state('myloanquery', {
                 url: '/myloanquery/:loannum/:loanstart/:loanend',
                templateUrl: cpUrl + 'consumerLoan/myLoan/myLoanquery.html',
                controller: 'myLoanqueryController as vm',
                loginState: true,
                simplePage: false
            })
            // loanpersondata 我的贷款--个人贷款资料维护
            .state('loanpersondata', {
                 url: '/loanpersondata',
                templateUrl: cpUrl + 'consumerLoan/loanpersonData/loanpersonData.html',
                controller: 'myLoaninfoController as vm',
                loginState: true,
                simplePage: false
            })
        }

        $urlRouterProvider.otherwise('/login');
    }

})();
