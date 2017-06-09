(function() {
    'use strict';

    angular
        .module('EBankProject')
        .controller('shoppingCarController', shoppingCarController);

    shoppingCarController.$inject = ['$scope', '$rootScope', 'UserService','CommonService','ModalService', 'CONFIG','$state', '$filter'];


    function shoppingCarController($scope, $rootScope, UserService,CommonService,ModalService, CONFIG, $state, $filter) {
        var vm = this;

        vm.numberchange = numberchange;             // 购物车更新
        vm.clickdelect = clickdelect;               // 删除购物车
        vm.gopayOrDeleteSelectCart = gopayOrDeleteSelectCart;     // 删除选中的购物车记录
        vm.checkedCart = checkedCart;               // 勾选购物车记录
        vm.shopcarproduct = shopcarproduct;         //展示产品详情
        vm.datanumerchange = datanumerchange;
        //初始化方法
        init();

        function init(){
             // 用户信息
            vm.user = JSON.parse(sessionStorage.getItem(CONFIG.SESSION.CURRENT_USER));

            queryShopCartFunction(vm.user.customerNo);
        }
         // 查询购物车
        function queryShopCartFunction(uniqueCstIdentity){
            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"queryShopCartAction"              // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                }
            };
            var promise = UserService.queryShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList != undefined && data.rspBody.recordList.length > 0){
                    vm.productDetail = data.rspBody.recordList;
                    $scope.length = vm.productDetail.length;

                    // 购物车计算总价格
                    vm.totalamt = 0;
                    var productItemList = [];

                    for (var i = 0; i < vm.productDetail.length; i++) {
                            var productItem = {};
                            productItem.cartItemId = vm.productDetail[i].id;   //  购物车标识
                            productItem.productId = vm.productDetail[i].productId;   //  产品标识
                            productItem.prodCatalogId = vm.productDetail[i].tradeData.prodCatalogId;    //  产品目录标识
                            productItem.configOptionId=vm.productDetail[i].tradeData.configOptionId;    //  配置项标识
                            if("Y"==vm.productDetail[i].tradeData.requireAmount){
                                productItem.selectedAmount = vm.productDetail[i].tradeData.bankBalance;
                                productItem.quantity = '1';
                                productItemList.push(productItem);
                                                // 单位
                            }else{
                                productItem.quantity = vm.productDetail[i].productAmount;               //  数量
                                productItem.selectedAmount = '0';
                                productItemList.push(productItem);
                            }

                    }

                    // 选中购物车记录
                    setTimeout(function(){
                        var chk = document.getElementsByName('allisSelected');
                        chk[0].checked = true;
                        SelectAll('allisSelected');
                   },300);


                    // 购物车产品试算
                    calculateShoppingCartPrice(productItemList);

                }

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }


        // 计算购物车价格
        function calculateShoppingCartPrice(productItemList){
            vm.totalamt=0.00;
            var params = {
                reqHead:{
                    flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                  // 交易步骤
                    serviceName:"calculateShoppingCartPriceAction"              // 服务名称
                },
                reqBody:{
                    productStoreId:vm.user.productStoreId, //  店铺标识
                    webSiteId:"baseWebStore",                   //  网点标识
                    currency:"CNY",                             //  币种
                    partyId:vm.user.customerNo,                           //  用户标识
                    productItemList:productItemList,            //  产品项组
                    uniqueCstIdentity:vm.user.customerNo            // 客户唯一标识
                }
            };
            var promise = UserService.calculateCartPrice(params);

            promise.then(function(data) {

                vm.totalamt=data.rspBody.displaySubTotal;
                vm.productPriceList = data.rspBody.productPriceList;
                if( vm.productDetail.length==productItemList.length){
                     vm.cartLinesList = data.rspBody.cartLinesList;
                }
               
                var productListNew  = data.rspBody.productList;
                var tableObj = document.getElementById("carTable");
                var rows = tableObj.rows;
                if(productListNew!=null){
                    for(var j=1;j<rows.length;j++){
                         var cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                             for (var i = 0; i < productListNew.length; i++) {
                            var products = productListNew[i];
                            if(cartItemId==products.id){
                                  rows[j].cells[2].children[1].value = products.productAmount;
                                  rows[j].cells[3].innerText = $filter('currency')(products.tradeData.displayPrice,'¥');
                                  rows[j].cells[4].innerText = $filter('currency')(products.tradeData.otherAdjustments,'¥');
                                  rows[j].cells[5].innerText = $filter('currency')(products.tradeData.displayItemSubTotal,'¥');

                            }
                      }

                    }
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });

        }

        /**
         * 删除购物车产品
         * @memberof shoppingCarController
         * @function clickdelect
         * @description 删除购物车产品
         */
        function clickdelect(index,product){
            vm.totalamt = '0.00';
            var tableObj = document.getElementById("carTable");
            vm.productDetail.splice(index, 1);
            tableObj.deleteRow(index+1);

            // 删除购物车-入参UUID
            var recordList = [];
            recordList.push(product.id);
            if (vm.productDetail.length==0) {
                 vm.cartLinesList = '';
             }else{
                vm.cartLinesList.splice(index,1);
             }
            deleteShopCartRecordFunction(vm.user.customerNo,recordList);
        }

        // 删除购物车中某些记录购
        function deleteShopCartRecordFunction(uniqueCstIdentity,recordList){

            vm.totalamt = 0.00;
            var params = {
                reqHead:{
                    flag:"1",                                           // 主副交易标志 1是主交易     2是副交易
                    tradeType:"1",                                      // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode:"01",                                      // 交易步骤
                    serviceName:"deleteShopCartRecordAction"    // 服务名称
                },
                reqBody:{
                   uniqueCstIdentity:uniqueCstIdentity,             // 客户唯一标识
                   recordList:recordList
                }
            };
            var promise = UserService.deleteClearShopCart(params);

            promise.then(function(data) {
                if(data.rspBody.recordList.length > 0){
                    vm.productDetail1 = data.rspBody.recordList;
                    $scope.length = vm.productDetail1.length;

                    var tableObj = document.getElementById("carTable");
                    var rows = tableObj.rows;
                    vm.totalamt = 0;
                    $scope.length = 0;
                    var productItemList = [];

                    for(var j=1;j<rows.length;j++){
                        if(rows[j].cells[0].children[0].checked == true){
                            var productItem = {};
                            productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                            productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                            productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                            var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                            var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                            productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                            var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                             if("Y"==requireAmount){
                                    productItem.selectedAmount = bankBalance;
                                    productItem.quantity = '1';
                                    productItemList.push(productItem);       // 单位
                            }else{
                                productItem.quantity = quantity;               //  数量
                                productItem.selectedAmount = '0';
                                productItemList.push(productItem);
                            }
                            $scope.length++;
                        }

                    }

                    // 表格里面复选框事件

                        if(rows.length-1 == $scope.length){
                            document.getElementById("allisSelectedtableRow").checked = true;
                            document.getElementById("allisSelectedTableSelect").checked = true;
                        }else{
                            document.getElementById("allisSelectedtableRow").checked = false;
                            document.getElementById("allisSelectedTableSelect").checked = false;
                        }
                      calculateShoppingCartPrice(productItemList);


                   //  // 选中购物车
                   //  setTimeout(function(){
                   //      var chk = document.getElementsByName('allisSelected');
                   //      chk[0].checked = true;
                   //      SelectAll('allisSelected');
                   // },300);
                }else{
                    vm.products = "";
                    $scope.length = 0;
                }
            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });
        }


        /**
         * 更改产品数量时更改产品金额
         * @memberof shoppingCarController
         * @function numberchange
         * @description 更改产品数量时更改产品金额
         */
        function numberchange(list,flag,obj){

            updateShopCartFunction(list,vm.user.customerNo,flag,obj);

        }
        
       
        function isInteger(productAmount){
            var INTEGER_REGEXP = /^\-?\d*$/;
            if(INTEGER_REGEXP.test(productAmount)){
                if(parseInt(productAmount)<0){
                   return false;
                }
                return true;
            }
            return false;
        }
        //数量变化
         function datanumerchange(data,obj){

            var productAmount =obj.target.value;  // 购物车必须接受字符串
             if(!isInteger(productAmount)){
                obj.target.value=data.productAmount;
                 CommonService.subShowMsg("3","请输入合法的整数");
                 return;
            }
            if (0==parseInt(productAmount)) {
                obj.target.value=data.productAmount;
                CommonService.subShowMsg("3","至少为1个");
                return;
            }
            if (parseInt(data.productAmount)==parseInt(productAmount)) {
                return;
            }
            
           
            data.productAmount = productAmount;
            obj.target.parentNode.parentNode.firstElementChild.children[0].checked = true;
            var tableObj = document.getElementById("carTable");
            var rows = tableObj.rows;
            vm.totalamt = 0;
            $scope.length = 0;
            var productItemList = [];

            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    var productItem = {};
                    productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                    productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                    productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                    var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                    var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                    productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                    var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                     if("Y"==requireAmount){
                            productItem.selectedAmount = bankBalance;
                            productItem.quantity = '1';
                            productItemList.push(productItem);       // 单位
                    }else{
                        productItem.quantity = quantity;               //  数量
                        productItem.selectedAmount = '0';
                        productItemList.push(productItem);
                    }
                    $scope.length++;
                }

            }

            // 表格里面复选框事件

                if(rows.length-1 == $scope.length){
                    document.getElementById("allisSelectedtableRow").checked = true;
                    document.getElementById("allisSelectedTableSelect").checked = true;
                }else{
                    document.getElementById("allisSelectedtableRow").checked = false;
                    document.getElementById("allisSelectedTableSelect").checked = false;
                }
              calculateShoppingCartPrice(productItemList);
         }

        // 更新购物车
         function updateShopCartFunction(data,uniqueCstIdentity,flag,obj){
            // var recordMap = {};
            // // 购物车数据
            // recordMap.id = data.id;                                    // 记录唯一标识uuid
            // recordMap.productId = data.productId;                      // 产品ID


            if(flag == 'add'){
                var productAmount = parseInt(data.productAmount) + 1;                             // 产品数量
            }else{
                var productAmount = parseInt(data.productAmount) - 1;                             // 产品数量
            }

            // recordMap.productAmount = productAmount+"";         // 购物车必须接受字符串
           
            data.productAmount = productAmount;
           
            

            obj.toElement.parentNode.children[1].value=productAmount;  // 购物车必须接受字符串
          
            // 产品调研项

            obj.toElement.parentNode.parentNode.firstElementChild.children[0].checked = true;
            var tableObj = document.getElementById("carTable");
            var rows = tableObj.rows;
            vm.totalamt = 0;
            $scope.length = 0;
            var productItemList = [];

            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    var productItem = {};
                    productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                    productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                    productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                    var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                    var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                    productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                    var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                     if("Y"==requireAmount){
                            productItem.selectedAmount = bankBalance;
                            productItem.quantity = '1';
                            productItemList.push(productItem);       // 单位
                    }else{
                        productItem.quantity = quantity;               //  数量
                        productItem.selectedAmount = '0';
                        productItemList.push(productItem);
                    }
                    $scope.length++;
                }

            }

            // 表格里面复选框事件

                if(rows.length-1 == $scope.length){
                    document.getElementById("allisSelectedtableRow").checked = true;
                    document.getElementById("allisSelectedTableSelect").checked = true;
                }else{
                    document.getElementById("allisSelectedtableRow").checked = false;
                    document.getElementById("allisSelectedTableSelect").checked = false;
                }
              calculateShoppingCartPrice(productItemList);
        }

        // 删除选中和结算流程
        function gopayOrDeleteSelectCart(flag){
            var tableObj = document.getElementById("carTable");
            var trcollect = new Array();
            // 删除购物车-入参UUID
            var recordList = [];
            var rows = tableObj.rows;
            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    if(trcollect.length > 0){
                        trcollect[trcollect.length] = tableObj.rows[j].rowIndex;
                    }else{
                        trcollect[0] = tableObj.rows[j].rowIndex;
                    }
                    var id = rows[j].cells[7].innerText;              // 购物车UUID主键
                    recordList.push(id);
                }

            }


            if (trcollect.length > 0){
                // 删除选中的
                if(flag == "delete"){
                    var m = trcollect.length;
                    for (var i = m; i >= 1 ; i--){
                        tableObj.deleteRow(trcollect[i-1]) ;
                        vm.cartLinesList.splice(trcollect[i-1]-1,1);
                    }

                    // 删除选中的购物车数据
                    deleteShopCartRecordFunction(vm.user.customerNo,recordList);
                }else{      // 结算购物车
                    sessionStorage.setItem(CONFIG.SESSION.SESSION_CAR_DATA, recordList);
                    $state.go('settlementShoppingCar');
                }

            }else{
                 CommonService.subShowMsg("4","请选择一条记录");
                return false;
            }
        }

        // 购物车勾选重新计算总价
        function checkedCart(tableRowObj,tableName){
            var tableObj = document.getElementById("carTable");
            var rows = tableObj.rows;
            vm.totalamt = 0;
            $scope.length = 0;
            var productItemList = [];


            // 全选必须放在表格上面,不然价格计算失败
            if(tableRowObj == "tableHead"){
               SelectAll(tableName);
            }

            if(tableRowObj == 'tableSelect'){
                SelectAll(tableName,'1');
            }

            for(var j=1;j<rows.length;j++){
                if(rows[j].cells[0].children[0].checked == true){
                    var productItem = {};
                    productItem.productId = rows[j].cells[0].children[1].outerText;   //  产品标识
                    productItem.prodCatalogId = rows[j].cells[0].children[2].outerText;  //  产品目录标识
                    productItem.configOptionId= rows[j].cells[0].children[3].outerText;    //  配置项标识
                    var bankBalance= rows[j].cells[0].children[4].outerText;    //  计价金额
                    var requireAmount= rows[j].cells[0].children[5].outerText;    //  计价单位
                    productItem.cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                    var  quantity =  rows[j].cells[2].children[1].value;         //  数量
                     if("Y"==requireAmount){
                            productItem.selectedAmount = bankBalance;
                            productItem.quantity = '1';
                            productItemList.push(productItem);       // 单位
                    }else{
                        productItem.quantity = quantity;               //  数量
                        productItem.selectedAmount = '0';
                        productItemList.push(productItem);
                    }
                    $scope.length++;
                }


            }

            // 表格里面复选框事件
            if(tableRowObj == "tableRow"){
                if(rows.length-1 == $scope.length){
                    var chk = document.getElementsByName(tableName);
                    for ( var i = 0; i < chk.length; i++) {
                        chk[i].checked = true;
                    }
                }else{
                    document.getElementById("allisSelectedtableRow").checked = false;
                    document.getElementById("allisSelectedTableSelect").checked = false;
                }
            }
            if(productItemList.length!=0){
              var params = {
                    reqHead:{
                        flag:"1",                                       // 主副交易标志 1是主交易     2是副交易
                        tradeType:"1",                                  // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                        stePcode:"01",                                  // 交易步骤
                        serviceName:"calculateShoppingCartPriceAction"              // 服务名称
                    },
                    reqBody:{
                        productStoreId:vm.user.productStoreId, //  店铺标识
                        webSiteId:"baseWebStore",                   //  网点标识
                        currency:"CNY",                             //  币种
                        partyId:vm.user.customerNo,                           //  用户标识
                        productItemList:productItemList,             //  产品项组
                        uniqueCstIdentity:vm.user.customerNo            // 客户唯一标识
                    }
                };
                var promise = UserService.calculateCartPrice(params);

                promise.then(function(data) {
                    vm.totalamt=data.rspBody.displaySubTotal;
                    var productListNew  = data.rspBody.productList;
                    if(productListNew!=null){
                        for(var j=1;j<rows.length;j++){
                             var cartItemId = rows[j].cells[0].children[6].outerText;   //  购物车标识
                            if(rows[j].cells[0].children[0].checked == true){
                                 for (var i = 0; i < productListNew.length; i++) {
                                var products = productListNew[i];
                                if(cartItemId==products.id){
                                      rows[j].cells[2].children[1].value = products.productAmount;
                                }
                          }
                        }
                    }
                    }
                    if( vm.productDetail.length==productItemList.length){
                       vm.cartLinesList = data.rspBody.cartLinesList;
                     }
                }).catch(function(error) {
                    CommonService.subShowMsg("2",error);
                });
              }
        }


        /**
         * 查询购物车优惠组合
         * @memberof shoppingCarController
         * @function clicktype
         * @description 查询购物车优惠组合
         */
        function clicktype(productname){
            $scope.producttj.show = !$scope.producttj.show;
                var params = {
                        productTpye: productname//产品标志
                    };
                var promise = UserService.getproductDetail(params);  //查询某一商品的推荐组合--后期改接口
                promise.then(function(data) {


                    if (data.returnCode=="00000000") {
                        vm.listcar = data.respData;
                    }else{
                        $scope.producttj={
                            show:false
                        };
                    }

                }).catch(function(error) {
                        CommonService.showError(error);
                });

        }
        /**
         * 购物车结算界面推荐产品初始化
         * @memberof shoppingCarController
         * @function showchushihua
         * @description 初始化
         */
        function showchushihua(){

            $scope.producttj={
                show:false
            };
        }

        /**
         * 弹窗展示购物车产品详情
         * @memberof shoppingCarController
         * @function shopcarproduct
         * @description 弹窗展示购物车产品详情
         */
        function shopcarproduct(list){
            var productdetailNew = {};
           var params = {
                reqHead: {
                    flag: "1",              //主副交易标志 1是主交易     2是副交易
                    tradeType: "1",         // 交易类型 1：查询类交易 2：账务类交易 3：管理类交易 4: 授权类交易
                    stePcode: "01",         // 交易步骤
                    serviceName: "queryAttributeInfoMethodAction" // 服务名称
                },
                reqBody: {
                    productCode: list.productId                    //产品标志
                }
            };

            var promise = UserService.queryAttributeInfoMethod(params); //查询某一商品的推荐组合--后期改接口
            promise.then(function(data) {

                  var returnList  = data.rspBody;
                
                sessionStorage.setItem(CONFIG.SESSION.SESSION_DATA_PAGE_INPUT, JSON.stringify(returnList));
                $state.go('productdetail');

            }).catch(function(error) {
                CommonService.subShowMsg("2",error);
            });


            /* ModalService.showModal({
                    templateUrl: 'app/layout/productDetail/productDetail.html',
                    windowTemplateUrl: 'app/layout/productDetail/productDetailShow.html',
                    size: 'lg',
                    backdrop: 'static',
                    windowClass: 'productdetail'
                });*/
        }


        // 控制表格全选
        function SelectAll(chk,index) {
            var chk = document.getElementsByName(chk);
            var obj = chk[0];
            if(index != undefined && index !=''){
                obj = chk[chk.length-1];
            }
            if(obj.checked){
                for ( var i = 0; i < chk.length; i++) {
                    chk[i].checked = true;
                }
            }else{
                for ( var i = 0; i < chk.length; i++) {
                    chk[i].checked =    false;
                }
            }
        }


    }
})();
