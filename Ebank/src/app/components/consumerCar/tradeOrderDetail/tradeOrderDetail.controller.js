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
