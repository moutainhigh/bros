(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('ModalService', ModalService);

    ModalService.$inject = ['$modal', '$modalStack', '$rootScope'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name ModalService
     * @param {service} CONFIG     通用Http请求服务
     * @param {service} http       通用配置项
     * @description  系统公共服务
     */
    function ModalService($modal, $modalStack, $rootScope) {
        var modalInstance = {};
        var modalSymbolArr = [];
        var modalSymbol;
        var service = {
            showModal: showModal,                   // 打开Modal
            closeModal: closeModal,                 // 关闭Modal
            dismissModal: dismissModal,             // 消除Modal
            dismissAllModal: dismissAllModal        // 消除所有Modal
        };

        return service;

        /**
         * 打开Modal
         * @memberOf ModalService
         * @param       {object}        modal所需参数
         */
        function showModal(params) {
            if($.inArray(params.controller, modalSymbolArr) < 0) {
                modalSymbolArr.push(params.controller);
            }
            modalSymbol = modalSymbolArr[modalSymbolArr.length-1];
            modalInstance[params.controller] = $modal.open(params);
        }

        /**
         * 关闭Modal
         * @memberOf ModalService
         */
        function closeModal() {
            modalInstance[modalSymbol].close();
            delete modalInstance[modalSymbol];
            modalSymbolArr.length--;
            modalSymbol = modalSymbolArr[modalSymbolArr.length-1];
        }

        /**
         * 消除Modal
         * @memberOf ModalService
         */
        function dismissModal() {
            modalSymbolArr.length = 0;
            for (var key in modalInstance) {
                modalInstance[key].dismiss();
                delete modalInstance[key];
            }
        }

        /**
         * 消除所有Modal
         * @memberOf ModalService
         */
        function dismissAllModal() {
            $modalStack.dismissAll();
            modalSymbolArr.length = 0;
            modalInstance = {};
        }
    }
})();
