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
