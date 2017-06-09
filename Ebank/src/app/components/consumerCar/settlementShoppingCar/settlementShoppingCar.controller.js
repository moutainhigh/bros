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
