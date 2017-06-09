(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('quotedPriceDetailController', quotedPriceDetailController);

    quotedPriceDetailController.$inject = ['$scope', '$rootScope', 'ValidationService', 'ModalService', 'CommonService', '$state', 'UserService', 'CONFIG', '$filter'];


    function quotedPriceDetailController($scope, $rootScope, ValidationService, ModalService, CommonService, $state, UserService, CONFIG, $filter) {
        var vm = this;
        vm.model = {};


        init();

        vm.showDetailInfo = showDetailInfo;

        vm.addRecordShopCartFun = addRecordShopCartFun;

        /**
         *初始化
         */
        function init() {

            // 初始化信息
            showInfo();

        }

        /**
         * 初始化信息
         * @memberof ChannelBaseInfoAddController
         * @function showInfo
         * @description 初始化修改信息
         */
        function showInfo() {

            var returnList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));

            if (returnList.quoteList != null) {
                vm.model.quoteId = returnList.quoteList.quoteId; //       报价ID
                vm.model.salesChannel = returnList.quoteList.salesChannel; //   销售渠道
                vm.model.quoteType = returnList.quoteList.quoteType; //   销售类型
                vm.model.status = returnList.quoteList.status; // 状态

                vm.model.partyId = returnList.quoteList.partyId; // 客户标识
                vm.model.quoteName = returnList.quoteList.quoteName; // 保价名
                vm.model.description = returnList.quoteList.description; // 描述
                vm.model.currency = returnList.quoteList.currency; // 币种
                vm.model.storeName = returnList.quoteList.storeName; // 产品销售网点名称
                vm.model.quoteRoleList = returnList.quoteList.quoteRoleList; //报价角色名称LIST   
                vm.model.quoteItemList = returnList.quoteList.quoteItemList; //报价项目

                if (vm.model.status == "已批准") {
                    vm.model.value = true;
                } else {
                    vm.model.value = false;
                }
 
            } else {
                $state.go('quotedPrice');
            }


        }

        /**
         * 我的订单-已完成订单
         * @function searchTransList
         * @memberof myOrderController
         */
        function queryQuotedPrice() {

            var params = {
                reqHead: {
                    flag: "1", //主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryQuotedPriceAction" // 服务名称
                },
                reqBody: {
                    partyId: "c10004" //partyId
                }
            };

            var promise = UserService.queryQuotedPrice(params);

            promise.then(function(data) {
                var returnHead = data.rspHead;
                var returnBody = data.rspBody;
                if (returnHead.returnCode == CONFIG.CORRECT_CODE) {

                    vm.quoteList = returnBody.quoteList;
                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });

        }

        function showDetailInfo(item) {

            var quoteItemList = item;
            if (quoteItemList != undefined) {

                var params = {
                    quoteItemList: quoteItemList
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));


                ModalService.showModal({
                    templateUrl: 'app/components/consumerCar/showQuotedPriceDetail/showQuotedPriceDetail.html',
                    windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'registerAgreementModal'
                });
                item = {};

            } else {
                CommonService.subShowMsg("4", "请选择信息");
            }
        }


        // 添加购物车
        function addRecordShopCartFun(x) {

            var productdetail = {};

            // 根据产品Id查询产品详情

            var params = {
                reqHead: {
                    flag: "1", //主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryAttributeInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productCode: x.productId //产品标志
                }
            };

            var promise = UserService.queryAttributeInfoMethod(params); //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {

                productdetail = data.rspBody;   
                //productdetail.prodCatalogId = productdetail.parentCode;
                //productdetail.prdTypeCode = productdetail.parentCode;
                

                if (!ValidationService.isEmpty(productdetail.configOptionId)) {
                    sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(productdetail));
                    $state.go('templateProduct');
                }



                // // 产品ID
                // vm.goodsCode = vm.productdetail.goodsCode;
                // // 价格
                // vm.productpice = vm.productdetail.price;
                // // 产品详情
                // $("#textDateID").html(vm.productdetail.textData);

                // vm.configOptionIdMap = {};
                // // 产品配置名称
                // vm.optionName = vm.productdetail.configOptionName;
                // vm.configOptionName = vm.optionName.split(",");
                // // 产品配置ID
                // vm.optionId = vm.productdetail.configOptionId;
                // vm.configOptionId = vm.optionId.split(",");

                // for (var i = 0; i < vm.configOptionName.length; i++) {
                //     vm.configOptionIdMap[vm.optionId.split(",")[i]] = vm.optionName.split(",")[i];
                // }
                // // 图片
                // vm.imageAdress = vm.productdetail.imageAdress;

            }).catch(function(error) {
                CommonService.subShowMsg(2, error);
            });

        }

    }
})();
