(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('demandController', demandController);

    demandController.$inject = ['$scope', '$rootScope','CommonService','$state','UserService', 'CONFIG','$filter'];


    function demandController($scope, $rootScope,CommonService, $state, UserService, CONFIG,$filter) {
        var vm = this;
        vm.model = [];
        init();
        //提交请求
        vm.submitgo = submitgo;
        /**
         *初始化
         */
        function init(){
        	// 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        }

        /**
         * @function submitgo
         * @memberof demandController
         */
        function submitgo(type){
            if(vm.model.name == undefined || vm.model.name == ""){
                CommonService.subShowMsg(4 , "请输入需求名称");
                return;
            }
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"3",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"addShoppingListAction"                                  // 服务名称
                },
                reqBody:{
                    listName:vm.model.name,               // 列名
                    description:vm.model.describe,        // 描述
                    productStoreId:vm.user.productStoreId, //  店铺标识
                    currencyUom:"CNY",                    // 币种
                    partyId:vm.user.customerNo,           // 用户标识
                    inputList:[
                        {
                            productId:"",                 //产品标识
                            custRequestItemSeqId:"00001", //客户请求明细序号标识
                            quantity:""                   //数量
                        }
                    ]
                    
                }
            };

            var promise = UserService.createShoppingListService(params);
            promise.then(function(data) {
                 if (data) {
                    if (data.rspHead.returnCode==CONFIG.CORRECT_CODE) {
                        CommonService.subShowMsg(4 , "需求提交成功");
                        vm.model.name = "";
                        vm.model.describe = "";
                    }
                 }
            }).catch(function(error) {
                CommonService.subShowMsg(2 , error);
            });
        }
    }
})();
