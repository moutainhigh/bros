
/**
 * @name transferProduce
 * @description
 * 菜单指令
 */
(function(){
    'use strict';

    angular
        .module('EBankProject')
        .directive('ebankTree', transferProduce);

    // ebankTree.$inject = ['UserService', 'CommonService', '$state', '$rootScope', '$cookies', 'ModalService'];

     /**
     * @memberof directbank
     * @ngdoc directive
     * @name primeProduct
     * @description
     *
     *
     * @attr {object} product 产品信息
     * @attr {theme} 主题 red orange
     * @example
     *   Usage:
     *    <ebank-tree compare="vm.menuTree();" treeList="treeList" ></ebank-tree>
     */

    function transferProduce(UserService, CommonService, $state, $rootScope, $cookies, ModalService, CONFIG) {
        var directive = {
            restrict: 'AE',
            scope:{
            	list: '=',
            },
            templateUrl:'app/directives/tree/tree.html',
            controller: getTreeInfoController,
            controllerAs: 'vm'

        };

        return directive;

        /**
         * 获取菜单信息
         * [getTreeInfoController description]
         * @return {[type]} [description]
         */
        function getTreeInfoController(){
            var vm = this;
            vm.getTreeList = getTreeList;

            function getTreeList(treeObject){
                var bmfProperties = treeObject.itemValue.bmfProperties; //  货架性质
                var bmprShelfcode = treeObject.itemValue.bmprShelfcode; //  货架编码
                var bmfUrl = treeObject.itemValue.bmfUrl;               //  菜单Url
                if (bmfProperties == "Y" && bmprShelfcode == "") {
                    CommonService.subShowMsg("3","该货架没有配置产品信息,请联系管理员处理!!!");
                    return false;
                }
                if (bmfProperties == "N") {
                    return false;
                }
                var params = {
                    reqHead:{
                        flag:"1",                                                   //主副交易标志 1是主交易     2是副交易
                        tradeType:"1",                                              // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                        stePcode:"01",                                              // 交易步骤
                        serviceName:"queryShelfGoodsMenuRelByShelfCodeAction"        // 服务名称
                    },
                    reqBody:{
                       shelfCode:bmprShelfcode
                       // channelCode:"3333"   //不同渠道上送不同的渠道编码，请注意修改
                    }
                };
                var promise = UserService.getAllChlShelfGood(params);
                promise.then(function(data) {
                    sessionStorage.setItem(CONFIG.SESSION.SHELFCODE_INFO, JSON.stringify(data.rspBody));
                    $rootScope.$broadcast('allChlShelfGoodCompare', data.rspBody);
                }).catch(function(error) {
                    CommonService.subShowMsg("2",error);
                });


            }

        }
    }

})();
