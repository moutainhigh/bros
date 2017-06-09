(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('showQuotedPriceDetailController', showQuotedPriceDetailController);

    showQuotedPriceDetailController.$inject = ['$scope', '$rootScope', 'ValidationService', 'ModalService', 'CommonService', '$state', 'UserService', 'CONFIG', '$filter'];


    function showQuotedPriceDetailController($scope, $rootScope, ValidationService, ModalService, CommonService, $state, UserService, CONFIG, $filter) {
        var vm = this;
        vm.model = {};

        init();

        /**
         *初始化
         */
        function init() {

            // 初始化信息
            showInfo();

        }
        /**
         * 初始化信息
         * @memberof ChannelBaseInfoAddController
         * @function showInfo
         * @description 初始化修改信息
         */
        function showInfo() {

            var returnList = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT));
            if (returnList != null) {
                vm.model.quoteItemSeqId = returnList.quoteItemList.quoteItemSeqId; //       报价ID
                vm.model.internalName = returnList.quoteItemList.internalName; //   销售渠道
                vm.model.quantity = returnList.quoteItemList.quantity; //   销售类型
                vm.model.selectedAmount = returnList.quoteItemList.selectedAmount; // 状态

                vm.model.totalQuoteItemAdjustmentAmountMap = returnList.quoteItemList.totalQuoteItemAdjustmentAmountMap; // 客户标识
                vm.model.quoteUnitPriceMap = returnList.quoteItemList.quoteUnitPriceMap; //
                vm.model.totalQuoteItemAmountMap = returnList.quoteItemList.totalQuoteItemAmountMap; //
                vm.model.quoteTermList = returnList.quoteItemList.quoteTermList; // 保价名

            }
        }

    }
})();
