(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope','ModalService', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name HomeController
     * @param  {service} CommonService    公用服务
     * @param  {service} $state           通用服务
     * @description
     * 首页控制器
     */
    function HomeController($rootScope,ModalService, UserService, CommonService, $state, CONFIG, $scope, $timeout) {
        var vm = this;

        vm.logout = logout;
        vm.personOverviewPage=personOverviewPage;
        vm.custPropertyDetail=custPropertyDetail;

        vm.products = [];
        var productsProcode;
        vm.optionDisplay = false;
        vm.slideProImg = slideProImg;//点击轮播图片触发方法

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
        }
        // , {
        //     type: '005',
        //     name: '贷款',
        //     color: '#00eeee',
        //     operator: [{
        //         'state': 'undefined',
        //         'name': '转入'
        //     }, {
        //         // 'state': 'cashRedemption',
        //         'state': 'undefined',
        //         'name': '转出'
        //     }]
        // }
        ];




        init();//初始化方法

        /**
         * @memberof HomeController
         * @function init
         * @description 初始化方法
         */
        function init(){

            if (sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER)) {
            // 用户信息
               vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
        	   queryTreeList();		                   // 查询菜单信息
               queryuser();                            //接收客户信息
               getPropertyDistribution();              // 获取资产分布

               // custPropertyDetail();
               if(JSON.parse(sessionStorage.getItem('promotionsList'))==undefined){
                    queryDiscoverPromotions();//查询促销信息
               }else{
                    vm.productIdList = JSON.parse(sessionStorage.getItem('promotionsList'));
                    promotCarousel();
               }
            }
        }

        function personRemindInfo(){
                var num = 0;
                var len = $(".product-info1 li").length;
                function fun(){
                var mal = num * -80
                $(".product-info1").animate({marginTop:mal},500)
                num++;
                if(num == 10){
                    num = 1
                }

            }
            // var t = setInterval(fun,1000)
        }

        /**查询促销信息
         * @memberof HomeController
         * @function queryDiscoverPromotions
         * @description 查询促销信息
         */
        function queryDiscoverPromotions(){
            var params = {
                reqHead: {
                    flag: "1", // 主副交易标志 1是主交易     2是副交易
                    tradeType: "1", // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01", // 交易步骤
                    serviceName: "queryDiscoverPromotionsAction" // 服务名称
                },
                reqBody: {
                    productStoreId: vm.user.productStoreId, //  店铺标识
                    goodStatus: "00",                            //  商品状态
                    goodsType: "02",                             //  商品类型
                    goodsFlag: "00"                              //  上下架标志
                }
            };
            var promise = UserService.queryDiscoverPromotions(params);

            promise.then(function(data) {
                if (data.rspHead.returnCode=="00000000") {
                    vm.productIdList = data.rspBody.promotionsList;
                    if (vm.productIdList==null || vm.productIdList==undefined) {
                        return;
                    }
                    sessionStorage.setItem('promotionsList', JSON.stringify(vm.productIdList));
                    promotCarousel();

                }

            }).catch(function(error) {
                CommonService.subShowMsg("2", error);
            });
        }
        /**设置轮播时间间隔
         * @memberof HomeController
         * @function promotCarousel
         * @description 设置轮播时间间隔
         */
        function promotCarousel(){
            // 设置轮播图图片间隔
            $scope.myInterval = 3000;
        }
        /**点击轮播图片进入促销产品列表
         * @memberof HomeController
         * @function slideProImg
         * @description 点击轮播图片进入促销产品列表
         */
        function slideProImg(productIdList){
            sessionStorage.setItem('discoverPromotionsList', JSON.stringify(productIdList));
            $state.go('discoverPromotions',{discoverPromotionsListFlag:'0'});
        }
         /**
         * @function
         * @name queryuser
         * @description 接收客户信息
         * */
        function queryuser(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            $rootScope.$broadcast('user', vm.user);
        }
        /**
         * @memberof HomeController
         * @parm treeMenuList
         * @description 实时接收客户信息
         */
         $scope.$on('user', function(event, data) {

            vm.user = data;

        });
        /**
         * @function
         * @name logout
         * @description 用户退出
         * */
        function logout() {
             var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"3",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"logoutAction"         // 服务名称
                },
                reqBody:{

                }
            };
            var promise = UserService.logout();
            promise.then(function() {
                $rootScope.hasLogin = false;
                $state.go('login');
            }).catch(function(error) {
                CommonService.showError(error);
            });
        }

        /**
         * @memberof queryTreeList
         * @param
         * @description 查询菜单信息
         */
        function queryTreeList(){

            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryPbankLoginMenuAction"         // 服务名称
                },
                reqBody:{

                }
            };

        	var promise = UserService.getTreeMenu(params);

        	promise.then(function(data) {
        		vm.treeMenuList = data.rspBody.returnList;
                $rootScope.$broadcast('treeMenuList', vm.treeMenuList);

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }

        /**
         * @memberof HomeController
         * @parm treeMenuList
         * @description 实时接收查询菜单信息
         */
         $scope.$on('treeMenuList', function(event, data) {

            vm.treeMenuList = data;

        });


         // 获取财富分布信息
        function getPropertyDistribution() {
            vm.flag=0;
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryPersonOverviewAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: vm.user.customerNo                   //客户号
                }
            };
            // var promise = UserService.getPropertyDistribution(params);
            var promise = UserService.personOverviewQuery(params);    //查询客户总览信息
            promise.then(function(data) {
                var personPropertyList=[];
                var depositPropertyMap={};  //存款资产
                var loanPropertyMap={};     //贷款资产
                var investPropertyMap={};   //投资资产
                var signProductCountMap={}; //签约产品汇总
                var creditCardCountMap={};  //信用卡汇总
                var personPropertyInfo=data.rspBody.personOverview.personPropertyInfo;
                vm.personRemindInfo=data.rspBody.personOverview.personRemindInfo;
                if(vm.personRemindInfo){
                    vm.flag=1;
                }
                if (personPropertyInfo && personPropertyInfo.length > 0) {

                    depositPropertyMap.assetType="1";
                    depositPropertyMap.assetSum=personPropertyInfo[0].depositProperty;
                    depositPropertyMap.assetDesc="存款资产";
                    personPropertyList.push(depositPropertyMap);

                    loanPropertyMap.assetType="2";
                    loanPropertyMap.assetSum=personPropertyInfo[0].loanProperty;
                    loanPropertyMap.assetDesc="贷款资产";
                    personPropertyList.push(loanPropertyMap);

                    investPropertyMap.assetType="3";
                    investPropertyMap.assetSum=personPropertyInfo[0].investProperty;
                    investPropertyMap.assetDesc="投资资产";
                    personPropertyList.push(investPropertyMap);

                    signProductCountMap.assetType="4";
                    signProductCountMap.assetSum=personPropertyInfo[0].signProductCount;
                    signProductCountMap.assetDesc="签约产品";
                    personPropertyList.push(signProductCountMap);

                    creditCardCountMap.assetType="5";
                    creditCardCountMap.assetSum=personPropertyInfo[0].creditCardCount;
                    creditCardCountMap.assetDesc="信用卡";
                    personPropertyList.push(creditCardCountMap);
                }
                showDistributionData(personPropertyList); //显示数据
                showDistributionPie(personPropertyList); //显示饼图
                personRemindInfo();
                // 保存产品信息--我的账户获取
                sessionStorage.setItem('personPropertyList', JSON.stringify(personPropertyList));
            }).catch(function(error) {
                // vm.emptyPie = emptyPiePath;
                CommonService.showError(error);
            });
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
        * 客户总览基本信息显示
        */
       function personOverviewPage(){
            $state.go('personOverview');
       }

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


    }

})();
