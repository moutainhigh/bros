(function() {
    'use strict';

    angular
        .module('EBankProject', ['ngAnimate', 'ngCookies', 'ngTouch',
            'ngSanitize', 'ngMessages', 'ngAria', 'ngResource', 'ui.router',
            'ui.bootstrap', 'toastr', 'ajoslin.promise-tracker', 'angular-flash.service',
            'angular-flash.flash-alert-directive', 'highcharts-ng',
            'angular-loading-bar', 'angular-svg-round-progress', 'ngFileUpload']);

    window.Mock.mockjax(angular.module('EBankProject'));
})();
