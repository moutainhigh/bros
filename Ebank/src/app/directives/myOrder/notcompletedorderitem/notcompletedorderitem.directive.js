(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('notcompletedorderItem', notcompletedorderitem);

    function notcompletedorderitem() {
        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/myOrder/notcompletedorderitem/notcompletedorderitem.html',
            controller: getNotCompleteOrderDetailManageController,
            controllerAs: 'vm'
        };

        return directive;
              /**
         * 获取已完成订单详情-getCompleteOrderDetailManageController
         */
        function getNotCompleteOrderDetailManageController($rootScope, UserService, ModalService,CommonService,$state,$scope,CONFIG){
            var vm = this;
              vm.showDetailInfo = showDetailInfo;
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

         // 添加购物车
        function addRecordShopCart(){
           $state.go('templateProduct');
        }
        }
    }
})();
