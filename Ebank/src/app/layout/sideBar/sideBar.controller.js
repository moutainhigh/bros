(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('sideBarController', sideBarController);

    sideBarController.$inject = ['$scope', '$rootScope','UserService','ModalService', 'CommonService','$state','CONFIG'];

    /**
     * @memberOf directbank
     * @ngdoc controller
     * @name sideSlideBarController
     * @description 侧边栏工具框
     * @param $scope
     * @param $rootScope
     */
    function sideBarController($scope, $rootScope,UserService,ModalService, CommonService, $state,CONFIG) {
        var vm = this;

        vm.carMouseenterFun = carMouseenterFun;
        vm.carMouseleaveFun = carMouseleaveFun;

        vm.toggleCompareBlock = toggleCompareBlock;
        vm.compareBlockHidden = compareBlockHidden;

        vm.productDelete = productDelete;
        vm.goCompare = goCompare;
        vm.toggleOption = toggleOption;
        vm.shopcarproduct = shopcarproduct;
        vm.products = [];
        var productsProcode;
        vm.optionDisplay = false;

        // 页面初始化
        init();

        function init(){
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        }
    
        
        /**
         * @memberof sideBarController
         * @function productDelete
         * @description 删除产品
         */
        function productDelete(index,data) {
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            deleteShopCartRecordFunction(vm.user.customerNo,data);

        }
       


        /**
         * @memberof sideBarController
         * @function toggleCompareBlock
         * @description 显示隐藏产品对比框
         */
        function toggleCompareBlock() {
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            $scope.showCompareBox = !$scope.showCompareBox;
            queryShopCartFunction(vm.user.customerNo);
        }

        /**
         * @memberof sideBarController
         * @function compareBlockHidden
         * @description 清空所有对比产品方法
         */
        function compareBlockHidden() {
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            deleteClearShopCarFunction(vm.user.customerNo);
            
        }


        /**
         * @memberof sideBarController
         * @function goCompare
         * @description 跳转至产品对比页并传送数据方法
         */
        function goCompare() {
            if (vm.products.length > 0) {
                $state.go('shoppingCar');
                return;
            }

            if ($scope.currentState == 'compare') {
                $rootScope.$broadcast('sendCompare', productsProcode);
            }else {
                $scope.$on('$stateChangeSuccess', function(e, toState) {
                    if (toState.name == 'compare') {
                        $rootScope.$broadcast('sendCompare', productsProcode);
                    }
                });
            }
        }

        /**
         * @memberof sideBarController
         * @name sendCompare
         * @param data 接收数据
         * @description 监听页面跳转隐藏对比框
         */
        $scope.$on('$stateChangeSuccess', function() {
            $scope.showCompareBox = false;
        });

        /**
         * @memberof sideBarController
         * @name toggleOption
         * @description 关闭对比框
         */
        function toggleOption() {
            $scope.showCompareBox = false;
        }

        function dataProcess() {
            productsProcode = [];
            vm.products.forEach(function(product, call) {
                productsProcode.push(product.prodCode);
            });
        }
        /**
         * 弹窗展示购物车产品详情
         * @memberof sideBarController
         * @function shopcarproduct
         * @description 弹窗展示购物车产品详情
         */
        function shopcarproduct(list){
            var params = {//-------------------后期改为单一的查询产品信息接口，这个是查询全部产品记录
                userCode: list.prodCode//产品代码
            };
            var promise = UserService.browsehis(params);
            promise.then(function(data) {

                $rootScope.productrec = data.respData[0];

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
             ModalService.showModal({
                    templateUrl: 'app/layout/productDetail/productDetail.html',
                    windowTemplateUrl: 'app/layout/productDetail/productDetailShow.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'productdetail'
                });

        }
        //控制头部购物车展开
       

        function carMouseenterFun(){
            $('.top-car .top-car-btn').addClass("top-car-btn-open ");
            $('.top-car-btn .glyphicon-menu-down').addClass("glyphicon-menu-up");
            $('.top-car').children(".top-car-box").show();
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            queryShopCartFunction(vm.user.customerNo);
        }

        function carMouseleaveFun(){
            $('.top-car .top-car-btn').removeClass("top-car-btn-open");
            $('.top-car-btn .glyphicon-menu-down').removeClass("glyphicon-menu-up");
            $('.top-car').children(".top-car-box").hide();
        }


        // 查询购物车
        function queryShopCartFunction(uniqueCstIdentity){

            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryShopCartAction"              // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                }
            };
            var promise = UserService.queryShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList != undefined && data.rspBody.recordList.length > 0){
                    vm.products = data.rspBody.recordList;
                    $scope.length = vm.products.length;
                }else{
                    vm.products = "";
                    $scope.length = 0;
                }
               
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        // 清除购物车
        function deleteClearShopCarFunction(uniqueCstIdentity){
             var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"deleteClearShopCartAction"         // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                }
            };
            var promise = UserService.deleteClearShopCart(params);

            promise.then(function(data) {
                queryShopCartFunction(uniqueCstIdentity);
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        // 删除购物车中某些记录购
        function deleteShopCartRecordFunction(uniqueCstIdentity,data){
            var recordList = [];
            recordList.push(data.id);

            var params = {
                reqHead:{
                    flag:"1",                                           // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                      // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                      // 交易步骤
                    serviceName:"deleteShopCartRecordAction"            // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                   recordList:recordList
                }
            };
            var promise = UserService.deleteClearShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList.length > 0){
                    vm.products = data.rspBody.recordList;
                    $scope.length = vm.products.length;
                }else{
                    vm.products = "";
                    $scope.length = 0;
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }
    }
})();
