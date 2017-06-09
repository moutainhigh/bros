/**
 * @name eChart
 * @description
 * echart组建-饼图
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('eChart', eChart);
        /**
         * @memberof directbank
         * @ngdoc directive
         * @name echart
         * @description
         *   echart组建
         *
         * @attr {String} option 图表参数
         * @example
         *   Usage:
         *    <div e-chart style="height:300px; width: 100%;" options="vm.assetOptions"></div>
         */

    eChart.$inject = ['$compile'];

    function eChart($compile) {
        var directive = {
            restrict:'AE',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr, ngModel) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = echarts.init(el[0], 'macarons');
            //监听options变化
            attr.$observe('options', function () {
                if(attr.options){
                    var options = scope.$eval(attr.options);
                    if (angular.isObject(options)) {
                        myChart.setOption(options);
                        if(options.backFun != null || options.backFun != "" || options.backFun != undefined){
                             myChart.on('click', options.backFun);
                        }
                    }
                }
            }, true);
        }
    }
})();
