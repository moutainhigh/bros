(function() {
    'use strict';

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
