(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('profession', profession);
    /**
    *   职业
    */
    function profession() {
        return function(profession) {
            var result = profession;
             switch (profession) {
                    case '1': //一般工商业
                        result = '一般工商业';
                        break;
                    case '2': //电脑/咨询/网络
                        result = '电脑/咨询/网络';
                        break;
                    case '3': //广告/行销/娱乐
                        result = '广告/行销/娱乐';
                        break;
                    case '4': //医疗/法律
                        result = '医疗/法律';
                        break;
                    case '5': //财经/保险/不动产
                        result = '财经/保险/不动产';
                        break;
                    case '6': //学生
                        result = '学生';
                        break;
                    case '7': //科教/文体
                        result = '科教/文体';
                        break;
                    case '8': //非营利事业/政府
                        result = '非营利事业/政府';
                        break;
                    case '9': //其他
                        result = '其他';
                        break;
                }

            return result;
        };
    }
})();
