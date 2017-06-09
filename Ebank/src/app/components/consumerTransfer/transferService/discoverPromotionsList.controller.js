(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('discoverPromotionsListController', discoverPromotionsListController);

    discoverPromotionsListController.$inject = ['$rootScope', '$scope', '$stateParams','CommonService','UserService', 'CONFIG', '$state'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name discoverPromotionsListController
     * @param  {service} CommonService    公用服务
     * @param  {service} $state           通用服务
     * @description
     * 促销订单列表
     */
    function discoverPromotionsListController($rootScope, $scope, $stateParams, CommonService, UserService, CONFIG, $state) {
        var vm = this;
        vm.pageSize = '4';//每页显示多少条
        vm.pageIndex = '1';//
        vm.currentPage = '1';//
        vm.DoCtrlPagingAct = DoCtrlPagingAct;//分页方法
        //方法
        vm.addcar=addcar;

        // 初始化
        init();

        function init(){
            if ($stateParams.discoverPromotionsListFlag==null) {
                vm.discoverPromotionsListFlag = JSON.parse(sessionStorage.getItem('discoverPromotionsListFlag'));
            }else{
                vm.discoverPromotionsListFlag = $stateParams.discoverPromotionsListFlag;//展示列表标识；0-展示促销列表；1-展示推荐产品列表
                sessionStorage.setItem("discoverPromotionsListFlag", JSON.stringify(vm.discoverPromotionsListFlag));
            }
            if (vm.discoverPromotionsListFlag=="0") {
                // 获取某一个促销规则的产品
                queryDiscoverPromotionsById();
            }else if (vm.discoverPromotionsListFlag=="1") {
                vm.productIdList = JSON.parse(sessionStorage.getItem('discoverHotList'));

                vm.pageTotal = '' + Math.ceil(parseInt(vm.productIdList.length) / parseInt(vm.pageSize));//总页数
                vm.page = {
                        'currentPage': vm.currentPage,
                        'page-size': vm.pageSize,
                        'total': vm.pageTotal
                    };
                var m = 0;
                vm.productIdListPage = [];
                var k = 0;
                if (vm.productIdList.length > parseInt(vm.pageSize)) {
                    k = parseInt(vm.pageSize);
                }else{
                    k = vm.productIdList.length;
                }
                for (var i = 0; i < k; i++) {
                    vm.productIdListPage[m] = vm.productIdList[i];
                    m++;
                }
            }
        }
        //分页
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = parseInt(page);
            vm.currentPage = parseInt(page);
            var m = 0;
            vm.productIdListPage = [];
            var j = (vm.pageIndex-1)*parseInt(vm.pageSize);
            var n;
            if(vm.pageIndex*parseInt(vm.pageSize)>vm.productIdList.length){
                n=vm.productIdList.length;
            }else{
                n=vm.pageIndex*parseInt(vm.pageSize);
            }
            for (var i = j; i < n; i++) {
                vm.productIdListPage[m] = vm.productIdList[i];
                m++;
            }
        }
        /**获取某一个促销规则的产品
         * @memberof discoverPromotionsListController
         * @function queryDiscoverPromotionsById
         * @description 获取某一个促销规则的产品
         */
        function queryDiscoverPromotionsById(){
            vm.productIdList = JSON.parse(sessionStorage.getItem('discoverPromotionsList')).productIdList;
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

            vm.promoText = JSON.parse(sessionStorage.getItem('discoverPromotionsList')).promoText;
            vm.pageTotal = '' + Math.ceil(parseInt(vm.productIdList.length) / parseInt(vm.pageSize));//总页数
                vm.page = {
                        'currentPage': vm.currentPage,
                        'page-size': vm.pageSize,
                        'total': vm.pageTotal
                    };
                var m = 0;
                vm.productIdListPage = [];
                var k = 0;
                if (vm.productIdList.length > parseInt(vm.pageSize)) {
                    k = parseInt(vm.pageSize);
                }else{
                    k = vm.productIdList.length;
                }
                for (var i = 0; i < k; i++) {
                    vm.productIdListPage[m] = vm.productIdList[i];
                    m++;
                }
        }


        /**
         * @memberof discoverPromotionsListController
         * @function addcar
         * @description 加入购物车方法
         */

        function addcar(product) {
            if(product.templateUrl == "" || product.templateUrl == null || product.templateUrl == undefined){
                 CommonService.subShowMsg("4","该产品（"+product.bmfName+"）没有配置产品调研项");
                 return;
            }
            // 跳转到相应的产品调研项
            $state.go(product.templateUrl);
        }

    }

})();
