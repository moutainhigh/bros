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
