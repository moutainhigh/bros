(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('completedorderItem', investItem);

    function investItem() {
        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/myOrder/completedorderitem/completedorderitem.html',
            controller: getCompleteOrderDetailManageController,
            controllerAs: 'vm'
        };

        return directive;
          /**
         * 获取已完成订单详情-getCompleteOrderDetailManageController
         */
        function getCompleteOrderDetailManageController($rootScope, UserService, ModalService,CommonService,$state,$scope,CONFIG){
            var vm = this;
           
            vm.addRecordShopCart = addRecordShopCart;               // 添加购物车方法
            vm.showDetailInfo = showDetailInfo;
            vm.getOrderDetail = getOrderDetail;
             
              /**
         * 我的订单-订单详情
         * @function searchTransList
         * @memberof myOrderController
         */
        function getOrderDetail(partyId,orderId){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrderDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: partyId,//
                   orderId: orderId  //
                }
            };

            var promise = UserService.getOrderDetail(params);

            promise.then(function(data) {
                vm.orderDetail = data.rspBody;
            }).catch(function(error) {
                CommonService.showError(error);
            });

        }
           function showDetailInfo(partyId ,orderId) {
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"getOrderDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: partyId,//
                   orderId: orderId  //
                }
            };

            var promise = UserService.getOrderDetail(params);

            promise.then(function(data) {
                vm.orderDetail = data.rspBody;
                 $rootScope.orderDetail =  vm.orderDetail;
                ModalService.showModal({
                    templateUrl: 'app/components/consumerCar/tradeOrderDetail/tradeOrderDetail.html',
                    windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'registerAgreementModal',
                    controller:'ModalTemplateController'
                });
            }).catch(function(error) {
                CommonService.showError(error);
            });
    
           }

        function closeLoginModal() {
            // 未调用$modal.open()之前，不存在$scope.$close()。该处语义不明确，还需调整
            $scope.$close();
        }


        

        // 添加购物车
        function addRecordShopCart(){
           $state.go('templateProduct');
        }
        }
    }
})();
