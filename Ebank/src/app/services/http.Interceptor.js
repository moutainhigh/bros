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
