(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('ShowmessageController', ShowmessageController);

    ShowmessageController.$inject = ['$scope','$rootScope','$timeout','ModalService'];


    /**
     * @memberof directbank
     * @ngdoc controller
     * @name ShowmessageController
     * @param  {service} ModalService       弹窗服务
     * @description
     * 弹窗提示控制器
     */
    function ShowmessageController($scope,$rootScope,$timeout,ModalService) {
        var vm = this;

        vm.close = close;
        vm.surebut = surebut;
        vm.error = $rootScope.error;//提示信息
        vm.flag = $rootScope.flag;//弹窗标志
        vm.closeFlag = "0";//关闭提示框标识

        if($rootScope.status != "-1"){
            if(vm.flag == "1" || vm.flag == "2"){
                vm.data = $rootScope.data;//交易信息
                vm.serialNo = vm.data.rspHead.globalSeqNo//流水号

                vm.returnCode = vm.data.rspHead.returnCode//交易返回code
            }
        }

        init();
        /**
         * 关闭按钮
         * @memberof ShowmessageController
         * @function close
         * @description 确认按钮
         */
        function close() {
            $timeout.cancel( timer );
            ModalService.closeModal();
        }
        /**
         * 确认按钮
         * @memberof ShowmessageController
         * @function close
         * @description 确认按钮
         */
        function surebut() {
            if($rootScope.backname != null && $rootScope.backname != undefined){
                   $rootScope.backname();
                }
            $timeout.cancel( timer );
            ModalService.closeModal();
            $rootScope.backname = null;
        }
        /**
         * 初始化时判断flag和msg
         * @memberof ShowmessageController
         * @function init
         * @description 初始化时判断flag和msg
         */
        function init(){

            if($rootScope.status == "-1"){
                vm.returnMsg = $rootScope.errorInterMsg;
            }else{
                if (vm.error != null && vm.error != undefined) {
                    if(vm.flag == "2"){
                        vm.returnMsg = vm.error.rspHead.returnMsg;
                    }else{
                        vm.returnMsg = vm.error;
                    }

                }else{
                    vm.returnMsg = vm.data.rspHead.returnMsg//交易返回信息
                }
            }
        }
        /**
         * 定时关闭提示框
         * @memberof ShowmessageController
         * @function closeShowMessage
         * @description 定时关闭提示框
         */
        var timer = $timeout(function(){
                    ModalService.closeModal();
                },5000)
    }
})();
