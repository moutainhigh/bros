(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('customPattern', customPattern);

    customPattern.$inject = ['ValidationService'];

    /**
     * customPattern指令
     * @memberof EBankProject
     * @function customPattern指令
     */
    function customPattern(ValidationService) {
        var directive = {
            restrict: 'A',
            require: 'ngModel',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr, ctrl) {
            if(!ctrl) return;

            var key;

            attr.$observe('customPattern', function(value) {
                key = value;        // value: identification || phone
                ctrl.$validate();
            });

            ctrl.$validators.customPattern = function(modelValue, viewValue) {
                // Return true/false for valid/invalid
                if(ctrl.$isEmpty(viewValue)) return true;           // 避免与required混淆
                return !ctrl.$isEmpty(viewValue) && ValidationService.validatePattern(key, viewValue);
            };
      }
    }
})();
