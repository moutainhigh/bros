(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('quotedPriceController', quotedPriceController);

    quotedPriceController.$inject = ['$scope', '$rootScope', 'ModalService', 'CommonService', '$state', 'UserService', 'CONFIG', '$filter'];


    function quotedPriceController($scope, $rootScope, ModalService, CommonService, $state, UserService, CONFIG, $filter) {
        var vm = this;

        init();

        vm.showDetailInfo = showDetailInfo;

        /**
         *初始化
         */
        function init() {
            queryQuotedPrice();
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

            var quoteList = item;
            if (quoteList != undefined) {

                var params = {
                    quoteList: quoteList
                };
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(params));
                $state.go('quotedPriceDetail');
                

                // ModalService.showModal({
                //     templateUrl: 'app/components/consumerCar/quotedPriceDetail/quotedPriceDetail.html',
                //     windowTemplateUrl: 'app/layout/modalBlock/modalWindowTemplate/modalWindowTemplate.html',
                //     size: 'lg',
                //     backdrop: 'static',
                //     windowClass: 'registerAgreementModal'
                // });
                // item = {};

            } else {
                CommonService.subShowMsg("4", "请选择信息");
            }
        }


    }
})();
