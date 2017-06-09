(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('productdetailController', productdetailController);

    productdetailController.$inject = ['$rootScope', 'UserService', 'CommonService', 'ValidationService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name productdetailController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 我的贷款主页面控制器
     */
    function productdetailController($rootScope, UserService, CommonService, ValidationService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.addcar = addcar;
        vm.selectConfigOptionFun = selectConfigOptionFun;
        vm.model = [];

        vm.items = [{
            type: CONFIG.CONSTANT.PRODUCT_MANAGE.BASEINFO,
            value: '产品信息'
        }, {
            type: CONFIG.CONSTANT.PRODUCT_MANAGE.PROPERTY,
            value: '产品配置'
        }];
        vm.tabIndex = 0;
        vm.productInfomsg1 = true;

        //初始化方法
        init();

        function init() {
            // 接受已售产品ID
            vm.productId = $state.params['productId'];
            if(vm.productId){
                queryAttributeInfoMethod();
            }else{
                vm.productdetail = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
                queryproductList(); //查询产品展示详情
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
         * 查询产品展示详情
         * @memberof productdetailController
         * @function queryproductList
         * @description 查询产品展示详情
         */
        function queryproductList() {
            vm.requireAmount = vm.productdetail.requireAmount;

            // configOptionId: "CARD_0521_10010"
            // configOptionName: "人民币活期开户"
            vm.configOptionName = vm.productdetail.configOptionName;
            vm.configOptionId = vm.productdetail.configOptionId;
            vm.goodsCode = vm.productdetail.goodsCode;
            vm.imageAdress = vm.productdetail.imageAdress;
            vm.textData = vm.productdetail.textData;
            vm.productdetitle = vm.productdetail.goodsName;
            vm.productname = vm.productdetail.goodsName;
            vm.producthistoryrate = vm.productdetail.historyRateReturn;
            vm.productrisk = vm.productdetail.investmentRis;
            vm.productpice = vm.productdetail.price;
            vm.productdesc = vm.productdetail.abstract;
            vm.productdel = vm.productdetail.abstract;
            $("#textDateID").html(vm.textData);

        }

        // 根据产品Id查询产品详情
        function queryAttributeInfoMethod(){
            var params = {
                reqHead: {
                    flag: "1",              //主副交易标志 1是主交易     2是副交易
                    tradeType: "1",         // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01",         // 交易步骤
                    serviceName: "queryAttributeInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productCode: vm.productId                    //产品标志
                }
            };

            var promise = UserService.queryAttributeInfoMethod(params); //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {
                vm.productdetail = data.rspBody;

                // 产品ID
                vm.goodsCode = vm.productdetail.goodsCode;
                // 价格
                vm.productpice = vm.productdetail.price;
                // 产品详情
                $("#textDateID").html(vm.productdetail.textData);

                vm.configOptionIdMap = {};
                // 产品配置名称
                vm.optionName = vm.productdetail.configOptionName;
                vm.configOptionName = vm.optionName.split(",");
                // 产品配置ID
                vm.optionId = vm.productdetail.configOptionId;
                vm.configOptionId = vm.optionId.split(",");
                // 产品配置价格
                vm.configPrice = vm.productdetail.configOptionPrice;

                for(var i = 0; i < vm.configOptionName.length; i++){
                    vm.configOptionIdMap[vm.optionId.split(",")[i]+"|"+vm.configPrice.split(",")[i]] = vm.optionName.split(",")[i];
                }
                // 图片
                vm.imageAdress = vm.productdetail.imageAdress;

            }).catch(function(error) {
                CommonService.subShowMsg(2,error);
            });
        }

        // 选中产品配置事件
        function selectConfigOptionFun(tagName, tagId, optionObject){
            switchTag(tagName, tagId);
            if(vm.productId){
                vm.selectoptionObject= optionObject.split("|");
                // 产品ID
                vm.productdetail.selectConfigOptionId = vm.selectoptionObject[0];
                vm.productdetail.configOptionId = vm.selectoptionObject[0];
                // 产品价格 = 基础价格 + 产品配置价格
                vm.productpice = (parseInt(vm.productdetail.price) + parseInt(vm.selectoptionObject[1])).toFixed(2);
            }else{
                vm.productpice = (parseInt(vm.productdetail.price) + parseInt(vm.productdetail.configOptionPrice)).toFixed(2);
                vm.productdetail.selectConfigOptionId = optionObject;
                vm.productdetail.configOptionId = optionObject;
            }
            
        }
        
        /**
         * 添加购物车
         * @memberof productdetailController
         * @function addcar
         * @description 添加购物车
         */
        vm.dataCompare = [];
         function addcar(bankBalance){
            if(vm.productdetail.templateUrl == "" || vm.productdetail.templateUrl == null || vm.productdetail.templateUrl == undefined){
                 CommonService.subShowMsg("4","该产品（"+vm.productdetail.goodsName+"）没有配置产品调研项");
                 return;
            }

            if(vm.productdetail.selectConfigOptionId == undefined){
                 CommonService.subShowMsg(4,"请选产品配置");
                 return false;
            }
            

            if('Y'==vm.requireAmount){
                if (ValidationService.isEmpty(bankBalance) || bankBalance=="0.00") {
                    CommonService.subShowMsg(4,"购买金额不能为空！");
                    return false;
                }
                 vm.productdetail.bankBalance = ValidationService.toStdAmount(bankBalance);
            }
            sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(vm.productdetail));
            // 跳转到相应的产品调研项
            $state.go(vm.productdetail.templateUrl);
        }
        

        /**
         * 选择查询的交易类型
         * @function transTypeWatcher
         * @memberof BindBankcardController
         */
        $scope.$watch('vm.tabIndex', function(newIndex, oldIndex) {

            if (oldIndex != newIndex) {
                var tabItem = vm.items.filter(function(item, index) {
                    if (newIndex === index) {
                        return true;
                    }
                });

                vm.type = tabItem[0]['type'];


                if (vm.type == "000") {
                    vm.productInfomsg1 = true;
                    vm.productInfomsg2 = false;
                } else if (vm.type == "001") {
                    vm.productInfomsg1 = false;
                    vm.productInfomsg2 = true;
                }
                vm.pageIndex = '1';
                vm.currentPage = '1';

            }
        });

    }
})();
