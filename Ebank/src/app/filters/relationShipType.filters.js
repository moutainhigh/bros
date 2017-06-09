(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('relationShipType', relationShipType);
    /**
    *   成员关系/社会关系
    */
    function relationShipType() {
        return function(relationShipType) {
            var result = relationShipType;
             switch (relationShipType) {
                    case '11': //配偶
                        result = '配偶';
                        break;
                    case '12': //子女
                        result = '子女';
                        break;
                    case '13': //自己父亲
                        result = '自己父亲';
                        break;
                    case '14': //自己母亲
                        result = '自己母亲';
                        break;
                    case '15': //配偶父亲
                        result = '配偶父亲';
                        break;
                    case '16': //配偶母亲
                        result = '配偶母亲';
                        break;
                    case '17': //祖父母
                        result = '祖父母';
                        break;
                    case '18': //外祖父母
                        result = '外祖父母';
                    case '19': //兄弟姐妹
                        result = '兄弟姐妹';
                        break;
                    case '1X': //其他关系
                        result = '其他关系';
                        break;
                    case '1a': //孙子女
                        result = '孙子女';
                        break;
                    case '21': //委托的代理人/代办人
                        result = '委托的代理人/代办人';
                        break;
                    case '22': //上司
                        result = '上司';
                        break;
                    case '23': //下属
                        result = '下属';
                        break;
                    case '24': //同事
                        result = '同事';
                        break;
                    case '25': //情侣关系
                        result = '情侣关系';
                        break;
                    case '26': //其他工作上的合作关系
                        result = '其他工作上的合作关系';
                        break;
                    case '27': //普通朋友
                        result = '普通朋友';
                        break;
                    case '28': //至交好友
                        result = '至交好友';
                        break;
                    case '29': //关系恶劣的联系人
                        result = '关系恶劣的联系人';
                        break;
                    case '2X': //其他关系
                        result = '其他关系';
                        break;
                }

            return result;
        };
    }
})();
