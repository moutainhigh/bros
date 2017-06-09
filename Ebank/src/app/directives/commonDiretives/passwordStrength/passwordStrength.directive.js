/**
 * @name passwordstrength
 * @description
 * 密码强度的指令
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('passwordstrength', passwordstrength);

    function passwordstrength() {
        var directive = {
            restrict: 'EA',
            replace: true,
            templateUrl: 'app/directives/commonDiretives/passwordStrength/passwordStrength.html',
            scope: {
                pwd: '=' //绑定指令外部的pwd属性
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

            //验证的规则
            var patternArray = [
                //if length is 8 characters or more, increase strength value
                /([a-zA-Z0-9!,%,&,@,#,$,^,*,?,_,~]){8,}/,
                //if password contains both lower and uppercase characters, increase strength value
                /([a-z].*[A-Z])|([A-Z].*[a-z])/,
                //if it has numbers and characters, increase strength value
                /([a-zA-Z])/,
                //if it has one special character, increase strength value
                /([!,%,&,@,#,$,^,*,?,_,~])/
            ];

            /*
             * @name checkPwdStrength
             * @param password 输入的密码
             * @returns {int} 密码强度
             * @description
             * 验证密码的强度,并返回强度的值
             * 默认规则为:8位密码以上,包含大小写,包含数字和字母,包含特殊字符
             */
            var checkPwdStrength = function(password) {
                var strength = 0; //密码强度

                if (password !== undefined) {
                    //根据正则表达式数组进行判断，满足正则表达式则增加强度
                    for (var i = 0; i < patternArray.length; i++) {
                        //满足要求
                        if (password.match(patternArray[i])) {
                            //强度增加
                            strength += 1;
                        }
                    }
                }
                //返回强度
                return strength;
            };

            //监控输入的密码，如有变化调用验证密码强度的指令
            scope.$watch('pwd', function() {
                scope.strength = checkPwdStrength(scope.pwd);
                scope.strengthStr = '';
                switch (scope.strength) {
                    case 0:
                    case 1:
                        scope.weakClass = 'weak';
                        scope.mediumClass = 'weak';
                        scope.strongClass = 'weak';
                        scope.strengthStr = '弱';
                        break;
                    case 2:
                        scope.weakClass = 'weak';
                        scope.mediumClass = 'medium';
                        scope.strongClass = 'medium';
                        scope.strengthStr = '中';
                        break;
                    case 3:
                    case 4:
                        scope.weakClass = 'weak';
                        scope.mediumClass = 'medium';
                        scope.strongClass = 'strong';
                        scope.strengthStr = '强';
                        break;

                }
            });
        }
    }

})();
