(function() {
    'use strict';

    angular
        .module('EBankProject')
        .factory('FileService', FileService);

    FileService.$inject = ['$rootScope','CONFIG', 'CommonService', 'Upload'];

    /**
     * @memberOf directbank
     * @ngdoc service
     * @name FileService
     * @param {service} httpService 通用HTTP请求服务
     * @param {service} CONFIG 通用配置项
     * @description
     * 处理文件上传相关的服务
     */
    function FileService($rootScope, CONFIG, CommonService, Upload) {

        var service = {
            fileUpload: fileUpload                                      // 文件上传
        };

        return service;


        /**
         * 文件上传
        * @memberOf FileService
        // 使用
        <div class="form-group">
        <input type="file" ngf-select ng-model="vm.regForm.file"/>
       </div>
       <div class="progress progress-striped active">
           <div class="progress-bar progress-bar-success" role="progressbar"
              aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
              ng-style="progressPercentageStyle">
              <span ng-style="sr-only"><p ng-bind="progressPercentagesrOnly"></p></span>
           </div>
        </div>


         *
         */
        function fileUpload(file,params) {
            Upload.upload({
                //服务端接收
                url: 'http://10.20.38.92:7879/bros-consumer-login/UploadFile',
                //上传的同时带的参数
                data: params,
                file: file
            }).progress(function (evt) {
                //进度条
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                var temp = progressPercentage + '%' ;
                $rootScope.progressPercentageStyle = {"width":temp};
                $rootScope.progressPercentagesrOnly = temp+"完成";
                console.log('progess:' + progressPercentage + '%' + evt.config.file.name);
            }).success(function (data, status, headers, config) {
                 if (data.returnCode == CONFIG.CORRECT_CODE) {
                        CommonService.showError("文件上传成功");
                    } else {
                         CommonService.showError(data);
                    }
                //上传成功
                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            }).error(function (data, status, headers, config) {
                //上传失败
                console.log('error status: ' + status);
            });
        }
    }

})();
