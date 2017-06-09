(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('browseHistoryController', browseHistoryController);

    browseHistoryController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ModalService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name browseHistoryController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 产品推荐，浏览记录
     */
    function browseHistoryController($rootScope, UserService, CommonService,ModalService, $state, CONFIG, $scope, $timeout) {
        var vm = this;
        vm.goproductdetail = goproductdetail;
        vm.recommendMorePro = recommendMorePro;//更多推荐产品
        vm.imgMouseover = imgMouseover;
        vm.imgMouseLeave = imgMouseLeave;
        init();  //初始化
        /**初始化方法
         * @memberof browseHistoryController
         * @function init
         * @description 初始化
         */
        function init(){
        	// 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            if (JSON.parse(sessionStorage.getItem('discoverHotList')==null)) {
                queryDiscoverHot();
            }else{
                vm.productIdList = JSON.parse(sessionStorage.getItem('discoverHotList'));
                var j;
                if (vm.productIdList.length>2) {
                    j=2
                    vm.recommendMore = "0";
                }else{
                    j = vm.productIdList.length;
                    vm.recommendMore = "1";
                }
                var n=0;
                vm.homeShowProductIdList = [];
                for (var m = 0; m < j; m++) {
                        vm.homeShowProductIdList[n] = vm.productIdList[m];
                        n++;
                    }
            }
        }
        function imgMouseover(){
            vm.mouseFlag = true;
        }
        function imgMouseLeave(){
            vm.mouseFlag = false;
        }
        /**查询热销信息
         * @memberof browseHistoryController
         * @function queryDiscoverHot
         * @description 查询热销信息
         */
        function queryDiscoverHot(){
            var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryDiscoverHotAction" // 服务名称
                },
                reqBody: {
                    productStoreId: vm.user.productStoreId, //  店铺标识
                    goodStatus: "00",                            //  商品状态
                    goodsType: "02",                             //  商品类型
                    goodsFlag: "00"                              //  上下架标志
                }
            };
            var promise = UserService.queryDiscoverHot(params);

            promise.then(function(data) {
                if (data.rspHead.returnCode=="00000000") {
                    vm.productIdList = data.rspBody.discoverHotList;
                    if (vm.productIdList==null || vm.productIdList==undefined) {
                        return;
                    }
                    for (var i = 0; i < vm.productIdList.length; i++) {
                        vm.productIdList[i].goodsName = vm.productIdList[i].productName;
                        vm.productIdList[i].goodsCode = vm.productIdList[i].productId;
                        vm.productIdList[i].configOptionName = vm.productIdList[i].productConfigOptionList[0].configOptionName;
                        vm.productIdList[i].configOptionId = vm.productIdList[i].productConfigOptionList[0].configOptionId;
                        vm.productIdList[i].configOptionPrice = vm.productIdList[i].productConfigOptionList[0].configOptionPrice;
                        vm.productIdList[i].parentCode = vm.productIdList[i].productCategoryList[0].rootProductCategoryId;

                        vm.productIdList[i].templateUrl = "templateProduct";//默认产品调研项
                        vm.productIdList[i].prdTypeCode = vm.productIdList[i].productCategoryList[0].parentProductCategoryId;
                        vm.productIdList[i].sunPrice = (parseInt(vm.productIdList[i].configOptionPrice) + parseInt(vm.productIdList[i].price)).toFixed(2);
                    }
                    sessionStorage.setItem("discoverHotList", JSON.stringify(vm.productIdList));
                    var j;
                    if (vm.productIdList.length>2) {
                        j=2;
                        vm.recommendMore = "0";
                    }else{
                        j = vm.productIdList.length;
                        vm.recommendMore = "1";
                    }
                    var n=0;
                    vm.homeShowProductIdList = [];
                    for (var m = 0; m < j; m++) {
                        vm.homeShowProductIdList[n] = vm.productIdList[m];
                        n++;
                    }
                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });
        }
        /**更多产品
         * @memberof browseHistoryController
         * @function recommendMorePro
         * @description 更多产品
         */
        function recommendMorePro(){
            $state.go('discoverPromotions',{discoverPromotionsListFlag:'1'});
        }
        /**查看产品详情
         * @memberof browseHistoryController
         * @function goproductdetail
         * @description 查看产品详情
         */
        function goproductdetail(product){
            sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(product));
            $state.go('productdetail');
        }

    }
})();
