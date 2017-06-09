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
            	//shaoxu 2017/5/26
                LOGIN: 'common_login',                                     // 用户登录
                LOGOUT: 'common_logout',                                   // 用户退出
            	//LOGIN: 'common/login', 
            	//LOGOUT: 'common/logout',
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
