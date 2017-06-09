(function() {
    'use strict';

    angular
        .module('EBankProject', ['ngAnimate', 'ngCookies', 'ngTouch',
            'ngSanitize', 'ngMessages', 'ngAria', 'ngResource', 'ui.router',
            'ui.bootstrap', 'toastr', 'ajoslin.promise-tracker', 'angular-flash.service',
            'angular-flash.flash-alert-directive', 'highcharts-ng',
            'angular-loading-bar', 'angular-svg-round-progress', 'ngFileUpload']);

    window.Mock.mockjax(angular.module('EBankProject'));
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('setAccountInfoPwdController', setAccountInfoPwdController);

    setAccountInfoPwdController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  setAccountInfoPwdController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function setAccountInfoPwdController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG) {
        var vm = this;
        vm.model = {};

        //method
        vm.backRegistAccountInforPage = backRegistAccountInforPage;               // 返回用户信息填写
        vm.nextGetAccountInfoPage = nextGetAccountInfoPage;                       // 跳转注册成功页面

        init();

        // 初始化页面
        function init(){
            var previousStateData = localStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT);
            localStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT,previousStateData);
        }

        /**
         *
         * @memberof setAccountInfoPwdController
         * @function backRegistAccountInforPage
         * @description 返回登录页
         */
        function backRegistAccountInforPage(){
             $state.go('selfRegistration');
        }

        /**
         *
         * @memberof setAccountInfoPwdController
         * @function nextGetAccountInfoPage
         * @description 跳转注册成功页面
         */
        function nextGetAccountInfoPage(){
            if (vm.setPwdForm.$valid) {  // 验证通过
                 var params = {
                    entryPswd: vm.model.entryPswd,
                    PswdTemp: vm.model.PswdTemp,
                    customerNickname: vm.model.customerNickname,
                    privacyIssues: vm.model.privacyIssues,
                    questionAnswer: vm.model.questionAnswer
                };

                var promise = UserService.register(params);

                promise.then(function(data) {
                    if (data.hostReturnCode == CONFIG.CORRECT_CODE) {
                        sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM,JSON.stringify(data));
                        $state.go('getAccountInfo');
                    }
                }).catch(function(error) {
                    CommonService.showError(error);
                });
            }

        }

    }

})();

/**
 * @name registerProgress
 * @description
 * 注册流程状态
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('registtielt', registtielt);

    function registtielt() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerLogin/selfRegistration/registTitile/registTitle.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            var progressLength = $('.register').children().length;
            var activeArray = [],
                finishArray = [];
            for (var i = 0; i < progressLength; i++) {
                activeArray.push(false);
                if (i + 1 < scope.flow) {
                    finishArray[i] = true;
                } else {
                    finishArray[i] = false;
                }
            }
            activeArray[scope.flow - 1] = true;
            scope.finishs = finishArray;
            scope.actives = activeArray;
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('selfRegistrationController', selfRegistrationController);

    selfRegistrationController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  selfRegistrationController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function selfRegistrationController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;                               // 返回登录
        vm.register = register;                                 // 提交用户注册信息方法
        vm.showAgreement = showAgreement;                       // 显示相关协议

        init();


        function init(){
            var previousStateData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            if(previousStateData != null){
                vm.model.accountNo = previousStateData.accountNo;
                vm.model.cardNumberType = previousStateData.cardNumberType;
                vm.model.cardPwd = previousStateData.cardPwd;
                vm.model.custName = previousStateData.custName;
                vm.model.idType = previousStateData.idType;
                vm.model.certNo = previousStateData.certNo;
                vm.model.iphone = previousStateData.iphone;
                vm.model.email = previousStateData.email;
            }
        }
        /**
         * 返回登录页
         * @memberof selfRegistrationController
         * @function backLogin
         * @description 返回登录页
         */
        function backLogin(){
            CommonService.toLogin();
        }


        /**
         * 提交用户注册信息方法
         * @memberof selfRegistrationController
         * @function register
         * @description 提交用户注册信息描述
         */
        function register() {
            var file = vm.regForm.file;
            var params = {
                type:"file"
            }
            FileService.fileUpload(file,params);
            return;

            if (vm.regForm.$valid) {  // 验证通过
                var params = {
                    accountNo: vm.model.accountNo,
                    cardNumberType: vm.model.cardNumberType,
                    cardPwd: vm.model.cardPwd,
                    custName: vm.model.custName,
                    idType: vm.model.idType,
                    certNo: vm.model.certNo,
                    iphone: vm.model.iphone,
                    email: vm.model.email,
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));
                $state.go('setAccountInfoPwd');
            }
        }

        /**
         * 显示相关协议方法
         * @memberof selfRegistrationController
         * @function showAgreement
         * @description 显示相关协议描述
         */
        function showAgreement() {
            ModalService.showModal({
                templateUrl: 'app/layout/modalBlock/registerAgreementModal/registerAgreement.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('getAccountInfoController', getAccountInfoController);

    getAccountInfoController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  getAccountInfoController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function getAccountInfoController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;               // 返回登录页

        init();

        function init(){
            var confirmData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM));
            if(confirmData != null){
                vm.model.custName = confirmData.custName;
            }
        }

        /**
         * 返回登录页
         * @memberof selfRegistrationController
         * @function backLogin
         * @description 返回登录页
         */
        function backLogin(){
            //$state.go('setAccountInfoPwd');
            CommonService.toLogin();
        }
    }

})();

(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('setPasswordSuccController', setPasswordSuccController);

    setPasswordSuccController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name setPasswordSuccController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function setPasswordSuccController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm = this;

        //method
        vm.backLogin = backLogin; //返回登录页

        //value

        vm.model = {};

        init();

        function init(){
            var confirmData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM));
            if(confirmData != null){
                vm.model.custName = confirmData.custName;
            }
        }



        /**
         * 返回登录页
         * @memberof selfRegistrationController
         * @function backLogin
         * @description 返回登录页
         */
         function backLogin(){
            //$state.go('setAccountInfoPwd');
            CommonService.toLogin();
        }

    }
})();

(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('setPasswordController', setPasswordController);

    setPasswordController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name setPasswordController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function setPasswordController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm = this;
        vm.model = {};
        //method
        vm.tosetPasswordSucc = tosetPasswordSucc;             //设置密码
        vm.goBack = goBack;                                   //返回上一页
        //value

        init();

        // 初始化页面
        function init(){
            var previousStateData = localStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT);
            localStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT,previousStateData);
        }


        /**
         *
         * @memberof setPasswordController
         * @function tosetPasswordSucc
         * @description  密码设置成功后跳转
         */
         function tosetPasswordSucc() {

            if (vm.regForm.$valid) {
                var params = {
                    entryPswd: vm.model.entryPswd,
                    reentryPswd: vm.model.reentryPswd,
                    name: vm.model.name,
                    question: vm.model.question,
                    answer: vm.model.answer
                };

                var promise = UserService.forgetPassword(params);

                promise.then(function(data) {
                    if (data.returnCode == CONFIG.CORRECT_CODE) {
                        sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM,JSON.stringify(data));
                       $state.go('setPasswordSucc');
                    }
                }).catch(function(error) {
                    CommonService.showError(error);
                });
            }

        }



        /**
         *
         * @memberof setPasswordController
         * @function goBack
         * @description 返回上一页
         */
         function goBack() {

           $state.go('forgetPassword');

       }
   }
})();

/**
 * @name forgetPasswordstateProgress
 * @description
 * 忘记吗密码流程状态
 */
 (function() {
    'use strict';

    angular.module('EBankProject').directive('forgetpasswordstate', forgetpasswordstate);

    function forgetpasswordstate() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerLogin/forgetPassword/forgetPasswordState/forgetPasswordState.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

            var progressLength = $('.forgetpassword').children().length;
            var activeArray = [],
            finishArray = [];
            for (var i = 0; i < progressLength; i++) {
                activeArray.push(false);
                if (i + 1 < scope.flow) {
                    finishArray[i] = true;
                } else {
                    finishArray[i] = false;
                }
            }
            activeArray[scope.flow - 1] = true;
            scope.finishs = finishArray;
            scope.actives = activeArray;
        }
    }

})();

(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('forgetPasswordController', forgetPasswordController);

    forgetPasswordController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name forgetPasswordController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function forgetPasswordController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm                = this;
        vm.model              = {};
        //method
        vm.toSetLoginPassword = toSetLoginPassword;             // 提交密码重置验证信息
        vm.backLogin          = backLogin;                               // 返回登录

        //value

        init();


        function init(){
            var previousStateData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            if(previousStateData != null){
                vm.model.account        = previousStateData.account;
                vm.model.cardtype       = previousStateData.cardtype;
                vm.model.cardpassword   = previousStateData.cardpassword;
                vm.model.custName       = previousStateData.custName;
                vm.model.idcardtype     = previousStateData.idcardtype;
                vm.model.certNo         = previousStateData.certNo;
                vm.model.moblie         = previousStateData.moblie;
                vm.model.email          = previousStateData.email;

            }
        }


        /**
         * 提交密码重置验证信息方法
         * @memberof ResetPasswordController
         * @function toSetLoginPassword
         * @description 提交密码重置验证信息描述
         */
         function toSetLoginPassword() {

          if (vm.regForm.$valid) {
             var params = {
                    account: vm.model.account,
                    cardtype: vm.model.cardtype,
                    cardpassword: vm.model.cardpassword,
                    custName: vm.model.custName,
                    idcardtype: vm.model.idcardtype,
                    certNo: vm.model.certNo,
                    moblie: vm.model.moblie,
                    email: vm.model.email,
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));
           $state.go('setPassword');
       }
   }

      /**
         * 返回登录页
         * @memberof selfRegistrationController
         * @function backLogin
         * @description 返回登录页
         */
         function backLogin(){
            //$state.go('setAccountInfoPwd');
            CommonService.toLogin();
        }
    }
})();

/**
 * @name registerProgress
 * @description
 * 活转定流程
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('rfixedtransfertitle', rfixedTransferTitleDirective);

    function rfixedTransferTitleDirective() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerAccount/fixedTransfer/title/fixedTransferTitle.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            var progressLength = $('.register').children().length;
            var activeArray = [],
                finishArray = [];
            for (var i = 0; i < progressLength; i++) {
                activeArray.push(false);
                if (i + 1 < scope.flow) {
                    finishArray[i] = true;
                } else {
                    finishArray[i] = false;
                }
            }
            activeArray[scope.flow - 1] = true;
            scope.finishs = finishArray;
            scope.actives = activeArray;
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedTransferSuccessController', fixedTransferSuccessController);

    fixedTransferSuccessController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedTransferSuccessController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function fixedTransferSuccessController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;               // 返回登录页

        init();

        function init(){
            
        }

        /**
         * 返回登录页
         * @memberof selfRegistrationController
         * @function backLogin
         * @description 返回登录页
         */
        function backLogin(){
            //$state.go('setAccountInfoPwd');
            CommonService.toLogin();
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedTransferController', fixedTransferController);

    fixedTransferController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedTransferController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function fixedTransferController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;                               // 返回登录
        vm.fixedTranserConfirm = fixedTranserConfirm;           // 跳转到确认页面

        init();


        function init(){

        }

        /**
         * 跳转到确认页面
         * @memberof fixedTransferController
         * @function fixedTranserConfirm
         * @description 返回登录页
         */ 
        function fixedTranserConfirm(){
            $state.go('fixedTransferConfirm');
        }

        /**
         * 返回登录页
         * @memberof fixedTransferController
         * @function backLogin
         * @description 返回登录页
         */
        function backLogin(){
            CommonService.toLogin();
        }

    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedTransferConfrmController', fixedTransferConfrmController);

    fixedTransferConfrmController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedTransferConfrmController
     * @param  {service} UserService 用户服务
     * @description
     * 用户注册控制器
     */

    function fixedTransferConfrmController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.backLogin = backLogin;                               // 返回登录
        vm.fixedTranserSussess = fixedTranserSussess;           // 跳转到确认页面

        init();


        function init(){

        }

        /**
         * 跳转到确认页面
         * @memberof fixedTransferConfrmController
         * @function fixedTranserConfirm
         * @description 返回登录页
         */ 
        function fixedTranserSussess(){
            $state.go('fixedTransferSuccess');
        }

        /**
         * 返回录入页
         * @memberof fixedTransferConfrmController
         * @function backLogin
         * @description 返回录入页
         */
        function backLogin(){
            $state.go('fixedTransfer');
        }

    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('ModalTemplateController', ModalTemplateController);

    ModalTemplateController.$inject = ['ModalService'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name ModalTemplateController
     * @param  {service} UserService       用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 用户登录控制器
     */
    function ModalTemplateController(ModalService) {
        var vm = this;

        vm.close = close;

        function close() {
            ModalService.closeModal();
        }
    }
})();

/**
 * @name registerProgress
 * @description
 * 推荐产品、浏览记录状态
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('browsehistory', browsehistory);
    browsehistory.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies'];

    function browsehistory(UserService, CommonService, $state, $rootScope, $cookies) {
        var directive = {
            restrict: 'EA',
            templateUrl: 'app/layout/modalBlock/browseHistory/browseHistory.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope,el,attrs) {

        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('browseHistoryController', browseHistoryController);

    browseHistoryController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ModalService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name browseHistoryController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 产品推荐，浏览记录
     */
    function browseHistoryController($rootScope, UserService, CommonService,ModalService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
        vm.goproductdetail = goproductdetail;
        vm.recommendMorePro = recommendMorePro;//更多推荐产品
        vm.imgMouseover = imgMouseover;
        vm.imgMouseLeave = imgMouseLeave;
        init();  //初始化
        /**初始化方法
         * @memberof browseHistoryController
         * @function init
         * @description 初始化
         */
        function init(){
        	// 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            if (JSON.parse(sessionStorage.getItem('discoverHotList')==null)) {
                queryDiscoverHot();
            }else{
                vm.productIdList = JSON.parse(sessionStorage.getItem('discoverHotList'));
                var j;
                if (vm.productIdList.length>2) {
                    j=2
                    vm.recommendMore = "0";
                }else{
                    j = vm.productIdList.length;
                    vm.recommendMore = "1";
                }
                var n=0;
                vm.homeShowProductIdList = [];
                for (var m = 0; m < j; m++) {
                        vm.homeShowProductIdList[n] = vm.productIdList[m];
                        n++;
                    }
            }
        }
        function imgMouseover(){
            vm.mouseFlag = true;
        }
        function imgMouseLeave(){
            vm.mouseFlag = false;
        }
        /**查询热销信息
         * @memberof browseHistoryController
         * @function queryDiscoverHot
         * @description 查询热销信息
         */
        function queryDiscoverHot(){
            var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryDiscoverHotAction" // 服务名称
                },
                reqBody: {
                    productStoreId: vm.user.productStoreId, //  店铺标识
                    goodStatus: "00",                            //  商品状态
                    goodsType: "02",                             //  商品类型
                    goodsFlag: "00"                              //  上下架标志
                }
            };
            var promise = UserService.queryDiscoverHot(params);

            promise.then(function(data) {
                if (data.rspHead.returnCode=="00000000") {
                    vm.productIdList = data.rspBody.discoverHotList;
                    if (vm.productIdList==null || vm.productIdList==undefined) {
                        return;
                    }
                    for (var i = 0; i < vm.productIdList.length; i++) {
                        vm.productIdList[i].goodsName = vm.productIdList[i].productName;
                        vm.productIdList[i].goodsCode = vm.productIdList[i].productId;
                        vm.productIdList[i].configOptionName = vm.productIdList[i].productConfigOptionList[0].configOptionName;
                        vm.productIdList[i].configOptionId = vm.productIdList[i].productConfigOptionList[0].configOptionId;
                        vm.productIdList[i].configOptionPrice = vm.productIdList[i].productConfigOptionList[0].configOptionPrice;
                        vm.productIdList[i].parentCode = vm.productIdList[i].productCategoryList[0].rootProductCategoryId;

                        vm.productIdList[i].templateUrl = "templateProduct";//默认产品调研项
                        vm.productIdList[i].prdTypeCode = vm.productIdList[i].productCategoryList[0].parentProductCategoryId;
                        vm.productIdList[i].sunPrice = (parseInt(vm.productIdList[i].configOptionPrice) + parseInt(vm.productIdList[i].price)).toFixed(2);
                    }
                    sessionStorage.setItem("discoverHotList", JSON.stringify(vm.productIdList));
                    var j;
                    if (vm.productIdList.length>2) {
                        j=2;
                        vm.recommendMore = "0";
                    }else{
                        j = vm.productIdList.length;
                        vm.recommendMore = "1";
                    }
                    var n=0;
                    vm.homeShowProductIdList = [];
                    for (var m = 0; m < j; m++) {
                        vm.homeShowProductIdList[n] = vm.productIdList[m];
                        n++;
                    }
                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });
        }
        /**更多产品
         * @memberof browseHistoryController
         * @function recommendMorePro
         * @description 更多产品
         */
        function recommendMorePro(){
            $state.go('discoverPromotions',{discoverPromotionsListFlag:'1'});
        }
        /**查看产品详情
         * @memberof browseHistoryController
         * @function goproductdetail
         * @description 查看产品详情
         */
        function goproductdetail(product){
            sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(product));
            $state.go('productdetail');
        }

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('shoppingtwoCardirson', shoppingtwoCardirson);

    function shoppingtwoCardirson() {

        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/shoppingCar/shoppingcarson2/shoppingCardir2.html'
        };

        return directive;
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('shoppingcardirController', shoppingcardirController);

    shoppingcardirController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name shoppingcardirController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 优惠组合控制器
     */
    function shoppingcardirController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {

        var vm = this;
        vm.checkboxclick = checkboxclick;
        vm.closetable = closetable;
        vm.addpaycarlist = addpaycarlist;
        vm.allpice = 0;
        vm.allpiceyh = 0;
        vm.allpicejs = 0;
        vm.productname = "";
        //初始化方法
        init();

        function init(){
            showchushihua();//初始化
        }
         /**
         * 选中事件
         * @memberof shoppingcardirController
         * @function checkboxclick
         * @description 选中事件
         */
        function checkboxclick(o){

            if(angular.element(document.getElementById(o.productCarList.productCode+1))[0].checked){
                vm.allpice += Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value);
                vm.a = Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value);
                vm.productname += o.productCarList.productname+"  ";
            }else{
                if (vm.a == Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value)) {
                    vm.allpice = vm.allpice - Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value);
                }else{
                    vm.allpice = vm.allpice-vm.a;
                }
            }

            vm.allpiceyh = vm.allpice * 0.90;
            vm.allpicejs = vm.allpice - vm.allpiceyh;
        }
        /**
         * 点击隐藏/显示推荐产品
         * @memberof shoppingcardirController
         * @function closetable
         * @description 点击隐藏/显示推荐产品
         */
        function closetable(){
            $scope.tjdetail.show = !$scope.tjdetail.show;
        }
         /**
         * 产品详情初始化
         * @memberof shoppingcardirController
         * @function showchushihua
         * @description 初始化
         */
        function showchushihua(){
            $scope.tjdetail={
                show:true
            };
        }
        /**
         * @memberof sideBarController
         * @param compareMessage2
         * @description 接收广播信息方法
         */
        $scope.$on('compareMessage2', function(event, data) {
            vm.products = data;
        });
        /**
         * 推荐组合确认购买提交事件
         * @memberof shoppingcardirController
         * @function addpaycarlist
         * @description 推荐组合确认购买提交事件
         */
        vm.dataCompare = [];
        function addpaycarlist(){
             var product={
                prodCode:"优惠组合",
                bankName:vm.products[0].name,
                prodName:vm.productname,
                bankBalance:Number(vm.products[0].pice)+Number(vm.allpiceyh)
            }
            vm.dataCompare.splice(0, 0, product);
            $rootScope.$broadcast('compareMessage', vm.dataCompare);
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('shoppingcardirson', shoppingcardirson);

    function shoppingcardirson() {

        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/shoppingCar/shoppingcarson/shoppingCardir.html'
        };

        return directive;
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('shoppingCarController', shoppingCarController);

    shoppingCarController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name shoppingCarController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 我的贷款主页面控制器
     */
    function shoppingCarController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
        vm.clicktype = clicktype;
        //初始化方法
        init();

        function init(){
            showchushihua();//初始化
        }

        /**
         * 查询商品组合产品
         * @memberof shoppingCarController
         * @function clicktype
         * @description 查询商品组合产品
         */
        function clicktype(productname){
            $scope.producttj.show = !$scope.producttj.show;
            var params = {
                productTpye: productname//产品标志
            };
            var promise = UserService.getproductDetail(params);  //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {

                vm.productCarList = data.respData;


            }).catch(function(error) {
                CommonService.showError(error);
            });
        }
         /**
         * 购物车结算界面推荐产品初始化
         * @memberof settlementShoppingCarController
         * @function showchushihua
         * @description 初始化
         */
        function showchushihua(){

            $scope.producttj={
                show:false
            };
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('notcompletedorderItem', notcompletedorderitem);

    function notcompletedorderitem() {
        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/myOrder/notcompletedorderitem/notcompletedorderitem.html',
            controller: getNotCompleteOrderDetailManageController,
            controllerAs: 'vm'
        };

        return directive;
              /**
         * 获取已完成订单详情-getCompleteOrderDetailManageController
         */
        function getNotCompleteOrderDetailManageController($rootScope, UserService, ModalService,CommonService,$state,$scope,CONFIG){
            var vm = this;
              vm.showDetailInfo = showDetailInfo;
             function showDetailInfo(partyId ,orderId) {
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrderDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: partyId,//
                   orderId: orderId  //
                }
            };

            var promise = UserService.getOrderDetail(params);

            promise.then(function(data) {
                vm.orderDetail = data.rspBody;
                 $rootScope.orderDetail =  vm.orderDetail;
                ModalService.showModal({
                    templateUrl: 'app/components/consumerCar/tradeOrderDetail/tradeOrderDetail.html',
                    windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'registerAgreementModal',
                    controller:'ModalTemplateController'
                });
            }).catch(function(error) {
                CommonService.showError(error);
            });
    
           }

         // 添加购物车
        function addRecordShopCart(){
           $state.go('templateProduct');
        }
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('completedorderItem', investItem);

    function investItem() {
        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/myOrder/completedorderitem/completedorderitem.html',
            controller: getCompleteOrderDetailManageController,
            controllerAs: 'vm'
        };

        return directive;
          /**
         * 获取已完成订单详情-getCompleteOrderDetailManageController
         */
        function getCompleteOrderDetailManageController($rootScope, UserService, ModalService,CommonService,$state,$scope,CONFIG){
            var vm = this;
           
            vm.addRecordShopCart = addRecordShopCart;               // 添加购物车方法
            vm.showDetailInfo = showDetailInfo;
            vm.getOrderDetail = getOrderDetail;
             
              /**
         * 我的订单-订单详情
         * @function searchTransList
         * @memberof myOrderController
         */
        function getOrderDetail(partyId,orderId){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrderDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: partyId,//
                   orderId: orderId  //
                }
            };

            var promise = UserService.getOrderDetail(params);

            promise.then(function(data) {
                vm.orderDetail = data.rspBody;
            }).catch(function(error) {
                CommonService.showError(error);
            });

        }
           function showDetailInfo(partyId ,orderId) {
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrderDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: partyId,//
                   orderId: orderId  //
                }
            };

            var promise = UserService.getOrderDetail(params);

            promise.then(function(data) {
                vm.orderDetail = data.rspBody;
                 $rootScope.orderDetail =  vm.orderDetail;
                ModalService.showModal({
                    templateUrl: 'app/components/consumerCar/tradeOrderDetail/tradeOrderDetail.html',
                    windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'registerAgreementModal',
                    controller:'ModalTemplateController'
                });
            }).catch(function(error) {
                CommonService.showError(error);
            });
    
           }

        function closeLoginModal() {
            // 未调用$modal.open()之前，不存在$scope.$close()。该处语义不明确，还需调整
            $scope.$close();
        }


        

        // 添加购物车
        function addRecordShopCart(){
           $state.go('templateProduct');
        }
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('myLoandir', myLoandir);

    function myLoandir() {

        var directive = {
            restrict : 'AE',
            scope: {
                product:'='
            },
            templateUrl: 'app/directives/consumerLoan/myLoan/myLoandir.html'
        };

        return directive;

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('loanServicedir', loanServicedir);

    function loanServicedir() {

        var directive = {
            restrict : 'AE',
            scope: {
                product:'='
            },
            templateUrl: 'app/directives/consumerLoan/loanService/loanServicedir.html'
        };

        return directive;
    }
})();

/**
 * @name customTab
 * @description
 * 页签切换指令
 */

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('customTab', customTab);

    function customTab() {
        var directive = {
            restrict: 'EA',
            scope: {
                items: '=',
                index: '=',
                itemWidth : '@'
            },
            templateUrl: 'app/directives/commonDiretives/tabs/tab.html',
            transclude:true,
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attrs) {
            if(scope.itemWidth){
                scope.width = scope.itemWidth;
            } else {
                scope.width = 100 / scope.items.length +'%';
            }

            scope.select = function(index) {
                scope.index = index;
            };
        }
    }
})();

/**
 * @name keyboard
 * @description
 * 软键盘指令
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('keyboard', keyboard);

    keyboard.$inject = ['$document', '$compile', '$timeout'];

    function keyboard($document, $compile, $timeout) {
        var directive = {
            restrict: 'A',
            require: '^ngModel',
            scope: {
                ngModel: '=',
                onlydigit: '@'
            },
            controller: keyboardController,
            link: linkFunc
        };

        return directive;

        function keyboardController($scope, $element) {
            var keyNumArray = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'];
            var keyCharArray = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];

            /**
             * @name randomNumberButton
             * @description
             * 用于打乱数组顺序
             */
            var randomNumberButton = function() {
                return Math.random() > 0.5 ? -1 : 1; // 用Math.random()函数生成0~1之间的随机数与0.5比较，返回-1或1
            };

            /**
             * @name closeKeyboard
             * @description
             * 关闭软键盘
             */
            $scope.closeKeyboard = function() {
                $scope.isShowKeyboard = false;
                $element[0].focus();
            };

            /**
             * @name showKeyboard
             * @description
             * 显示软键盘
             */
            $scope.setKeyboardContent = function() {
                $scope.onlydigit = !!($element.attr('onlydigit') == 'true' || $element.attr('onlydigit') === '');

                // 打乱字母的数组
                keyNumArray.sort(randomNumberButton);
                // 打乱数组的数组
                keyCharArray.sort(randomNumberButton);

                $scope.keyNumArray = keyNumArray;
                $scope.keyCharArray = keyCharArray;
            };

            /**
             * @name stopPropagation
             * @description
             * 阻止返回上层事件
             */
            $scope.stopPropagation = function() {
                event.stopPropagation();
            };

            /**
             * @name deleteKey
             * @description
             * 键盘上的删除键
             */
            $scope.deleteKey = function() {
                $scope.ngModel = $scope.ngModel.substring(0, $scope.ngModel.length - 1);
            };

            /**
             * @name setCapsLock
             * @description
             * 切换大小写
             */
            $scope.setCapsLock = function() {
                if ($scope.keyCharArray) {
                    // 获取数组的第一个字符
                    var keyMap = $scope.keyCharArray[0];
                    // 如果是大写则转换成小写，反之.
                    if (keyMap >= 'A' && keyMap <= 'Z') {
                        // uppercase
                        for (var key in $scope.keyCharArray) {
                            $scope.keyCharArray[key] = $scope.keyCharArray[key].toLowerCase();
                        }
                    } else {
                        // lowercase
                        for (var key in $scope.keyCharArray) {
                            $scope.keyCharArray[key] = $scope.keyCharArray[key].toUpperCase();
                        }
                    }
                }
            };

            /**
             * @name addKey
             * @param key 输入的字母
             * @description
             * 点击软键盘上的字母
             */
            $scope.addKey = function(key) {
                if ($scope.ngModel === undefined) {
                    $scope.ngModel = '';
                }
                $scope.ngModel = $scope.ngModel + key;
            };

            $scope.show = function() {
                var keyboardHeight = angular.element('.softKeyboard').prop('offsetHeight');
                var left = 0;
                if ($element.parent().hasClass('input-group')) {
                    left = 0 - $element.parent().prev().prop('offsetWidth');
                }

                // 得到点击图标的坐标
                var position = $element[0].getBoundingClientRect();
                var bottom = document.body.clientHeight - position.bottom;
                if (bottom < keyboardHeight) {
                    $scope.style = {
                        'bottom': position.height + 'px',
                        'left': left
                    };
                } else {
                    $scope.style = {
                        'top': position.height + 'px',
                        'left': left
                    };
                }

                $scope.isShowKeyboard = true;
            };
        }

        function linkFunc(scope, element, attrs) {

            $(element).wrap('<div class="input-group"></div>');
            // 给元素后方追加点击按钮
            var content = $compile('<span key="' + scope.$id + '"  class="input-group-btn"><button class="btn btn-default" type="button"><span  class="fa fa-keyboard-o fa-lg"></span></button></span>')(scope);
            element.after(content);

            element.next().bind('click', function(event) {

                scope.$apply(function() {
                    // 第一次点击软键盘按钮
                    if (scope.isShowKeyboard === undefined) {
                        // 绘制软键盘
                        var content = $compile('<div ng-click="stopPropagation()" ng-show="isShowKeyboard" onload="show()"  ng-include=\'"app/directives/commonDiretives/softKeyboard/softkeyboard.html"\'></div>')(scope);

                        element.after(content);
                    }

                    // 如果已经绘制过软键盘
                    if (scope.isShowKeyboard !== true) {
                        // 设置软键盘显示内容
                        scope.setKeyboardContent();
                        scope.isShowKeyboard = true;
                    } else {
                        scope.isShowKeyboard = false;
                    }
                });
            });

            // 点击页面其他位置，取消软键盘
            $document.bind('click', function(event) {
                var checked = $(event.target).parents('span').attr('key') != $(element[0]).parent().children('span[key]').attr('key');
                if (scope.isShowKeyboard && checked) {
                    scope.$apply(function() {
                        scope.isShowKeyboard = false; // 隐藏软键盘
                    });
                }
            });
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('pwdEye', pwdEye);

    pwdEye.$inject = ['$compile'];

    function pwdEye($compile) {
        var directive = {
            restrict: 'AE',
            scope:{
                'for': '@'
            },
            link: linkFunc,
            controller: pwdEyeCrtrl,
            controllerAs: 'vm'
        };

        return directive;

        function linkFunc(scope, el, attr, ctrl) {
            scope.vm.id = scope.for;
            el.html('<i class="fa" ' +
                'ng-class="{\'fa-eye-slash\': vm.type==\'password\', \'fa-eye\': vm.type==\'text\'}" ' +
                'ng-click="vm.changeShowStatus(vm.id)"></i>');
            $compile(el.contents())(scope);
        }

        function pwdEyeCrtrl() {
            var vm = this;
            vm.type = 'password';

            vm.changeShowStatus = function(id) {
                var el = $('#' + id);
                var type = el.attr('type');
                if(type == 'password') {
                    el.attr('type', 'text');
                    vm.type = 'text';
                } else {
                    el.attr('type', 'password');
                    vm.type = 'password';
                }
            };
        }
    }
})();

/**
 * @name doubleCheck
 * @description
 * 判断两次输入是否相同
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('pwCheck', ['$compile', pwCheck]);

    function pwCheck($compile) {
        var directive = {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                /* password: '@'*/
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attr, ngModel) {
            var firstPassword = '#' + attr.pwCheck;

            element.add(firstPassword).on('keyup', function() {
                scope.$apply(function() {

// console.info(element.val() === $(firstPassword).val());
// console.info("element    "+element.val() );
// console.info("element    "+firstPassword);
// console.info("firstPassword      "+$(firstPassword).val() );

                    ngModel.$setValidity('pwmatch', element.val() === $(firstPassword).val());

                    // ngModel.$setValidity('pwmatch', element.val() === $(firstPassword).val());
                });
            });
        }
    }

})();

// angular.module('myApp.directives', [])
//   .directive('pwCheck', [function () {
//     return {
//       require: 'ngModel',
//       link: function (scope, elem, attrs, ctrl) {
//         var firstPassword = '#' + attrs.pwCheck;
//         elem.add(firstPassword).on('keyup', function () {
//           scope.$apply(function () {
//             var v = elem.val()===$(firstPassword).val();
//             ctrl.$setValidity('pwmatch', v);
//           });
//         });
//       }
//     }
//   }]);

/**
 * @name passwordstrength
 * @description
 * 密码强度的指令
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('passwordstrength', passwordstrength);

    function passwordstrength() {
        var directive = {
            restrict: 'EA',
            replace: true,
            templateUrl: 'app/directives/commonDiretives/passwordStrength/passwordStrength.html',
            scope: {
                pwd: '=' //绑定指令外部的pwd属性
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

            //验证的规则
            var patternArray = [
                //if length is 8 characters or more, increase strength value
                /([a-zA-Z0-9!,%,&,@,#,$,^,*,?,_,~]){8,}/,
                //if password contains both lower and uppercase characters, increase strength value
                /([a-z].*[A-Z])|([A-Z].*[a-z])/,
                //if it has numbers and characters, increase strength value
                /([a-zA-Z])/,
                //if it has one special character, increase strength value
                /([!,%,&,@,#,$,^,*,?,_,~])/
            ];

            /*
             * @name checkPwdStrength
             * @param password 输入的密码
             * @returns {int} 密码强度
             * @description
             * 验证密码的强度,并返回强度的值
             * 默认规则为:8位密码以上,包含大小写,包含数字和字母,包含特殊字符
             */
            var checkPwdStrength = function(password) {
                var strength = 0; //密码强度

                if (password !== undefined) {
                    //根据正则表达式数组进行判断，满足正则表达式则增加强度
                    for (var i = 0; i < patternArray.length; i++) {
                        //满足要求
                        if (password.match(patternArray[i])) {
                            //强度增加
                            strength += 1;
                        }
                    }
                }
                //返回强度
                return strength;
            };

            //监控输入的密码，如有变化调用验证密码强度的指令
            scope.$watch('pwd', function() {
                scope.strength = checkPwdStrength(scope.pwd);
                scope.strengthStr = '';
                switch (scope.strength) {
                    case 0:
                    case 1:
                        scope.weakClass = 'weak';
                        scope.mediumClass = 'weak';
                        scope.strongClass = 'weak';
                        scope.strengthStr = '弱';
                        break;
                    case 2:
                        scope.weakClass = 'weak';
                        scope.mediumClass = 'medium';
                        scope.strongClass = 'medium';
                        scope.strengthStr = '中';
                        break;
                    case 3:
                    case 4:
                        scope.weakClass = 'weak';
                        scope.mediumClass = 'medium';
                        scope.strongClass = 'strong';
                        scope.strengthStr = '强';
                        break;

                }
            });
        }
    }

})();

/**
 * @name paging
 * @description
 * 分页组件
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('paging', paging);

    paging.$inject = ['$timeout'];
        /**
         * @memberof directbank
         * @ngdoc directive
         * @name paging
         * @description
         *   分页组建
         *
         * @attr {String} page              当前页面页码
         * @attr {String} pageSize          每页条目数
         * @attr {String} total             总条目数
         * @attr {String} dots              折叠页面符号：默认为...
         * @attr {String} hideIfEmpty       不足分页时是否隐藏，默认为'true'
         * @attr {String} ulClass           ul样式，默认为'pagination'
         * @attr {String} activeClass       页面选中时样式，默认为'active'
         * @attr {String} disabledClass     页面未选中时样式，默认为'disabled'
         * @attr {String} adjacnet          相邻页面数量，默认为'2'
         * @attr {String} scrollTop         是否返回头部，默认为'false'
         * @attr {String} showPrevNext      显示前一页、下一页，默认为'true'
         * @attr {method} pagingAction      分页点击触发事件
         * @example
         *   Usage:
         *   <paging class="small"
         *       page="1"
         *       page-size="10"
         *       total="1000"
         *       adjacent="1"
         *       scroll-top="false"
         *       hide-if-empty="true"
         *       show-prev-next="true"
         *       paging-action="vm.DoCtrlPagingAct('Paging Clicked', page, pageSize, total)">
         *   </paging>
         */

    function paging($timeout) {
        var directive = {
            restrict: 'EA',
            link: fieldLink,
            scope: {
                page: '=',
                pageSize: '=',
                total: '=',
                dots: '@',
                hideIfEmpty: '@',
                ulClass: '@',
                activeClass: '@',
                disabledClass: '@',
                adjacent: '@',
                scrollTop: '@',
                showPrevNext: '@',
                pagingAction: '&'
            },
            template:'<div class="paging-panel">' +
                '<div class="page-pagination">' +
                '<ul data-ng-hide="Hide" data-ng-class="ulClass">' +
                '<li ' +
                'title="{{Item.title}}" ' +
                'data-ng-class="Item.liClass" ' +
                'data-ng-click="Item.action()" ' +
                'data-ng-repeat="Item in List">' +
                '<span data-ng-bind="Item.value"></span>' +
                '</li>' +
                '</ul>' +
                '</div>' +
                '</div>'
        };

        return directive;

        /**
         * Link the directive to enable our scope watch values
         *
         * @param {object} scope - Angular link scope
         * @param {object} el - Angular link element
         * @param {object} attrs - Angular link attribute
         */
        function fieldLink(scope, el, attrs) {
            scope.$watchCollection('[page, total]', function(newValue, oldValue) {
                $timeout(function() {
                    build(scope, attrs);
                });
            });
        }

        /**
         * Assign default scope values from settings
         * Feel free to tweak / fork these for your application
         *
         * @param {Object} scope - The local directive scope object
         * @param {Object} attrs - The local directive attribute object
         */
        function setScopeValues(scope, attrs) {
            scope.List = [];
            scope.Hide = false;
            scope.dots = scope.dots || '...';
            scope.page = parseInt(scope.page) || 1;
            scope.pageSize = parseInt(scope.pageSize) || 8;
            scope.total = parseInt(scope.total) || 0;
            scope.ulClass = scope.ulClass || 'pagination';
            scope.adjacent = parseInt(scope.adjacent) || 2;
            scope.activeClass = scope.activeClass || 'active';
            scope.disabledClass = scope.disabledClass || 'disabled';

            scope.scrollTop = scope.$eval(attrs.scrollTop);
            // scope.hideIfEmpty = scope.$eval(attrs.hideIfEmpty);
            scope.hideIfEmpty = false;
            scope.showPrevNext = scope.$eval(attrs.showPrevNext);
        }


        /**
         * Validate and clean up any scope values
         * This happens after we have set the scope values
         *
         * @param {Object} scope - The local directive scope object
         * @param {int} pageCount - The last page number or total page count
         */
        function validateScopeValues(scope, pageCount) {
            // Block where the page is larger than the pageCount
            if (scope.page > pageCount) {
                scope.page = pageCount;
            }

            // Block where the page is less than 0
            if (scope.page <= 0) {
                scope.page = 1;
            }

            // Block where adjacent value is 0 or below
            if (scope.adjacent <= 0) {
                scope.adjacent = 2;
            }

            // Hide from page if we have 1 or less pages
            // if directed to hide empty
            if (pageCount <= 1) {
                scope.Hide = scope.hideIfEmpty;
            }
        }

        /**
         * Assign the method action to take when a page is clicked
         *
         * @param {Object} scope - The local directive scope object
         * @param {int} page - The current page of interest
         */
        function internalAction(scope, page) {
            // Block clicks we try to load the active page
            if (scope.page == page) {
                return;
            }

            // Update the page in scope
            scope.page = page;

            // Pass our parameters to the paging action
            scope.pagingAction({
                page: scope.page,
                pageSize: scope.pageSize,
                total: scope.total
            });

            // If allowed scroll up to the top of the page
            if (scope.scrollTop) {
                scrollTo(0, 0);
            }
        }


        /**
         * Add the first, previous, next, and last buttons if desired
         * The logic is defined by the mode of interest
         * This method will simply return if the scope.showPrevNext is false
         * This method will simply return if there are no pages to display
         *
         * @param {Object} scope - The local directive scope object
         * @param {int} pageCount - The last page number or total page count
         * @param {string} mode - The mode of interest either prev or last
         */
        function addPrevNext(scope, pageCount, mode) {
            // Ignore if we are not showing
            // or there are no pages to display
            if (!scope.showPrevNext || pageCount < 1) {
                return;
            }

            // Local variables to help determine logic
            var disabled, alpha, beta;


            // Determine logic based on the mode of interest
            // Calculate the previous / next page and if the click actions are allowed
            if (mode === 'prev') {
                disabled = scope.page - 1 <= 0;
                var prevPage = scope.page - 1 <= 0 ? 1 : scope.page - 1;

                alpha = {
                    value: '首页',
                    title: 'First Page',
                    page: 1
                };

                beta = {
                    value: '上一页',
                    title: 'Previous Page',
                    page: prevPage
                };
            } else {
                disabled = scope.page + 1 > pageCount;
                var nextPage = scope.page + 1 >= pageCount ? pageCount : scope.page + 1;

                alpha = {
                    value: '下一页',
                    title: 'Next Page',
                    page: nextPage
                };

                beta = {
                    value: '尾页',
                    title: 'Last Page',
                    page: pageCount
                };
            }

            // Create the Add Item Function
            var buildItem = function(item, disabled) {
                return {
                    value: item.value,
                    title: item.title,
                    liClass: disabled ? scope.disabledClass : '',
                    action: function() {
                        if (!disabled) {
                            internalAction(scope, item.page);
                        }
                    }
                };
            };

            // Build our items
            var alphaItem = buildItem(alpha, disabled);
            var betaItem = buildItem(beta, disabled);

            // Add our items
            scope.List.push(alphaItem);
            scope.List.push(betaItem);
        }


        /**
         * Adds a range of numbers to our list
         * The range is dependent on the start and finish parameters
         *
         * @param {int} start - The start of the range to add to the paging list
         * @param {int} finish - The end of the range to add to the paging list
         * @param {Object} scope - The local directive scope object
         */
        function addRange(start, finish, scope) {
            // Create the Add Item Function
            var buildItem = function(i) {
                return {
                    value: i,
                    title: 'Page ' + i,
                    liClass: scope.page == i ? scope.activeClass : '',
                    action: function() {
                        internalAction(scope, this.value);
                    }
                };
            };

            // Add our items where i is the page number
            var i = 0;
            for (i = start; i <= finish; i++) {
                var item = buildItem(i);
                scope.List.push(item);
            }
        }

        /**
         * Add Dots ie: 1 2 [...] 10 11 12 [...] 56 57
         * This is my favorite function not going to lie
         *
         * @param {Object} scope - The local directive scope object
         */
        function addDots(scope) {
            scope.List.push({
                value: scope.dots
            });
        }

        /**
         * Add the first or beginning items in our paging list
         * We leverage the 'next' parameter to determine if the dots are required
         *
         * @param {Object} scope - The local directive scope object
         * @param {int} next - the next page number in the paging sequence
         */
        function addFirst(scope, next) {
            addRange(1, 2, scope);

            // We ignore dots if the next value is 3
            // ie: 1 2 [...] 3 4 5 becomes just 1 2 3 4 5
            if (next != 3) {
                addDots(scope);
            }
        }

        /**
         * Add the last or end items in our paging list
         * We leverage the 'prev' parameter to determine if the dots are required
         *
         * @param {int} pageCount - The last page number or total page count
         * @param {Object} scope - The local directive scope object
         * @param {int} prev - the previous page number in the paging sequence
         */
        // Add Last Pages
        function addLast(pageCount, scope, prev) {
            // We ignore dots if the previous value is one less that our start range
            // ie: 1 2 3 4 [...] 5 6  becomes just 1 2 3 4 5 6
            if (prev != pageCount - 2) {
                addDots(scope);
            }

            addRange(pageCount - 1, pageCount, scope);
        }

        /**
         * The main build function used to determine the paging logic
         * Feel free to tweak / fork values for your application
         *
         * @param {Object} scope - The local directive scope object
         * @param {Object} attrs - The local directive attribute object
         */
        function build(scope, attrs) {
            // Block divide by 0 and empty page size
            if (!scope.pageSize || scope.pageSize <= 0) {
                scope.pageSize = 1;
            }

            // Determine the last page or total page count
            var pageCount = Math.ceil(scope.total / scope.pageSize);

            // Set the default scope values where needed
            setScopeValues(scope, attrs);

            // Validate the scope values to protect against strange states
            validateScopeValues(scope, pageCount);

            // Create the beginning and end page values
            var start, finish;

            // Calculate the full adjacency value
            var fullAdjacentSize = (scope.adjacent * 2) + 2;

            // Add the Next and Previous buttons to our list
            addPrevNext(scope, pageCount, 'prev');

            // If the page count is less than the full adjacnet size
            // Then we simply display all the pages, Otherwise we calculate the proper paging display
            if (pageCount <= (fullAdjacentSize + 2)) {
                start = 1;
                addRange(start, pageCount, scope);
            } else {
                // Determine if we are showing the beginning of the paging list
                // We know it is the beginning if the page - adjacent is <= 2
                if (scope.page - scope.adjacent <= 2) {
                    start = 1;
                    finish = 1 + fullAdjacentSize;
                    addRange(start, finish, scope);
                    addLast(pageCount, scope, finish);
                }

                // Determine if we are showing the middle of the paging list
                // We know we are either in the middle or at the end since the beginning is ruled out above
                // So we simply check if we are not at the end
                // Again 2 is hard coded as we always display two pages after the dots
                else if (scope.page < pageCount - (scope.adjacent + 2)) {
                    start = scope.page - scope.adjacent;
                    finish = scope.page + scope.adjacent;
                    addFirst(scope, start);
                    addRange(start, finish, scope);
                    addLast(pageCount, scope, finish);
                }

                // If nothing else we conclude we are at the end of the paging list
                // We know this since we have already ruled out the beginning and middle above
                else {
                    start = pageCount - fullAdjacentSize;
                    finish = pageCount;
                    addFirst(scope, start);
                    addRange(start, finish, scope);
                }
            }

            // Add the next and last buttons to our paging list
            addPrevNext(scope, pageCount, 'next');
        }
    }
})();

/**
 * Created by macbook on 16/3/10.
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('highLight', highLight);

    highLight.$inject = ['$compile','$timeout'];

    /**
     * 输入框放大镜
     * @memberof DirectBankProject
     * @function highLight
     * example:
     *  <input type="text" high-light num="3" symbol="" />
     *   symbol:    间隔字符
     *     默认——空格
     *     null——什么也不加
     *     string——一些字符
     *   num:       多少字符间隔
     *     数字
     */
    function highLight($compile,$timeout) {
        var directive = {
            restrict: 'EA',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            var num = parseInt(attr.num)||3;
            var symbol = '&nbsp;';

            if(attr.symbol === undefined){
                symbol = '';
            }

            if(attr.symbol){
                symbol = attr.symbol;

                if(symbol === 'null'){
                   symbol = '';
                }
            }

            el.bind('blur', function() {
                 el.siblings('.highlight').hide();
            });

            el.bind('focus', function() {
                if(el.siblings('.highlight')){
                    el.siblings('.highlight').show();
                }
            });
            var model = attr.highLight;

            el.parent().addClass('pr');

            var node = $compile('<div ng-show="' + model + '" class="highlight overflow-hidden"> ' +
                '<div class="hightBlock font-default col-xs-12">' +
                '<em ng-bind="' + model + '|separator:{num:' + num + ',symbol:\'' + symbol + '\'}" ></em>' +
                '</div>' +
                '</div>')(scope);

            el.parent().append(node);

            $timeout(function(){
                var elWidth = el.context.offsetWidth;
                var elHeight = el.context.offsetHeight;

                el.siblings('.highlight').css({
                    height: elHeight + 5,
                    width: elWidth,
                    top: -elHeight - 7,
                    lineHeight: elHeight + 5 + 'px'
                });
            },1000);
        }
    }
})();

/**
 * @name eChart
 * @description
 * echart组建-饼图
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('eChart', eChart);
        /**
         * @memberof directbank
         * @ngdoc directive
         * @name echart
         * @description
         *   echart组建
         *
         * @attr {String} option 图表参数
         * @example
         *   Usage:
         *    <div e-chart style="height:300px; width: 100%;" options="vm.assetOptions"></div>
         */

    eChart.$inject = ['$compile'];

    function eChart($compile) {
        var directive = {
            restrict:'AE',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr, ngModel) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = echarts.init(el[0], 'macarons');
            //监听options变化
            attr.$observe('options', function () {
                if(attr.options){
                    var options = scope.$eval(attr.options);
                    if (angular.isObject(options)) {
                        myChart.setOption(options);
                        if(options.backFun != null || options.backFun != "" || options.backFun != undefined){
                             myChart.on('click', options.backFun);
                        }
                    }
                }
            }, true);
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('customPattern', customPattern);

    customPattern.$inject = ['ValidationService'];

    /**
     * customPattern指令
     * @memberof EBankProject
     * @function customPattern指令
     */
    function customPattern(ValidationService) {
        var directive = {
            restrict: 'A',
            require: 'ngModel',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr, ctrl) {
            if(!ctrl) return;

            var key;

            attr.$observe('customPattern', function(value) {
                key = value;        // value: identification || phone
                ctrl.$validate();
            });

            ctrl.$validators.customPattern = function(modelValue, viewValue) {
                // Return true/false for valid/invalid
                if(ctrl.$isEmpty(viewValue)) return true;           // 避免与required混淆
                return !ctrl.$isEmpty(viewValue) && ValidationService.validatePattern(key, viewValue);
            };
      }
    }
})();

 (function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('uiCalendar', uiCalendar);

    function uiCalendar() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/directives/commonDiretives/calendar/calendar.html',
            scope: {
                'time': '='
            },
            link: linkFunc,
            controller: uiCalendarCtrl,
            controllerAs: 'vm'
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('time', function(time) {
                scope.time = time;
            });
        }

        function uiCalendarCtrl($scope) {
            var vm = this;
            vm.minDate = new Date(2013, 0, 1);
            vm.maxDate = new Date();

            vm.today = function() {
                $scope.time = new Date();
            };

            vm.clear = function() {
                $scope.time = null;
            };

            vm.open = function($event) {
                vm.status.opened = true;
            };

            vm.setDate = function(year, month, day) {
                $scope.time = new Date(year, month, day);
            };

            vm.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
            };

            vm.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'yyyyMMdd', 'dd.MM.yyyy', 'shortDate'];
            vm.format = vm.formats[1];

            vm.status = {
                opened: false
            };

            var tomorrow = new Date();
            tomorrow.setDate(tomorrow.getDate() + 1);
            var afterTomorrow = new Date();
            afterTomorrow.setDate(tomorrow.getDate() + 2);
            vm.events =
                [{
                    date: tomorrow,
                    status: 'full'
                }, {
                    date: afterTomorrow,
                    status: 'partially'
                }];

            vm.getDayClass = function(date, mode) {
                if (mode === 'day') {
                    var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                    for (var i = 0; i < vm.events.length; i++) {
                        var currentDay = new Date(vm.events[i].date).setHours(0, 0, 0, 0);

                        if (dayToCheck === currentDay) {
                            return vm.events[i].status;
                        }
                    }
                }

                return '';
            };
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('amtInput', amtInput);

    function amtInput() {

        var directive = {
            restrict : 'AE',
            scope: {
                'afield':'='
            },
            templateUrl: 'app/directives/commonDiretives/amtInput/amtInput.html',
            controller: amtInputController,
            controllerAs: 'vm'
        };

        return directive;

        function amtInputController($scope){
            var vm = this;
            vm.keyUpAmt = keyUpAmt;//按键敲击事件
            vm.blurAmtInput = blurAmtInput;//离开金额域触发事件
            var txt = '';
            init();
            function init(){
                $scope.afield="0.00";
            }
            function keyUpAmt(){
                //金额去千分符
                var getAmt = ($scope.afield).replace(new RegExp('\,',["g"]),'');
                //校验金额是否合法
                var reg = getAmt.match(/^[0-9]{0,16}\.{0,1}[0-9]{0,2}$/);

                //判断金额是否null
                if (reg != null) {
                    //判断金额是否大于三位
                    if (reg[0].length > 3){
                        var regN = "";
                        var regX = "";
                        //判断金额里是否有“.”
                        if (reg[0].indexOf(".") >= 0) {
                            regN = reg[0].substr(0, reg[0].indexOf('.'));
                            regX = reg[0].substr(reg[0].indexOf('.'), reg[0].indexOf('.')+2);
                        }else{
                            regN = reg[0];
                        }
                        var mod = regN.length % 3;
                        var output = (mod > 0 ? (regN.substring(0,mod)) : '');
                        for (var i=0 ; i < Math.floor(regN.length / 3); i++) {
                            if ((mod == 0) && (i == 0)){
                                output += regN.substring(mod+ 3 * i, mod + 3 * i + 3);
                            }else{
                                output += ',' + regN.substring(mod + 3 * i, mod + 3 * i + 3);
                            }
                        }
                        txt = output+regX;
                    }else {
                        txt = reg[0];
                    }
                }
                $scope.afield = txt;
            }

            /*
                焦点离开金额域触发事件
             */
            function blurAmtInput(){
                var getAmt = ($scope.afield).replace(new RegExp('\,',["g"]),'');
                if (getAmt == null || getAmt =='null' || getAmt==''){
                    afield = "0.00";
                }
                var afield = txt;
                if(afield.substring(0, 1)==''){
                    afield = "0"+afield;
                }
                if ( afield.length == 0 ){
                    return "0.00";
                }
                var dotPos = afield.indexOf(".");
                if ( dotPos < 0 ){
                    afield = afield+'.00'
                }
                if ( dotPos == 0 ){
                    afield = '0' + afield;
                    dotPos = afield.indexOf(".");
                }
                if ( dotPos == afield.length-2){
                    afield = afield + '0';
                }
                if ( dotPos == afield.length-1){
                    afield = afield + '00';
                }
                $scope.afield = afield;
            }
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedClownController', fixedClownController);

    fixedClownController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedClownController
     * @param  {service} UserService 用户服务
     * @description
     * 定活宝购物车
     */

    function fixedClownController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.addRecordShopCart = addRecordShopCart;               // 添加购物车方法

        init();


        function init(){
            // 产品信息
            vm.productdetail = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            getSurveyQuestion();
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        }


        // 购物车添加方法
        function addRecordShopCart(){

            if (vm.regForm.$valid) {
                addRecordShopCartFun(); // 添加购物车
            }
        }

     function getSurveyQuestion(){
         var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getSurveyQuestionAction"                                  // 服务名称
                },
             reqBody:{
                configOptionId:vm.productdetail.configOptionId,
                productId:vm.productdetail.goodsCode
                // configOptionId:"DP_0523_10001",
                // productId:"DP_0523_10000-01"
            }
        }

            var promise = UserService.getSurveyQuestion(params);

            promise.then(function(data) {
                vm.enumQuestionList = data.rspBody.enumQuestionList;
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        // 添加购物车
        function addRecordShopCartFun(){
            var recordMap = {};
            // 购物车数据
            recordMap.productId = vm.productdetail.goodsCode;                    // 产品ID
            recordMap.productName = vm.productdetail.goodsName;                    // 产品名称
            recordMap.productDes = vm.productdetail.textData;//bmfDesc;        // 产品描述
            // recordMap.productDes = vm.productdetail.bmfDesc;                     // 产品描述
            // 价格 = 基础价格 + 产品配置项价格
            recordMap.productPrice = (parseInt(vm.productdetail.price) + parseInt(vm.productdetail.configOptionPrice)).toFixed(2);                     // 产品价格
            recordMap.productAmount = "1";
                                                 // 产品数量
            var surveyInfo={};

            for (var i = 0; i < vm.enumQuestionList.length; i++) {
                 var descption = vm.enumQuestionList[i].description;
                 surveyInfo[descption]= vm.model.descption[i];
            }


            // 产品调研项数据
            var tradeData = {
                "surveyInfo":JSON.stringify(surveyInfo),             //  调查信息
                "bankBalance":vm.productdetail.bankBalance,           // 价格
                "requireAmount":vm.productdetail.requireAmount,        //计价单位
                // "cardpassword":vm.model.cardpassword,                            //  密码
                // "custAccount":vm.model.custAccount,                              //  客户账户
                // "certificate":vm.model.certificate,                              //  凭证批号
                // "voucherNo":vm.model.voucherNo,                                  //  凭证序号
                "prodCatalogId":vm.productdetail.parentCode,//prdTypeCode,                    //  产品目录
                "configOptionId": vm.productdetail.configOptionId                //  配置项标识
            };

            // 客户号
            var uniqueCstIdentity = vm.user.customerNo;
            recordMap.tradeData = tradeData;


            var reqBody = {
                uniqueCstIdentity:uniqueCstIdentity,
                recordMap:recordMap
            }

            var promise = UserService.addRecordShopCart(reqBody);

            promise.then(function(data) {
                $state.go('shoppingCar');
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }



    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('TransferController', TransferController);

    TransferController.$inject = ['$rootScope', '$scope', 'CommonService','UserService', 'CONFIG', '$state'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name TransferController
     * @param  {service} CommonService    公用服务
     * @param  {service} $state           通用服务
     * @description
     * 首页控制器
     */
    function TransferController($rootScope, $scope, CommonService, UserService, CONFIG, $state) {
        var vm = this;

        // 变量
        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';

        //方法
        vm.addcar=addcar;
        vm.transferServiceOpen =transferServiceOpen;
        var header = {};

        // 初始化
        init();

        function init(){
            // 获取固收产品列表
            // getProduct();
            getShelfCodeList();
        }

        /**
         * @memberof accountManagementController
         * @param compareMessage
         * @description 接收货架信息方法
         */
        $scope.$on('allChlShelfGoodCompare', function(event, data) {
            if(data != "" || data != null || data != undefined){
                setTimeout(function() {
                    $scope.$apply(function() {
                        vm.shelfCodeList = data;
                        vm.product = vm.shelfCodeList.returnGoodsList;
                        for(var i = 0; i < vm.product.length; i++){
                            var sunPrice = (parseInt(vm.product[i].price) + parseInt(vm.product[i].configOptionPrice)).toFixed(2);
                            vm.product[i].sunPrice = sunPrice;
                        }
                    });
                });
                
            }
        });


        // 接受货架信息
        function getShelfCodeList(){
            vm.shelfCodeList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SHELFCODE_INFO));
            setTimeout(function() {
                    $scope.$apply(function() {
                        vm.product = vm.shelfCodeList.returnGoodsList;
                        for(var i = 0; i < vm.product.length; i++){
                            var sunPrice = (parseInt(vm.product[i].price) + parseInt(vm.product[i].configOptionPrice)).toFixed(2);
                            vm.product[i].sunPrice = sunPrice;
                        }
                    });
                });
            
        }

         /**
         * @memberof TransferController
         * @function getProduct
         * @description 获取固收产品列表
         */
        function getProduct() {

            vm.product = [];

            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:""                                  // 服务名称
                },
                reqBody:{
                    pageIndex: vm.pageIndex,
                    pageSize: vm.pageSize
                }
            };

            var promise = UserService.getFixedProduct(params);

            promise.then(function(data) {
                if (data.returnList && data.returnList.length !== 0) {
                    vm.product = data.returnList;
                    vm.pageTotal = '' + Math.ceil(parseInt(data.totalCount) / parseInt(vm.pageSize));
                    vm.page = {
                        'currentPage': vm.currentPage,
                        'page-size': vm.pageSize,
                        'total': vm.pageTotal
                    };
                } else {
                    console.log('列表无数据');
                }
            }).catch(function(error) {
                CommonService.showError(error);
            });

        }


        /**
         * @memberof TransferController
         * @function DoCtrlPagingAct
         * @description 产品列表分页控制
         */
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = page;
            vm.currentPage = page;
            getProduct();
        }

        /**
         * @memberof TransferController
         * @function addcar
         * @description 加入购物车方法
         */
        
        function addcar(product) {
            if(product.templateUrl == "" || product.templateUrl == null || product.templateUrl == undefined){
                 CommonService.subShowMsg("4","该产品（"+product.bmfName+"）没有配置产品调研项");
                 return;
            }

            // 跳转到相应的产品调研项
            $state.go(product.templateUrl);
        }

        /**
         * 显示弹出录入页面
         * @memberof selfRegistrationController
         * @function showAgreement
         * @description 显示相关协议描述
         */
        function transferServiceOpen() {
            ModalService.showModal({
                templateUrl: 'app/components/consumerTransfer/transferService/transferServiceOpen.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('discoverPromotionsListController', discoverPromotionsListController);

    discoverPromotionsListController.$inject = ['$rootScope', '$scope', '$stateParams','CommonService','UserService', 'CONFIG', '$state'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name discoverPromotionsListController
     * @param  {service} CommonService    公用服务
     * @param  {service} $state           通用服务
     * @description
     * 促销订单列表
     */
    function discoverPromotionsListController($rootScope, $scope, $stateParams, CommonService, UserService, CONFIG, $state) {
        var vm = this;
        vm.pageSize = '4';//每页显示多少条
        vm.pageIndex = '1';//
        vm.currentPage = '1';//
        vm.DoCtrlPagingAct = DoCtrlPagingAct;//分页方法
        //方法
        vm.addcar=addcar;

        // 初始化
        init();

        function init(){
            if ($stateParams.discoverPromotionsListFlag==null) {
                vm.discoverPromotionsListFlag = JSON.parse(sessionStorage.getItem('discoverPromotionsListFlag'));
            }else{
                vm.discoverPromotionsListFlag = $stateParams.discoverPromotionsListFlag;//展示列表标识；0-展示促销列表；1-展示推荐产品列表
                sessionStorage.setItem("discoverPromotionsListFlag", JSON.stringify(vm.discoverPromotionsListFlag));
            }
            if (vm.discoverPromotionsListFlag=="0") {
                // 获取某一个促销规则的产品
                queryDiscoverPromotionsById();
            }else if (vm.discoverPromotionsListFlag=="1") {
                vm.productIdList = JSON.parse(sessionStorage.getItem('discoverHotList'));

                vm.pageTotal = '' + Math.ceil(parseInt(vm.productIdList.length) / parseInt(vm.pageSize));//总页数
                vm.page = {
                        'currentPage': vm.currentPage,
                        'page-size': vm.pageSize,
                        'total': vm.pageTotal
                    };
                var m = 0;
                vm.productIdListPage = [];
                var k = 0;
                if (vm.productIdList.length > parseInt(vm.pageSize)) {
                    k = parseInt(vm.pageSize);
                }else{
                    k = vm.productIdList.length;
                }
                for (var i = 0; i < k; i++) {
                    vm.productIdListPage[m] = vm.productIdList[i];
                    m++;
                }
            }
        }
        //分页
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = parseInt(page);
            vm.currentPage = parseInt(page);
            var m = 0;
            vm.productIdListPage = [];
            var j = (vm.pageIndex-1)*parseInt(vm.pageSize);
            var n;
            if(vm.pageIndex*parseInt(vm.pageSize)>vm.productIdList.length){
                n=vm.productIdList.length;
            }else{
                n=vm.pageIndex*parseInt(vm.pageSize);
            }
            for (var i = j; i < n; i++) {
                vm.productIdListPage[m] = vm.productIdList[i];
                m++;
            }
        }
        /**获取某一个促销规则的产品
         * @memberof discoverPromotionsListController
         * @function queryDiscoverPromotionsById
         * @description 获取某一个促销规则的产品
         */
        function queryDiscoverPromotionsById(){
            vm.productIdList = JSON.parse(sessionStorage.getItem('discoverPromotionsList')).productIdList;
            for (var i = 0; i < vm.productIdList.length; i++) {
                vm.productIdList[i].goodsName = vm.productIdList[i].productName;
                vm.productIdList[i].goodsCode = vm.productIdList[i].productId;
                vm.productIdList[i].configOptionName = vm.productIdList[i].productConfigOptionList[0].configOptionName;
                vm.productIdList[i].configOptionId = vm.productIdList[i].productConfigOptionList[0].configOptionId;
                vm.productIdList[i].configOptionPrice = vm.productIdList[i].productConfigOptionList[0].configOptionPrice;
                vm.productIdList[i].parentCode = vm.productIdList[i].productCategoryList[0].rootProductCategoryId;
                vm.productIdList[i].templateUrl = "templateProduct";//默认产品调研项
                vm.productIdList[i].prdTypeCode = vm.productIdList[i].productCategoryList[0].parentProductCategoryId;
                vm.productIdList[i].sunPrice = (parseInt(vm.productIdList[i].configOptionPrice) + parseInt(vm.productIdList[i].price)).toFixed(2);
            }

            vm.promoText = JSON.parse(sessionStorage.getItem('discoverPromotionsList')).promoText;
            vm.pageTotal = '' + Math.ceil(parseInt(vm.productIdList.length) / parseInt(vm.pageSize));//总页数
                vm.page = {
                        'currentPage': vm.currentPage,
                        'page-size': vm.pageSize,
                        'total': vm.pageTotal
                    };
                var m = 0;
                vm.productIdListPage = [];
                var k = 0;
                if (vm.productIdList.length > parseInt(vm.pageSize)) {
                    k = parseInt(vm.pageSize);
                }else{
                    k = vm.productIdList.length;
                }
                for (var i = 0; i < k; i++) {
                    vm.productIdListPage[m] = vm.productIdList[i];
                    m++;
                }
        }


        /**
         * @memberof discoverPromotionsListController
         * @function addcar
         * @description 加入购物车方法
         */

        function addcar(product) {
            if(product.templateUrl == "" || product.templateUrl == null || product.templateUrl == undefined){
                 CommonService.subShowMsg("4","该产品（"+product.bmfName+"）没有配置产品调研项");
                 return;
            }
            // 跳转到相应的产品调研项
            $state.go(product.templateUrl);
        }

    }

})();

(function() {
    'use strict';

    angular
    .module('EBankProject')
    .controller('TransferDefaultSuccController', TransferDefaultSuccController);

    TransferDefaultSuccController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name TransferDefaultSuccController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
     function TransferDefaultSuccController(UserService, CommonService, $state, CONFIG, $scope) {

        var vm = this;

        //method
        vm.backTranfer = backTranfer; //返回转账主页

        //value

        vm.model = {};

        init();

        function init(){
            var confirmData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_CONFIRM));
            if(confirmData != null){
                vm.model.custName = confirmData.custName;
                vm.model.payamount = confirmData.payamount;
            }
        }



      /**
         * 返回转账页面
         * @memberof TransferDefaultInputController
         * @function backTranfer
         * @description 返回转账页面
         */
         function backTranfer(){
            $state.go('accountManagement');

        }

    }
})();

/**
 * @name transferDefaultStateProgress
 * @description
 *  转账流程状态
 */
 (function() {
    'use strict';

    angular.module('EBankProject').directive('transferdefaultstate', transferdefaultstate);

    function transferdefaultstate() {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerTransfer/transferDefaultState/transferDefaultState.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

            var progressLength = $('.transferdefault').children().length;
            var activeArray = [],
            finishArray = [];
            for (var i = 0; i < progressLength; i++) {
                activeArray.push(false);
                if (i + 1 < scope.flow) {
                    finishArray[i] = true;
                } else {
                    finishArray[i] = false;
                }
            }
            activeArray[scope.flow - 1] = true;
            scope.finishs = finishArray;
            scope.actives = activeArray;
        }
    }

})();

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

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('TransferDefaultInputController', TransferDefaultInputController);

    TransferDefaultInputController.$inject = ['UserService', 'CommonService', '$state', 'CONFIG', '$scope', 'ModalService', '$rootScope'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name TransferDefaultInputController
     * @param  {service} UserService 用户服务
     * @description
     * 用户重置登录密码控制器
     */
    function TransferDefaultInputController(UserService, CommonService, $state, CONFIG, $scope, ModalService, $rootScope) {

        var vm = this;
        vm.model = {};
        //method
        vm.bankList = bankList;
        vm.bankListSearch = bankListSearch;
        vm.contactsList = contactsList;
        vm.totransferDefaultService = totransferDefaultService; // 提交转账信息
        vm.backTranfer = backTranfer; // 返回转账主页
        vm.products = [];
        //value

        init();


        function init() {
            
            getCitylist();
            // getCity();
            var previousStateData = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));

            if (previousStateData != null) {

                vm.model.payaccount = previousStateData.payaccount; //付款账号
                vm.model.payamount = previousStateData.payamount; //付款金额
                vm.model.collectaccount = previousStateData.collectaccount; //收款账号
                vm.model.custName = previousStateData.custName; //收款姓名
                vm.model.payaccountbank = previousStateData.payaccountbank; //收款账号
                vm.model.bankNo = previousStateData.bankNo; //开户网点
                vm.model.mobile = previousStateData.mobile; //短信通知手机号
                vm.model.transferuse = previousStateData.transferuse; //转账用途
            }
        }



        /**
         * 获取城市列表
         * @function getCitylist
         * @memberof BindBankcardController
         */
        function getCitylist() {
            var promise = CommonService.getCitylist();

            promise.then(function(data) {
                var p = [];
                var c = [];
                for (var i = 0; i < data.length; i++) {
                    var item = data[i];

                    p.push({
                        value: item['p'].substr(0, 6),
                        name: item['p'].substr(7)
                    });



                    for (var j = 0; j < item['c'].length; j++) {
                        if (item['c'][j].substr(0, 6) === p[i].value) {
                            c.push({
                                value1: item['c'][j].substr(0, 6),
                                area: item['c'][j].substr(7)
                            });
                        }
                    }


                }

                vm.city = c;

                vm.province = p;

            }).catch(function(error) {
                CommonService.showError(error);
            });
        }

        /**
         * 获取城市列表
         * @function getCity
         * @memberof BindBankcardController
         */
        function getCity(value) {
            alert(1);
            var value = vm.model.provCode;
            var promise = CommonService.getCitylist();

            promise.then(function(data) {
                var c = [];


                for (var i = 0; i < data.length; i++) {
                    var item = data[i];
                    if (item['c'][0].substr(0, 6) === value) {

                        for (var j = 0; j < item['c'].length; j++) {
                            c.push({
                                value1: item['c'][j].substr(0, 6),
                                area: item['c'][j].substr(7)
                            });
                        }
                    }
                }


                vm.city = c;


            }).catch(function(error) {
                CommonService.showError(error);
            });
        }


        /**
         * 提交转账验证信息方法
         * @memberof TransferDefaultInputController
         * @function totransferDefaultService
         * @description 提交转账验证信息描述
         */
        function totransferDefaultService() {
            if (vm.transForm.$valid) {
                var params = {
                    payaccount: vm.model.payaccount,
                    payamount: vm.model.payamount,
                    collectaccount: vm.model.collectaccount,
                    custName: vm.model.custName,
                    payaccountbank: vm.model.payaccountbank,
                    bankNo: vm.model.bankNo,
                    mobile: vm.model.mobile,
                    transferuse: vm.model.transferuse,
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));
                $state.go('transferDefaultService');
            }

        }

        /**
         * 返回转账页面
         * @memberof TransferDefaultInputController
         * @function backTranfer
         * @description 返回转账页面
         */
        function backTranfer() {
            $state.go('accountManagement');

        }

        /**
         * @memberof TransferDefaultInputController
         * @param contactsMessage
         * @description 接收广播信息方法       'accountNo': '6226170101699854',
        'aliasName': '公司',
        'bankLogoUrl': '中国工商银行股份有限公司北京崇文门外大街支行',
        'customerName': '张三'
         */
        $scope.$on('contactsMessage', function(event, data) {

            vm.products = data;
            vm.model.custName = vm.products.customerName;
            vm.model.bankNo = vm.products.bankLogoUrl;
            vm.model.collectaccount = vm.products.accountNo;
            vm.model.payaccountbank = vm.products.aliasName;
        });

        /**
         * @memberof TransferDefaultInputController
         * @param bankMessage
         * @description 接收广播信息方法（开户行名称信息）
         */
        $scope.$on('bankMessage', function(event, data) {

            vm.products = data;
            vm.model.payaccountbank = vm.products.bankName;
        });

        /**
         * @memberof TransferDefaultInputController
         * @param bankAddressMessage
         * @description 接收广播信息方法（开户行网点信息）
         */
        $scope.$on('bankAddressMessage', function(event, data) {

            vm.products = data;
            vm.model.bankNo = vm.products.bankName;
        });


        /**
         * 显示弹出录入页面
         * @memberof selfRegistrationController
         * @function showAgreement
         * @description 显示相关协议描述
         */
        function bankList() {
            ModalService.showModal({
                templateUrl: 'app/components/consumerTransfer/transferDefaultInput/bankList.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }

        function bankListSearch() {
            ModalService.showModal({
                templateUrl: 'app/components/consumerTransfer/transferDefaultInput/bankListSearch.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }

        function contactsList() {
            ModalService.showModal({
                templateUrl: 'app/components/consumerTransfer/transferDefaultInput/contactsList.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }



        $scope.division = {
            "北京市": {
                "北京市": ["东城区", "西城区", "崇文区", "宣武区", "朝阳区", "丰台区", "石景山区", "海淀区", "门头沟区", "房山区", "通州区", "顺义区", "昌平区", "大兴区", "怀柔区", "平谷区", "密云县", "延庆县"]
            },
            "上海市": {
                "上海市": ["黄浦区", "卢湾区", "徐汇区", "长宁区", "静安区", "普陀区", "闸北区", "虹口区", "杨浦区", "闵行区", "宝山区", "嘉定区", "浦东新区", "金山区", "松江区", "青浦区", "南汇区", "奉贤区", "崇明县"]
            },
            "天津市": {
                "天津市": ["和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区", "东丽区", "西青区", "津南区", "北辰区", "武清区", "宝坻区", "宁河县", "静海县", "蓟县"]
            },
            "重庆市": {
                "重庆市": ["万州区", "涪陵区", "渝中区", "大渡口区", "江北区", "沙坪坝区", "九龙坡区", "南岸区", "北碚区", "万盛区", "双桥区", "渝北区", "巴南区", "黔江区", "长寿区", "綦江县", "潼南县", "铜梁县", "大足县", "荣昌县", "璧山县", "梁平县", "城口县", "丰都县", "垫江县", "武隆县", "忠县", "开县", "云阳县", "奉节县", "巫山县", "巫溪县", "石柱土家族自治县", "秀山土家族苗族自治县", "酉阳土家族苗族自治县", "彭水苗族土家族自治县", "江津市", "合川市", "永川市", "南川市"]
            },
            "四川省": {
                "成都市": ["锦江区", "青羊区", "金牛区", "武侯区", "成华区", "龙泉驿区", "青白江区", "新都区", "温江县", "金堂县", "双流县", "郫县", "大邑县", "蒲江县", "新津县", "都江堰市", "彭州市", "邛崃市", "崇州市"],
                "自贡市": ["自流井区", "贡井区", "大安区", "沿滩区", "荣县", "富顺县"],
                "攀枝花市": ["东区", "西区", "仁和区", "米易县", "盐边县"],
                "泸州市": ["江阳区", "纳溪区", "龙马潭区", "泸县", "合江县", "叙永县", "古蔺县"],
                "德阳市": ["旌阳区", "中江县", "罗江县", "广汉市", "什邡市", "绵竹市"],
                "绵阳市": ["涪城区", "游仙区", "三台县", "盐亭县", "安县", "梓潼县", "北川羌族自治县", "平武县", "江油市"],
                "广元市": ["市中区", "元坝区", "朝天区", "旺苍县", "青川县", "剑阁县", "苍溪县"],
                "遂宁市": ["船山区", "安居区", "蓬溪县", "射洪县", "大英县"],
                "内江市": ["市中区", "东兴区", "威远县", "资中县", "隆昌县"],
                "乐山市": ["市中区", "沙湾区", "五通桥区", "金口河区", "犍为县", "井研县", "夹江县", "沐川县", "峨边彝族自治县", "马边彝族自治县", "峨眉山市"],
                "南充市": ["顺庆区", "高坪区", "嘉陵区", "南部县", "营山县", "蓬安县", "仪陇县", "西充县", "阆中市"],
                "眉山市": ["东坡区", "仁寿县", "彭山县", "洪雅县", "丹棱县", "青神县"],
                "宜宾市": ["翠屏区", "宜宾县", "南溪县", "江安县", "长宁县", "高县", "珙县", "筠连县", "兴文县", "屏山县"],
                "广安市": ["广安区", "岳池县", "武胜县", "邻水县", "华莹市"],
                "达州市": ["通川区", "达县", "宣汉县", "开江县", "大竹县", "渠县", "万源市"],
                "雅安市": ["雨城区", "名山县", "荥经县", "汉源县", "石棉县", "天全县", "芦山县", "宝兴县"],
                "巴中市": ["巴州区", "通江县", "南江县", "平昌县"],
                "资阳市": ["雁江区", "安岳县", "乐至县", "简阳市"],
                "阿坝藏族羌族自治州": ["汶川县", "理县", "茂县", "松潘县", "九寨沟县", "金川县", "小金县", "黑水县", "马尔康县", "壤塘县", "阿坝县", "若尔盖县", "红原县"],
                "甘孜藏族自治州": ["康定县", "泸定县", "丹巴县", "九龙县", "雅江县", "道孚县", "炉霍县", "甘孜县", "新龙县", "德格县", "白玉县", "石渠县", "色达县", "理塘县", "巴塘县", "乡城县", "稻城县", "得荣县"],
                "凉山彝族自治州": ["西昌市", "木里藏族自治县", "盐源县", "德昌县", "会理县", "会东县", "宁南县", "普格县", "布拖县", "金阳县", "昭觉县", "喜德县", "冕宁县", "越西县", "甘洛县", "美姑县", "雷波县"]
            },
            "贵州省": {
                "贵阳市": ["南明区", "云岩区", "花溪区", "乌当区", "白云区", "小河区", "开阳县", "息烽县", "修文县", "清镇市"],
                "六盘水市": ["钟山区", "六枝特区", "水城县", "盘县"],
                "遵义市": ["红花岗区", "汇川区", "遵义县", "桐梓县", "绥阳县", "正安县", "道真仡佬族苗族自治县", "务川仡佬族苗族自治县", "凤冈县", "湄潭县", "余庆县", "习水县", "赤水市", "仁怀市"],
                "安顺市": ["西秀区", "平坝县", "普定县", "镇宁布依族苗族自治县", "关岭布依族苗族自治县", "紫云苗族布依族自治县"],
                "铜仁地区": ["铜仁市", "江口县", "玉屏侗族自治县", "石阡县", "思南县", "印江土家族苗族自治县", "德江县", "沿河土家族自治县", "松桃苗族自治县", "万山特区"],
                "黔西南布依族苗族自治州": ["兴义市", "兴仁县", "普安县", "晴隆县", "贞丰县", "望谟县", "册亨县", "安龙县"],
                "毕节地区": ["毕节市", "大方县", "黔西县", "金沙县", "织金县", "纳雍县", "威宁彝族回族苗族自治县", "赫章县"],
                "黔东南苗族侗族自治州": ["凯里市", "黄平县", "施秉县", "三穗县", "镇远县", "岑巩县", "天柱县", "锦屏县", "剑河县", "台江县", "黎平县", "榕江县", "从江县", "雷山县", "麻江县", "丹寨县"],
                "黔南布依族苗族自治州": ["都匀市", "福泉市", "荔波县", "贵定县", "瓮安县", "独山县", "平塘县", "罗甸县", "长顺县", "龙里县", "惠水县", "三都水族自治县"]
            },
            "云南省": {
                "昆明市": ["五华区", "盘龙区", "官渡区", "西山区", "东川区", "呈贡县", "晋宁县", "富民县", "宜良县", "石林彝族自治县", "嵩明县", "禄劝彝族苗族自治县", "寻甸回族彝族自治县", "安宁市"],
                "曲靖市": ["麒麟区", "马龙县", "陆良县", "师宗县", "罗平县", "富源县", "会泽县", "沾益县", "宣威市"],
                "玉溪市": ["红塔区", "江川县", "澄江县", "通海县", "华宁县", "易门县", "峨山彝族自治县", "新平彝族傣族自治县", "元江哈尼族彝族傣族自治县"],
                "保山市": ["隆阳区", "施甸县", "腾冲县", "龙陵县", "昌宁县"],
                "昭通市": ["昭阳区", "鲁甸县", "巧家县", "盐津县", "大关县", "永善县", "绥江县", "镇雄县", "彝良县", "威信县", "水富县"],
                "丽江市": ["古城区", "玉龙纳西族自治县", "永胜县", "华坪县", "宁蒗彝族自治县"],
                "思茅市": ["翠云区", "普洱哈尼族彝族自治县", "墨江哈尼族自治县", "景东彝族自治县", "景谷傣族彝族自治县", "镇沅彝族哈尼族拉祜族自治县", "江城哈尼族彝族自治县", "孟连傣族拉祜族佤族自治县", "澜沧拉祜族自治县", "西盟佤族自治县"],
                "临沧市": ["临翔区", "凤庆县", "云县", "永德县", "镇康县", "双江拉祜族佤族布朗族傣族自治县", "耿马傣族佤族自治县", "沧源佤族自治县"],
                "楚雄彝族自治州": ["楚雄市", "双柏县", "牟定县", "南华县", "姚安县", "大姚县", "永仁县", "元谋县", "武定县", "禄丰县"],
                "红河哈尼族彝族自治州": ["个旧市", "开远市", "蒙自县", "屏边苗族自治县", "建水县", "石屏县", "弥勒县", "泸西县", "元阳县", "红河县", "金平苗族瑶族傣族自治县", "绿春县", "河口瑶族自治县"],
                "文山壮族苗族自治州": ["文山县", "砚山县", "西畴县", "麻栗坡县", "马关县", "丘北县", "广南县", "富宁县"],
                "西双版纳傣族自治州": ["景洪市", "勐海县", "勐腊县"],
                "大理白族自治州": ["大理市", "漾濞彝族自治县", "祥云县", "宾川县", "弥渡县", "南涧彝族自治县", "巍山彝族回族自治县", "永平县", "云龙县", "洱源县", "剑川县", "鹤庆县"],
                "德宏傣族景颇族自治州": ["瑞丽市", "潞西市", "梁河县", "盈江县", "陇川县"],
                "怒江傈僳族自治州": ["泸水县", "福贡县", "贡山独龙族怒族自治县", "兰坪白族普米族自治县"],
                "迪庆藏族自治州": ["香格里拉县", "德钦县", "维西傈僳族自治县"]
            },
            "西藏省": {
                "拉萨市": ["城关区", "林周县", "当雄县", "尼木县", "曲水县", "堆龙德庆县", "达孜县", "墨竹工卡县"],
                "昌都地区": ["昌都县", "江达县", "贡觉县", "类乌齐县", "丁青县", "察雅县", "八宿县", "左贡县", "芒康县", "洛隆县", "边坝县"],
                "山南地区": ["乃东县", "扎囊县", "贡嘎县", "桑日县", "琼结县", "曲松县", "措美县", "洛扎县", "加查县", "隆子县", "错那县", "浪卡子县"],
                "日喀则地区": ["日喀则市", "南木林县", "江孜县", "定日县", "萨迦县", "拉孜县", "昂仁县", "谢通门县", "白朗县", "仁布县", "康马县", "定结县", "仲巴县", "亚东县", "吉隆县", "聂拉木县", "萨嘎县", "岗巴县"],
                "那曲地区": ["那曲县", "嘉黎县", "比如县", "聂荣县", "安多县", "申扎县", "索县", "班戈县", "巴青县", "尼玛县"],
                "阿里地区": ["普兰县", "札达县", "噶尔县", "日土县", "革吉县", "改则县", "措勤县"],
                "林芝地区": ["林芝县", "工布江达县", "米林县", "墨脱县", "波密县", "察隅县", "朗县"]
            },
            "河南省": {
                "郑州市": ["中原区", "二七区", "管城回族区", "金水区", "上街区", "邙山区", "中牟县", "巩义市", "荥阳市", "新密市", "新郑市", "登封市"],
                "开封市": ["龙亭区", "顺河回族区", "鼓楼区", "南关区", "郊区", "杞县", "通许县", "尉氏县", "开封县", "兰考县"],
                "洛阳市": ["老城区", "西工区", "廛河回族区", "涧西区", "吉利区", "洛龙区", "孟津县", "新安县", "栾川县", "嵩县", "汝阳县", "宜阳县", "洛宁县", "伊川县", "偃师市"],
                "平顶山市": ["新华区", "卫东区", "石龙区", "湛河区", "宝丰县", "叶县", "鲁山县", "郏县", "舞钢市", "汝州市"],
                "安阳市": ["文峰区", "北关区", "殷都区", "龙安区", "安阳县", "汤阴县", "滑县", "内黄县", "林州市"],
                "鹤壁市": ["鹤山区", "山城区", "淇滨区", "浚县", "淇县"],
                "新乡市": ["红旗区", "卫滨区", "凤泉区", "牧野区", "新乡县", "获嘉县", "原阳县", "延津县", "封丘县", "长垣县", "卫辉市", "辉县市"],
                "焦作市": ["解放区", "中站区", "马村区", "山阳区", "修武县", "博爱县", "武陟县", "温县", "济源市", "沁阳市", "孟州市"],
                "濮阳市": ["华龙区", "清丰县", "南乐县", "范县", "台前县", "濮阳县"],
                "许昌市": ["魏都区", "许昌县", "鄢陵县", "襄城县", "禹州市", "长葛市"],
                "漯河市": ["源汇区", "郾城区", "召陵区", "舞阳县", "临颍县"],
                "三门峡市": ["湖滨区", "渑池县", "陕县", "卢氏县", "义马市", "灵宝市"],
                "南阳市": ["宛城区", "卧龙区", "南召县", "方城县", "西峡县", "镇平县", "内乡县", "淅川县", "社旗县", "唐河县", "新野县", "桐柏县", "邓州市"],
                "商丘市": ["梁园区", "睢阳区", "民权县", "睢县", "宁陵县", "柘城县", "虞城县", "夏邑县", "永城市"],
                "信阳市": ["师河区", "平桥区", "罗山县", "光山县", "新县", "商城县", "固始县", "潢川县", "淮滨县", "息县"],
                "周口市": ["川汇区", "扶沟县", "西华县", "商水县", "沈丘县", "郸城县", "淮阳县", "太康县", "鹿邑县", "项城市"],
                "驻马店市": ["驿城区", "西平县", "上蔡县", "平舆县", "正阳县", "确山县", "泌阳县", "汝南县", "遂平县", "新蔡县"]
            },
            "湖北省": {
                "武汉市": ["江岸区", "江汉区", "乔口区", "汉阳区", "武昌区", "青山区", "洪山区", "东西湖区", "汉南区", "蔡甸区", "江夏区", "黄陂区", "新洲区"],
                "黄石市": ["黄石港区", "西塞山区", "下陆区", "铁山区", "阳新县", "大冶市"],
                "十堰市": ["茅箭区", "张湾区", "郧县", "郧西县", "竹山县", "竹溪县", "房县", "丹江口市"],
                "宜昌市": ["西陵区", "伍家岗区", "点军区", "猇亭区", "夷陵区", "远安县", "兴山县", "秭归县", "长阳土家族自治县", "五峰土家族自治县", "宜都市", "当阳市", "枝江市"],
                "襄樊市": ["襄城区", "樊城区", "襄阳区", "南漳县", "谷城县", "保康县", "老河口市", "枣阳市", "宜城市"],
                "鄂州市": ["梁子湖区", "华容区", "鄂城区"],
                "荆门市": ["东宝区", "掇刀区", "京山县", "沙洋县", "钟祥市"],
                "孝感市": ["孝南区", "孝昌县", "大悟县", "云梦县", "应城市", "安陆市", "汉川市"],
                "荆州市": ["沙市区", "荆州区", "公安县", "监利县", "江陵县", "石首市", "洪湖市", "松滋市"],
                "黄冈市": ["黄州区", "团风县", "红安县", "罗田县", "英山县", "浠水县", "蕲春县", "黄梅县", "麻城市", "武穴市"],
                "咸宁市": ["咸安区", "嘉鱼县", "通城县", "崇阳县", "通山县", "赤壁市"],
                "随州市": ["曾都区", "广水市"],
                "恩施土家族苗族自治州": ["恩施市", "利川市", "建始县", "巴东县", "宣恩县", "咸丰县", "来凤县", "鹤峰县"],
                "省直辖行政单位": ["仙桃市", "潜江市", "天门市", "神农架林区"]
            },
            "湖南省": {
                "长沙市": ["芙蓉区", "天心区", "岳麓区", "开福区", "雨花区", "长沙县", "望城县", "宁乡县", "浏阳市"],
                "株洲市": ["荷塘区", "芦淞区", "石峰区", "天元区", "株洲县", "攸县", "茶陵县", "炎陵县", "醴陵市"],
                "湘潭市": ["雨湖区", "岳塘区", "湘潭县", "湘乡市", "韶山市"],
                "衡阳市": ["珠晖区", "雁峰区", "石鼓区", "蒸湘区", "南岳区", "衡阳县", "衡南县", "衡山县", "衡东县", "祁东县", "耒阳市", "常宁市"],
                "邵阳市": ["双清区", "大祥区", "北塔区", "邵东县", "新邵县", "邵阳县", "隆回县", "洞口县", "绥宁县", "新宁县", "城步苗族自治县", "武冈市"],
                "岳阳市": ["岳阳楼区", "云溪区", "君山区", "岳阳县", "华容县", "湘阴县", "平江县", "汨罗市", "临湘市"],
                "常德市": ["武陵区", "鼎城区", "安乡县", "汉寿县", "澧县", "临澧县", "桃源县", "石门县", "津市市"],
                "张家界市": ["永定区", "武陵源区", "慈利县", "桑植县"],
                "益阳市": ["资阳区", "赫山区", "南县", "桃江县", "安化县", "沅江市"],
                "郴州市": ["北湖区", "苏仙区", "桂阳县", "宜章县", "永兴县", "嘉禾县", "临武县", "汝城县", "桂东县", "安仁县", "资兴市"],
                "永州市": ["芝山区", "冷水滩区", "祁阳县", "东安县", "双牌县", "道县", "江永县", "宁远县", "蓝山县", "新田县", "江华瑶族自治县"],
                "怀化市": ["鹤城区", "中方县", "沅陵县", "辰溪县", "溆浦县", "会同县", "麻阳苗族自治县", "新晃侗族自治县", "芷江侗族自治县", "靖州苗族侗族自治县", "通道侗族自治县", "洪江市"],
                "娄底市": ["娄星区", "双峰县", "新化县", "冷水江市", "涟源市"],
                "湘西土家族苗族自治州": ["吉首市", "泸溪县", "凤凰县", "花垣县", "保靖县", "古丈县", "永顺县", "龙山县"]
            },
            "广东省": {
                "广州市": ["东山区", "荔湾区", "越秀区", "海珠区", "天河区", "芳村区", "白云区", "黄埔区", "番禺区", "花都区", "增城市", "从化市"],
                "韶关市": ["武江区", "浈江区", "曲江区", "始兴县", "仁化县", "翁源县", "乳源瑶族自治县", "新丰县", "乐昌市", "南雄市"],
                "深圳市": ["罗湖区", "福田区", "南山区", "宝安区", "龙岗区", "盐田区"],
                "珠海市": ["香洲区", "斗门区", "金湾区"],
                "汕头市": ["龙湖区", "金平区", "濠江区", "潮阳区", "潮南区", "澄海区", "南澳县"],
                "佛山市": ["禅城区", "南海区", "顺德区", "三水区", "高明区"],
                "江门市": ["蓬江区", "江海区", "新会区", "台山市", "开平市", "鹤山市", "恩平市"],
                "湛江市": ["赤坎区", "霞山区", "坡头区", "麻章区", "遂溪县", "徐闻县", "廉江市", "雷州市", "吴川市"],
                "茂名市": ["茂南区", "茂港区", "电白县", "高州市", "化州市", "信宜市"],
                "肇庆市": ["端州区", "鼎湖区", "广宁县", "怀集县", "封开县", "德庆县", "高要市", "四会市"],
                "惠州市": ["惠城区", "惠阳区", "博罗县", "惠东县", "龙门县"],
                "梅州市": ["梅江区", "梅县", "大埔县", "丰顺县", "五华县", "平远县", "蕉岭县", "兴宁市"],
                "汕尾市": ["城区", "海丰县", "陆河县", "陆丰市"],
                "河源市": ["源城区", "紫金县", "龙川县", "连平县", "和平县", "东源县"],
                "阳江市": ["江城区", "阳西县", "阳东县", "阳春市"],
                "清远市": ["清城区", "佛冈县", "阳山县", "连山壮族瑶族自治县", "连南瑶族自治县", "清新县", "英德市", "连州市"],
                "东莞市": ["东莞市"],
                "中山市": ["中山市"],
                "潮州市": ["湘桥区", "潮安县", "饶平县"],
                "揭阳市": ["榕城区", "揭东县", "揭西县", "惠来县", "普宁市"],
                "云浮市": ["云城区", "新兴县", "郁南县", "云安县", "罗定市"]
            },
            "广西省": {
                "南宁市": ["兴宁区", "青秀区", "江南区", "西乡塘区", "良庆区", "邕宁区", "武鸣县", "隆安县", "马山县", "上林县", "宾阳县", "横县"],
                "柳州市": ["城中区", "鱼峰区", "柳南区", "柳北区", "柳江县", "柳城县", "鹿寨县", "融安县", "融水苗族自治县", "三江侗族自治县"],
                "桂林市": ["秀峰区", "叠彩区", "象山区", "七星区", "雁山区", "阳朔县", "临桂县", "灵川县", "全州县", "兴安县", "永福县", "灌阳县", "龙胜各族自治县", "资源县", "平乐县", "荔蒲县", "恭城瑶族自治县"],
                "梧州市": ["万秀区", "蝶山区", "长洲区", "苍梧县", "藤县", "蒙山县", "岑溪市"],
                "北海市": ["海城区", "银海区", "铁山港区", "合浦县"],
                "防城港市": ["港口区", "防城区", "上思县", "东兴市"],
                "钦州市": ["钦南区", "钦北区", "灵山县", "浦北县"],
                "贵港市": ["港北区", "港南区", "覃塘区", "平南县", "桂平市"],
                "玉林市": ["玉州区", "容县", "陆川县", "博白县", "兴业县", "北流市"],
                "百色市": ["右江区", "田阳县", "田东县", "平果县", "德保县", "靖西县", "那坡县", "凌云县", "乐业县", "田林县", "西林县", "隆林各族自治县"],
                "贺州市": ["八步区", "昭平县", "钟山县", "富川瑶族自治县"],
                "河池市": ["金城江区", "南丹县", "天峨县", "凤山县", "东兰县", "罗城仫佬族自治县", "环江毛南族自治县", "巴马瑶族自治县", "都安瑶族自治县", "大化瑶族自治县", "宜州市"],
                "来宾市": ["兴宾区", "忻城县", "象州县", "武宣县", "金秀瑶族自治县", "合山市"],
                "崇左市": ["江洲区", "扶绥县", "宁明县", "龙州县", "大新县", "天等县", "凭祥市"]
            },
            "陕西省": {
                "西安市": ["新城区", "碑林区", "莲湖区", "灞桥区", "未央区", "雁塔区", "阎良区", "临潼区", "长安区", "蓝田县", "周至县", "户县", "高陵县"],
                "铜川市": ["王益区", "印台区", "耀州区", "宜君县"],
                "宝鸡市": ["渭滨区", "金台区", "陈仓区", "凤翔县", "岐山县", "扶风县", "眉县", "陇县", "千阳县", "麟游县", "凤县", "太白县"],
                "咸阳市": ["秦都区", "杨凌区", "渭城区", "三原县", "泾阳县", "乾县", "礼泉县", "永寿县", "彬县", "长武县", "旬邑县", "淳化县", "武功县", "兴平市"],
                "渭南市": ["临渭区", "华县", "潼关县", "大荔县", "合阳县", "澄城县", "蒲城县", "白水县", "富平县", "韩城市", "华阴市"],
                "延安市": ["宝塔区", "延长县", "延川县", "子长县", "安塞县", "志丹县", "吴旗县", "甘泉县", "富县", "洛川县", "宜川县", "黄龙县", "黄陵县"],
                "汉中市": ["汉台区", "南郑县", "城固县", "洋县", "西乡县", "勉县", "宁强县", "略阳县", "镇巴县", "留坝县", "佛坪县"],
                "榆林市": ["榆阳区", "神木县", "府谷县", "横山县", "靖边县", "定边县", "绥德县", "米脂县", "佳县", "吴堡县", "清涧县", "子洲县"],
                "安康市": ["汉滨区", "汉阴县", "石泉县", "宁陕县", "紫阳县", "岚皋县", "平利县", "镇坪县", "旬阳县", "白河县"],
                "商洛市": ["商州区", "洛南县", "丹凤县", "商南县", "山阳县", "镇安县", "柞水县"]
            },
            "甘肃省": {
                "兰州市": ["城关区", "七里河区", "西固区", "安宁区", "红古区", "永登县", "皋兰县", "榆中县"],
                "嘉峪关市": ["市辖区"],
                "金昌市": ["金川区", "永昌县"],
                "白银市": ["白银区", "平川区", "靖远县", "会宁县", "景泰县"],
                "天水市": ["秦城区", "北道区", "清水县", "秦安县", "甘谷县", "武山县", "张家川回族自治县"],
                "武威市": ["凉州区", "民勤县", "古浪县", "天祝藏族自治县"],
                "张掖市": ["甘州区", "肃南裕固族自治县", "民乐县", "临泽县", "高台县", "山丹县"],
                "平凉市": ["崆峒区", "泾川县", "灵台县", "崇信县", "华亭县", "庄浪县", "静宁县"],
                "酒泉市": ["肃州区", "金塔县", "安西县", "肃北蒙古族自治县", "阿克塞哈萨克族自治县", "玉门市", "敦煌市"],
                "庆阳市": ["西峰区", "庆城县", "环县", "华池县", "合水县", "正宁县", "宁县", "镇原县"],
                "定西市": ["安定区", "通渭县", "陇西县", "渭源县", "临洮县", "漳县", "岷县"],
                "陇南市": ["武都区", "成县", "文县", "宕昌县", "康县", "西和县", "礼县", "徽县", "两当县"],
                "临夏回族自治州": ["临夏市", "临夏县", "康乐县", "永靖县", "广河县", "和政县", "东乡族自治县", "积石山保安族东乡族撒拉族自治县"],
                "甘南藏族自治州": ["合作市", "临潭县", "卓尼县", "舟曲县", "迭部县", "玛曲县", "碌曲县", "夏河县"]
            },
            "青海省": {
                "西宁市": ["城东区", "城中区", "城西区", "城北区", "大通回族土族自治县", "湟中县", "湟源县"],
                "海东地区": ["平安县", "民和回族土族自治县", "乐都县", "互助土族自治县", "化隆回族自治县", "循化撒拉族自治县"],
                "海北藏族自治州": ["门源回族自治县", "祁连县", "海晏县", "刚察县"],
                "黄南藏族自治州": ["同仁县", "尖扎县", "泽库县", "河南蒙古族自治县"],
                "海南藏族自治州": ["共和县", "同德县", "贵德县", "兴海县", "贵南县"],
                "果洛藏族自治州": ["玛沁县", "班玛县", "甘德县", "达日县", "久治县", "玛多县"],
                "玉树藏族自治州": ["玉树县", "杂多县", "称多县", "治多县", "囊谦县", "曲麻莱县"],
                "海西蒙古族藏族自治州": ["格尔木市", "德令哈市", "乌兰县", "都兰县", "天峻县"]
            },
            "宁夏省": {
                "银川市": ["兴庆区", "西夏区", "金凤区", "永宁县", "贺兰县", "灵武市"],
                "石嘴山市": ["大武口区", "惠农区", "平罗县"],
                "吴忠市": ["利通区", "盐池县", "同心县", "青铜峡市"],
                "固原市": ["原州区", "西吉县", "隆德县", "泾源县", "彭阳县", "海原县"],
                "中卫市": ["沙坡头区", "中宁县"]
            },
            "新疆省": {
                "乌鲁木齐市": ["天山区", "沙依巴克区", "新市区", "水磨沟区", "头屯河区", "达坂城区", "东山区", "乌鲁木齐县"],
                "克拉玛依市": ["独山子区", "克拉玛依区", "白碱滩区", "乌尔禾区"],
                "吐鲁番地区": ["吐鲁番市", "鄯善县", "托克逊县"],
                "哈密地区": ["哈密市", "巴里坤哈萨克自治县", "伊吾县"],
                "昌吉回族自治州": ["昌吉市", "阜康市", "米泉市", "呼图壁县", "玛纳斯县", "奇台县", "吉木萨尔县", "木垒哈萨克自治县"],
                "博尔塔拉蒙古自治州": ["博乐市", "精河县", "温泉县"],
                "巴音郭楞蒙古自治州": ["库尔勒市", "轮台县", "尉犁县", "若羌县", "且末县", "焉耆回族自治县", "和静县", "和硕县", "博湖县"],
                "阿克苏地区": ["阿克苏市", "温宿县", "库车县", "沙雅县", "新和县", "拜城县", "乌什县", "阿瓦提县", "柯坪县"],
                "克孜勒苏柯尔克孜自治州": ["阿图什市", "阿克陶县", "阿合奇县", "乌恰县"],
                "喀什地区": ["喀什市", "疏附县", "疏勒县", "英吉沙县", "泽普县", "莎车县", "叶城县", "麦盖提县", "岳普湖县", "伽师县", "巴楚县", "塔什库尔干塔吉克自治县"],
                "和田地区": ["和田市", "和田县", "墨玉县", "皮山县", "洛浦县", "策勒县", "于田县", "民丰县"],
                "伊犁哈萨克自治州": ["伊宁市", "奎屯市", "伊宁县", "察布查尔锡伯自治县", "霍城县", "巩留县", "新源县", "昭苏县", "特克斯县", "尼勒克县"],
                "塔城地区": ["塔城市", "乌苏市", "额敏县", "沙湾县", "托里县", "裕民县", "和布克赛尔蒙古自治县"],
                "阿勒泰地区": ["阿勒泰市", "布尔津县", "富蕴县", "福海县", "哈巴河县", "青河县", "吉木乃县"],
                "省直辖行政单位": ["石河子市", "阿拉尔市", "图木舒克市", "五家渠市"]
            },
            "河北省": {
                "石家庄市": ["长安区", "桥东区", "桥西区", "新华区", "井陉矿区", "裕华区", "井陉县", "正定县", "栾城县", "行唐县", "灵寿县", "高邑县", "深泽县", "赞皇县", "无极县", "平山县", "元氏县", "赵县", "辛集市", "藁城市", "晋州市", "新乐市", "鹿泉市"],
                "唐山市": ["路南区", "路北区", "古冶区", "开平区", "丰南区", "丰润区", "滦县", "滦南县", "乐亭县", "迁西县", "玉田县", "唐海县", "遵化市", "迁安市"],
                "秦皇岛市": ["海港区", "山海关区", "北戴河区", "青龙满族自治县", "昌黎县", "抚宁县", "卢龙县"],
                "邯郸市": ["邯山区", "丛台区", "复兴区", "峰峰矿区", "邯郸县", "临漳县", "成安县", "大名县", "涉县", "磁县", "肥乡县", "永年县", "邱县", "鸡泽县", "广平县", "馆陶县", "魏县", "曲周县", "武安市"],
                "邢台市": ["桥东区", "桥西区", "邢台县", "临城县", "内丘县", "柏乡县", "隆尧县", "任县", "南和县", "宁晋县", "巨鹿县", "新河县", "广宗县", "平乡县", "威县", "清河县", "临西县", "南宫市", "沙河市"],
                "保定市": ["新市区", "北市区", "南市区", "满城县", "清苑县", "涞水县", "阜平县", "徐水县", "定兴县", "唐县", "高阳县", "容城县", "涞源县", "望都县", "安新县", "易县", "曲阳县", "蠡县", "顺平县", "博野县", "雄县", "涿州市", "定州市", "安国市", "高碑店市"],
                "张家口市": ["桥东区", "桥西区", "宣化区", "下花园区", "宣化县", "张北县", "康保县", "沽源县", "尚义县", "蔚县", "阳原县", "怀安县", "万全县", "怀来县", "涿鹿县", "赤城县", "崇礼县"],
                "承德市": ["双桥区", "双滦区", "鹰手营子矿区", "承德县", "兴隆县", "平泉县", "滦平县", "隆化县", "丰宁满族自治县", "宽城满族自治县", "围场满族蒙古族自治县"],
                "沧州市": ["新华区", "运河区", "沧县", "青县", "东光县", "海兴县", "盐山县", "肃宁县", "南皮县", "吴桥县", "献县", "孟村回族自治县", "泊头市", "任丘市", "黄骅市", "河间市"],
                "廊坊市": ["安次区", "广阳区", "固安县", "永清县", "香河县", "大城县", "文安县", "大厂回族自治县", "霸州市", "三河市"],
                "衡水市": ["桃城区", "枣强县", "武邑县", "武强县", "饶阳县", "安平县", "故城县", "景县", "阜城县", "冀州市", "深州市"]
            },
            "山西省": {
                "太原市": ["小店区", "迎泽区", "杏花岭区", "尖草坪区", "万柏林区", "晋源区", "清徐县", "阳曲县", "娄烦县", "古交市"],
                "大同市": ["城区", "矿区", "南郊区", "新荣区", "阳高县", "天镇县", "广灵县", "灵丘县", "浑源县", "左云县", "大同县"],
                "阳泉市": ["城区", "矿区", "郊区", "平定县", "盂县"],
                "长治市": ["城区", "郊区", "长治县", "襄垣县", "屯留县", "平顺县", "黎城县", "壶关县", "长子县", "武乡县", "沁县", "沁源县", "潞城市"],
                "晋城市": ["城区", "沁水县", "阳城县", "陵川县", "泽州县", "高平市"],
                "朔州市": ["朔城区", "平鲁区", "山阴县", "应县", "右玉县", "怀仁县"],
                "晋中市": ["榆次区", "榆社县", "左权县", "和顺县", "昔阳县", "寿阳县", "太谷县", "祁县", "平遥县", "灵石县", "介休市"],
                "运城市": ["盐湖区", "临猗县", "万荣县", "闻喜县", "稷山县", "新绛县", "绛县", "垣曲县", "夏县", "平陆县", "芮城县", "永济市", "河津市"],
                "忻州市": ["忻府区", "定襄县", "五台县", "代县", "繁峙县", "宁武县", "静乐县", "神池县", "五寨县", "岢岚县", "河曲县", "保德县", "偏关县", "原平市"],
                "临汾市": ["尧都区", "曲沃县", "翼城县", "襄汾县", "洪洞县", "古县", "安泽县", "浮山县", "吉县", "乡宁县", "大宁县", "隰县", "永和县", "蒲县", "汾西县", "侯马市", "霍州市"],
                "吕梁市": ["离石区", "文水县", "交城县", "兴县", "临县", "柳林县", "石楼县", "岚县", "方山县", "中阳县", "交口县", "孝义市", "汾阳市"]
            },
            "内蒙古省": {
                "呼和浩特市": ["新城区", "回民区", "玉泉区", "赛罕区", "土默特左旗", "托克托县", "和林格尔县", "清水河县", "武川县"],
                "包头市": ["东河区", "昆都仑区", "青山区", "石拐区", "白云矿区", "九原区", "土默特右旗", "固阳县", "达尔罕茂明安联合旗"],
                "乌海市": ["海勃湾区", "海南区", "乌达区"],
                "赤峰市": ["红山区", "元宝山区", "松山区", "阿鲁科尔沁旗", "巴林左旗", "巴林右旗", "林西县", "克什克腾旗", "翁牛特旗", "喀喇沁旗", "宁城县", "敖汉旗"],
                "通辽市": ["科尔沁区", "科尔沁左翼中旗", "科尔沁左翼后旗", "开鲁县", "库伦旗", "奈曼旗", "扎鲁特旗", "霍林郭勒市"],
                "鄂尔多斯市": ["东胜区", "达拉特旗", "准格尔旗", "鄂托克前旗", "鄂托克旗", "杭锦旗", "乌审旗", "伊金霍洛旗"],
                "呼伦贝尔市": ["海拉尔区", "阿荣旗", "莫力达瓦达斡尔族自治旗", "鄂伦春自治旗", "鄂温克族自治旗", "陈巴尔虎旗", "新巴尔虎左旗", "新巴尔虎右旗", "满洲里市", "牙克石市", "扎兰屯市", "额尔古纳市", "根河市"],
                "巴彦淖尔市": ["临河区", "五原县", "磴口县", "乌拉特前旗", "乌拉特中旗", "乌拉特后旗", "杭锦后旗"],
                "乌兰察布市": ["集宁区", "卓资县", "化德县", "商都县", "兴和县", "凉城县", "察哈尔右翼前旗", "察哈尔右翼中旗", "察哈尔右翼后旗", "四子王旗", "丰镇市"],
                "兴安盟": ["乌兰浩特市", "阿尔山市", "科尔沁右翼前旗", "科尔沁右翼中旗", "扎赉特旗", "突泉县"],
                "锡林郭勒盟": ["二连浩特市", "锡林浩特市", "阿巴嘎旗", "苏尼特左旗", "苏尼特右旗", "东乌珠穆沁旗", "西乌珠穆沁旗", "太仆寺旗", "镶黄旗", "正镶白旗", "正蓝旗", "多伦县"],
                "阿拉善盟": ["阿拉善左旗", "阿拉善右旗", "额济纳旗"]
            },
            "江苏省": {
                "南京市": ["玄武区", "白下区", "秦淮区", "建邺区", "鼓楼区", "下关区", "浦口区", "栖霞区", "雨花台区", "江宁区", "六合区", "溧水县", "高淳县"],
                "无锡市": ["崇安区", "南长区", "北塘区", "锡山区", "惠山区", "滨湖区", "江阴市", "宜兴市"],
                "徐州市": ["鼓楼区", "云龙区", "九里区", "贾汪区", "泉山区", "丰县", "沛县", "铜山县", "睢宁县", "新沂市", "邳州市"],
                "常州市": ["天宁区", "钟楼区", "戚墅堰区", "新北区", "武进区", "溧阳市", "金坛市"],
                "苏州市": ["沧浪区", "平江区", "金阊区", "虎丘区", "吴中区", "相城区", "常熟市", "张家港市", "昆山市", "吴江市", "太仓市"],
                "南通市": ["崇川区", "港闸区", "海安县", "如东县", "启东市", "如皋市", "通州市", "海门市"],
                "连云港市": ["连云区", "新浦区", "海州区", "赣榆县", "东海县", "灌云县", "灌南县"],
                "淮安市": ["清河区", "楚州区", "淮阴区", "清浦区", "涟水县", "洪泽县", "盱眙县", "金湖县"],
                "盐城市": ["亭湖区", "盐都区", "响水县", "滨海县", "阜宁县", "射阳县", "建湖县", "东台市", "大丰市"],
                "扬州市": ["广陵区", "邗江区", "郊区", "宝应县", "仪征市", "高邮市", "江都市"],
                "镇江市": ["京口区", "润州区", "丹徒区", "丹阳市", "扬中市", "句容市"],
                "泰州市": ["海陵区", "高港区", "兴化市", "靖江市", "泰兴市", "姜堰市"],
                "宿迁市": ["宿城区", "宿豫区", "沭阳县", "泗阳县", "泗洪县"]
            },
            "浙江省": {
                "杭州市": ["上城区", "下城区", "江干区", "拱墅区", "西湖区", "滨江区", "萧山区", "余杭区", "桐庐县", "淳安县", "建德市", "富阳市", "临安市"],
                "宁波市": ["海曙区", "江东区", "江北区", "北仑区", "镇海区", "鄞州区", "象山县", "宁海县", "余姚市", "慈溪市", "奉化市"],
                "温州市": ["鹿城区", "龙湾区", "瓯海区", "洞头县", "永嘉县", "平阳县", "苍南县", "文成县", "泰顺县", "瑞安市", "乐清市"],
                "嘉兴市": ["秀城区", "秀洲区", "嘉善县", "海盐县", "海宁市", "平湖市", "桐乡市"],
                "湖州市": ["吴兴区", "南浔区", "德清县", "长兴县", "安吉县"],
                "绍兴市": ["越城区", "绍兴县", "新昌县", "诸暨市", "上虞市", "嵊州市"],
                "金华市": ["婺城区", "金东区", "武义县", "浦江县", "磐安县", "兰溪市", "义乌市", "东阳市", "永康市"],
                "衢州市": ["柯城区", "衢江区", "常山县", "开化县", "龙游县", "江山市"],
                "舟山市": ["定海区", "普陀区", "岱山县", "嵊泗县"],
                "台州市": ["椒江区", "黄岩区", "路桥区", "玉环县", "三门县", "天台县", "仙居县", "温岭市", "临海市"],
                "丽水市": ["莲都区", "青田县", "缙云县", "遂昌县", "松阳县", "云和县", "庆元县", "景宁畲族自治县", "龙泉市"]
            },
            "安徽省": {
                "合肥市": ["瑶海区", "庐阳区", "蜀山区", "包河区", "长丰县", "肥东县", "肥西县"],
                "芜湖市": ["镜湖区", "马塘区", "新芜区", "鸠江区", "芜湖县", "繁昌县", "南陵县"],
                "蚌埠市": ["龙子湖区", "蚌山区", "禹会区", "淮上区", "怀远县", "五河县", "固镇县"],
                "淮南市": ["大通区", "田家庵区", "谢家集区", "八公山区", "潘集区", "凤台县"],
                "马鞍山市": ["金家庄区", "花山区", "雨山区", "当涂县"],
                "淮北市": ["杜集区", "相山区", "烈山区", "濉溪县"],
                "铜陵市": ["铜官山区", "狮子山区", "郊区", "铜陵县"],
                "安庆市": ["迎江区", "大观区", "郊区", "怀宁县", "枞阳县", "潜山县", "太湖县", "宿松县", "望江县", "岳西县", "桐城市"],
                "黄山市": ["屯溪区", "黄山区", "徽州区", "歙县", "休宁县", "黟县", "祁门县"],
                "滁州市": ["琅琊区", "南谯区", "来安县", "全椒县", "定远县", "凤阳县", "天长市", "明光市"],
                "阜阳市": ["颍州区", "颍东区", "颍泉区", "临泉县", "太和县", "阜南县", "颍上县", "界首市"],
                "宿州市": ["墉桥区", "砀山县", "萧县", "灵璧县", "泗县"],
                "巢湖市": ["居巢区", "庐江县", "无为县", "含山县", "和县"],
                "六安市": ["金安区", "裕安区", "寿县", "霍邱县", "舒城县", "金寨县", "霍山县"],
                "亳州市": ["谯城区", "涡阳县", "蒙城县", "利辛县"],
                "池州市": ["贵池区", "东至县", "石台县", "青阳县"],
                "宣城市": ["宣州区", "郎溪县", "广德县", "泾县", "绩溪县", "旌德县", "宁国市"]
            },
            "福建省": {
                "福州市": ["鼓楼区", "台江区", "仓山区", "马尾区", "晋安区", "闽侯县", "连江县", "罗源县", "闽清县", "永泰县", "平潭县", "福清市", "长乐市"],
                "厦门市": ["思明区", "海沧区", "湖里区", "集美区", "同安区", "翔安区"],
                "莆田市": ["城厢区", "涵江区", "荔城区", "秀屿区", "仙游县"],
                "三明市": ["梅列区", "三元区", "明溪县", "清流县", "宁化县", "大田县", "尤溪县", "沙县", "将乐县", "泰宁县", "建宁县", "永安市"],
                "泉州市": ["鲤城区", "丰泽区", "洛江区", "泉港区", "惠安县", "安溪县", "永春县", "德化县", "金门县", "石狮市", "晋江市", "南安市"],
                "漳州市": ["芗城区", "龙文区", "云霄县", "漳浦县", "诏安县", "长泰县", "东山县", "南靖县", "平和县", "华安县", "龙海市"],
                "南平市": ["延平区", "顺昌县", "浦城县", "光泽县", "松溪县", "政和县", "邵武市", "武夷山市", "建瓯市", "建阳市"],
                "龙岩市": ["新罗区", "长汀县", "永定县", "上杭县", "武平县", "连城县", "漳平市"],
                "宁德市": ["蕉城区", "霞浦县", "古田县", "屏南县", "寿宁县", "周宁县", "柘荣县", "福安市", "福鼎市"]
            },
            "江西省": {
                "南昌市": ["东湖区", "西湖区", "青云谱区", "湾里区", "青山湖区", "南昌县", "新建县", "安义县", "进贤县"],
                "景德镇市": ["昌江区", "珠山区", "浮梁县", "乐平市"],
                "萍乡市": ["安源区", "湘东区", "莲花县", "上栗县", "芦溪县"],
                "九江市": ["庐山区", "浔阳区", "九江县", "武宁县", "修水县", "永修县", "德安县", "星子县", "都昌县", "湖口县", "彭泽县", "瑞昌市"],
                "新余市": ["渝水区", "分宜县"],
                "鹰潭市": ["月湖区", "余江县", "贵溪市"],
                "赣州市": ["章贡区", "赣县", "信丰县", "大余县", "上犹县", "崇义县", "安远县", "龙南县", "定南县", "全南县", "宁都县", "于都县", "兴国县", "会昌县", "寻乌县", "石城县", "瑞金市", "南康市"],
                "吉安市": ["吉州区", "青原区", "吉安县", "吉水县", "峡江县", "新干县", "永丰县", "泰和县", "遂川县", "万安县", "安福县", "永新县", "井冈山市"],
                "宜春市": ["袁州区", "奉新县", "万载县", "上高县", "宜丰县", "靖安县", "铜鼓县", "丰城市", "樟树市", "高安市"],
                "抚州市": ["临川区", "南城县", "黎川县", "南丰县", "崇仁县", "乐安县", "宜黄县", "金溪县", "资溪县", "东乡县", "广昌县"],
                "上饶市": ["信州区", "上饶县", "广丰县", "玉山县", "铅山县", "横峰县", "弋阳县", "余干县", "鄱阳县", "万年县", "婺源县", "德兴市"]
            },
            "山东省": {
                "济南市": ["历下区", "市中区", "槐荫区", "天桥区", "历城区", "长清区", "平阴县", "济阳县", "商河县", "章丘市"],
                "青岛市": ["市南区", "市北区", "四方区", "黄岛区", "崂山区", "李沧区", "城阳区", "胶州市", "即墨市", "平度市", "胶南市", "莱西市"],
                "淄博市": ["淄川区", "张店区", "博山区", "临淄区", "周村区", "桓台县", "高青县", "沂源县"],
                "枣庄市": ["市中区", "薛城区", "峄城区", "台儿庄区", "山亭区", "滕州市"],
                "东营市": ["东营区", "河口区", "垦利县", "利津县", "广饶县"],
                "烟台市": ["芝罘区", "福山区", "牟平区", "莱山区", "长岛县", "龙口市", "莱阳市", "莱州市", "蓬莱市", "招远市", "栖霞市", "海阳市"],
                "潍坊市": ["潍城区", "寒亭区", "坊子区", "奎文区", "临朐县", "昌乐县", "青州市", "诸城市", "寿光市", "安丘市", "高密市", "昌邑市"],
                "济宁市": ["市中区", "任城区", "微山县", "鱼台县", "金乡县", "嘉祥县", "汶上县", "泗水县", "梁山县", "曲阜市", "兖州市", "邹城市"],
                "泰安市": ["泰山区", "岱岳区", "宁阳县", "东平县", "新泰市", "肥城市"],
                "威海市": ["环翠区", "文登市", "荣成市", "乳山市"],
                "日照市": ["东港区", "岚山区", "五莲县", "莒县"],
                "莱芜市": ["莱城区", "钢城区"],
                "临沂市": ["兰山区", "罗庄区", "河东区", "沂南县", "郯城县", "沂水县", "苍山县", "费县", "平邑县", "莒南县", "蒙阴县", "临沭县"],
                "德州市": ["德城区", "陵县", "宁津县", "庆云县", "临邑县", "齐河县", "平原县", "夏津县", "武城县", "乐陵市", "禹城市"],
                "聊城市": ["东昌府区", "阳谷县", "莘县", "茌平县", "东阿县", "冠县", "高唐县", "临清市"],
                "滨州市": ["滨城区", "惠民县", "阳信县", "无棣县", "沾化县", "博兴县", "邹平县"],
                "荷泽市": ["牡丹区", "曹县", "单县", "成武县", "巨野县", "郓城县", "鄄城县", "定陶县", "东明县"]
            },
            "辽宁省": {
                "沈阳市": ["和平区", "沈河区", "大东区", "皇姑区", "铁西区", "苏家屯区", "东陵区", "新城子区", "于洪区", "辽中县", "康平县", "法库县", "新民市"],
                "大连市": ["中山区", "西岗区", "沙河口区", "甘井子区", "旅顺口区", "金州区", "长海县", "瓦房店市", "普兰店市", "庄河市"],
                "鞍山市": ["铁东区", "铁西区", "立山区", "千山区", "台安县", "岫岩满族自治县", "海城市"],
                "抚顺市": ["新抚区", "东洲区", "望花区", "顺城区", "抚顺县", "新宾满族自治县", "清原满族自治县"],
                "本溪市": ["平山区", "溪湖区", "明山区", "南芬区", "本溪满族自治县", "桓仁满族自治县"],
                "丹东市": ["元宝区", "振兴区", "振安区", "宽甸满族自治县", "东港市", "凤城市"],
                "锦州市": ["古塔区", "凌河区", "太和区", "黑山县", "义县", "凌海市", "北宁市"],
                "营口市": ["站前区", "西市区", "鲅鱼圈区", "老边区", "盖州市", "大石桥市"],
                "阜新市": ["海州区", "新邱区", "太平区", "清河门区", "细河区", "阜新蒙古族自治县", "彰武县"],
                "辽阳市": ["白塔区", "文圣区", "宏伟区", "弓长岭区", "太子河区", "辽阳县", "灯塔市"],
                "盘锦市": ["双台子区", "兴隆台区", "大洼县", "盘山县"],
                "铁岭市": ["银州区", "清河区", "铁岭县", "西丰县", "昌图县", "调兵山市", "开原市"],
                "朝阳市": ["双塔区", "龙城区", "朝阳县", "建平县", "喀喇沁左翼蒙古族自治县", "北票市", "凌源市"],
                "葫芦岛市": ["连山区", "龙港区", "南票区", "绥中县", "建昌县", "兴城市"]
            },
            "吉林省": {
                "长春市": ["南关区", "宽城区", "朝阳区", "二道区", "绿园区", "双阳区", "农安县", "九台市", "榆树市", "德惠市"],
                "吉林市": ["昌邑区", "龙潭区", "船营区", "丰满区", "永吉县", "蛟河市", "桦甸市", "舒兰市", "磐石市"],
                "四平市": ["铁西区", "铁东区", "梨树县", "伊通满族自治县", "公主岭市", "双辽市"],
                "辽源市": ["龙山区", "西安区", "东丰县", "东辽县"],
                "通化市": ["东昌区", "二道江区", "通化县", "辉南县", "柳河县", "梅河口市", "集安市"],
                "白山市": ["八道江区", "抚松县", "靖宇县", "长白朝鲜族自治县", "江源县", "临江市"],
                "松原市": ["宁江区", "前郭尔罗斯蒙古族自治县", "长岭县", "乾安县", "扶余县"],
                "白城市": ["洮北区", "镇赉县", "通榆县", "洮南市", "大安市"],
                "延边朝鲜族自治州": ["延吉市", "图们市", "敦化市", "珲春市", "龙井市", "和龙市", "汪清县", "安图县"]
            },
            "黑龙江省": {
                "哈尔滨市": ["道里区", "南岗区", "道外区", "香坊区", "动力区", "平房区", "松北区", "呼兰区", "依兰县", "方正县", "宾县", "巴彦县", "木兰县", "通河县", "延寿县", "阿城市", "双城市", "尚志市", "五常市"],
                "齐齐哈尔市": ["龙沙区", "建华区", "铁锋区", "昂昂溪区", "富拉尔基区", "碾子山区", "梅里斯达斡尔族区", "龙江县", "依安县", "泰来县", "甘南县", "富裕县", "克山县", "克东县", "拜泉县", "讷河市"],
                "鸡西市": ["鸡冠区", "恒山区", "滴道区", "梨树区", "城子河区", "麻山区", "鸡东县", "虎林市", "密山市"],
                "鹤岗市": ["向阳区", "工农区", "南山区", "兴安区", "东山区", "兴山区", "萝北县", "绥滨县"],
                "双鸭山市": ["尖山区", "岭东区", "四方台区", "宝山区", "集贤县", "友谊县", "宝清县", "饶河县"],
                "大庆市": ["萨尔图区", "龙凤区", "让胡路区", "红岗区", "大同区", "肇州县", "肇源县", "林甸县", "杜尔伯特蒙古族自治县"],
                "伊春市": ["伊春区", "南岔区", "友好区", "西林区", "翠峦区", "新青区", "美溪区", "金山屯区", "五营区", "乌马河区", "汤旺河区", "带岭区", "乌伊岭区", "红星区", "上甘岭区", "嘉荫县", "铁力市"],
                "佳木斯市": ["永红区", "向阳区", "前进区", "东风区", "郊区", "桦南县", "桦川县", "汤原县", "抚远县", "同江市", "富锦市"],
                "七台河市": ["新兴区", "桃山区", "茄子河区", "勃利县"],
                "牡丹江市": ["东安区", "阳明区", "爱民区", "西安区", "东宁县", "林口县", "绥芬河市", "海林市", "宁安市", "穆棱市"],
                "黑河市": ["爱辉区", "嫩江县", "逊克县", "孙吴县", "北安市", "五大连池市"],
                "绥化市": ["北林区", "望奎县", "兰西县", "青冈县", "庆安县", "明水县", "绥棱县", "安达市", "肇东市", "海伦市"],
                "大兴安岭地区": ["呼玛县", "塔河县", "漠河县"]
            },
            "海南省": {
                "海口市": ["秀英区", "龙华区", "琼山区", "美兰区"],
                "三亚市": ["三亚市"],
                "省直辖县级行政单位": ["五指山市", "琼海市", "儋州市", "文昌市", "万宁市", "东方市", "定安县", "屯昌县", "澄迈县", "临高县", "白沙黎族自治县", "昌江黎族自治县", "乐东黎族自治县", "陵水黎族自治县", "保亭黎族苗族自治县", "琼中黎族苗族自治县", "西沙群岛", "南沙群岛", "中沙群岛的岛礁及其海域"]
            }
        };


    }
})();
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

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('bankListController', bankListController);

    bankListController.$inject = ['$rootScope', '$scope', 'CommonService', 'UserService', '$state', 'CONFIG', 'ModalService'];

    /**
     * @memberOf directbank
     * @ngdoc controller
     * @name bankListController
     * @description 银行卡
     * @param {service} CommonService  系统公共服务
     * @param {service} userService    系统用户相关服务
     */
    function bankListController($rootScope, $scope, CommonService, userService, $state, CONFIG, ModalService) {
        var vm               = this;
        vm.model             = {};
        vm.showPanel         = true;
        vm.showMore          = true;
        vm.validateCode      = false; //他行卡需要获取验证码
        vm.showLimit         = 5;
        vm.model['cardType'] = 'true';
        vm.bankList          = [];
        vm.showMore          = showMore;
        vm.selectBank        = selectBank;
        init();

        /**
         * 页面初始化函数
         * @function init
         * @describe 获取银行列表、获取城市列表
         * @memberof BindBankcardController
         */
        function init() {
            getBanklist();
        }
        /**
         * 获取银行列表
         * @function getBanklist
         * @memberof bankListController
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
         * @memberof bankListController
         */
        function showMore() {
            vm.showLimit = 200;
            vm.showMore = false;
        }

        /**
         * 选择银行
         * @function selectBank
         * @memberof bankListController
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
                $rootScope.$broadcast('bankMessage', vm.selectedBank); //广播所选取的银行信息，供输入页面使用
                ModalService.closeModal(); //关闭选择银行页面
            }
        }

    }
})();
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ValidationService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name LoginController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 用户登录控制器
     */
    function LoginController($rootScope, UserService, CommonService, ValidationService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        //method
        vm.login = login; // 登录方法
        vm.closeLoginModal = closeLoginModal;
        vm.requestNewValidateCode = requestNewValidateCode; // 请求新的验证码图片

        /**
         * @property {object} model 提交给后端数据模型
         */
        vm.model = {};

        /**
         * @property {bool} hasLogin 标示是否已经登录
         */
        vm.hasLogin = $rootScope.hasLogin;

        /**
         * @property {bool} isNeedShowValidateCode 是否显示验证码
         */
        vm.isNeedShowValidateCode = true;

        /**
         * @property init 初始化方法
         */
        init();

        /**
         * @property requestNewValidateCode 图片验证码
         */
        function init(){
            requestNewValidateCode();
        }


        function checkInput(){
            if(ValidationService.isEmpty(vm.model.mobileNo)){
                CommonService.showError("登录名或卡号不能为空");
                return false;
            }

            if(ValidationService.isEmpty(vm.model.password)){
                CommonService.showError("登录密码不能为空");
                return false;
            }

            if(ValidationService.isEmpty(vm.model.small)){
                CommonService.showError("验证码不能为空");
                return false;
            }

            return true;
        }

        /**
         * 登录方法
         * @memberof LoginController
         * @function login
         * @description 登录描述
         */
        function login() {

            if(!checkInput()){
                return;
            }

             var params = {
                reqHead:{
                    flag:"1",                        //主副交易标志 1是主交易     2是副交易
                    tradeType:"3",                   // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    serviceName:"loginAction"        // 服务名称
                },
                reqBody:{
                    mobileNo: vm.model.mobileNo,
                    passWord: vm.model.password,
                    picValidateCode: vm.model.small
                }
                
            };

            var promise = UserService.login(params);                                    // 账户登录

            promise.then(function(data) {
                var user = data;
                if (user) {
                    $rootScope.hasLogin = true;
                    user.customerNo = vm.model.mobileNo;
                    sessionStorage.setItem(CONFIG.SESSION.CURRENT_USER, JSON.stringify(user));
                    $rootScope.user = user;
                    $state.go('index',{user:user});
                }
            }).catch(function(error) {
                vm.isNeedShowValidateCode = true;                                       // 设置需要显示验证码
                CommonService.showError(error);
            });
        }

         /**
         * 请求新的验证码图片
         * @memberof LoginController
         * @function requestNewValidateCode
         * @description 请求新的验证码图片
         */
        function requestNewValidateCode() {
            $timeout(function() {
                vm.validateCode = CommonService.getValidatePic();
            });

        }

        function closeLoginModal() {
            // 未调用$modal.open()之前，不存在$scope.$close()。该处语义不明确，还需调整
            $scope.$close();
        }
    }
})();

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

/**
 * @name registerProgress
 * @description
 * 推荐产品、浏览记录状态
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('loanhead', loanhead);
    loanhead.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies'];

    function loanhead(UserService, CommonService, $state, $rootScope, $cookies) {
        var directive = {
            restrict: 'AE',
            templateUrl: 'app/components/consumerLoan/loanhead/loanhead.html',
            scope: {
                flow: '@'
            },
            link: linkFunc
        };

        return directive;


        function linkFunc(scope, element, attrs) {
            var progressLength = $('.register').children().length;
            var activeArray = [],
                finishArray = [];
            for (var i = 0; i < progressLength; i++) {
                activeArray.push(false);
                if (i + 1 < scope.flow) {
                    finishArray[i] = true;
                } else {
                    finishArray[i] = false;
                }
            }
            activeArray[scope.flow - 1] = true;
            scope.finishs = finishArray;
            scope.actives = activeArray;
        }

    }

})();

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

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('loanApplicationCarController', loanApplicationCarController);

    loanApplicationCarController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ModalService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name loanApplicationCarController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 贷款申请加入购物车控制器
     */
    function loanApplicationCarController($rootScope, UserService, CommonService, ModalService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        //method
        vm.setShopcar = setShopcar; // 加入购物车方法
        vm.backLoanService = backLoanService;//返回贷款service
        vm.productname = $state.params['productname'];

        init();

        var productList;
        function init(){
            productList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
        }
        /**
         * 加入购物车方法
         * @memberof settlementShoppingCarController
         * @function requestCarList
         * @description 加入购物车方法
         */
        function setShopcar(){

            var car={
            'carCode': vm.productname,//产品编码
            'carPice':vm.model.money,//金额
            'carName': vm.productname//产品名称
            };//从界面取值
            sessionStorage.setItem(CONFIG.SESSION.SESSION_CAR_DATA,car);

            if ($rootScope.$broadcast.carList == undefined) {
                vm.dataCompare = [];
            }else{
                vm.dataCompare;
            }

            var exist = $.inArray(car, vm.dataCompare);
            var compareCount = vm.dataCompare.length;

            if (exist >= 0) {

                alert('请勿重复添加!');


                return true;
            } else {
                vm.dataCompare.splice(0, 0, car);
                $rootScope.$broadcast('carList', vm.dataCompare);

            }
        }
        /**
         * 关闭
         * @memberof settlementShoppingCarController
         * @function backLoanService
         * @description 关闭弹出框
         */
        function backLoanService(){
            ModalService.closeModal();
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('productdetailController', productdetailController);

    productdetailController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ValidationService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name productdetailController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 我的贷款主页面控制器
     */
    function productdetailController($rootScope, UserService, CommonService, ValidationService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.addcar = addcar;
        vm.selectConfigOptionFun = selectConfigOptionFun;
        vm.model = [];

        vm.items = [{
            type: CONFIG.CONSTANT.PRODUCT_MANAGE.BASEINFO,
            value: '产品信息'
        }, {
            type: CONFIG.CONSTANT.PRODUCT_MANAGE.PROPERTY,
            value: '产品配置'
        }];
        vm.tabIndex = 0;
        vm.productInfomsg1 = true;

        //初始化方法
        init();

        function init() {
            // 接受已售产品ID
            vm.productId = $state.params['productId'];
            if(vm.productId){
                queryAttributeInfoMethod();
            }else{
                vm.productdetail = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
                queryproductList(); //查询产品展示详情
            }
            
            
        }


        // 页签切换功能
        function switchTag(tagName, tagId) {
            var length = document.getElementsByName(tagName).length;
            var tags = document.getElementsByName(tagName);
            for (var i = 0; i < length; i++) {
                if (tags[i].id == tagId) {
                    document.getElementById(tagId).className = "payment-item item-selected online-payment";
                } else {
                    document.getElementById(tags[i].id).className = "payment-item online-payment";
                }
            }
        }
        /**
         * 查询产品展示详情
         * @memberof productdetailController
         * @function queryproductList
         * @description 查询产品展示详情
         */
        function queryproductList() {
            vm.requireAmount = vm.productdetail.requireAmount;

            // configOptionId: "CARD_0521_10010"
            // configOptionName: "人民币活期开户"
            vm.configOptionName = vm.productdetail.configOptionName;
            vm.configOptionId = vm.productdetail.configOptionId;
            vm.goodsCode = vm.productdetail.goodsCode;
            vm.imageAdress = vm.productdetail.imageAdress;
            vm.textData = vm.productdetail.textData;
            vm.productdetitle = vm.productdetail.goodsName;
            vm.productname = vm.productdetail.goodsName;
            vm.producthistoryrate = vm.productdetail.historyRateReturn;
            vm.productrisk = vm.productdetail.investmentRis;
            vm.productpice = vm.productdetail.price;
            vm.productdesc = vm.productdetail.abstract;
            vm.productdel = vm.productdetail.abstract;
            $("#textDateID").html(vm.textData);

        }

        // 根据产品Id查询产品详情
        function queryAttributeInfoMethod(){
            var params = {
                reqHead: {
                    flag: "1",              //主副交易标志 1是主交易     2是副交易
                    tradeType: "1",         // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01",         // 交易步骤
                    serviceName: "queryAttributeInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productCode: vm.productId                    //产品标志
                }
            };

            var promise = UserService.queryAttributeInfoMethod(params); //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {
                vm.productdetail = data.rspBody;

                // 产品ID
                vm.goodsCode = vm.productdetail.goodsCode;
                // 价格
                vm.productpice = vm.productdetail.price;
                // 产品详情
                $("#textDateID").html(vm.productdetail.textData);

                vm.configOptionIdMap = {};
                // 产品配置名称
                vm.optionName = vm.productdetail.configOptionName;
                vm.configOptionName = vm.optionName.split(",");
                // 产品配置ID
                vm.optionId = vm.productdetail.configOptionId;
                vm.configOptionId = vm.optionId.split(",");
                // 产品配置价格
                vm.configPrice = vm.productdetail.configOptionPrice;

                for(var i = 0; i < vm.configOptionName.length; i++){
                    vm.configOptionIdMap[vm.optionId.split(",")[i]+"|"+vm.configPrice.split(",")[i]] = vm.optionName.split(",")[i];
                }
                // 图片
                vm.imageAdress = vm.productdetail.imageAdress;

            }).catch(function(error) {
                CommonService.subShowMsg(2,error);
            });
        }

        // 选中产品配置事件
        function selectConfigOptionFun(tagName, tagId, optionObject){
            switchTag(tagName, tagId);
            if(vm.productId){
                vm.selectoptionObject= optionObject.split("|");
                // 产品ID
                vm.productdetail.selectConfigOptionId = vm.selectoptionObject[0];
                vm.productdetail.configOptionId = vm.selectoptionObject[0];
                // 产品价格 = 基础价格 + 产品配置价格
                vm.productpice = (parseInt(vm.productdetail.price) + parseInt(vm.selectoptionObject[1])).toFixed(2);
            }else{
                vm.productpice = (parseInt(vm.productdetail.price) + parseInt(vm.productdetail.configOptionPrice)).toFixed(2);
                vm.productdetail.selectConfigOptionId = optionObject;
                vm.productdetail.configOptionId = optionObject;
            }
            
        }
        
        /**
         * 添加购物车
         * @memberof productdetailController
         * @function addcar
         * @description 添加购物车
         */
        vm.dataCompare = [];
         function addcar(bankBalance){
            if(vm.productdetail.templateUrl == "" || vm.productdetail.templateUrl == null || vm.productdetail.templateUrl == undefined){
                 CommonService.subShowMsg("4","该产品（"+vm.productdetail.goodsName+"）没有配置产品调研项");
                 return;
            }

            if(vm.productdetail.selectConfigOptionId == undefined){
                 CommonService.subShowMsg(4,"请选产品配置");
                 return false;
            }
            

            if('Y'==vm.requireAmount){
                if (ValidationService.isEmpty(bankBalance) || bankBalance=="0.00") {
                    CommonService.subShowMsg(4,"购买金额不能为空！");
                    return false;
                }
                 vm.productdetail.bankBalance = ValidationService.toStdAmount(bankBalance);
            }
            sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(vm.productdetail));
            // 跳转到相应的产品调研项
            $state.go(vm.productdetail.templateUrl);
        }
        

        /**
         * 选择查询的交易类型
         * @function transTypeWatcher
         * @memberof BindBankcardController
         */
        $scope.$watch('vm.tabIndex', function(newIndex, oldIndex) {

            if (oldIndex != newIndex) {
                var tabItem = vm.items.filter(function(item, index) {
                    if (newIndex === index) {
                        return true;
                    }
                });

                vm.type = tabItem[0]['type'];


                if (vm.type == "000") {
                    vm.productInfomsg1 = true;
                    vm.productInfomsg2 = false;
                } else if (vm.type == "001") {
                    vm.productInfomsg1 = false;
                    vm.productInfomsg2 = true;
                }
                vm.pageIndex = '1';
                vm.currentPage = '1';

            }
        });

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('tradeOrderDetail', tradeOrderDetail);

    tradeOrderDetail.$inject = ['$rootScope', 'UserService', 'ModalService','CommonService', 'ValidationService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name tradeOrderDetail
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 用户登录控制器
     */
    function tradeOrderDetail($rootScope, UserService,ModalService, CommonService, ValidationService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
              //  关闭弹出窗口
        vm.close = close;
        vm.getOrderDetail = getOrderDetail;
        // 关闭窗口
        vm.closeLoginModal = closeLoginModal;
        vm.addRecordShopCartFun = addRecordShopCartFun;
        vm.shopcarproduct = shopcarproduct;         //展示产品详情
           /**
         * @property init 初始化方法
         */
        init();


        function init() {
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            vm.partyId = $rootScope.partyId;
            vm.orderId = $rootScope.orderId;
            vm.orderDetail= $rootScope.orderDetail;
            
        }
    
         /**
         * 我的订单-订单详情
         * @function searchTransList
         * @memberof myOrderController
         */
        function getOrderDetail(partyId,orderId){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrderDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: partyId,//
                   orderId: orderId  //
                }
            };

            var promise = UserService.getOrderDetail(params);

            promise.then(function(data) {
                vm.orderDetail  = data.rspBody;
               
                

            }).catch(function(error) {
                CommonService.showError(error);
            });

        }


         // 添加购物车
        function addRecordShopCartFun(x){
         var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "checkShowShelfGoodsInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productStoreId: vm.user.productStoreId, //  店铺标识
                    productId:x.productMap.productId,
                    goodStatus: "00",                            //  商品状态
                    goodsType: "02",                             //  商品类型
                    goodsFlag: "00"                              //  上下架标志
                }
            };
            var promise = UserService.checkShowShelfGoodsInfoMethod(params);

            promise.then(function(data) {
                if (data.rspHead.returnCode=="00000000") {
                   var productdetail = {};
                    var flag = data.rspBody.flag;
                    if(flag){
                        productdetail.goodsCode= x.productMap.productId;
                        productdetail.goodsName = x.productMap.productName;
                        productdetail.textData = x.productMap.orderItemDesc;
                        productdetail.price = x.priceMap.price;
                        productdetail.parentCode =x.productMap.prodCatalogId;
                        productdetail.configOptionId =x.productMap.productConfigOptionMap.configOptionId;
                      
                        sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(productdetail));
                        $state.go('templateProduct');
                        $scope.$close();
                    }else{
                         CommonService.subShowMsg("3", "该商品已下架");
                    }
                    
                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });


        	
        }
      /**
         * 弹窗展示购物车产品详情
         * @memberof shoppingCarController
         * @function shopcarproduct
         * @description 弹窗展示购物车产品详情
         */
        function shopcarproduct(list){
            var productdetailNew = {};
           var params = {
                reqHead: {
                    flag: "1",              //主副交易标志 1是主交易     2是副交易
                    tradeType: "1",         // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01",         // 交易步骤
                    serviceName: "queryAttributeInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productCode: list.productMap.productId                    //产品标志
                }
            };

            var promise = UserService.queryAttributeInfoMethod(params); //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {

                  var returnList  = data.rspBody;
                
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(returnList));
                $state.go('productdetail');

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });


            /* ModalService.showModal({
                    templateUrl: 'app/layout/productDetail/productDetail.html',
                    windowTemplateUrl: 'app/layout/productDetail/productDetailShow.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'productdetail'
                });*/
        }

        /**
         * 关闭，确认按钮
         * @memberof GoodsAddController
         * @function close
         * @description 关闭，确认按钮
         */
        function close() {
            ModalService.closeModal();
        }

        function closeLoginModal() {
            // 未调用$modal.open()之前，不存在$scope.$close()。该处语义不明确，还需调整
            $scope.$close();
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('showQuotedPriceDetailController', showQuotedPriceDetailController);

    showQuotedPriceDetailController.$inject = ['$scope', '$rootScope', 'ValidationService', 'ModalService', 'CommonService', '$state', 'UserService', 'CONFIG', '$filter'];


    function showQuotedPriceDetailController($scope, $rootScope, ValidationService, ModalService, CommonService, $state, UserService, CONFIG, $filter) {
        var vm = this;
        vm.model = {};

        init();

        /**
         *初始化
         */
        function init() {

            // 初始化信息
            showInfo();

        }
        /**
         * 初始化信息
         * @memberof ChannelBaseInfoAddController
         * @function showInfo
         * @description 初始化修改信息
         */
        function showInfo() {

            var returnList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            if (returnList != null) {
                vm.model.quoteItemSeqId = returnList.quoteItemList.quoteItemSeqId; //       报价ID
                vm.model.internalName = returnList.quoteItemList.internalName; //   销售渠道
                vm.model.quantity = returnList.quoteItemList.quantity; //   销售类型
                vm.model.selectedAmount = returnList.quoteItemList.selectedAmount; // 状态

                vm.model.totalQuoteItemAdjustmentAmountMap = returnList.quoteItemList.totalQuoteItemAdjustmentAmountMap; // 客户标识
                vm.model.quoteUnitPriceMap = returnList.quoteItemList.quoteUnitPriceMap; //
                vm.model.totalQuoteItemAmountMap = returnList.quoteItemList.totalQuoteItemAmountMap; //
                vm.model.quoteTermList = returnList.quoteItemList.quoteTermList; // 保价名

            }
        }

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('shoppingCarController', shoppingCarController);

    shoppingCarController.$inject = ['$scope', '$rootScope', 'UserService','CommonService','ModalService', 'CONFIG','$state', '$filter'];


    function shoppingCarController($scope, $rootScope, UserService,CommonService,ModalService, CONFIG, $state, $filter) {
        var vm = this;

        vm.numberchange = numberchange;             // 购物车更新
        vm.clickdelect = clickdelect;               // 删除购物车
        vm.gopayOrDeleteSelectCart = gopayOrDeleteSelectCart;     // 删除选中的购物车记录
        vm.checkedCart = checkedCart;               // 勾选购物车记录
        vm.shopcarproduct = shopcarproduct;         //展示产品详情
        vm.datanumerchange = datanumerchange;
        //初始化方法
        init();

        function init(){
             // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));

            queryShopCartFunction(vm.user.customerNo);
        }
         // 查询购物车
        function queryShopCartFunction(uniqueCstIdentity){
            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryShopCartAction"              // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                }
            };
            var promise = UserService.queryShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList != undefined && data.rspBody.recordList.length > 0){
                    vm.productDetail = data.rspBody.recordList;
                    $scope.length = vm.productDetail.length;

                    // 购物车计算总价格
                    vm.totalamt = 0;
                    var productItemList = [];

                    for (var i = 0; i < vm.productDetail.length; i++) {
                            var productItem = {};
                            productItem.cartItemId = vm.productDetail[i].id;   //  购物车标识
                            productItem.productId = vm.productDetail[i].productId;   //  产品标识
                            productItem.prodCatalogId = vm.productDetail[i].tradeData.prodCatalogId;    //  产品目录标识
                            productItem.configOptionId=vm.productDetail[i].tradeData.configOptionId;    //  配置项标识
                            if("Y"==vm.productDetail[i].tradeData.requireAmount){
                                productItem.selectedAmount = vm.productDetail[i].tradeData.bankBalance;
                                productItem.quantity = '1';
                                productItemList.push(productItem);
                                                // 单位
                            }else{
                                productItem.quantity = vm.productDetail[i].productAmount;               //  数量
                                productItem.selectedAmount = '0';
                                productItemList.push(productItem);
                            }

                    }

                    // 选中购物车记录
                    setTimeout(function(){
                        var chk = document.getElementsByName('allisSelected');
                        chk[0].checked = true;
                        SelectAll('allisSelected');
                   },300);


                    // 购物车产品试算
                    calculateShoppingCartPrice(productItemList);

                }

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }


        // 计算购物车价格
        function calculateShoppingCartPrice(productItemList){
            vm.totalamt=0.00;
            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"calculateShoppingCartPriceAction"              // 服务名称
                },
                reqBody:{
                    productStoreId:vm.user.productStoreId, //  店铺标识
                    webSiteId:"baseWebStore",                   //  网点标识
                    currency:"CNY",                             //  币种
                    partyId:vm.user.customerNo,                           //  用户标识
                    productItemList:productItemList,            //  产品项组
                    uniqueCstIdentity:vm.user.customerNo            // 客户唯一标识
                }
            };
            var promise = UserService.calculateCartPrice(params);

            promise.then(function(data) {

                vm.totalamt=data.rspBody.displaySubTotal;
                vm.productPriceList = data.rspBody.productPriceList;
                if( vm.productDetail.length==productItemList.length){
                     vm.cartLinesList = data.rspBody.cartLinesList;
                }
               
                var productListNew  = data.rspBody.productList;
                var tableObj = document.getElementById("carTable");
                var rows = tableObj.rows;
                if(productListNew!=null){
                    for(var j=1;j<rows.length;j++){
                         var cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                             for (var i = 0; i < productListNew.length; i++) {
                            var products = productListNew[i];
                            if(cartItemId==products.id){
                                  rows[j].cells[2].children[1].value = products.productAmount;
                                  rows[j].cells[3].innerText = $filter('currency')(products.tradeData.displayPrice,'¥');
                                  rows[j].cells[4].innerText = $filter('currency')(products.tradeData.otherAdjustments,'¥');
                                  rows[j].cells[5].innerText = $filter('currency')(products.tradeData.displayItemSubTotal,'¥');

                            }
                      }

                    }
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });

        }

        /**
         * 删除购物车产品
         * @memberof shoppingCarController
         * @function clickdelect
         * @description 删除购物车产品
         */
        function clickdelect(index,product){
            vm.totalamt = '0.00';
            var tableObj = document.getElementById("carTable");
            vm.productDetail.splice(index, 1);
            tableObj.deleteRow(index+1);

            // 删除购物车-入参UUID
            var recordList = [];
            recordList.push(product.id);
            if (vm.productDetail.length==0) {
                 vm.cartLinesList = '';
             }else{
                vm.cartLinesList.splice(index,1);
             }
            deleteShopCartRecordFunction(vm.user.customerNo,recordList);
        }

        // 删除购物车中某些记录购
        function deleteShopCartRecordFunction(uniqueCstIdentity,recordList){

            vm.totalamt = 0.00;
            var params = {
                reqHead:{
                    flag:"1",                                           // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                      // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                      // 交易步骤
                    serviceName:"deleteShopCartRecordAction"    // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                   recordList:recordList
                }
            };
            var promise = UserService.deleteClearShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList.length > 0){
                    vm.productDetail1 = data.rspBody.recordList;
                    $scope.length = vm.productDetail1.length;

                    var tableObj = document.getElementById("carTable");
                    var rows = tableObj.rows;
                    vm.totalamt = 0;
                    $scope.length = 0;
                    var productItemList = [];

                    for(var j=1;j<rows.length;j++){
                        if(rows[j].cells[0].children[0].checked == true){
                            var productItem = {};
                            productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                            productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                            productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                            var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                            var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                            productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                            var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                             if("Y"==requireAmount){
                                    productItem.selectedAmount = bankBalance;
                                    productItem.quantity = '1';
                                    productItemList.push(productItem);       // 单位
                            }else{
                                productItem.quantity = quantity;               //  数量
                                productItem.selectedAmount = '0';
                                productItemList.push(productItem);
                            }
                            $scope.length++;
                        }

                    }

                    // 表格里面复选框事件

                        if(rows.length-1 == $scope.length){
                            document.getElementById("allisSelectedtableRow").checked = true;
                            document.getElementById("allisSelectedTableSelect").checked = true;
                        }else{
                            document.getElementById("allisSelectedtableRow").checked = false;
                            document.getElementById("allisSelectedTableSelect").checked = false;
                        }
                      calculateShoppingCartPrice(productItemList);


                   //  // 选中购物车
                   //  setTimeout(function(){
                   //      var chk = document.getElementsByName('allisSelected');
                   //      chk[0].checked = true;
                   //      SelectAll('allisSelected');
                   // },300);
                }else{
                    vm.products = "";
                    $scope.length = 0;
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }


        /**
         * 更改产品数量时更改产品金额
         * @memberof shoppingCarController
         * @function numberchange
         * @description 更改产品数量时更改产品金额
         */
        function numberchange(list,flag,obj){

            updateShopCartFunction(list,vm.user.customerNo,flag,obj);

        }
        
       
        function isInteger(productAmount){
            var INTEGER_REGEXP = /^\-?\d*$/;
            if(INTEGER_REGEXP.test(productAmount)){
                if(parseInt(productAmount)<0){
                   return false;
                }
                return true;
            }
            return false;
        }
        //数量变化
         function datanumerchange(data,obj){

            var productAmount =obj.target.value;  // 购物车必须接受字符串
             if(!isInteger(productAmount)){
                obj.target.value=data.productAmount;
                 CommonService.subShowMsg("3","请输入合法的整数");
                 return;
            }
            if (0==parseInt(productAmount)) {
                obj.target.value=data.productAmount;
                CommonService.subShowMsg("3","至少为1个");
                return;
            }
            if (parseInt(data.productAmount)==parseInt(productAmount)) {
                return;
            }
            
           
            data.productAmount = productAmount;
            obj.target.parentNode.parentNode.firstElementChild.children[0].checked = true;
            var tableObj = document.getElementById("carTable");
            var rows = tableObj.rows;
            vm.totalamt = 0;
            $scope.length = 0;
            var productItemList = [];

            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    var productItem = {};
                    productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                    productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                    productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                    var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                    var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                    productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                    var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                     if("Y"==requireAmount){
                            productItem.selectedAmount = bankBalance;
                            productItem.quantity = '1';
                            productItemList.push(productItem);       // 单位
                    }else{
                        productItem.quantity = quantity;               //  数量
                        productItem.selectedAmount = '0';
                        productItemList.push(productItem);
                    }
                    $scope.length++;
                }

            }

            // 表格里面复选框事件

                if(rows.length-1 == $scope.length){
                    document.getElementById("allisSelectedtableRow").checked = true;
                    document.getElementById("allisSelectedTableSelect").checked = true;
                }else{
                    document.getElementById("allisSelectedtableRow").checked = false;
                    document.getElementById("allisSelectedTableSelect").checked = false;
                }
              calculateShoppingCartPrice(productItemList);
         }

        // 更新购物车
         function updateShopCartFunction(data,uniqueCstIdentity,flag,obj){
            // var recordMap = {};
            // // 购物车数据
            // recordMap.id = data.id;                                    // 记录唯一标识uuid
            // recordMap.productId = data.productId;                      // 产品ID


            if(flag == 'add'){
                var productAmount = parseInt(data.productAmount) + 1;                             // 产品数量
            }else{
                var productAmount = parseInt(data.productAmount) - 1;                             // 产品数量
            }

            // recordMap.productAmount = productAmount+"";         // 购物车必须接受字符串
           
            data.productAmount = productAmount;
           
            

            obj.toElement.parentNode.children[1].value=productAmount;  // 购物车必须接受字符串
          
            // 产品调研项

            obj.toElement.parentNode.parentNode.firstElementChild.children[0].checked = true;
            var tableObj = document.getElementById("carTable");
            var rows = tableObj.rows;
            vm.totalamt = 0;
            $scope.length = 0;
            var productItemList = [];

            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    var productItem = {};
                    productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                    productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                    productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                    var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                    var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                    productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                    var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                     if("Y"==requireAmount){
                            productItem.selectedAmount = bankBalance;
                            productItem.quantity = '1';
                            productItemList.push(productItem);       // 单位
                    }else{
                        productItem.quantity = quantity;               //  数量
                        productItem.selectedAmount = '0';
                        productItemList.push(productItem);
                    }
                    $scope.length++;
                }

            }

            // 表格里面复选框事件

                if(rows.length-1 == $scope.length){
                    document.getElementById("allisSelectedtableRow").checked = true;
                    document.getElementById("allisSelectedTableSelect").checked = true;
                }else{
                    document.getElementById("allisSelectedtableRow").checked = false;
                    document.getElementById("allisSelectedTableSelect").checked = false;
                }
              calculateShoppingCartPrice(productItemList);
        }

        // 删除选中和结算流程
        function gopayOrDeleteSelectCart(flag){
            var tableObj = document.getElementById("carTable");
            var trcollect = new Array();
            // 删除购物车-入参UUID
            var recordList = [];
            var rows = tableObj.rows;
            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    if(trcollect.length > 0){
                        trcollect[trcollect.length] = tableObj.rows[j].rowIndex;
                    }else{
                        trcollect[0] = tableObj.rows[j].rowIndex;
                    }
                    var id = rows[j].cells[7].innerText;              // 购物车UUID主键
                    recordList.push(id);
                }

            }


            if (trcollect.length > 0){
                // 删除选中的
                if(flag == "delete"){
                    var m = trcollect.length;
                    for (var i = m; i >= 1 ; i--){
                        tableObj.deleteRow(trcollect[i-1]) ;
                        vm.cartLinesList.splice(trcollect[i-1]-1,1);
                    }

                    // 删除选中的购物车数据
                    deleteShopCartRecordFunction(vm.user.customerNo,recordList);
                }else{      // 结算购物车
                    sessionStorage.setItem(CONFIG.SESSION.SESSION_CAR_DATA, recordList);
                    $state.go('settlementShoppingCar');
                }

            }else{
                 CommonService.subShowMsg("4","请选择一条记录");
                return false;
            }
        }

        // 购物车勾选重新计算总价
        function checkedCart(tableRowObj,tableName){
            var tableObj = document.getElementById("carTable");
            var rows = tableObj.rows;
            vm.totalamt = 0;
            $scope.length = 0;
            var productItemList = [];


            // 全选必须放在表格上面,不然价格计算失败
            if(tableRowObj == "tableHead"){
               SelectAll(tableName);
            }

            if(tableRowObj == 'tableSelect'){
                SelectAll(tableName,'1');
            }

            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    var productItem = {};
                    productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                    productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                    productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                    var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                    var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                    productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                    var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                     if("Y"==requireAmount){
                            productItem.selectedAmount = bankBalance;
                            productItem.quantity = '1';
                            productItemList.push(productItem);       // 单位
                    }else{
                        productItem.quantity = quantity;               //  数量
                        productItem.selectedAmount = '0';
                        productItemList.push(productItem);
                    }
                    $scope.length++;
                }


            }

            // 表格里面复选框事件
            if(tableRowObj == "tableRow"){
                if(rows.length-1 == $scope.length){
                    var chk = document.getElementsByName(tableName);
                    for ( var i = 0; i < chk.length; i++) {
                        chk[i].checked = true;
                    }
                }else{
                    document.getElementById("allisSelectedtableRow").checked = false;
                    document.getElementById("allisSelectedTableSelect").checked = false;
                }
            }
            if(productItemList.length!=0){
              var params = {
                    reqHead:{
                        flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                        tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                        stePcode:"01",                                  // 交易步骤
                        serviceName:"calculateShoppingCartPriceAction"              // 服务名称
                    },
                    reqBody:{
                        productStoreId:vm.user.productStoreId, //  店铺标识
                        webSiteId:"baseWebStore",                   //  网点标识
                        currency:"CNY",                             //  币种
                        partyId:vm.user.customerNo,                           //  用户标识
                        productItemList:productItemList,             //  产品项组
                        uniqueCstIdentity:vm.user.customerNo            // 客户唯一标识
                    }
                };
                var promise = UserService.calculateCartPrice(params);

                promise.then(function(data) {
                    vm.totalamt=data.rspBody.displaySubTotal;
                    var productListNew  = data.rspBody.productList;
                    if(productListNew!=null){
                        for(var j=1;j<rows.length;j++){
                             var cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                            if(rows[j].cells[0].children[0].checked == true){
                                 for (var i = 0; i < productListNew.length; i++) {
                                var products = productListNew[i];
                                if(cartItemId==products.id){
                                      rows[j].cells[2].children[1].value = products.productAmount;
                                }
                          }
                        }
                    }
                    }
                    if( vm.productDetail.length==productItemList.length){
                       vm.cartLinesList = data.rspBody.cartLinesList;
                     }
                }).catch(function(error) {
                    CommonService.subShowMsg("2",error);
                });
              }
        }


        /**
         * 查询购物车优惠组合
         * @memberof shoppingCarController
         * @function clicktype
         * @description 查询购物车优惠组合
         */
        function clicktype(productname){
            $scope.producttj.show = !$scope.producttj.show;
                var params = {
                        productTpye: productname//产品标志
                    };
                var promise = UserService.getproductDetail(params);  //查询某一商品的推荐组合--后期改接口
                promise.then(function(data) {


                    if (data.returnCode=="00000000") {
                        vm.listcar = data.respData;
                    }else{
                        $scope.producttj={
                            show:false
                        };
                    }

                }).catch(function(error) {
                        CommonService.showError(error);
                });

        }
        /**
         * 购物车结算界面推荐产品初始化
         * @memberof shoppingCarController
         * @function showchushihua
         * @description 初始化
         */
        function showchushihua(){

            $scope.producttj={
                show:false
            };
        }

        /**
         * 弹窗展示购物车产品详情
         * @memberof shoppingCarController
         * @function shopcarproduct
         * @description 弹窗展示购物车产品详情
         */
        function shopcarproduct(list){
            var productdetailNew = {};
           var params = {
                reqHead: {
                    flag: "1",              //主副交易标志 1是主交易     2是副交易
                    tradeType: "1",         // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01",         // 交易步骤
                    serviceName: "queryAttributeInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productCode: list.productId                    //产品标志
                }
            };

            var promise = UserService.queryAttributeInfoMethod(params); //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {

                  var returnList  = data.rspBody;
                
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(returnList));
                $state.go('productdetail');

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });


            /* ModalService.showModal({
                    templateUrl: 'app/layout/productDetail/productDetail.html',
                    windowTemplateUrl: 'app/layout/productDetail/productDetailShow.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'productdetail'
                });*/
        }


        // 控制表格全选
        function SelectAll(chk,index) {
            var chk = document.getElementsByName(chk);
            var obj = chk[0];
            if(index != undefined && index !=''){
                obj = chk[chk.length-1];
            }
            if(obj.checked){
                for ( var i = 0; i < chk.length; i++) {
                    chk[i].checked = true;
                }
            }else{
                for ( var i = 0; i < chk.length; i++) {
                    chk[i].checked =    false;
                }
            }
        }


    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('settlementShoppingCarController', settlementShoppingCarController);

    settlementShoppingCarController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ValidationService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name settlementShoppingCarController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 结账界面控制器
     */
    function settlementShoppingCarController($rootScope, UserService, CommonService, ValidationService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
        vm.bnsp = "    ";
        vm.totalmount = 0;
        //method
        vm.orderdetail = orderdetail; // 订单信息方法
        vm.established = established; // 确认订单方法
        vm.paysybmit = paysybmit; //支付提交
        vm.successreturn = successreturn; //支付成功返回
        vm.paysubreturn = paysubreturn; //支付界面返回

        vm.queryProduct = queryProduct;
        vm.externalProcessOrder = externalProcessOrder; //提交订单


        /**
         * @property {object} model 提交给后端数据模型
         */
        vm.model = {};

        vm.select = select;

        var addressList; //地址信息

        var payList ; //支付信息

        var shipMeth ; //交付信息

        vm.model.className = false;

        vm.model.itemSelected = false
        vm.model.itemNoselected = false;

        function select(tagName, tagId, data) {
            switchTag(tagName, tagId);

            //为选中地址与支付方式赋值
            if (tagName === "address") {
                addressList = data;
            }
            if (tagName === "pay") {
                payList = data;
            }
            if (tagName === "shipMeth") {
                shipMeth = data;
            }

        }


        // 页签切换功能
        function switchTag(tagName, tagId) {
            var length = document.getElementsByName(tagName).length;
            var tags = document.getElementsByName(tagName);
            for (var i = 0; i < length; i++) {
                if (tags[i].id == tagId) {
                    document.getElementById(tagId).className = "payment-item item-selected online-payment";
                } else {
                    document.getElementById(tags[i].id).className = "payment-item online-payment";
                }
            }
        }

        /**
         * @property init 初始化方法
         */
        init();

        function init() {
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            // 购物车查询
            queryShopCartFunction(vm.user.customerNo);

            showchushihua(); //订单信息、确认按钮初始化为true
        }


        // 提交订单
        function externalProcessOrder() {

            if (addressList == null || addressList == undefined) {
                CommonService.subShowMsg("3", "请选择地址");
                return;
            }
             if (shipMeth == null || shipMeth == undefined) {
                CommonService.subShowMsg("3", "请选择交付方式");
                return;
            }
             if (payList == null || payList == undefined) {
                CommonService.subShowMsg("3", "请选择支付方式");
                return;
            }
            if (vm.model.password == null || vm.model.password == undefined) {
                CommonService.subShowMsg("3", "请选择密码");
                return;
            }
        	var productItemList = [];
            for (var i = 0; i < vm.carlist.length; i++) {
	                var productItem = {};
	                productItem.productId = vm.carlist[i].productId;   //  产品标识
                    productItem.surveyInfo = vm.carlist[i].tradeData.surveyInfo;    //  产品调查信息
	                productItem.prodCatalogId = vm.carlist[i].tradeData.prodCatalogId;    //  产品目录标识
	                productItem.configOptionId=vm.carlist[i].tradeData.configOptionId;    //  配置项标识
	                productItem.quantity = vm.carlist[i].productAmount;                     //  数量
                    if("Y"==vm.carlist[i].tradeData.requireAmount){
                            productItem.selectedAmount = vm.carlist[i].bankBalance;
                            productItem.quantity = '1';
                            productItemList.push(productItem);
                                            // 单位
                    }else{
                        productItem.quantity = vm.carlist[i].productAmount;               //  数量
                        productItem.selectedAmount = '0'; 
                        productItemList.push(productItem);
                    }
               }
            var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "externalProcessOrderAction" // 服务名称
                },
                reqBody: {
                    productStoreId:vm.user.productStoreId, //  店铺标识
                    webSiteId: "baseWebStore", //  网点标识
                    currency: "CNY", //  币种
                    partyId: vm.user.customerNo,//"c10004", //  用户标识
                    shippingContactMechId: addressList.contactMechId, //
                    shippingMethod: shipMeth.shipMethId,
                    paymentMethodId: payList.paymentMethodId,
                    productItemList: productItemList //  产品项组
                }
            };
            var promise = UserService.externalProcessOrder(params);

            promise.then(function(data) {
              
                $scope.showpay.show = false; //支付方式不可见
                $scope.showpayresult.show = true; //支付结果可见

                var recordList = [];
                for (var i = 0; i < vm.carlist.length; i++) {
                    recordList.push(vm.carlist[i].id);
                }

                var returnHead = data.rspHead;
                vm.txSerialNo = data.rspHead.globalSeqNo; //流水
                deleteShopCartRecordFunction(vm.user.customerNo, recordList);
            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });

        }

          // 计算购物车价格
        function calculateShoppingCartPrice(productItemList){
            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"calculateShoppingCartPriceAction"              // 服务名称
                },
                reqBody:{
                    productStoreId:vm.user.productStoreId, //  店铺标识
                    webSiteId:"baseWebStore",                   //  网点标识
                    currency:"CNY",                             //  币种
                    partyId:"c10004",                           //  用户标识
                    productItemList:productItemList,             //  产品项组
                    uniqueCstIdentity:vm.user.customerNo         // 客户唯一标识
                }
            };
            var promise = UserService.calculateCartPrice(params);

            promise.then(function(data) {
                vm.totalamt=data.rspBody.displaySubTotal;
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        
        }

        // 查询购物车
        function queryShopCartFunction(uniqueCstIdentity) {

            var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryShopCartAction" // 服务名称
                },
                reqBody: {
                    uniqueCstIdentity: uniqueCstIdentity, // 客户唯一标识
                }
            };
            var promise = UserService.queryShopCart(params);

            promise.then(function(data) {
                if (data.rspBody.recordList != undefined && data.rspBody.recordList.length > 0) {
                    vm.recordList = data.rspBody.recordList;

                    var session_car_data = sessionStorage.getItem(CONFIG.SESSION.SESSION_CAR_DATA);
                    var sessionCarTemp = session_car_data.split(","); //字符分割 
                    // 购物车计算总价格
                    vm.totalamt = 0;
                    vm.totalcount = 0;
                    vm.carlist = [];
                    var productItemList = [];
      
                    for (var j = 0; j < sessionCarTemp.length; j++) {
                        for (var i = 0; i < vm.recordList.length; i++) {
                            if (sessionCarTemp[j] == vm.recordList[i].id) {
                                vm.carlist.push(vm.recordList[i]);
                                // 总价 = 数量 * 单价
                          
                                vm.totalcount++;
                            }
                        }

                    }

                     for (var i = 0; i < vm.carlist.length; i++) {
                            var productItem = {};
                            productItem.productId = vm.carlist[i].productId;   //  产品标识
                            productItem.prodCatalogId = vm.carlist[i].tradeData.prodCatalogId;    //  产品目录标识
                            productItem.configOptionId=vm.carlist[i].tradeData.configOptionId;    //  配置项标识
                             if("Y"==vm.carlist[i].tradeData.requireAmount){
                                productItem.selectedAmount = vm.carlist[i].tradeData.bankBalance;
                                productItem.quantity = '1';
                                productItemList.push(productItem);
                                                // 单位
                            }else{
                                productItem.quantity = vm.carlist[i].productAmount;               //  数量
                                 productItem.selectedAmount = '0'; 
                                productItemList.push(productItem);
                            }
                    }

                     calculateShoppingCartPrice(productItemList);
                      queryStoreListInfo();//查询支付。交付。地址
                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });
        }

        /**
         * 展示订单信息
         * @memberof settlementShoppingCarController
         * @function orderdetail
         * @description 展示订单信息描述
         */
        function orderdetail() {
            $scope.showorderdetail.show = !$scope.showorderdetail.show;
        }
        /**
         * 订单信息初始化
         * @memberof settlementShoppingCarController
         * @function showchushihua
         * @description 订单信息初始化
         */
        function showchushihua() {

            $scope.showorderdetail = {
                show: true
            };
            $scope.established = {
                show: false
            };
            $scope.showpay = {
                show: true
            };
            $scope.showpayresult = {
                show: false
            };
           
        }
        /**
         * 确认订单
         * @memberof settlementShoppingCarController
         * @function established
         * @description 确认订单以后，设置div的可见与隐藏
         */
        function established() {

            queryStoreListInfo();

        }

        /**
         * 查询店铺与客户支付工具和客户地址信息列表流程
         * @memberof settlementShoppingCarController
         * @function queryStoreListInfo
         * @description 确认订单以后，设置div的可见与隐藏
         */
        function queryStoreListInfo() {

            if (vm.carlist != null || vm.carlist != undefined ) {
                var productId ;
            for (var i = 0; i < vm.carlist.length; i++) {
                    
                  productId  = vm.carlist[i].productId;   //  产品标识
                    
               }
           
            
            var params = {
                reqHead: {
                    flag: "1", //主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryStoreListInfoAction" // 服务名称

                    //serviceName: "queryProductListProcessAction" // 服务名称
                },
                reqBody: {
                    partyId: "c10004", //当事人标识
                    productId: productId,//"CARD_0521_10000-01", //产品Id
                    productStoreId: vm.user.productStoreId, //  店铺标识
                    webSiteId:"baseWebStore",
                    currency:"CNY"

                }
            };
            //     var promise = UserService.queryProductListProcess(params); //支付发送
            var promise = UserService.queryStoreListInfo(params); //支付发送
            promise.then(function(data) {
                var returnHead = data.rspHead;
                var returnBody = data.rspBody;
                var returnCode = data.rspHead.returnCode;
                // vm.txSerialNo = data.rspHead.txSerialNo; //支付流水
                // vm.returnMsg = data.rspHead.returnMsg; //支付结果msg
                if (returnCode == "00000000") {
                    $scope.showpay.show = false; //支付方式不可见
                    $scope.showpayresult.show = false; //支付结果可见


                    var recordList = [];
                    for (var i = 0; i < returnBody.partyContactMechValueMaps.length; i++) {
                        recordList.push(vm.carlist.id);

                        vm.partyContactMechValueMaps = returnBody.partyContactMechValueMaps;
                        for (var j = 0; j < vm.partyContactMechValueMaps.length; j++) {
                            vm.partyContactMechValueMaps[j].index = j;
                        }
                    }

                    for (var i = 0; i < returnBody.paymentMethod.length; i++) {
                        recordList.push(vm.carlist.id);

                        vm.paymentMethod = returnBody.paymentMethod;
                        for (var j = 0; j < vm.paymentMethod.length; j++) {
                            vm.paymentMethod[j].index = j;
                        }
                    }

                    for (var i = 0; i < returnBody.shipMethList.length; i++) {
                        recordList.push(vm.carlist.id);

                        vm.shipMethList = returnBody.shipMethList;
                        for (var j = 0; j < vm.shipMethList.length; j++) {
                            vm.shipMethList[j].index = j;
                        }
                    }

                    $scope.showorderdetail.show = false; //订单信息不可见
                    $scope.established.show = false; //确认按钮在的div不可见
                    $scope.showpay.show = true; //支付方式可见
                    sessionStorage.removeItem(CONFIG.SESSION.SESSION_NOW_BAY);

                }
            }).catch(function(error) {
                CommonService.showError(error);
            })

        }
         }

        function queryProduct() {
            //alert(addressList.toName + payList.paymentMethodTypeId);
            if (vm.regForm.$valid) { // 验证通过
                var params = {
                    reqHead: {
                        flag: "1", //主副交易标志 1是主交易     2是副交易
                        tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                        stePcode: "01", // 交易步骤
                        serviceName: "queryProductListProcessAction" // 服务名称
                    },
                    reqBody: {
                        productStoreId: vm.user.productStoreId //  店铺标识
                    }
                };

                var promise = UserService.queryProductListProcess(params); //支付发送
                promise.then(function(data) {
                    var returnCode = data.rspHead.returnCode;
                    vm.txSerialNo = data.rspHead.txSerialNo; //支付流水
                    vm.returnMsg = data.rspHead.returnMsg; //支付结果msg
                    if (returnCode == "00000000") {
                        //$scope.showpay.show = false; //支付方式不可见
                        //$scope.showpayresult.show = true; //支付结果可见

                        var recordList = [];
                        for (var i = 0; i < vm.carlist.length; i++) {
                            recordList.push(vm.carlist.id);
                        }

                    }
                }).catch(function(error) {
                    CommonService.showError(error);
                })

            } else {
                CommonService.showError("输入错误，请正确填写！");
            }
        }


        /**
         * 支付提交
         * @memberof settlementShoppingCarController
         * @function established
         * @description 支付提交
         */
        function paysybmit() {
            if (vm.regForm.$valid) { // 验证通过
                var params = {
                    reqHead: {
                        flag: "1", //主副交易标志 1是主交易     2是副交易
                        tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                        stePcode: "01", // 交易步骤
                        serviceName: "" // 服务名称
                    },
                    reqBody: {
                        accountType: vm.model.cardtype, //账号类型
                        cardPassword: vm.model.cardpassword, //短信验证码（实际开发时要换命名要更改）
                        moblie: vm.model.accpassword //账号密码
                    }
                };

                var promise = UserService.sendPayReturn(params); //支付发送
                promise.then(function(data) {
                    var returnCode = data.rspHead.returnCode;
                    vm.txSerialNo = data.rspHead.txSerialNo; //支付流水
                    vm.returnMsg = data.rspHead.returnMsg; //支付结果msg
                    if (returnCode == "00000000") {
                        $scope.showpay.show = false; //支付方式不可见
                        $scope.showpayresult.show = true; //支付结果可见

                        var recordList = [];
                        for (var i = 0; i < vm.carlist.length; i++) {
                            recordList.push(vm.carlist.id);
                        }
                        deleteShopCartRecordFunction(vm.user.customerNo, recordList);
                    }
                }).catch(function(error) {
                    CommonService.showError(error);
                })

            } else {
                CommonService.showError("输入错误，请正确填写！");
            }
        }
        /**
         * 支付成功返回清空购物车
         * @memberof settlementShoppingCarController
         * @function cleanshoppingcar
         * @description 支付成功返回清空购物车
         */
        function deleteShopCartRecordFunction(uniqueCstIdentity, recordList) {


            var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "deleteShopCartRecordAction" // 服务名称
                },
                reqBody: {
                    uniqueCstIdentity: uniqueCstIdentity, // 客户唯一标识
                    recordList: recordList
                }
            };
            var promise = UserService.deleteClearShopCart(params);

            promise.then(function(data) {


            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });
        }

        /**
         * 支付成功返回
         * @memberof settlementShoppingCarController
         * @function successreturn
         * @description 支付成功返回
         */
        function successreturn() {
            $state.go('index');
        }
        /**
         * 支付界面返回
         * @memberof settlementShoppingCarController
         * @function paysubreturn
         * @description 支付界面返回
         */
        function paysubreturn() {
            $state.go('shoppingCar');
        }

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('quotedPriceDetailController', quotedPriceDetailController);

    quotedPriceDetailController.$inject = ['$scope', '$rootScope', 'ValidationService', 'ModalService', 'CommonService', '$state', 'UserService', 'CONFIG', '$filter'];


    function quotedPriceDetailController($scope, $rootScope, ValidationService, ModalService, CommonService, $state, UserService, CONFIG, $filter) {
        var vm = this;
        vm.model = {};


        init();

        vm.showDetailInfo = showDetailInfo;

        vm.addRecordShopCartFun = addRecordShopCartFun;

        /**
         *初始化
         */
        function init() {

            // 初始化信息
            showInfo();

        }

        /**
         * 初始化信息
         * @memberof ChannelBaseInfoAddController
         * @function showInfo
         * @description 初始化修改信息
         */
        function showInfo() {

            var returnList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));

            if (returnList.quoteList != null) {
                vm.model.quoteId = returnList.quoteList.quoteId; //       报价ID
                vm.model.salesChannel = returnList.quoteList.salesChannel; //   销售渠道
                vm.model.quoteType = returnList.quoteList.quoteType; //   销售类型
                vm.model.status = returnList.quoteList.status; // 状态

                vm.model.partyId = returnList.quoteList.partyId; // 客户标识
                vm.model.quoteName = returnList.quoteList.quoteName; // 保价名
                vm.model.description = returnList.quoteList.description; // 描述
                vm.model.currency = returnList.quoteList.currency; // 币种
                vm.model.storeName = returnList.quoteList.storeName; // 产品销售网点名称
                vm.model.quoteRoleList = returnList.quoteList.quoteRoleList; //报价角色名称LIST   
                vm.model.quoteItemList = returnList.quoteList.quoteItemList; //报价项目

                if (vm.model.status == "已批准") {
                    vm.model.value = true;
                } else {
                    vm.model.value = false;
                }
 
            } else {
                $state.go('quotedPrice');
            }


        }

        /**
         * 我的订单-已完成订单
         * @function searchTransList
         * @memberof myOrderController
         */
        function queryQuotedPrice() {

            var params = {
                reqHead: {
                    flag: "1", //主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryQuotedPriceAction" // 服务名称
                },
                reqBody: {
                    partyId: "c10004" //partyId
                }
            };

            var promise = UserService.queryQuotedPrice(params);

            promise.then(function(data) {
                var returnHead = data.rspHead;
                var returnBody = data.rspBody;
                if (returnHead.returnCode == CONFIG.CORRECT_CODE) {

                    vm.quoteList = returnBody.quoteList;
                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });

        }

        function showDetailInfo(item) {

            var quoteItemList = item;
            if (quoteItemList != undefined) {

                var params = {
                    quoteItemList: quoteItemList
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));


                ModalService.showModal({
                    templateUrl: 'app/components/consumerCar/showQuotedPriceDetail/showQuotedPriceDetail.html',
                    windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'registerAgreementModal'
                });
                item = {};

            } else {
                CommonService.subShowMsg("4", "请选择信息");
            }
        }


        // 添加购物车
        function addRecordShopCartFun(x) {

            var productdetail = {};

            // 根据产品Id查询产品详情

            var params = {
                reqHead: {
                    flag: "1", //主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryAttributeInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productCode: x.productId //产品标志
                }
            };

            var promise = UserService.queryAttributeInfoMethod(params); //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {

                productdetail = data.rspBody;   
                //productdetail.prodCatalogId = productdetail.parentCode;
                //productdetail.prdTypeCode = productdetail.parentCode;
                

                if (!ValidationService.isEmpty(productdetail.configOptionId)) {
                    sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(productdetail));
                    $state.go('templateProduct');
                }



                // // 产品ID
                // vm.goodsCode = vm.productdetail.goodsCode;
                // // 价格
                // vm.productpice = vm.productdetail.price;
                // // 产品详情
                // $("#textDateID").html(vm.productdetail.textData);

                // vm.configOptionIdMap = {};
                // // 产品配置名称
                // vm.optionName = vm.productdetail.configOptionName;
                // vm.configOptionName = vm.optionName.split(",");
                // // 产品配置ID
                // vm.optionId = vm.productdetail.configOptionId;
                // vm.configOptionId = vm.optionId.split(",");

                // for (var i = 0; i < vm.configOptionName.length; i++) {
                //     vm.configOptionIdMap[vm.optionId.split(",")[i]] = vm.optionName.split(",")[i];
                // }
                // // 图片
                // vm.imageAdress = vm.productdetail.imageAdress;

            }).catch(function(error) {
                CommonService.subShowMsg(2, error);
            });

        }

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('quotedPriceController', quotedPriceController);

    quotedPriceController.$inject = ['$scope', '$rootScope', 'ModalService', 'CommonService', '$state', 'UserService', 'CONFIG', '$filter'];


    function quotedPriceController($scope, $rootScope, ModalService, CommonService, $state, UserService, CONFIG, $filter) {
        var vm = this;

        init();

        vm.showDetailInfo = showDetailInfo;

        /**
         *初始化
         */
        function init() {
            queryQuotedPrice();
        }

        /**
         * 我的订单-已完成订单
         * @function searchTransList
         * @memberof myOrderController
         */
        function queryQuotedPrice() {

            var params = {
                reqHead: {
                    flag: "1", //主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryQuotedPriceAction" // 服务名称
                },
                reqBody: {
                    partyId: "c10004" //partyId
                }
            };

            var promise = UserService.queryQuotedPrice(params);

            promise.then(function(data) {
                var returnHead = data.rspHead;
                var returnBody = data.rspBody;
                if (returnHead.returnCode == CONFIG.CORRECT_CODE) {

                    vm.quoteList = returnBody.quoteList;
                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });

        }

        function showDetailInfo(item) {

            var quoteList = item;
            if (quoteList != undefined) {

                var params = {
                    quoteList: quoteList
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));
                $state.go('quotedPriceDetail');
                

                // ModalService.showModal({
                //     templateUrl: 'app/components/consumerCar/quotedPriceDetail/quotedPriceDetail.html',
                //     windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                //     size: 'lg',
                //     backdrop: 'static',
                //     windowClass: 'registerAgreementModal'
                // });
                // item = {};

            } else {
                CommonService.subShowMsg("4", "请选择信息");
            }
        }


    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('myOrderController', myOrderController);

    myOrderController.$inject = ['$scope', '$rootScope','CommonService','$state','UserService', 'CONFIG','$filter'];


    function myOrderController($scope, $rootScope,CommonService, $state, UserService, CONFIG,$filter) {
        var vm = this;

        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';
        vm.salesChannelEnumId = "";
        vm.orderTypeId = "";
        vm.resetInfo = resetInfo;                                               // 重置信息
        vm.type = CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT;
        vm.tabIndex = 0;
        vm.items = [{
            type: CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT,
            value: '已完成订单'
        }, {
            type: CONFIG.CONSTANT.TRANS_CATEGORY.DEPOSIT,
            value: '未完成订单'
        }];

        if($state.params['type']) {
            vm.type = $state.params['type'];
            vm.items.filter(function(item, index) {
                if(item.type === vm.type) {
                    vm.tabIndex = index;
                    return true;
                }
            });
        }


        // 方法
        vm.queryOrderList = queryOrderList;                               // 我的订单-已完成订单
        vm.DoCtrlPagingAct = DoCtrlPagingAct;                             // 未完成订单列表分页控制
        init();

        /**
         *初始化
         */
        function init(){
              vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            vm.endDate = new Date();
            vm.startDate = window.moment().add(-1, 'M')['_d'];

            queryOrderList(CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT);
        }

        /**
         * 我的订单-已完成订单
         * @function searchTransList
         * @memberof myOrderController
         */
        function queryOrderList(type){
            var orderStatusIds = [];

            if(type == CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT){
                var orderStatusId = {};
                orderStatusId.orderStatusId = "ORDER_COMPLETED";   //  订单状态标识
                orderStatusIds.push(orderStatusId);
            }else{
                var statusState = ['ORDER_CREATED','ORDER_SENT','ORDER_PROCESSING','ORDER_APPROVED',
                    'ORDER_HOLD','ORDER_REJECTED','ORDER_CANCELLED'];
                for (var i = 0; i < statusState.length; i++) {
                         var orderStatusId = {};
                         orderStatusId.orderStatusId = statusState[i];   //  订单状态标识
                         orderStatusIds.push(orderStatusId);
                }

            }

             var orderTypeIds = [];
             var orderTypeId = {};
             orderTypeId.orderTypeId = vm.orderTypeId;
             orderTypeIds.push(orderTypeId);
             var salesChannelEnumIds = [];
             var salesChannelEnumId = {};
             salesChannelEnumId.salesChannelEnumId = vm.salesChannelEnumId;
             salesChannelEnumIds.push(salesChannelEnumId);
            var start = $filter('date')(vm.startDate, 'yyyy-MM-dd 00:00:00');
            var end = $filter('date')(vm.endDate, 'yyyy-MM-dd 23:59:59');

            var beginDate = $filter('date')(vm.startDate, 'yyyy-MM-dd');
            var endDate = $filter('date')(vm.endDate, 'yyyy-MM-dd');

            var begin = beginDate.replace(/-/g,"");
            var end  = endDate.replace(/-/g,"");
            if(parseInt(begin)>parseInt(end)){
                 CommonService.subShowMsg("3","开始时间不能大于结束时间");
                 return;
            }


            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrdersListAction"                                  // 服务名称
                },
                reqBody:{
                   viewIndex: vm.pageIndex+'',
                   viewSize: vm.pageSize+'',
                   orderStatusIds:orderStatusIds,
                   showAll:"Y",
                   minDate:start,
                   maxDate:end,
                   orderTypeIds:orderTypeIds,
                   partyId:vm.user.customerNo,
                   salesChannelEnumIds:salesChannelEnumIds
                }
            };

            var promise = UserService.getOrdersList(params);

            promise.then(function(data) {
                if(type == CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT){
                    vm.tradeList = data.rspBody.orderList;
                }else{
                    vm.tradeList = data.rspBody.orderList;
                }

                vm.pageTotal = '' + Math.ceil(parseInt(data.rspBody.orderListSize) / parseInt(vm.pageSize));
                vm.page = {
                    'currentPage': vm.currentPage,
                    'page-size': vm.pageSize,
                    'total': vm.pageTotal
                };
            }).catch(function(error) {
                CommonService.showError(error);
            });

        }

         // 重置方法
        function resetInfo(){
           vm.endDate = new Date();
           vm.startDate = window.moment().add(-1, 'M')['_d'];
           vm.salesChannelEnumId = "";
           vm.orderTypeId = "";
        }


         /**
         * 选择查询的交易类型
         * @function queryOrdersList
         * @memberof myOrderController
         */
        $scope.$watch('vm.tabIndex', function(newIndex, oldIndex) {
            if(oldIndex != newIndex) {
                var tabItem = vm.items.filter(function(item, index) {
                    if(newIndex === index) {
                        return true;
                    }
                });
                vm.type = tabItem[0]['type'];
                vm.pageIndex = '1';
                vm.currentPage = '1';
                queryOrderList(vm.type);
            }
        });



        /**
         * @memberof myOrderController
         * @function DoCtrlPagingAct
         * @description 未完成订单列表分页控制
         */
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = parseInt(page);
            vm.currentPage = parseInt(page);
            queryOrderList(vm.type);
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('demandController', demandController);

    demandController.$inject = ['$scope', '$rootScope','CommonService','$state','UserService', 'CONFIG','$filter'];


    function demandController($scope, $rootScope,CommonService, $state, UserService, CONFIG,$filter) {
        var vm = this;
        vm.model = [];
        init();
        //提交请求
        vm.submitgo = submitgo;
        /**
         *初始化
         */
        function init(){
        	// 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        }

        /**
         * @function submitgo
         * @memberof demandController
         */
        function submitgo(type){
            if(vm.model.name == undefined || vm.model.name == ""){
                CommonService.subShowMsg(4 , "请输入需求名称");
                return;
            }
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"3",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"addShoppingListAction"                                  // 服务名称
                },
                reqBody:{
                    listName:vm.model.name,               // 列名
                    description:vm.model.describe,        // 描述
                    productStoreId:vm.user.productStoreId, //  店铺标识
                    currencyUom:"CNY",                    // 币种
                    partyId:vm.user.customerNo,           // 用户标识
                    inputList:[
                        {
                            productId:"",                 //产品标识
                            custRequestItemSeqId:"00001", //客户请求明细序号标识
                            quantity:""                   //数量
                        }
                    ]
                    
                }
            };

            var promise = UserService.createShoppingListService(params);
            promise.then(function(data) {
                 if (data) {
                    if (data.rspHead.returnCode==CONFIG.CORRECT_CODE) {
                        CommonService.subShowMsg(4 , "需求提交成功");
                        vm.model.name = "";
                        vm.model.describe = "";
                    }
                 }
            }).catch(function(error) {
                CommonService.subShowMsg(2 , error);
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('productViewController', productViewController);

    productViewController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout', '$location', 'ModalService'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name productViewController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 账户控制器
     */
    function productViewController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout, $location, ModalService) {

        var vm = this;
        vm.pageSize = '10';//每页显示多少条
        vm.pageIndex = '1';//
        vm.currentPage = '1';//
        vm.DoCtrlPagingAct = DoCtrlPagingAct;//分页方法

        var categoryList = [{
            type: '1',
            name: '存款资产',
            color: '#d193db',
            operator: [{
                'state': 'undefined',
                'name': '充值'
            }, {
                'state': 'undefined',
                'name': '提现'
            }]
        }, {
            type: '2',
            name: '贷款资产',
            color: '#1156b6',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '3',
            name: '投资资产',
            color: '#ffaf00',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '4',
            name: '签约产品',
            color: '#20bd61',
            operator: [{
                'state': 'undefined',
                'name': '申购'
            }, {
                'state': 'undefined',
                'name': '赎回'
            }]
        }, {
            type: '5',
            name: '信用卡',
            color: '#f35050',
            operator: [{
                'state': 'undefined',
                'name': '转入'
            }, {
                'state': 'undefined',
                'name': '转出'
            }]
        }];

        init();//初始化方法

        function init(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            getPropertyDistribution();              // 获取资产分布
            getCustomerListFun();                   // 获取账户信息
            
        }

         // 获取财富分布信息
        function getPropertyDistribution() {
            // 接受首页产品信息（饼图）
            vm.personPropertyList = JSON.parse(sessionStorage.getItem("personPropertyList"));
            showDistributionData(vm.personPropertyList); //显示数据
            showDistributionPie(vm.personPropertyList); //显示饼图
           
        }


         function showDistributionPie(productList) {
            productList = productList.filter(function(product){
                return product.assetSum > 0;
            });

            if(productList && productList.length > 0){
                vm.hasPie = true;
            } else {
                vm.hasPie = false;
            }

            var colorArray = [];        //饼图颜色
            var pieSeries = [];
            var distribution;
            if (productList && productList.length > 0) {
                productList.forEach(function(product) {
                    colorArray.push(product.color);
                    var assetAmt="0.00";
                    if(product.assetType=="4"||product.assetType=="5"){
                        assetAmt="5000.00";
                    }else{
                        assetAmt=product.assetSum;
                    }
                    pieSeries.push({
                        assetType:product.assetType,
                        value: parseFloat(assetAmt),
                        name: product.assetDesc
                    });
                });
            } else {
                vm.emptyPie = emptyPiePath;
            }

            vm.pieOptions = {
                color: colorArray,
                backFun:custPropertyDetail,
                series: [{
                    name: '账户总览',
                    type: 'pie',
                    selectedMode: null,
                    hoverAnimation: false,
                    avoidLabelOverlap: false,
                    center: ['50%', '45%'],
                    radius: ['40%', '85%'],
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            formatter: '{b} \n {d}%',
                            textStyle: {
                                color: '#262626',
                                fontSize: 18
                            }
                        }
                    },
                    data: pieSeries
                }]
            };
        }


        // 鼠标饼图其中一个视图事件
        function custPropertyDetail(params){
           // 向子页面传值
            $rootScope.detialData = params;
            ModalService.showModal({
                templateUrl: 'app/components/personOverview/custPropertyDetail.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }


        function showDistributionData(productList) {
            var distributionData = [];
            categoryList.forEach(function(category) {
                var data = {
                    type: category.type,
                    color: category.color,
                    operator: category.operator,
                    sum: 0,
                    desc: category.name
                };
                if (productList && productList.length > 0) {
                    productList.forEach(function(product) {
                        if (product.assetType == category.type) {
                            data.assetType=product.assetType;
                            data.sum = parseFloat(product.assetSum);
                            product.color = data.color;
                        }
                    });
                }
                distributionData.push(data);
            });
            vm.distributionData = distributionData;
        }


        /**
         * @memberof accountManagementController
         * @function 获取账户信息
         */
        function getCustomerListFun(){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryCustomerProductServiceByPartyIdAction"         // 服务名称
                },
                reqBody:{
                    partyId:vm.user.customerNo
                }
            };
            var promise = UserService.getCustomerListService(params);

            promise.then(function(data) {
                vm.accountList = data.rspBody.returnList;
                vm.pageTotal = '' + Math.ceil(parseInt(vm.accountList.length) / parseInt(vm.pageSize));//总页数
                vm.page = {
                        'currentPage': vm.currentPage,
                        'page-size': vm.pageSize,
                        'total': vm.pageTotal
                    };
                var m = 0;
                vm.productIdListPage = [];
                vm.pageSizeAcc = 0;
                if(vm.accountList.length > parseInt(vm.pageSize)){
                    vm.pageSizeAcc = vm.pageSize;
                }else{
                    vm.pageSizeAcc = vm.accountList.length;
                }
                for (var i = 0; i < parseInt(vm.pageSizeAcc); i++) {
                    vm.productIdListPage[m] = vm.accountList[i];
                    m++;
                }
            }).catch(function(error) {
                CommonService.showError(error);
            });
        }

        //分页
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = parseInt(page);
            vm.currentPage = parseInt(page);
            var m = 0;
            vm.productIdListPage = [];
            var j = (vm.pageIndex-1)*parseInt(vm.pageSize);
            var n;
            if(vm.pageIndex*parseInt(vm.pageSize)>vm.accountList.length){
                n=vm.accountList.length;
            }else{
                n=vm.pageIndex*parseInt(vm.pageSize);
            }
            for (var i = j; i < n; i++) {
                vm.productIdListPage[m] = vm.accountList[i];
                m++;
            }
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('accountManagementController', accountManagementController);

    accountManagementController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout', '$location', 'ModalService'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name accountManagementController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 账户控制器
     */
    function accountManagementController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout, $location, ModalService) {

        var vm = this;
        vm.pageSize = '10';//每页显示多少条
        vm.pageIndex = '1';//
        vm.currentPage = '1';//

        /**
         * @memberof accountManagementController
         * @param compareMessage
         * @description 接收货架信息方法
         */
        $scope.$on('allChlShelfGoodCompare', function(event, data) {
            if(data != "" || data != null || data != undefined){
                vm.shelfCodeList = data;
            }
        });

        // 方法

  
        var categoryList = [{
            type: '1',
            name: '存款资产',
            color: '#d193db',
            operator: [{
                'state': 'undefined',
                'name': '充值'
            }, {
                'state': 'undefined',
                'name': '提现'
            }]
        }, {
            type: '2',
            name: '贷款资产',
            color: '#1156b6',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '3',
            name: '投资资产',
            color: '#ffaf00',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '4',
            name: '签约产品',
            color: '#20bd61',
            operator: [{
                'state': 'undefined',
                'name': '申购'
            }, {
                'state': 'undefined',
                'name': '赎回'
            }]
        }, {
            type: '5',
            name: '信用卡',
            color: '#f35050',
            operator: [{
                'state': 'undefined',
                'name': '转入'
            }, {
                'state': 'undefined',
                'name': '转出'
            }]
        }];

        init();//初始化方法

        function init(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            vm.shelfCodeList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SHELFCODE_INFO));
            getPropertyDistribution();              // 获取资产分布
            getCustomerListFun();                   // 获取账户信息
        }

         // 获取财富分布信息
        function getPropertyDistribution() {
            // 接受首页产品信息（饼图）
            vm.personPropertyList = JSON.parse(sessionStorage.getItem("personPropertyList"));
            showDistributionData(vm.personPropertyList); //显示数据
            showDistributionPie(vm.personPropertyList); //显示饼图
           
        }


         function showDistributionPie(productList) {
            productList = productList.filter(function(product){
                return product.assetSum > 0;
            });

            if(productList && productList.length > 0){
                vm.hasPie = true;
            } else {
                vm.hasPie = false;
            }

            var colorArray = [];        //饼图颜色
            var pieSeries = [];
            var distribution;
            if (productList && productList.length > 0) {
                productList.forEach(function(product) {
                    colorArray.push(product.color);
                    var assetAmt="0.00";
                    if(product.assetType=="4"||product.assetType=="5"){
                        assetAmt="5000.00";
                    }else{
                        assetAmt=product.assetSum;
                    }
                    pieSeries.push({
                        assetType:product.assetType,
                        value: parseFloat(assetAmt),
                        name: product.assetDesc
                    });
                });
            } else {
                vm.emptyPie = emptyPiePath;
            }

            vm.pieOptions = {
                color: colorArray,
                backFun:custPropertyDetail,
                series: [{
                    name: '账户总览',
                    type: 'pie',
                    selectedMode: null,
                    hoverAnimation: false,
                    avoidLabelOverlap: false,
                    center: ['50%', '45%'],
                    radius: ['40%', '85%'],
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            formatter: '{b} \n {d}%',
                            textStyle: {
                                color: '#262626',
                                fontSize: 18
                            }
                        }
                    },
                    data: pieSeries
                }]
            };
        }


        // 鼠标饼图其中一个视图事件
        function custPropertyDetail(params){
           // 向子页面传值
            $rootScope.detialData = params;
            ModalService.showModal({
                templateUrl: 'app/components/personOverview/custPropertyDetail.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }


        function showDistributionData(productList) {
            var distributionData = [];
            categoryList.forEach(function(category) {
                var data = {
                    type: category.type,
                    color: category.color,
                    operator: category.operator,
                    sum: 0,
                    desc: category.name
                };
                if (productList && productList.length > 0) {
                    productList.forEach(function(product) {
                        if (product.assetType == category.type) {
                            data.assetType=product.assetType;
                            data.sum = parseFloat(product.assetSum);
                            product.color = data.color;
                        }
                    });
                }
                distributionData.push(data);
            });
            vm.distributionData = distributionData;
        }


        /**
         * @memberof accountManagementController
         * @function 获取账户信息
         */
        function getCustomerListFun(){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:""         // 服务名称
                },
                reqBody:{
                   
                }
            };
            var promise = UserService.getCustomerList(params);

            promise.then(function(data) {
                vm.accountList = data.respData;
            }).catch(function(error) {
                CommonService.showError(error);
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('sidebar',sidebar);

    function sidebar() {
        var directive = {
            restrict: 'EA',
            templateUrl: 'app/layout/sideBar/sideBar.html',
            link:linkFunc
        };

        return directive;

        function linkFunc(scope,el,attrs) {

        }

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('sideBarController', sideBarController);

    sideBarController.$inject = ['$scope', '$rootScope','UserService','ModalService', 'CommonService','$state','CONFIG'];

    /**
     * @memberOf directbank
     * @ngdoc controller
     * @name sideSlideBarController
     * @description 侧边栏工具框
     * @param $scope
     * @param $rootScope
     */
    function sideBarController($scope, $rootScope,UserService,ModalService, CommonService, $state,CONFIG) {
        var vm = this;

        vm.carMouseenterFun = carMouseenterFun;
        vm.carMouseleaveFun = carMouseleaveFun;

        vm.toggleCompareBlock = toggleCompareBlock;
        vm.compareBlockHidden = compareBlockHidden;

        vm.productDelete = productDelete;
        vm.goCompare = goCompare;
        vm.toggleOption = toggleOption;
        vm.shopcarproduct = shopcarproduct;
        vm.products = [];
        var productsProcode;
        vm.optionDisplay = false;

        // 页面初始化
        init();

        function init(){
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        }
    
        
        /**
         * @memberof sideBarController
         * @function productDelete
         * @description 删除产品
         */
        function productDelete(index,data) {
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            deleteShopCartRecordFunction(vm.user.customerNo,data);

        }
       


        /**
         * @memberof sideBarController
         * @function toggleCompareBlock
         * @description 显示隐藏产品对比框
         */
        function toggleCompareBlock() {
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            $scope.showCompareBox = !$scope.showCompareBox;
            queryShopCartFunction(vm.user.customerNo);
        }

        /**
         * @memberof sideBarController
         * @function compareBlockHidden
         * @description 清空所有对比产品方法
         */
        function compareBlockHidden() {
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            deleteClearShopCarFunction(vm.user.customerNo);
            
        }


        /**
         * @memberof sideBarController
         * @function goCompare
         * @description 跳转至产品对比页并传送数据方法
         */
        function goCompare() {
            if (vm.products.length > 0) {
                $state.go('shoppingCar');
                return;
            }

            if ($scope.currentState == 'compare') {
                $rootScope.$broadcast('sendCompare', productsProcode);
            }else {
                $scope.$on('$stateChangeSuccess', function(e, toState) {
                    if (toState.name == 'compare') {
                        $rootScope.$broadcast('sendCompare', productsProcode);
                    }
                });
            }
        }

        /**
         * @memberof sideBarController
         * @name sendCompare
         * @param data 接收数据
         * @description 监听页面跳转隐藏对比框
         */
        $scope.$on('$stateChangeSuccess', function() {
            $scope.showCompareBox = false;
        });

        /**
         * @memberof sideBarController
         * @name toggleOption
         * @description 关闭对比框
         */
        function toggleOption() {
            $scope.showCompareBox = false;
        }

        function dataProcess() {
            productsProcode = [];
            vm.products.forEach(function(product, call) {
                productsProcode.push(product.prodCode);
            });
        }
        /**
         * 弹窗展示购物车产品详情
         * @memberof sideBarController
         * @function shopcarproduct
         * @description 弹窗展示购物车产品详情
         */
        function shopcarproduct(list){
            var params = {//-------------------后期改为单一的查询产品信息接口，这个是查询全部产品记录
                userCode: list.prodCode//产品代码
            };
            var promise = UserService.browsehis(params);
            promise.then(function(data) {

                $rootScope.productrec = data.respData[0];

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
             ModalService.showModal({
                    templateUrl: 'app/layout/productDetail/productDetail.html',
                    windowTemplateUrl: 'app/layout/productDetail/productDetailShow.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'productdetail'
                });

        }
        //控制头部购物车展开
       

        function carMouseenterFun(){
            $('.top-car .top-car-btn').addClass("top-car-btn-open ");
            $('.top-car-btn .glyphicon-menu-down').addClass("glyphicon-menu-up");
            $('.top-car').children(".top-car-box").show();
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            queryShopCartFunction(vm.user.customerNo);
        }

        function carMouseleaveFun(){
            $('.top-car .top-car-btn').removeClass("top-car-btn-open");
            $('.top-car-btn .glyphicon-menu-down').removeClass("glyphicon-menu-up");
            $('.top-car').children(".top-car-box").hide();
        }


        // 查询购物车
        function queryShopCartFunction(uniqueCstIdentity){

            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryShopCartAction"              // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                }
            };
            var promise = UserService.queryShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList != undefined && data.rspBody.recordList.length > 0){
                    vm.products = data.rspBody.recordList;
                    $scope.length = vm.products.length;
                }else{
                    vm.products = "";
                    $scope.length = 0;
                }
               
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        // 清除购物车
        function deleteClearShopCarFunction(uniqueCstIdentity){
             var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"deleteClearShopCartAction"         // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                }
            };
            var promise = UserService.deleteClearShopCart(params);

            promise.then(function(data) {
                queryShopCartFunction(uniqueCstIdentity);
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        // 删除购物车中某些记录购
        function deleteShopCartRecordFunction(uniqueCstIdentity,data){
            var recordList = [];
            recordList.push(data.id);

            var params = {
                reqHead:{
                    flag:"1",                                           // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                      // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                      // 交易步骤
                    serviceName:"deleteShopCartRecordAction"            // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                   recordList:recordList
                }
            };
            var promise = UserService.deleteClearShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList.length > 0){
                    vm.products = data.rspBody.recordList;
                    $scope.length = vm.products.length;
                }else{
                    vm.products = "";
                    $scope.length = 0;
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('ShowmessageController', ShowmessageController);

    ShowmessageController.$inject = ['$scope','$rootScope','$timeout','ModalService'];


    /**
     * @memberof directbank
     * @ngdoc controller
     * @name ShowmessageController
     * @param  {service} ModalService       弹窗服务
     * @description
     * 弹窗提示控制器
     */
    function ShowmessageController($scope,$rootScope,$timeout,ModalService) {
        var vm = this;

        vm.close = close;
        vm.surebut = surebut;
        vm.error = $rootScope.error;//提示信息
        vm.flag = $rootScope.flag;//弹窗标志
        vm.closeFlag = "0";//关闭提示框标识

        if($rootScope.status != "-1"){
            if(vm.flag == "1" || vm.flag == "2"){
                vm.data = $rootScope.data;//交易信息
                vm.serialNo = vm.data.rspHead.globalSeqNo//流水号

                vm.returnCode = vm.data.rspHead.returnCode//交易返回code
            }
        }

        init();
        /**
         * 关闭按钮
         * @memberof ShowmessageController
         * @function close
         * @description 确认按钮
         */
        function close() {
            $timeout.cancel( timer );
            ModalService.closeModal();
        }
        /**
         * 确认按钮
         * @memberof ShowmessageController
         * @function close
         * @description 确认按钮
         */
        function surebut() {
            if($rootScope.backname != null && $rootScope.backname != undefined){
                   $rootScope.backname();
                }
            $timeout.cancel( timer );
            ModalService.closeModal();
            $rootScope.backname = null;
        }
        /**
         * 初始化时判断flag和msg
         * @memberof ShowmessageController
         * @function init
         * @description 初始化时判断flag和msg
         */
        function init(){

            if($rootScope.status == "-1"){
                vm.returnMsg = $rootScope.errorInterMsg;
            }else{
                if (vm.error != null && vm.error != undefined) {
                    if(vm.flag == "2"){
                        vm.returnMsg = vm.error.rspHead.returnMsg;
                    }else{
                        vm.returnMsg = vm.error;
                    }

                }else{
                    vm.returnMsg = vm.data.rspHead.returnMsg//交易返回信息
                }
            }
        }
        /**
         * 定时关闭提示框
         * @memberof ShowmessageController
         * @function closeShowMessage
         * @description 定时关闭提示框
         */
        var timer = $timeout(function(){
                    ModalService.closeModal();
                },5000)
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('productDetailShowController', productDetailShowController);

    productDetailShowController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ModalService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name productDetailShowController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 弹出框产品展示
     */
    function productDetailShowController($rootScope, UserService, CommonService,ModalService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.productrec = $rootScope.productrec;
        vm.addcar = addcar;

        /**
         * 添加购物车
         * @memberof productDetailShowController
         * @function addcar
         * @description 添加购物车
         */
        vm.dataCompare = [];
        function addcar(){
            var product={
                prodCode:vm.productrec.productCode,
                bankName:vm.productrec.productname,
                prodName:vm.productrec.productdesc,
                bankBalance:vm.model.bankBalance
            }
            vm.dataCompare = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_CAR_DATA));
            if (vm.dataCompare == null) {
                vm.dataCompare = [];
            }else{
                vm.dataCompare.splice(0, 0, product);
            }

            sessionStorage.setItem(CONFIG.SESSION.SESSION_CAR_DATA, JSON.stringify(vm.dataCompare));
            $rootScope.$broadcast('compareMessage', vm.dataCompare);
            ModalService.closeModal();
         }
    }
})();

/**
 * @name HEADER
 * @description
 * HEADER
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('cheader', cheader);

    cheader.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies'];


    function cheader(UserService, CommonService, $state, $rootScope, $cookies) {
        var directive = {
            restrict: 'A',
            replace: true,
            templateUrl: 'app/layout/header/header.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

        }
    }
})();

/**
 * Created by lenovo on 2015/2/11.
 */
/**
 * @name HEADER
 * @description
 * FOOTER
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('cfooter', cfooter);

    function cfooter() {
        var directive = {
            restrict: 'A',
            replace: true,
            templateUrl: 'app/layout/footer/footer.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

        }
    }
})();


/**
 * @name transferProduce
 * @description
 * 菜单指令
 */
(function(){
    'use strict';

    transferProduce.$inject = ["UserService", "CommonService", "$state", "$rootScope", "$cookies", "ModalService", "CONFIG"];
    angular
        .module('EBankProject')
        .directive('ebankTree', transferProduce);

    // ebankTree.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies', 'ModalService'];

     /**
     * @memberof directbank
     * @ngdoc directive
     * @name primeProduct
     * @description
     *
     *
     * @attr {object} product 产品信息
     * @attr {theme} 主题 red orange
     * @example
     *   Usage:
     *    <ebank-tree compare="vm.menuTree();" treeList="treeList" ></ebank-tree>
     */

    function transferProduce(UserService, CommonService, $state, $rootScope, $cookies, ModalService, CONFIG) {
        var directive = {
            restrict: 'AE',
            scope:{
            	list: '=',
            },
            templateUrl:'app/directives/tree/tree.html',
            controller: getTreeInfoController,
            controllerAs: 'vm'

        };

        return directive;

        /**
         * 获取菜单信息
         * [getTreeInfoController description]
         * @return {[type]} [description]
         */
        function getTreeInfoController(){
            var vm = this;
            vm.getTreeList = getTreeList;

            function getTreeList(treeObject){
                var bmfProperties = treeObject.itemValue.bmfProperties; //  货架性质
                var bmprShelfcode = treeObject.itemValue.bmprShelfcode; //  货架编码
                var bmfUrl = treeObject.itemValue.bmfUrl;               //  菜单Url
                if (bmfProperties == "Y" && bmprShelfcode == "") {
                    CommonService.subShowMsg("3","该货架没有配置产品信息,请联系管理员处理!!!");
                    return false;
                }
                if (bmfProperties == "N") {
                    return false;
                }
                var params = {
                    reqHead:{
                        flag:"1",                                                   //主副交易标志 1是主交易     2是副交易
                        tradeType:"1",                                              // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                        stePcode:"01",                                              // 交易步骤
                        serviceName:"queryShelfGoodsMenuRelByShelfCodeAction"        // 服务名称
                    },
                    reqBody:{
                       shelfCode:bmprShelfcode
                       // channelCode:"3333"   //不同渠道上送不同的渠道编码，请注意修改
                    }
                };
                var promise = UserService.getAllChlShelfGood(params);
                promise.then(function(data) {
                    sessionStorage.setItem(CONFIG.SESSION.SHELFCODE_INFO, JSON.stringify(data.rspBody));
                    $rootScope.$broadcast('allChlShelfGoodCompare', data.rspBody);
                }).catch(function(error) {
                    CommonService.subShowMsg("2",error);
                });


            }

        }
    }

})();


/**
 * @name transferProduce
 * @description
 * 转账产品指令
 */
(function(){
    'use strict';

    transferProduce.$inject = ["$compile"];
    angular
        .module('EBankProject')
        .directive('transferProduct', transferProduce);

     /**
     * @memberof directbank
     * @ngdoc directive
     * @name primeProduct
     * @description
     *
     *
     * @attr {object} product 产品信息
     * @attr {theme} 主题 red orange
     * @example
     *   Usage:
     *    <transfer-product compare="vm.addcar();" product="product" ></transfer-product>
     */

    function transferProduce($compile) {
        var directive = {
            restrict: 'E',
            scope:{
                product: '=',
                compare: '&'
            },
            templateUrl:'app/directives/transferService/transferProduct.html',
            controller:transferProductController,
            controllerAs: 'vm'
        };
        return directive;

        function transferProductController($state,CONFIG){
            var vm=this;
            vm.imgMouseover = imgMouseover;
            vm.imgMouseLeave = imgMouseLeave;
            vm.goproductdetail = goproductdetail;
            function goproductdetail(product){
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(product));

                $state.go('productdetail');
            }
            function imgMouseover(){
                vm.mouseFlag = true;
            }
            function imgMouseLeave(){
                vm.mouseFlag = false;
            }
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('shoppingCardirective', shoppingCardirective);

    shoppingCardirective.$inject = ['$compile','CONFIG'];

    function shoppingCardirective($compile,CONFIG) {
        var directive = {
            restrict: 'AE',
            scope: {
                type: '=',
                list:'='
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('type', function(type) {
                switch (type) {
                    case "000":
                        el.html('<shopping-cardirson list="list"></shopping-cardirson>');
                        $compile(el.contents())(scope);
                        break;
                }
            });
        }
    }
})();


/**
 * @name productView
 * @description
 * 产品视图
 */
(function(){
    'use strict';

    productView.$inject = ["$compile"];
    angular
        .module('EBankProject')
        .directive('productView', productView);

     /**
     * @memberof directbank
     * @ngdoc directive
     * @name productView
     * @description
     *
     *
     * @attr {object} product 账户信息
     * @example
     *   Usage:
     *    <account-management accountList="accountList" ></account-management>
     */

    function productView($compile) {
        var directive = {
            restrict: 'E',
            scope:{
            	list: '=',
                compare: '&'
            },
            templateUrl:'app/directives/productView/productViewDir.html'
        };
        return directive;
    }

})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('financialTradeItem', financialTradeItem);

    financialTradeItem.$inject = ['$compile','CONFIG'];

    function financialTradeItem($compile,CONFIG) {
        var directive = {
            restrict: 'AE',
            scope: {
                type: '=',
                list:'='
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('type', function(type) {
                switch (type) {
                    case CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT://已完成订单
                        el.html('<completedorder-item list="list"></completedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.TRANS_CATEGORY.DEPOSIT: //未完成订单
                        el.html('<notcompletedorder-item list="list"></notcompletedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                }
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('consumerLoandirective', consumerLoandirective);

    consumerLoandirective.$inject = ['$compile','CONFIG'];

    function consumerLoandirective($compile,CONFIG) {
        var directive = {
            restrict: 'AE',
            scope: {
                type: '=',
                list:'=',
                product:'='
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('type', function(type) {
                switch (type) {
                    case CONFIG.CONSTANT.CONSUMER_LOAN.LOANSERVICE://贷款申请
                        el.html('<loan-servicedir product="product"></loan-servicedir>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.CONSUMER_LOAN.MYLOAN: //我的贷款
                        el.html('<my-loandir product="product"></my-loandir>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.CONSUMER_LOAN.PROGRESS: //贷款进度
                        el.html('<notcompletedorder-item list="list"></notcompletedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                    case CONFIG.CONSTANT.CONSUMER_LOAN.PERSONDATA: //个人资料
                        el.html('<notcompletedorder-item list="list"></notcompletedorder-item>');
                        $compile(el.contents())(scope);
                        break;
                }
            });
        }
    }
})();


/**
 * @name accountManagement
 * @description
 * 我的账户指令
 */
(function(){
    'use strict';

    accountManagement.$inject = ["$compile"];
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

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('personOverviewController', personOverviewController);

    personOverviewController.$inject = ['$rootScope', 'UserService','CommonService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name personOverviewController
     * @param  {service} UserService 账户服务
     * @description
     * 交易明细页面控制器
     */
    function personOverviewController($rootScope, UserService,CommonService, $state, CONFIG, $scope, $timeout) {
        
        var vm = this;
        vm.submitgo = submitgo;
        vm.backInput = backInput;
        vm.personOverviewQuery=personOverviewQuery;
        vm.model={};
        //初始化方法
        init();

        function init(){
            
             vm.personSummaryInfo=[];
            vm.familyMemberInfo=[];
            vm.relativePersonInfo=[];  
            // vm.familyMemberInfo=[{
            //     'partyRelationShipTypeId':'11',
            //     'partyId':'010000000011',
            //     'custName':'李四',
            //     'contactCertificateTypeId':'1X',
            //     'contactCertificateNo':'123432123',
            //     'authCountry':'CNH',
            //     'birthDate':'20010101',
            //     'gender':'2',
            //     'flag':'7',
            //     // 'partyId':'020000001002',
            //     'partyGroupName':'北京联创智融信息技术有限公司',
            //     'partyAddr':'北京市东城区航星园',
            //     'partyPostal':'100022',
            //     'jobTitle':'2',
            //     'post':'人力资源总监',
            //     'contactNumber':'01088887777',
            //     'monthIncome':'6000',
            //     'degree':'2',
            //     'isOurBankCustomer':'0'
            // }];
            // vm.relativePersonInfo=[{
            //     'partyRelationShipId':'333',
            //     'partyRelationShipTypeId':'24',
            //     'partyId':'010000000123',
            //     'contactCertificateTypeId':'1X',
            //     'contactCertificateNo':'767654323',
            //     'authCountry':'CNH',
            //     'lastName':'王五',
            //     'birthDate':'19991201',
            //     'gender':'1',
            //     'job':'2',
            //     'partyName':'南京张三王五有限公司',
            //     'partyAddr':'南京市鼓楼区叉叉路',
            //     'partyPhone':'02533334444',
            //     'partyType':'1',
            //     'post':'助理',
            //     'monthIncome':'6000',
            //     'contactNumber':'13544442222',
            //     'qualify':'2',
            //     'isOurBankCustomer':'1'
            // }];
            queryuser();
            personOverviewQuery();
        }
         /**
         * @function
         * @name queryuser
         * @description 接收客户信息
         * */
        function queryuser(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            $rootScope.$broadcast('user', vm.user);
        }
         function submitgo(){
            if (vm.regForm.$valid) {
            $state.go('depositCurrentToTimeResult');
            }
         }
         function backInput(){
            $state.go('depositCurrentToTime');
         }
         /**
         * 查询客户总览信息
         */
         function personOverviewQuery(){
            var params = {
                reqHead:{
                    flag:"1",                        //主副交易标志 1是主交易     2是副交易
                    tradeType:"3",                   // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    serviceName:"queryPersonOverviewAction"        // 服务名称
                },
                reqBody:{
                    partyId: vm.user.customerNo                    //客户号
                }
                
            };
            var promise = UserService.personOverviewQuery(params);    //查询客户总览信息

            promise.then(function(data) {
                
                if (data) {
                    vm.personSummaryInfo=data.rspBody.personOverview.personSummaryInfo;
                    vm.familyMemberInfo=data.rspBody.personOverview.familyMemberInfo;
                    vm.relativePersonInfo=data.rspBody.personOverview.relativePersonInfo;  
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
         }
    }
})();
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('custPropertyDetailController', custPropertyDetailController);

    custPropertyDetailController.$inject = ['$rootScope', 'UserService','CommonService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name personOverviewController
     * @param  {service} UserService 账户服务
     * @description
     * 交易明细页面控制器
     */
    function custPropertyDetailController($rootScope, UserService,CommonService, $state, CONFIG, $scope, $timeout) {
        
        var vm = this;
        vm.custPropertyDetail=custPropertyDetail;
        vm.model={};
        //初始化方法
        init();

        function init(){
            
             vm.depositDetailInfo=[];
            vm.loanDetailInfo=[];
            vm.investDetailInfo=[];  
            vm.signDetailInfo=[];
            vm.creditCardDetailInfo=[];
            var detialData=$rootScope.detialData;
            if(detialData){
              vm.assetType=detialData.data.assetType;
              vm.name=detialData.name;
            }
            queryuser();
            custPropertyDetail();
        }
        /**
         * @function
         * @name queryuser
         * @description 接收客户信息
         * */
        function queryuser(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            $rootScope.$broadcast('user', vm.user);
        }
         
         /**
         * 查询客户资产明细信息
         */
         function custPropertyDetail(){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryCustPropertyDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: vm.user.customerNo,                    //客户号
                   partyTypeId:"1",                            //客户类型:1-个人客户,3-公司客户,0-同业客户
                   propertyType:vm.assetType                             //资产类型:1-存款类,2-贷款类,3-投资类,4-签约类,5-信用卡
                }
            };
            // var promise = UserService.getPropertyDistribution(params);
            var promise = UserService.custPropertyDetailQuery(params);    //查询客户总览信息
            promise.then(function(data) {
                if(data){//rspBody
                    vm.depositDetailInfo=data.rspBody.custPropertyDetailQry.depositDetailInfo;
                    vm.loanDetailInfo=data.rspBody.custPropertyDetailQry.loanDetailInfo;
                    vm.investDetailInfo=data.rspBody.custPropertyDetailQry.investDetailInfo;  
                    vm.signDetailInfo=data.rspBody.custPropertyDetailQry.signDetailInfo;
                    vm.creditCardDetailInfo=data.rspBody.custPropertyDetailQry.creditCardDetailInfo;
                }
            }).catch(function(error) {
                vm.emptyPie = emptyPiePath;
                CommonService.showError(error);
            });
         }
    }
})();
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope','ModalService', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name HomeController
     * @param  {service} CommonService    公用服务
     * @param  {service} $state           通用服务
     * @description
     * 首页控制器
     */
    function HomeController($rootScope,ModalService, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.logout = logout;
        vm.personOverviewPage=personOverviewPage;
        vm.custPropertyDetail=custPropertyDetail;

        vm.products = [];
        var productsProcode;
        vm.optionDisplay = false;
        vm.slideProImg = slideProImg;//点击轮播图片触发方法

        var categoryList = [{
            type: '1',
            name: '存款资产',
            color: '#d193db',
            operator: [{
                'state': 'undefined',
                'name': '充值'
            }, {
                'state': 'undefined',
                'name': '提现'
            }]
        }, {
            type: '2',
            name: '贷款资产',
            color: '#1156b6',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '3',
            name: '投资资产',
            color: '#ffaf00',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '4',
            name: '签约产品',
            color: '#20bd61',
            operator: [{
                'state': 'undefined',
                'name': '申购'
            }, {
                'state': 'undefined',
                'name': '赎回'
            }]
        }, {
            type: '5',
            name: '信用卡',
            color: '#f35050',
            operator: [{
                'state': 'undefined',
                'name': '转入'
            }, {
                'state': 'undefined',
                'name': '转出'
            }]
        }
        // , {
        //     type: '005',
        //     name: '贷款',
        //     color: '#00eeee',
        //     operator: [{
        //         'state': 'undefined',
        //         'name': '转入'
        //     }, {
        //         // 'state': 'cashRedemption',
        //         'state': 'undefined',
        //         'name': '转出'
        //     }]
        // }
        ];




        init();//初始化方法

        /**
         * @memberof HomeController
         * @function init
         * @description 初始化方法
         */
        function init(){

            if (sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER)) {
            // 用户信息
               vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        	   queryTreeList();		                   // 查询菜单信息
               queryuser();                            //接收客户信息
               getPropertyDistribution();              // 获取资产分布

               // custPropertyDetail();
               if(JSON.parse(sessionStorage.getItem('promotionsList'))==undefined){
                    queryDiscoverPromotions();//查询促销信息
               }else{
                    vm.productIdList = JSON.parse(sessionStorage.getItem('promotionsList'));
                    promotCarousel();
               }
            }
        }

        function personRemindInfo(){
                var num = 0;
                var len = $(".product-info1 li").length;
                function fun(){
                var mal = num * -80
                $(".product-info1").animate({marginTop:mal},500)
                num++;
                if(num == 10){
                    num = 1
                }

            }
            // var t = setInterval(fun,1000)
        }

        /**查询促销信息
         * @memberof HomeController
         * @function queryDiscoverPromotions
         * @description 查询促销信息
         */
        function queryDiscoverPromotions(){
            var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryDiscoverPromotionsAction" // 服务名称
                },
                reqBody: {
                    productStoreId: vm.user.productStoreId, //  店铺标识
                    goodStatus: "00",                            //  商品状态
                    goodsType: "02",                             //  商品类型
                    goodsFlag: "00"                              //  上下架标志
                }
            };
            var promise = UserService.queryDiscoverPromotions(params);

            promise.then(function(data) {
                if (data.rspHead.returnCode=="00000000") {
                    vm.productIdList = data.rspBody.promotionsList;
                    if (vm.productIdList==null || vm.productIdList==undefined) {
                        return;
                    }
                    sessionStorage.setItem('promotionsList', JSON.stringify(vm.productIdList));
                    promotCarousel();

                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });
        }
        /**设置轮播时间间隔
         * @memberof HomeController
         * @function promotCarousel
         * @description 设置轮播时间间隔
         */
        function promotCarousel(){
            // 设置轮播图图片间隔
            $scope.myInterval = 3000;
        }
        /**点击轮播图片进入促销产品列表
         * @memberof HomeController
         * @function slideProImg
         * @description 点击轮播图片进入促销产品列表
         */
        function slideProImg(productIdList){
            sessionStorage.setItem('discoverPromotionsList', JSON.stringify(productIdList));
            $state.go('discoverPromotions',{discoverPromotionsListFlag:'0'});
        }
         /**
         * @function
         * @name queryuser
         * @description 接收客户信息
         * */
        function queryuser(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            $rootScope.$broadcast('user', vm.user);
        }
        /**
         * @memberof HomeController
         * @parm treeMenuList
         * @description 实时接收客户信息
         */
         $scope.$on('user', function(event, data) {

            vm.user = data;

        });
        /**
         * @function
         * @name logout
         * @description 用户退出
         * */
        function logout() {
             var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"3",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"logoutAction"         // 服务名称
                },
                reqBody:{

                }
            };
            var promise = UserService.logout();
            promise.then(function() {
                $rootScope.hasLogin = false;
                $state.go('login');
            }).catch(function(error) {
                CommonService.showError(error);
            });
        }

        /**
         * @memberof queryTreeList
         * @param
         * @description 查询菜单信息
         */
        function queryTreeList(){

            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryPbankLoginMenuAction"         // 服务名称
                },
                reqBody:{

                }
            };

        	var promise = UserService.getTreeMenu(params);

        	promise.then(function(data) {
        		vm.treeMenuList = data.rspBody.returnList;
                $rootScope.$broadcast('treeMenuList', vm.treeMenuList);

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        /**
         * @memberof HomeController
         * @parm treeMenuList
         * @description 实时接收查询菜单信息
         */
         $scope.$on('treeMenuList', function(event, data) {

            vm.treeMenuList = data;

        });


         // 获取财富分布信息
        function getPropertyDistribution() {
            vm.flag=0;
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryPersonOverviewAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: vm.user.customerNo                   //客户号
                }
            };
            // var promise = UserService.getPropertyDistribution(params);
            var promise = UserService.personOverviewQuery(params);    //查询客户总览信息
            promise.then(function(data) {
                var personPropertyList=[];
                var depositPropertyMap={};  //存款资产
                var loanPropertyMap={};     //贷款资产
                var investPropertyMap={};   //投资资产
                var signProductCountMap={}; //签约产品汇总
                var creditCardCountMap={};  //信用卡汇总
                var personPropertyInfo=data.rspBody.personOverview.personPropertyInfo;
                vm.personRemindInfo=data.rspBody.personOverview.personRemindInfo;
                if(vm.personRemindInfo){
                    vm.flag=1;
                }
                if (personPropertyInfo && personPropertyInfo.length > 0) {

                    depositPropertyMap.assetType="1";
                    depositPropertyMap.assetSum=personPropertyInfo[0].depositProperty;
                    depositPropertyMap.assetDesc="存款资产";
                    personPropertyList.push(depositPropertyMap);

                    loanPropertyMap.assetType="2";
                    loanPropertyMap.assetSum=personPropertyInfo[0].loanProperty;
                    loanPropertyMap.assetDesc="贷款资产";
                    personPropertyList.push(loanPropertyMap);

                    investPropertyMap.assetType="3";
                    investPropertyMap.assetSum=personPropertyInfo[0].investProperty;
                    investPropertyMap.assetDesc="投资资产";
                    personPropertyList.push(investPropertyMap);

                    signProductCountMap.assetType="4";
                    signProductCountMap.assetSum=personPropertyInfo[0].signProductCount;
                    signProductCountMap.assetDesc="签约产品";
                    personPropertyList.push(signProductCountMap);

                    creditCardCountMap.assetType="5";
                    creditCardCountMap.assetSum=personPropertyInfo[0].creditCardCount;
                    creditCardCountMap.assetDesc="信用卡";
                    personPropertyList.push(creditCardCountMap);
                }
                showDistributionData(personPropertyList); //显示数据
                showDistributionPie(personPropertyList); //显示饼图
                personRemindInfo();
                // 保存产品信息--我的账户获取
                sessionStorage.setItem('personPropertyList', JSON.stringify(personPropertyList));
            }).catch(function(error) {
                // vm.emptyPie = emptyPiePath;
                CommonService.showError(error);
            });
        }


         function showDistributionPie(productList) {
            productList = productList.filter(function(product){
                return product.assetSum > 0;
            });

            if(productList && productList.length > 0){
                vm.hasPie = true;
            } else {
                vm.hasPie = false;
            }

            var colorArray = [];        //饼图颜色
            var pieSeries = [];
            var distribution;
            if (productList && productList.length > 0) {
                productList.forEach(function(product) {
                    colorArray.push(product.color);
                    var assetAmt="0.00";
                    if(product.assetType=="4"||product.assetType=="5"){
                        assetAmt="5000.00";
                    }else{
                        assetAmt=product.assetSum;
                    }
                    pieSeries.push({
                        assetType:product.assetType,
                        value: parseFloat(assetAmt),
                        name: product.assetDesc
                    });
                });
            } else {
                vm.emptyPie = emptyPiePath;
            }

            vm.pieOptions = {
                color: colorArray,
                backFun:custPropertyDetail,
                series: [{
                    name: '账户总览',
                    type: 'pie',
                    selectedMode: null,
                    hoverAnimation: false,
                    avoidLabelOverlap: false,
                    center: ['50%', '45%'],
                    radius: ['40%', '85%'],
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            formatter: '{b} \n {d}%',
                            textStyle: {
                                color: '#262626',
                                fontSize: 18
                            }
                        }
                    },
                    data: pieSeries
                }]
            };
        }


        function showDistributionData(productList) {
            var distributionData = [];
            categoryList.forEach(function(category) {
                var data = {
                    type: category.type,
                    color: category.color,
                    operator: category.operator,
                    sum: 0,
                    desc: category.name
                };
                if (productList && productList.length > 0) {
                    productList.forEach(function(product) {
                        if (product.assetType == category.type) {
                            data.assetType=product.assetType;
                            data.sum = parseFloat(product.assetSum);
                            product.color = data.color;
                        }
                    });
                }
                distributionData.push(data);
            });
            vm.distributionData = distributionData;
        }
        /**
        * 客户总览基本信息显示
        */
       function personOverviewPage(){
            $state.go('personOverview');
       }

       function custPropertyDetail(params){
            // 向子页面传值
            $rootScope.detialData = params;
            ModalService.showModal({
                templateUrl: 'app/components/personOverview/custPropertyDetail.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
       }


    }

})();

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

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('ValidationService', ValidationService);

    ValidationService.$inject = ['CommonService', 'UserService', 'CONFIG', '$rootScope', '$state'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name ValidationService
     * @param {service} CONFIG     通用Http请求服务
     * @param {service} http       通用配置项
     * @description  系统公共服务
     */
    function ValidationService(CommonService, UserService, CONFIG, $rootScope, $state) {
        var service = {
            validatePattern: validatePattern,               // 自定义格式验证
            validateIDCard: validateIDCard,                 // 验证身份证是否符合格式要求
            validatePhoneNumber: validatePhoneNumber,       // 验证手机号码是否符合格式要求
            validateName: validateName,                     // 验证姓名格式是否符合要求
            isEmpty: isEmpty,                               // 验证字符串判空
            isShorter: isShorter,                           // 检查字符串的长度
            isInteger: isInteger,                           // 检查字符串是否是整数
            isDecimal: isDecimal,                           // 判断输入变量是否是实数
            isIntChar: isIntChar,                           // 判断输入变量是否是数字或者字母
            isIntCharSpecial: isIntCharSpecial,             // 判断输入变量是否是数字或者字母或者特殊字符
            containIntChar: containIntChar,                 // 判断输入变量是否包含数字或者字母
            containSpecial: containSpecial,                 // 判断输入变量是否包含特殊字符
            containRiskStr: containRiskStr,                 // 判断输入变量是否包含危险输入
            checkAcc: checkAcc,                             // 账号验证，账号要求不能为空，为8-20位整数
            trim: trim,                                     // 去掉字符串前后的空格
            isDate: isDate,                                 // 检验日期是否符合YYYYMMDD的格式，是否合法
            dateInterval: dateInterval,                     // 检查时间间隔是否在规定间隔之内
            checkRecAcc: checkRecAcc,                       // 跨行转账账号验证，账号要求不能为空，最大长度35位
            monthBetween: monthBetween,                     // 查询的起始时间和结束时间是否在一个范围内
            getDateNormal: getDateNormal,                   // 获取当前日期
            formatShowDateTime: formatShowDateTime,         // 用于处理数据库记录的时间，将其转换成可以显示在页面上的时间，时间格式为yyyyMMddHHmmss
            removeComma: removeComma,                       // 替换千分号
            isMoney: isMoney,                               // 检查字符串是否为合法的金额
            toStdAmount: toStdAmount,                       // 删除千分号
            toCashWithComma: toCashWithComma,               // 为金额添加,分割符
            toCashWithMinus: toCashWithMinus,               // 为负数的金额添加,分割符和.分割符
            toCashWithCommaAndDot: toCashWithCommaAndDot,   // 为金额添加,分割符和.分割符
            toChineseCash: toChineseCash,                   // 将金额转换为大写
            formatDateTime: formatDateTime,                 // 格式化日期
            formatDateTimeYmd: formatDateTimeYmd,           // 格式化月份YYYY年MM月
            formatMonthYm: formatMonthYm,                   // 格式化日期（年月日）
            getFmtFullGrade: getFmtFullGrade,               // 格式化利率
            deleteMonthBeforeZero: deleteMonthBeforeZero,   // 去掉1~9月份前面的0
            bigNumSub: bigNumSub,                           // 减法运算 求a,b两个数的差
            bigNumAdd: bigNumAdd,                           // 加法运算 求a,b两个数的和
            fillSameLengthArray: fillSameLengthArray,       // 对传入的字符串a,b,进行处理（左补零或右补零），使之变成相同的长度的数值，存放在返回的数组里面
            trimStringZero: trimStringZero,                 // 去掉金额前面多余的0
            bigSmalCompare: bigSmalCompare                  // 判断数据大小

        };

        return service;


        /**
         * 验证身份证是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validatePattern(key, value) {
            var isValid = false;
            switch(key) {
                case 'identification':
                    isValid = validateIDCard(value);
                    break;
                case 'phone':
                    isValid = validatePhoneNumber(value);
                    break;
                case 'name':
                    isValid = validateName(value);
                    break;
                case 'amount':
                    isValid = validateAmount(value);
                    break;
                case 'email':
                    isValid = validateEmail(value);
                    break;
                case 'account':
                    isValid = validateAccount(value);
                    break;
                default:
                    isValid = false;
                    break;
            }
            return isValid;
        }


        /**
         * 验证银行账号是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validateAccount(value){
            var reg = /^\d{19}$/g;   // 以19位数字开头，以19位数字结尾

            return reg.test(value);
        }

        /**
         * 验证邮箱是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validateEmail(value){
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;

            return reg.test(value);
        }

        /**
         * 验证身份证是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validateIDCard(value) {
            var regex = /^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}(((19|20)\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|((19|20)\d{2}(0[13578]|1[02])31)|((19|20)\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)+$/;

            return regex.test(value);
        }

        /**
         * 验证手机号码是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validatePhoneNumber(value) {
            var regex = /^0?1[3|4|5|8|7][0-9]\d{8}$/;

            return regex.test(value);
        }


        /**
         * 验证姓名格式是否符合要求
         * @memberOf UserService
         * @return true or false
         */
        function validateName(value) {
            var regex = /^[\u4E00-\u9FA5]{2,6}$/;

            return regex.test(value);
        }


        /**
         * 验证提现和充值资金输入是否合理
         * @memberOf UserService
         * @return true or false
         */
        function validateAmount(value) {
            var regex = /^(([1-9](\d+)?(\.\d{1,2})?))$|^(0\.\d{1,2})$/;

            return regex.test(value);
        }

        /**
         * 字符串去空
         * @memberOf UserService
         * @return true or false
         */
        function trim(input) {
            return input.replace(/(^\s*)|(\s*$)/g, "");
        }


        /**
        * 本文件包括两部分，前半部分为通用验主下函数，后半部分为针对网银业务的验证函数
        ***************************通用验证函数*******************************
        *
        */

        /**
        * 检验字符串是否为空
        * @param {String} 字符串
        * @return {bool} 是否为空
        */
        function isEmpty(input)
        {
            if( input==null ||  trim(input).length == 0 || input == 'null')
                return true;
            else
                return false;
        }

        /**
        * 检查字符串的长度
        * @param {String} 字符串
        * @param {Integer} 要比较的长度
        * @return {bool} true：变量长度<给出的长度；false：变量长度>=给出的长度
        */
        function isShorter(str,reqlength)
        {
            if( str.length<reqlength )
                return true;
            else
                return false;
        }

        /**
        * 检查字符串是否是整数
        * @param {String} 字符串
        * @return {bool} 是否是整数
        */
        function isInteger( s )
        {
            var isInteger = RegExp(/^[0-9]+$/);
            return ( isInteger.test(s) );
        }

        /**
        * 判断输入变量是否是实数
        * @param {String} 要检查的变量值
        * @return {bool} 是否为实数
        */
        function isDecimal( s )
        {
            var isDecimal = RegExp(/^([0-9]+(\.?))?[0-9]+$/);
            return ( isDecimal.test(s) );
        }

        /**
        * 判断输入变量是否是数字或者字母
        * @param {String} 要检查的变量值
        * @return {bool} 是否为数字或者字母
        */
        function isIntChar( s )
        {
            var isIntChar = RegExp(/^[a-zA-Z0-9]+$/);
            return ( isIntChar.test(s) );
         }

        /**
        * 判断输入变量是否是数字或者字母或者特殊字符
        * 特殊字符：|_ - * & % $ # @ ! ~ ^ ( )
        * @param {String} 要检查的变量值
        * @return {bool} 是否为数字或者字母或者特殊字符
        */
        function isIntCharSpecial( s )
        {
            var isIntCharSpecial = RegExp(/^[a-zA-Z0-9(\|)(\_)(\*)(\&)(\%)(\$)(\#)(\@)(\!)(\~)(\^)]+$/);
            return ( isIntCharSpecial.test(s) );
        }

        /**
        * 判断输入变量是否包含数字或者字母
        * @param {String} 要检查的变量值
        * @return {bool} 是否包含数字或者字母
        */
        function containIntChar( s )
        {
            var containIntChar = RegExp(/[a-zA-Z0-9]+/);
            return ( containIntChar.test(s) );
        }

        /**
        * 判断输入变量是否包含特殊字符
        * 特殊字符：~ ! @ # $ % ^ & * ( ) - _ + = [ ] { } | \ ; : ' " , . / < > ? <
        * @param {String} 要检查的变量值
        * @return {bool} 是否包含特殊字符
        */
        function containSpecial( s )
        {
            var containSpecial = RegExp(/[\ \~\!\@\#\$\%\^\&\*\_\+\=\[\]\{\}\|\\\;\:\'\"\,\.\/\<\>\?]+/);
            return ( containSpecial.test(s) );
        }


        /**
        * 判断输入变量是否包含危险输入
        * 特殊字符： , -- < > ; % & script  select  insert delete ...
        * @param {String} 要检查的变量值
        * @return {bool} 是否包含特殊字符
        */
        function containRiskStr( s )
        {

            var regArray=new Array(/',/i,/</i,/>/i,/';/i,/%/i,/&/i,/├/i,/└/i,/script/i,/iframe/i,/select/i,/insert/i,/delete/i,/from/i,/drop/i,/update/i,/exec/i,/master/i,/form/i);
            for(var i=0;i<regArray.length;i++){
                var pattern=regArray[i];
                  if(pattern.test(s)){
                        return true;
                  }
            }
            return false;
        }


        /*****************************************网银通用验证函数***************************************
        /***
         * 账号验证，账号要求不能为空，为8-20位整数
         * @param acc
         */
        function checkAcc(acc){
            if(acc==null||acc==""||acc=="null"){
                return 1;
            }
            if(acc.length<8||acc.length>20){
                return 2;
            }
            if(!isInteger(acc)){
                return 3;
            }
            return 0;
        }
        /**
        * 去掉字符串前后的空格
        * @param {String} 字符串
        * @return {String} 去除空格后的字符串
        */
        function trim(input) {
            return input.replace(/(^\s*)|(\s*$)/g, "");
        }

        /**
        * 检验日期是否符合YYYYMMDD的格式，是否合法
        * @param {String} 日期字符串
        * @return {bool} 是否是合法日期
        */
        function isDate(dateInput) {
            var inputYear = dateInput.substring( 0, 4 );
            var inputMonth = parseInt(dateInput.substring( 4, 6 ),10)-1;
            var inputDay = dateInput.substring( 6, 8 );
            var dateTest = new Date(inputYear,inputMonth,inputDay);
            var testYear = dateTest.getFullYear();
            var testMonth = dateTest.getMonth();
            var testDay = dateTest.getDate();
            var isValidateDate = (inputYear == testYear && inputMonth == testMonth && inputDay == testDay );
            return isValidateDate;
        }

        /**
        * 检查时间间隔是否在规定间隔之内
        * @param {String} 开始日期
        * @param {String} 结束日期
        * @param {Integer} 间隔，单位为天
        * @return {bool} 是否在规定间隔之内
        */
        function dateInterval(startDate,endDate,interval) {
            var date1 = new Date(eval(startDate.substring(0,4)),eval(startDate.substring(4,6))-1,eval(startDate.substring(6,8)));
            var date2 = new Date(eval(endDate.substring(0,4)),eval(endDate.substring(4,6))-1,eval(endDate.substring(6,8)));
            if ( ( date2 - date1 ) / 86400000 > eval(interval) - 1 )
                return false;
            return true;
        }
        /***
         * 跨行转账账号验证，账号要求不能为空，最大长度35位
         * @param acc
         */
        function checkRecAcc(acc){
            if(isEmpty(acc)){
                return 1;
            }
            if(containRiskStr(acc)){
                return 2;
            }
            if(!isInteger(acc)){
                return 2;
            }
            return 0;
        }

        /**
         * 查询的起始时间和结束时间是否在一个范围内
         * 示例：
         * monthBetween(startDate,endDate,monthNum);
         *
         * 参数：startDate 起始时间
         * 参数：endDate 结束时间
         * 参数：monthNum 时间月份范围
         * 返回值： true 在 false 不在
         *
         * Version: 1.00
         * Author: 姜志鑫
         */
        function monthBetween(startDate,endDate,monthNum){
            var startYear = startDate.substring(0,4);
            var startMonth = startDate.substring(4,6);
            var startDay = startDate.substring(6);
            var endYear = endDate.substring(0,4);
            var endMonth = endDate.substring(4,6);
            var endDay = endDate.substring(6);
            var betweenMonth = (parseInt(endYear)-parseInt(startYear))*12 + parseInt(endMonth - startMonth);
            if(monthNum >= 0 && (parseInt(endDay,10) - parseInt(startDay,10)) > 0)
                betweenMonth=Math.abs(betweenMonth)+1;
            if(monthNum < 0 && (parseInt(endDay,10) - parseInt(startDay,10)) < 0)
                betweenMonth=Math.abs(betweenMonth)+1;
            //为了兼容负数，用绝对值来比较
            betweenMonth=Math.abs(betweenMonth);
            monthNum=Math.abs(monthNum);
            if(betweenMonth==0)
                return true;
            else if(betweenMonth - monthNum < 0)
            {
                return true;
            }
            else if((betweenMonth - monthNum) == 0)
            {
                return true;
            }
            else
                return false;
        }


        /**
        * 获取当前日期
        */
        function getDateNormal(){
            var myDate = new Date();
            var year=myDate.getFullYear();
            var month=myDate.getMonth()+1;
            if(month<10){
                month="0"+month;
            }
            var date=myDate.getDate();
            if(date<10){
                date="0"+date;
            }
            return year+'-'+month+"-"+date;
        }

        /***
         * 用于处理数据库记录的时间，将其转换成可以显示在页面上的时间，时间格式为yyyyMMddHHmmss
         * @param v
         */
        function formatShowDateTime(v){
            if(v==""){
                return "";
            }else{
                return v.substring(0,4)+"-"+v.substring(4,6)+"-"+v.substring(6,8)+" "+v.substring(8,10)+":"+v.substring(10,12)+":"+v.substring(12,14);
            }
        }

        /**
         * 替换千分号
         * @param str
         * @returns
         */
        function removeComma(str){
            return str.replace(new RegExp('\,',["g"]),'');
        }

        /**
         * 检查字符串是否为合法的金额
         * @param {String} 字符串
         * @return {bool} 是否为合法金额
         */
        function isMoney(s){
            var isMoney = RegExp(/^[0-9]{0,16}\.{0,1}[0-9]{0,2}$/);
            return ( isMoney.test(s) );
        }

        /**
         * 删除千分号
         * @param sAmount
         * @returns
         */
        function toStdAmount(sAmount){
            var sComma = /\,/gi;
            var sResult = sAmount.replace(sComma,"");
            var iDotIndex = sResult.indexOf('.');
            var iLength = sResult.length;
            var toMatchNaNum = /\D/;
            if ((iDotIndex!=-1&&(iLength-iDotIndex>3||toMatchNaNum.test(sResult.slice(0,iDotIndex))))
                ||toMatchNaNum.test(sResult.slice(iDotIndex+1,iLength))){
                flag=false;
                return 1;       // 小数点后大于2位数 或 含有非数字字符
            }else{
        // 将金额处理为######.##形式
                if (iDotIndex==-1){
                    sResult = sResult+'.00';
                }else if (iDotIndex==0){
                    if (iLength-iDotIndex==1) sResult='0'+sResult+'00';
                    if (iLength-iDotIndex==2) sResult='0'+sResult+'0';
                    if (iLength-iDotIndex==3) sResult='0'+sResult;
                }else{
                    if (iLength-iDotIndex==2) sResult=sResult+'0';
                    if (iLength-iDotIndex==1) sResult=sResult+'00';
                }

        // 处理金额非前面的0
                var sTemp = "";
                sTemp = sResult.slice(0,iDotIndex);
                /*var iTemp = new Number(sTemp);
                sTemp = iTemp.toString();*/
                sTemp = sTemp.split('.')[0];
                if (sTemp.length>16) {
                    flag = false;return 2;// 太长的金额
                }
                iDotIndex = sResult.indexOf('.');
                sResult = sTemp+sResult.slice(iDotIndex);   // 返回######.##形式的金额
                return sResult;
            }
        }

        function addComma(str) {
            if (str.length > 3){
                var mod = str.length % 3;
                var output = (mod > 0 ? (str.substring(0,mod)) : '');
                for (i=0 ; i < Math.floor(str.length / 3); i++) {
                    if ((mod == 0) && (i == 0)){
                        output += str.substring(mod+ 3 * i, mod + 3 * i + 3);
                    }else{
                        output += ',' + str.substring(mod + 3 * i, mod + 3 * i + 3);
                    }
                }
                return (output);
            }
            else {
                return str;
            }
        }

        /**
         * 为金额添加,分割符
         * @param {String} 要转换的金额字符串
         * @return {String} 转换后的金额字符串
         */
        function toCashWithComma( cash ){
            while( cash.charAt(0) == '0' )  {
                cash = cash.substr(1);
            }
            if( !isFloat( cash ) ){
                return addComma(cash);
            }
            var dotIndex = cash.indexOf('.');
            var integerCash = cash.substring( 0, dotIndex );
            var decimalCash = cash.substring( dotIndex );
            return addComma(integerCash)+decimalCash;
        }
        /**
         * 为负数的金额添加,分割符和.分割符
         *
         * @param {String}
         *            要转换的金额字符串
         * @return {String} 转换后的金额字符串
         */
        function toCashWithMinus(cash){
            if(cash.substring(0, 1)=='-'){
                cash=cash.substring(0, 1)+''+toCashWithCommaAndDot(cash.substring(1, cash.length));
            }else{
                cash=toCashWithCommaAndDot(cash);
            }
            if(cash.substring(0, 1)==''){
                cash = "0"+cash;
            }
            return cash;
        }

        /**
         * 为金额添加,分割符和.分割符
         * @param {String} 要转换的金额字符串
         * @return {String} 转换后的金额字符串
         */
        function toCashWithCommaAndDot( cash )
        {
            if (cash == null || cash =='null' || cash==''){
                return '';
            }
            var temp = toCashWithComma( cash );
            if(temp.substring(0, 1)==''){
                temp = "0"+temp;
            }
            if ( temp.length == 0 )
            {
                return "0.00";
            }
            var dotPos = temp.indexOf(".");
            if ( dotPos < 0 )
            {
                return temp+'.00';
            }
            if ( dotPos == 0 )
            {
                temp = '0' + temp;
                dotPos = temp.indexOf(".");
            }
            if ( dotPos == temp.length-2)
            {
                return temp + '0';
            }
            if ( dotPos == temp.length-1)
            {
                return temp + '00';
            }
            return temp;
        }

        /**
         * 将金额转换为大写
         * @param currencyDigits 需要转化的金额
         * @returns 转换后的大写金额，如果金额有误，返回空串
         */
        function toChineseCash(sAmount){
            if(sAmount==null || sAmount==""){
                return "";
            }
            var value = toStdAmount(sAmount);
            var sCN_Num = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");
            var unit = new Array('元', '万', '亿', '万');
            var subunit = new Array('拾', '佰', '仟');
            var sCNzero = '零';
            var result = "";
            var iDotIndex = value.indexOf('.');
            var sBeforeDot = value.slice(0, iDotIndex);
            var sAfterDot = value.slice(iDotIndex);
            var len = 0;
            len = sBeforeDot.length;
            var i = 0, j = 0, k = 0; // j is use to subunit,k is use to unit
            var oldC = '3';
            var cc = '0';
            result = unit[0] + result;
            var oldHasN = false;
            var hasN = false;
            var allZero = true;
            for (i = 0; i < len; i++) {
                if (j == 0 && i != 0) {
                    if (!hasN){
                        if ((k % 2) == 0) result = result.slice(1);
                    }else{
                        if (oldC == '0') result = sCNzero + result;
                    }
                    result = unit[k] + result;
                    oldHasN = hasN;
                    hasN = false;
                }
                cc = sBeforeDot.charAt(len - i - 1);
                if (oldC == '0' && cc != oldC){
                    if (hasN) result = sCNzero + result;
                }
                if (cc != '0'){
                    if (j != 0){
                        result = subunit[j - 1] + result;
                    }
                    var dig = '0';
                    dig = sCN_Num[cc];

                    if (dig == '0'){
                        return false;
                    }
                    hasN = true;
                    allZero = false;
                    result = dig + result;
                }
                oldC = cc;
                j++;
                if (j == 4){
                    k++;
                    j = 0;
                }
            }
            if (allZero){
                result = "零元";
            }else {
                var bb = 0;
                if (!hasN) {
                    bb++;
                    if (!oldHasN) {
                        bb++;
                    }
                }
                if (bb != 0){
                    result = result.slice(bb);
                }
                if (result.charAt(0) == '零'){
                    result = result.slice(1);
                }
            }

            // after dot
            sAfterDot = sAfterDot.slice(1);
            len = sAfterDot.length;
            var corn = new Array('0','0');
            var cornunit = new Array('角', '分');
            var n = 0; // j is use to subunit,k is use to unit
            var dig = '0';
            corn[0] = sAfterDot.charAt(0);
            if (len > 1){
                corn[1] = sAfterDot.charAt(1);
            }else{
                corn[1] = '0';
            }
            if ((corn[0] ==  '0') && (corn[1] == '0')) {
                return result += '整';
            }else{
                if (allZero) result = "";
            }
            for (i = 0; i < 2; i++){
                var curchar = corn[i];
                dig = sCN_Num[curchar];
                if (i==0){
                    if(result!=""||curchar!='0'){
                        result += dig;
                    }
                    if(curchar!='0'){
                        result += cornunit[0];
                    }
                }
                if (i==1&&curchar!='0') {
                    result = result+dig+cornunit[1];
                }
            }
            return result;
        }

        /*
        函数：格式化日期
        参数：formatStr-格式化字符串
        d：将日显示为不带前导零的数字，如1
        dd：将日显示为带前导零的数字，如01
        ddd：将日显示为缩写形式，如Sun
        dddd：将日显示为全名，如Sunday
        M：将月份显示为不带前导零的数字，如一月显示为1
        MM：将月份显示为带前导零的数字，如01
        MMM：将月份显示为缩写形式，如Jan
        MMMM：将月份显示为完整月份名，如January
        yy：以两位数字格式显示年份
        yyyy：以四位数字格式显示年份
        h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
        hh：使用12小时制将小时显示为带前导零的数字
        H：使用24小时制将小时显示为不带前导零的数字
        HH：使用24小时制将小时显示为带前导零的数字
        m：将分钟显示为不带前导零的数字
        mm：将分钟显示为带前导零的数字
        s：将秒显示为不带前导零的数字
        ss：将秒显示为带前导零的数字
        l：将毫秒显示为不带前导零的数字
        ll：将毫秒显示为带前导零的数字
        tt：显示am/pm
        TT：显示AM/PM
        返回：格式化后的日期
        */
        Date.prototype.format = function (formatStr) {
            var date = this;
             /*
             函数：填充0字符
             参数：value-需要填充的字符串, length-总长度
             返回：填充后的字符串
            */
            var zeroize = function (value, length) {
            if (!length) {
            length = 2;
            }
            value = new String(value);
            for (var i = 0, zeros = ''; i < (length - value.length); i++) {
             zeros += '0';
             }
             return zeros + value;
             };
             return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
             switch ($0) {
             case 'd': return date.getDate();
             case 'dd': return zeroize(date.getDate());
             case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
             case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
             case 'M': return date.getMonth();
             case 'MM': return zeroize(date.getMonth());
             case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
             case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
             case 'yy': return new String(date.getFullYear()).substr(2);
             case 'yyyy': return date.getFullYear();
             case 'h': return date.getHours() % 12 || 12;
             case 'hh': return zeroize(date.getHours() % 12 || 12);
             case 'H': return date.getHours();
             case 'HH': return zeroize(date.getHours());
             case 'm': return date.getMinutes();
             case 'mm': return zeroize(date.getMinutes());
             case 's': return date.getSeconds();
             case 'ss': return zeroize(date.getSeconds());
             case 'l': return date.getMilliseconds();
             case 'll': return zeroize(date.getMilliseconds());
             case 'tt': return date.getHours() < 12 ? 'am' : 'pm';
             case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';
             }
         });
         };

        /**
         * 格式化日期
         * @param v
         * @returns
         */
        function formatDateTime(v){
            try{
                if(isEmpty(v)){
                    return "";
                }else{
                    if(v.length>8&&15>v.length){
                        return v.substring(0,4)+"-"+v.substring(4,6)+"-"+v.substring(6,8)+" "+v.substring(8,10)+":"+v.substring(10,12)+":"+v.substring(12,14);
                    }
                    if(v.length>0&&9>v.length){
                        return  v.substring(0,4)+"-"+v.substring(4,6)+"-"+v.substring(6,8);
                    }else{
                        return v;
                    }
                }
            }catch(e){
                  return v;
            }
        }

        /**
         * 格式化日期（年月日）
         * @param v
         * @returns
         */
        function formatDateTimeYmd(v){
            try{
                if(isEmpty(v)){
                    return "";
                }else{
                    return v.substring(0,4)+"年"+v.substring(4,6)+"月"+v.substring(6,8)+"日";
                }
            }catch(e){
                  return v;
            }
        }

        //格式化月份YYYY年MM月
        function formatMonthYm(v){
            if(v==""){
                return "";
            }else{
                return v.substring(0,4)+"年"+v.substring(4,6)+"月";
            }
        }

        /**
         * 格式化利率
         * @param v
         * @returns
         */
        function getFmtFullGrade(v){
            try{
                if(isEmpty(v)){
                    return "";
                }
                var grade = (v/100).toFixed(2);
                return grade + '%';
            }catch(e){
                return v;
            }
        }


        /**
         * 去掉1~9月份前面的0
         * @param v
         * @returns
         */
        function deleteMonthBeforeZero(v){
            try{
                // 删除字符串开始的0
                return v.replace(/^0/, '');
            }catch(e){

            }
        }




        //------------------------大数据运算   开始--------------------------

        /**
         * @description 减法运算 求a,b两个数的差
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} a 减数，只能为字符串，不能为负数
         * @param {String} b 被减数，之能为字符串，不能为负数
         * @return {String} 返回a减b的结果
         * @example 范例1：
           function testExample(){
                bigNumSub("222222.01","22222");//返回:200000.01
                bigNumSub("222222.01","222221111.99");//返回:-221998889.98
                bigNumSub("9223372036854775809","9223372036854775807");//返回:2.0
                bigNumSub("9223372036854775805","9223372036854775809");//返回:-4.0
           }
        */
        function bigNumSub(a,b){
               var arrTmp = new Array();//函数返回的数组
               var arraTmp=new Array();//存放数据a的数组
               var arrbTmp=new Array();//存放数据b的数组
               var sumTmp = new Array();//存放和的数组
               var boolFu = false; //负数标志
               var tmp;
               if(bigSmalCompare(b,a)){
                      tmp = a;
                      a = b;
                      b = tmp;
                      boolFu = true;
               }

               arrTmp = fillSameLengthArray(a,b);
               arraTmp = arrTmp[0];
               arrbTmp = arrTmp[1];

               var borrow = 0;//借位
               for(var i=(arraTmp.length-1);i>=0;i--){
                     if(arraTmp[i] == '.'){
                         sumTmp[i] = ".";
                         continue;
                     }
                     //判断是否需要借位
                     var tmp = ""+eval(arraTmp[i]+"-"+arrbTmp[i]+"-"+borrow);
                     if(tmp < 0){
                           borrow = 1;
                           sumTmp[i] = borrow*10 - Math.abs(Number(tmp));
                     }else{
                          borrow = 0;
                          sumTmp[i] = tmp;
                     }

               }

               var sum="";
               for(var i=0;i<sumTmp.length;i++){
                    sum = sum+""+sumTmp[i];
               }
               sum = trimStringZero(sum);//去除左边多余的零
               if(boolFu){
                   sum = "-"+sum;
               }
               return sum+"";
        }



        /**
         * @description 加法运算 求a,b两个数的和
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} a 加数，只能为字符串，不能为负数
         * @param {String} b 被加数，只能为字符串，不能为负数
         * @return {String} 返回a加b的结果
         * @example 范例1：
           function testExample(){
                bigNumAdd("9223372036854775807","9223372036854775807");//返回:18446744073709551614.0
           }
        */
        function bigNumAdd(a,b){
               var arraTmp=new Array();//存放数据a的数组
               var arrbTmp=new Array();//存放数据b的数组
               var sumTmp = "";
               var arrTmp = new Array();//函数返回的数组

               arrTmp = fillSameLengthArray(a,b);
               arraTmp = arrTmp[0];
               arrbTmp = arrTmp[1];

               var overflower = 0;//进位
               for(var i=(arraTmp.length-1);i>=0;i--){
                     if(arraTmp[i] == "."){
                         sumTmp = "."+sumTmp;
                         continue;
                     }
                     var tmp = ""+eval(arraTmp[i]+"+"+arrbTmp[i]+"+"+overflower);
                     if(tmp.length == 1){
                          overflower = 0;
                          sumTmp = tmp+sumTmp+"";
                     }else{
                         if(i<=0){//最后1个数字
                             sumTmp = tmp+sumTmp+"";
                         }else{
                              overflower = tmp.substr(0,1);
                              sumTmp = tmp.substr(1,1)+sumTmp+"";
                         }

                     }
               }

              sum = trimStringZero(sumTmp);//去除左边多余的零
              return sum+"";
        }

        /**
         * @description 对传入的字符串a,b,进行处理（左补零或右补零），使之变成相同的长度的数值，存放在返回的数组里面
         * @author 作者 2014-1-1
         * @version 1.0
         * @private : 私有，内部
         * @param {String} a 字符串，只能为正数
         * @param {String} b 字符串，只能为正数
         * @return {Array} 返回数组
         * @example 无
        */
        function fillSameLengthArray(a,b){
               var aTmp = new Array();//存放临时a变量 aTmp[0]存放整数部分  aTmp[1]存放小数部分
               var bTmp = new Array();//存放临时b变量 bTmp[0]存放整数部分  bTmp[1]存放小数部分
               var arraTmp=new Array();//存放数据相同长度的a的数组
               var arrbTmp=new Array();//存放数据相同长度的b的数组
               var sumArray = new Array();//用来存放返回的数据  sumArray[0] = arraTmp   sumArray[1] = arrbTmp;

               if(a.indexOf(".") != -1){
                    aTmp = a.split(".");
               }else{
                    aTmp[0] = a;
                    aTmp[1] = "0";
               }
               if(b.indexOf(".") != -1){
                    bTmp = b.split(".");
               }else{
                    bTmp[0] = b;
                    bTmp[1] = "0";
               }

               //小数右边补零
               var lengthFillright = Math.abs( aTmp[1].length - bTmp[1].length);
               for(var i=0;i<lengthFillright;i++){
                     if(aTmp[1].length > bTmp[1].length){
                           bTmp[1] = bTmp[1]+"0";
                     }else{
                           aTmp[1] = aTmp[1]+"0";
                     }
               }

               //整数左补零
               var lengthFillleft = Math.abs( aTmp[0].length - bTmp[0].length);
               for(var i=0;i<lengthFillleft;i++){
                     if(aTmp[0].length > bTmp[0].length){
                           bTmp[0] = "0"+bTmp[0];
                     }else{
                           aTmp[0] = "0"+aTmp[0];
                     }
               }
               a = aTmp[0]+"."+aTmp[1];
               b = bTmp[0]+"."+bTmp[1];
               //数值都是一样的长度，将数据都放到数组里面

               for(var i=0;i<a.length;i++){
                    arraTmp[i] = a.substr(i,1);
                    arrbTmp[i] = b.substr(i,1);
               }

               sumArray[0] = arraTmp;
               sumArray[1] = arrbTmp;
               return sumArray;
        }


        /**
         * @description 去掉金额前面多余的0
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} str 需要进行处理的金额字符串
         * @return {String} 处理之后的字符串
         * @example 范例1：
           function testExample(){
                trimStringZero("0000012344.1234");//返回:12344.1234
           }
        */
        function trimStringZero(str)
        {
            var strTmp="";
            var times = 0;
            if(str.indexOf(".") != -1){//有小数位
                 strTmp = str.split(".");
                 str = strTmp[0];
                 times++;
            }
            //去除整数部分左边多余的0
            while ((str.substring(0,1)=="0") &&(str.length!=1) )
            {
                str = str.substring(1);
            }
            if(times > 0){//有小数位
                 str = str+"."+strTmp[1];
            }
            return str;
        }


        /**
         * @description 判断数据大小
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} a  只能为字符串
         * @param {String} b  只能为字符串
         * @return {boolean} true:a>b  false:a<=b
         * @example 范例1：
           function testExample(){
                bigSmalCompare("1","-123");//返回:true
                bigSmalCompare("9223372036854775807","9223372036854775807");//返回:false
                bigSmalCompare("9223372036854775807","9223372036854775808");//返回:false
                bigSmalCompare("-9223372036854775809","9223372036854775807");//返回:false
           }
        */
        function bigSmalCompare(a,b){
               var arrTmp = new Array();//函数返回的数组
               var arraTmp=new Array();//存放数据a的数组
               var arrbTmp=new Array();//存放数据b的数组
               arrTmp = fillSameLengthArray(a,b);
               arraTmp = arrTmp[0];
               arrbTmp = arrTmp[1];

               //比较大小
               for(var i=0;i<a.length;i++){
                     if(arraTmp[i] == "."){
                          continue;
                     }

                     if(arraTmp[i] > arrbTmp[i]){
                           return true;
                     }else if(arraTmp[i] < arrbTmp[i]){
                           return false;
                     }else{
                           if(i == (a.length-1)){
                                return false;
                           }
                     }
               }
        }
        //------------------------大数据运算   结束

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('UserService', UserService);

    UserService.$inject = ['$cookieStore', 'httpService', '$q', 'CONFIG', '$timeout','$rootScope'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name UserService
     * @param {service} httpService 通用HTTP请求服务
     * @param {service} CONFIG 通用配置项
     * @description
     * 处理用户相关的服务
     */
    function UserService($cookieStore, http, $q, CONFIG, $timeout,$rootScope) {

        var service = {
            login: login,                                       // 登录
            logout: logout,                                     // 登出
            getAllChlShelfGood: getAllChlShelfGood,             // 根据货架编号查询货架商品及菜单信息
            addRecordShopCart:addRecordShopCart,                // 添加某条记录到购物车并返回购物车信息服务
            queryShopCart:queryShopCart,                        // 查询购物车
            deleteClearShopCart:deleteClearShopCart,            // 清除购物车
            deleteShopCartRecord:deleteShopCartRecord,          // 删除购物车中某些记录
            updateRecordShopCart:updateRecordShopCart,          // 更新购物车的某条记录，并返回购物车信息服务
            calculateCartPrice:calculateCartPrice,              // 计算购物车价格
            externalProcessOrder:externalProcessOrder,          // 提交订单
            register:register,                                  // 自助注册
            forgetPassword:forgetPassword,                      // 忘记密码
            getCustomerListService:getCustomerListService,      // 产品视图
            getCustomerList:getCustomerList,                    // 查询账户列表
            sendPayReturn:sendPayReturn,                        // 支付发送
            getPropertyDistribution:getPropertyDistribution,    // 获取个人资产分布
            getproductDetail:getproductDetail,                  // 获取贷款产品信息列表
            getOrdersList:getOrdersList,                        // 我的订单-未完成订单
            getOrderDetail:getOrderDetail,                      // 我的订单-订单详情
            queryQuotedPrice:queryQuotedPrice,                  // 报价查询
            getSurveyQuestion:getSurveyQuestion,                // 获取调查信息
            queryloanList:queryloanList,                        // 查询我的贷款信息列表
            getFixedProduct: getFixedProduct,                   // 获取固收产品列表
            queryloandetail:queryloandetail,                    // 查询我的贷款信息详细列表
            getMobileValidateCode: getMobileValidateCode,       // 手机验证码
            validateMobileCode:validateMobileCode,              // 验证手机验证码
            transfer:transfer,                                  // 查询我的贷款信息详细列表
            getTreeMenu:getTreeMenu,                            // 菜单查询
            queryfinanciaList:queryfinanciaList,                // 查询理财产品列表
            queryproductList:queryproductList,                  // 查询产品详情
            getSubAccountListService:getSubAccountListService,  // 子账户信息查询
            queryStoreListInfo:queryStoreListInfo,              // 查询店铺与客户支付工具和客户地址信息列表流程
            queryProductListProcess:queryProductListProcess,    // 查询产品
            productrec:productrec,                              // 查询推荐产品列表
            browsehis:browsehis,                                // 查询浏览记录
            showProductDetail:showProductDetail,                // 查询产品详情
            queryDiscoverPromotions:queryDiscoverPromotions,    // 查询促销
            queryDiscoverHot:queryDiscoverHot,                  // 查询热销
            personOverviewQuery:personOverviewQuery,            // 客户总览查询
            updateBatchRecordShopCart:updateBatchRecordShopCart,// 批量更新购物车
            queryAttributeInfoMethod:queryAttributeInfoMethod,  // 客户总览查询
            custPropertyDetailQuery:custPropertyDetailQuery,    // 客户资产明细查询
            createShoppingListService:createShoppingListService, // 需求提交
            checkShowShelfGoodsInfoMethod:checkShowShelfGoodsInfoMethod //检查是否上架
        };

        return service;


        /**********************************平台登录体系开始*****************************************/

         /**
         * 用户登录
         * @memberOf UserService
         * @param       {String}    mobileNo            手机号码
         * @param       {String}    entryPswd           登录密码
         * @param       {String}    picValidateCode     图片验证码(可为空)
         * @returns     {Promise}   返回请求登录的Promise对象
         */
        function login(params) {
            return http.request(CONFIG.API.LOGIN, params);
        }
         /**
         * 用户登出
         * @memberOf UserService
         * @returns     {Promise}   返回请求登出的Promise对象
         */
        function logout(params) {
            localStorage.clear();
            sessionStorage.clear();
            return http.request(CONFIG.API.LOGOUT,params);
        }

        /**
         * 用户自助注册
         * @memberOf UserService
         * @returns     {Promise}   返回请求登出的Promise对象
         */
        function register(params){
            return http.request(CONFIG.API.REGISTER, params);
        }

        /**
         * 忘记密码
         * @memberOf forgetPassword
         * @returns     {Promise}   返回请求登出的Promise对象
         */
        function forgetPassword(params){
            return http.request(CONFIG.API.FORGETPASSWORD, params);
        }

        /**
         * 菜单查询
         * @memberOf UserService
         * @returns     {Promise}   返回请求登出的Promise对象
         */
        function getTreeMenu(params){
            return http.request(CONFIG.API.PBANKLOGINMENUINFO, params);
        }

        /**
         * 根据货架编号查询货架商品及菜单信息
         * @memberOf UserService
         * @returns     {Promise}   返回请求登出的Promise对象
         */
        function getAllChlShelfGood(params){
            return http.request(CONFIG.API.SHELFBYSHELFCODE, params);
        }


        /**********************************平台登录体系结束*****************************************/

        /**********************************账户体系开始*****************************************/

        /**
         * 产品视图查询
         * @memberOf UserService
         * @returns     {Promise}   返回查询的Promise对象
         */
        function getCustomerListService(params){
            return http.request(CONFIG.API.GETCUSTOMERLIST,params);
        }

        /**
         * 我的账户查询
         * @memberOf UserService
         * @returns     {Promise}   返回查询的Promise对象
         */
        function getCustomerList(params){
            return http.request(CONFIG.API.GETCUSTOMERLISTSERVICE,params);
        }

        /**
         * 子账户列表查询
         * @memberOf UserService
         * @returns     {Promise}   返回查询的Promise对象
         */
        function getSubAccountListService(params){
            return http.request(CONFIG.API.GETSUBACCOUNT,params);
        }

         /**
         * 获取个人资产分布
         * @memberOf FinancialService
         * @description 获取个人资产的分布
         * ```
         * 接口返回数据如下:
         * assetDesc 资产描述
         * assetNo 资产账号
         * assetPercent 资产占总资产百分比
         * assetSum 资产产品购买总额
         * assetType资产产品类别ID
         * ```
         * @returns {Promise} 获取个人资产分布信息
         */
        function getPropertyDistribution(params){
             return http.request(CONFIG.API.GET_PROPERTY_DISTRIBUTION, params);
        }

        /**
         * 手机验证码
         * @memberOf UserService
         * @returns     {Promise}   返回查询的Promise对象
         */
         function getMobileValidateCode(params) {
            return http.request(CONFIG.API.GET_MOBILE_CODE, params);
        }

        /**
         * 验证手机验证码
         * @memberOf UserService
         * @param       {String}    mobileNo        用户手机号码
         * @param       {String}    vrfyTxCode      验证码所属交易
         * @param       {String}    vrfyCode        验证码
         * @returns     {Promise}   返回请求验证手机验证码的Promise对象
         */
        function validateMobileCode(params){
            return http.request(CONFIG.API.VALIDATE_MOBILE_CODE, params);
        }
         /**
         * 转账
         * @memberOf UserService
         * @returns     {Promise}   返回查询的Promise对象
         */
         function transfer(params) {
            return http.request(CONFIG.API.TRANSFER, params);
        }

        /**********************************账户体系结束*****************************************/

        /**********************************平台转账体系开始*****************************************/
        /**
         * 获取固产品列表
         * @memberOf UserService
         * @param {string} CONFIG.API.FIXED_PRODUCT 固收产品接口号
         * @return {object} 返回产品列表以及产品总条数
         */
        function getFixedProduct(params) {
            return http.request(CONFIG.API.FIXED_PRODUCT, params);
        }
        /**********************************平台转账体系结束*****************************************/

        /**********************************平台授权体系开始*****************************************/

        /**********************************平台授权体系结束*****************************************/

        /**********************************平台机构体系开始*****************************************/

        /**********************************平台机构体系结束*****************************************/

        /**********************************平台客户体系开始*****************************************/
        /**
         * 客户总览查询
         * @memberOf UserService
         * @param       {String}    financialproductcode         理财模块码
         * @returns     {Promise}   返回提交的Promise对象
         */
        function personOverviewQuery(params){
            return http.request(CONFIG.API.PERSONOVERVIEWQUERY, params);
        }
        /**
         * 客户资产明细查询
         * @memberOf UserService
         * @param       {String}    financialproductcode         理财模块码
         * @returns     {Promise}   返回提交的Promise对象
         */
        function custPropertyDetailQuery(params){
            return http.request(CONFIG.API.CUSTPROPERTYDETAILQUERY, params);
        }
        /**
         *  产品ID查询产品详情
         * @memberOf UserService
         * @param       {String}    financialproductcode         
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryAttributeInfoMethod(params){
            return http.request(CONFIG.API.QUERYATTRIBUTEINFOMETHOD, params);
        }

        /**********************************平台客户体系结束*****************************************/

        /**********************************平台理财体系开始*****************************************/


        /**
         * 理财模块产品查询
         * @memberOf UserService
         * @param       {String}    financialproductcode         理财模块码
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryfinanciaList(params){
            return http.request(CONFIG.API.QUERYFINANCIALIST,params);
        }

        /**
         * 产品详情查询
         * @memberOf UserService
         * @param       {String}    productCode         产品编码
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryproductList(params){
            return http.request(CONFIG.API.QUERYPRODUCTLIST,params);
        }
        /**
         * 查询推荐产品
         * @memberOf UserService
         * @param       {String}    productCode         产品编码
         * @returns     {Promise}   返回查询的Promise对象
         */
         function productrec(params) {
            return http.request(CONFIG.API.PRODUCTREC, params);
        }
         /**
         * 查询浏览记录
         * @memberOf UserService
         * @param       {String}    userCode         客户编码
         * @returns     {Promise}   返回查询的Promise对象
         */
         function browsehis(params) {
            return http.request(CONFIG.API.BROWSEHIS, params);
        }

        /**********************************平台理财体系结束*****************************************/

        /**********************************平台安全体系开始*****************************************/

        /**********************************平台安全体系结束*****************************************/

        /**********************************平台柜员体系开始*****************************************/

        /**********************************平台柜员体系结束*****************************************/

        /**********************************购物车流程体系开始*****************************************/

        /**
         * 支付提交
         * @memberOf UserService
         **@param       {String}    accountType            账户
         * @param       {String}    cardPassword           短信验证码
         * @param       {String}    moblie                 密码
         * @returns     {Promise}   返回提交的Promise对象
         */
        function sendPayReturn(params){
            return http.request(CONFIG.API.SENDPAYRETURN,params);
        }

        /**
         * 我的订单-已完成订单
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function getOrdersList(params){
            return http.request(CONFIG.API.COMPLETED_ORDER,params);
        }
         /**
         * 我的订单-订单详情
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function getOrderDetail(params){
            return http.request(CONFIG.API.ORDER_DETAIL,params);
        }

         /**
         * 报价信息
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryQuotedPrice(params){
            return http.request(CONFIG.API.QUERYQUOTEDPRICE,params);
        }
          /**
         * 获取调查信息
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function getSurveyQuestion(params){
            return http.request(CONFIG.API.SURVEY_QUESTION,params);
        }
        /**
         * 需求提交
         * @memberOf UserService
         * @returns  {Promise}   返回提交的Promise对象
         */
        function createShoppingListService(params){
            return http.request(CONFIG.API.CREATESHOPPINGLISTSERVICE,params);
        }


         /**
         * 检查是否上架
         * @memberOf UserService
         * @returns  {Promise}   返回提交的Promise对象
         */
        function checkShowShelfGoodsInfoMethod(params){
            return http.request(CONFIG.API.CHECKSHOWSHELFGOODSINFOMETHOD,params);
        }
        /**
         * 购物车-更新购物车
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function addRecordShopCart(reqBody){
            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"addRecordShopCartAction"              // 服务名称
                }
            };

            params.reqBody = reqBody;
            return http.request(CONFIG.API.ADDRECORDSHOPCART,params);
        }

        /**
         * 购物车-查询购物车
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryShopCart(params){
            return http.request(CONFIG.API.QUERYSHOPCART,params);
        }

        /**
         * 购物车-清除购物车
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function deleteClearShopCart(params){
            return http.request(CONFIG.API.DELETECLEARSHOPCART,params);
        }

        /**
         * 购物车-删除购物车中某些记录
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function deleteShopCartRecord(params){
            return http.request(CONFIG.API.DELETESHOPCARTRECORD,params);
        }

        /**
         * 购物车-更新购物车的某条记录，并返回购物车信息服务
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function updateRecordShopCart(params){
            return http.request(CONFIG.API.UPDATERECORDSHOPCART,params);
        }

         /**
         * 购物车-计算购物车价格
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function calculateCartPrice(params){
            return http.request(CONFIG.API.CALCULATECARTPRICE,params);
        }


        /**
         * 购物车-提交订单
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function externalProcessOrder(params){
            return http.request(CONFIG.API.EXTERNALPROCESSORDER,params);
        }


        /**
         * 购物车-查询店铺与客户支付工具和客户地址信息列表流程
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryStoreListInfo(params){
            return http.request(CONFIG.API.QUERYSTORELISTINFO,params);
        }

        /**
         * 购物车-查询产品
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryProductListProcess(params){
            return http.request(CONFIG.API.QUERYPRODUCTLISTPROCESS,params);
        }
        /**
         * 查询促销
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryDiscoverPromotions(params){
            return http.request(CONFIG.API.QUERYDISCOVERPROMOTIONS,params);
        }
        /**
         * 查询热销
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function queryDiscoverHot(params){
            return http.request(CONFIG.API.QUERYDISCOVERHOT,params);
        }
         /**
         * 购物车-查询产品详情
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function showProductDetail(params){
            return http.request(CONFIG.API.SHOWPRODUCTDETAIL,params);
        }
       /**
         * 购物车-批量更新购物车
         * @memberOf UserService
         * @returns     {Promise}   返回提交的Promise对象
         */
        function updateBatchRecordShopCart(params){
            return http.request(CONFIG.API.UPDATEBATCHRECORDSHOPCART,params);
        }
        /**********************************购物车流程体系结束*****************************************/

        /**********************************平台贷款体系开始*****************************************/

        /**
         * 查询贷款产品信息
         * @memberOf UserService
         *@param       {String}    productTpye           产品编码
         * @returns     {Promise}   返回查询的Promise对象
         */
        function getproductDetail(params){
            return http.request(CONFIG.API.GETPRODUCTDETAIL,params);
        }
        /**
         * 查询我的贷款信息
         * @memberOf UserService
         *@param       {String}    custId           客户号
         * @returns     {Promise}   返回查询的Promise对象
         */
        function queryloanList(params){
            return http.request(CONFIG.API.QUERYLOANLIST,params);
        }
         /**
         * 查询我的贷款信息
         * @memberOf UserService
         * @param       {String}    loannum           贷款编号
         * @param       {String}    startdate         查询开始日期
         * @param       {String}    enddate           查询结束日期
         * @returns     {Promise}   返回查询的Promise对象
         */
        function queryloandetail(params){
            return http.request(CONFIG.API.QUERYLOANDETAIL,params);
        }

        /**********************************平台贷款体系结束*****************************************/

    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('ModalService', ModalService);

    ModalService.$inject = ['$modal', '$modalStack', '$rootScope'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name ModalService
     * @param {service} CONFIG     通用Http请求服务
     * @param {service} http       通用配置项
     * @description  系统公共服务
     */
    function ModalService($modal, $modalStack, $rootScope) {
        var modalInstance = {};
        var modalSymbolArr = [];
        var modalSymbol;
        var service = {
            showModal: showModal,                   // 打开Modal
            closeModal: closeModal,                 // 关闭Modal
            dismissModal: dismissModal,             // 消除Modal
            dismissAllModal: dismissAllModal        // 消除所有Modal
        };

        return service;

        /**
         * 打开Modal
         * @memberOf ModalService
         * @param       {object}        modal所需参数
         */
        function showModal(params) {
            if($.inArray(params.controller, modalSymbolArr) < 0) {
                modalSymbolArr.push(params.controller);
            }
            modalSymbol = modalSymbolArr[modalSymbolArr.length-1];
            modalInstance[params.controller] = $modal.open(params);
        }

        /**
         * 关闭Modal
         * @memberOf ModalService
         */
        function closeModal() {
            modalInstance[modalSymbol].close();
            delete modalInstance[modalSymbol];
            modalSymbolArr.length--;
            modalSymbol = modalSymbolArr[modalSymbolArr.length-1];
        }

        /**
         * 消除Modal
         * @memberOf ModalService
         */
        function dismissModal() {
            modalSymbolArr.length = 0;
            for (var key in modalInstance) {
                modalInstance[key].dismiss();
                delete modalInstance[key];
            }
        }

        /**
         * 消除所有Modal
         * @memberOf ModalService
         */
        function dismissAllModal() {
            $modalStack.dismissAll();
            modalSymbolArr.length = 0;
            modalInstance = {};
        }
    }
})();

(function() {
    'use strict';
    angular
        .module('EBankProject')
        .factory('httpService', HttpService);

    HttpService.$inject = ['$rootScope','$http', '$q', 'CONFIG', '$log'];

    function HttpService($rootScope,$http, $q, CONFIG, $log) {
        var _config, _send, _timeout, _serviceUrl;
        _timeout = 60000;
        _serviceUrl = CONFIG.ROOT_URL + CONFIG.URL;
        _config = function(method, url, options) {
            var c;
            c = {
                method: method,
                url: _serviceUrl,
                timeout: _timeout
            };
            return angular.extend({}, c, options);
        };
        _send = function(verb, url, options) {
            options = options || {};
            return $http(_config(verb, url, options));
        };

        return {
            request: function(txCode, reqParams) {
                if(reqParams == undefined){
                    reqParams = {};
                }
                if(reqParams.reqHead == undefined){
                    reqParams.reqHead = {};
                }
                if(reqParams.reqHead.version == "" || reqParams.reqHead.version == null){
                    var VERSION = CONFIG.REQHEAD.VERSION;
                }else{
                    var VERSION = reqParams.reqHead.version;
                }

                var CHANNELDATE = getDateNormal();

                var reqHead = {
                    "taxi":CONFIG.REQHEAD.TAXI,                             // 租户
                    "stePcode":reqParams.reqHead.stePcode,                  // 场景步骤
                    "channelDate":CHANNELDATE,                              // 发起端交易日期
                    "sceneCode":CONFIG.REQHEAD.SCENECODE,                   // 场景编码
                    "sceneName":CONFIG.REQHEAD.SCENENAME,                   // 场景名称
                    "legalId":CONFIG.REQHEAD.LEGALID,                       // 法人id
                    "legalCode":CONFIG.REQHEAD.LEGALCODE,                   // 法人编码
                    "flag":reqParams.reqHead.flag,                          // 主副交易标志 1是主交易     2是副交易
                    "tradeType":reqParams.reqHead.tradeType,                // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    "serviceName":reqParams.reqHead.serviceName,            // 服务名称
                    "version":VERSION                                       // 版本号，从1.0.0开始
                };

                reqParams.reqHead = reqHead;

                var paramsTemp = JSON.stringify(reqParams);
                var params = {
                        data: paramsTemp
                };

                var txCodeTem = txCode.substr(0,txCode.indexOf("/"));

                var txCodeuppercase = angular.uppercase(txCodeTem);

                var defer = $q.defer();

                if(CONFIG.OFFLINE){
                     _serviceUrl = CONFIG.ROOT_URL + CONFIG.URL + '/' + txCode;
                }else{
                    _serviceUrl = CONFIG.BROSURLROOT_URL[txCodeuppercase] + CONFIG.BROSURL[txCodeuppercase] + '/' + txCode;
                    //_serviceUrl = CONFIG.ROOT_URL + CONFIG.BROSURL[txCodeuppercase] + '/' + txCode;
                }

                _send('POST', _serviceUrl, params).success(function(data) {
                    // 暂时使用
                    if(txCode == "common_login" || txCode == "common_logout" || txCode == "manage/getCustomerLSstservice"){
                        data = {
                            "rspHead":{
                                "channelDate":"99999999",
                                "tranTime":"888888",
                                "acctDate":"",
                                "returnCode":"00000000",
                                "langCode":"",
                                "tranDate":"77777888",
                                "returnMsg":"交易成功",
                                "globalSeqNo":"000120160716111111110000009401",
                                "rsrvContent":"",
                                "backendSysId":"6666",
                                "backendSeqNo":"0001201607161111111100000094019001000000"
                            },
                            'pageInfo': {
                                'totalCount': '5'
                            },
                            'customerNo':'c10004',
                            // 'productStoreId':'MODEL-FP-OL-STORE-OB'
                            'productStoreId':'MODEL-FP-OL-STORE',
                            'respData': [{
                                'accnum': '6225 8888 8888 8888',
                                'accname':'工资卡',
                                'acclance':'1000.00',
                                'acctype':'01'
                            },{
                                'accnum': '6225 8888 8888 8888',
                                'accname':'工资卡',
                                'acclance':'1000.00',
                                'acctype':'01'
                            },{
                                'accnum': '6225 9999 9999 9999',
                                'accname':'借记卡',
                                'acclance':'2000.00',
                                'acctype':'02'
                            },{
                                'accnum': '6225 7777 7777 7777',
                                'accname':'活期一本通',
                                'acclance':'3000.00',
                                'acctype':'03'
                            },{
                                'accnum': '6225 6666 6666 6666',
                                'accname':'定期一本通',
                                'acclance':'4000.00',
                                'acctype':'04'
                            }]
                            
                            
                        }
                    }
                    $rootScope.data = data;
                    if (data != null && data.rspHead != null && data.rspHead.returnCode == CONFIG.CORRECT_CODE) {
                        defer.resolve(data);
                    } else {
                        defer.reject(data);
                    }
                }).error(function(err) {
                    $rootScope.data = err;
                    $log.log(err);
                    defer.reject(err);
                });

                return defer.promise;
            }
        };

        /**
        * 获取当前日期
        */
        function getDateNormal(){

            var myDate = new Date();
            var year=myDate.getFullYear();
            var month=myDate.getMonth()+1;
            if(month<10){
                month="0"+month;
            }else{
                month=""+month;
            }
            var date=myDate.getDate();
            if(date<10){
                date="0"+date;
            }else{
                date=""+date;
            }
            return year+month+date;
        }
    }

})();

(function() {
    'use strict';

    angular.module('EBankProject')
    .factory('httpInterceptor', httpInterceptor);

    httpInterceptor.$inject = ['$rootScope', '$q', 'CONFIG'];

    function httpInterceptor($rootScope, $q, CONFIG) {
        var httpInterceptor = {
            request: function(config) {
                // console.log(1);
                console.log(config);
                $rootScope.errorMsg = '';
                if(config.params && config.params.data) {
                    console.log(config.params.data.txCode);
                }
                return config;
            },

            requestError: function(config) {
                 //console.log(2);
                 //console.log(config);
                return config;
            },

            response: function(response) {
                //console.log(3);
                // console.log(response);
                return response;
            },

            responseError: function(responseError) {
                // 500
                //console.log(4);
                //console.log(responseError);
                var status = responseError.status;
                $rootScope.errorMsg = "";
                $rootScope.status = "";
                $rootScope.errorInterMsg = "";
                if(status != null) {
                    switch(status) {
                        case -1:
                            $rootScope.errorMsg = CONFIG.CONSTANT.ERROR.NO_SERVER;
                            $rootScope.status = "-1";
                            $rootScope.errorInterMsg = CONFIG.CONSTANT.ERROR.NO_SERVER;
                            break;
                        case 401:
                            $rootScope.errorMsg ="";
                            break;
                        case 404:
                            $rootScope.errorMsg = CONFIG.CONSTANT.ERROR.NO_ERROR;
                            $rootScope.status = "-1";
                            $rootScope.errorInterMsg = CONFIG.CONSTANT.ERROR.NO_ERROR;
                            break;
                        case 408:
                            $rootScope.errorMsg ="";
                            break;
                    }
                }
                return responseError;
            }
        };

        return httpInterceptor;
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('FileService', FileService);

    FileService.$inject = ['$rootScope','CONFIG', 'CommonService', 'Upload'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name FileService
     * @param {service} httpService 通用HTTP请求服务
     * @param {service} CONFIG 通用配置项
     * @description
     * 处理文件上传相关的服务
     */
    function FileService($rootScope, CONFIG, CommonService, Upload) {

        var service = {
            fileUpload: fileUpload                                      // 文件上传
        };

        return service;


        /**
         * 文件上传
        * @memberOf FileService
        // 使用
        <div class="form-group">
        <input type="file" ngf-select ng-model="vm.regForm.file"/>
       </div>
       <div class="progress progress-striped active">
           <div class="progress-bar progress-bar-success" role="progressbar"
              aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
              ng-style="progressPercentageStyle">
              <span ng-style="sr-only"><p ng-bind="progressPercentagesrOnly"></p></span>
           </div>
        </div>


         *
         */
        function fileUpload(file,params) {
            Upload.upload({
                //服务端接收
                url: 'http://10.20.38.92:7879/bros-consumer-login/UploadFile',
                //上传的同时带的参数
                data: params,
                file: file
            }).progress(function (evt) {
                //进度条
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                var temp = progressPercentage + '%' ;
                $rootScope.progressPercentageStyle = {"width":temp};
                $rootScope.progressPercentagesrOnly = temp+"完成";
                console.log('progess:' + progressPercentage + '%' + evt.config.file.name);
            }).success(function (data, status, headers, config) {
                 if (data.returnCode == CONFIG.CORRECT_CODE) {
                        CommonService.showError("文件上传成功");
                    } else {
                         CommonService.showError(data);
                    }
                //上传成功
                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            }).error(function (data, status, headers, config) {
                //上传失败
                console.log('error status: ' + status);
            });
        }
    }

})();

(function() {
    'use strict';

    angular.module('EBankProject')
    .factory('EventBusService', EventBusService);

    EventBusService.$inject = ['$rootScope', 'CircularListService', '$state'];

    function EventBusService($rootScope, CircularListService, $state) {
        var dataList = [];
        var eventMap = {}
        //_maxEvents = 10;
        //events = CircularListService.create(_maxEvents);


        var EventBusService = {
            publish: publish,
            subscribe: subscribe,
            unsubscribe:unsubscribe,
            set:set,
            get:get
            // getAll: getAll,
            // removeeventAll: removeeventAll,
        };

        return EventBusService;

        function set(key,value){
            dataList.push({
                state:$state.current.name,
                key:key,
                value:value
            });
        }

        function get(state,key){
            if(dataList.length>0){
                dataList.forEach(function(data){
                    if(data.state == state && data.key == key){
                        return data;
                    }
                });
            }
        }

        function publish(channel,topic,data) {
            var eventType = channel+'.'+topic;
            if (eventMap && eventMap[eventType]) {
                for (var i = 0; i < eventMap[eventType].length; i++) {
                    eventMap[eventType][i](data);
                }
            }
        }
        function unsubscribe(channel,topic, handler) {
            var eventType = channel+'.'+topic;
            for (var i = 0; i < eventMap[eventType].length; i++) {
                if (eventMap[eventType][i] === handler) {
                    eventMap[eventType].splice(i, 1);
                    break;
                }
            }
        }
        function subscribe(channel,topic, handler) {
            //multiple event listener
            var eventType = channel+'.'+topic;
            if (!eventMap[eventType]) {
                eventMap[eventType] = [];
            }
            eventMap[eventType].push(handler);
        }

        // 广播需要传递的消息
        // function publish(channel, topic, data) {
        //     events.add({
        //         channel: channel,
        //         topic: topic,
        //         data: data
        //     });
        // }

        // 接收广播消息
        // function subscribe(channel, topic, callback) {
        //     var data = {},sub;
        //     var subscriber = channel+'.'+topic;
        //     sub = subs[subscriber];
        //
        //     if(!sub && events.length() > 0){
        //         var eventArray = events.getAll();
        //         eventArray.forEach(function(event){
        //             if(event.channel == channel && event.topic == topic){
        //                 console.log('load from events');
        //                 callback({},event.data);
        //                 return false;
        //             }
        //         });
        //     }
        //     subs[subscriber] = sub;
        // }

        // 接收广播消息
        // function subscribe(subscriber, name, listener) {
        //     var sub;
        //     sub = subs[subscriber] || {};
        //     if (typeof sub[name] === 'function') {
        //         sub[name]();            // unbind the subscribe event !important
        //     }
        //     //sub[name] = $rootScope.$on(name, listener);  // bind the subscriber
        //     subs[subscriber] = sub;
        //     return subs;
        // }

        // function getAll(){
        //     return events.getAll();
        // }
        //
        // function removeeventAll() {
        //     return events.removeAll();
        // }
    }
})();

//

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('CommonService', CommonService);

    CommonService.$inject = ['$rootScope', 'CONFIG', 'httpService','ModalService', '$q', '$timeout', '$state'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name CommonService
     * @param {service} CONFIG     通用Http请求服务
     * @param {service} http       通用配置项
     * @description  系统公共服务
     */
    function CommonService($rootScope, CONFIG, http,ModalService, $q, $timeout, $state) {
        var service = {
            getValidatePic: getValidatePic,                 // 请求验证码图片
            getBankList: getBankList,                       // 请求银行列表
            getLocalBank: getLocalBank,                     // 获取本行信息
            getContactsList:getContactsList,                // 获取联系人
            getAnnouncementList: getAnnouncementList,       // 获得系统公告列表
            getAnnouncementDetail: getAnnouncementDetail,   // 获得系统公告详情
            getCitylist: getCitylist,                       // 获取开户省
            showError: showError,                           // 显示错误信息
            clearError: clearError,                         // 清除错误信息
            errorBlackHole: errorBlackHole,                 // 吞噬错误
            toLogin: toLogin,                               // 返回登录
            subShowMsg:subShowMsg                           //提示信息

        };

        return service;
        /**
         * @memberOf CommonService
         * @name subshowmsg
         * @description 提示信息
         */
        function subShowMsg(flag,error,backname) {//1-成功，2-失败，3-警告，4-提示；error,报错信息，不传，取返回msg值
            $rootScope.error = error;
            $rootScope.flag = flag;
            if (flag != null && flag != undefined) {
                if(backname != null && backname != undefined){
                    $rootScope.backname = backname;
                }
                var msgtipurl = "";
                if (flag == "1") {
                    msgtipurl = 'app/layout/showmsg/showmsgsuccess.html';
                }
                if (flag == "2") {
                    msgtipurl = 'app/layout/showmsg/showmsgerror.html';
                }
                if (flag == "3") {
                    msgtipurl = 'app/layout/showmsg/showmsgwarning.html';
                }
                if (flag == "4") {
                    msgtipurl = 'app/layout/showmsg/showmsgtips.html';
                }
                ModalService.showModal({
                    templateUrl: msgtipurl,
                    windowTemplateUrl: 'app/layout/showmsg/showmessage.html',
                    size: 'sm',
                    backdrop: 'static',
                    windowClass: 'showmsg'
                });
            }else{
                console.log(error);
                if(typeof(error) === 'string') {
                    $timeout(function() {
                        $rootScope.errorMsg = error;
                    });
                } else {
                    if (error) {
                        switch(error.returnCode) {
                            case '9801':
                                $rootScope.hasLogin = false;
                                sessionStorage.clear();
                                localStorage.clear();
                                event.preventDefault();
                                $state.go('login');
                                break;
                            default:
                                $rootScope.errorMsg =  error.returnMsg;
                                break;
                        }
                    } else {
                        $rootScope.errorMsg =  CONFIG.CONSTANT.ERROR.NO_DEAL;
                    }
                }
            }

        }
         /**
         * @memberOf CommonService
         * @name toLogin
         * @description 返回登录
         */
        function toLogin() {
            sessionStorage.clear();
            $state.go('login');
        }

        /**
         * @memberOf CommonService
         * @name getLocalBank
         * @description 获取本行银行
         */
        function getLocalBank(){
            var defer = $q.defer();
            if($rootScope['bankList']) {
                $rootScope['bankList'].forEach(function(bank){
                    if(bank.bankCode === CONFIG.BANKCODE){
                        defer.resolve(bank);
                    }
                });
            } else {
                getBankList().then(function(data) {
                    $rootScope['bankList'] = data.respData;
                    $rootScope['bankList'].forEach(function(bank){
                        if(bank.bankCode === CONFIG.BANKCODE){
                            defer.resolve(bank);
                        }
                    });
                    defer.resolve($rootScope['bankList']);
                }).catch(function(error) {
                    defer.reject({});
                });
            }
            return defer.promise;
        }

        /**
         * @memberOf CommonService
         * @name errorBlackHole
         * @description 吞噬不显示的错误消息
         */
        function errorBlackHole(error) {
            return true;
        }

        /**
         * @memberOf CommonService
         * @name clearError
         * @description rootScope清除errorMsg
         */
        function clearError() {
            $rootScope.errorMsg = '';
        }

        /**
         * @memberOf CommonService
         * @name showError
         * @description rootScope绑定错误信息
         */
        function showError(error) {
            console.log(error);
            if($rootScope.status == "-1"){
                $rootScope.errorMsg = $rootScope.errorInterMsg;
                return;
            }
            if(typeof(error) === 'string') {
                $timeout(function() {
                    $rootScope.errorMsg = error;
                });
            } else {
                if (error) {
                    switch(error.returnCode) {
                        case '9801':
                            $rootScope.hasLogin = false;
                            sessionStorage.clear();
                            localStorage.clear();
                            event.preventDefault();
                            $state.go('login');
                            break;
                        default:
                            $rootScope.errorMsg =  error.returnMsg;
                            break;
                    }
                } else {
                    $rootScope.errorMsg =  CONFIG.CONSTANT.ERROR.NO_DEAL;
                }
            }
        }
        /**
         * @memberOf CommonService
         * @name getValidatePic
         * @description 请求验证码图片
         * @return {url} 验证码地址
         */
        function getValidatePic() {
            return CONFIG.ROOT_URL + CONFIG.VALIDATE_PIC_URL + '?d=' + new Date().getTime();
        }

        /**
         * @memberOf CommonService
         * @name getBankList
         * @description 获取银行列表,修改银行logo地址
         * @return {object} 银行卡列表信息
         */
        function getBankList() {
            var defer = $q.defer();
            if ($rootScope['bankList']) {
                var data = {
                    respData: $rootScope.bankList
                };
                defer.resolve(data);
            } else {
                var promise = http.request(CONFIG.API.BANK_LIST);

                promise.then(function(data) {
                    for(var i = 0; i < data.respData.length; i++) {
                        data.respData[i]['bankLogoUrl'] = CONFIG.ROOT_URL + 'ifp_front/attachGet?path=/assets/images' + data.respData[i]['bankLogoUrl'];
                        if(parseInt(data.respData[i]['dayMaxAmt']) === 0) {
                            data.respData[i]['dayMaxAmt'] = '无限额';
                        }
                    }
                    defer.resolve(data);
                });
            }
            return defer.promise;
        }

        /**
         * @memberOf CommonService
         * @name getContactsList
         * @description 获取联系人
         * @return {object} 联系人信息
         */
        function getContactsList(params) {
            return http.request(CONFIG.API.CONTTACTS_LIST, params);

        }

        /**
         * @memberOf CommonService
         * @name getAnnouncementList
         * @description 获取公告列表
         * @return {object} 公告列表信息
         */
        function getAnnouncementList(params) {
            return http.request(CONFIG.API.GET_ANNOUNCEMENT_LIST, params);
        }

        function getAnnouncementDetail(params) {
            return http.request(CONFIG.API.GET_ANNOUNCEMENT_DETAIL, params);
        }

        function getCitylist() {
            var data = [{
                "p": "110000 北京市",
                "c": ["110000 北京市"]
            }, {
                "p": "310000 上海市",
                "c": ["310000 上海市"]
            }, {
                "p": "120000 天津市",
                "c": ["120000 天津市"]
            }, {
                "p": "500000 重庆市",
                "c": ["500000 重庆市"]
            }, {
                "p": "340000 安徽省",
                "c": ["340100 合肥市", "341100 滁州市", "340200 芜湖市", "341200 阜阳市", "340300 蚌埠市", "341300 宿州市", "340400 淮南市", "341400 巢湖市", "340500 马鞍山市", "341500 六安市", "340600 淮北市", "341600 亳州市", "340700 铜陵市", "341700 池州市", "340800 安庆市", "341800 宣城市", "341000 黄山市"]
            }, {
                "p": "350000 福建省",
                "c": ["350100 福州市", "350600 漳州市", "350200 厦门市", "350700 南平市", "350300 莆田市", "350800 龙岩市", "350400 三明市", "350900 宁德市", "350500 泉州市"]
            }, {
                "p": "440000 广东省",
                "c": ["440100 广州市", "441400 梅州市", "440200 韶关市", "441500 汕尾市", "440300 深圳市", "441600 河源市", "440400 珠海市", "441700 阳江市", "440500 汕头市", "441800 清远市", "440600 佛山市", "441900 东莞市", "440700 江门市", "442000 中山市", "440800 湛江市", "445100 潮州市", "440900 茂名市", "445200 揭阳市", "441200 肇庆市", "445300 云浮市", "441300 惠州市"]
            }, {
                "p": "450000 广西壮族自治区",
                "c": ["450100 南宁市", "450800 贵港市", "450200 柳州市", "450900 玉林市", "450300 桂林市", "451000 百色市", "450400 梧州市", "451100 贺州市", "450500 北海市", "451200 河池市", "450600 防城港市", "451300 来宾市", "450700 钦州市", "451400 崇左市"]
            }, {
                "p": "520000 贵州省",
                "c": ["520100 贵阳市", "522300 黔西南布依族苗族自治州", "520200 六盘水市", "522400 毕节地区", "520300 遵义市", "522600 黔东南苗族侗族自治州", "520400 安顺市", "522700 黔南布依族苗族自治州", "522200 铜仁地区"]
            }, {
                "p": "620000 甘肃省",
                "c": ["620100 兰州市", "620800 平凉市", "620200 嘉峪关市", "620900 酒泉市", "620300 金昌市", "621000 庆阳市", "620400 白银市", "621100 定西市", "620500 天水市", "622900 临夏回族自治州", "620600 武威市", "623000 甘南藏族自治州", "620700 张掖市", "621200 陇南市"]
            }, {
                "p": "430000 湖南省",
                "c": ["430100 长沙市", "430800 张家界市", "430200 株洲市", "430900 益阳市", "430300 湘潭市", "431000 郴州市", "430400 衡阳市", "431100 永州市", "430500 邵阳市", "431200 怀化市", "430600 岳阳市", "431300 娄底市", "430700 常德市", "433100 湘西土家族苗族自治州"]
            }, {
                "p": "420000 湖北省",
                "c": ["420100 武汉市", "420900 孝感市", "420200 黄石市", "421000 荆州市", "420300 十堰市", "421100 黄冈市", "420500 宜昌市", "421200 咸宁市", "420600 襄樊市", "421300 随州市", "420700 鄂州市", "422800 恩施土家族苗族自治州", "420800 荆门市"]
            }, {
                "p": "130000 河北省",
                "c": ["130100 石家庄市", "130700 张家口市", "130200 唐山市", "130800 承德市", "130300 秦皇岛市", "130900 沧州市", "130400 邯郸市", "131000 廊坊市", "130500 邢台市", "131100 衡水市", "130600 保定市"]
            }, {
                "p": "410000 河南省",
                "c": ["410100 郑州市", "411000 许昌市", "410200 开封市", "411100 漯河市", "410300 洛阳市", "411200 三门峡市", "410400 平顶山市", "411300 南阳市", "410500 安阳市", "411400 商丘市", "410600 鹤壁市", "411500 信阳市", "410700 新乡市", "411600 周口市", "410800 焦作市", "411700 驻马店市", "410900 濮阳市", "410881 济源市"]
            }, {
                "p": "230000 黑龙江省",
                "c": ["230100 哈尔滨市", "230800 佳木斯市", "230200 齐齐哈尔市", "230900 七台河市", "230300 鸡西市", "231000 牡丹江市", "230400 鹤岗市", "231100 黑河市", "230500 双鸭山市", "231200 绥化市", "230600 大庆市", "232700 大兴安岭地区", "230700 伊春市"]
            }, {
                "p": "460000 海南省",
                "c": ["460100 海口市", "460200 三亚市", "469001 五指山市", "469002 琼海市", "469003  儋州市", "469005   文昌市", "469006   万宁市", "469007   东方市", "469025   白沙黎族自治县", "469026 昌江黎族自治县", "469027 乐东黎族自治县", "469028   陵水黎族自治县", "469030 琼中黎族苗族自治县", "469031 西沙群岛", "469033 中沙群岛的岛礁及其海域", "469000 省直辖县级行政区划", "469021 定安县", "469022 屯昌县", "469023 澄迈县", "469024 临高县", "469029 保亭黎族苗族自治县", "469032 南沙群岛"]
            }, {
                "p": "320000 江苏省",
                "c": ["320100 南京市", "320800 淮安市", "320200 无锡市", "320900 盐城市", "320300 徐州市", "321000 扬州市", "320400 常州市", "321100 镇江市", "320500 苏州市", "321200 泰州市", "320600 南通市", "321300 宿迁市", "320700 连云港市"]
            }, {
                "p": "220000 吉林省",
                "c": ["220100 长春市", "220200 吉林市"]
            }, {
                "p": "360000 江西省",
                "c": ["360100 南昌市", "360700 赣州市", "360200 景德镇市", "360800 吉安市", "360300 萍乡市", "360900 宜春市", "360400 九江市", "361000 抚州市", "360500 新余市", "361100 上饶市", "360600 鹰潭市"]
            }, {
                "p": "210000 辽宁省",
                "c": ["210100 沈阳市", "210800 营口市", "210200 大连市", "210900 阜新市", "210300 鞍山市", "211000 辽阳市", "210400 抚顺市", "211100 盘锦市", "210500 本溪市", "211200 铁岭市", "210600 丹东市", "211300 朝阳市", "210700 锦州市", "211400 葫芦岛市"]
            }, {
                "p": "640000 宁夏回族自治区",
                "c": ["640100 银川市", "640400 固原市", "640200 石嘴山市", "640500 中卫市", "640300 吴忠市"]
            }, {
                "p": "150000 内蒙古自治区",
                "c": ["150100 呼和浩特市", "150700 呼伦贝尔市", "150200 包头市", "150800 巴彦淖尔市", "150300 乌海市", "150900 乌兰察布市", "150400 赤峰市", "152200 兴安盟", "150500 通辽市", "152500 锡林郭勒盟", "150600 鄂尔多斯市", "152900 阿拉善盟 210000"]
            }, {
                "p": "630000 青海省",
                "c": ["630100 西宁市", "632600 果洛藏族自治州", "632100 海东地区", "632700 玉树藏族自治州", "632200 海北藏族自治州", "632800 海西蒙古族藏族自治州", "632300 黄南藏族自治州", "632500 海南藏族自治州"]
            }, {
                "p": "370000 山东省",
                "c": ["370100 济南市", "371000 威海市", "370200 青岛市", "371100 日照市", "370300 淄博市", "371200 莱芜市", "370400 枣庄市", "371300 临沂市", "370500 东营市", "371400 德州市", "370600 烟台市", "371500 聊城市", "370700 潍坊市", "371600 滨州市", "370800 济宁市", "371700 荷泽市", "370900 泰安市"]
            }, {
                "p": "610000 陕西省",
                "c": ["610100 西安市", "610600 延安市", "610200 铜川市", "610700 汉中市", "610300 宝鸡市", "610800 榆林市", "610400 咸阳市", "610900 安康市", "610500 渭南市", "611000 商洛市"]
            }, {
                "p": "140000 山西省",
                "c": ["140100 太原市", "140700 晋中市", "140200 大同市", "140800 运城市", "140300 阳泉市", "140900 忻州市", "140400 长治市", "141000 临汾市", "140500 晋城市", "141100 吕梁市", "140600 朔州市"]
            }, {
                "p": "510000 四川省",
                "c": ["510100 成都市", "511400 眉山市", "510300 自贡市", "511500 宜宾市", "510400 攀枝花市", "511600 广安市", "510500 泸州市", "511700 达州市", "510600 德阳市", "511800 雅安市", "510700 绵阳市", "511900 巴中市", "510800 广元市", "512000 资阳市", "510900 遂宁市", "513200 阿坝藏族羌族自治州", "511000 内江市", "513300 甘孜藏族自治州", "511100 乐山市", "513400 凉山彝族自治州", "511300 南充市"]
            }, {
                "p": "650000 新疆维吾尔自治区",
                "c": ["650100 乌鲁木齐市", "653000 克孜勒苏柯尔克孜自治州", "650200 克拉玛依市", "653100 喀什地区", "652100 吐鲁番地区", "653200 和田地区", "652200 哈密地区", "654000 伊犁哈萨克自治州", "652300 昌吉回族自治州", "654200 塔城地区", "652700 博尔塔拉蒙古自治州", "654300 阿勒泰地区", "652800 巴音郭楞蒙古自治州", "659000 省直辖行政单位", "659001 石河子市", "659002 阿拉尔市", "659003 图木舒克市", "659004 五家渠市", "652900 阿克苏地区"]
            }, {
                "p": "540000 西藏自治区",
                "c": ["540100 拉萨市", "542400 那曲地区", "542100 昌都地区", "542500 阿里地区", "542200 山南地区", "542600 林芝地区", "542300 日喀则地区"]
            }, {
                "p": "530000 云南省",
                "c": ["530100 昆明市", "532300 楚雄彝族自治州", "530300 曲靖市", "532500 红河哈尼族彝族自治州", "530400 玉溪市", "532600 文山壮族苗族自治州", "530500 保山市", "532800 西双版纳傣族自治州", "530600 昭通市", "532900 大理白族自治州", "530700 丽江市", "533100 德宏傣族景颇族自治州", "530800 思茅市", "533300 怒江傈僳族自治州", "530900 临沧市", "533400 迪庆藏族自治州"]
            }, {
                "p": "330000 浙江省",
                "c": ["330100 杭州市", "330700 金华市", "330200 宁波市", "330800 衢州市", "330300 温州市", "330900 舟山市", "330400 嘉兴市", "331000 台州市", "330500 湖州市", "331100 丽水市", "330600 绍兴市"]
            }];

            var defer = $q.defer();
            defer.resolve(data);
            return defer.promise;
        }

    }
})();

(function() {
    'use strict';

    angular.module('EBankProject')
    .factory('CircularListService', CircularListService);

    CircularListService.$inject = [];

    function CircularListService() {
        var CircularListService = {
            create: create
        };

        function create(capacity) {
            var index, list;

            list = [];
            index = 0;
            return {
                add: function(obj) {
                    list[index] = obj;
                    index = (index + 1) % capacity;
                    return index;
                },
                getAll: function() {
                    var l, _ref, _ref1;
                    l = list.slice(index);
                    [].splice.apply(l, [(_ref = l.length), 9e9].concat(_ref1 = list.slice(0, index)));
                    return l;
                },
                removeAll: function() {
                    list = [];
                    index = 0;
                    return list;
                },
                length:function(){
                    return list.length;
                }
            };
        }

        return CircularListService;
    }
})();

var PBANKLOGINMENUINFO = {
    "rspHead": {
        "acctDate": "",
        "tranTime": "165414",
        "channelDate": "20160906",
        "langCode": "",
        "returnCode": "00000000",
        "tranDate": "20160906",
        "globalSeqNo": "000120160906222200000001174001",
        "returnMsg": "交易成功",
        "rsrvContent": "",
        "backendSysId": "2222",
        "backendSeqNo": "0001201609062222000000011740019001000000"
    },
    "rspBody": {
        "returnList": {
            "itemValue": null,
            "children": [
                {
                    "itemValue": {
                        "bmfProperties": "N",
                        "bmprMenucode": "",
                        "parentShelf": "",
                        "bmfNote1": "",
                        "bmfLevel": "1",
                        "bmfNote2": "",
                        "bmfChannel": "2222",
                        "bmfSeqno": 1,
                        "bmfName": "首页",
                        "bmfStt": "0",
                        "shelfDesc": "",
                        "bmfId": "302001001",
                        "bmfCode": "302001001",
                        "bmfUrl": "index",
                        "bmprPrdtypecode": "",
                        "shelfName": "",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "首页",
                        "bmprShelfcode": "",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": ""
                    },
                    "children": []
                },
                {
                    "itemValue": {
                        "bmf_note2": "",
                        "bmfProperties": "Y",
                        "bmprMenucode": "302001002",
                        "bmf_note1": "",
                        "parentShelf": "",
                        "bmfLevel": "1",
                        "bmfChannel": "2222",
                        "bmfSeqno": 2,
                        "bmfName": "账户管理",
                        "bmfStt": "0",
                        "shelfDesc": "账户管理描述",
                        "bmfId": "302001002",
                        "bmfCode": "302001002",
                        "bmfUrl": "",
                        "bmprPrdtypecode": "",
                        "shelfName": "账户",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "账户",
                        "bmprShelfcode": "SD01",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": "1"
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 8,
                                "bmfName": "我的账户",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "01306dd2-3c43-439a-9860-d25d40bec731",
                                "bmfCode": "302001049",
                                "bmfUrl": "accountManagement",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001002",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 9,
                                "bmfName": "账户设置",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "90337ac1-305d-44af-9fbb-ba4824fc51aa",
                                "bmfCode": "302001029",
                                "bmfUrl": "setAccount",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001002",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 10,
                                "bmfName": "交易明细",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "5335c09d-b149-4ead-9f9b-82c288254cb0",
                                "bmfCode": "302001030",
                                "bmfUrl": "accountDetail",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001002",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 11,
                                "bmfName": "账户挂失",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "b6a46b7e-fbeb-4bc2-9ee7-11a498a6a0fd",
                                "bmfCode": "302001031",
                                "bmfUrl": "suspendAccount",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001002",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmf_note2": "",
                        "bmfProperties": "Y",
                        "bmprMenucode": "302001003",
                        "bmf_note1": "",
                        "parentShelf": "",
                        "bmfLevel": "1",
                        "bmfChannel": "2222",
                        "bmfSeqno": 3,
                        "bmfName": "投资理财",
                        "bmfStt": "0",
                        "shelfDesc": "理财管理描述",
                        "bmfId": "302001003",
                        "bmfCode": "302001003",
                        "bmfUrl": "consumerFinancial",
                        "bmprPrdtypecode": "",
                        "shelfName": "理财",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "理财",
                        "bmprShelfcode": "SD02",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": "2"
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "我的理财产品",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "c2f0d313-aa9f-4671-aff0-e343cf7c8511",
                                "bmfCode": "302001057",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001003",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "交易明细查询",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "af5d38ce-3c61-48e7-bcd3-50ad7b1046ca",
                                "bmfCode": "302001058",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001003",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 3,
                                "bmfName": "理财产品购买",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "8abaf229-faec-4a2b-a0a2-45983a0ceea9",
                                "bmfCode": "302001059",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001003",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 4,
                                "bmfName": "理财产品撤单",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "d524b8b3-b0c1-4818-870e-3b94a330fc10",
                                "bmfCode": "302001060",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001003",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 5,
                                "bmfName": "风险承受能力评估",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "a488662a-56ad-4ebb-8c95-48bbf8589c01",
                                "bmfCode": "302001061",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "302001003",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmf_note2": "",
                        "bmfProperties": "Y",
                        "bmprMenucode": "302001004",
                        "bmf_note1": "",
                        "parentShelf": "",
                        "bmfLevel": "1",
                        "bmfChannel": "2222",
                        "bmfSeqno": 4,
                        "bmfName": "贷款产品",
                        "bmfStt": "0",
                        "shelfDesc": "贷款产品管理描述",
                        "bmfId": "302001004",
                        "bmfCode": "302001004",
                        "bmfUrl": "loanService",
                        "bmprPrdtypecode": "",
                        "shelfName": "贷款产品",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "贷款产品",
                        "bmprShelfcode": "SD07",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": "7"
                    },
                    "children": []
                },
                {
                    "itemValue": {
                        "bmf_note2": "",
                        "bmfProperties": "Y",
                        "bmprMenucode": "302001005",
                        "bmf_note1": "",
                        "parentShelf": "",
                        "bmfLevel": "1",
                        "bmfChannel": "2222",
                        "bmfSeqno": 5,
                        "bmfName": "定活宝",
                        "bmfStt": "0",
                        "shelfDesc": "定活宝管理描述",
                        "bmfId": "302001005",
                        "bmfCode": "302001005",
                        "bmfUrl": "transferService",
                        "bmprPrdtypecode": "",
                        "shelfName": "定活宝",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "定活宝",
                        "bmprShelfcode": "SD03",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": "3"
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmf_note2": "",
                                "bmfProperties": "Y",
                                "bmprMenucode": "302001025",
                                "bmf_note1": "",
                                "parentShelf": "SD03",
                                "bmfLevel": "2",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "定期产品分类",
                                "bmfStt": "0",
                                "shelfDesc": "定期存款分类",
                                "bmfId": "302001025",
                                "bmfCode": "302001025",
                                "bmfUrl": "transferService",
                                "bmprPrdtypecode": "",
                                "shelfName": "定期存款分类2",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "定期产品分类",
                                "bmprShelfcode": "SD0301",
                                "bmfParentid": "302001005",
                                "bmprGoodscode": "",
                                "bmprId": "21"
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmf_note2": "",
                                "bmfProperties": "Y",
                                "bmprMenucode": "302001026",
                                "bmf_note1": "",
                                "parentShelf": "SD03",
                                "bmfLevel": "2",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "活期产品分类",
                                "bmfStt": "0",
                                "shelfDesc": "活期存款分类12",
                                "bmfId": "302001026",
                                "bmfCode": "302001026",
                                "bmfUrl": "transferService",
                                "bmprPrdtypecode": "",
                                "shelfName": "活期存款分类12",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "活期产品分类",
                                "bmprShelfcode": "SD0302",
                                "bmfParentid": "302001005",
                                "bmprGoodscode": "",
                                "bmprId": "22"
                            },
                            "children": []
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmf_note2": "",
                        "bmfProperties": "Y",
                        "bmprMenucode": "302001006",
                        "bmf_note1": "",
                        "parentShelf": "",
                        "bmfLevel": "1",
                        "bmfChannel": "2222",
                        "bmfSeqno": 6,
                        "bmfName": "存款产品",
                        "bmfStt": "0",
                        "shelfDesc": "存款产品管理描述",
                        "bmfId": "302001006",
                        "bmfCode": "302001006",
                        "bmfUrl": "localhost:8080/账户",
                        "bmprPrdtypecode": "",
                        "shelfName": "存款产品",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "存款产品",
                        "bmprShelfcode": "SD04",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": "4"
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmf_note2": "",
                                "bmfProperties": "Y",
                                "bmprMenucode": "302001007",
                                "bmf_note1": "",
                                "parentShelf": "SD04",
                                "bmfLevel": "1",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "存款分类",
                                "bmfStt": "0",
                                "shelfDesc": "存款分类描述",
                                "bmfId": "302001007",
                                "bmfCode": "302001007",
                                "bmfUrl": "localhost:8080/账户",
                                "bmprPrdtypecode": "",
                                "shelfName": "存款分类",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "存款分类",
                                "bmprShelfcode": "SD0401",
                                "bmfParentid": "302001006",
                                "bmprGoodscode": "",
                                "bmprId": "5"
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmf_note2": "",
                                "bmfProperties": "Y",
                                "bmprMenucode": "302001008",
                                "bmf_note1": "",
                                "parentShelf": "SD04",
                                "bmfLevel": "2",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "借记卡分类",
                                "bmfStt": "0",
                                "shelfDesc": "借记卡分类描述",
                                "bmfId": "302001008",
                                "bmfCode": "302001008",
                                "bmfUrl": "localhost:8080/账户",
                                "bmprPrdtypecode": "",
                                "shelfName": "借记卡分类",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "借记卡分类",
                                "bmprShelfcode": "SD0402",
                                "bmfParentid": "302001006",
                                "bmprGoodscode": "",
                                "bmprId": "6"
                            },
                            "children": []
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmfProperties": "N",
                        "bmprMenucode": "",
                        "parentShelf": "",
                        "bmfNote1": "",
                        "bmfLevel": "1",
                        "bmfNote2": "",
                        "bmfChannel": "2222",
                        "bmfSeqno": 7,
                        "bmfName": "转账汇款",
                        "bmfStt": "0",
                        "shelfDesc": "",
                        "bmfId": "d12bf60e-a7a4-4d8e-9996-9cf5f654a161",
                        "bmfCode": "302001032",
                        "bmfUrl": "",
                        "bmprPrdtypecode": "",
                        "shelfName": "",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "",
                        "bmprShelfcode": "",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": ""
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "行内转账",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "2c01e8a4-f484-421a-8d99-23b0e61fb43b",
                                "bmfCode": "302001050",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "d12bf60e-a7a4-4d8e-9996-9cf5f654a161",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": [
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 1,
                                        "bmfName": "本行汇款",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "8b043a43-cd2c-46ae-aba4-40cd0a52a1a7",
                                        "bmfCode": "302001051",
                                        "bmfUrl": "bankTransfer",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "2c01e8a4-f484-421a-8d99-23b0e61fb43b",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 2,
                                        "bmfName": "同名转账",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "0d119dec-2bcd-4df4-9f76-9a60400c4468",
                                        "bmfCode": "302001052",
                                        "bmfUrl": "sameNameTransfer",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "2c01e8a4-f484-421a-8d99-23b0e61fb43b",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 3,
                                        "bmfName": "行内转账历史",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "66b44cd5-0104-481a-887f-e57c7ce934bc",
                                        "bmfCode": "302001053",
                                        "bmfUrl": "bankTransferDetail",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "2c01e8a4-f484-421a-8d99-23b0e61fb43b",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                }
                            ]
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "跨行汇款",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "0a509f91-1e7f-4ffb-8fcd-c4fe8eaf7573",
                                "bmfCode": "302001054",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "d12bf60e-a7a4-4d8e-9996-9cf5f654a161",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": [
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 1,
                                        "bmfName": "跨行汇款",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "210d83ee-7840-401a-980a-2f0eb9a94280",
                                        "bmfCode": "302001055",
                                        "bmfUrl": "interBankTransfer",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "0a509f91-1e7f-4ffb-8fcd-c4fe8eaf7573",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 2,
                                        "bmfName": "跨行转账历史",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "f7cf46ad-7819-463e-a850-8bcac48dad83",
                                        "bmfCode": "302001056",
                                        "bmfUrl": "interBankTransferDetail",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "0a509f91-1e7f-4ffb-8fcd-c4fe8eaf7573",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                }
                            ]
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 3,
                                "bmfName": "收款人维护",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "49472e71-a92e-4431-b7c2-fa4bac9ad287",
                                "bmfCode": "302001034",
                                "bmfUrl": "recvPersonInfo",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "d12bf60e-a7a4-4d8e-9996-9cf5f654a161",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 4,
                                "bmfName": "回单打印",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "a5efa36b-9c9d-48fc-bb5e-18d104c31a88",
                                "bmfCode": "302001033",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "d12bf60e-a7a4-4d8e-9996-9cf5f654a161",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": [
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 1,
                                        "bmfName": "电子回单打印",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "19e3db13-3e4c-478e-9c8e-7e44739b6267",
                                        "bmfCode": "302001036",
                                        "bmfUrl": "elecReceiptPrint",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "a5efa36b-9c9d-48fc-bb5e-18d104c31a88",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 2,
                                        "bmfName": "电子回单验证",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "f6582be5-7d24-4714-b8d8-bdd09dabfbc7",
                                        "bmfCode": "302001037",
                                        "bmfUrl": "elecReceiptCheck",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "a5efa36b-9c9d-48fc-bb5e-18d104c31a88",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                }
                            ]
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 5,
                                "bmfName": "落地审批查询",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "252c928a-f349-4b0b-a91a-53ce81dc054e",
                                "bmfCode": "302001035",
                                "bmfUrl": "floorApproval",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "d12bf60e-a7a4-4d8e-9996-9cf5f654a161",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmfProperties": "N",
                        "bmprMenucode": "",
                        "parentShelf": "",
                        "bmfNote1": "",
                        "bmfLevel": "1",
                        "bmfNote2": "",
                        "bmfChannel": "2222",
                        "bmfSeqno": 8,
                        "bmfName": "网银互联",
                        "bmfStt": "0",
                        "shelfDesc": "",
                        "bmfId": "7dc83e63-5bfc-4b99-ad45-7e00baf23cca",
                        "bmfCode": "302001038",
                        "bmfUrl": "",
                        "bmprPrdtypecode": "",
                        "shelfName": "",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "",
                        "bmprShelfcode": "",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": ""
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "网银互联管理",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "753b5470-d9a4-4b87-87e2-1471a39dc9cc",
                                "bmfCode": "302001039",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "7dc83e63-5bfc-4b99-ad45-7e00baf23cca",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": [
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 1,
                                        "bmfName": "签约他行账户",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "2cda315e-b4e8-4ea8-9b72-b9d79d03b7c4",
                                        "bmfCode": "302001040",
                                        "bmfUrl": "signedHeDid",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "753b5470-d9a4-4b87-87e2-1471a39dc9cc",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 2,
                                        "bmfName": "签约协议管理",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "380d9a21-db27-484c-b793-ce2ce6a63469",
                                        "bmfCode": "302001041",
                                        "bmfUrl": "contractManage",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "753b5470-d9a4-4b87-87e2-1471a39dc9cc",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 3,
                                        "bmfName": "他行账户管理",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "27d6a9af-e375-4685-a39f-fd721f4a3dab",
                                        "bmfCode": "302001042",
                                        "bmfUrl": "heDidmanage",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "753b5470-d9a4-4b87-87e2-1471a39dc9cc",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                }
                            ]
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "账户资金归集",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "a3e71ee9-9dde-434f-be29-dddd65eedbf2",
                                "bmfCode": "302001043",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "7dc83e63-5bfc-4b99-ad45-7e00baf23cca",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": [
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 1,
                                        "bmfName": "资金归集设置",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "859943a5-0a05-479a-9cf2-8e342f942d8a",
                                        "bmfCode": "302001044",
                                        "bmfUrl": "setFundCollection",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "a3e71ee9-9dde-434f-be29-dddd65eedbf2",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 2,
                                        "bmfName": "跨行汇入",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "c5013feb-5830-459c-a960-7e7920b481f9",
                                        "bmfCode": "302001045",
                                        "bmfUrl": "interBank",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "a3e71ee9-9dde-434f-be29-dddd65eedbf2",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 3,
                                        "bmfName": "资金归集管理",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "2d31f46d-61f1-4c57-82b9-eca52ffec7e2",
                                        "bmfCode": "302001046",
                                        "bmfUrl": "funcollmanag",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "a3e71ee9-9dde-434f-be29-dddd65eedbf2",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                }
                            ]
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmfProperties": "N",
                        "bmprMenucode": "",
                        "parentShelf": "",
                        "bmfNote1": "",
                        "bmfLevel": "1",
                        "bmfNote2": "",
                        "bmfChannel": "2222",
                        "bmfSeqno": 9,
                        "bmfName": "个人中心",
                        "bmfStt": "0",
                        "shelfDesc": "",
                        "bmfId": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                        "bmfCode": "302001047",
                        "bmfUrl": "",
                        "bmprPrdtypecode": "",
                        "shelfName": "",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "",
                        "bmprShelfcode": "",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": ""
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "个人限额维护",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "6dac0fe5-e7a2-4e6b-8a04-ed47fb6d185d",
                                "bmfCode": "302001048",
                                "bmfUrl": "personallimit",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "操作日志查询",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "47a182c4-b1a5-459e-a038-c917f5bf4890",
                                "bmfCode": "302001079",
                                "bmfUrl": "operationquery",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 3,
                                "bmfName": "客户昵称设置",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "6b30d66c-da4d-4527-a353-6c2411d0560f",
                                "bmfCode": "302001080",
                                "bmfUrl": "customernickname",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 4,
                                "bmfName": "预留信息设置",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "d8c4be11-325d-4efe-b45f-4f8ce4032f27",
                                "bmfCode": "302001081",
                                "bmfUrl": "reservedinformation",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 5,
                                "bmfName": "修改登录密码",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "b894d5c1-7787-4a4a-81e7-69d1f7540c49",
                                "bmfCode": "302001082",
                                "bmfUrl": "modifypassword",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 6,
                                "bmfName": "快捷菜单设置",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "f8de8c1c-a5c9-4e3a-aacd-f496fb424f56",
                                "bmfCode": "302001083",
                                "bmfUrl": "shortcutmenusettings",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 7,
                                "bmfName": "电子渠道管理",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "f612a391-b496-44c9-96f0-6965ce753ccc",
                                "bmfCode": "302001084",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "9f02cbfc-96e7-4095-9c3f-b27f71b0414a",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmfProperties": "N",
                        "bmprMenucode": "",
                        "parentShelf": "",
                        "bmfNote1": "",
                        "bmfLevel": "1",
                        "bmfNote2": "",
                        "bmfChannel": "2222",
                        "bmfSeqno": 10,
                        "bmfName": "储蓄服务",
                        "bmfStt": "0",
                        "shelfDesc": "",
                        "bmfId": "445c42c5-8e72-4b7c-aeb7-7f3fd32aa129",
                        "bmfCode": "302001062",
                        "bmfUrl": "",
                        "bmprPrdtypecode": "",
                        "shelfName": "",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "",
                        "bmprShelfcode": "",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": ""
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "定活互转",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "77f16ea8-323c-4569-9288-0cabed50a89e",
                                "bmfCode": "302001065",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "445c42c5-8e72-4b7c-aeb7-7f3fd32aa129",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": [
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 1,
                                        "bmfName": "定期转活期",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "221a395a-2e47-44a5-8c4c-56c7f86aa20c",
                                        "bmfCode": "302001067",
                                        "bmfUrl": "timeToCurrentDeposit",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "77f16ea8-323c-4569-9288-0cabed50a89e",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 2,
                                        "bmfName": "活期转定期",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "fb8cf310-0535-4f7e-a118-42e6ba71cbe6",
                                        "bmfCode": "302001068",
                                        "bmfUrl": "currentToTimeDeposit",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "77f16ea8-323c-4569-9288-0cabed50a89e",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                }
                            ]
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "通知存款",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "789206f1-6db5-4dce-97c9-e9263addf209",
                                "bmfCode": "302001066",
                                "bmfUrl": "",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "445c42c5-8e72-4b7c-aeb7-7f3fd32aa129",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": [
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 1,
                                        "bmfName": "我的通知存款",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "81dcd1b4-4530-46f9-8883-48f6f67cf70f",
                                        "bmfCode": "302001069",
                                        "bmfUrl": "myNoticeDeposit",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "789206f1-6db5-4dce-97c9-e9263addf209",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                },
                                {
                                    "itemValue": {
                                        "bmfProperties": "N",
                                        "bmprMenucode": "",
                                        "parentShelf": "",
                                        "bmfNote1": "",
                                        "bmfLevel": "3",
                                        "bmfNote2": "",
                                        "bmfChannel": "2222",
                                        "bmfSeqno": 2,
                                        "bmfName": "存入通知存款",
                                        "bmfStt": "0",
                                        "shelfDesc": "",
                                        "bmfId": "e2a07528-5f38-48b9-9962-b754e538e828",
                                        "bmfCode": "302001070",
                                        "bmfUrl": "noticeDeposit",
                                        "bmprPrdtypecode": "",
                                        "shelfName": "",
                                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                        "bmfDesc": "",
                                        "bmprShelfcode": "",
                                        "bmfParentid": "789206f1-6db5-4dce-97c9-e9263addf209",
                                        "bmprGoodscode": "",
                                        "bmprId": ""
                                    },
                                    "children": []
                                }
                            ]
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmfProperties": "N",
                        "bmprMenucode": "",
                        "parentShelf": "",
                        "bmfNote1": "",
                        "bmfLevel": "1",
                        "bmfNote2": "",
                        "bmfChannel": "2222",
                        "bmfSeqno": 11,
                        "bmfName": "安全管理",
                        "bmfStt": "0",
                        "shelfDesc": "",
                        "bmfId": "7c0ca5dc-9782-493b-987d-18e07a916ec5",
                        "bmfCode": "302001063",
                        "bmfUrl": "",
                        "bmprPrdtypecode": "",
                        "shelfName": "",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "",
                        "bmprShelfcode": "",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": ""
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "证书查询",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "f7a2bee7-5d0d-4374-a11e-3eb7f3b806a6",
                                "bmfCode": "302001071",
                                "bmfUrl": "credentialsquery",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "7c0ca5dc-9782-493b-987d-18e07a916ec5",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "证书更新",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "fbe94381-8e4d-40d3-849a-80c207f39889",
                                "bmfCode": "302001072",
                                "bmfUrl": "credentialsupdate",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "7c0ca5dc-9782-493b-987d-18e07a916ec5",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 3,
                                "bmfName": "令牌动态口令同步",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "a9d63fb8-ed50-40a6-9bb0-969e80693333",
                                "bmfCode": "302001073",
                                "bmfUrl": "dynamictoken",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "7c0ca5dc-9782-493b-987d-18e07a916ec5",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        }
                    ]
                },
                {
                    "itemValue": {
                        "bmfProperties": "N",
                        "bmprMenucode": "",
                        "parentShelf": "",
                        "bmfNote1": "",
                        "bmfLevel": "1",
                        "bmfNote2": "",
                        "bmfChannel": "2222",
                        "bmfSeqno": 12,
                        "bmfName": "客户服务",
                        "bmfStt": "0",
                        "shelfDesc": "",
                        "bmfId": "aa168fbf-432b-4186-afb1-918f3b213e62",
                        "bmfCode": "302001064",
                        "bmfUrl": "",
                        "bmprPrdtypecode": "",
                        "shelfName": "",
                        "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                        "bmfDesc": "",
                        "bmprShelfcode": "",
                        "bmfParentid": "",
                        "bmprGoodscode": "",
                        "bmprId": ""
                    },
                    "children": [
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 1,
                                "bmfName": "客户之声",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "76c8b6be-a4bd-47c8-b1d4-37c839ad1e3d",
                                "bmfCode": "302001074",
                                "bmfUrl": "customervoice",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "aa168fbf-432b-4186-afb1-918f3b213e62",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 2,
                                "bmfName": "银行客服",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "45193932-80ec-4f9f-aaf0-01f9875cccb4",
                                "bmfCode": "302001075",
                                "bmfUrl": "bankcustomer",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "aa168fbf-432b-4186-afb1-918f3b213e62",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 3,
                                "bmfName": "安全提示",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "4071f074-dd36-4c04-ad50-bfa0f73dc8f5",
                                "bmfCode": "302001076",
                                "bmfUrl": "safetytips",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "aa168fbf-432b-4186-afb1-918f3b213e62",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 4,
                                "bmfName": "银行公告",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "d09d8ae8-7cdb-428f-bad1-4381e2013178",
                                "bmfCode": "302001077",
                                "bmfUrl": "bankannouncement",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "aa168fbf-432b-4186-afb1-918f3b213e62",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        },
                        {
                            "itemValue": {
                                "bmfProperties": "N",
                                "bmprMenucode": "",
                                "parentShelf": "",
                                "bmfNote1": "",
                                "bmfLevel": "2",
                                "bmfNote2": "",
                                "bmfChannel": "2222",
                                "bmfSeqno": 5,
                                "bmfName": "短信通知管理",
                                "bmfStt": "0",
                                "shelfDesc": "",
                                "bmfId": "93ddec3e-559a-4a11-852d-2007a9ba5989",
                                "bmfCode": "302001078",
                                "bmfUrl": "smsnotification",
                                "bmprPrdtypecode": "",
                                "shelfName": "",
                                "bmfLegal": "80fb68c1-fce5-440d-85a3-9c392ba1ba83",
                                "bmfDesc": "",
                                "bmprShelfcode": "",
                                "bmfParentid": "aa168fbf-432b-4186-afb1-918f3b213e62",
                                "bmprGoodscode": "",
                                "bmprId": ""
                            },
                            "children": []
                        }
                    ]
                }
            ]
        }
    }
};
var QUERYLOANDETAIL = {
"rspHead":{
        "channelDate":"99999999",
        "tranTime":"888888",
        "acctDate":"",
        "returnCode":"00000000",
        "langCode":"",
        "tranDate":"77777888",
        "returnMsg":"交易成功",
        "globalSeqNo":"000120160716111111110000009401",
        "rsrvContent":"",
        "backendSysId":"6666",
        "backendSeqNo":"0001201607161111111100000094019001000000"
    },
    'pageInfo': {
        'totalCount': '5'
    },
    'loannum':'123456789012345678',
    'loankind':'住房贷款',
    'loanamount':'70000',
    'loanblance':'30000',
    'loanstart':'20100101',
    'loanend':'20160101',
    'nowrate':'0.433321',
    'defaultrate':'0.640031',
    'loanbank':'中国银行',
    'paymentway':'等额本金',
    'paymentcyc':'月',
    'paymentday':'28',
    'peymentacount':'6225 8888 8888 8888',
    'loantype':'正常',
    'guaranteeway1':'抵押',
    'guaranteething1':'房产',
    'guaranteeway2':'质押',
    'guaranteething2':'本票',
    'respData': [{
        'paydate': '20150101',
        'payamt':'100',
        'payamo':'100',
        'payrate':'10',
        'allloanamount':'70000'
    },{
        'paydate': '20150202',
        'payamt':'200',
        'payamo':'200',
        'payrate':'20',
        'allloanamount':'60000'
    },{
        'paydate': '20150303',
        'payamt':'300',
        'payamo':'300',
        'payrate':'30',
        'allloanamount':'50000'
    },{
        'paydate': '20150404',
        'payamt':'400',
        'payamo':'400',
        'payrate':'40',
        'allloanamount':'40000'
    },{
       'paydate': '20150505',
        'payamt':'500',
        'payamo':'500',
        'payrate':'50',
        'allloanamount':'50000'
    }]
};

var BROWSEHIS = {
"rspHead":{
        "channelDate":"99999999",
        "tranTime":"888888",
        "acctDate":"",
        "returnCode":"00000000",
        "langCode":"",
        "tranDate":"77777888",
        "returnMsg":"交易成功",
        "globalSeqNo":"000120160716111111110000009401",
        "rsrvContent":"",
        "backendSysId":"6666",
        "backendSeqNo":"0001201607161111111100000094019001000000"
    },
    'respData': [{
        'productCode':'B0060',
        'productname': '60天理财债券型投资基金',
        'producttags':[
            {'producttag':'低风险'},
            {'producttag':'申赎免费'},
            {'producttag':'收益稳健'}
            ],
        'producturl':'../assets/img/layout/img03.jpg',
        'productrate':'24.16',
        'producttype':'低',
        'productdesc':'巴拉巴拉',
        'productdetitle':[{
            'smatitle':'风险收益特征',
            'productdeta':'1本基金为混合型基金，其预期风险、预期收益高于货币市场基金和债券型基金，低于股票型基金。 投资目标 通过深入研究，积极投资于我国长期经济结构转型过程中符合经济发展方向、增长前景确定且估值水平合理的优质企业，分享经济转型发展带来的高成长和高收益，在控制风险的前提下，力争实现基金资产的长期稳健增值。'
        },{
            'smatitle':'投资范围',
            'productdeta':'1本基金的投资对象是具有良好流动性的金融工具，包括国内依法发行上市的股票（包括中小板、创业板及其他经中国证监会核准上市的股票）、权证、股指期货等权益类金融工具，债券等固定收益类金融工具（包括国债、央行票据、金融债、企业债、公司债、次级债、中小企业私募债、地方政府债券、中期票据、可转换债券（含分离交易可转债）、短期融资券、资产支持证券、债券回购、银行存款等）及法律法规或中国证监会允许基金投资的其他金融工具（但须符合中国证监会的相关规定）。如法律法规或监管机构以后允许基金投资其他品种，基金管理人在履行适当程序后，可以将其纳入投资范围。'
        }]
    },{'productCode':'B0060',
        'productname': '61天理财债券型投资基金',
        'producttags':[
            {'producttag':'低风险'},
            {'producttag':'申赎免费'},
            {'producttag':'收益稳健'}
            ],
        'producturl':'../assets/img/layout/img03.jpg',
        'productrate':'24.16',
        'producttype':'低',
        'productdesc':'巴拉巴拉',
        'productdetitle':[{
            'smatitle':'风险收益特征',
            'productdeta':'2本基金为混合型基金，其预期风险、预期收益高于货币市场基金和债券型基金，低于股票型基金。 投资目标 通过深入研究，积极投资于我国长期经济结构转型过程中符合经济发展方向、增长前景确定且估值水平合理的优质企业，分享经济转型发展带来的高成长和高收益，在控制风险的前提下，力争实现基金资产的长期稳健增值。'
        },{
            'smatitle':'投资范围',
            'productdeta':'2本基金的投资对象是具有良好流动性的金融工具，包括国内依法发行上市的股票（包括中小板、创业板及其他经中国证监会核准上市的股票）、权证、股指期货等权益类金融工具，债券等固定收益类金融工具（包括国债、央行票据、金融债、企业债、公司债、次级债、中小企业私募债、地方政府债券、中期票据、可转换债券（含分离交易可转债）、短期融资券、资产支持证券、债券回购、银行存款等）及法律法规或中国证监会允许基金投资的其他金融工具（但须符合中国证监会的相关规定）。如法律法规或监管机构以后允许基金投资其他品种，基金管理人在履行适当程序后，可以将其纳入投资范围。'
        }]
    },{'productCode':'B0060',
        'productname': '62天理财债券型投资基金',
        'producttags':[
            {'producttag':'低风险'},
            {'producttag':'申赎免费'},
            {'producttag':'收益稳健'}
            ],
        'producturl':'../assets/img/layout/img03.jpg',
        'productrate':'24.16',
        'producttype':'低',
        'productdesc':'巴拉巴拉',
        'productdetitle':[{
            'smatitle':'风险收益特征',
            'productdeta':'3本基金为混合型基金，其预期风险、预期收益高于货币市场基金和债券型基金，低于股票型基金。 投资目标 通过深入研究，积极投资于我国长期经济结构转型过程中符合经济发展方向、增长前景确定且估值水平合理的优质企业，分享经济转型发展带来的高成长和高收益，在控制风险的前提下，力争实现基金资产的长期稳健增值。'
        },{
            'smatitle':'投资范围',
            'productdeta':'3本基金的投资对象是具有良好流动性的金融工具，包括国内依法发行上市的股票（包括中小板、创业板及其他经中国证监会核准上市的股票）、权证、股指期货等权益类金融工具，债券等固定收益类金融工具（包括国债、央行票据、金融债、企业债、公司债、次级债、中小企业私募债、地方政府债券、中期票据、可转换债券（含分离交易可转债）、短期融资券、资产支持证券、债券回购、银行存款等）及法律法规或中国证监会允许基金投资的其他金融工具（但须符合中国证监会的相关规定）。如法律法规或监管机构以后允许基金投资其他品种，基金管理人在履行适当程序后，可以将其纳入投资范围。'
        }]
    }]
};

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('separator', separator);

    function separator() {
        return function(str, sepObj) {
            str = str || '';
            var newStr = '';
            if(str.indexOf('****') != -1) {
                newStr = str;
            } else {
                for (var i = 0; i < str.length; i++) {
                    if (i !== 0 && i % sepObj.num === 0) {
                        newStr += sepObj.symbol;
                    }
                    newStr += str[i];
                }
            }

            return newStr;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('relationShipType', relationShipType);
    /**
    *   成员关系/社会关系
    */
    function relationShipType() {
        return function(relationShipType) {
            var result = relationShipType;
             switch (relationShipType) {
                    case '11': //配偶
                        result = '配偶';
                        break;
                    case '12': //子女
                        result = '子女';
                        break;
                    case '13': //自己父亲
                        result = '自己父亲';
                        break;
                    case '14': //自己母亲
                        result = '自己母亲';
                        break;
                    case '15': //配偶父亲
                        result = '配偶父亲';
                        break;
                    case '16': //配偶母亲
                        result = '配偶母亲';
                        break;
                    case '17': //祖父母
                        result = '祖父母';
                        break;
                    case '18': //外祖父母
                        result = '外祖父母';
                    case '19': //兄弟姐妹
                        result = '兄弟姐妹';
                        break;
                    case '1X': //其他关系
                        result = '其他关系';
                        break;
                    case '1a': //孙子女
                        result = '孙子女';
                        break;
                    case '21': //委托的代理人/代办人
                        result = '委托的代理人/代办人';
                        break;
                    case '22': //上司
                        result = '上司';
                        break;
                    case '23': //下属
                        result = '下属';
                        break;
                    case '24': //同事
                        result = '同事';
                        break;
                    case '25': //情侣关系
                        result = '情侣关系';
                        break;
                    case '26': //其他工作上的合作关系
                        result = '其他工作上的合作关系';
                        break;
                    case '27': //普通朋友
                        result = '普通朋友';
                        break;
                    case '28': //至交好友
                        result = '至交好友';
                        break;
                    case '29': //关系恶劣的联系人
                        result = '关系恶劣的联系人';
                        break;
                    case '2X': //其他关系
                        result = '其他关系';
                        break;
                }

            return result;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('profession', profession);
    /**
    *   职业
    */
    function profession() {
        return function(profession) {
            var result = profession;
             switch (profession) {
                    case '1': //一般工商业
                        result = '一般工商业';
                        break;
                    case '2': //电脑/咨询/网络
                        result = '电脑/咨询/网络';
                        break;
                    case '3': //广告/行销/娱乐
                        result = '广告/行销/娱乐';
                        break;
                    case '4': //医疗/法律
                        result = '医疗/法律';
                        break;
                    case '5': //财经/保险/不动产
                        result = '财经/保险/不动产';
                        break;
                    case '6': //学生
                        result = '学生';
                        break;
                    case '7': //科教/文体
                        result = '科教/文体';
                        break;
                    case '8': //非营利事业/政府
                        result = '非营利事业/政府';
                        break;
                    case '9': //其他
                        result = '其他';
                        break;
                }

            return result;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('paymentMethodTypeId', paymentMethodTypeId);

    function paymentMethodTypeId() {
        return function(paymentMethodTypeId) {
            var result = '';
            switch (paymentMethodTypeId) {
                case 'OUR_DEBIT_CARD': //本行借记卡
                    result = '本行借记卡';
                    break;
                case 'OTHER_DEBIT_CARD': //他行借记卡
                    result = '他行借记卡';
                    break;
                case 'PUBLIC_CURRENT_ACCOUNT': //对公活期户
                    result = '对公活期户';
                    break;
                case 'PRIVATE_CURRENT_ACCOUNT': //对私活期户
                    result = '对私活期户';
                    break;
                case 'CAPAY': //现金
                    result = '现金';
                    break;
            }
            return result;

        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('loanStatus', loanStatus);
    /**
    *   性别
    */
    function loanStatus() {
        return function(loanStatus) {
            var result = loanStatus;
             switch (loanStatus) {
                    case '0': //正常
                        result = '正常';
                        break;
                    case '1': //逾期
                        result = '逾期';
                        break;
                    case '2': //呆滞
                        result = '呆滞';
                        break;
                    case '3': //呆账
                        result = '呆账';
                        break;
                }

            return result;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('intercept', intercept);
    /**
    *   性别
    */
    function intercept() {
        return function(input) {
            var inputFile = input;
            var reg = /\[(.*?)\]/gi;
            var tmp = inputFile.match(reg);
            if (tmp) {
                for (var i = 0; i < tmp.length; i++) {
                    // alert(tmp[i]); // 保留中括号
                    // alert(tmp[i].replace(reg, "$1")); // 不保留中括号
                    inputFile = tmp[i].replace(reg, "$1");
                }
            }
            return inputFile;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('idCardType', idCardType);
    /**
    *   证件类型
    */
    function idCardType() {
        return function(idCardType) {
            var result = idCardType;
             switch (idCardType) {
                    case '10': //身份证
                        result = '身份证';
                        break;
                    case '11': //户口本
                        result = '户口本';
                        break;
                    case '12': //护照
                        result = '护照';
                        break;
                    case '13': //军官证
                        result = '军官证';
                        break;
                    case '14': //军人士兵证
                        result = '军人士兵证';
                        break;
                    case '15': //港澳居民往来内地通行证
                        result = '港澳居民往来内地通行证';
                        break;
                    case '16': //台湾居民来往大陆通行证
                        result = '台湾居民来往大陆通行证';
                        break;
                    case '17': //临时身份证
                        result = '临时身份证';
                        break;
                    case '18': //外国人永久居留证
                        result = '外国人永久居留证';
                        break;
                    case '19': //警官证
                        result = '警官证';
                        break;
                    case '1a': //武警士兵证
                        result = '武警士兵证';
                        break;
                    case '1b': //军人文职干部证
                        result = '军人文职干部证';
                        break;
                    case '1c': //武警文职干部证
                        result = '武警文职干部证';
                        break;
                    case '1d': //驾驶证
                        result = '驾驶证';
                        break;
                    case '1e': //军人身份证
                        result = '军人身份证';
                        break;
                    case '1f': //武装警察身份证
                        result = '武装警察身份证';
                        break;
                    case '1g': //外国公民护照
                        result = '外国公民护照';
                        break;
                    case '1h': //个人证件类型
                        result = '个人证件类型';
                        break;
                    case '1i': //军官退休证
                        result = '军官退休证';
                        break;
                    case '1j': //文职干部退休证
                        result = '文职干部退休证';
                        break;
                    case '1k': //武警军官退休证
                        result = '武警军官退休证';
                        break;
                    case '1l': //武警文职干部退休证
                        result = '武警文职干部退休证';
                        break;
                    case '1m': //中国护照
                        result = '中国护照';
                        break;
                    case '1n': //学生证
                        result = '学生证';
                        break;
                    case '1o': //港澳台居民往来通行证
                        result = '港澳台居民往来通行证';
                        break;
                    case '1p': //港澳台居民身份证件
                        result = '港澳台居民身份证件';
                        break;
                    case '1q': //香港居民身份证
                        result = '香港居民身份证';
                        break;
                    case '1r': //澳门居民身份证
                        result = '澳门居民身份证';
                        break;
                    case '1s': //台湾身份证
                        result = '台湾身份证';
                        break;
                    case '1t': //执行公务证
                        result = '执行公务证';
                        break;
                    case '1u': //社会保障卡
                        result = '社会保障卡';
                        break;
                    case '1v': //外国人居留证
                        result = '外国人居留证';
                        break;
                    case '1w': //旅行证件
                        result = '旅行证件';
                        break;
                    case '1y': //外国身份证
                        result = '外国身份证';
                        break;
                    case '1X': //其他证件
                        result = '其他证件';
                        break;
                }

            return result;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('gender', gender);
    /**
    *   性别
    */
    function gender() {
        return function(gender) {
            var result = gender;
             switch (gender) {
                    case '1': //男
                        result = '男';
                        break;
                    case '2': //女
                        result = '女';
                        break;
                    case '3': //不详
                        result = '不详';
                        break;
                }

            return result;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('degree', degree);
    /**
    *   学历
    */
    function degree() {
        return function(degree) {
            var result = degree;
             switch (degree) {
                    case '1': //大专以下
                        result = '大专以下';
                        break;
                    case '2': //本科
                        result = '本科';
                        break;
                    case '3': //硕士
                        result = '硕士';
                        break;
                    case '4': //博士
                        result = '博士';
                        break;
                    case '5': //海归
                        result = '海归';
                        break;
                    case '6': //其他
                        result = '其他';
                        break;
                }

            return result;
        };
    }
})();

/**
 * Created by macbook on 16/2/18.
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('dateFormat', dateFormat);
    /**
     * 日期格式化过滤器
     * @memberof EBankProject
     * @function dateFormat
     * example:
     * <div ng-bind="'ASASSdsff21312D20160302SSDAasfdsf'|dateFormat:{symbol:''}"></div>
     * @description 日期格式化过滤器
     * data  传入的数据
     * format 格式化参数(不区分大小写)
     * 格式:symbol
     *     年 yyyy
     *     月 mm
     *     日 dd
     *     连接符号可自定义 :
     *          dd-mm-yyyy  dd/mm/yyyy dd:mm:yyyy .....
     *     顺序颠倒:
     *          dd-mm-yyyy  mm-dd-yyyy  yyyy-mm-dd   .....
     *     可以缺省配置:
     *          dd-mm   yyyy   dd  dd-mm   .....
     *     不区分大小写:
     *          Dd-mm  YYYY    MM-dd-yyYy ....
     *     可以不设置任何格式:
     *          {symbol:''}为默认显示
     */

    function dateFormat() {
        return function(data, format) {

            var str = data || '';
            var regexp = /[1-2][09]\d{2}[01][1-9][0-3][0-9]/;
            var result = null;
            var yyyy = '',
                MM = '',
                dd = '';
            var fullYear, fullMonth, fullDate = null;

            if (str.length > 0) {

                var dateNum = str.match(regexp);
                dateNum = dateNum.toString();
                var dateArr = dateNum.split('');

                for (var i = 0; i < dateArr.length; i++) {
                    if (i < 4) {
                        yyyy += dateArr[i];
                    } else if (i >= 4 && i < 6) {
                        MM += dateArr[i];
                    } else {
                        dd += dateArr[i];
                    }
                }

                fullYear = format.symbol.toLowerCase().replace(/yyyy/, function(word) {
                    return yyyy || '';
                });
                fullMonth = fullYear.replace(/mm/, function(word) {
                    return MM || '';
                });
                fullDate = fullMonth.replace(/dd/, function(word) {
                    return dd || '';
                });
                result = fullDate || dateNum;
            }

            return result;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('currencyFormat', currencyFormat);

    currencyFormat.$inject = ['$filter'];

    function currencyFormat($filter) {
        return function(number) {
            var num = parseInt(number);

            if(!isNaN(num)) {
                if (num < 10000) {
                    number = $filter('number')(number, '0');
                } else if (num >= 10000) {
                    number = parseInt(num / 10000) + '万';
                }
            }

            return number;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('currencyC', currencyC);
    //币种
    function currencyC() {
        return function(currencyC) {
            var result = '';
             switch (currencyC) {
                    case '01': //人民币
                        result = '人民币';
                        break;
                    case 'CNY': //人民币
                    result = '人民币';
                    break;
                }
            return result;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('ChinaCost', ChinaCost);

    /**
     * 金额大写格式化过滤器
     * @memberof EBankProject
     * @function ChinaCost
     * example:
     * <div ng-bind="'1000'|ChinaCost}"></div>
     * @description 金额格式化过滤器
     * input  传入的数据
     */

    function ChinaCost() {
         return  function(input){
          if(input){
           var numberValue=new String(Math.round(input*100)); // 数字金额
        var chineseValue=""; // 转换后的汉字金额
        var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
        var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
        var len=numberValue.length; // numberValue 的字符串长度
        var Ch1; // 数字的汉语读法
        var Ch2; // 数字位的汉字读法
        var nZero=0; // 用来计算连续的零值的个数
        var String3; // 指定位置的数值
        if(len>15){
        alert("超出计算范围");
        return "";
        }
        if (numberValue==0){
        chineseValue = "零元整";
        return chineseValue;
        }

        String2 = String2.substr(String2.length-len, len); // 取出对应位数的STRING2的值
        for(var i=0; i<len; i++){
        String3 = parseInt(numberValue.substr(i, 1),10); // 取出需转换的某一位的值
        if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){
        if ( String3 == 0 ){
        Ch1 = "";
        Ch2 = "";
        nZero = nZero + 1;
        }
        else if ( String3 != 0 && nZero != 0 ){
        Ch1 = "零" + String1.substr(String3, 1);
        Ch2 = String2.substr(i, 1);
        nZero = 0;
        }
        else{
        Ch1 = String1.substr(String3, 1);
        Ch2 = String2.substr(i, 1);
        nZero = 0;
        }
        }
        else{ // 该位是万亿，亿，万，元位等关键位
        if( String3 != 0 && nZero != 0 ){
        Ch1 = "零" + String1.substr(String3, 1);
        Ch2 = String2.substr(i, 1);
        nZero = 0;
        }
        else if ( String3 != 0 && nZero == 0 ){
        Ch1 = String1.substr(String3, 1);
        Ch2 = String2.substr(i, 1);
        nZero = 0;
        }
        else if( String3 == 0 && nZero >= 3 ){
        Ch1 = "";
        Ch2 = "";
        nZero = nZero + 1;
        }
        else{
        Ch1 = "";
        Ch2 = String2.substr(i, 1);
        nZero = nZero + 1;
        }
        if( i == (len - 11) || i == (len - 3)){ // 如果该位是亿位或元位，则必须写上
        Ch2 = String2.substr(i, 1);
        }
        }
        chineseValue = chineseValue + Ch1 + Ch2;
        }

        if ( String3 == 0 ){ // 最后一位（分）为0时，加上“整”
        chineseValue = chineseValue + "整";
        }

        return chineseValue;
          }
    };
    }
})();




// MyAppFilter.filter('rmbFilter',[function(){

//     function ChinaCost(input){
//         var numberValue=new String(Math.round(input*100)); // 数字金额
//         var chineseValue=""; // 转换后的汉字金额
//         var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
//         var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
//         var len=numberValue.length; // numberValue 的字符串长度
//         var Ch1; // 数字的汉语读法
//         var Ch2; // 数字位的汉字读法
//         var nZero=0; // 用来计算连续的零值的个数
//         var String3; // 指定位置的数值
//         if(len>15){
//         alert("超出计算范围");
//         return "";
//         }
//         if (numberValue==0){
//         chineseValue = "零元整";
//         return chineseValue;
//         }

//         String2 = String2.substr(String2.length-len, len); // 取出对应位数的STRING2的值
//         for(var i=0; i<len; i++){
//         String3 = parseInt(numberValue.substr(i, 1),10); // 取出需转换的某一位的值
//         if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){
//         if ( String3 == 0 ){
//         Ch1 = "";
//         Ch2 = "";
//         nZero = nZero + 1;
//         }
//         else if ( String3 != 0 && nZero != 0 ){
//         Ch1 = "零" + String1.substr(String3, 1);
//         Ch2 = String2.substr(i, 1);
//         nZero = 0;
//         }
//         else{
//         Ch1 = String1.substr(String3, 1);
//         Ch2 = String2.substr(i, 1);
//         nZero = 0;
//         }
//         }
//         else{ // 该位是万亿，亿，万，元位等关键位
//         if( String3 != 0 && nZero != 0 ){
//         Ch1 = "零" + String1.substr(String3, 1);
//         Ch2 = String2.substr(i, 1);
//         nZero = 0;
//         }
//         else if ( String3 != 0 && nZero == 0 ){
//         Ch1 = String1.substr(String3, 1);
//         Ch2 = String2.substr(i, 1);
//         nZero = 0;
//         }
//         else if( String3 == 0 && nZero >= 3 ){
//         Ch1 = "";
//         Ch2 = "";
//         nZero = nZero + 1;
//         }
//         else{
//         Ch1 = "";
//         Ch2 = String2.substr(i, 1);
//         nZero = nZero + 1;
//         }
//         if( i == (len - 11) || i == (len - 3)){ // 如果该位是亿位或元位，则必须写上
//         Ch2 = String2.substr(i, 1);
//         }
//         }
//         chineseValue = chineseValue + Ch1 + Ch2;
//         }

//         if ( String3 == 0 ){ // 最后一位（分）为0时，加上“整”
//         chineseValue = chineseValue + "整";
//         }

//         return chineseValue;
//     }
//     return  function(input){
//           if(input){
//            return  ChinaCost(input);
//           }
//     };

// }]);

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('cardCiper', cardCiper);

    function cardCiper() {
        return function(cardNo) {
            if (cardNo && cardNo.length > 4) {
                return cardNo.substr(0, 4) + ' **** **** ' + cardNo.substr(-4);
            } else {
                return cardNo;
            }
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('bankLogo', bankLogo);

    function bankLogo() {
        return function(url) {
            if(!url) {
                return;
            }
            var location = url.indexOf('.png');
            var urlBig = url.substring(0,location) + '-b' +  url.substr(location, 4);
            return urlBig;
        };
    }
})();

(function() {
    'use strict';

    /**
     * 账号格式化过滤器
     * @memberof EBankProject
     * @function splitNumberFilter
     * example:
     * ng-bind="(vm.model.payaccount| splitNumberFilter)"
     * @description 账号格式化过滤器
     * input  传入的数据
     */
    angular
        .module('EBankProject')
        .filter('splitNumberFilter', splitNumberFilter);

    function splitNumberFilter() {
        return function(content) {
            return content ? content.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1  ") : content;
        };
    }
})();



// app.filter('splitNumberFilter', [function() {
//       return function(content) {
//         return content ? content.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ") : content;
//       }
// }]);

(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('addPlus', addPlus);

    function addPlus() {
        return function(price) {
            var result = '';
            if (parseFloat(price) > 0) {
                result = '+' + price;
            } else {
                result = price;
            }
            return result;
        };
    }
})();

// /**
//  * MobileWeb 通用功能助手，包含常用的 UA 判断、页面适配、search 参数转 键值对。
//  * 该 JS 应在 head 中尽可能早的引入，减少重绘。
//  *
//  * fixScreen 方法根据两种情况适配，该方法自动执行。
//  *      1. 定宽： 对应 meta 标签写法 -- <meta name="viewport" content="width=750">
//  *          该方法会提取 width 值，主动添加 scale 相关属性值。
//  *          注意： 如果 meta 标签中指定了 initial-scale， 该方法将不做处理（即不执行）。
//  *      2. REM: 不用写 meta 标签，该方法根据 dpr 自动生成，并在 html 标签中加上 data-dpr 和 font-size 两个属性值。
//  *          该方法约束：IOS 系统最大 dpr = 3，其它系统 dpr = 1，页面每 dpr 最大宽度（即页面宽度/dpr） = 750，REM 换算比值为 16。
//  *          对应 css 开发，任何弹性尺寸均使用 rem 单位，rem 默认宽度为 视觉稿宽度 / 16;
//  *              scss 中 $ppr(pixel per rem) 变量写法 -- $ppr: 750px/16/1rem;
//  *                      元素尺寸写法 -- html { font-size: $ppr*1rem; } body { width: 750px/$ppr; }。
//
//  */
// window.mobileUtil = (function(win, doc) {
//     var UA = navigator.userAgent,
//         isAndroid = /android|adr/gi.test(UA),
//         isIos = /iphone|ipod|ipad/gi.test(UA) && !isAndroid, // 据说某些国产机的UA会同时包含 android iphone 字符
//         isMobile = isAndroid || isIos; // 粗略的判断
//
//     return {
//         isAndroid: isAndroid,
//         isIos: isIos,
//         isMobile: isMobile,
//
//         isNewsApp: /NewsApp\/[\d\.]+/gi.test(UA),
//         isWeixin: /MicroMessenger/gi.test(UA),
//         isQQ: /QQ\/\d/gi.test(UA),
//         isYixin: /YiXin/gi.test(UA),
//         isWeibo: /Weibo/gi.test(UA),
//         isTXWeibo: /T(?:X|encent)MicroBlog/gi.test(UA),
//
//         tapEvent: isMobile ? 'tap' : 'click',
//
//         /**
//          * 缩放页面
//          */
//         fixScreen: function() {
//             var metaEl = doc.querySelector('meta[name="viewport"]'),
//                 metaCtt = metaEl ? metaEl.content : '',
//                 matchScale = metaCtt.match(/initial\-scale=([\d\.]+)/),
//                 matchWidth = metaCtt.match(/width=([^,\s]+)/);
//
//             var docEl = doc.documentElement,
//                 maxwidth = 720,
// 				minwidth = 320,
//                 dpr = isIos ? Math.min(win.devicePixelRatio, 3) : 1,
//                 scale = 1 / dpr,
//                 tid;
//
//             // docEl.removeAttribute('data-mw');
//             // docEl.dataset.dpr = dpr;
//             // metaEl = doc.createElement('meta');
//             // metaEl.name = 'viewport';
//             // metaEl.content = fillScale(scale);
//             // docEl.firstElementChild.appendChild(metaEl);
//
//             var refreshRem = function() {
//                 var width = docEl.getBoundingClientRect().width;
//                 if (width > maxwidth) {
//                     return ;
//                 }
// 				if(width < minwidth){
// 					width = minwidth;
// 				}
//                 var rem = width / 26;
//                 docEl.style.fontSize = rem + 'px';
//             };
//
//             win.addEventListener('resize', function() {
//                 clearTimeout(tid);
//                 tid = setTimeout(refreshRem, 300);
//             }, false);
//             win.addEventListener('pageshow', function(e) {
//                 if (e.persisted) {
//                     clearTimeout(tid);
//                     tid = setTimeout(refreshRem, 300);
//                 }
//             }, false);
//
//             refreshRem();
//         }
//     };
// })(window, document);
//
// // 默认直接适配页面
// mobileUtil.fixScreen();

(function() {
    'use strict';

    runBlock.$inject = ["$rootScope", "$cookies", "$state", "$stateParams", "CONFIG", "UserService", "CommonService", "$modalStack", "$timeout", "EventBusService"];
    angular
        .module('EBankProject')
        .run(runBlock);

    /** @ngInject */
    function runBlock($rootScope, $cookies, $state, $stateParams, CONFIG, UserService, CommonService, $modalStack, $timeout, EventBusService) {
        $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams, $urlRouter) {
            // when state change starts, check sessionstorage whether contains current_user or not.
            $modalStack.dismissAll();
            $rootScope.previousState = fromState.name;                      // 上一个state
            $rootScope.nextState = toState.name;                            // 下一个state
            $rootScope.errorMsg = '';
            $rootScope.showCompareBox = false;
            $rootScope.moment = window.moment;

            if(toState.name === 'login') {
                var params = {
                    reqHead:{
                       flag:"1",                        //主副交易标志 1是主交易     2是副交易
                       tradeType:"3",                   // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                       serviceName:"loginAction"        // 服务名称
                    },
                    reqBody:{
                        
                    }
                    
                };

                // var promise = UserService.logout(params);                                   // 非正常情况回到login页面处理

                // promise.then(function(data) {
                    
                // }).catch(function(error) {
                //     CommonService.showError(error);
                // });
            }

            if (sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER)) {
                $rootScope.hasLogin = true;
                $rootScope.hideTopMenu = false;
                $rootScope.hideFooter = true;
            } else {
                $rootScope.hasLogin = false;                                // 账户处于未登录状态
                if (toState.loginState) {
                    event.preventDefault();
                    $state.go('login');                                     // 返回到登录界面

                    $timeout(function () {
                        $rootScope.hideTopMenu = true;
                        $rootScope.hideFooter = false;
                    });
                }else{
                    $rootScope.hideTopMenu = true;
                    $rootScope.hideFooter = false;
                }
            }

        });

        $rootScope.CONFIG = CONFIG;

        if (CONFIG.OFFLINE) {
            var mockURL = CONFIG.ROOT_URL + CONFIG.URL;
            for (var api in CONFIG.API) {
                var url = mockURL + '/' + CONFIG.API[api];
                if (window[api]) {
                    window[api].txCode = CONFIG.API[api];
                    window.Mock.mock(url, window[api]);
                }
            }
        }

        // 次函数监控页面刷新事件，需要注意浏览器兼容性(风险点)。
        window.addEventListener('beforeunload', function(event) {
            return null;
        });
    }
})();

(function() {
    'use strict';

    routerConfig.$inject = ["$stateProvider", "$urlRouterProvider"];
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

/* global malarkey:false, moment:false */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .constant('CONFIG', {
            OFFLINE: window.OFFLINE,
            CORRECT_CODE: '00000000',                                       // 服务器返回正确的编码
            TRADE_ERR_CODE:'0001',                                          // 处理中的编码
            ROOT_URL: window.ROOT_URL,                                      // mock服务器url
            BROSURLROOT_URL: window.BROSURLROOT_URL,
            URL: window.URL,
            BROSURL:window.BROSURL,
            VALIDATE_PIC_URL: 'bros-consumer-login/VerifyImage',           // 验证码图片地址
            BANKCODE: '401',                                               // 本行银行代码
            API:{
                LOGIN: 'common_login',                                     // 用户登录
                LOGOUT: 'common_logout',                                   // 用户退出
                PBANKLOGINMENUINFO: 'manage/pbankLoginMenuInfo',           // 个人网银菜单查询
                SHELFBYSHELFCODE:'manage/shelfGoodsMenuByShelfCode',       // 根据货架编号查询货架商品及菜单信息
                ADDRECORDSHOPCART:'manage/addRecordShopCart',              // 添加某条记录到购物车并返回购物车信息服务
                QUERYSHOPCART:'manage/queryShopCart',                      // 查询购物车
                DELETECLEARSHOPCART:'manage/deleteClearShopCart',          // 清除购物车
                DELETESHOPCARTRECORD:'manage/deleteShopCartRecord',        // 删除购物车中某些记录
                UPDATERECORDSHOPCART:'manage/updateRecordShopCart',        // 更新购物车的某条记录，并返回购物车信息服务
                CALCULATECARTPRICE:'manage/calculateShoppingCartPrice',    // 计算购物车价格
                EXTERNALPROCESSORDER:'manage/externalProcessOrder',        // 计算购物车价格

                QUERYSTORELISTINFO:'manage/queryStoreListInfo',            // 查询店铺与客户支付工具和客户地址信息列表流程
                QUERYPRODUCTLISTPROCESS:'manage/queryProductListProcess',  // 查询产品
                QUERYDISCOVERPROMOTIONS:'manage/queryDiscoverPromotions',  //查询促销
                QUERYDISCOVERHOT:'manage/queryDiscoverHot',                 //查询热销

                GETCUSTOMERLIST:'manage/queryCustomerProductServiceByPartyId',// 根据partyId查询客户产品视图
                GETCUSTOMERLISTSERVICE:'manage/getCustomerLSstservice',    // 我的账户
                REGISTER: 'login_register',                                // 用户自助注册
                FORGETPASSWORD:'login_forgetpassword',                     // 忘记密码
                SENDPAYRETURN:'login_sendPayReturn',                       // 支付发送
                GET_PROPERTY_DISTRIBUTION:'login_distribution',            // 用户资产分布
                GETPRODUCTDETAIL:'login_getproductDetail',                 // 贷款产品列表查询
                COMPLETED_ORDER:'manage/getOrdersList',                    // 我的订单-订单列表
                ORDER_DETAIL:'manage/getOrderDetail',                      // 我的订单-订单详情
                SURVEY_QUESTION:'manage/getSurveyQuestion',                // 我的订单-订单详情
                QUERYQUOTEDPRICE:'manage/queryQuotedPrice',                // 报价
                QUERYLOANLIST:'logn_queeryloanlist',                       // 我的贷款信息查询
                FIXED_PRODUCT:'logn_fixedProduct',                         // 获取固产品列表
                TRANSFER:'login_transfer',                                 // 转账
                BANK_LIST: 'login_banklist',                               // 银行列表
                CONTTACTS_LIST: 'login_contactsList',                      // 获取收款人名册列表
                QUERYLOANDETAIL:'login_queryloandetail',                   // 我的贷款信息详细查询
                QUERYFINANCIALIST:'login_queryfinancialist',               // 理财模块产品查询
                QUERYPRODUCTLIST:'login_queryproductlist',                 // 产品详情查询
            	GETSUBACCOUNT:'login_getsubaccount',                	   // 获取子账户信息
                PRODUCTREC:'login_productrec',                             // 查询推荐产品信息
                SHOWPRODUCTDETAIL:'manage/showProductDetail',              // 查询产品详细信息
                PERSONOVERVIEWQUERY:'manage/personOverviewQuery',          // 客户总览查询
                CUSTPROPERTYDETAILQUERY:'manage/custPropertyDetailQuery',  // 查询客户资产明细
                QUERYATTRIBUTEINFOMETHOD:'manage/queryProductDetail',      // 产品ID查询产品详情
                BROWSEHIS:'login_browsehis',                               // 查询浏览记录
                UPDATEBATCHRECORDSHOPCART:'manage/updateBatchRecordShopCart', // 批量更新购物车
                CREATESHOPPINGLISTSERVICE:'manage/createShoppingListService',// 需求提交
                CHECKSHOWSHELFGOODSINFOMETHOD:'manage/checkShowShelfGoodsInfoMethod' //检查是否上架

            },

            REQHEAD:{
                TAXI:"0001",                                                // 租户
                STEPCODE:"01",                                              // 场景步骤
                SCENECODE:"0000",                                           // 场景编码
                SCENENAME:"sceneName",                                      // 场景名称
                LEGALID:"80fb68c1-fce5-440d-85a3-9c392ba1ba83",             // 法人ID
                LEGALCODE:"",                                               // 法人编码
                TRADETYPE:"tradeType",                                      // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                SERVICENAME:"serviceName",                                  // 服务名称
                VERSION:"1.0.0"                                             // 版本号，从1.0.0开始
            },

            SESSION:{
                CURRENT_USER: 'CURRENT_USER',                               // 当前的用户信息
                TREE_MENU: 'TREE_MENU',                                     // 当前菜单信息
                SESSION_DATA_PAGE_INPUT: 'SESSION_DATA_PAGE_INPUT',         // 页面数据传输录入
                SESSION_DATA_PAGE_CONFIRM: 'SESSION_DATA_PAGE_CONFIRM',     // 页面数据传输确认
                SESSION_CAR_DATA:'SESSION_CAR_DATA',                        // 购物车数据
                SESSION_NOW_BAY:'SESSION_NOW_BAY'
            },

            CONSTANT: {
                SHELFCODE:{
                    SD0101: 'SC0301',                                        // 借记卡货架
                    SD0102: 'SC0302',                                        // 定期一本货架
                    SD0103: 'SC0304',                                        // 活期一本货架
                    SD0104: 'SC0303'                                         // 存单卡货架
                },
                ACCTYPE:{
                    ACCTYPE01:'01',                                          // 借记卡账户
                    ACCTYPE02:'02',                                          // 定期一本账户
                    ACCTYPE03:'03',                                          // 活期一本账户
                    ACCTYPE04:'04',                                          // 存单账户
                },
                TRANS_CATEGORY: {                                           // 我的订单交易分类
                    BENEFIT: '000',                                         // 已完成订单
                    DEPOSIT: '001',                                         // 未完成订单
                    ALL: '-1'                                               // 所有交易
                },
                PRODUCT_MANAGE: {                                                 // 产品管理
                    BASEINFO: '000',                                              // 基本信息
                    PROPERTY: '001',                                              // 产品属性
                    SERVICE:  '002',                                              // 产品服务
                    ALL: '-1'                                                     // 所有交易
                },
                CONSUMER_LOAN: {                                            // 贷款模块
                    LOANSERVICE: '000',                                     // 贷款申请
                    MYLOAN: '001',                                          // 我的贷款
                    PROGRESS : '002',                                       // 贷款进度
                    PERSONDATA: '003',                                      // 个人资料
                    ALL: '-1'                                               // 所有交易
                },
                ERROR: {
                    NO_SERVER: '服务器连接失败,请稍后重试',
                    NO_ERROR: '404错误,服务器连接失败,请稍后重试',
                    NO_DEAL: '交易失败，请稍后重试',
                    NO_VALIDATE_CODE: '未获取手机验证码',
                    ACCOUNT_ERROR: '账户处于非正常状态',
                    AMT_BIG: '投资金额'
                }
            }
        });
})();

(function() {
    'use strict';

    config.$inject = ["$logProvider", "toastrConfig", "$httpProvider", "$provide", "CONFIG"];
    angular
        .module('EBankProject')
        .config(config);

    /** @ngInject */
    function config($logProvider, toastrConfig ,$httpProvider,$provide,CONFIG) {
        // Enable log
        $logProvider.debugEnabled(true);
        // Set options third-party lib
        toastrConfig.allowHtml = true;
        toastrConfig.timeOut = 2000;
        toastrConfig.positionClass = 'toast-top-center';
        toastrConfig.preventOpenDuplicates = true;
        toastrConfig.progressBar = false;

        $httpProvider.interceptors.push('httpInterceptor');

         // 头部配置
        //$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
        //$httpProvider.defaults.headers.post['Accept'] = 'application/json, text/javascript, */*; q=0.01';
        //$httpProvider.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
        // http请求头协议中头部ContentType属性
        //$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';

        //重写表单请求数据格式
        // Override $http service's default transformRequest
        //$httpProvider.defaults.transformRequest = [function(data) {
        /**
         * The workhorse; converts an object to x-www-form-urlencoded serialization.
         * @param {Object} obj
         * @return {String}
         */
    //     var param = function(obj) {
    //         var query = '';
    //         var name, value, fullSubName, subName, subValue, innerObj, i;
    //         for (name in obj) {
    //             value = obj[name];
    //             if (value instanceof Array) {
    //                 for (i = 0; i < value.length; ++i) {
    //                     subValue = value[i];
    //                     fullSubName = name + '[' + i + ']';
    //                     innerObj = {};
    //                     innerObj[fullSubName] = subValue;
    //                     query += param(innerObj) + '&';
    //                 }
    //             } else if (value instanceof Object) {
    //                 for (subName in value) {
    //                     subValue = value[subName];
    //                     fullSubName = name + '[' + subName + ']';
    //                     innerObj = {};
    //                     innerObj[fullSubName] = subValue;
    //                     query += param(innerObj) + '&';
    //                 }
    //             } else if (value !== undefined && value !== null) {
    //                 query += encodeURIComponent(name) + '='
    //                         + encodeURIComponent(value) + '&';
    //             }
    //         }
    //         return query.length ? query.substr(0, query.length - 1) : query;
    //     };
    //     return angular.isObject(data) && String(data) !== '[object File]'
    //             ? param(data)
    //             : data;
    // }];


        // $provide.decorator('$rootScope', [
        //     '$delegate', function ($delegate) {
        //     Object.defineProperty($delegate.constructor.prototype,
        //     '$bus', {
        //         value: window.postal,
        //         enumerable: false
        //     });
        //     return $delegate;
        // }]);
    }
})();

angular.module("EBankProject").run(["$templateCache", function($templateCache) {$templateCache.put("app/components/consumerFinancial/consumerFinancial.html","<div><div class=\"banner-transfer\"></div><div class=\"main-box\"><div class=\"product-list-two\"><div class=\"product-list-box\"><div class=\"product-list\"><div class=\"list\" ng-repeat=\"list in vm.financialList\"><div class=\"img\"><img src=\"{{list.financialurl}}\" alt=\"\" ui-sref=\"productdetail({productCode:list.financialcode})\"></div><ul class=\"product-info\"><li class=\"product-title\" ui-sref=\"productdetail({productCode:list.financialcode})\" ng-bind=\"list.financialname\"></li><li ng-bind=\"list.financialbewrite\">稳健投资，安心回报</li><li>近一年收益率：<b class=\"big-font\" ng-bind=\"list.financialrate\"></b><b class=\"big-font\"><span>%</span></b></li><li>投资风险：<span ng-bind=\"list.financialtype\"></span></li><li class=\"product-but\"><button type=\"button\" class=\"btn btn-primary\" ui-sref=\"productdetail({productCode:list.financialcode})\">加入购物车</button></li></ul></div></div></div><div><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div></div></div></div>");
$templateCache.put("app/components/home/home.html","<div><carousel interval=\"myInterval\"><slide ng-repeat=\"productIdList in vm.productIdList\" active=\"slide.active\"><img ng-src=\"../assets{{productIdList.contentImg}}\" style=\"margin:auto;width: 100%;height: 400px\" ng-click=\"vm.slideProImg(productIdList);\"></slide></carousel><div class=\"account-box\"><div class=\"account\"><div class=\"account-chart\"><div class=\"title\">账户总览</div><div class=\"my-fortune\"><div class=\"finance-summary\"><div class=\"main\"><div class=\"finance-body\"><div class=\"row\"><div class=\"col-lg-5 col-md-5 col-sm-5\"><div ng-if=\"vm.hasPie\" e-chart=\"\" class=\"pieChart\" options=\"vm.pieOptions\"></div><img ng-if=\"!vm.hasPie\" ng-src=\"{{vm.emptyPie}}\" style=\"margin-left:15px\"><div class=\"shadow-line\"></div></div><div class=\"col-lg-7 col-md-7 col-sm-7\"><div class=\"row finance-row\" ng-repeat=\"distribution in vm.distributionData\"><div class=\"col-md-1\"><div class=\"circle\" ng-style=\"{background:distribution.color}\"></div></div><div class=\"col-md-2 product-name\" ng-bind=\"distribution.desc\"></div><div class=\"col-md-4 text-right no-wrap\" ng-if=\"distribution.assetType==\'1\' || distribution.assetType==\'2\'||distribution.assetType==\'3\'\"><span class=\"money\" privacy=\"\" ng-bind=\"(distribution.sum| number:2)\"></span>元</div><div class=\"col-md-4 text-right no-wrap\" ng-if=\"distribution.assetType==\'4\' || distribution.assetType==\'5\'\"><span class=\"money\" privacy=\"\" ng-bind=\"(distribution.sum)\"></span>张</div><div class=\"col-md-3 oper\"><a class=\"operlink\" ng-repeat=\"operator in distribution.operator\" ng-if=\"operator.state\" ui-sref=\"{{operator.state}}\">{{operator.name}}</a></div></div></div></div></div></div></div></div></div><div class=\"recommend-product\"><div class=\"title\">到期提醒</div><div><span></span></div><div class=\"gunlun\"><ul class=\"product-info1\" style=\"border: 0xp;\"><li ng-repeat=\"personRemindInfo in vm.personRemindInfo\"><div class=\"tex\"><span>产品</span>有<b>1</b>{{personRemindInfo.remindTxt}}<b></b><br></div></li></ul></div></div></div></div><div class=\"main-box\"><div browsehistory=\"\"></div></div></div>");
$templateCache.put("app/components/personOverview/custPropertyDetail.html","<div class=\"page-form\" ng-controller=\"custPropertyDetailController as vm\" style=\"padding-left: 30px;padding-right: 30px;\"><h>资产明细-<span ng-bind=\"vm.name\"></span></h><div class=\"content\"><div class=\"tableList\" ng-if=\"vm.assetType==\'1\'\"><table width=\"100%\"><thead><tr><th>产品名称</th><th>账号</th><th>币种</th><th>金额</th></tr></thead><tbody><tr ng-repeat=\"returnList in vm.depositDetailInfo\"><td ng-bind=\"returnList.productName\"></td><td ng-bind=\"returnList.accountNum\"></td><td ng-bind=\"returnList.currency | currencyC\"></td><td ng-bind=\"returnList.amount | number:\'2\'\"></td></tr></tbody></table></div><div class=\"tableList\" ng-if=\"vm.assetType==\'2\'\"><table width=\"100%\"><thead><tr><th>贷款合约号</th><th>产品名称</th><th>币种</th><th>贷款金额</th><th>期限</th><th>贷款余额</th><th>到期日期</th><th>贷款状态</th></tr></thead><tbody><tr ng-repeat=\"returnList in vm.loanDetailInfo\"><td ng-bind=\"returnList.loanNo\"></td><td ng-bind=\"returnList.productName\"></td><td ng-bind=\"returnList.currency | currencyC\"></td><td ng-bind=\"returnList.loanAmount | number:\'2\'\"></td><td ng-bind=\"returnList.deadline\"></td><td ng-bind=\"returnList.loanBalance | number:\'2\'\"></td><td ng-bind=\"returnList.expireDate\"></td><td ng-bind=\"returnList.loanStatus | loanStatus\"></td></tr></tbody></table></div><div class=\"tableList\" ng-if=\"vm.assetType==\'3\'\"><table width=\"100%\"><thead><tr><th>账号</th><th>产品名称</th><th>币种</th><th>本金</th></tr></thead><tbody><tr ng-repeat=\"returnList in vm.investDetailInfo\"><td ng-bind=\"returnList.accountNum\"></td><td ng-bind=\"returnList.productName\"></td><td ng-bind=\"returnList.currency | currencyC\"></td><td ng-bind=\"returnList.principal | number:\'2\'\"></td></tr></tbody></table></div><div class=\"tableList\" ng-if=\"vm.assetType==\'4\'\"><table width=\"100%\"><thead><tr><th>产品名称</th><th>签约卡号</th><th>签约机构</th><th>签约日期</th></tr></thead><tbody><tr ng-repeat=\"returnList in vm.signDetailInfo\"><td ng-bind=\"returnList.productName\"></td><td ng-bind=\"returnList.cardNo\"></td><td ng-bind=\"returnList.org\"></td><td ng-bind=\"returnList.date\"></td></tr></tbody></table></div><div class=\"tableList\" ng-if=\"vm.assetType==\'5\'\"><table width=\"100%\"><thead><tr><th>卡号</th><th>开卡日期</th><th>开卡机构</th></tr></thead><tbody><tr ng-repeat=\"returnList in vm.creditCardDetailInfo\"><td ng-bind=\"returnList.cardNo\"></td><td ng-bind=\"returnList.issueDate\"></td><td ng-bind=\"returnList.issueOrg\"></td></tr></tbody></table></div></div></div>");
$templateCache.put("app/components/personOverview/personOverview.html","<div class=\"content\"><div class=\"content-inner\"><div class=\"content-part1\"><div class=\"part1-top\"><p>个人信息</p><div class=\"part1-right\" ng-hide=\"true\"><a href=\"\">更新</a> <a href=\"\">修改密码</a> <a href=\"\">隐藏过去的</a></div></div><table class=\"a-table\"><tr><td>客户号</td><td ng-bind=\"vm.personSummaryInfo[0].partyId\"></td><td>客户名称</td><td ng-bind=\"vm.personSummaryInfo[0].customerName\"></td></tr><tr><td>证件类型</td><td ng-bind=\"vm.personSummaryInfo[0].contactCertificateTypeId | idCardType\"></td><td>号码</td><td ng-bind=\"vm.personSummaryInfo[0].contactCertificateNo\"></td></tr><tr><td>手机号码</td><td ng-bind=\"vm.personSummaryInfo[0].mobilePhone\"></td><td>通讯地址</td><td ng-bind=\"vm.personSummaryInfo[0].postalAddress\"></td></tr></table></div><div class=\"content-part2\"><div class=\"part2-top\"><p class=\"xinxi\">家庭成员信息</p></div><table class=\"a-table\"><tr><td>成员关系</td><td>姓名</td><td>证件类型</td><td>证件号码</td><td>出生日期</td><td>性别</td><td>职业</td><td>单位名称</td><td>月收入</td><td>学历</td></tr><tr ng-repeat=\"returnList in vm.familyMemberInfo\"><td ng-bind=\"returnList.partyRelationShipTypeId | relationShipType\"></td><td ng-bind=\"returnList.custName\"></td><td ng-bind=\"returnList.contactCertificateTypeId | idCardType\"></td><td ng-bind=\"returnList.contactCertificateNo\"></td><td ng-bind=\"returnList.birthDate\"></td><td ng-bind=\"returnList.gender | gender\"></td><td ng-bind=\"returnList.flag | profession\"></td><td ng-bind=\"returnList.partyGroupName\"></td><td ng-bind=\"returnList.monthIncome | number:2\"></td><td ng-bind=\"returnList.degree | degree\"></td></tr></table></div><div class=\"content-part2\"><div class=\"part3-top\"><p class=\"xinxi\">社会关系信息</p><a href=\"\" ng-hide=\"true\">创建新的</a></div><table class=\"a-table\"><tr><td>社会关系</td><td>姓名</td><td>证件类型</td><td>证件号码</td><td>出生日期</td><td>性别</td><td>职业</td><td>单位名称</td><td>月收入</td><td>学历</td></tr><tr ng-repeat=\"returnList in vm.relativePersonInfo\"><td ng-bind=\"returnList.partyRelationShipTypeId | relationShipType\"></td><td ng-bind=\"returnList.lastName\"></td><td ng-bind=\"returnList.contactCertificateTypeId | idCardType\"></td><td ng-bind=\"returnList.contactCertificateNo\"></td><td ng-bind=\"returnList.birthDate\"></td><td ng-bind=\"returnList.gender | gender\"></td><td ng-bind=\"returnList.job | profession\"></td><td ng-bind=\"returnList.partyName\"></td><td ng-bind=\"returnList.monthIncome | number:2\"></td><td ng-bind=\"returnList.qualify | degree\"></td></tr></table></div><div class=\"content-part4\"></div></div></div>");
$templateCache.put("app/directives/accountManagementDir/accountManagementDir.html","<div ng-repeat=\"itemShelFcodeList in shelfcodelist.returnList.children\"><div class=\"main-box\" ng-repeat=\"accountList in list\" ng-if=\"itemShelFcodeList.itemValue.shelfCode == vm.SD0101 && accountList.acctype == vm.ACCTYPE01\"><div class=\"account-list-box\"><div class=\"account-list-title\"><div class=\"card-box\"><div class=\"card\"><div class=\"card-img card2\"><div class=\"card-number\" ng-bind=\"accountList.accnum | cardCiper\"></div></div></div><div class=\"card-mask\"><span ng-bind=\"itemShelFcodeList.itemValue.shelfName\"></span></div></div><div class=\"card-info\"><dl><dd>账户别名</dd><dt ng-bind=\"accountList.accname\"></dt></dl><dl><dd>账户余额(元)</dd><dt ng-bind=\"accountList.acclance |currency:\'¥\'\"></dt></dl><dl><dd>账户状态</dd><dt ng-bind=\"accountList.acctype\"></dt></dl></div><div class=\"card-btn\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-repeat=\"prdTypecodeList in itemShelFcodeList.children\" ui-sref=\"{{prdTypecodeList.itemValue.bmfUrl}}\">{{prdTypecodeList.itemValue.bmfName}}</button></div></div><div class=\"account-opne-btn\"><span ng-click=\"vm.getSubAccount();\">查看子账户</span><i class=\"glyphicon glyphicon-menu-up\"></i></div><div class=\"account-list-detail-shadow\"></div><div class=\"account-list-detail\" style=\"display:\"><div class=\"tableList\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr><th>子账户号</th><th>币种</th><th>钞／汇</th><th>账户余额</th><th>储种</th><th>利率</th><th>状态</th><th>操作</th></tr></thead><tbody><tr ng-repeat=\"subAccountList in vm.subAccountList\"><td ng-bind=\"subAccountList.subAccountNumber\"></td><td ng-bind=\"subAccountList.currency\"></td><td ng-bind=\"subAccountList.notesFlag\"></td><td ng-bind=\"subAccountList.accountBalance\"></td><td ng-bind=\"subAccountList.kindFlag\"></td><td ng-bind=\"subAccountList.interestRate\"></td><td ng-bind=\"subAccountList.accountState\"></td><td><a href=\"\">详情</a></td></tr></tbody></table></div><div class=\"pageDiv\"><ul class=\"pagination\"><li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li><li class=\"active\"><a href=\"#\">1 <span class=\"sr-only\">(current)</span></a></li><li><a href=\"#\">2</a></li><li><a href=\"#\">3</a></li><li><a href=\"#\">4</a></li><li><a href=\"#\">5</a></li><li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li></ul></div></div></div></div><div class=\"main-box\" ng-repeat=\"accountList in list\" ng-if=\"itemShelFcodeList.itemValue.shelfCode == vm.SD0102 && accountList.acctype == vm.ACCTYPE02\"><div class=\"account-list-box\"><div class=\"account-list-title\"><div class=\"card-box\"><div class=\"card\"><div class=\"card-img card3\"><div class=\"card-number\" ng-bind=\"accountList.accnum | cardCiper\"></div></div></div><div class=\"card-mask\"><span ng-bind=\"itemShelFcodeList.itemValue.shelfName\"></span></div></div><div class=\"card-info\"><dl><dd>账户别名</dd><dt ng-bind=\"accountList.accname\"></dt></dl><dl><dd>账户余额(元)</dd><dt ng-bind=\"accountList.acclance |currency:\'¥\'\"></dt></dl><dl><dd>账户状态</dd><dt ng-bind=\"accountList.acctype\"></dt></dl></div><div class=\"card-btn\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-repeat=\"prdTypecodeList in itemShelFcodeList.children\" ui-sref=\"{{prdTypecodeList.itemValue.bmfUrl}}\">{{prdTypecodeList.itemValue.bmfName}}</button></div></div><div class=\"account-opne-btn\"><span ng-click=\"vm.getSubAccount();\">查看子账户</span><i class=\"glyphicon glyphicon-menu-up\"></i></div><div class=\"account-list-detail-shadow\"></div><div class=\"account-list-detail\" style=\"display:\"><div class=\"tableList\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr><th>子账户号</th><th>币种</th><th>钞／汇</th><th>账户余额</th><th>储种</th><th>利率</th><th>状态</th><th>操作</th></tr></thead><tbody><tr ng-repeat=\"subAccountList in vm.subAccountList\"><td ng-bind=\"subAccountList.subAccountNumber\"></td><td ng-bind=\"subAccountList.currency\"></td><td ng-bind=\"subAccountList.notesFlag\"></td><td ng-bind=\"subAccountList.accountBalance\"></td><td ng-bind=\"subAccountList.kindFlag\"></td><td ng-bind=\"subAccountList.interestRate\"></td><td ng-bind=\"subAccountList.accountState\"></td><td><a href=\"\">详情</a></td></tr></tbody></table></div><div class=\"pageDiv\"><ul class=\"pagination\"><li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li><li class=\"active\"><a href=\"#\">1 <span class=\"sr-only\">(current)</span></a></li><li><a href=\"#\">2</a></li><li><a href=\"#\">3</a></li><li><a href=\"#\">4</a></li><li><a href=\"#\">5</a></li><li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li></ul></div></div></div></div><div class=\"main-box\" ng-repeat=\"accountList in list\" ng-if=\"itemShelFcodeList.itemValue.shelfCode == vm.SD0103 && accountList.acctype == vm.ACCTYPE03\"><div class=\"account-list-box\"><div class=\"account-list-title\"><div class=\"card-box\"><div class=\"card\"><div class=\"card-img card2\"><div class=\"card-number\" ng-bind=\"accountList.accnum | cardCiper\"></div></div></div><div class=\"card-mask\"><span ng-bind=\"itemShelFcodeList.itemValue.shelfName\"></span></div></div><div class=\"card-info\"><dl><dd>账户别名</dd><dt ng-bind=\"accountList.accname\"></dt></dl><dl><dd>账户余额(元)</dd><dt ng-bind=\"accountList.acclance |currency:\'¥\'\"></dt></dl><dl><dd>账户状态</dd><dt ng-bind=\"accountList.acctype\"></dt></dl></div><div class=\"card-btn\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-repeat=\"prdTypecodeList in itemShelFcodeList.children\" ui-sref=\"{{prdTypecodeList.itemValue.bmfUrl}}\">{{prdTypecodeList.itemValue.bmfName}}</button></div></div><div class=\"account-opne-btn\"><span ng-click=\"vm.getSubAccount();\">查看子账户</span><i class=\"glyphicon glyphicon-menu-up\"></i></div><div class=\"account-list-detail-shadow\"></div><div class=\"account-list-detail\" style=\"display:\"><div class=\"tableList\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr><th>子账户号</th><th>币种</th><th>钞／汇</th><th>账户余额</th><th>储种</th><th>利率</th><th>状态</th><th>操作</th></tr></thead><tbody><tr ng-repeat=\"subAccountList in vm.subAccountList\"><td ng-bind=\"subAccountList.subAccountNumber\"></td><td ng-bind=\"subAccountList.currency\"></td><td ng-bind=\"subAccountList.notesFlag\"></td><td ng-bind=\"subAccountList.accountBalance\"></td><td ng-bind=\"subAccountList.kindFlag\"></td><td ng-bind=\"subAccountList.interestRate\"></td><td ng-bind=\"subAccountList.accountState\"></td><td><a href=\"\">详情</a></td></tr></tbody></table></div><div class=\"pageDiv\"><ul class=\"pagination\"><li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li><li class=\"active\"><a href=\"#\">1 <span class=\"sr-only\">(current)</span></a></li><li><a href=\"#\">2</a></li><li><a href=\"#\">3</a></li><li><a href=\"#\">4</a></li><li><a href=\"#\">5</a></li><li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li></ul></div></div></div></div><div class=\"main-box\" ng-repeat=\"accountList in list\" ng-if=\"itemShelFcodeList.itemValue.shelfCode == vm.SD0104 && accountList.acctype == vm.ACCTYPE04\"><div class=\"account-list-box\"><div class=\"account-list-title\"><div class=\"card-box\"><div class=\"card\"><div class=\"card-img card2\"><div class=\"card-number\" ng-bind=\"accountList.accnum | cardCiper\"></div></div></div><div class=\"card-mask\"><span ng-bind=\"itemShelFcodeList.itemValue.shelfName\"></span></div></div><div class=\"card-info\"><dl><dd>账户别名</dd><dt ng-bind=\"accountList.accname\"></dt></dl><dl><dd>账户余额(元)</dd><dt ng-bind=\"accountList.acclance |currency:\'¥\'\"></dt></dl><dl><dd>账户状态</dd><dt ng-bind=\"accountList.acctype\"></dt></dl></div><div class=\"card-btn\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-repeat=\"prdTypecodeList in itemShelFcodeList.children\" ui-sref=\"{{prdTypecodeList.itemValue.bmfUrl}}\">{{prdTypecodeList.itemValue.bmfName}}</button></div></div><div class=\"account-opne-btn\"><span ng-click=\"vm.getSubAccount();\">查看子账户</span><i class=\"glyphicon glyphicon-menu-up\"></i></div><div class=\"account-list-detail-shadow\"></div><div class=\"account-list-detail\" style=\"display:\"><div class=\"tableList\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr><th>子账户号</th><th>币种</th><th>钞／汇</th><th>账户余额</th><th>储种</th><th>利率</th><th>状态</th><th>操作</th></tr></thead><tbody><tr ng-repeat=\"subAccountList in vm.subAccountList\"><td ng-bind=\"subAccountList.subAccountNumber\"></td><td ng-bind=\"subAccountList.currency\"></td><td ng-bind=\"subAccountList.notesFlag\"></td><td ng-bind=\"subAccountList.accountBalance\"></td><td ng-bind=\"subAccountList.kindFlag\"></td><td ng-bind=\"subAccountList.interestRate\"></td><td ng-bind=\"subAccountList.accountState\"></td><td><a href=\"\">详情</a></td></tr></tbody></table></div><div class=\"pageDiv\"><ul class=\"pagination\"><li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li><li class=\"active\"><a href=\"#\">1 <span class=\"sr-only\">(current)</span></a></li><li><a href=\"#\">2</a></li><li><a href=\"#\">3</a></li><li><a href=\"#\">4</a></li><li><a href=\"#\">5</a></li><li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li></ul></div></div></div></div></div>");
$templateCache.put("app/directives/productView/productViewDir.html","<div class=\"main-box\" ng-repeat=\"accountList in list\" ng-if=\"list && list.length>0\"><div class=\"account-list-box\"><div class=\"account-list-title\"><div class=\"card-box\"><div class=\"card\"><div class=\"card-img card2\"><div class=\"card-number\" ng-bind=\"accountList.productName\"></div></div></div><div class=\"card-mask\"><span>序号:{{$index+1}}</span></div></div><div class=\"card-info\"><dl><dd>产品标识</dd><dt ng-bind=\"accountList.productId\"></dt></dl><dl><dd>产品名字</dd><dt ng-bind=\"accountList.productName\"></dt></dl></div><div class=\"card-btn\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-repeat=\"productIdList in accountList.productIdList\" ui-sref=\"productdetail({productId:productIdList.productId})\">{{productIdList.productName|intercept}}</button></div></div></div></div><div class=\"main-box\" ng-if=\"!(list && list.length>0)\"><div class=\"account-list-box\"><div class=\"account-list-title\"><div class=\"card-box\"><div class=\"card\"><div class=\"card-img card2\"><div class=\"card-number\">该客户无签约产品</div></div></div><div class=\"card-mask\"><span></span></div></div><div class=\"card-info\"><h4>该客户无签约产品</h4></div></div></div></div>");
$templateCache.put("app/directives/transferService/transferProduct.html","<div class=\"list\"><div class=\"img\" ng-click=\"vm.goproductdetail(product)\" style=\"padding:{{vm.mouseFlag ? \'0px\' : \'10px\'}}\"><img src=\"../assets{{product.imageAdress}}\" ng-mouseover=\"vm.imgMouseover();\" ng-mouseleave=\"vm.imgMouseLeave();\" style=\"height:{{vm.mouseFlag ? \'160px\' : \'150px\'}};width:{{vm.mouseFlag ? \'200px\' : \'180px\'}}\"></div><ul class=\"transfer-info\"><li class=\"transfer-title\" ng-bind=\"product.goodsName\" ng-click=\"vm.goproductdetail(product)\"></li><li class=\"transfer-title\" ng-bind=\"product.goodsCode\"></li><li>产品综合价格</li><li><b class=\"big-font\" ng-bind=\"product.sunPrice|currency:\'¥\'\"></b><b class=\"big-font\"><span>元</span></b></li><li class=\"transfer-but\"><button type=\"button\" class=\"btn btn-primary\" ng-click=\"vm.goproductdetail(product);\">查看详情</button></li></ul></div>");
$templateCache.put("app/directives/tree/tree.html","<ul id=\"menu\"><li ng-repeat=\"item in list.children\" ng-if=\"list.children\"><a ui-sref=\"{{item.itemValue.bmfUrl}}\" ng-click=\"vm.getTreeList(item);\">{{item.itemValue.bmfName}}</a><ul><li ng-repeat=\"itemTwo in item.children\" ng-if=\"item.children\"><a ui-sref=\"{{itemTwo.itemValue.bmfUrl}}\" ng-click=\"vm.getTreeList(itemTwo);\">{{itemTwo.itemValue.bmfName}}</a><ul><li ng-repeat=\"itemThree in itemTwo.children\" ng-if=\"itemTwo.children\"><a ui-sref=\"{{itemThree.itemValue.bmfUrl}}\" ng-click=\"vm.getTreeList(itemThree);\">{{itemThree.itemValue.bmfName}}</a></li></ul></li></ul></li></ul>");
$templateCache.put("app/layout/footer/footer.html","<div><div class=\"footer-login\" ng-show=\"$root.hideTopMenu\"><p><a href=\"#\">关于我们</a><span>|</span><a href=\"#\">帮助中心</a><span>|</span><a href=\"#\">联系我们</a><span>|</span><a href=\"#\">意见反馈</a></p><p><span>京ICP证150277号</span><span>京ICP备15002532号</span><span>UISFTECH © 2016 联创智融科技有限公司</span></p></div><div class=\"footer\" ng-show=\"$root.hideFooter\"><p><a href=\"#\">关于我们</a><span>|</span><a href=\"#\">帮助中心</a><span>|</span><a href=\"#\">联系我们</a><span>|</span><a href=\"#\">意见反馈</a></p><p><span>京ICP证150277号</span><span>京ICP备15002532号</span><span>UISFTECH © 2016 联创智融科技有限公司</span></p></div></div>");
$templateCache.put("app/layout/header/header.html","<div ng-controller=\"HomeController as vm\"><div class=\"header-box\" ng-show=\"$root.hideTopMenu\"><div class=\"header\"><div class=\"logo\"></div><ul class=\"link\"><li><a href=\"#\"><i class=\"glyphicon glyphicon-menu-right\"></i>安全指引</a></li><li><a href=\"#\"><i class=\"glyphicon glyphicon-menu-right\"></i>常见问题</a></li><li><a href=\"#\"><i class=\"glyphicon glyphicon-menu-right\"></i>登录演示</a></li><li><a href=\"#\"><i class=\"glyphicon glyphicon-menu-right\"></i>网银助手</a></li></ul></div></div><div class=\"header-box\" ng-show=\"$root.hideFooter\"><div class=\"header-bar-box\"><div class=\"header-bar\"><div class=\"message\"><span><b>张先生</b>,欢迎您 ! 您有新消息</span><a href=\"#\"><i class=\"glyphicon glyphicon-envelope\"></i>12</a> !<a ng-click=\"vm.personOverviewPage();\"><i class=\"glyphicon glyphicon-menu-down\"></i></a></div><ul class=\"header-bar-btn\"><li><a ui-sref=\"quotedPrice\"><i class=\"glyphicon glyphicon-list-alt\"></i>报价</a></li><li><a ui-sref=\"demand\"><i class=\"glyphicon glyphicon-list-alt\"></i>需求</a></li><li><a ui-sref=\"myOrder\"><i class=\"glyphicon glyphicon-list-alt\"></i>我的订单</a></li><li><a href=\"javascript:;\" ng-click=\"vm.logout()\"><i class=\"glyphicon glyphicon-share\"></i>安全退出</a></li></ul></div></div><div class=\"header\"><div class=\"logo\"></div><div class=\"top-car\" ng-controller=\"sideBarController as vm\" ng-mouseenter=\"vm.carMouseenterFun();\" ng-mouseleave=\"vm.carMouseleaveFun();\"><div class=\"top-car-btn\"><i class=\"glyphicon glyphicon-shopping-cart\"></i> <span>购物车</span><b ng-bind=\"length\"></b><i class=\"glyphicon glyphicon-menu-down\"></i></div><div class=\"top-car-box\"><div class=\"top-car-title\">我的购物车</div><div class=\"top-car-list\"><div class=\"list\" ng-if=\"vm.products\" ng-repeat=\"product in vm.products\"><div class=\"ico\"><i class=\"glyphicon glyphicon-yen\"></i></div><div class=\"title\"><span ng-bind=\"product.productId\"></span> <span ng-bind=\"product.productName\"></span></div><div class=\"money\"><p><b ng-bind=\"product.tradeData.displayPrice|currency:\'¥\'\"></b>x<b ng-bind=\"product.productAmount\"></b></p><p class=\"tool\" ng-click=\"vm.productDelete($index,product)\">删除</p></div></div><div class=\"list\" ng-if=\"!vm.products\">购物车中还没有商品，赶紧选购吧！</div></div><div class=\"top-car-info\"><div class=\"top-car-total\"></div><div class=\"top-car-info-btn\" ng-show=\"vm.products.length > 0\"><button type=\"button\" class=\"btn btn-primary\" ng-click=\"vm.compareBlockHidden()\">清空购物车</button> <button type=\"button\" class=\"btn btn-primary\" ng-click=\"vm.goCompare()\">结算</button></div></div></div></div></div><div class=\"menu-box\"><ebank-tree list=\"vm.treeMenuList\"></ebank-tree></div></div></div>");
$templateCache.put("app/layout/productDetail/productDetail.html","<div class=\"main-box\" ng-controller=\"productDetailShowController as vm\"><div class=\"titile-step\"><div class=\"title\" ng-bind=\"vm.productrec.productname\"></div></div><div class=\"trading-one-box\"><div class=\"product-detail-box clearfix\"><div class=\"product-detail-img clearfix\"><img src=\"{{vm.productrec.producturl}}\"></div><ul class=\"product-detail-info clearfix\"><li class=\"title\" ng-bind=\"vm.productrec.productname\"></li><li><span>历史投资回报率：</span><b class=\"big-font\" ng-bind=\"vm.productrec.productrate\"></b><b class=\"big-font\"><i>%</i></b></li><li><span>投资风险：</span>{{vm.productrec.producttype}}</li><li><span>产品简介：</span>{{vm.productrec.productdesc}}</li></ul><ul class=\"product-detail-input clearfix\"><li><span class=\"title\">金额：</span> <input type=\"text\" class=\"form-control\" ng-model=\"vm.model.bankBalance\" ng-blur=\"vm.blured()\"><span>元</span></li><li><span class=\"title\"></span> <button type=\"button\" class=\"btn btn-primary btn-lg\" ng-click=\"vm.addcar()\">添加至购物车</button></li></ul></div></div><div class=\"product-detail-list2\"><div class=\"titile-step\"><div class=\"title\">产品说明</div></div><div class=\"trading-one-box\"><div class=\"product-detail-content2\" ng-repeat=\"productde in vm.productrec.productdetitle\"><p><b ng-bind=\"productde.smatitle\"></b></p><p ng-bind=\"productde.productdeta\"></p></div></div></div></div>");
$templateCache.put("app/layout/productDetail/productDetailShow.html","<div tabindex=\"-1\" role=\"dialog\" class=\"modal\" ng-class=\"{in: animate}\" ng-style=\"{\'z-index\': 1050 + index*10, display: \'block\'}\"><div class=\"modal-dialog\" ng-class=\"{\'modal-sm\': size == \'sm\', \'modal-lg\': size == \'lg\'}\"><div class=\"modal-content\"><div class=\"modal-header\" ng-controller=\"ModalTemplateController as vm\"><a class=\"close\" ng-click=\"vm.close()\"><i></i></a></div><div class=\"modal-body\" modal-transclude=\"\"></div></div></div></div>");
$templateCache.put("app/layout/showmsg/showmessage.html","<div tabindex=\"-1\" role=\"dialog\" class=\"modal\" ng-class=\"{in: animate}\" ng-style=\"{\'z-index\': 1050 + index*10, display: \'block\'}\"><div class=\"modal-dialog\" ng-class=\"{\'modal-sm\': size == \'sm\', \'modal-lg\': size == \'lg\'}\"><div class=\"modal-body2\" modal-transclude=\"\"></div></div></div>");
$templateCache.put("app/layout/showmsg/showmsg.html","<div class=\"resultWindowsBox\" style=\"margin-top: 100px; margin-left: -315px;\" ng-controller=\"ShowmessageController as vm\"><div ng-show=\"suce.show\"><div class=\"resultBox\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>成功！</dt>流水号：<strong ng-bind=\"vm.serialNo\"></strong><dd ng-bind=\"vm.returnMsg\"></dd></dl><div class=\"btnBox\"><div></div><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div><div ng-show=\"eror.show\"><div class=\"resultBox resultBoxError\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>失败！</dt>流水号：<strong ng-bind=\"vm.serialNo\"></strong><dd ng-bind=\"vm.returnMsg\"></dd></dl><div class=\"btnBox\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div><div ng-show=\"waig.show\"><div class=\"resultBox resultBoxWarning\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>警告！</dt><dd ng-bind=\"vm.error\"></dd></dl><div class=\"btnBox\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div><div ng-show=\"tis.show\"><div class=\"resultBox resultBoxTips\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>提示！</dt><dd ng-bind=\"vm.error\"></dd></dl><div class=\"btnBox\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div></div>");
$templateCache.put("app/layout/showmsg/showmsgerror.html","<div class=\"resultWindowsBox\" style=\"margin-top: 100px; margin-left: -315px;\" ng-controller=\"ShowmessageController as vm\"><div class=\"resultBox resultBoxError\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>失败！</dt>流水号：<strong ng-bind=\"vm.serialNo\"></strong><dd ng-bind=\"vm.returnMsg\"></dd></dl><div class=\"btnBox\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div>");
$templateCache.put("app/layout/showmsg/showmsgsuccess.html","<div class=\"resultWindowsBox\" style=\"margin-top: 100px; margin-left: -315px;\" ng-controller=\"ShowmessageController as vm\"><div class=\"resultBox\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>成功！</dt>流水号：<strong ng-bind=\"vm.serialNo\"></strong><dd ng-bind=\"vm.returnMsg\"></dd></dl><div class=\"btnBox\"><div></div><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div>");
$templateCache.put("app/layout/showmsg/showmsgtips.html","<div class=\"resultWindowsBox\" style=\"margin-top: 100px; margin-left: -315px;\" ng-controller=\"ShowmessageController as vm\"><div class=\"resultBox resultBoxTips\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>提示！</dt><dd ng-bind=\"vm.error\"></dd></dl><div class=\"btnBox\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div>");
$templateCache.put("app/layout/showmsg/showmsgwarning.html","<div class=\"resultWindowsBox\" style=\"margin-top: 100px; margin-left: -315px;\" ng-controller=\"ShowmessageController as vm\"><div><div class=\"resultBox resultBoxWarning\"><div class=\"resultIco\"></div><div class=\"resultInfo\"><dl><dt>警告！</dt><dd ng-bind=\"vm.error\"></dd></dl><div class=\"btnBox\"><button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.surebut()\">确定</button> <button type=\"button\" class=\"btn btn-primary btn-sm\" ng-click=\"vm.close()\">关闭</button></div></div></div></div></div>");
$templateCache.put("app/layout/sideBar/sideBar.html","<ul class=\"icon-bar hidden-xs\" ng-controller=\"sideBarController as vm\"><li class=\"icon-contain\" ng-show=\"none;\"><div class=\"icon-group\"><div class=\"icon icon-weibiaoti2\"></div><div class=\"icon-tips\" ui-sref=\"customRobot\">在线客服</div></div></li><li class=\"icon-contain\" ng-click=\"vm.toggleCompareBlock()\"><div class=\"icon-group\"><div class=\"icon icon-shoppingcart\"></div><div class=\"icon-tips\"><span ng-show=\"!showCompareBox\">购物车</span> <span ng-show=\"showCompareBox\" class=\"glyphicon glyphicon-arrow-right\"></span></div></div></li><div ng-class=\"{\'show\':showCompareBox}\" class=\"compare-tool-block\"><div class=\"btn-group\"><div type=\"button\" class=\"btn btn-content btn-default\">购物车</div><button type=\"button\" ng-click=\"vm.toggleOption()\" class=\"btn btn-default dropdown-toggle\"><span class=\"clear-glyphicon glyphicon glyphicon-remove\"></span></button></div><div class=\"clearfix\"></div><ul class=\"compare-diaplay-list\"><li ng-if=\"vm.products\" ng-repeat=\"product in vm.products\" class=\"compare-diaplay-li\"><span><span ng-bind=\"product.productId\"></span> <span ng-bind=\"product.productName\"></span> &nbsp;&nbsp;&nbsp;&nbsp; <span><b ng-bind=\"product.tradeData.displayPrice|currency:\'¥\'\"></b>x<b ng-bind=\"product.productAmount\"></b></span></span> <i ng-click=\"vm.productDelete($index,product)\" class=\"clear-list cursor glyphicon glyphicon-remove-sign\"></i></li><li ng-if=\"!vm.products\" class=\"compare-diaplay-li\"><span>购物车中还没有商品，赶紧选购吧！</span></li></ul><div class=\"compare-footer-content\" ng-show=\"vm.products.length > 0\"><button class=\"btn btn-danger\" ng-click=\"vm.goCompare()\" type=\"button\" ui-sref=\"compare\">结算</button> <span class=\"clear-info cursor pull-right\" ng-click=\"vm.compareBlockHidden()\">清空</span></div></div><li class=\"icon-contain\" ng-show=\"none;\"><div class=\"icon-group\"><div class=\"icon icon-yijianfankui\"></div><div class=\"icon-tips\">意见反馈</div></div></li><li class=\"icon-contain\" ng-show=\"none;\"><div class=\"icon-group\"><div class=\"icon icon-bangzhuzhongxin\"></div><div class=\"icon-tips\" ui-sref=\"helpCenter\">帮助中心</div></div></li><li class=\"icon-contain\"><div class=\"icon-group\"><div class=\"icon icon-xiangshang\"></div><div class=\"icon-tips\"><a href=\"javascript:scroll(0,0)\">返回顶部</a></div></div></li></ul>");
$templateCache.put("app/components/consumerAccount/accountManagement/accountManagement.html","<div><div class=\"account-box\"><div class=\"account\"><div class=\"account-chart\"><div class=\"title\">账户总览</div><div class=\"my-fortune\"><div class=\"finance-summary\"><div class=\"main\"><div class=\"finance-body\"><div class=\"row\"><div class=\"col-lg-5 col-md-5 col-sm-5\"><div ng-if=\"vm.hasPie\" e-chart=\"\" class=\"pieChart\" options=\"vm.pieOptions\"></div><img ng-if=\"!vm.hasPie\" ng-src=\"{{vm.emptyPie}}\" style=\"margin-left:15px\"><div class=\"shadow-line\"></div></div><div class=\"col-lg-7 col-md-7 col-sm-7\"><div class=\"row finance-row\" ng-repeat=\"distribution in vm.distributionData\"><div class=\"col-md-1\"><div class=\"circle\" ng-style=\"{background:distribution.color}\"></div></div><div class=\"col-md-2 product-name\" ng-bind=\"distribution.desc\"></div><div class=\"col-md-4 text-right no-wrap\"><span class=\"money\" privacy=\"\" ng-bind=\"(distribution.sum| number:2)\"></span>元</div><div class=\"col-md-3 oper\"><a class=\"operlink\" ng-repeat=\"operator in distribution.operator\" ng-if=\"operator.state\" ui-sref=\"{{operator.state}}\">{{operator.name}}</a></div></div></div></div></div></div></div></div></div><div class=\"recommend-product\"><div class=\"title\">推荐产品</div><div class=\"img\"><img src=\"../assets/img/layout/img05.jpg\" alt=\"\"></div><ul class=\"product-info\"><li class=\"product-title\">60天理财债券型投资基金</li><li class=\"product-tag\"><span>低风险</span><span>申赎免费</span><span>收益稳健</span></li><li>近一年收益率：<b class=\"big-font\">24.16<span>%</span></b></li><li>投资风险：低</li><li class=\"product-but\"><button type=\"button\" class=\"btn btn-primary\">查看详情</button></li></ul></div></div></div><div class=\"account-nav-box\"><div class=\"nav-title-bar\"><div class=\"title\">本行账户</div><ul class=\"nav nav-tabs\"><li role=\"presentation\" class=\"active\"><a href=\"#\">本行账户</a></li><li role=\"presentation\" class=\"\"><a href=\"#\">他行账户</a></li></ul></div></div><account-management compare=\"vm.getSubAccount();\" list=\"vm.accountList\" shelfcodelist=\"vm.shelfCodeList\"></account-management></div>");
$templateCache.put("app/components/consumerAccount/productView/productView.html","<div><div class=\"account-box\"><div class=\"account\"><div class=\"account-chart\"><div class=\"title\">账户总览</div><div class=\"my-fortune\"><div class=\"finance-summary\"><div class=\"main\"><div class=\"finance-body\"><div class=\"row\"><div class=\"col-lg-5 col-md-5 col-sm-5\"><div ng-if=\"vm.hasPie\" e-chart=\"\" class=\"pieChart\" options=\"vm.pieOptions\"></div><img ng-if=\"!vm.hasPie\" ng-src=\"{{vm.emptyPie}}\" style=\"margin-left:15px\"><div class=\"shadow-line\"></div></div><div class=\"col-lg-7 col-md-7 col-sm-7\"><div class=\"row finance-row\" ng-repeat=\"distribution in vm.distributionData\"><div class=\"col-md-1\"><div class=\"circle\" ng-style=\"{background:distribution.color}\"></div></div><div class=\"col-md-2 product-name\" ng-bind=\"distribution.desc\"></div><div class=\"col-md-4 text-right no-wrap\" ng-if=\"distribution.assetType==\'1\' || distribution.assetType==\'2\'||distribution.assetType==\'3\'\"><span class=\"money\" privacy=\"\" ng-bind=\"(distribution.sum| number:2)\"></span>元</div><div class=\"col-md-4 text-right no-wrap\" ng-if=\"distribution.assetType==\'4\' || distribution.assetType==\'5\'\"><span class=\"money\" privacy=\"\" ng-bind=\"(distribution.sum)\"></span>张</div><div class=\"col-md-3 oper\"><a class=\"operlink\" ng-repeat=\"operator in distribution.operator\" ng-if=\"operator.state\" ui-sref=\"{{operator.state}}\">{{operator.name}}</a></div></div></div></div></div></div></div></div></div><div class=\"recommend-product\"><div class=\"title\">推荐产品</div><div class=\"img\"><img src=\"../assets/img/layout/img05.jpg\" alt=\"\"></div><ul class=\"product-info\"><li class=\"product-title\">60天理财债券型投资基金</li><li class=\"product-tag\"><span>低风险</span><span>申赎免费</span><span>收益稳健</span></li><li>近一年收益率：<b class=\"big-font\">24.16<span>%</span></b></li><li>投资风险：低</li><li class=\"product-but\"><button type=\"button\" class=\"btn btn-primary\">查看详情</button></li></ul></div></div></div><div class=\"account-nav-box\"><div class=\"nav-title-bar\"><div class=\"title\">产品视图</div></div></div><product-view compare=\"vm.getSubAccount();\" list=\"vm.productIdListPage\"></product-view><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div>");
$templateCache.put("app/components/consumerCar/demand/demand.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\">需求</div></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"row\"><table class=\"table table-bordered no-margin\"><tbody><tr><td class=\"td-title-bg\" style=\"width:100px;\"><div class=\"form-group\"><label for=\"txt_name\" class=\"col-sm-12 control-label\" align=\"right\"><span class=\"red\">*</span>名称：</label></div></td><td><div class=\"form-group\"><div class=\"col-sm-12\"><input type=\"text\" required=\"\" id=\"txt_name\" name=\"txt_name\" ng-model=\"vm.model.name\" class=\"form-control ess-input\" placeholder=\"请输入名称\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regFormSubmit.txt_name.$error\"><span class=\"help-block\" ng-message=\"required\">请输入名称</span></div></div></td></tr><tr><td class=\"td-title-bg\"><div class=\"form-group\"><label for=\"txt_describe\" class=\"col-sm-12 control-label\" align=\"right\">描述：</label></div></td><td><div class=\"form-group\"><div class=\"col-sm-12\"><textarea ng-model=\"vm.model.describe\" class=\"form-control ess-input\" rows=\"10\" placeholder=\"请输入描述\"></textarea></div></div></td></tr></tbody></table><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"button\" class=\"btn btn-primary btn-lg\" ng-click=\"vm.submitgo();\">提交</button></div></div></div></div></div></div>");
$templateCache.put("app/components/consumerCar/myOrder/myOrder.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\">我的订单</div></div><div class=\"trading-one-box\" style=\"height: 120px\"><div class=\"col-md-12\"><div class=\"form-group col-md-6\"><label for=\"input_afficheClass\" class=\"col-sm-5 control-label\">订单类型</label><div class=\"col-sm-7\"><select class=\"form-control ess-input\" id=\"input_afficheClass\" name=\"input_afficheClass\" ng-model=\"vm.orderTypeId\"><option value=\"\">请选择</option><option value=\"PURCHASE_ORDER\">采购订单</option><option value=\"SALES_ORDER\">销售订单</option></select></div></div><div class=\"form-group col-md-6\"><label for=\"input_bmfCode\" class=\"col-sm-5 control-label\">渠道分类</label><div class=\"col-sm-7\"><select class=\"form-control ess-input select2\" id=\"input_operChannel\" name=\"operChannel\" ng-model=\"vm.salesChannelEnumId\" placeholder=\"\"><option value=\"\">请选择</option><option value=\"WEB_SALES_CHANNEL\">网站渠道</option><option value=\"POS_SALES_CHANNEL\">POS渠道</option><option value=\"ATM_SALES_CHANNEL\">自助设备</option><option value=\"PHONE_SALES_CHANNEL\">电话渠道</option><option value=\"RS_SALES_CHANNEL\">实体网点</option><option value=\"APP_SALES_CHANNEL\">APP渠道</option><option value=\"AFFIL_SALES_CHANNEL\">合作渠道</option><option value=\"UNKNWN_SALES_CHANNEL\">未知渠道</option></select></div></div><div class=\"form-group col-md-6\"><label for=\"input_bmfCode\" class=\"col-sm-5 control-label\">开始日期</label><div class=\"col-sm-7\"><div class=\"startDate\"><ui-calendar time=\"vm.startDate\"></ui-calendar></div></div></div><div class=\"form-group col-md-6\"><label for=\"input_bmfCode\" class=\"col-sm-5 control-label\">结束日期</label><div class=\"col-sm-7\"><div class=\"endDate\"><ui-calendar time=\"vm.endDate\"></ui-calendar></div></div></div></div></div><div class=\"box-footer mT-15\"><div class=\"center\" style=\"margin-left: 418px;\"><button type=\"submit\" class=\"btn btn-primary\" ng-click=\"vm.queryOrderList(vm.type);\">查询</button> <button type=\"submit\" class=\"btn btn-default\" ng-click=\"vm.resetInfo();\">重置</button></div></div><custom-tab items=\"vm.items\" index=\"vm.tabIndex\"><div class=\"financial-content\"><financial-trade-item type=\"vm.type\" list=\"vm.tradeList\"></financial-trade-item></div></custom-tab><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging><div browsehistory=\"\"></div></div>");
$templateCache.put("app/components/consumerCar/quotedPrice/quotedPrice.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\">报价历史记录</div></div><div class=\"financial-trade\"><table rules=\"rows\"><thead><tr><th width=\"15%\">请求编号</th><th width=\"35%\">名称</th><th width=\"30%\">描述</th><th width=\"20%\">状态</th><th width=\"20%\">操作</th></tr></thead><tbody><tr ng-repeat=\"item in vm.quoteList\" ng-if=\"vm.quoteList\"><td ng-bind=\"item.quoteId\"></td><td ng-bind=\"item.quoteName\"></td><td ng-bind=\"item.description\"></td><td ng-bind=\"item.status\"></td><td><button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.showDetailInfo(item);\"><i class=\"fa fa-fw fa-newspaper-o text-light-blue\"></i> 详情</button></td></tr><tr ng-if=\"vm.quoteList.length==0\"><td colspan=\"4\" style=\"text-align: center\">暂无相关记录</td></tr></tbody></table></div></div>");
$templateCache.put("app/components/consumerCar/quotedPriceDetail/quotedPriceDetail.html","<div class=\"max_inner\"><div class=\"box1\"><header class=\"header\"><h6>销售订单 # <span ng-bind=\"vm.model.quoteId\"></span></h6></header><table class=\"table1\"><tr><td class=\"color\">类型</td><td class=\"right\"><span ng-bind=\"vm.model.quoteType\"></span></td></tr><tr><td class=\"color\">销售渠道</td><td class=\"right\"><span ng-bind=\"vm.model.salesChannel\"></span></td></tr><tr><td class=\"color\">状态</td><td class=\"right\"><span ng-bind=\"vm.model.status\"></span></td></tr><tr><td class=\"color\">客户标识</td><td class=\"right\"><span ng-bind=\"vm.model.partyId\"></span></td></tr><tr><td class=\"color\">报价名</td><td class=\"right\"><span ng-bind=\"vm.model.quoteName\"></span></td></tr><tr><td class=\"color\">描述</td><td class=\"right\"><span ng-bind=\"vm.model.description\"></span></td></tr><tr><td class=\"color\">货币</td><td class=\"right\"><span ng-bind=\"vm.model.currency\"></span></td></tr><tr><td class=\"color\">产品销售网点</td><td class=\"right\"><span ng-bind=\"vm.model.storeName\"></span></td></tr></table></div><div class=\"box2\"><header class=\"header\"><h6>报价角色</h6></header><table class=\"table1\"><tr ng-repeat=\"item in vm.model.quoteRoleList\"><td class=\"color\">报价角色名称</td><td class=\"right\"><span ng-bind=\"item.quoteRoleName\"></span></td></tr></table></div><div class=\"box3\"><header class=\"header\"><h6>订单列表</h6></header><table class=\"tableList\"><thead><tr><th class=\"title\">名称</th><th style=\"text-align:center\">数量</th><th class=\"money\">原价(元)</th><th class=\"money\">调整金额(元)</th><th class=\"money\">小计</th><th class=\"money\">总额</th><th class=\"title\">操作</th></tr></thead><tbody><tr ng-repeat=\"x in vm.model.quoteItemList\"><td class=\"title\"><p ng-bind=\"x.productId + x.internalName\"></p></td><td align=\"center\" ng-bind=\"x.quantity\"></td><td class=\"money\" ng-bind=\"x.quoteUnitPriceMap.quoteUnitPrice|currency:\'¥\'\"></td><td class=\"money\" ng-bind=\"x.totalQuoteItemAdjustmentAmountMap.totalQuoteItemAdjustmentAmount|currency:\'¥\'\"></td><td class=\"money\" ng-bind=\"x.totalQuoteAmountMap.totalQuoteAmount|currency:\'¥\'\"></td><td class=\"money\" ng-bind=\"x.grandTotalQuoteAmountMap.grandTotalQuoteAmount|currency:\'¥\'\"></td><td><button type=\"button\" class=\"btn btn-default\" ng-show=\"vm.model.value\" ng-click=\"vm.addRecordShopCartFun(x);\"><i class=\"glyphicon glyphicon-shopping-cart\"></i> 加入购物车</button> <button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.showDetailInfo(x);\"><i class=\"glyphicon glyphicon-shopping-cart\"></i> 查产品详情</button></td></tr></tbody></table></div></div>");
$templateCache.put("app/components/consumerCar/settlementShoppingCar/settlementShoppingCar.html","<div class=\"main-box\"><div class=\"order-box\"><div class=\"order-title\"><div class=\"title-big\">订单信息</div></div><div class=\"order-list\"><div class=\"order-bar\"><div class=\"order-info\"><span>共计<b ng-bind=\"vm.totalcount\"></b>笔订单</span><span>总价<b ng-bind=\"vm.totalamt|currency:\'¥\'\"></b>元</span></div><div class=\"order-btn\" ng-click=\"vm.orderdetail();\">订单信息 <i class=\"glyphicon glyphicon-chevron-down\"></i></div></div><div class=\"tableList\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr><th class=\"title\">名称</th><th style=\"text-align:center\">数量</th><th class=\"money\">单价(元)</th><th class=\"money\">优惠价格(元)</th><th class=\"money\">小计(元)</th></tr></thead><tbody><tr ng-repeat=\"x in vm.carlist\"><td class=\"title\"><p ng-bind=\"x.productId + x.productName\"></p></td><td align=\"center\" ng-bind=\"x.productAmount\"></td><td class=\"money\"><p ng-bind=\"x.tradeData.displayPrice|currency:\'¥\'\"></p></td><td class=\"money\"><p ng-bind=\"x.tradeData.otherAdjustments|currency:\'¥\'\"></p></td><td class=\"money\"><p ng-bind=\"x.tradeData.displayItemSubTotal|currency:\'¥\'\"></p></td></tr></tbody></table></div></div></div><form class=\"form-horizontal\" role=\"form\" name=\"vm.regForm\" novalidate=\"\" ng-submit=\"vm.paysybmit()\"><div class=\"pay-box\" ng-show=\"showpay.show\"><div class=\"pay-title\"><div class=\"title-big\">地址</div></div><div class=\"pay-list-box\"><div class=\"payAdd-list\"><ul ng-repeat=\"returnList in vm.partyContactMechValueMaps\"><li><div class=\"payment-item online-payment\" id=\"address0000{{$index}}\" name=\"address\" ng-click=\"vm.select(\'address\',\'address0000\'+$index,returnList);\"><b></b><span ng-bind=\"returnList.toName\"></span></div><div class=\"add-list\"><span ng-bind=\"returnList.countryGeoName\"></span><span ng-bind=\"returnList.stateProvinceGeoName\"></span><span ng-bind=\"returnList.city\"></span><span ng-bind=\"returnList.address1\"></span></div></li></ul></div></div><div class=\"pay-title\"><div class=\"title-big\">交付方式</div></div><div class=\"pay-list-box\"><div class=\"payment-list\"><ul ng-repeat=\"returnList in vm.shipMethList\"><li><div class=\"payment-item online-payment\" id=\"shipMeth0000{{$index}}\" name=\"shipMeth\" ng-click=\"vm.select(\'shipMeth\',\'shipMeth0000\'+$index,returnList);\"><b></b><span ng-bind=\"returnList.shipmentMethodName\"></span></div></li></ul></div></div><div class=\"pay-title\"><div class=\"title-big\">支付方式</div></div><div class=\"pay-list-box\"><div class=\"payAdd-list\"><ul ng-repeat=\"return in vm.paymentMethod\"><li><div class=\"payment-item online-payment\" id=\"pay0000{{$index}}\" name=\"pay\" ng-click=\"vm.select(\'pay\',\'pay0000\'+$index,return);\"><b></b><span ng-bind=\"return.paymentMethodTypeId | paymentMethodTypeId\"></span></div><div class=\"add-list\"><span ng-bind=\"return.cardNumber\"></span><span ng-bind=\"return.bankName\"></span><span ng-bind=\"return.custAcctno\"></span></div></li></ul></div><div class=\"pay-info\"><div class=\"ebank-input-box\"><form class=\"form-horizontal ng-pristine ng-invalid ng-invalid-required ng-valid-maxlength ng-valid-custom-pattern\" novalidate=\"\" ng-submit=\"vm.toSetLoginPassword()\" name=\"vm.regForm\"><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>账户密码</label><div class=\"col-sm-4\"><input type=\"password\" class=\"form-control ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required ng-valid-custom-pattern\" id=\"txt_password\" name=\"txt_password\" required=\"\" ng-model=\"vm.model.password\" placeholder=\"\" aria-invalid=\"true\"></div><div class=\"col-sm-4 error ng-active\" ng-messages=\"vm.regForm.txt_moblie.$error\" aria-live=\"assertive\"><span class=\"help-block ng-scope\" ng-message=\"required\">请输入账户密码</span></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary\" ng-click=\"vm.externalProcessOrder();\">确认</button> <button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.paysubreturn();\">返回</button></div></div></form></div></div></div></div></form><div class=\"pay-result-info\" ng-show=\"showpayresult.show\"><div class=\"order-title\"><div class=\"title-big\">支付结果信息</div></div><div class=\"alert alert-success\" role=\"alert\"><strong ng-bind=\"vm.returnMsg\"></strong> 您已成功支付此笔订单(订单流水号：{{vm.txSerialNo}})</div><div class=\"btn-box-center-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.successreturn();\">返回</button></div></div><div browsehistory=\"\"></div></div>");
$templateCache.put("app/components/consumerCar/shoppingCar/shoppingCar.html","<div class=\"main-box\"><div class=\"shopping-car-box\"><div class=\"cart-title\"><div class=\"title-big\">购物车</div><div class=\"link\"><a ui-sref=\"index\">继续购物</a></div></div><div class=\"cart-list\"><div class=\"tableList\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"carTable\"><colgroup><col width=\"10%\"><col width=\"15%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\"><col width=\"5%\"></colgroup><thead><tr><th class=\"choice\"><input type=\"checkbox\" id=\"allisSelectedtableRow\" name=\"allisSelected\" ng-click=\"vm.checkedCart(\'tableHead\',\'allisSelected\');\"> 全选</th><th class=\"title\">名称</th><th style=\"text-align:center\">数量</th><th class=\"money\">单价(元)</th><th class=\"money\">优惠价格(元)</th><th class=\"money\">小计(元)</th><th class=\"tool\">操作</th><th class=\"tool\" ng-hide=\"true\">id</th></tr></thead><tbody ng-repeat=\"list in vm.productDetail\"><tr><td class=\"choice\"><input type=\"checkbox\" name=\"allisSelected\" ng-bind=\"list\" ng-click=\"vm.checkedCart(\'tableRow\',\'allisSelected\');\"><p ng-bind=\"list.productId\" ng-hide=\"true\"></p><p ng-bind=\"list.tradeData.prodCatalogId\" ng-hide=\"true\"></p><p ng-bind=\"list.tradeData.configOptionId\" ng-hide=\"true\"></p><p ng-bind=\"list.tradeData.bankBalance\" ng-hide=\"true\"></p><p ng-bind=\"list.tradeData.requireAmount\" ng-hide=\"true\"></p><p ng-bind=\"list.id\" ng-hide=\"true\"></p></td><td class=\"title\"><p ng-bind=\"list.productId + list.productName\" ng-click=\"vm.shopcarproduct(list)\"></p></td><td align=\"center\"><button ng-click=\"vm.numberchange(list,\'minus\',$event);\" ng-disabled=\"1==list.productAmount||\'Y\'==list.tradeData.requireAmount\">-</button> <input style=\"width:50px\" type=\"text\" ng-value=\"list.productAmount\" ng-disabled=\"\'Y\'==list.tradeData.requireAmount\" ng-blur=\"vm.datanumerchange(list,$event);\"> <button ng-click=\"vm.numberchange(list,\'add\',$event);\" ng-disabled=\"\'Y\'==list.tradeData.requireAmount\">+</button></td><td class=\"money\"><p ng-bind=\"list.tradeData.displayPrice|currency:\'¥\'\"></p></td><td class=\"money\"><p ng-bind=\"list.tradeData.otherAdjustments|currency:\'¥\'\"></p></td><td class=\"money\"><p ng-bind=\"list.tradeData.displayItemSubTotal|currency:\'¥\'\"></p></td><td class=\"tool\"><a ng-click=\"vm.clickdelect($index,list)\">删除</a></td><td class=\"tool\" ng-hide=\"true\" ng-bind=\"list.id\"></td></tr></tbody></table></div></div><div class=\"shopping-car-bar\"><div class=\"bar-delete\"><input type=\"checkbox\" id=\"allisSelectedTableSelect\" name=\"allisSelected\" ng-click=\"vm.checkedCart(\'tableSelect\',\'allisSelected\');\"> <span>全选</span><i ng-click=\"vm.gopayOrDeleteSelectCart(\'delete\');\">删除选中</i></div><div class=\"bar-info\"><span>已选择<b ng-bind=\"length\"></b>个产品</span><span>总价<b ng-bind=\"vm.totalamt|currency:\'¥\'\"></b>元</span></div><button class=\"bar-btn\" ng-click=\"vm.gopayOrDeleteSelectCart(\'gopay\')\">结算</button></div></div><div class=\"pay-box\"><div class=\"pay-title\"><div class=\"title-big\">购物车使用促销</div></div><div class=\"pay-list-box\"><div class=\"payAdd-list\"><ul ng-repeat=\"returnList in vm.cartLinesList\"><li><div>产品：<span ng-bind=\"returnList.productId\"></span> 已使用促销数量：<span ng-bind=\"returnList.promoQuantityUsed\"></span> 数量：<span ng-bind=\"returnList.quantity\"></span> 可使用促销数量：<span ng-bind=\"returnList.promoQuantityAvailable\"></span></div><ul ng-repeat=\"childList in returnList.quantityUsedPerPromoActualList\"><li><div>促销产品：<span ng-bind=\"childList.productName\"></span> 实际使用数量：<span ng-bind=\"childList.actualQuantityUsed\"></span> 促销名称：<span ng-bind=\"childList.promoName\"></span> 促销规则：<span ng-bind=\"childList.promoText\"></span></div></li></ul></li></ul></div></div><div browsehistory=\"\"></div></div></div>");
$templateCache.put("app/components/consumerCar/showQuotedPriceDetail/showQuotedPriceDetail.html","<div class=\"max_inner\" ng-controller=\"showQuotedPriceDetailController as vm\"><div class=\"box1\"><header class=\"header\"><h6>报价项目</h6></header><table class=\"table1\"><tr><td class=\"color\">明细</td><td class=\"right\"><span ng-bind=\"vm.model.quoteItemSeqId\"></span></td></tr><tr><td class=\"color\">产品</td><td class=\"right\"><span ng-bind=\"vm.model.internalName\"></span></td></tr><tr><td class=\"color\">数量</td><td class=\"right\"><span ng-bind=\"vm.model.quantity\"></span></td></tr><tr><td class=\"color\">已选金额</td><td class=\"right\"><span ng-bind=\"vm.model.selectedAmount\"></span></td></tr><tr><td class=\"color\">报价单价</td><td class=\"right\"><span ng-bind=\"vm.model.quoteUnitPriceMap.quoteUnitPrice\"></span></td></tr><tr><td class=\"color\">调整</td><td class=\"right\"><span ng-bind=\"vm.model.totalQuoteItemAdjustmentAmountMap.totalQuoteItemAdjustmentAmount\"></span></td></tr><tr><td class=\"color\">小计</td><td class=\"right\"><span ng-bind=\"vm.model.totalQuoteItemAmountMap.totalQuoteItemAmount\"></span></td></tr></table><table class=\"tableList\"><thead><tr><th width=\"10%\">协议类型</th><th width=\"10%\">协议值</th><th width=\"10%\">协议天数</th><th width=\"10%\">条款描述</th></tr></thead><tbody><tr ng-repeat=\"x in vm.model.quoteTermList\"><td align=\"center\" ng-bind=\"x.termTypeName\"></td><td class=\"money\" ng-bind=\"x.termValue\"></td><td class=\"money\" ng-bind=\"x.termDays\"></td><td class=\"money\" ng-bind=\"x.quoteTermDescription\"></td></tr></tbody></table></div></div>");
$templateCache.put("app/components/consumerCar/tradeOrderDetail/tradeOrderDetail.html","<div ng-controller=\"tradeOrderDetail as vm\"><div class=\"max_inner\"><div class=\"box1\"><header class=\"header\"><h6>销售订单 # <span ng-bind=\"vm.orderDetail.orderInfo.orderId\"></span></h6></header><table class=\"table1\"><tr><td class=\"color\">历史状态</td><td class=\"right\"><div class=\"td\">当前状态:<span ng-bind=\"vm.orderDetail.orderInfo.orderCurrentStatus\"></span></div><span ng-repeat=\"status in vm.orderDetail.orderInfo.orderStatusList\"><span ng-bind=\"status.status\"></span>-<span ng-bind=\"status.statusDatetime\"></span><br></span></td></tr><tr><td class=\"color\">订货日期</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderInfo.orderDate\"></span></td></tr><tr><td class=\"color\">货币</td><td class=\"right\">CNY</td></tr><tr><td class=\"color\">销售渠道</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderInfo.channelName\"></span></td></tr><tr><td class=\"color\">货品仓库</td><td class=\"right\">润和云总行<div class=\"fillet\">(MODEL-FP-OL-STORE)</div></td></tr><tr><td class=\"color\">创建者</td><td class=\"right\"><div class=\"fillet1\"><span ng-bind=\"vm.orderDetail.orderInfo.createdBy\"></span></div></td></tr></table></div><div class=\"box2\"><header class=\"header\"><h6>订单联络信息</h6></header><table class=\"table1\"><tr><td class=\"color\">客户名称</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderContactInfoMap.fullName\"></span></td></tr><tr><td class=\"color\">下订单客户的编号</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderContactInfoMap.partyId\"></span></td></tr><tr><td class=\"color\">货运地址</td><td class=\"right\"><span ng-repeat=\"addstr in vm.orderDetail.orderContactInfoMap.postalAddressList\">收货人 <span ng-bind=\"addstr.toName\"></span> 地址：<span ng-bind=\"addstr.countryName\"></span>-<span ng-bind=\"addstr.stateProvinceName\"></span>-<span ng-bind=\"addstr.city\"></span> - <span ng-bind=\"addstr.address1\"></span><br></span></td></tr></table></div><div class=\"box3\"><header class=\"header\"><h6>订单价格</h6></header><table class=\"table1\"><tr><td class=\"color\">项目小计</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderItemsMap.orderSubTotal\"></span>（元）</td></tr><tr><td class=\"color\">其他调整总计</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderItemsMap.otherAdjAmount\"></span>（元）</td></tr><tr><td class=\"color\">货运及处理总计</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderItemsMap.shippingAmount\"></span>（元）</td></tr><tr><td class=\"color\">销售税总额</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderItemsMap.taxAmount\"></span>（元）</td></tr><tr><td class=\"color\">应付总计</td><td class=\"right\"><span ng-bind=\"vm.orderDetail.orderItemsMap.grandTotal\"></span>（元）</td></tr></table></div><div class=\"box3\"><header class=\"header\"><h6>订单列表</h6></header><table class=\"tableList\"><thead><tr><th class=\"title\">名称</th><th style=\"text-align:center\">数量</th><th class=\"money\">原价(元)</th><th class=\"money\">调整金额(元)</th><th class=\"money\">小计</th><th class=\"title\">操作</th></tr></thead><tbody><tr ng-repeat=\"x in vm.orderDetail.orderItemsMap.orderItemLists\"><td class=\"title\"><p ng-bind=\"x.productMap.productId + x.productMap.productName\" ng-click=\"vm.shopcarproduct(x)\"></p></td><td align=\"center\" ng-bind=\"x.quantityMap.quantity|number\"></td><td class=\"money\" ng-bind=\"x.unitPriceMap.unitPrice|currency:\'¥\'\"></td><td class=\"money\" ng-bind=\"x.adjustMentMap.adjustMent|currency:\'¥\'\"></td><td class=\"money\" ng-bind=\"x.subTotalMap.subTotal|currency:\'¥\'\"></td><td><button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.addRecordShopCartFun(x);\"><i class=\"glyphicon glyphicon-shopping-cart\"></i> 加入购物车</button></td></tr></tbody></table></div></div></div>");
$templateCache.put("app/components/consumerFinancial/productdetails/productdetail.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\" ng-bind=\"vm.productdetitle\"></div></div><div class=\"trading-one-box\"><div class=\"product-detail-box clearfix\"><div class=\"product-detail-img clearfix\"><img src=\"../assets{{vm.imageAdress}}\" alt=\"\"></div><ul class=\"product-detail-info clearfix\"><li class=\"title\" style=\"padding-left:110px\" ng-bind=\"vm.productname\"></li><li><span>产品ID:</span> {{vm.goodsCode}}</li><li><span>价格:</span>{{vm.productpice|currency:\'¥\'}}</li></ul><ul class=\"product-detail-input clearfix\"><li ng-if=\"vm.requireAmount==\'Y\'\"><span class=\"title\">金额：</span><amt-input afield=\"vm.model.bankBalance\"></amt-input><span>元</span></li><li><span class=\"title\"></span> <button type=\"button\" class=\"btn btn-primary btn-lg\" ng-click=\"vm.addcar(vm.model.bankBalance)\">添加购物车</button></li></ul></div></div><div class=\"product-detail-list\"><custom-tab items=\"vm.items\" index=\"vm.tabIndex\"><div class=\"financial-content\"><product-type-manage type=\"vm.type\"></product-type-manage></div><div class=\"col-md-6 col-sm-8\"></div></custom-tab><div class=\"trading-one-box\" ng-show=\"vm.productInfomsg1\"><p><b id=\"textDateID\"></b></p></div><div class=\"trading-one-box\" ng-show=\"vm.productInfomsg2\"><div class=\"pay-list-box\" ng-if=\"vm.productId\"><div class=\"payAdd-list\"><ul ng-repeat=\"(key, value) in vm.configOptionIdMap\"><li><div class=\"payment-item online-payment\" id=\"address0000{{$index}}\" name=\"address\" ng-click=\"vm.selectConfigOptionFun(\'address\',\'address0000\'+$index,key);\"><b></b><span ng-bind=\"value\"></span></div></li></ul></div></div><div class=\"pay-list-box\" ng-if=\"!vm.productId\"><div class=\"payAdd-list\"><ul><li><div class=\"payment-item online-payment\" id=\"address0000{{$index}}\" name=\"address\" ng-click=\"vm.selectConfigOptionFun(\'address\',\'address0000\'+$index,vm.configOptionId);\"><b></b><span ng-bind=\"vm.configOptionName\"></span></div></li></ul></div></div></div><div browsehistory=\"\"></div></div></div>");
$templateCache.put("app/components/consumerLoan/loanApplication/loanApplication.html","<div class=\"main-box\"><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\" ng-model=\"vm.model.title\">个人汽车消费贷款</div><form class=\"form-horizontal ng-pristine ng-valid\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>选择地区</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>请选择贷款服务行</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>贷款金额</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" ng-model=\"vm.model.money\"><p class=\"help-text\">大写：<span>500,000.00元</span></p></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>贷款期限</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>担保方式</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>担保物</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\"></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>收款人姓名</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\"></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary\" ng-click=\"vm.setShopcar()\">添加</button> <button type=\"button\" class=\"btn btn-default\" ui-sref=\"loanService\">关闭</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLoan/loanService/loanService.html","<div><div class=\"banner-transfer\"></div><div class=\"main-box\"><div class=\"product-list-two\"><div class=\"nav-title-bar\"><div class=\"title\">贷款申请</div><div loanhead=\"\" flow=\"1\"></div></div><div class=\"product-list-box\"><div class=\"product-list\" ng-repeat=\"product in vm.productDetail\"><consumer-loandirective product=\"product\" type=\"vm.type\"></consumer-loandirective></div></div><div><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div></div></div></div>");
$templateCache.put("app/components/consumerLoan/loanhead/loanhead.html","<div class=\"nav-title-bar\"><ul class=\"nav nav-tabs\"><li role=\"presentation\" ng-class=\"{\'active\': flow==1,\'\':flow>1}\"><a ui-sref=\"loanService\">贷款申请</a></li><li ng-class=\"{\'active\': flow==2,\'\':flow>2}\"><a ui-sref=\"mayloan\">我的贷款</a></li><li ng-class=\"{\'active\': flow==3,\'\':flow>3}\"><a ui-sref=\"loanService\">贷款申请进度</a></li><li ng-class=\"{\'active\': flow==4,\'\':flow>4}\"><a ui-sref=\"loanpersondata\">个人信息维护</a></li></ul></div>");
$templateCache.put("app/components/consumerLoan/loanpersonData/loanpersonData.html","<div class=\"banner-transfer\"></div><div class=\"main-box\"><div class=\"nav-title-bar\"><div class=\"title\">个人信息维护</div><div loanhead=\"\" flow=\"4\"></div></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><form class=\"form-horizontal ng-pristine ng-valid\"><div class=\"ebank-input-title\">个人资料</div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>选择地区</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>请选择贷款服务行</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>贷款金额</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\"><p class=\"help-text\">大写：<span>500,000.00元</span></p></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>贷款期限</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>担保方式</label><div class=\"col-sm-4\"><select class=\"form-control\"><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option></select></div><div class=\"col-sm-4\"></div></div><div class=\"ebank-input-title\">联系地址</div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>担保物</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\"></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>收款人姓名</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\"></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary\">添加</button> <button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.backLogin();\">关闭</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLoan/myLoan/myLoan.html","<div class=\"banner-transfer\"></div><div class=\"main-box\"><div class=\"product-list-two\"><div class=\"nav-title-bar\"><div class=\"title\">我的贷款</div><div loanhead=\"\" flow=\"2\"></div></div><div class=\"trading-one-box\"><div class=\"ebak-table-list\"><consumer-loandirective product=\"vm.loanList\" type=\"vm.type\"></consumer-loandirective><div class=\"pagination-box clearfix\"><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div></div></div></div></div>");
$templateCache.put("app/components/consumerLoan/myLoan/myLoaninfo.html","<div class=\"banner-transfer\"></div><div class=\"main-box\"><div class=\"product-list-two\"><div class=\"nav-title-bar\"><div class=\"title\">我的贷款</div><div loanhead=\"\" flow=\"2\"></div></div><div class=\"trading-one-box\"><div class=\"ebak-table-list\"><div class=\"ebak-table-title\">贷款信息</div><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"table table-striped table-detail\"><tbody><tr><th class=\"right\">贷款编号：</th><td ng-bind=\"vm.alldata.loannum\"></td><th class=\"right\">贷款品种：</th><td ng-bind=\"vm.alldata.loankind\"></td></tr><tr><th class=\"right\">贷款本金：</th><td ng-bind=\"vm.alldata.loanamount|currency:\'¥\'\"></td><th class=\"right\">贷款余额：</th><td ng-bind=\"vm.alldata.loanblance|currency:\'¥\'\"></td></tr><tr><th class=\"right\">起息日：</th><td ng-bind=\"vm.alldata.loanstart|dateFormat:{symbol:\'yyyy-mm-dd\'}\"></td><th class=\"right\">到期日：</th><td ng-bind=\"vm.alldata.loanend|dateFormat:{symbol:\'yyyy-mm-dd\'}\"></td></tr><tr><th class=\"right\">执行利率(年)：</th><td ng-bind=\"vm.alldata.nowrate\"></td><th class=\"right\">罚息执行利率(年)：</th><td ng-bind=\"vm.alldata.defaultrate\"></td></tr><tr><th class=\"right\">贷款银行：</th><td ng-bind=\"vm.alldata.loanbank\"></td><th class=\"right\">还款方式：</th><td ng-bind=\"vm.alldata.paymentway\"></td></tr><tr><th class=\"right\">还款周期：</th><td ng-bind=\"vm.alldata.paymentcyc\"></td><th class=\"right\">还款日：</th><td ng-bind=\"vm.alldata.paymentday\"></td></tr><tr><th class=\"right\">还款账号：</th><td ng-bind=\"vm.alldata.peymentacount\"></td><th class=\"right\">贷款状态：</th><td ng-bind=\"vm.alldata.loantype\"></td></tr></tbody></table><div class=\"ebak-table-title\">担保信息</div><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"table table-striped table-detail\"><tbody><tr><th class=\"right\">担保方式：</th><td ng-bind=\"vm.alldata.guaranteeway1\">质押</td><th class=\"right\">担保物：</th><td ng-bind=\"vm.alldata.guaranteething1\">房产</td></tr><tr><th class=\"right\">担保方式：</th><td ng-bind=\"vm.alldata.guaranteeway2\">抵押</td><th class=\"right\">担保物：</th><td ng-bind=\"vm.alldata.guaranteething2\">房产</td></tr></tbody></table><div class=\"form-group\"><div class=\"col-sm-offset-5 btn-box-big\"><button type=\"button\" class=\"btn btn-default\" ui-sref=\"mayloan\">关闭</button></div></div></div></div></div></div>");
$templateCache.put("app/components/consumerLoan/myLoan/myLoanquery.html","<div class=\"banner-transfer\"></div><div class=\"main-box\"><div class=\"product-list-two\"><div class=\"nav-title-bar\"><div class=\"title\">我的贷款</div><div loanhead=\"\" flow=\"2\"></div></div><div class=\"trading-one-box\"><nav class=\"navbar navbar-default\"><form class=\"navbar-form navbar-left\" role=\"search\"><span>日期范围</span><div class=\"form-group\"><div style=\"float:left\"><ui-calendar time=\"vm.startDate\"></ui-calendar></div><div style=\"float:left\"><ui-calendar time=\"vm.endDate\"></ui-calendar></div></div><button type=\"submit\" class=\"btn btn-default\" ng-click=\"vm.startserch()\">开始查询</button></form></nav><div class=\"ebak-table-list\"><div class=\"ebak-table-title\">查询结果</div><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"table table-striped\"><thead><tr><th class=\"center\">扣款日</th><th class=\"center\">应还本息合计</th><th class=\"right\">应还本金</th><th class=\"center\">应还利息</th><th class=\"center\">剩余本金</th></tr></thead><tbody ng-repeat=\"list in vm.loanqueryList\"><tr><td class=\"center\" ng-bind=\"list.paydate|dateFormat:{symbol:\'yyyy-mm-dd\'}\"></td><td class=\"center\" ng-bind=\"list.payamt|currency:\'¥\'\"></td><td class=\"center\" ng-bind=\"list.payamo|currency:\'¥\'\"></td><td class=\"center\" ng-bind=\"list.payrate|currency:\'¥\'\"></td><td class=\"center\" ng-bind=\"list.allloanamount|currency:\'¥\'\"></td></tr></tbody></table><div class=\"pagination-box clearfix\"><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div><div class=\"form-group\"><div class=\"col-sm-offset-5 btn-box-big\"><button type=\"button\" class=\"btn btn-default\" ui-sref=\"mayloan\">关闭</button></div></div></div></div></div></div>");
$templateCache.put("app/components/consumerLogin/login/login.html","<script src=\"login.controller.js\"></script><div class=\"banner-login\"><div class=\"login-box\"><div class=\"login\"><div class=\"title\">网上银行登录</div><form role=\"form\" name=\"vm.loginForm\" novalidate=\"\"><div class=\"alert alert-danger\" role=\"alert\" ng-if=\"errorMsg\" ng-bind=\"errorMsg\"></div><input class=\"input\" type=\"label\" id=\"inp_mobileNo\" name=\"inp_mobileNo\" maxlength=\"11\" required=\"\" ng-model=\"vm.model.mobileNo\" placeholder=\"登录名或卡号\"> <input class=\"input\" type=\"password\" id=\"input_password\" name=\"input_password\" required=\"\" ng-model=\"vm.model.password\" placeholder=\"登录密码\"><div style=\"visibility:{{vm.isNeedShowValidateCode ? \'visible\' : \'hidden\'}}\"><input class=\"input-small\" type=\"text\" id=\"input_small\" name=\"input_small\" maxlength=\"4\" required=\"\" ng-model=\"vm.model.small\" placeholder=\"验证码\"> <span class=\"refresh-img\"><img ng-src=\"../assets/img/login/a5c8.png\" alt=\"\"></span> <a href=\"javascript:;\" class=\"refresh\" ng-click=\"vm.requestNewValidateCode()\">刷新</a></div><button type=\"submit\" class=\"btn btn-primary btn-lg\" ng-click=\"vm.login();\">登录</button></form><div class=\"text-link clearfix\"><a ui-sref=\"forgetPassword\">忘记密码</a> <span>|</span> <a ui-sref=\"selfRegistration\">自助注册</a></div></div></div></div>");
$templateCache.put("app/components/consumerTransfer/transferDefaultInput/bankList.html","<div class=\"bank-list-box\" ng-controller=\"bankListController as vm\"><div class=\"bank-title\">选择开户行</div><ul class=\"nav nav-tabs\"><li role=\"presentation\" class=\"active\"><a href=\"#\">热门银行</a></li><li role=\"presentation\"><a href=\"#\">ABCDE</a></li><li role=\"presentation\"><a href=\"#\">FGHI</a></li><li role=\"presentation\"><a href=\"#\">JKLMN</a></li><li role=\"presentation\"><a href=\"#\">TWXYZ</a></li></ul><div class=\"bank-list clearfix\"><div ng-repeat=\"bank in vm.bankList | limitTo: vm.showLimit\"><ul><li ng-click=\"vm.selectBank(bank.bankCode)\"><span ng-bind=\"bank.bankName\"></span></li></ul></div></div><div class=\"bank-list clearfix\" ng-if=\"vm.showMore\"><span class=\"show-more-btn\" ng-click=\"vm.showMore()\">显示更多</span></div></div>");
$templateCache.put("app/components/consumerTransfer/transferDefaultInput/bankListSearch.html","<div class=\"bank-address-box\" ng-controller=\"bankListSearchController as vm\"><div class=\"address-title\">中国工商银行</div><div class=\"address-search clearfix\"><div class=\"keyword\">关键字查询 例如 北京 崇文门</div><div class=\"input-group\"><input type=\"text\" class=\"form-control\" placeholder=\"\"> <span class=\"input-group-btn\"><button class=\"btn btn-default\" type=\"button\">搜索</button></span></div></div><div class=\"address-list\"><table class=\"table table-striped\"><tbody><tr ng-repeat=\"bank in vm.bankList\"><th scope=\"row\"><i class=\"glyphicon glyphicon-map-marker\"></i></th><td><span ng-bind=\"bank.bankCode\"></span><p>行号</p></td><td ng-bind=\"bank.bankName\"></td><td><span ng-click=\"vm.selectBank(bank.bankCode)\">选择</span></td></tr></tbody></table></div><div class=\"pagination-box\"><ul class=\"pagination\"><li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li><li class=\"active\"><a href=\"#\">1 <span class=\"sr-only\">(current)</span></a></li><li><a href=\"#\">2</a></li><li><a href=\"#\">3</a></li><li><a href=\"#\">4</a></li><li><a href=\"#\">5</a></li><li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li></ul></div></div>");
$templateCache.put("app/components/consumerTransfer/transferDefaultInput/contactsList.html","<div class=\"contacts-list-box\" ng-controller=\"contactsListController as vm\"><div class=\"contacts-title\">选择联系人</div><div class=\"contacts-search clearfix\"><div class=\"keyword\">关键字查询 例如 北京 崇文门</div><div class=\"input-group\"><input type=\"text\" class=\"form-control\" placeholder=\"\"> <span class=\"input-group-btn\"><button class=\"btn btn-default\" type=\"button\">搜索</button></span></div></div><div class=\"contacts-list\"><table class=\"table table-striped\"><tbody><tr ng-repeat=\"contacts in vm.contactsList\"><th scope=\"row\"><i class=\"glyphicon glyphicon-user\"></i></th><td><span ng-bind=\"contacts.customerName\"></span></td><td><span ng-bind=\"contacts.aliasName\"></span></td><td><span ng-bind=\"contacts.accountNo\"></span></td><td><span ng-bind=\"contacts.bankLogoUrl\"></span></td><td><span ng-click=\"vm.selectContacts(contacts.bankCode)\">选择</span></td></tr></tbody></table></div><div class=\"pagination-box\"><ul class=\"pagination\"><li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li><li class=\"active\"><a href=\"#\">1 <span class=\"sr-only\">(current)</span></a></li><li><a href=\"#\">2</a></li><li><a href=\"#\">3</a></li><li><a href=\"#\">4</a></li><li><a href=\"#\">5</a></li><li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li></ul></div></div>");
$templateCache.put("app/components/consumerTransfer/transferDefaultInput/transferDefaultInput.html","<div class=\"main-box\"><div transferdefaultstate=\"\" flow=\"1\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">填写账户信息</div><form class=\"form-horizontal\" novalidate=\"\" ng-submit=\"vm.totransferDefaultService()\" name=\"vm.transForm\"><div class=\"form-group\"><label for=\"slt_provCode\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 开户省市:</label><div class=\"col-sm-4\"><select required=\"\" class=\"col-sm-4 form-control\" name=\"slt_provCode\" ng-model=\"address.province\" ng-options=\"key as key for (key,value) in division\" ng-change=\"address.city=\'\';address.district=\'\';\"><option value=\"\">请选择开户省</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.slt_provCode.$error\"><span class=\"help-block\" ng-message=\"required\">请选择开户省市</span></div></div><div class=\"form-group\"><label for=\"slt_cityCode\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 开户市:</label><div class=\"col-sm-4\"><select class=\"col-sm-4 form-control\" name=\"slt_cityCode\" ng-model=\"address.city\" ng-options=\"key as key for (key,value) in division[address.province]\" ng-change=\"address.district=\'\';\" required=\"\"><option value=\"\">请选择开户市</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.slt_cityCode.$error\"><span class=\"help-block\" ng-message=\"required\">请选择开户市</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 付款账号</label><div class=\"col-sm-4\"><select class=\"form-control\" ng-model=\"vm.model.payaccount\" required=\"\" id=\"txt_payaccount\" name=\"txt_payaccount\" placeholder=\"请选择付款账号\"><option value=\"\">请选择付款账号</option><option value=\"11111111111\">11111111111</option><option value=\"22222222222\">2222222</option><option value=\"333333333\">33333333</option><option value=\"44444444\">4444444</option><option value=\"55555555\">555555</option></select><p class=\"help-text\">账户余额： <span>500,000.00元</span></p></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_payaccount.$error\"><span class=\"help-block\" ng-message=\"required\">请选择付款账号</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 转账金额</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" maxlength=\"13\" required=\"\" id=\"txt_payamount\" name=\"txt_payamount\" ng-model=\"vm.model.payamount\" placeholder=\"请输入您的转账金额\"><p class=\"help-text\"><span ng-bind=\"vm.model.payamount|ChinaCost\"></span></p></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_payamount.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的转账金额</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 收款账号</label><div class=\"col-sm-4\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" required=\"\" ng-model=\"vm.model.collectaccount\" id=\"txt_collectaccount\" name=\"txt_collectaccount\" placeholder=\"请选择收款账号\"> <span key=\"6\" class=\"input-group-btn\"><button class=\"btn btn-info\" type=\"button\" ng-click=\"vm.contactsList();\">选择</button></span></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_collectaccount.$error\"><span class=\"help-block\" ng-message=\"required\">请选择收款账号</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 收款人姓名</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" id=\"txt_custName\" name=\"txt_custName\" ng-model=\"vm.model.custName\" placeholder=\"请输入收款人姓名\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_custName.$error\"><span class=\"help-block\" ng-message=\"required\">请输入收款人姓名</span> <span class=\"help-block\" ng-message=\"customPattern\">姓名为2至6位中文</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 收款人开户行</label><div class=\"col-sm-4\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" ng-disabled=\"\" required=\"\" ng-model=\"vm.model.payaccountbank\" id=\"txt_payaccountbank\" name=\"txt_payaccountbank\" placeholder=\"请选择收款人开户行\"> <span key=\"6\" class=\"input-group-btn\"><button class=\"btn btn-info\" type=\"button\" ng-click=\"vm.bankList();\">选择</button></span></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_payaccountbank.$error\"><span class=\"help-block\" ng-message=\"required\">请选择收款人开户行</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 开户网点</label><div class=\"col-sm-4\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" required=\"\" ng-model=\"vm.model.bankNo\" id=\"txt_bankNo\" name=\"txt_bankNo\" placeholder=\"请选择开户网点\"> <span key=\"6\" class=\"input-group-btn\"><button class=\"btn btn-info\" type=\"button\" ng-click=\"vm.bankListSearch();\">选择</button></span></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_bankNo.$error\"><span class=\"help-block\" ng-message=\"required\">请选择开户网点</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 短信通知手机号</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" maxlength=\"11\" ng-model=\"vm.model.mobile\" id=\"txt_mobile\" name=\"txt_mobile\" placeholder=\"请输入短信通知手机号\" custom-pattern=\"phone\"><div class=\"checkbox\"><label><input type=\"checkbox\" ng-model=\"vm.model.mobliesign\" value=\"\">是否短信通知</label></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_mobile.$error\"><span class=\"help-block\" ng-message=\"required\">请输入短信通知手机号</span> <span class=\"help-block\" ng-message=\"customPattern\">短信通知手机号格式不正确</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 转账用途</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" ng-model=\"vm.model.transferuse\" id=\"txt_trandsferuse\" name=\"txt_trandsferuse\" placeholder=\"请输入转账用途\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_trandsferuse.$error\"><span class=\"help-block\" ng-message=\"required\">请输入转账用途</span></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backTranfer();\">返回</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerTransfer/transferDefaultService/transferDefaultService.html","w<div class=\"main-box\"><div transferdefaultstate=\"\" flow=\"2\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">确认转账信息</div><form class=\"form-horizontal\" novalidate=\"\" ng-submit=\"vm.totransferDefaultSucc();\" name=\"vm.transForm\"><div class=\"transfer-confirm-info\"><div class=\"transfer-info clearfix\"><div class=\"info-ico\"><i class=\"ico ico-pay\">付</i></div><ul class=\"info-list clearfix\"><li>付款人： <span>张小明</span></li><li>付款账号： <span ng-bind=\"(vm.model.payaccount| splitNumberFilter)\"></span></li><li>付款用途： <span ng-bind=\"vm.model.transferuse\"></span></li></ul></div><div class=\"transfer-info transfer-info-money clearfix\"><div class=\"info-ico\"><i class=\"ico ico-money\">￥</i></div><ul class=\"info-list clearfix\"><li>转账金额：<b><span ng-bind=\"(vm.model.payamount | number:2)\"></span></b> 元</li><li>手续费：<b>1.00</b> 元</li></ul></div><div class=\"transfer-info transfer-info clearfix\"><div class=\"info-ico\"><i class=\"ico\">收</i></div><ul class=\"info-list clearfix\"><li>收款人： <span ng-bind=\"vm.model.custName\"></span></li><li>收款账号： <span ng-bind=\"(vm.model.collectaccount|splitNumberFilter)\" <=\"\" span=\"\"></span></li><li>手机号： <span ng-bind=\"vm.model.mobile\"></span></li></ul></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 安全认证工具</label><div class=\"col-sm-4\" id=\"inlineCheckbox\"><label class=\"radio-inline\"><input type=\"radio\" required=\"\" name=\"rdo_securityType\" value=\"rdo_securityType1\" id=\"rdo_securityType\" ng-model=\"vm.model.securityType\">短信认证</label> <label class=\"radio-inline\"><input type=\"radio\" required=\"\" name=\"rdo_securityType\" value=\"rdo_securityType2\" id=\"rdo_securityType\" ng-model=\"vm.model.securityType\">USB Key</label> <label class=\"radio-inline\"><input type=\"radio\" required=\"\" name=\"rdo_securityType\" value=\"rdo_securityType3\" id=\"rdo_securityType\" ng-model=\"vm.model.securityType\">安全令牌</label></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.rdo_securityType.$error\"><span class=\"help-block\" ng-message=\"required\">请选择安全认证工具</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 获取短信验证码手机号</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" maxlength=\"11\" required=\"\" ng-model=\"vm.model.mobileNo\" id=\"txt_mobileNo\" name=\"txt_mobileNo\" placeholder=\"请输入短信通知手机号\" custom-pattern=\"phone\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_mobileNo.$error\"><span class=\"help-block\" ng-message=\"required\">请输入手机号</span> <span class=\"help-block\" ng-message=\"customPattern\">手机号格式不正确</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 短信认证码</label><div class=\"col-sm-4\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" id=\"txt_mobliecode\" name=\"txt_mobliecode\" ng-model=\"vm.model.mobliecode\" required=\"\"> <span key=\"6\" class=\"input-group-btn\"><button class=\"btn btn-info\" ng-click=\"vm.getMobileValidateCode()\" type=\"button\" ng-disabled=\"!(vm.transForm.rdo_securityType.$valid && vm.transForm.txt_mobileNo.$valid)\">获取短信认证码</button></span></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_mobliecode.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的短信确认码</span></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 交易密码</label><div class=\"col-sm-4\"><input type=\"password\" class=\"form-control\" required=\"\" id=\"txt_cardpassword\" name=\"txt_cardpassword\" ng-model=\"vm.model.cardpassword\" placeholder=\"请输入您的交易密码\" keyboard=\"\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.transForm.txt_cardpassword.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的交易密码</span></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\" ng-disabled=\"!(vm.validateCode)\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backTransferDefaultInput();\">返回</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerTransfer/transferDefaultState/transferDefaultState.html","<div class=\"titile-step\"><div class=\"title\">转账汇款</div><div class=\"step\"><span ng-class=\"{\'current\': flow==1,\'\':flow>1}\"><i>1</i>填写转账信息</span> <span ng-class=\"{\'current\': flow==2,\'\':flow>2}\"><i>2</i>确认转账信息</span> <span ng-class=\"{\'current\': flow==3,\'\':flow>3}\"><i>3</i>查看转账结果</span></div></div>");
$templateCache.put("app/components/consumerTransfer/transferDefaultSucc/transferDefaultSucc.html","<div class=\"main-box\"><div transferdefaultstate=\"\" flow=\"3\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">查看转账结果</div><div class=\"alert alert-success\" role=\"alert\"><strong>您的转账汇款交易已成功！</strong><div class=\"alert-text\"><span ng-bind=\"vm.model.custName\"></span> ，你好，您已汇款成功，汇款金额 <span ng-bind=\"(vm.model.payamount | number:2)\"></span>元</div></div><div class=\"btn-box-center-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ui-sref=\"accountManagement\">返回</button></div></div></div></div>");
$templateCache.put("app/components/consumerTransfer/transferService/discoverPromotionsList.html","<div><div class=\"main-box\" ng-if=\"vm.discoverPromotionsListFlag==\'0\'\"><div class=\"nav-title-bar\"><div class=\"title\">促销明细：</div></div><div class=\"bar\"><span ng-bind=\"vm.promoText\"></span></div><div class=\"nav-title-bar\"><div class=\"title\">用于促销的产品列表</div></div><div><div class=\"transfer-list-two\"><div class=\"transfer-list-box\"><div class=\"transfer-list\" ng-repeat=\"product in vm.productIdList\"><transfer-product compare=\"vm.addcar(product);\" product=\"product\"></transfer-product></div></div><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div></div><div browsehistory=\"\"></div></div><div class=\"main-box\" ng-if=\"vm.discoverPromotionsListFlag==\'1\'\"><div class=\"nav-title-bar\"><div class=\"title\">推荐产品列表</div></div><div><div class=\"transfer-list-two\"><div class=\"transfer-list-box\"><div class=\"transfer-list\" ng-repeat=\"product in vm.productIdListPage\"><transfer-product compare=\"vm.addcar(product);\" product=\"product\"></transfer-product></div></div><paging class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div></div></div></div>");
$templateCache.put("app/components/consumerTransfer/transferService/transferService.html","<div><div class=\"banner-transfer\"></div><div class=\"main-box\"><div class=\"nav-title-bar\"><div class=\"title\">产品列表</div></div><div><div class=\"transfer-list-two\"><div class=\"transfer-list-box\"><div class=\"transfer-list\" ng-repeat=\"product in vm.product\" ng-if=\"product.saleFalg == 0\"><transfer-product compare=\"vm.addcar(product);\" product=\"product\"></transfer-product></div></div></div><paging ng-if=\"vm.product.length > 0\" class=\"small\" page=\"vm.page.currentPage\" page-size=\"vm.page.pageSize\" total=\"vm.page.total\" adjacent=\"{{vm.page.adjacent}}\" dots=\"{{vm.page.dots}}\" scroll-top=\"false\" hide-if-empty=\"true\" ul-class=\"pagination\" active-class=\"active\" disabled-class=\"disabled\" show-prev-next=\"true\" paging-action=\"vm.DoCtrlPagingAct(\'Paging Clicked\', page, pageSize, total)\"></paging></div><div browsehistory=\"\"></div></div></div>");
$templateCache.put("app/components/consumerTransfer/transferService/transferServiceOpen.html","<div class=\"transfer-wind-input\"><div class=\"transfer-wind-title\">加入购物车</div><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>付款账号</label><div class=\"col-sm-5\"><select class=\"form-control\"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select><p class=\"help-text\">账户余额：<span>500,000.00元</span></p></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>转账金额</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\"><p class=\"help-text\">大写：<span>500,000.00元</span></p></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>收款账号</label><div class=\"col-sm-5\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\"> <span key=\"6\" class=\"input-group-btn\"><button class=\"btn btn-info\" type=\"button\">选择</button></span></div></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>收款人姓名</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\"></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>开户行</label><div class=\"col-sm-5\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\"> <span key=\"6\" class=\"input-group-btn\"><button class=\"btn btn-info\" type=\"button\">选择</button></span></div></div><div class=\"col-sm-5\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>短信通知</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\"><div class=\"checkbox\"><label><input type=\"checkbox\" value=\"\">是否短信通知</label></div></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>转账用途</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\"></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>安全认证工具</label><div class=\"col-sm-5\"><label class=\"checkbox-inline\"><input type=\"checkbox\" id=\"inlineCheckbox1\" value=\"option1\"> 短信认证</label> <label class=\"checkbox-inline\"><input type=\"checkbox\" id=\"inlineCheckbox2\" value=\"option2\"> USB Key</label> <label class=\"checkbox-inline\"><input type=\"checkbox\" id=\"inlineCheckbox3\" value=\"option3\"> 安全令牌</label></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>短信认证码</label><div class=\"col-sm-5\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\"> <span key=\"6\" class=\"input-group-btn\"><button class=\"btn btn-info\" type=\"button\">获取短信认证码</button></span></div></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-3 control-label\"><span class=\"red\">*</span>交易密码</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\"></div><div class=\"col-sm-4\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-3 col-sm-9\"><button type=\"submit\" class=\"btn btn-primary\" ng-click=\"vm.addcar()\">加入购物车</button></div></div></form></div></div>");
$templateCache.put("app/components/templatePage/fixedClown/fixedClown.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\" ng-bind=\"vm.productdetail.goodsName\"></div></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">请输入产品调查要素</div><form class=\"form-horizontal\" ng-submit=\"vm.addRecordShopCart()\" name=\"vm.regForm\"><div ng-repeat=\"list in vm.enumQuestionList\"><div ng-if=\"list.enumTypeId.length==0\"><div class=\"form-group\" ng-if=\"list.question.indexOf(\'密码\')<0\"><label for=\"txt_custAccount\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> <span ng-bind=\"list.question\" style=\"color: #231b1b\"></span></label><div class=\"col-sm-4\"><input type=\"text\" required=\"\" class=\"form-control\" id=\"txt_custAccount\" name=\"list.descption\" ng-model=\"vm.model.descption[$index]\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_custAccount.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的<span ng-bind=\"list.question\"></span></span></div></div><div class=\"form-group\" ng-if=\"list.question.indexOf(\'密码\')>0\"><label for=\"txt_cardpassword\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> <span ng-bind=\"list.question\" style=\"color: #231b1b\"></span></label><div class=\"col-sm-4\"><input type=\"password\" class=\"form-control\" required=\"\" id=\"txt_cardpassword\" name=\"txt_cardpassword\" keyboard=\"\" ng-model=\"vm.model.descption[$index]\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_cardpassword.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的<span ng-bind=\"list.question\"></span></span></div></div></div><div ng-if=\"list.enumTypeId.length!=0\"><div class=\"form-group\"><label for=\"txt_answers\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> <span style=\"color: #231b1b\" ng-bind=\"list.question\"></span></label><div class=\"col-sm-4\"><select class=\"form-control\" required=\"\" id=\"txt_answers\" name=\"txt_answers\" ng-model=\"vm.model.descption[$index]\" ng-change=\"vm.selectChange();\"><option ng-repeat=\"qlist in list.enumList\" value=\"{{qlist.enumId}}\">{{qlist.descption}}</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_answers.$error\"><span class=\"help-block\" ng-message=\"required\">请选择 <span ng-bind=\"list.question\"></span></span></div></div></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">提交</button></div></div></form></div></div></div>");
$templateCache.put("app/directives/consumerLoan/loanService/loanServicedir.html","<div class=\"product-list\"><div class=\"list\"><div class=\"img\" ui-sref=\"productdetail({productCode:product.financialcode})\"><img src=\"{{product.producturl}}\" alt=\"\"></div><ul class=\"product-info\"><li class=\"product-title\" ng-bind=\"product.productname\" ui-sref=\"productdetail({productCode:product.financialcode})\"></li><li class=\"product-tag\" ng-repeat=\"producttag in product.producttags\"><span ng-bind=\"producttag.producttag\"></span></li><li>贷款利率：<b class=\"big-font\" ng-bind=\"product.productrate\"></b><span>%</span></li><li class=\"product-but\"><button type=\"button\" class=\"btn btn-primary\" ui-sref=\"loanapplication({productname:product.productname})\">加入购物车</button></li></ul></div></div>");
$templateCache.put("app/directives/consumerLoan/myLoan/myLoandir.html","<table width=\"100%\" class=\"table table-striped\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr><th class=\"center\">贷款编号</th><th class=\"center\">贷款品种</th><th class=\"center\">放款日</th><th class=\"right\">到期日</th><th class=\"right\">贷款金额</th><th class=\"center\">贷款状态</th><th class=\"center\">操作</th></tr></thead><tbody ng-repeat=\"product in product\"><tr><td><a ui-sref=\"myloaninfo({loannum:product.loannum})\" ng-bind=\"product.loannum\"></a></td><td ng-bind=\"product.loantype\"></td><td class=\"center\" ng-bind=\"product.loanstart|dateFormat:{symbol:\'yyyy-mm-dd\'}\"></td><td class=\"center\" ng-bind=\"product.loanend|dateFormat:{symbol:\'yyyy-mm-dd\'}\"></td><td class=\"right\" ng-bind=\"product.loanamt|currency:\'¥\'\"></td><td class=\"center\" ng-bind=\"product.loanflag\"></td><td class=\"center\"><a ui-sref=\"myloanquery({loannum:product.loannum,loanstart:product.loanstart,loanend:product.loanend})\">还款计划表</a> <a ui-sref=\"myloanquery({loannum:product.loannum})\">还款历史</a> <a ui-sref=\"myloanquery({loannum:product.loannum})\">逾期明细</a></td></tr></tbody></table>");
$templateCache.put("app/directives/myOrder/completedorderitem/completedorderitem.html","<div class=\"financial-trade-item\"><div class=\"financial-trade\"><table rules=\"rows\"><thead><tr><th width=\"10%\">订单名称</th><th width=\"15%\">订单数量</th><th width=\"15%\">订单金额</th><th width=\"10%\">订单状态</th><th width=\"15%\">订单日期</th><th width=\"20%\">操作</th></tr></thead><tbody><tr ng-repeat=\"item in list\" ng-if=\"list\"><td ng-bind=\"item.orderTypeName\"></td><td ng-bind=\"item.totalOrderItemsQuantity|number\"></td><td ng-bind=\"item.grandTotalMap.grandTotal|currency:\'¥\'\"></td><td ng-bind=\"item.statusName\"></td><td ng-bind=\"item.orderDate\"></td><td><button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.showDetailInfo(item.partyId,item.orderId);\"><i class=\"fa fa-fw fa-newspaper-o text-light-blue\"></i> 详情</button></td></tr><tr ng-if=\"list.length==0\"><td colspan=\"4\" style=\"text-align: center\">暂无相关记录</td></tr></tbody></table></div></div>");
$templateCache.put("app/directives/myOrder/notcompletedorderitem/notcompletedorderitem.html","<div class=\"financial-trade-item\"><div class=\"financial-trade\"><table rules=\"rows\"><thead><tr><th width=\"10%\">订单名称</th><th width=\"15%\">订单数量</th><th width=\"15%\">订单金额</th><th width=\"10%\">订单状态</th><th width=\"15%\">订单日期</th><th width=\"20%\">操作</th></tr></thead><tbody><tr ng-repeat=\"item in list\" ng-if=\"list\"><td ng-bind=\"item.orderTypeName\"></td><td ng-bind=\"item.totalOrderItemsQuantity|number\"></td><td ng-bind=\"item.grandTotalMap.grandTotal|currency:\'¥\'\"></td><td ng-bind=\"item.statusName\"></td><td ng-bind=\"item.orderDate\"></td><td><button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.showDetailInfo(item.partyId,item.orderId);\"><i class=\"fa fa-fw fa-newspaper-o text-light-blue\"></i> 详情</button></td></tr><tr ng-if=\"list.length==0\"><td colspan=\"4\" style=\"text-align: center\">暂无相关记录</td></tr></tbody></table></div></div>");
$templateCache.put("app/components/consumerLogin/forgetPassword/forgetPassword.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\">密码重置</div><div class=\"step\"><span class=\"current\"><i>1</i>验证身份信息认证</span><span><i>2</i>设置新密码</span><span><i>3</i>密码设置结果信息</span></div></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">验证身份信息认证</div><form class=\"form-horizontal\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">账号/卡号</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>卡号类型</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"11\">借记卡</option><option value=\"12\">活期一本通</option><option value=\"13\">定期一本通</option><option value=\"14\">普通存折</option></select></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>账号/卡号密码</label><div class=\"col-sm-8\"><input type=\"password\" class=\"form-control\" id=\"exampleInputPassword1\" placeholder=\"\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>客户姓名</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件类型</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"1\">居民身份证</option><option value=\"2\">临时身份证</option><option value=\"3\">军官证</option><option value=\"4\">武警证</option><option value=\"5\">士兵证</option><option value=\"6\">文职干部证</option><option value=\"7\">外国护照</option><option value=\"8\">香港通行证</option><option value=\"9\">澳门通行证</option><option value=\"10\">台湾通行证</option><option value=\"11\">其他证件</option></select></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件号码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>联系电话</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>E-mail地址</label><div class=\"col-sm-8\"><input type=\"email\" class=\"form-control\" id=\"inputEmail3\" placeholder=\"Email\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\">返回</button></div></div></form></div><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">设置新密码</div><form class=\"form-horizontal\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">网银登录密码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">重新输入网银登录密码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">客户昵称</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>私密问题</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"1\">请选择私密问题</option><option value=\"2\">您父亲的生日是？</option><option value=\"3\">您母亲的生日是？</option></select></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">私密问题答案</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\">上一步</button></div></div></form></div><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"ebank-input-title\">密码重置结果信息</div><div class=\"alert alert-success\" role=\"alert\"><strong>密码找回成功!</strong> 系统5秒后将会自动跳转到登录页面，如果没有自动跳转，请点击登录。</div><div class=\"form-group\"><div class=\"col-sm-offset-5 col-sm-6 btn-box-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ui-sref=\"login\">返回登录</button></div></div></form></div></div></div>");
$templateCache.put("app/directives/commonDiretives/amtInput/amtInput.html","<input type=\"txt\" class=\"form-control\" ng-keyup=\"vm.keyUpAmt();\" ng-blur=\"vm.blurAmtInput();\" ng-model=\"afield\">");
$templateCache.put("app/directives/commonDiretives/calendar/calendar.html","<p class=\"input-group\"><input type=\"text\" class=\"form-control\" datepicker-popup=\"{{vm.format}}\" ng-model=\"time\" is-open=\"vm.status.opened\" min-date=\"vm.minDate\" max-date=\"vm.maxDate\" datepicker-options=\"vm.dateOptions\" ng-required=\"true\"> <span class=\"input-group-btn\"><button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.open($event)\" style=\"\"><i class=\"glyphicon glyphicon-calendar\"></i></button></span></p>");
$templateCache.put("app/directives/shoppingCar/shoppingcarson/shoppingCardir.html","<td colspan=\"4\" class=\"td-np\"><div class=\"discount-box\"><div class=\"discount-bar\"><div class=\"discount-title\">优惠组合</div><div class=\"discount-close glyphicon glyphicon-remove-sign\"></div></div><div class=\"discount-list\"><ul class=\"product-list\" ng-repeat=\"productCarList in list\"><li><a href=\"\"><img src=\"{{productCarList.producturl}}\" alt=\"\"></a></li><li><b ng-bind=\"productCarList.productname\"></b></li><li>收益率：<strong ng-bind=\"productCarList.productrate\"></strong><b>%</b></li><li>起售金额:<strong ng-bind=\"productCarList.productrate|currency:\'￥\'\"></strong></li><li class=\"li-fp\"><input type=\"checkbox\"> <input type=\"text\" class=\"form-control\"> 元</li></ul><ul class=\"product-info\"><li><b>优惠信息</b></li><li class=\"product-line\"><span>总价:</span><i>1500.00元</i></li><li class=\"product-line\"><span>优惠价:</span><strong>1000.00元</strong></li><li><span>节省:</span><i>500.00元</i></li><li><i><button type=\"button\" class=\"btn btn-primary\">确认购买</button></i></li></ul></div></div></td>");
$templateCache.put("app/components/consumerLogin/selfRegistration/selfRegistration.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\">自助注册</div><div class=\"step\"><span class=\"current\"><i>1</i></span>电子银行服务协议<span><i>2</i>填写账户信息</span><span><i>3</i>设置密码</span><span><i>4</i>注册完成</span></div></div><div class=\"trading-one-box\"><div><div class=\"other-agreement\"><h2>个人客户电子银行服务协议</h2><div class=\"content\"><p>尊敬的客户，为了维护您的权益，请在签署本协议前，仔细阅读本协议条款内容。 甲方：电子银行个人用户 乙方：盛京银行股份有限公司 为明确双方的权利和义务，规范双方业务行为，甲、乙双方本着平等互利的原则，就盛京银行电子银行服务相关事宜达成本协议并共同遵守。</p><p><b>第一条</b></p><p>如无特别说明，下列用语在本协议中的含义为： 电子银行业务：指乙方利用面向社会公众开放的通讯通道或开放型公众网络，以及乙方特定自助服务设施或为甲方建立的专用网络，向甲方提供账户管理、投资理财、贷款融资、汇款支付等离柜金融服务。根据渠道不同，乙方电子银行可分为网上银行、手机银行（包括手机登陆、Pad登陆等，下同）、电视银行、电话银行、自助银行（包括自动取款机、自动存取款机、查询缴费机等自助设备，下同）等。 身份认证要素：指甲方向乙方申请的或甲方自行设置的、用于乙方识别甲方身份的信息要素，如客户号、登录名、账号、密码、数字证书、动态口令、签约设置的电话或手机号码等。 数字证书：指存放甲方身份标识，用以在交易中识别甲方身份以及保障交易安全的、对甲方发送的电子银行交易信息进行数字签名的电子文件。乙方数字证书的存放介质为USBKEY。 电子交易指令：指甲方通过乙方电子银行凭借身份认证要素发起的查询、转账、支付结算等交易请求的统称。 密码：指由甲方自行设定或按照一定规则随机生成的，用于校验甲方身份的字符信息，分为静态密码和动态密码两种。静态密码是由甲方自行设定的密码，包括取款密码、查询密码、登录密码、支付密码等；动态密码为交易中乙方按照一定规则随机生成的用于识别甲方身份发送的交易验证码。</p><p><b>第二条</b></p><p>服务内容 一、网上银行服务内容 甲方通过乙方网上银行、柜面或其他途径签约并办理相关手续后，即成为乙方网上银行签约客户，根据客户签约级别不同，可享受包括账户管理、信息查询、汇款支付、公共事业缴费等服务，以及申请开通或变更网上银行所提供的服务功能。 二、手机银行服务内容 甲方通过乙方手机银行、柜面或其他途径签约并办理相关手续后，即成为乙方手机银行签约客户，根据客户签约级别不同，可享受包括账户管理、信息查询、汇款支付、公共事业缴费等服务，以及申请开通或变更手机银行所提供的服务功能。 三、电视银行服务内容 甲方通过乙方电视银行、柜面或其他途径签约并办理相关手续后，即成为电视银行的签约客户，可享受包括账户管理、信息查询、公共事业缴费等服务，以及申请开通或变更电视银行所提供的服务功能。 四、电话银行服务内容 甲方通过乙方柜面或其他途径办理银行卡、活期一本通后，即可享受电话银行账务查询、转账服务、自助缴费、凭证挂失、修改密码、传真服务等服务，同时客户可享受全方位、周到的电话银行人工服务。 五、自助银行服务内容 甲方通过乙方柜面或其他途径办理银行卡、活期一本通后，即可享受自助银行信息查询、现金存取、公共事业缴费等服务；通过乙方柜面或其他途径签约自助设备转账等协议并办理相关手续后，即可享受自助银行转账汇款等服务。</p><p><b>第三条</b></p><p>甲方权利、义务</p><p>一、权利</p><p>（一）甲方自愿向乙方申请开通或签约办理电子银行业务，经乙方同意后，将有权在遵守国家法律法规和乙方相关业务规则的前提下，根据注册方式的不同，要求乙方提供相关银行服务。</p><p>（二）甲方有权选择申请电子银行业务种类，有权申请开通电子银行余额查询、明细查询、自助转账等功能，并可在乙方规定的最高限额内设定对外转账限额，具体标准以乙方公告为准。</p><p>（三）甲方在电子银行服务有效期内有权办理电子银行服务注销或解约手续。</p><p>（四）甲方对乙方电子银行服务如有疑问、建议或意见时，可拨打客服电话4006996666、登录乙方官方网站或到乙方营业网点进行咨询或投诉。</p><p>二、义务</p><p>（一）甲方办理电子银行业务，应遵守本协议、《盛京银行电子银行服务章程》和乙方相关业务规则的规定。 （二）甲方前往乙方柜面办理电子银行业务的开通申请（如需）、维护（含关联或变更等）、注销等手续的，应本人亲自办理。按甲方要求提供相关资料、填写申请表，并亲自签名确认。甲方向乙方提供的业务申请表是本协议不可分割的组成部分。甲方应保证所填写的申请表和所提供的资料为本人的真实、准确、完整、合法、有效的资料，对于因甲方提供信息不真实、不完整、不准确、无效或非本人资料所造成的风险及损失由甲方自行承担。甲方为未成年人时，应由其监护人代为申请，提供有效监护关系证明，办理电子银行业务。</p></div></div><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8\"><div class=\"checkbox\"><label><input type=\"checkbox\"> <span class=\"font_b_18\">同意《个人银行服务协议》</span></label></div></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">阅读并同意以上客户协议，确认！</button></div></div></form></div></div><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">填写账户信息</div><form class=\"form-horizontal\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">账号/卡号</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>卡号类型</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"11\">借记卡</option><option value=\"12\">活期一本通</option><option value=\"13\">定期一本通</option><option value=\"14\">普通存折</option></select></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>账号/卡号密码</label><div class=\"col-sm-8\"><input type=\"password\" class=\"form-control\" id=\"exampleInputPassword1\" placeholder=\"\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>客户姓名</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件类型</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"1\">居民身份证</option><option value=\"2\">临时身份证</option><option value=\"3\">军官证</option><option value=\"4\">武警证</option><option value=\"5\">士兵证</option><option value=\"6\">文职干部证</option><option value=\"7\">外国护照</option><option value=\"8\">香港通行证</option><option value=\"9\">澳门通行证</option><option value=\"10\">台湾通行证</option><option value=\"11\">其他证件</option></select></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件号码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>联系电话</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>E-mail地址</label><div class=\"col-sm-8\"><input type=\"email\" class=\"form-control\" id=\"inputEmail3\" placeholder=\"Email\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8\"><div class=\"checkbox\"><label><input type=\"checkbox\"> <a href=\"#\">个人电子银行服务协议</a></label></div></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\">步一步</button></div></div></form></div><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">设置密码</div><form class=\"form-horizontal\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">网银登录密码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">重新输入网银登录密码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">客户昵称</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>私密问题</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"1\">请选择私密问题</option><option value=\"2\">您父亲的生日是？</option><option value=\"3\">您母亲的生日是？</option></select></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">私密问题答案</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\">上一步</button></div></div></form></div><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"ebank-input-title\">注册结果信息</div><div class=\"alert alert-success\" role=\"alert\"><strong>注册成功!</strong> 系统5秒后将会自动跳转到登录页面，如果没有自动跳转，请点击登录。</div><div class=\"form-group\"><div class=\"col-sm-offset-5 col-sm-6 btn-box-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ui-sref=\"login\">返回登录</button></div></div></form></div></div></div>");
$templateCache.put("app/directives/commonDiretives/tabs/tab.html","<div class=\"tabs\"><div class=\"custome-tab\"><div class=\"custome-tab-item displayIB\" ng-class=\"{\'selected\':$index == index, \'unselected\': $index != index}\" ng-click=\"select($index)\" ng-repeat=\"item in items\"><span ng-bind=\"item.value\"></span></div></div><div class=\"custome-tab-body\"><div ng-transclude=\"\"></div></div></div>");
$templateCache.put("app/directives/commonDiretives/passwordStrength/passwordStrength.html","<div class=\"pswStrengthContainer\"><div class=\"pswStrengthTip\">密码强度:</div><div class=\"pswStrength\"><div ng-class=\"weakClass\"></div><div ng-class=\"mediumClass\"></div><div ng-class=\"strongClass\"></div></div><div class=\"pswStr\">{{strengthStr}}</div></div>");
$templateCache.put("app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html","<div tabindex=\"-1\" role=\"dialog\" class=\"modal\" ng-class=\"{in: animate}\" ng-style=\"{\'z-index\': 1050 + index*10, display: \'block\'}\"><div class=\"modal-dialog\" ng-class=\"{\'modal-sm\': size == \'sm\', \'modal-lg\': size == \'lg\'}\"><div class=\"modal-content\"><div class=\"modal-header\" ng-controller=\"ModalTemplateController as vm\"><a class=\"close\" ng-click=\"vm.close()\"><i></i></a></div><div class=\"modal-body\" modal-transclude=\"\"></div></div></div></div>");
$templateCache.put("app/layout/modalBlock/registerAgreementModal/registerAgreement.html","<div class=\"page-form\"><div class=\"trading-one-box\"><div class=\"other-agreement\"><h2>个人客户电子银行服务协议</h2><div class=\"content\"><p>尊敬的客户，为了维护您的权益，请在签署本协议前，仔细阅读本协议条款内容。 甲方：电子银行个人用户 乙方：盛京银行股份有限公司 为明确双方的权利和义务，规范双方业务行为，甲、乙双方本着平等互利的原则，就盛京银行电子银行服务相关事宜达成本协议并共同遵守。</p><p><b>第一条</b></p><p>如无特别说明，下列用语在本协议中的含义为： 电子银行业务：指乙方利用面向社会公众开放的通讯通道或开放型公众网络，以及乙方特定自助服务设施或为甲方建立的专用网络，向甲方提供账户管理、投资理财、贷款融资、汇款支付等离柜金融服务。根据渠道不同，乙方电子银行可分为网上银行、手机银行（包括手机登陆、Pad登陆等，下同）、电视银行、电话银行、自助银行（包括自动取款机、自动存取款机、查询缴费机等自助设备，下同）等。 身份认证要素：指甲方向乙方申请的或甲方自行设置的、用于乙方识别甲方身份的信息要素，如客户号、登录名、账号、密码、数字证书、动态口令、签约设置的电话或手机号码等。 数字证书：指存放甲方身份标识，用以在交易中识别甲方身份以及保障交易安全的、对甲方发送的电子银行交易信息进行数字签名的电子文件。乙方数字证书的存放介质为USBKEY。 电子交易指令：指甲方通过乙方电子银行凭借身份认证要素发起的查询、转账、支付结算等交易请求的统称。 密码：指由甲方自行设定或按照一定规则随机生成的，用于校验甲方身份的字符信息，分为静态密码和动态密码两种。静态密码是由甲方自行设定的密码，包括取款密码、查询密码、登录密码、支付密码等；动态密码为交易中乙方按照一定规则随机生成的用于识别甲方身份发送的交易验证码。</p><p><b>第二条</b></p><p>服务内容 一、网上银行服务内容 甲方通过乙方网上银行、柜面或其他途径签约并办理相关手续后，即成为乙方网上银行签约客户，根据客户签约级别不同，可享受包括账户管理、信息查询、汇款支付、公共事业缴费等服务，以及申请开通或变更网上银行所提供的服务功能。 二、手机银行服务内容 甲方通过乙方手机银行、柜面或其他途径签约并办理相关手续后，即成为乙方手机银行签约客户，根据客户签约级别不同，可享受包括账户管理、信息查询、汇款支付、公共事业缴费等服务，以及申请开通或变更手机银行所提供的服务功能。 三、电视银行服务内容 甲方通过乙方电视银行、柜面或其他途径签约并办理相关手续后，即成为电视银行的签约客户，可享受包括账户管理、信息查询、公共事业缴费等服务，以及申请开通或变更电视银行所提供的服务功能。 四、电话银行服务内容 甲方通过乙方柜面或其他途径办理银行卡、活期一本通后，即可享受电话银行账务查询、转账服务、自助缴费、凭证挂失、修改密码、传真服务等服务，同时客户可享受全方位、周到的电话银行人工服务。 五、自助银行服务内容 甲方通过乙方柜面或其他途径办理银行卡、活期一本通后，即可享受自助银行信息查询、现金存取、公共事业缴费等服务；通过乙方柜面或其他途径签约自助设备转账等协议并办理相关手续后，即可享受自助银行转账汇款等服务。</p><p><b>第三条</b></p><p>甲方权利、义务</p><p>一、权利</p><p>（一）甲方自愿向乙方申请开通或签约办理电子银行业务，经乙方同意后，将有权在遵守国家法律法规和乙方相关业务规则的前提下，根据注册方式的不同，要求乙方提供相关银行服务。</p><p>（二）甲方有权选择申请电子银行业务种类，有权申请开通电子银行余额查询、明细查询、自助转账等功能，并可在乙方规定的最高限额内设定对外转账限额，具体标准以乙方公告为准。</p><p>（三）甲方在电子银行服务有效期内有权办理电子银行服务注销或解约手续。</p><p>（四）甲方对乙方电子银行服务如有疑问、建议或意见时，可拨打客服电话4006996666、登录乙方官方网站或到乙方营业网点进行咨询或投诉。</p><p>二、义务</p><p>（一）甲方办理电子银行业务，应遵守本协议、《盛京银行电子银行服务章程》和乙方相关业务规则的规定。 （二）甲方前往乙方柜面办理电子银行业务的开通申请（如需）、维护（含关联或变更等）、注销等手续的，应本人亲自办理。按甲方要求提供相关资料、填写申请表，并亲自签名确认。甲方向乙方提供的业务申请表是本协议不可分割的组成部分。甲方应保证所填写的申请表和所提供的资料为本人的真实、准确、完整、合法、有效的资料，对于因甲方提供信息不真实、不完整、不准确、无效或非本人资料所造成的风险及损失由甲方自行承担。甲方为未成年人时，应由其监护人代为申请，提供有效监护关系证明，办理电子银行业务。</p></div></div></div></div>");
$templateCache.put("app/layout/modalBlock/browseHistory/browseHistory.html","<div class=\"product-list-two\"><div class=\"recommend-box\" ng-controller=\"browseHistoryController as vm\"><div class=\"title-bar\"><div class=\"title\">推荐产品</div><div class=\"bar\" ng-if=\"vm.recommendMore==\'0\'\"><a href=\"\" ng-click=\"vm.recommendMorePro();\">更多</a></div></div><div class=\"product-list-box\"><div class=\"product-list\" ng-repeat=\"product in vm.homeShowProductIdList\"><transfer-product compare=\"vm.addcar(product);\" product=\"product\"></transfer-product></div></div></div></div>");
$templateCache.put("app/directives/commonDiretives/softKeyboard/softkeyboard.html","<div class=\"softKeyboard\" ng-style=\"style\" ng-show=\"isShowKeyboard\"><div class=\"keyboardHeader clearfix\"><ul><li class=\"pull-left\" ng-hide=\"onlydigit\"><button type=\"button\" ng-click=\"closeKeyboard()\">切换到键盘</button></li><li class=\"pull-right\" ng-hide=\"onlydigit\"><button type=\"button\" ng-click=\"setCapsLock()\">切换大小写</button></li><li class=\"pull-right\" ng-show=\"onlydigit\"><button type=\"button\" class=\"operation\" ng-click=\"closeKeyboard()\">确定</button></li><li class=\"pull-right\" ng-show=\"onlydigit\"><button type=\"button\" class=\"delete\" ng-click=\"deleteKey()\"><span class=\"fa fa-arrow-left\"></span></button></li></ul></div><div class=\"keyboardBody\"><ul class=\"clearfix\"><li><button type=\"button\" ng-click=\"addKey(keyNumArray[0])\">{{keyNumArray[0]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[1])\">{{keyNumArray[1]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[2])\">{{keyNumArray[2]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[3])\">{{keyNumArray[3]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[4])\">{{keyNumArray[4]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[5])\">{{keyNumArray[5]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[6])\">{{keyNumArray[6]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[7])\">{{keyNumArray[7]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[8])\">{{keyNumArray[8]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyNumArray[9])\">{{keyNumArray[9]}}</button></li></ul><div ng-hide=\"onlydigit\"><ul class=\"clearfix\"><li><button type=\"button\" ng-click=\"addKey(keyCharArray[0])\">{{keyCharArray[0]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[1])\">{{keyCharArray[1]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[2])\">{{keyCharArray[2]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[3])\">{{keyCharArray[3]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[4])\">{{keyCharArray[4]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[5])\">{{keyCharArray[5]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[6])\">{{keyCharArray[6]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[7])\">{{keyCharArray[7]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[8])\">{{keyCharArray[8]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[9])\">{{keyCharArray[9]}}</button></li></ul><ul class=\"clearfix\"><li><button type=\"button\" ng-click=\"addKey(keyCharArray[10])\">{{keyCharArray[10]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[11])\">{{keyCharArray[11]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[12])\">{{keyCharArray[12]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[13])\">{{keyCharArray[13]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[14])\">{{keyCharArray[14]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[15])\">{{keyCharArray[15]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[16])\">{{keyCharArray[16]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[17])\">{{keyCharArray[17]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[18])\">{{keyCharArray[18]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[19])\">{{keyCharArray[19]}}</button></li></ul><ul class=\"clearfix\"><li><button type=\"button\" ng-click=\"addKey(keyCharArray[20])\">{{keyCharArray[20]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[21])\">{{keyCharArray[21]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[22])\">{{keyCharArray[22]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[23])\">{{keyCharArray[23]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[24])\">{{keyCharArray[24]}}</button></li><li><button type=\"button\" ng-click=\"addKey(keyCharArray[25])\">{{keyCharArray[25]}}</button></li><li><button type=\"button\" class=\"delete\" ng-click=\"deleteKey()\"><span class=\"fa fa-arrow-left\"></span></button></li><li><button type=\"button\" class=\"operation\" ng-click=\"closeKeyboard()\">确认</button></li></ul></div></div></div>");
$templateCache.put("app/directives/shoppingCar/shoppingcarson2/shoppingCardir2.html","<div class=\"discount-box\" ng-controller=\"shoppingcardirController as vm\"><div class=\"discount-bar\"><div class=\"discount-title\">优惠组合</div><div class=\"discount-close glyphicon glyphicon-remove-sign\" ng-click=\"vm.closetable()\"></div></div><div class=\"discount-list\" ng-show=\"tjdetail.show\"><ul class=\"product-list\" ng-repeat=\"productCarList in list\"><li><a href=\"\"><img src=\"{{productCarList.producturl}}\" alt=\"\" ui-sref=\"productdetail({productCode:productCarList.productCode})\"></a></li><li><b ng-bind=\"productCarList.productname\" ui-sref=\"productdetail({productCode:productCarList.productCode})\"></b></li><li>收益率：<strong ng-bind=\"productCarList.productrate\"></strong><b>%</b></li><li>起售金额:<strong ng-bind=\"productCarList.productrate|currency:\'￥\'\"></strong></li><li class=\"li-fp\"><table><tr><td><input type=\"checkbox\" id=\"{{productCarList.productCode}}1\" ng-click=\"vm.checkboxclick(this)\"></td><td><input type=\"text\" class=\"form-control\" id=\"{{productCarList.productCode}}\"> 元</td></tr></table></li></ul><ul class=\"product-info\"><li><b>优惠信息</b></li><li class=\"product-line\"><span>总价:</span><i ng-bind=\"vm.allpice|currency:\'￥\'\"></i></li><li class=\"product-line\"><span>优惠价:</span><strong ng-bind=\"vm.allpiceyh|currency:\'￥\'\"></strong></li><li><span>节省:</span><i ng-bind=\"vm.allpicejs|currency:\'￥\'\"></i></li><li><i><button type=\"button\" class=\"btn btn-primary\" ng-click=\"vm.addpaycarlist()\">确认购买</button></i></li></ul></div></div>");
$templateCache.put("app/components/consumerAccount/fixedTransfer/confirm/fixedTransferConfirm.html","<div class=\"main-box\"><div rfixedtransfertitle=\"\" flow=\"2\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">确认信息</div><div class=\"alert alert-danger\" role=\"alert\" ng-if=\"errorMsg\" ng-bind=\"errorMsg\"></div><form class=\"form-horizontal\" role=\"form\" name=\"vm.regForm\" novalidate=\"\" ng-submit=\"vm.fixedTranserSussess()\"><div class=\"form-group\"><label for=\"txt_cardNumberType\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>产品名称</label><div class=\"col-sm-4\"><select class=\"form-control\" required=\"\" name=\"txt_cardNumberType\" id=\"txt_cardNumberType\" ng-model=\"vm.model.cardNumberType\"><option value=\"\" selected=\"\">三个月期人民币整存整取</option><option value=\"12\">六个月期人民币整存整取</option><option value=\"13\">一年期人民币整存整取</option><option value=\"14\">两年期人民币整存整取</option><option value=\"14\">三年期人民币整存整取</option><option value=\"14\">五年期人民币整存整取</option></select></div></div><div class=\"form-group\"><label for=\"txt_cardPwd\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>起存金额</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"\" name=\"\" ng-disabled=\"true\"></div></div><div class=\"form-group\"><label for=\"txt_custName\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>挂牌利率</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"\" name=\"\" ng-disabled=\"true\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>转出卡(账)号</label><div class=\"col-sm-4\"><select class=\"form-control\" id=\"txt_idType\" name=\"txt_idType\" required=\"\" ng-model=\"vm.model.idType\"><option value=\"\">6229828601000201602</option></select></div></div><div class=\"form-group\"><label for=\"txt_iphone\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>转存金额</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" id=\"\" name=\"\"></div></div><div class=\"form-group\"><label for=\"txt_iphone\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>交易密码</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" id=\"\" name=\"\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backLogin();\">返回</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerAccount/fixedTransfer/input/fixedTransfer.html","<div class=\"main-box\"><div rfixedtransfertitle=\"\" flow=\"1\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">填写账户信息</div><div class=\"alert alert-danger\" role=\"alert\" ng-if=\"errorMsg\" ng-bind=\"errorMsg\"></div><form class=\"form-horizontal\" role=\"form\" name=\"vm.regForm\" novalidate=\"\" ng-submit=\"vm.fixedTranserConfirm()\"><div class=\"form-group\"><label for=\"txt_cardNumberType\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>产品名称</label><div class=\"col-sm-4\"><select class=\"form-control\" required=\"\" name=\"txt_cardNumberType\" id=\"txt_cardNumberType\" ng-model=\"vm.model.cardNumberType\"><option value=\"\" selected=\"\">三个月期人民币整存整取</option><option value=\"12\">六个月期人民币整存整取</option><option value=\"13\">一年期人民币整存整取</option><option value=\"14\">两年期人民币整存整取</option><option value=\"14\">三年期人民币整存整取</option><option value=\"14\">五年期人民币整存整取</option></select></div></div><div class=\"form-group\"><label for=\"txt_cardPwd\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>起存金额</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"\" name=\"\" ng-disabled=\"true\"></div></div><div class=\"form-group\"><label for=\"txt_custName\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>挂牌利率</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"\" name=\"\" ng-disabled=\"true\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>转出卡(账)号</label><div class=\"col-sm-4\"><select class=\"form-control\" id=\"txt_idType\" name=\"txt_idType\" required=\"\" ng-model=\"vm.model.idType\"><option value=\"\">6229828601000201602</option></select></div></div><div class=\"form-group\"><label for=\"txt_iphone\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>转存金额</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" id=\"\" name=\"\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backLogin();\">返回</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerAccount/fixedTransfer/success/fixedTransferSuccess.html","<div class=\"main-box\"><div rfixedtransfertitle=\"\" flow=\"3\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"ebank-input-title\">活转定结果信息</div><div class=\"alert alert-success\" role=\"alert\"><strong>交易成功!</strong> 系统5秒后将会自动跳转到登录页面，如果没有自动跳转，请点击登录。</div><div class=\"form-group\"><div class=\"col-sm-offset-5 col-sm-6 btn-box-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backLogin();\">返回登录</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerAccount/fixedTransfer/title/fixedTransferTitle.html","<div class=\"titile-step\"><div class=\"title\">活转定</div><div class=\"step\"><span ng-class=\"{\'current\': flow==1,\'\':flow>1}\"><i>1</i>填写账户信息</span> <span ng-class=\"{\'current\': flow==2,\'\':flow>2}\"><i>2</i>确认信息</span> <span ng-class=\"{\'current\': flow==3,\'\':flow>3}\"><i>3</i>交易完成</span></div></div>");
$templateCache.put("app/components/consumerLogin/forgetPassword/forgetPasswordState/forgetPasswordState.html","<div class=\"titile-step\"><div class=\"title\">密码重置</div><div class=\"step\"><span ng-class=\"{\'current\': flow==1,\'\':flow>1}\"><i>1</i>验证身份信息认证</span> <span ng-class=\"{\'current\': flow==2,\'\':flow>2}\"><i>2</i>设置新密码</span> <span ng-class=\"{\'current\': flow==3,\'\':flow>3}\"><i>3</i>密码设置结果信息</span></div></div>");
$templateCache.put("app/components/consumerLogin/forgetPassword/setPassword/setPassword.html","<div class=\"main-box\"><div forgetpasswordstate=\"\" flow=\"2\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">设置新密码</div><form class=\"form-horizontal\" novalidate=\"\" ng-submit=\"vm.tosetPasswordSucc()\" name=\"vm.regForm\"><div class=\"form-group\"><label for=\"txt_entryPswd\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 设置登录密码:</label><div class=\"col-sm-4\"><div class=\"input-group\"><input type=\"password\" required=\"\" class=\"form-control\" ng-model=\"vm.model.entryPswd\" id=\"txt_entryPswd\" name=\"txt_entryPswd\" minlength=\"1\" maxlength=\"20\" placeholder=\"请输入您的登录密码\"><div class=\"input-group-addon\"><pwd-eye for=\"txt_entryPswd\"></pwd-eye></div></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_entryPswd.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的登录密码</span> <span class=\"help-block\" ng-message=\"minlength\">密码长度需为8至20位</span></div></div><div class=\"form-group\" ng-show=\"vm.regForm.txt_entryPswd.$viewValue\"><div class=\"col-sm-offset-4 col-sm-4\"><div passwordstrength=\"\" pwd=\"vm.model.entryPswd\"></div></div></div><div class=\"form-group\"><label for=\"txt_reentryPswd\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>重新输入网银登录密码</label><div class=\"col-sm-4\"><div class=\"input-group\"><input type=\"text\" style=\"display: none;\" ng-model=\"vm.model.entryPswd\" id=\"pw1\" name=\"pw1\"> <input type=\"password\" required=\"\" class=\"form-control\" ng-model=\"vm.model.reentryPswd\" id=\"txt_reentryPswd\" name=\"txt_reentryPswd\" minlength=\"1\" maxlength=\"20\" placeholder=\"请输入您的登录密码\" pw-check=\"pw1\"><div class=\"input-group-addon\"><pwd-eye for=\"txt_reentryPswd\"></pwd-eye></div></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_reentryPswd.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的登录密码</span><span class=\"help-block\" ng-message=\"pwmatch\">密码不一致</span></div></div><div class=\"form-group\"><label for=\"txt_name\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>客户昵称</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"txt_name\" name=\"txt_name\" ng-model=\"vm.model.name\" placeholder=\"请输入客户昵称\" required=\"\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_name.$error\"><span class=\"help-block\" ng-message=\"required\">请输入客户昵称</span></div></div><div class=\"form-group\"><label for=\"txt_question\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>私密问题</label><div class=\"col-sm-4\"><select class=\"form-control\" ng-model=\"vm.model.question\" required=\"\" id=\"txt_question\" name=\"txt_question\"><option value=\"1\">请选择私密问题</option><option value=\"2\">您父亲的生日是？</option><option value=\"3\">您母亲的生日是？</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_question.$error\"><span class=\"help-block\" ng-message=\"required\">请选择私密问题</span></div></div><div class=\"form-group\"><label for=\"txt_answer\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>私密问题答案</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"txt_answer\" name=\"txt_answer\" required=\"\" ng-model=\"vm.model.answer\" placeholder=\"请入私密问题答案\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_answer.$error\"><span class=\"help-block\" ng-message=\"required\">请入私密问题答案</span></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" ng-click=\"vm.goBack()\" class=\"btn btn-default btn-lg\">上一步</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLogin/forgetPassword/setPasswordSucc/setPasswordSucc.html","<div class=\"main-box\"><div forgetpasswordstate=\"\" flow=\"3\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"ebank-input-title\">密码重置结果信息</div><div class=\"alert alert-success\" role=\"alert\"><strong><span ng-bind=\"vm.model.custName\"></span>,先生,您好！密码找回成功!</strong> 系统5秒后将会自动跳转到登录页面，如果没有自动跳转，请点击登录。</div><div class=\"form-group\"><div class=\"col-sm-offset-5 col-sm-6 btn-box-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backLogin();\">返回登录</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLogin/selfRegistration/getAccountInfo/getAccountInfo.html","<div class=\"main-box\"><div registtielt=\"\" flow=\"3\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"ebank-input-title\">注册结果信息</div><div class=\"alert alert-success\" role=\"alert\"><strong><span ng-bind=\"vm.model.custName\"></span>,先生,您好！您的网银已注册成功!</strong> 系统5秒后将会自动跳转到登录页面，如果没有自动跳转，请点击登录。</div><div class=\"form-group\"><div class=\"col-sm-offset-5 col-sm-6 btn-box-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backLogin();\">返回登录</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLogin/selfRegistration/registAccountInfor/registAccountInfor.html","<div class=\"main-box\"><div registtielt=\"\" flow=\"1\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">填写账户信息</div><div class=\"alert alert-danger\" role=\"alert\" ng-if=\"errorMsg\" ng-bind=\"errorMsg\"></div><form class=\"form-horizontal\" role=\"form\" name=\"vm.regForm\" novalidate=\"\" ng-submit=\"vm.register()\"><div class=\"form-group\"><label for=\"txt_accountNo\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>账号/卡号</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"txt_accountNo\" required=\"\" name=\"txt_accountNo\" custom-pattern=\"account\" ng-model=\"vm.model.accountNo\" high-light=\"vm.regForm.txt_accountNo.$viewValue\" maxlength=\"19\" placeholder=\"请输入您的账号/卡号\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_accountNo.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的账号/卡号</span> <span class=\"help-block\" ng-message=\"customPattern\">账号/卡号格式不正确</span></div></div><div class=\"form-group\"><label for=\"txt_cardNumberType\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>卡号类型</label><div class=\"col-sm-4\"><select class=\"form-control\" required=\"\" name=\"txt_cardNumberType\" id=\"txt_cardNumberType\" ng-model=\"vm.model.cardNumberType\"><option value=\"11\">借记卡</option><option value=\"12\">活期一本通</option><option value=\"13\">定期一本通</option><option value=\"14\">普通存折</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_cardNumberType.$error\"><span class=\"help-block\" ng-message=\"required\">请选择卡号类型</span></div></div><div class=\"form-group\"><label for=\"txt_cardPwd\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>账号/卡号密码</label><div class=\"col-sm-4\"><input type=\"password\" class=\"form-control\" required=\"\" id=\"txt_cardPwd\" name=\"txt_cardPwd\" ng-model=\"vm.model.cardPwd\" keyboard=\"\" maxlength=\"6\" placeholder=\"请输入您的卡号密码\"></div><div class=\"col-md-4 error\" ng-messages=\"vm.regForm.txt_cardPwd.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的卡号密码</span></div></div><div class=\"form-group\"><label for=\"txt_custName\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>客户姓名</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" id=\"txt_custName\" name=\"txt_custName\" ng-model=\"vm.model.custName\" high-light=\"vm.regForm.txt_custName.$viewValue\" placeholder=\"请输入您的客户姓名\"></div><div class=\"col-md-4 error\" ng-messages=\"vm.regForm.txt_custName.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的客户姓名</span></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件类型</label><div class=\"col-sm-4\"><select class=\"form-control\" id=\"txt_idType\" name=\"txt_idType\" required=\"\" ng-model=\"vm.model.idType\"><option value=\"1\">居民身份证</option><option value=\"2\">临时身份证</option><option value=\"3\">军官证</option><option value=\"4\">武警证</option><option value=\"5\">士兵证</option><option value=\"6\">文职干部证</option><option value=\"7\">外国护照</option><option value=\"8\">香港通行证</option><option value=\"9\">澳门通行证</option><option value=\"10\">台湾通行证</option><option value=\"11\">其他证件</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_idType.$error\"><span class=\"help-block\" ng-message=\"required\">请选择证件类型</span></div></div><div class=\"form-group\"><label for=\"txt_certNo\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件号码</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"txt_certNo\" name=\"txt_certNo\" custom-pattern=\"identification\" required=\"\" ng-model=\"vm.model.certNo\" high-light=\"vm.regForm.txt_certNo.$viewValue\" maxlength=\"18\" placeholder=\"请输入您的证件号码\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_certNo.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的证件号码</span> <span class=\"help-block\" ng-message=\"customPattern\">证件格式不正确</span></div></div><div class=\"form-group\"><label for=\"txt_iphone\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>联系电话</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" id=\"txt_iphone\" name=\"txt_iphone\" custom-pattern=\"phone\" ng-model=\"vm.model.iphone\" high-light=\"vm.regForm.txt_iphone.$viewValue\" maxlength=\"11\" placeholder=\"请输入您的联系电话\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_iphone.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的联系电话</span> <span class=\"help-block\" ng-message=\"customPattern\">联系电话格式不正确</span></div></div><div class=\"form-group\"><label for=\"txt_email\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>E-mail地址</label><div class=\"col-sm-4\"><input type=\"email\" class=\"form-control\" id=\"txt_email\" name=\"txt_email\" required=\"\" ng-model=\"vm.model.email\" custom-pattern=\"email\" high-light=\"vm.regForm.txt_email.$viewValue\" placeholder=\"请输入您的E-mail地址\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_email.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的E-mail地址</span> <span class=\"help-block\" ng-message=\"customPattern\">E-mail地址格式不正确</span></div></div><div class=\"form-group\"><label for=\"txt_email\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>文件类型</label><div class=\"col-sm-4\"><input type=\"file\" ngf-select=\"\" ng-model=\"vm.regForm.file\"></div><div class=\"progress progress-striped active\"><div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" ng-style=\"progressPercentageStyle\"><span ng-style=\"sr-only\"><p ng-bind=\"progressPercentagesrOnly\"></p></span></div></div></div><div class=\"form-group\"><label for=\"txt_email\" class=\"col-sm-4 control-label\"></label><div class=\"col-sm-4\"><div class=\"checkbox\"><label><input type=\"checkbox\" ng-show=\"true\" required=\"\" id=\"chk_deal\" name=\"chk_deal\" ng-model=\"vm.model.checkContract\"> <span class=\"grayColor\">我已阅读并同意</span> <a href=\"javascript:void(0);\" ng-click=\"vm.showAgreement();\">《个人电子银行服务协议》</a></label></div></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.chk_deal.$error\"><span class=\"help-block\" ng-message=\"required\">请勾选我已阅读并同意协议</span></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backLogin();\">返回</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLogin/selfRegistration/registTitile/registTitle.html","<div class=\"titile-step\"><div class=\"title\">自助注册</div><div class=\"step\"><span ng-class=\"{\'current\': flow==1,\'\':flow>1}\"><i>1</i>填写账户信息</span> <span ng-class=\"{\'current\': flow==2,\'\':flow>2}\"><i>2</i>设置密码</span> <span ng-class=\"{\'current\': flow==3,\'\':flow>3}\"><i>3</i>注册完成</span></div></div>");
$templateCache.put("app/components/consumerLogin/selfRegistration/setAccountInfoPwd/setAccountInfoPwd.html","<div class=\"main-box\"><div registtielt=\"\" flow=\"2\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">设置密码</div><form class=\"form-horizontal\" role=\"form\" name=\"vm.setPwdForm\" novalidate=\"\" ng-submit=\"vm.nextGetAccountInfoPage();\"><div class=\"form-group\"><label for=\"txt_entryPswd\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>网银登录密码</label><div class=\"col-sm-4\"><div class=\"input-group\"><input type=\"password\" class=\"form-control\" id=\"txt_entryPswd\" name=\"txt_entryPswd\" required=\"\" minlength=\"8\" maxlength=\"20\" ng-model=\"vm.model.entryPswd\" placeholder=\"请输入您的网银登录密码\"><div class=\"input-group-addon\"><pwd-eye for=\"txt_entryPswd\"></pwd-eye></div></div></div><div class=\"col-md-4 error\" ng-messages=\"vm.setPwdForm.txt_entryPswd.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的登录密码</span> <span class=\"help-block\" ng-message=\"minlength\">密码长度需为8至20位</span></div></div><div class=\"form-group\" ng-show=\"vm.setPwdForm.txt_entryPswd.$viewValue\"><div class=\"col-md-offset-4 col-md-4 col-xs-4 col-sm-4\"><div passwordstrength=\"\" pwd=\"vm.model.entryPswd\"></div></div></div><div class=\"form-group\"><label for=\"txt_entryPswdTemp\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>重新输入网银登录密码</label><div class=\"col-sm-4\"><input type=\"password\" class=\"form-control\" required=\"\" id=\"txt_entryPswdTemp\" name=\"txt_entryPswdTemp\" minlength=\"8\" maxlength=\"20\" ng-model=\"vm.model.PswdTemp\" placeholder=\"请重新输入网银登录密码\"></div><div class=\"col-md-4 error\" ng-messages=\"vm.setPwdForm.txt_entryPswdTemp.$error\"><span class=\"help-block\" ng-message=\"required\">请重新输入网银登录密码</span> <span class=\"help-block\" ng-message=\"minlength\">密码长度需为8至20位</span></div></div><div class=\"form-group\"><label for=\"txt_customerNickname\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>客户昵称</label><div class=\"col-sm-4\"><input type=\"text\" required=\"\" class=\"form-control\" id=\"txt_customerNickname\" name=\"txt_customerNickname\" ng-model=\"vm.model.customerNickname\" placeholder=\"请输入客户昵称\"></div><div class=\"col-md-4 error\" ng-messages=\"vm.setPwdForm.txt_customerNickname.$error\"><span class=\"help-block\" ng-message=\"required\">请输入客户昵称</span></div></div><div class=\"form-group\"><label for=\"txt_privacyIssues\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>私密问题</label><div class=\"col-sm-4\"><select class=\"form-control\" required=\"\" id=\"txt_privacyIssues\" name=\"txt_privacyIssues\" ng-model=\"vm.model.privacyIssues\"><option value=\"1\">请选择私密问题</option><option value=\"2\">您父亲的生日是？</option><option value=\"3\">您母亲的生日是？</option></select></div><div class=\"col-md-4 error\" ng-messages=\"vm.setPwdForm.txt_privacyIssues.$error\"><span class=\"help-block\" ng-message=\"required\">请请选择私密问题</span></div></div><div class=\"form-group\"><label for=\"txt_questionAnswer\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>私密问题答案</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" id=\"txt_questionAnswer\" name=\"txt_questionAnswer\" ng-model=\"vm.model.questionAnswer\" placeholder=\"请输入私密问题答案\"></div><div class=\"col-md-4 error\" ng-messages=\"vm.setPwdForm.txt_questionAnswer.$error\"><span class=\"help-block\" ng-message=\"required\">请输入私密问题答案</span></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-4 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backRegistAccountInforPage();\">上一步</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLogin/forgetPassword/forgetPassword/forgetPassword.html","<div class=\"main-box\"><div forgetpasswordstate=\"\" flow=\"1\"></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">验证身份信息认证</div><form class=\"form-horizontal\" novalidate=\"\" ng-submit=\"vm.toSetLoginPassword()\" name=\"vm.regForm\"><div class=\"form-group\"><label for=\"txt_account\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 账号/卡号</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" required=\"\" placeholder=\"请输入您的账号/卡号\" id=\"txt_account\" name=\"txt_account\" ng-model=\"vm.model.account\" high-light=\"vm.regForm.txt_account.$viewValue\" custom-pattern=\"account\" maxlength=\"19\" num=\"4\" symbol=\"\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_account.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的账号/卡号</span> <span class=\"help-block\" ng-message=\"customPattern\">请正确输入您的账号/卡号</span></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 卡号类型</label><div class=\"col-sm-4\"><select class=\"form-control\" required=\"\" id=\"txt_cardtype\" name=\"txt_cardtype\" ng-model=\"vm.model.cardtype\"><option value=\"\">请选择卡号类型</option><option value=\"11\">借记卡</option><option value=\"12\">活期一本通</option><option value=\"13\">定期一本通</option><option value=\"14\">普通存折</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_cardtype.$error\"><span class=\"help-block\" ng-message=\"required\">请选择卡号类型</span></div></div><div class=\"form-group\"><label for=\"txt_cardpassword\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 账号/卡号密码</label><div class=\"col-sm-4\"><input type=\"password\" class=\"form-control\" required=\"\" id=\"txt_cardpassword\" name=\"txt_cardpassword\" keyboard=\"\" ng-model=\"vm.model.cardpassword\" placeholder=\"请输入您的账号/卡号密码\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_cardpassword.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的账号/卡号密码</span></div></div><div class=\"form-group\"><label for=\"txt_custName\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 客户姓名</label><div class=\"col-sm-4\"><input type=\"text\" maxlength=\"6\" custom-pattern=\"name\" required=\"\" class=\"form-control\" id=\"txt_custName\" name=\"txt_custName\" ng-model=\"vm.model.custName\" placeholder=\"请输入您的真实姓名\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_custName.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的真实姓名</span> <span class=\"help-block\" ng-message=\"customPattern\">姓名为2至6位中文</span></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 证件类型</label><div class=\"col-sm-4\"><select class=\"form-control\" id=\"txt_idcardtype\" name=\"txt_idcardtype\" ng-model=\"vm.model.idcardtype\" required=\"\"><option value=\"\">请选择证件类型</option><option value=\"1\">居民身份证</option><option value=\"2\">临时身份证</option><option value=\"3\">军官证</option><option value=\"4\">武警证</option><option value=\"5\">士兵证</option><option value=\"6\">文职干部证</option><option value=\"7\">外国护照</option><option value=\"8\">香港通行证</option><option value=\"9\">澳门通行证</option><option value=\"10\">台湾通行证</option><option value=\"11\">其他证件</option></select></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_idcardtype.$error\"><span class=\"help-block\" ng-message=\"required\">请选择证件类型</span></div></div><div class=\"form-group\"><label for=\"txt_certNo\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 证件号码</label><div class=\"col-sm-4\"><input type=\"text\" maxlength=\"18\" class=\"form-control\" id=\"txt_certNo\" required=\"\" name=\"txt_certNo\" custom-pattern=\"identification\" ng-model=\"vm.model.certNo\" placeholder=\"请输入您的身份证号码\" high-light=\"vm.regForm.txt_certNo.$viewValue\" ng-disabled=\"vm.mobileValidateCode\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_certNo.$error\"><span class=\"help-block\" ng-message=\"required\">请输入您的证件号码</span> <span class=\"help-block\" ng-message=\"customPattern\">证件格式不正确</span></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> 联系电话</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"txt_moblie\" name=\"txt_moblie\" required=\"\" custom-pattern=\"phone\" ng-model=\"vm.model.moblie\" placeholder=\"请输入联系电话\" maxlength=\"11\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_moblie.$error\"><span class=\"help-block\" ng-message=\"required\">请输入联系电话</span> <span class=\"help-block\" ng-message=\"customPattern\">联系电话格式不正确</span></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span> E-mail地址</label><div class=\"col-sm-4\"><input type=\"text\" class=\"form-control\" id=\"txt_email\" name=\"txt_email\" ng-model=\"vm.model.email\" required=\"\" custom-pattern=\"email\" placeholder=\"请输入Email\"></div><div class=\"col-sm-4 error\" ng-messages=\"vm.regForm.txt_email.$error\"><span class=\"help-block\" ng-message=\"required\">请输入Email</span> <span class=\"help-block\" ng-message=\"customPattern\">Email格式不正确</span></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\" ng-click=\"vm.backLogin();\">返回</button></div></div></form></div></div></div>");
$templateCache.put("app/components/consumerLogin/forgetPassword/forgetPassword/forgetPassword1.html","<div class=\"main-box\"><div class=\"titile-step\"><div class=\"title\">密码重置</div><div class=\"step\"><span class=\"current\"><i>1</i>验证身份信息认证</span><span><i>2</i>设置新密码</span><span><i>3</i>密码设置结果信息</span></div></div><div class=\"trading-one-box\"><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">验证身份信息认证</div><form class=\"form-horizontal\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">账号/卡号</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>卡号类型</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"11\">借记卡</option><option value=\"12\">活期一本通</option><option value=\"13\">定期一本通</option><option value=\"14\">普通存折</option></select></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>账号/卡号密码</label><div class=\"col-sm-8\"><input type=\"password\" class=\"form-control\" id=\"exampleInputPassword1\" placeholder=\"\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>客户姓名</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件类型</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"1\">居民身份证</option><option value=\"2\">临时身份证</option><option value=\"3\">军官证</option><option value=\"4\">武警证</option><option value=\"5\">士兵证</option><option value=\"6\">文职干部证</option><option value=\"7\">外国护照</option><option value=\"8\">香港通行证</option><option value=\"9\">澳门通行证</option><option value=\"10\">台湾通行证</option><option value=\"11\">其他证件</option></select></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>证件号码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>联系电话</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>E-mail地址</label><div class=\"col-sm-8\"><input type=\"email\" class=\"form-control\" id=\"inputEmail3\" placeholder=\"Email\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\">返回</button></div></div></form></div><div class=\"ebank-input-box\"><div class=\"ebank-input-title\">设置新密码</div><form class=\"form-horizontal\"><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">网银登录密码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">重新输入网银登录密码</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">客户昵称</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><label for=\"inputPassword3\" class=\"col-sm-4 control-label\"><span class=\"red\">*</span>私密问题</label><div class=\"col-sm-8\"><select class=\"form-control\"><option value=\"1\">请选择私密问题</option><option value=\"2\">您父亲的生日是？</option><option value=\"3\">您母亲的生日是？</option></select></div></div><div class=\"form-group\"><label for=\"inputEmail3\" class=\"col-sm-4 control-label\">私密问题答案</label><div class=\"col-sm-8\"><input type=\"text\" class=\"form-control\"></div></div><div class=\"form-group\"><div class=\"col-sm-offset-4 col-sm-8 btn-box-big\"><button type=\"submit\" class=\"btn btn-primary btn-lg\">确认</button> <button type=\"button\" class=\"btn btn-default btn-lg\">上一步</button></div></div></form></div><div class=\"ebank-input-box\"><form class=\"form-horizontal\"><div class=\"ebank-input-title\">密码重置结果信息</div><div class=\"alert alert-success\" role=\"alert\"><strong>密码找回成功!</strong> 系统5秒后将会自动跳转到登录页面，如果没有自动跳转，请点击登录。</div><div class=\"form-group\"><div class=\"col-sm-offset-5 col-sm-6 btn-box-big\"><button type=\"button\" class=\"btn btn-default btn-lg\" ui-sref=\"login\">返回登录</button></div></div></form></div></div></div>");}]);
//# sourceMappingURL=../maps/scripts/app-8db2614a90.js.map
