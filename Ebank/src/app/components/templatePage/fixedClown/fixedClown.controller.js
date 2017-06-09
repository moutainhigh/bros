(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('fixedClownController', fixedClownController);

    fixedClownController.$inject = ['$rootScope', 'CommonService', 'UserService', '$state', 'ModalService', '$scope', 'CONFIG', 'FileService'];
    /**
     * @memberof directbank
     * @ngdoc controller
     * @name  fixedClownController
     * @param  {service} UserService 用户服务
     * @description
     * 定活宝购物车
     */

    function fixedClownController($rootScope, CommonService, UserService, $state, ModalService, $scope, CONFIG, FileService) {
        var vm = this;
        vm.model = {};

        //method
        vm.addRecordShopCart = addRecordShopCart;               // 添加购物车方法

        init();


        function init(){
            // 产品信息
            vm.productdetail = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            getSurveyQuestion();
            // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        }


        // 购物车添加方法
        function addRecordShopCart(){

            if (vm.regForm.$valid) {
                addRecordShopCartFun(); // 添加购物车
            }
        }

     function getSurveyQuestion(){
         var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getSurveyQuestionAction"                                  // 服务名称
                },
             reqBody:{
                configOptionId:vm.productdetail.configOptionId,
                productId:vm.productdetail.goodsCode
                // configOptionId:"DP_0523_10001",
                // productId:"DP_0523_10000-01"
            }
        }

            var promise = UserService.getSurveyQuestion(params);

            promise.then(function(data) {
                vm.enumQuestionList = data.rspBody.enumQuestionList;
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        // 添加购物车
        function addRecordShopCartFun(){
            var recordMap = {};
            // 购物车数据
            recordMap.productId = vm.productdetail.goodsCode;                    // 产品ID
            recordMap.productName = vm.productdetail.goodsName;                    // 产品名称
            recordMap.productDes = vm.productdetail.textData;//bmfDesc;        // 产品描述
            // recordMap.productDes = vm.productdetail.bmfDesc;                     // 产品描述
            // 价格 = 基础价格 + 产品配置项价格
            recordMap.productPrice = (parseInt(vm.productdetail.price) + parseInt(vm.productdetail.configOptionPrice)).toFixed(2);                     // 产品价格
            recordMap.productAmount = "1";
                                                 // 产品数量
            var surveyInfo={};

            for (var i = 0; i < vm.enumQuestionList.length; i++) {
                 var descption = vm.enumQuestionList[i].description;
                 surveyInfo[descption]= vm.model.descption[i];
            }


            // 产品调研项数据
            var tradeData = {
                "surveyInfo":JSON.stringify(surveyInfo),             //  调查信息
                "bankBalance":vm.productdetail.bankBalance,           // 价格
                "requireAmount":vm.productdetail.requireAmount,        //计价单位
                // "cardpassword":vm.model.cardpassword,                            //  密码
                // "custAccount":vm.model.custAccount,                              //  客户账户
                // "certificate":vm.model.certificate,                              //  凭证批号
                // "voucherNo":vm.model.voucherNo,                                  //  凭证序号
                "prodCatalogId":vm.productdetail.parentCode,//prdTypeCode,                    //  产品目录
                "configOptionId": vm.productdetail.configOptionId                //  配置项标识
            };

            // 客户号
            var uniqueCstIdentity = vm.user.customerNo;
            recordMap.tradeData = tradeData;


            var reqBody = {
                uniqueCstIdentity:uniqueCstIdentity,
                recordMap:recordMap
            }

            var promise = UserService.addRecordShopCart(reqBody);

            promise.then(function(data) {
                $state.go('shoppingCar');
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }



    }

})();
