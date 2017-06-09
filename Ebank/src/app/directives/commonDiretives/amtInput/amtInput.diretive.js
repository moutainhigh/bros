(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('amtInput', amtInput);

    function amtInput() {

        var directive = {
            restrict : 'AE',
            scope: {
                'afield':'='
            },
            templateUrl: 'app/directives/commonDiretives/amtInput/amtInput.html',
            controller: amtInputController,
            controllerAs: 'vm'
        };

        return directive;

        function amtInputController($scope){
            var vm = this;
            vm.keyUpAmt = keyUpAmt;//按键敲击事件
            vm.blurAmtInput = blurAmtInput;//离开金额域触发事件
            var txt = '';
            init();
            function init(){
                $scope.afield="0.00";
            }
            function keyUpAmt(){
                //金额去千分符
                var getAmt = ($scope.afield).replace(new RegExp('\,',["g"]),'');
                //校验金额是否合法
                var reg = getAmt.match(/^[0-9]{0,16}\.{0,1}[0-9]{0,2}$/);

                //判断金额是否null
                if (reg != null) {
                    //判断金额是否大于三位
                    if (reg[0].length > 3){
                        var regN = "";
                        var regX = "";
                        //判断金额里是否有“.”
                        if (reg[0].indexOf(".") >= 0) {
                            regN = reg[0].substr(0, reg[0].indexOf('.'));
                            regX = reg[0].substr(reg[0].indexOf('.'), reg[0].indexOf('.')+2);
                        }else{
                            regN = reg[0];
                        }
                        var mod = regN.length % 3;
                        var output = (mod > 0 ? (regN.substring(0,mod)) : '');
                        for (var i=0 ; i < Math.floor(regN.length / 3); i++) {
                            if ((mod == 0) && (i == 0)){
                                output += regN.substring(mod+ 3 * i, mod + 3 * i + 3);
                            }else{
                                output += ',' + regN.substring(mod + 3 * i, mod + 3 * i + 3);
                            }
                        }
                        txt = output+regX;
                    }else {
                        txt = reg[0];
                    }
                }
                $scope.afield = txt;
            }

            /*
                焦点离开金额域触发事件
             */
            function blurAmtInput(){
                var getAmt = ($scope.afield).replace(new RegExp('\,',["g"]),'');
                if (getAmt == null || getAmt =='null' || getAmt==''){
                    afield = "0.00";
                }
                var afield = txt;
                if(afield.substring(0, 1)==''){
                    afield = "0"+afield;
                }
                if ( afield.length == 0 ){
                    return "0.00";
                }
                var dotPos = afield.indexOf(".");
                if ( dotPos < 0 ){
                    afield = afield+'.00'
                }
                if ( dotPos == 0 ){
                    afield = '0' + afield;
                    dotPos = afield.indexOf(".");
                }
                if ( dotPos == afield.length-2){
                    afield = afield + '0';
                }
                if ( dotPos == afield.length-1){
                    afield = afield + '00';
                }
                $scope.afield = afield;
            }
        }
    }
})();
