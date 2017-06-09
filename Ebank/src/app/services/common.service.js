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
