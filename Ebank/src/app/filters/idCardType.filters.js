(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('idCardType', idCardType);
    /**
    *   证件类型
    */
    function idCardType() {
        return function(idCardType) {
            var result = idCardType;
             switch (idCardType) {
                    case '10': //身份证
                        result = '身份证';
                        break;
                    case '11': //户口本
                        result = '户口本';
                        break;
                    case '12': //护照
                        result = '护照';
                        break;
                    case '13': //军官证
                        result = '军官证';
                        break;
                    case '14': //军人士兵证
                        result = '军人士兵证';
                        break;
                    case '15': //港澳居民往来内地通行证
                        result = '港澳居民往来内地通行证';
                        break;
                    case '16': //台湾居民来往大陆通行证
                        result = '台湾居民来往大陆通行证';
                        break;
                    case '17': //临时身份证
                        result = '临时身份证';
                        break;
                    case '18': //外国人永久居留证
                        result = '外国人永久居留证';
                        break;
                    case '19': //警官证
                        result = '警官证';
                        break;
                    case '1a': //武警士兵证
                        result = '武警士兵证';
                        break;
                    case '1b': //军人文职干部证
                        result = '军人文职干部证';
                        break;
                    case '1c': //武警文职干部证
                        result = '武警文职干部证';
                        break;
                    case '1d': //驾驶证
                        result = '驾驶证';
                        break;
                    case '1e': //军人身份证
                        result = '军人身份证';
                        break;
                    case '1f': //武装警察身份证
                        result = '武装警察身份证';
                        break;
                    case '1g': //外国公民护照
                        result = '外国公民护照';
                        break;
                    case '1h': //个人证件类型
                        result = '个人证件类型';
                        break;
                    case '1i': //军官退休证
                        result = '军官退休证';
                        break;
                    case '1j': //文职干部退休证
                        result = '文职干部退休证';
                        break;
                    case '1k': //武警军官退休证
                        result = '武警军官退休证';
                        break;
                    case '1l': //武警文职干部退休证
                        result = '武警文职干部退休证';
                        break;
                    case '1m': //中国护照
                        result = '中国护照';
                        break;
                    case '1n': //学生证
                        result = '学生证';
                        break;
                    case '1o': //港澳台居民往来通行证
                        result = '港澳台居民往来通行证';
                        break;
                    case '1p': //港澳台居民身份证件
                        result = '港澳台居民身份证件';
                        break;
                    case '1q': //香港居民身份证
                        result = '香港居民身份证';
                        break;
                    case '1r': //澳门居民身份证
                        result = '澳门居民身份证';
                        break;
                    case '1s': //台湾身份证
                        result = '台湾身份证';
                        break;
                    case '1t': //执行公务证
                        result = '执行公务证';
                        break;
                    case '1u': //社会保障卡
                        result = '社会保障卡';
                        break;
                    case '1v': //外国人居留证
                        result = '外国人居留证';
                        break;
                    case '1w': //旅行证件
                        result = '旅行证件';
                        break;
                    case '1y': //外国身份证
                        result = '外国身份证';
                        break;
                    case '1X': //其他证件
                        result = '其他证件';
                        break;
                }

            return result;
        };
    }
})();
