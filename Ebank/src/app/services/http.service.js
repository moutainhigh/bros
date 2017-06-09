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
                
              //shaoxu 2017/5/26
                console.log('shaoxu;'+_serviceUrl);

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
