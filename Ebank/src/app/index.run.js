(function() {
    'use strict';

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
