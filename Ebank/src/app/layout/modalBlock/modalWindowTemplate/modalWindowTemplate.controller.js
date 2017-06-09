(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('ModalTemplateController', ModalTemplateController);

    ModalTemplateController.$inject = ['ModalService'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name ModalTemplateController
     * @param  {service} UserService       用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 用户登录控制器
     */
    function ModalTemplateController(ModalService) {
        var vm = this;

        vm.close = close;

        function close() {
            ModalService.closeModal();
        }
    }
})();
