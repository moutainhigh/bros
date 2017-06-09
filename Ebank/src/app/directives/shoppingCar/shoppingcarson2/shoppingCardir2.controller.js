(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('shoppingcardirController', shoppingcardirController);

    shoppingcardirController.$inject = ['$rootScope', 'UserService', 'CommonService', '$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name shoppingcardirController
     * @param  {service} UserService 用户服务
     * @param  {service} CommonService     通用服务
     * @description
     * 优惠组合控制器
     */
    function shoppingcardirController($rootScope, UserService, CommonService, $state, CONFIG, $scope, $timeout) {

        var vm = this;
        vm.checkboxclick = checkboxclick;
        vm.closetable = closetable;
        vm.addpaycarlist = addpaycarlist;
        vm.allpice = 0;
        vm.allpiceyh = 0;
        vm.allpicejs = 0;
        vm.productname = "";
        //初始化方法
        init();

        function init(){
            showchushihua();//初始化
        }
         /**
         * 选中事件
         * @memberof shoppingcardirController
         * @function checkboxclick
         * @description 选中事件
         */
        function checkboxclick(o){

            if(angular.element(document.getElementById(o.productCarList.productCode+1))[0].checked){
                vm.allpice += Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value);
                vm.a = Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value);
                vm.productname += o.productCarList.productname+"  ";
            }else{
                if (vm.a == Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value)) {
                    vm.allpice = vm.allpice - Number(angular.element(document.getElementById(o.productCarList.productCode))[0].value);
                }else{
                    vm.allpice = vm.allpice-vm.a;
                }
            }

            vm.allpiceyh = vm.allpice * 0.90;
            vm.allpicejs = vm.allpice - vm.allpiceyh;
        }
        /**
         * 点击隐藏/显示推荐产品
         * @memberof shoppingcardirController
         * @function closetable
         * @description 点击隐藏/显示推荐产品
         */
        function closetable(){
            $scope.tjdetail.show = !$scope.tjdetail.show;
        }
         /**
         * 产品详情初始化
         * @memberof shoppingcardirController
         * @function showchushihua
         * @description 初始化
         */
        function showchushihua(){
            $scope.tjdetail={
                show:true
            };
        }
        /**
         * @memberof sideBarController
         * @param compareMessage2
         * @description 接收广播信息方法
         */
        $scope.$on('compareMessage2', function(event, data) {
            vm.products = data;
        });
        /**
         * 推荐组合确认购买提交事件
         * @memberof shoppingcardirController
         * @function addpaycarlist
         * @description 推荐组合确认购买提交事件
         */
        vm.dataCompare = [];
        function addpaycarlist(){
             var product={
                prodCode:"优惠组合",
                bankName:vm.products[0].name,
                prodName:vm.productname,
                bankBalance:Number(vm.products[0].pice)+Number(vm.allpiceyh)
            }
            vm.dataCompare.splice(0, 0, product);
            $rootScope.$broadcast('compareMessage', vm.dataCompare);
        }
    }
})();
