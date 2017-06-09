(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('personOverviewController', personOverviewController);

    personOverviewController.$inject = ['$rootScope', 'UserService','CommonService','$state', 'CONFIG', '$scope', '$timeout'];

    /**
     * @memberof directbank
     * @ngdoc controller
     * @name personOverviewController
     * @param  {service} UserService 账户服务
     * @description
     * 交易明细页面控制器
     */
    function personOverviewController($rootScope, UserService,CommonService, $state, CONFIG, $scope, $timeout) {
        
        var vm = this;
        vm.submitgo = submitgo;
        vm.backInput = backInput;
        vm.personOverviewQuery=personOverviewQuery;
        vm.model={};
        //初始化方法
        init();

        function init(){
            
             vm.personSummaryInfo=[];
            vm.familyMemberInfo=[];
            vm.relativePersonInfo=[];  
            // vm.familyMemberInfo=[{
            //     'partyRelationShipTypeId':'11',
            //     'partyId':'010000000011',
            //     'custName':'李四',
            //     'contactCertificateTypeId':'1X',
            //     'contactCertificateNo':'123432123',
            //     'authCountry':'CNH',
            //     'birthDate':'20010101',
            //     'gender':'2',
            //     'flag':'7',
            //     // 'partyId':'020000001002',
            //     'partyGroupName':'北京联创智融信息技术有限公司',
            //     'partyAddr':'北京市东城区航星园',
            //     'partyPostal':'100022',
            //     'jobTitle':'2',
            //     'post':'人力资源总监',
            //     'contactNumber':'01088887777',
            //     'monthIncome':'6000',
            //     'degree':'2',
            //     'isOurBankCustomer':'0'
            // }];
            // vm.relativePersonInfo=[{
            //     'partyRelationShipId':'333',
            //     'partyRelationShipTypeId':'24',
            //     'partyId':'010000000123',
            //     'contactCertificateTypeId':'1X',
            //     'contactCertificateNo':'767654323',
            //     'authCountry':'CNH',
            //     'lastName':'王五',
            //     'birthDate':'19991201',
            //     'gender':'1',
            //     'job':'2',
            //     'partyName':'南京张三王五有限公司',
            //     'partyAddr':'南京市鼓楼区叉叉路',
            //     'partyPhone':'02533334444',
            //     'partyType':'1',
            //     'post':'助理',
            //     'monthIncome':'6000',
            //     'contactNumber':'13544442222',
            //     'qualify':'2',
            //     'isOurBankCustomer':'1'
            // }];
            queryuser();
            personOverviewQuery();
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
         function submitgo(){
            if (vm.regForm.$valid) {
            $state.go('depositCurrentToTimeResult');
            }
         }
         function backInput(){
            $state.go('depositCurrentToTime');
         }
         /**
         * 查询客户总览信息
         */
         function personOverviewQuery(){
            var params = {
                reqHead:{
                    flag:"1",                        //主副交易标志 1是主交易     2是副交易
                    tradeType:"3",                   // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    serviceName:"queryPersonOverviewAction"        // 服务名称
                },
                reqBody:{
                    partyId: vm.user.customerNo                    //客户号
                }
                
            };
            var promise = UserService.personOverviewQuery(params);    //查询客户总览信息

            promise.then(function(data) {
                
                if (data) {
                    vm.personSummaryInfo=data.rspBody.personOverview.personSummaryInfo;
                    vm.familyMemberInfo=data.rspBody.personOverview.familyMemberInfo;
                    vm.relativePersonInfo=data.rspBody.personOverview.relativePersonInfo;  
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
         }
    }
})();