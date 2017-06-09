(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('custPropertyDetailController', custPropertyDetailController);

    custPropertyDetailController.$inject = ['$rootScope', 'UserService','CommonService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name personOverviewController
     * @param  {service} UserService 账户服务
     * @description
     * 交易明细页面控制器
     */
    function custPropertyDetailController($rootScope, UserService,CommonService, $state, CONFIG, $scope, $timeout) {
        
        var vm = this;
        vm.custPropertyDetail=custPropertyDetail;
        vm.model={};
        //初始化方法
        init();

        function init(){
            
             vm.depositDetailInfo=[];
            vm.loanDetailInfo=[];
            vm.investDetailInfo=[];  
            vm.signDetailInfo=[];
            vm.creditCardDetailInfo=[];
            var detialData=$rootScope.detialData;
            if(detialData){
              vm.assetType=detialData.data.assetType;
              vm.name=detialData.name;
            }
            queryuser();
            custPropertyDetail();
        }
        /**
         * @function
         * @name queryuser
         * @description 接收客户信息
         * */
        function queryuser(){
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));
            $rootScope.$broadcast('user', vm.user);
        }
         
         /**
         * 查询客户资产明细信息
         */
         function custPropertyDetail(){
            var params = {
                reqHead:{
                    flag:"1",                                       //主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryCustPropertyDetailAction"                                  // 服务名称
                },
                reqBody:{
                   partyId: vm.user.customerNo,                    //客户号
                   partyTypeId:"1",                            //客户类型:1-个人客户,3-公司客户,0-同业客户
                   propertyType:vm.assetType                             //资产类型:1-存款类,2-贷款类,3-投资类,4-签约类,5-信用卡
                }
            };
            // var promise = UserService.getPropertyDistribution(params);
            var promise = UserService.custPropertyDetailQuery(params);    //查询客户总览信息
            promise.then(function(data) {
                if(data){//rspBody
                    vm.depositDetailInfo=data.rspBody.custPropertyDetailQry.depositDetailInfo;
                    vm.loanDetailInfo=data.rspBody.custPropertyDetailQry.loanDetailInfo;
                    vm.investDetailInfo=data.rspBody.custPropertyDetailQry.investDetailInfo;  
                    vm.signDetailInfo=data.rspBody.custPropertyDetailQry.signDetailInfo;
                    vm.creditCardDetailInfo=data.rspBody.custPropertyDetailQry.creditCardDetailInfo;
                }
            }).catch(function(error) {
                vm.emptyPie = emptyPiePath;
                CommonService.showError(error);
            });
         }
    }
})();