/**
 * Created by macbook on 16/3/10.
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('highLight', highLight);

    highLight.$inject = ['$compile','$timeout'];

    /**
     * 输入框放大镜
     * @memberof DirectBankProject
     * @function highLight
     * example:
     *  <input type="text" high-light num="3" symbol="" />
     *   symbol:    间隔字符
     *     默认——空格
     *     null——什么也不加
     *     string——一些字符
     *   num:       多少字符间隔
     *     数字
     */
    function highLight($compile,$timeout) {
        var directive = {
            restrict: 'EA',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            var num = parseInt(attr.num)||3;
            var symbol = '&nbsp;';

            if(attr.symbol === undefined){
                symbol = '';
            }

            if(attr.symbol){
                symbol = attr.symbol;

                if(symbol === 'null'){
                   symbol = '';
                }
            }

            el.bind('blur', function() {
                 el.siblings('.highlight').hide();
            });

            el.bind('focus', function() {
                if(el.siblings('.highlight')){
                    el.siblings('.highlight').show();
                }
            });
            var model = attr.highLight;

            el.parent().addClass('pr');

            var node = $compile('<div ng-show="' + model + '" class="highlight overflow-hidden"> ' +
                '<div class="hightBlock font-default col-xs-12">' +
                '<em ng-bind="' + model + '|separator:{num:' + num + ',symbol:\'' + symbol + '\'}" ></em>' +
                '</div>' +
                '</div>')(scope);

            el.parent().append(node);

            $timeout(function(){
                var elWidth = el.context.offsetWidth;
                var elHeight = el.context.offsetHeight;

                el.siblings('.highlight').css({
                    height: elHeight + 5,
                    width: elWidth,
                    top: -elHeight - 7,
                    lineHeight: elHeight + 5 + 'px'
                });
            },1000);
        }
    }
})();
