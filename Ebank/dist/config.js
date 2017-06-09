'use strict';

// var ROOT_URL = 'http://10.10.6.12:8800/';                                       // API地址
//shaoxu 2017/06/05
var ip='127.0.0.1';
var port=':8080';
var ROOT_URL=ip+port;//'http://10.10.10.131:8800/';                             			
var BROSURLROOT_URL = {                                               			// 本地开发地址IP配置url
    PRODUCT:'http://'+ip+port+'/',//'http://10.10.10.131:9000/',
    COMMON:'http://'+ip+port+'/',//'http://10.10.10.131:9000/',
    MANAGE:'http://'+ip+port+'/',//'http://10.10.10.131:9000/',
    AUTH:'http://'+ip+port+'/'//'http://10.10.10.131:9000/'
};
var OFFLINE = false;                                                             // 离线模式
var URL = 'bros-manage-common';													// 前置路径
var BROSURL = {																	// 前置路径本地开发                                                        			
    PRODUCT:'bros-manage-product',
    COMMON:'bros-manage-common',
    MANAGE:'bros-network-manage',
    AUTH:'bros-manage-auth'
};
