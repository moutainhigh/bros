(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('ValidationService', ValidationService);

    ValidationService.$inject = ['CommonService', 'UserService', 'CONFIG', '$rootScope', '$state'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name ValidationService
     * @param {service} CONFIG     通用Http请求服务
     * @param {service} http       通用配置项
     * @description  系统公共服务
     */
    function ValidationService(CommonService, UserService, CONFIG, $rootScope, $state) {
        var service = {
            validatePattern: validatePattern,               // 自定义格式验证
            validateIDCard: validateIDCard,                 // 验证身份证是否符合格式要求
            validatePhoneNumber: validatePhoneNumber,       // 验证手机号码是否符合格式要求
            validateName: validateName,                     // 验证姓名格式是否符合要求
            isEmpty: isEmpty,                               // 验证字符串判空
            isShorter: isShorter,                           // 检查字符串的长度
            isInteger: isInteger,                           // 检查字符串是否是整数
            isDecimal: isDecimal,                           // 判断输入变量是否是实数
            isIntChar: isIntChar,                           // 判断输入变量是否是数字或者字母
            isIntCharSpecial: isIntCharSpecial,             // 判断输入变量是否是数字或者字母或者特殊字符
            containIntChar: containIntChar,                 // 判断输入变量是否包含数字或者字母
            containSpecial: containSpecial,                 // 判断输入变量是否包含特殊字符
            containRiskStr: containRiskStr,                 // 判断输入变量是否包含危险输入
            checkAcc: checkAcc,                             // 账号验证，账号要求不能为空，为8-20位整数
            trim: trim,                                     // 去掉字符串前后的空格
            isDate: isDate,                                 // 检验日期是否符合YYYYMMDD的格式，是否合法
            dateInterval: dateInterval,                     // 检查时间间隔是否在规定间隔之内
            checkRecAcc: checkRecAcc,                       // 跨行转账账号验证，账号要求不能为空，最大长度35位
            monthBetween: monthBetween,                     // 查询的起始时间和结束时间是否在一个范围内
            getDateNormal: getDateNormal,                   // 获取当前日期
            formatShowDateTime: formatShowDateTime,         // 用于处理数据库记录的时间，将其转换成可以显示在页面上的时间，时间格式为yyyyMMddHHmmss
            removeComma: removeComma,                       // 替换千分号
            isMoney: isMoney,                               // 检查字符串是否为合法的金额
            toStdAmount: toStdAmount,                       // 删除千分号
            toCashWithComma: toCashWithComma,               // 为金额添加,分割符
            toCashWithMinus: toCashWithMinus,               // 为负数的金额添加,分割符和.分割符
            toCashWithCommaAndDot: toCashWithCommaAndDot,   // 为金额添加,分割符和.分割符
            toChineseCash: toChineseCash,                   // 将金额转换为大写
            formatDateTime: formatDateTime,                 // 格式化日期
            formatDateTimeYmd: formatDateTimeYmd,           // 格式化月份YYYY年MM月
            formatMonthYm: formatMonthYm,                   // 格式化日期（年月日）
            getFmtFullGrade: getFmtFullGrade,               // 格式化利率
            deleteMonthBeforeZero: deleteMonthBeforeZero,   // 去掉1~9月份前面的0
            bigNumSub: bigNumSub,                           // 减法运算 求a,b两个数的差
            bigNumAdd: bigNumAdd,                           // 加法运算 求a,b两个数的和
            fillSameLengthArray: fillSameLengthArray,       // 对传入的字符串a,b,进行处理（左补零或右补零），使之变成相同的长度的数值，存放在返回的数组里面
            trimStringZero: trimStringZero,                 // 去掉金额前面多余的0
            bigSmalCompare: bigSmalCompare                  // 判断数据大小

        };

        return service;


        /**
         * 验证身份证是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validatePattern(key, value) {
            var isValid = false;
            switch(key) {
                case 'identification':
                    isValid = validateIDCard(value);
                    break;
                case 'phone':
                    isValid = validatePhoneNumber(value);
                    break;
                case 'name':
                    isValid = validateName(value);
                    break;
                case 'amount':
                    isValid = validateAmount(value);
                    break;
                case 'email':
                    isValid = validateEmail(value);
                    break;
                case 'account':
                    isValid = validateAccount(value);
                    break;
                default:
                    isValid = false;
                    break;
            }
            return isValid;
        }


        /**
         * 验证银行账号是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validateAccount(value){
            var reg = /^\d{19}$/g;   // 以19位数字开头，以19位数字结尾

            return reg.test(value);
        }

        /**
         * 验证邮箱是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validateEmail(value){
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;

            return reg.test(value);
        }

        /**
         * 验证身份证是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validateIDCard(value) {
            var regex = /^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}(((19|20)\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|((19|20)\d{2}(0[13578]|1[02])31)|((19|20)\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)+$/;

            return regex.test(value);
        }

        /**
         * 验证手机号码是否符合格式要求
         * @memberOf UserService
         * @return true or false
         */
        function validatePhoneNumber(value) {
            var regex = /^0?1[3|4|5|8|7][0-9]\d{8}$/;

            return regex.test(value);
        }


        /**
         * 验证姓名格式是否符合要求
         * @memberOf UserService
         * @return true or false
         */
        function validateName(value) {
            var regex = /^[\u4E00-\u9FA5]{2,6}$/;

            return regex.test(value);
        }


        /**
         * 验证提现和充值资金输入是否合理
         * @memberOf UserService
         * @return true or false
         */
        function validateAmount(value) {
            var regex = /^(([1-9](\d+)?(\.\d{1,2})?))$|^(0\.\d{1,2})$/;

            return regex.test(value);
        }

        /**
         * 字符串去空
         * @memberOf UserService
         * @return true or false
         */
        function trim(input) {
            return input.replace(/(^\s*)|(\s*$)/g, "");
        }


        /**
        * 本文件包括两部分，前半部分为通用验主下函数，后半部分为针对网银业务的验证函数
        ***************************通用验证函数*******************************
        *
        */

        /**
        * 检验字符串是否为空
        * @param {String} 字符串
        * @return {bool} 是否为空
        */
        function isEmpty(input)
        {
            if( input==null ||  trim(input).length == 0 || input == 'null')
                return true;
            else
                return false;
        }

        /**
        * 检查字符串的长度
        * @param {String} 字符串
        * @param {Integer} 要比较的长度
        * @return {bool} true：变量长度<给出的长度；false：变量长度>=给出的长度
        */
        function isShorter(str,reqlength)
        {
            if( str.length<reqlength )
                return true;
            else
                return false;
        }

        /**
        * 检查字符串是否是整数
        * @param {String} 字符串
        * @return {bool} 是否是整数
        */
        function isInteger( s )
        {
            var isInteger = RegExp(/^[0-9]+$/);
            return ( isInteger.test(s) );
        }

        /**
        * 判断输入变量是否是实数
        * @param {String} 要检查的变量值
        * @return {bool} 是否为实数
        */
        function isDecimal( s )
        {
            var isDecimal = RegExp(/^([0-9]+(\.?))?[0-9]+$/);
            return ( isDecimal.test(s) );
        }

        /**
        * 判断输入变量是否是数字或者字母
        * @param {String} 要检查的变量值
        * @return {bool} 是否为数字或者字母
        */
        function isIntChar( s )
        {
            var isIntChar = RegExp(/^[a-zA-Z0-9]+$/);
            return ( isIntChar.test(s) );
         }

        /**
        * 判断输入变量是否是数字或者字母或者特殊字符
        * 特殊字符：|_ - * & % $ # @ ! ~ ^ ( )
        * @param {String} 要检查的变量值
        * @return {bool} 是否为数字或者字母或者特殊字符
        */
        function isIntCharSpecial( s )
        {
            var isIntCharSpecial = RegExp(/^[a-zA-Z0-9(\|)(\_)(\*)(\&)(\%)(\$)(\#)(\@)(\!)(\~)(\^)]+$/);
            return ( isIntCharSpecial.test(s) );
        }

        /**
        * 判断输入变量是否包含数字或者字母
        * @param {String} 要检查的变量值
        * @return {bool} 是否包含数字或者字母
        */
        function containIntChar( s )
        {
            var containIntChar = RegExp(/[a-zA-Z0-9]+/);
            return ( containIntChar.test(s) );
        }

        /**
        * 判断输入变量是否包含特殊字符
        * 特殊字符：~ ! @ # $ % ^ & * ( ) - _ + = [ ] { } | \ ; : ' " , . / < > ? <
        * @param {String} 要检查的变量值
        * @return {bool} 是否包含特殊字符
        */
        function containSpecial( s )
        {
            var containSpecial = RegExp(/[\ \~\!\@\#\$\%\^\&\*\_\+\=\[\]\{\}\|\\\;\:\'\"\,\.\/\<\>\?]+/);
            return ( containSpecial.test(s) );
        }


        /**
        * 判断输入变量是否包含危险输入
        * 特殊字符： , -- < > ; % & script  select  insert delete ...
        * @param {String} 要检查的变量值
        * @return {bool} 是否包含特殊字符
        */
        function containRiskStr( s )
        {

            var regArray=new Array(/',/i,/</i,/>/i,/';/i,/%/i,/&/i,/├/i,/└/i,/script/i,/iframe/i,/select/i,/insert/i,/delete/i,/from/i,/drop/i,/update/i,/exec/i,/master/i,/form/i);
            for(var i=0;i<regArray.length;i++){
                var pattern=regArray[i];
                  if(pattern.test(s)){
                        return true;
                  }
            }
            return false;
        }


        /*****************************************网银通用验证函数***************************************
        /***
         * 账号验证，账号要求不能为空，为8-20位整数
         * @param acc
         */
        function checkAcc(acc){
            if(acc==null||acc==""||acc=="null"){
                return 1;
            }
            if(acc.length<8||acc.length>20){
                return 2;
            }
            if(!isInteger(acc)){
                return 3;
            }
            return 0;
        }
        /**
        * 去掉字符串前后的空格
        * @param {String} 字符串
        * @return {String} 去除空格后的字符串
        */
        function trim(input) {
            return input.replace(/(^\s*)|(\s*$)/g, "");
        }

        /**
        * 检验日期是否符合YYYYMMDD的格式，是否合法
        * @param {String} 日期字符串
        * @return {bool} 是否是合法日期
        */
        function isDate(dateInput) {
            var inputYear = dateInput.substring( 0, 4 );
            var inputMonth = parseInt(dateInput.substring( 4, 6 ),10)-1;
            var inputDay = dateInput.substring( 6, 8 );
            var dateTest = new Date(inputYear,inputMonth,inputDay);
            var testYear = dateTest.getFullYear();
            var testMonth = dateTest.getMonth();
            var testDay = dateTest.getDate();
            var isValidateDate = (inputYear == testYear && inputMonth == testMonth && inputDay == testDay );
            return isValidateDate;
        }

        /**
        * 检查时间间隔是否在规定间隔之内
        * @param {String} 开始日期
        * @param {String} 结束日期
        * @param {Integer} 间隔，单位为天
        * @return {bool} 是否在规定间隔之内
        */
        function dateInterval(startDate,endDate,interval) {
            var date1 = new Date(eval(startDate.substring(0,4)),eval(startDate.substring(4,6))-1,eval(startDate.substring(6,8)));
            var date2 = new Date(eval(endDate.substring(0,4)),eval(endDate.substring(4,6))-1,eval(endDate.substring(6,8)));
            if ( ( date2 - date1 ) / 86400000 > eval(interval) - 1 )
                return false;
            return true;
        }
        /***
         * 跨行转账账号验证，账号要求不能为空，最大长度35位
         * @param acc
         */
        function checkRecAcc(acc){
            if(isEmpty(acc)){
                return 1;
            }
            if(containRiskStr(acc)){
                return 2;
            }
            if(!isInteger(acc)){
                return 2;
            }
            return 0;
        }

        /**
         * 查询的起始时间和结束时间是否在一个范围内
         * 示例：
         * monthBetween(startDate,endDate,monthNum);
         *
         * 参数：startDate 起始时间
         * 参数：endDate 结束时间
         * 参数：monthNum 时间月份范围
         * 返回值： true 在 false 不在
         *
         * Version: 1.00
         * Author: 姜志鑫
         */
        function monthBetween(startDate,endDate,monthNum){
            var startYear = startDate.substring(0,4);
            var startMonth = startDate.substring(4,6);
            var startDay = startDate.substring(6);
            var endYear = endDate.substring(0,4);
            var endMonth = endDate.substring(4,6);
            var endDay = endDate.substring(6);
            var betweenMonth = (parseInt(endYear)-parseInt(startYear))*12 + parseInt(endMonth - startMonth);
            if(monthNum >= 0 && (parseInt(endDay,10) - parseInt(startDay,10)) > 0)
                betweenMonth=Math.abs(betweenMonth)+1;
            if(monthNum < 0 && (parseInt(endDay,10) - parseInt(startDay,10)) < 0)
                betweenMonth=Math.abs(betweenMonth)+1;
            //为了兼容负数，用绝对值来比较
            betweenMonth=Math.abs(betweenMonth);
            monthNum=Math.abs(monthNum);
            if(betweenMonth==0)
                return true;
            else if(betweenMonth - monthNum < 0)
            {
                return true;
            }
            else if((betweenMonth - monthNum) == 0)
            {
                return true;
            }
            else
                return false;
        }


        /**
        * 获取当前日期
        */
        function getDateNormal(){
            var myDate = new Date();
            var year=myDate.getFullYear();
            var month=myDate.getMonth()+1;
            if(month<10){
                month="0"+month;
            }
            var date=myDate.getDate();
            if(date<10){
                date="0"+date;
            }
            return year+'-'+month+"-"+date;
        }

        /***
         * 用于处理数据库记录的时间，将其转换成可以显示在页面上的时间，时间格式为yyyyMMddHHmmss
         * @param v
         */
        function formatShowDateTime(v){
            if(v==""){
                return "";
            }else{
                return v.substring(0,4)+"-"+v.substring(4,6)+"-"+v.substring(6,8)+" "+v.substring(8,10)+":"+v.substring(10,12)+":"+v.substring(12,14);
            }
        }

        /**
         * 替换千分号
         * @param str
         * @returns
         */
        function removeComma(str){
            return str.replace(new RegExp('\,',["g"]),'');
        }

        /**
         * 检查字符串是否为合法的金额
         * @param {String} 字符串
         * @return {bool} 是否为合法金额
         */
        function isMoney(s){
            var isMoney = RegExp(/^[0-9]{0,16}\.{0,1}[0-9]{0,2}$/);
            return ( isMoney.test(s) );
        }

        /**
         * 删除千分号
         * @param sAmount
         * @returns
         */
        function toStdAmount(sAmount){
            var sComma = /\,/gi;
            var sResult = sAmount.replace(sComma,"");
            var iDotIndex = sResult.indexOf('.');
            var iLength = sResult.length;
            var toMatchNaNum = /\D/;
            if ((iDotIndex!=-1&&(iLength-iDotIndex>3||toMatchNaNum.test(sResult.slice(0,iDotIndex))))
                ||toMatchNaNum.test(sResult.slice(iDotIndex+1,iLength))){
                flag=false;
                return 1;       // 小数点后大于2位数 或 含有非数字字符
            }else{
        // 将金额处理为######.##形式
                if (iDotIndex==-1){
                    sResult = sResult+'.00';
                }else if (iDotIndex==0){
                    if (iLength-iDotIndex==1) sResult='0'+sResult+'00';
                    if (iLength-iDotIndex==2) sResult='0'+sResult+'0';
                    if (iLength-iDotIndex==3) sResult='0'+sResult;
                }else{
                    if (iLength-iDotIndex==2) sResult=sResult+'0';
                    if (iLength-iDotIndex==1) sResult=sResult+'00';
                }

        // 处理金额非前面的0
                var sTemp = "";
                sTemp = sResult.slice(0,iDotIndex);
                /*var iTemp = new Number(sTemp);
                sTemp = iTemp.toString();*/
                sTemp = sTemp.split('.')[0];
                if (sTemp.length>16) {
                    flag = false;return 2;// 太长的金额
                }
                iDotIndex = sResult.indexOf('.');
                sResult = sTemp+sResult.slice(iDotIndex);   // 返回######.##形式的金额
                return sResult;
            }
        }

        function addComma(str) {
            if (str.length > 3){
                var mod = str.length % 3;
                var output = (mod > 0 ? (str.substring(0,mod)) : '');
                for (i=0 ; i < Math.floor(str.length / 3); i++) {
                    if ((mod == 0) && (i == 0)){
                        output += str.substring(mod+ 3 * i, mod + 3 * i + 3);
                    }else{
                        output += ',' + str.substring(mod + 3 * i, mod + 3 * i + 3);
                    }
                }
                return (output);
            }
            else {
                return str;
            }
        }

        /**
         * 为金额添加,分割符
         * @param {String} 要转换的金额字符串
         * @return {String} 转换后的金额字符串
         */
        function toCashWithComma( cash ){
            while( cash.charAt(0) == '0' )  {
                cash = cash.substr(1);
            }
            if( !isFloat( cash ) ){
                return addComma(cash);
            }
            var dotIndex = cash.indexOf('.');
            var integerCash = cash.substring( 0, dotIndex );
            var decimalCash = cash.substring( dotIndex );
            return addComma(integerCash)+decimalCash;
        }
        /**
         * 为负数的金额添加,分割符和.分割符
         *
         * @param {String}
         *            要转换的金额字符串
         * @return {String} 转换后的金额字符串
         */
        function toCashWithMinus(cash){
            if(cash.substring(0, 1)=='-'){
                cash=cash.substring(0, 1)+''+toCashWithCommaAndDot(cash.substring(1, cash.length));
            }else{
                cash=toCashWithCommaAndDot(cash);
            }
            if(cash.substring(0, 1)==''){
                cash = "0"+cash;
            }
            return cash;
        }

        /**
         * 为金额添加,分割符和.分割符
         * @param {String} 要转换的金额字符串
         * @return {String} 转换后的金额字符串
         */
        function toCashWithCommaAndDot( cash )
        {
            if (cash == null || cash =='null' || cash==''){
                return '';
            }
            var temp = toCashWithComma( cash );
            if(temp.substring(0, 1)==''){
                temp = "0"+temp;
            }
            if ( temp.length == 0 )
            {
                return "0.00";
            }
            var dotPos = temp.indexOf(".");
            if ( dotPos < 0 )
            {
                return temp+'.00';
            }
            if ( dotPos == 0 )
            {
                temp = '0' + temp;
                dotPos = temp.indexOf(".");
            }
            if ( dotPos == temp.length-2)
            {
                return temp + '0';
            }
            if ( dotPos == temp.length-1)
            {
                return temp + '00';
            }
            return temp;
        }

        /**
         * 将金额转换为大写
         * @param currencyDigits 需要转化的金额
         * @returns 转换后的大写金额，如果金额有误，返回空串
         */
        function toChineseCash(sAmount){
            if(sAmount==null || sAmount==""){
                return "";
            }
            var value = toStdAmount(sAmount);
            var sCN_Num = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");
            var unit = new Array('元', '万', '亿', '万');
            var subunit = new Array('拾', '佰', '仟');
            var sCNzero = '零';
            var result = "";
            var iDotIndex = value.indexOf('.');
            var sBeforeDot = value.slice(0, iDotIndex);
            var sAfterDot = value.slice(iDotIndex);
            var len = 0;
            len = sBeforeDot.length;
            var i = 0, j = 0, k = 0; // j is use to subunit,k is use to unit
            var oldC = '3';
            var cc = '0';
            result = unit[0] + result;
            var oldHasN = false;
            var hasN = false;
            var allZero = true;
            for (i = 0; i < len; i++) {
                if (j == 0 && i != 0) {
                    if (!hasN){
                        if ((k % 2) == 0) result = result.slice(1);
                    }else{
                        if (oldC == '0') result = sCNzero + result;
                    }
                    result = unit[k] + result;
                    oldHasN = hasN;
                    hasN = false;
                }
                cc = sBeforeDot.charAt(len - i - 1);
                if (oldC == '0' && cc != oldC){
                    if (hasN) result = sCNzero + result;
                }
                if (cc != '0'){
                    if (j != 0){
                        result = subunit[j - 1] + result;
                    }
                    var dig = '0';
                    dig = sCN_Num[cc];

                    if (dig == '0'){
                        return false;
                    }
                    hasN = true;
                    allZero = false;
                    result = dig + result;
                }
                oldC = cc;
                j++;
                if (j == 4){
                    k++;
                    j = 0;
                }
            }
            if (allZero){
                result = "零元";
            }else {
                var bb = 0;
                if (!hasN) {
                    bb++;
                    if (!oldHasN) {
                        bb++;
                    }
                }
                if (bb != 0){
                    result = result.slice(bb);
                }
                if (result.charAt(0) == '零'){
                    result = result.slice(1);
                }
            }

            // after dot
            sAfterDot = sAfterDot.slice(1);
            len = sAfterDot.length;
            var corn = new Array('0','0');
            var cornunit = new Array('角', '分');
            var n = 0; // j is use to subunit,k is use to unit
            var dig = '0';
            corn[0] = sAfterDot.charAt(0);
            if (len > 1){
                corn[1] = sAfterDot.charAt(1);
            }else{
                corn[1] = '0';
            }
            if ((corn[0] ==  '0') && (corn[1] == '0')) {
                return result += '整';
            }else{
                if (allZero) result = "";
            }
            for (i = 0; i < 2; i++){
                var curchar = corn[i];
                dig = sCN_Num[curchar];
                if (i==0){
                    if(result!=""||curchar!='0'){
                        result += dig;
                    }
                    if(curchar!='0'){
                        result += cornunit[0];
                    }
                }
                if (i==1&&curchar!='0') {
                    result = result+dig+cornunit[1];
                }
            }
            return result;
        }

        /*
        函数：格式化日期
        参数：formatStr-格式化字符串
        d：将日显示为不带前导零的数字，如1
        dd：将日显示为带前导零的数字，如01
        ddd：将日显示为缩写形式，如Sun
        dddd：将日显示为全名，如Sunday
        M：将月份显示为不带前导零的数字，如一月显示为1
        MM：将月份显示为带前导零的数字，如01
        MMM：将月份显示为缩写形式，如Jan
        MMMM：将月份显示为完整月份名，如January
        yy：以两位数字格式显示年份
        yyyy：以四位数字格式显示年份
        h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
        hh：使用12小时制将小时显示为带前导零的数字
        H：使用24小时制将小时显示为不带前导零的数字
        HH：使用24小时制将小时显示为带前导零的数字
        m：将分钟显示为不带前导零的数字
        mm：将分钟显示为带前导零的数字
        s：将秒显示为不带前导零的数字
        ss：将秒显示为带前导零的数字
        l：将毫秒显示为不带前导零的数字
        ll：将毫秒显示为带前导零的数字
        tt：显示am/pm
        TT：显示AM/PM
        返回：格式化后的日期
        */
        Date.prototype.format = function (formatStr) {
            var date = this;
             /*
             函数：填充0字符
             参数：value-需要填充的字符串, length-总长度
             返回：填充后的字符串
            */
            var zeroize = function (value, length) {
            if (!length) {
            length = 2;
            }
            value = new String(value);
            for (var i = 0, zeros = ''; i < (length - value.length); i++) {
             zeros += '0';
             }
             return zeros + value;
             };
             return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
             switch ($0) {
             case 'd': return date.getDate();
             case 'dd': return zeroize(date.getDate());
             case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
             case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
             case 'M': return date.getMonth();
             case 'MM': return zeroize(date.getMonth());
             case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
             case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
             case 'yy': return new String(date.getFullYear()).substr(2);
             case 'yyyy': return date.getFullYear();
             case 'h': return date.getHours() % 12 || 12;
             case 'hh': return zeroize(date.getHours() % 12 || 12);
             case 'H': return date.getHours();
             case 'HH': return zeroize(date.getHours());
             case 'm': return date.getMinutes();
             case 'mm': return zeroize(date.getMinutes());
             case 's': return date.getSeconds();
             case 'ss': return zeroize(date.getSeconds());
             case 'l': return date.getMilliseconds();
             case 'll': return zeroize(date.getMilliseconds());
             case 'tt': return date.getHours() < 12 ? 'am' : 'pm';
             case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';
             }
         });
         };

        /**
         * 格式化日期
         * @param v
         * @returns
         */
        function formatDateTime(v){
            try{
                if(isEmpty(v)){
                    return "";
                }else{
                    if(v.length>8&&15>v.length){
                        return v.substring(0,4)+"-"+v.substring(4,6)+"-"+v.substring(6,8)+" "+v.substring(8,10)+":"+v.substring(10,12)+":"+v.substring(12,14);
                    }
                    if(v.length>0&&9>v.length){
                        return  v.substring(0,4)+"-"+v.substring(4,6)+"-"+v.substring(6,8);
                    }else{
                        return v;
                    }
                }
            }catch(e){
                  return v;
            }
        }

        /**
         * 格式化日期（年月日）
         * @param v
         * @returns
         */
        function formatDateTimeYmd(v){
            try{
                if(isEmpty(v)){
                    return "";
                }else{
                    return v.substring(0,4)+"年"+v.substring(4,6)+"月"+v.substring(6,8)+"日";
                }
            }catch(e){
                  return v;
            }
        }

        //格式化月份YYYY年MM月
        function formatMonthYm(v){
            if(v==""){
                return "";
            }else{
                return v.substring(0,4)+"年"+v.substring(4,6)+"月";
            }
        }

        /**
         * 格式化利率
         * @param v
         * @returns
         */
        function getFmtFullGrade(v){
            try{
                if(isEmpty(v)){
                    return "";
                }
                var grade = (v/100).toFixed(2);
                return grade + '%';
            }catch(e){
                return v;
            }
        }


        /**
         * 去掉1~9月份前面的0
         * @param v
         * @returns
         */
        function deleteMonthBeforeZero(v){
            try{
                // 删除字符串开始的0
                return v.replace(/^0/, '');
            }catch(e){

            }
        }




        //------------------------大数据运算   开始--------------------------

        /**
         * @description 减法运算 求a,b两个数的差
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} a 减数，只能为字符串，不能为负数
         * @param {String} b 被减数，之能为字符串，不能为负数
         * @return {String} 返回a减b的结果
         * @example 范例1：
           function testExample(){
                bigNumSub("222222.01","22222");//返回:200000.01
                bigNumSub("222222.01","222221111.99");//返回:-221998889.98
                bigNumSub("9223372036854775809","9223372036854775807");//返回:2.0
                bigNumSub("9223372036854775805","9223372036854775809");//返回:-4.0
           }
        */
        function bigNumSub(a,b){
               var arrTmp = new Array();//函数返回的数组
               var arraTmp=new Array();//存放数据a的数组
               var arrbTmp=new Array();//存放数据b的数组
               var sumTmp = new Array();//存放和的数组
               var boolFu = false; //负数标志
               var tmp;
               if(bigSmalCompare(b,a)){
                      tmp = a;
                      a = b;
                      b = tmp;
                      boolFu = true;
               }

               arrTmp = fillSameLengthArray(a,b);
               arraTmp = arrTmp[0];
               arrbTmp = arrTmp[1];

               var borrow = 0;//借位
               for(var i=(arraTmp.length-1);i>=0;i--){
                     if(arraTmp[i] == '.'){
                         sumTmp[i] = ".";
                         continue;
                     }
                     //判断是否需要借位
                     var tmp = ""+eval(arraTmp[i]+"-"+arrbTmp[i]+"-"+borrow);
                     if(tmp < 0){
                           borrow = 1;
                           sumTmp[i] = borrow*10 - Math.abs(Number(tmp));
                     }else{
                          borrow = 0;
                          sumTmp[i] = tmp;
                     }

               }

               var sum="";
               for(var i=0;i<sumTmp.length;i++){
                    sum = sum+""+sumTmp[i];
               }
               sum = trimStringZero(sum);//去除左边多余的零
               if(boolFu){
                   sum = "-"+sum;
               }
               return sum+"";
        }



        /**
         * @description 加法运算 求a,b两个数的和
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} a 加数，只能为字符串，不能为负数
         * @param {String} b 被加数，只能为字符串，不能为负数
         * @return {String} 返回a加b的结果
         * @example 范例1：
           function testExample(){
                bigNumAdd("9223372036854775807","9223372036854775807");//返回:18446744073709551614.0
           }
        */
        function bigNumAdd(a,b){
               var arraTmp=new Array();//存放数据a的数组
               var arrbTmp=new Array();//存放数据b的数组
               var sumTmp = "";
               var arrTmp = new Array();//函数返回的数组

               arrTmp = fillSameLengthArray(a,b);
               arraTmp = arrTmp[0];
               arrbTmp = arrTmp[1];

               var overflower = 0;//进位
               for(var i=(arraTmp.length-1);i>=0;i--){
                     if(arraTmp[i] == "."){
                         sumTmp = "."+sumTmp;
                         continue;
                     }
                     var tmp = ""+eval(arraTmp[i]+"+"+arrbTmp[i]+"+"+overflower);
                     if(tmp.length == 1){
                          overflower = 0;
                          sumTmp = tmp+sumTmp+"";
                     }else{
                         if(i<=0){//最后1个数字
                             sumTmp = tmp+sumTmp+"";
                         }else{
                              overflower = tmp.substr(0,1);
                              sumTmp = tmp.substr(1,1)+sumTmp+"";
                         }

                     }
               }

              sum = trimStringZero(sumTmp);//去除左边多余的零
              return sum+"";
        }

        /**
         * @description 对传入的字符串a,b,进行处理（左补零或右补零），使之变成相同的长度的数值，存放在返回的数组里面
         * @author 作者 2014-1-1
         * @version 1.0
         * @private : 私有，内部
         * @param {String} a 字符串，只能为正数
         * @param {String} b 字符串，只能为正数
         * @return {Array} 返回数组
         * @example 无
        */
        function fillSameLengthArray(a,b){
               var aTmp = new Array();//存放临时a变量 aTmp[0]存放整数部分  aTmp[1]存放小数部分
               var bTmp = new Array();//存放临时b变量 bTmp[0]存放整数部分  bTmp[1]存放小数部分
               var arraTmp=new Array();//存放数据相同长度的a的数组
               var arrbTmp=new Array();//存放数据相同长度的b的数组
               var sumArray = new Array();//用来存放返回的数据  sumArray[0] = arraTmp   sumArray[1] = arrbTmp;

               if(a.indexOf(".") != -1){
                    aTmp = a.split(".");
               }else{
                    aTmp[0] = a;
                    aTmp[1] = "0";
               }
               if(b.indexOf(".") != -1){
                    bTmp = b.split(".");
               }else{
                    bTmp[0] = b;
                    bTmp[1] = "0";
               }

               //小数右边补零
               var lengthFillright = Math.abs( aTmp[1].length - bTmp[1].length);
               for(var i=0;i<lengthFillright;i++){
                     if(aTmp[1].length > bTmp[1].length){
                           bTmp[1] = bTmp[1]+"0";
                     }else{
                           aTmp[1] = aTmp[1]+"0";
                     }
               }

               //整数左补零
               var lengthFillleft = Math.abs( aTmp[0].length - bTmp[0].length);
               for(var i=0;i<lengthFillleft;i++){
                     if(aTmp[0].length > bTmp[0].length){
                           bTmp[0] = "0"+bTmp[0];
                     }else{
                           aTmp[0] = "0"+aTmp[0];
                     }
               }
               a = aTmp[0]+"."+aTmp[1];
               b = bTmp[0]+"."+bTmp[1];
               //数值都是一样的长度，将数据都放到数组里面

               for(var i=0;i<a.length;i++){
                    arraTmp[i] = a.substr(i,1);
                    arrbTmp[i] = b.substr(i,1);
               }

               sumArray[0] = arraTmp;
               sumArray[1] = arrbTmp;
               return sumArray;
        }


        /**
         * @description 去掉金额前面多余的0
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} str 需要进行处理的金额字符串
         * @return {String} 处理之后的字符串
         * @example 范例1：
           function testExample(){
                trimStringZero("0000012344.1234");//返回:12344.1234
           }
        */
        function trimStringZero(str)
        {
            var strTmp="";
            var times = 0;
            if(str.indexOf(".") != -1){//有小数位
                 strTmp = str.split(".");
                 str = strTmp[0];
                 times++;
            }
            //去除整数部分左边多余的0
            while ((str.substring(0,1)=="0") &&(str.length!=1) )
            {
                str = str.substring(1);
            }
            if(times > 0){//有小数位
                 str = str+"."+strTmp[1];
            }
            return str;
        }


        /**
         * @description 判断数据大小
         * @author 作者 2014-1-1
         * @version 1.0
         * @param {String} a  只能为字符串
         * @param {String} b  只能为字符串
         * @return {boolean} true:a>b  false:a<=b
         * @example 范例1：
           function testExample(){
                bigSmalCompare("1","-123");//返回:true
                bigSmalCompare("9223372036854775807","9223372036854775807");//返回:false
                bigSmalCompare("9223372036854775807","9223372036854775808");//返回:false
                bigSmalCompare("-9223372036854775809","9223372036854775807");//返回:false
           }
        */
        function bigSmalCompare(a,b){
               var arrTmp = new Array();//函数返回的数组
               var arraTmp=new Array();//存放数据a的数组
               var arrbTmp=new Array();//存放数据b的数组
               arrTmp = fillSameLengthArray(a,b);
               arraTmp = arrTmp[0];
               arrbTmp = arrTmp[1];

               //比较大小
               for(var i=0;i<a.length;i++){
                     if(arraTmp[i] == "."){
                          continue;
                     }

                     if(arraTmp[i] > arrbTmp[i]){
                           return true;
                     }else if(arraTmp[i] < arrbTmp[i]){
                           return false;
                     }else{
                           if(i == (a.length-1)){
                                return false;
                           }
                     }
               }
        }
        //------------------------大数据运算   结束

    }
})();
