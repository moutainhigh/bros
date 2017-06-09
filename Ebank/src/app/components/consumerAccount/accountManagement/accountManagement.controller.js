(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('accountManagementController', accountManagementController);

    accountManagementController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout', '$location', 'ModalService'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name accountManagementController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 账户控制器
     */
    function accountManagementController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout, $location, ModalService) {

        var vm = this;
        vm.pageSize = '10';//每页显示多少条
        vm.pageIndex = '1';//
        vm.currentPage = '1';//

        /**
         * @memberof accountManagementController
         * @param compareMessage
         * @description 接收货架信息方法
         */
        $scope.$on('allChlShelfGoodCompare', function(event, data) {
            if(data != "" || data != null || data != undefined){
                vm.shelfCodeList = data;
            }
        });

        // 方法

  
        var categoryList = [{
            type: '1',
            name: '存款资产',
            color: '#d193db',
            operator: [{
                'state': 'undefined',
                'name': '充值'
            }, {
                'state': 'undefined',
                'name': '提现'
            }]
        }, {
            type: '2',
            name: '贷款资产',
            color: '#1156b6',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '3',
            name: '投资资产',
            color: '#ffaf00',
            operator: [{
                'state': 'undefined',
                'name': '购买'
            }]
        }, {
            type: '4',
            name: '签约产品',
            color: '#20bd61',
            operator: [{
                'state': 'undefined',
                'name': '申购'
            }, {
                'state': 'undefined',
                'name': '赎回'
            }]
        }, {
            type: '5',
            name: '信用卡',
            color: '#f35050',
            operator: [{
                'state': 'undefined',
                'name': '转入'
            }, {
                'state': 'undefined',
                'name': '转出'
            }]
        }];

        init();//初始化方法

        function init(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            vm.shelfCodeList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SHELFCODE_INFO));
            getPropertyDistribution();              // 获取资产分布
            getCustomerListFun();                   // 获取账户信息
        }

         // 获取财富分布信息
        function getPropertyDistribution() {
            // 接受首页产品信息（饼图）
            vm.personPropertyList = JSON.parse(sessionStorage.getItem("personPropertyList"));
            showDistributionData(vm.personPropertyList); //显示数据
            showDistributionPie(vm.personPropertyList); //显示饼图
           
        }


         function showDistributionPie(productList) {
            productList = productList.filter(function(product){
                return product.assetSum > 0;
            });

            if(productList && productList.length > 0){
                vm.hasPie = true;
            } else {
                vm.hasPie = false;
            }

            var colorArray = [];        //饼图颜色
            var pieSeries = [];
            var distribution;
            if (productList && productList.length > 0) {
                productList.forEach(function(product) {
                    colorArray.push(product.color);
                    var assetAmt="0.00";
                    if(product.assetType=="4"||product.assetType=="5"){
                        assetAmt="5000.00";
                    }else{
                        assetAmt=product.assetSum;
                    }
                    pieSeries.push({
                        assetType:product.assetType,
                        value: parseFloat(assetAmt),
                        name: product.assetDesc
                    });
                });
            } else {
                vm.emptyPie = emptyPiePath;
            }

            vm.pieOptions = {
                color: colorArray,
                backFun:custPropertyDetail,
                series: [{
                    name: '账户总览',
                    type: 'pie',
                    selectedMode: null,
                    hoverAnimation: false,
                    avoidLabelOverlap: false,
                    center: ['50%', '45%'],
                    radius: ['40%', '85%'],
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            formatter: '{b} \n {d}%',
                            textStyle: {
                                color: '#262626',
                                fontSize: 18
                            }
                        }
                    },
                    data: pieSeries
                }]
            };
        }


        // 鼠标饼图其中一个视图事件
        function custPropertyDetail(params){
           // 向子页面传值
            $rootScope.detialData = params;
            ModalService.showModal({
                templateUrl: 'app/components/personOverview/custPropertyDetail.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }


        function showDistributionData(productList) {
            var distributionData = [];
            categoryList.forEach(function(category) {
                var data = {
                    type: category.type,
                    color: category.color,
                    operator: category.operator,
                    sum: 0,
                    desc: category.name
                };
                if (productList && productList.length > 0) {
                    productList.forEach(function(product) {
                        if (product.assetType == category.type) {
                            data.assetType=product.assetType;
                            data.sum = parseFloat(product.assetSum);
                            product.color = data.color;
                        }
                    });
                }
                distributionData.push(data);
            });
            vm.distributionData = distributionData;
        }


        /**
         * @memberof accountManagementController
         * @function 获取账户信息
         */
        function getCustomerListFun(){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:""         // 服务名称
                },
                reqBody:{
                   
                }
            };
            var promise = UserService.getCustomerList(params);

            promise.then(function(data) {
                vm.accountList = data.respData;
            }).catch(function(error) {
                CommonService.showError(error);
            });
        }
    }
})();
