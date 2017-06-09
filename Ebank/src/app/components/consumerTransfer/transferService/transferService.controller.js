(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('TransferController', TransferController);

    TransferController.$inject = ['$rootScope', '$scope', 'CommonService','UserService', 'CONFIG', '$state'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name TransferController
     * @param  {service} CommonService    公用服务
     * @param  {service} $state           通用服务
     * @description
     * 首页控制器
     */
    function TransferController($rootScope, $scope, CommonService, UserService, CONFIG, $state) {
        var vm = this;

        // 变量
        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';

        //方法
        vm.addcar=addcar;
        vm.transferServiceOpen =transferServiceOpen;
        var header = {};

        // 初始化
        init();

        function init(){
            // 获取固收产品列表
            // getProduct();
            getShelfCodeList();
        }

        /**
         * @memberof accountManagementController
         * @param compareMessage
         * @description 接收货架信息方法
         */
        $scope.$on('allChlShelfGoodCompare', function(event, data) {
            if(data != "" || data != null || data != undefined){
                setTimeout(function() {
                    $scope.$apply(function() {
                        vm.shelfCodeList = data;
                        vm.product = vm.shelfCodeList.returnGoodsList;
                        for(var i = 0; i < vm.product.length; i++){
                            var sunPrice = (parseInt(vm.product[i].price) + parseInt(vm.product[i].configOptionPrice)).toFixed(2);
                            vm.product[i].sunPrice = sunPrice;
                        }
                    });
                });
                
            }
        });


        // 接受货架信息
        function getShelfCodeList(){
            vm.shelfCodeList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SHELFCODE_INFO));
            setTimeout(function() {
                    $scope.$apply(function() {
                        vm.product = vm.shelfCodeList.returnGoodsList;
                        for(var i = 0; i < vm.product.length; i++){
                            var sunPrice = (parseInt(vm.product[i].price) + parseInt(vm.product[i].configOptionPrice)).toFixed(2);
                            vm.product[i].sunPrice = sunPrice;
                        }
                    });
                });
            
        }

         /**
         * @memberof TransferController
         * @function getProduct
         * @description 获取固收产品列表
         */
        function getProduct() {

            vm.product = [];

            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:""                                  // 服务名称
                },
                reqBody:{
                    pageIndex: vm.pageIndex,
                    pageSize: vm.pageSize
                }
            };

            var promise = UserService.getFixedProduct(params);

            promise.then(function(data) {
                if (data.returnList && data.returnList.length !== 0) {
                    vm.product = data.returnList;
                    vm.pageTotal = '' + Math.ceil(parseInt(data.totalCount) / parseInt(vm.pageSize));
                    vm.page = {
                        'currentPage': vm.currentPage,
                        'page-size': vm.pageSize,
                        'total': vm.pageTotal
                    };
                } else {
                    console.log('列表无数据');
                }
            }).catch(function(error) {
                CommonService.showError(error);
            });

        }


        /**
         * @memberof TransferController
         * @function DoCtrlPagingAct
         * @description 产品列表分页控制
         */
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = page;
            vm.currentPage = page;
            getProduct();
        }

        /**
         * @memberof TransferController
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

        /**
         * 显示弹出录入页面
         * @memberof selfRegistrationController
         * @function showAgreement
         * @description 显示相关协议描述
         */
        function transferServiceOpen() {
            ModalService.showModal({
                templateUrl: 'app/components/consumerTransfer/transferService/transferServiceOpen.html',
                windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                size: 'lg',
                backdrop: 'static',
                windowClass: 'registerAgreementModal'
            });
        }
    }

})();
