(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('myOrderController', myOrderController);

    myOrderController.$inject = ['$scope', '$rootScope','CommonService','$state','UserService', 'CONFIG','$filter'];


    function myOrderController($scope, $rootScope,CommonService, $state, UserService, CONFIG,$filter) {
        var vm = this;

        vm.pageSize = '10';
        vm.pageIndex = '1';
        vm.currentPage = '1';
        vm.salesChannelEnumId = "";
        vm.orderTypeId = "";
        vm.resetInfo = resetInfo;                                               // 重置信息
        vm.type = CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT;
        vm.tabIndex = 0;
        vm.items = [{
            type: CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT,
            value: '已完成订单'
        }, {
            type: CONFIG.CONSTANT.TRANS_CATEGORY.DEPOSIT,
            value: '未完成订单'
        }];

        if($state.params['type']) {
            vm.type = $state.params['type'];
            vm.items.filter(function(item, index) {
                if(item.type === vm.type) {
                    vm.tabIndex = index;
                    return true;
                }
            });
        }


        // 方法
        vm.queryOrderList = queryOrderList;                               // 我的订单-已完成订单
        vm.DoCtrlPagingAct = DoCtrlPagingAct;                             // 未完成订单列表分页控制
        init();

        /**
         *初始化
         */
        function init(){
              vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            vm.endDate = new Date();
            vm.startDate = window.moment().add(-1, 'M')['_d'];

            queryOrderList(CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT);
        }

        /**
         * 我的订单-已完成订单
         * @function searchTransList
         * @memberof myOrderController
         */
        function queryOrderList(type){
            var orderStatusIds = [];

            if(type == CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT){
                var orderStatusId = {};
                orderStatusId.orderStatusId = "ORDER_COMPLETED";   //  订单状态标识
                orderStatusIds.push(orderStatusId);
            }else{
                var statusState = ['ORDER_CREATED','ORDER_SENT','ORDER_PROCESSING','ORDER_APPROVED',
                    'ORDER_HOLD','ORDER_REJECTED','ORDER_CANCELLED'];
                for (var i = 0; i < statusState.length; i++) {
                         var orderStatusId = {};
                         orderStatusId.orderStatusId = statusState[i];   //  订单状态标识
                         orderStatusIds.push(orderStatusId);
                }

            }

             var orderTypeIds = [];
             var orderTypeId = {};
             orderTypeId.orderTypeId = vm.orderTypeId;
             orderTypeIds.push(orderTypeId);
             var salesChannelEnumIds = [];
             var salesChannelEnumId = {};
             salesChannelEnumId.salesChannelEnumId = vm.salesChannelEnumId;
             salesChannelEnumIds.push(salesChannelEnumId);
            var start = $filter('date')(vm.startDate, 'yyyy-MM-dd 00:00:00');
            var end = $filter('date')(vm.endDate, 'yyyy-MM-dd 23:59:59');

            var beginDate = $filter('date')(vm.startDate, 'yyyy-MM-dd');
            var endDate = $filter('date')(vm.endDate, 'yyyy-MM-dd');

            var begin = beginDate.replace(/-/g,"");
            var end  = endDate.replace(/-/g,"");
            if(parseInt(begin)>parseInt(end)){
                 CommonService.subShowMsg("3","开始时间不能大于结束时间");
                 return;
            }


            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrdersListAction"                                  // 服务名称
                },
                reqBody:{
                   viewIndex: vm.pageIndex+'',
                   viewSize: vm.pageSize+'',
                   orderStatusIds:orderStatusIds,
                   showAll:"Y",
                   minDate:start,
                   maxDate:end,
                   orderTypeIds:orderTypeIds,
                   partyId:vm.user.customerNo,
                   salesChannelEnumIds:salesChannelEnumIds
                }
            };

            var promise = UserService.getOrdersList(params);

            promise.then(function(data) {
                if(type == CONFIG.CONSTANT.TRANS_CATEGORY.BENEFIT){
                    vm.tradeList = data.rspBody.orderList;
                }else{
                    vm.tradeList = data.rspBody.orderList;
                }

                vm.pageTotal = '' + Math.ceil(parseInt(data.rspBody.orderListSize) / parseInt(vm.pageSize));
                vm.page = {
                    'currentPage': vm.currentPage,
                    'page-size': vm.pageSize,
                    'total': vm.pageTotal
                };
            }).catch(function(error) {
                CommonService.showError(error);
            });

        }

         // 重置方法
        function resetInfo(){
           vm.endDate = new Date();
           vm.startDate = window.moment().add(-1, 'M')['_d'];
           vm.salesChannelEnumId = "";
           vm.orderTypeId = "";
        }


         /**
         * 选择查询的交易类型
         * @function queryOrdersList
         * @memberof myOrderController
         */
        $scope.$watch('vm.tabIndex', function(newIndex, oldIndex) {
            if(oldIndex != newIndex) {
                var tabItem = vm.items.filter(function(item, index) {
                    if(newIndex === index) {
                        return true;
                    }
                });
                vm.type = tabItem[0]['type'];
                vm.pageIndex = '1';
                vm.currentPage = '1';
                queryOrderList(vm.type);
            }
        });



        /**
         * @memberof myOrderController
         * @function DoCtrlPagingAct
         * @description 未完成订单列表分页控制
         */
        function DoCtrlPagingAct(text, page, pageSize, total) {
            vm.pageIndex = parseInt(page);
            vm.currentPage = parseInt(page);
            queryOrderList(vm.type);
        }
    }
})();
