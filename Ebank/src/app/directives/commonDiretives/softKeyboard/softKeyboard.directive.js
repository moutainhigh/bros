/**
 * @name keyboard
 * @description
 * 软键盘指令
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('keyboard', keyboard);

    keyboard.$inject = ['$document', '$compile', '$timeout'];

    function keyboard($document, $compile, $timeout) {
        var directive = {
            restrict: 'A',
            require: '^ngModel',
            scope: {
                ngModel: '=',
                onlydigit: '@'
            },
            controller: keyboardController,
            link: linkFunc
        };

        return directive;

        function keyboardController($scope, $element) {
            var keyNumArray = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'];
            var keyCharArray = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];

            /**
             * @name randomNumberButton
             * @description
             * 用于打乱数组顺序
             */
            var randomNumberButton = function() {
                return Math.random() > 0.5 ? -1 : 1; // 用Math.random()函数生成0~1之间的随机数与0.5比较，返回-1或1
            };

            /**
             * @name closeKeyboard
             * @description
             * 关闭软键盘
             */
            $scope.closeKeyboard = function() {
                $scope.isShowKeyboard = false;
                $element[0].focus();
            };

            /**
             * @name showKeyboard
             * @description
             * 显示软键盘
             */
            $scope.setKeyboardContent = function() {
                $scope.onlydigit = !!($element.attr('onlydigit') == 'true' || $element.attr('onlydigit') === '');

                // 打乱字母的数组
                keyNumArray.sort(randomNumberButton);
                // 打乱数组的数组
                keyCharArray.sort(randomNumberButton);

                $scope.keyNumArray = keyNumArray;
                $scope.keyCharArray = keyCharArray;
            };

            /**
             * @name stopPropagation
             * @description
             * 阻止返回上层事件
             */
            $scope.stopPropagation = function() {
                event.stopPropagation();
            };

            /**
             * @name deleteKey
             * @description
             * 键盘上的删除键
             */
            $scope.deleteKey = function() {
                $scope.ngModel = $scope.ngModel.substring(0, $scope.ngModel.length - 1);
            };

            /**
             * @name setCapsLock
             * @description
             * 切换大小写
             */
            $scope.setCapsLock = function() {
                if ($scope.keyCharArray) {
                    // 获取数组的第一个字符
                    var keyMap = $scope.keyCharArray[0];
                    // 如果是大写则转换成小写，反之.
                    if (keyMap >= 'A' && keyMap <= 'Z') {
                        // uppercase
                        for (var key in $scope.keyCharArray) {
                            $scope.keyCharArray[key] = $scope.keyCharArray[key].toLowerCase();
                        }
                    } else {
                        // lowercase
                        for (var key in $scope.keyCharArray) {
                            $scope.keyCharArray[key] = $scope.keyCharArray[key].toUpperCase();
                        }
                    }
                }
            };

            /**
             * @name addKey
             * @param key 输入的字母
             * @description
             * 点击软键盘上的字母
             */
            $scope.addKey = function(key) {
                if ($scope.ngModel === undefined) {
                    $scope.ngModel = '';
                }
                $scope.ngModel = $scope.ngModel + key;
            };

            $scope.show = function() {
                var keyboardHeight = angular.element('.softKeyboard').prop('offsetHeight');
                var left = 0;
                if ($element.parent().hasClass('input-group')) {
                    left = 0 - $element.parent().prev().prop('offsetWidth');
                }

                // 得到点击图标的坐标
                var position = $element[0].getBoundingClientRect();
                var bottom = document.body.clientHeight - position.bottom;
                if (bottom < keyboardHeight) {
                    $scope.style = {
                        'bottom': position.height + 'px',
                        'left': left
                    };
                } else {
                    $scope.style = {
                        'top': position.height + 'px',
                        'left': left
                    };
                }

                $scope.isShowKeyboard = true;
            };
        }

        function linkFunc(scope, element, attrs) {

            $(element).wrap('<div class="input-group"></div>');
            // 给元素后方追加点击按钮
            var content = $compile('<span key="' + scope.$id + '"  class="input-group-btn"><button class="btn btn-default" type="button"><span  class="fa fa-keyboard-o fa-lg"></span></button></span>')(scope);
            element.after(content);

            element.next().bind('click', function(event) {

                scope.$apply(function() {
                    // 第一次点击软键盘按钮
                    if (scope.isShowKeyboard === undefined) {
                        // 绘制软键盘
                        var content = $compile('<div ng-click="stopPropagation()" ng-show="isShowKeyboard" onload="show()"  ng-include=\'"app/directives/commonDiretives/softKeyboard/softkeyboard.html"\'></div>')(scope);

                        element.after(content);
                    }

                    // 如果已经绘制过软键盘
                    if (scope.isShowKeyboard !== true) {
                        // 设置软键盘显示内容
                        scope.setKeyboardContent();
                        scope.isShowKeyboard = true;
                    } else {
                        scope.isShowKeyboard = false;
                    }
                });
            });

            // 点击页面其他位置，取消软键盘
            $document.bind('click', function(event) {
                var checked = $(event.target).parents('span').attr('key') != $(element[0]).parent().children('span[key]').attr('key');
                if (scope.isShowKeyboard && checked) {
                    scope.$apply(function() {
                        scope.isShowKeyboard = false; // 隐藏软键盘
                    });
                }
            });
        }
    }

})();
