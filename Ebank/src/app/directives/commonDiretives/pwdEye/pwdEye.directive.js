(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('pwdEye', pwdEye);

    pwdEye.$inject = ['$compile'];

    function pwdEye($compile) {
        var directive = {
            restrict: 'AE',
            scope:{
                'for': '@'
            },
            link: linkFunc,
            controller: pwdEyeCrtrl,
            controllerAs: 'vm'
        };

        return directive;

        function linkFunc(scope, el, attr, ctrl) {
            scope.vm.id = scope.for;
            el.html('<i class="fa" ' +
                'ng-class="{\'fa-eye-slash\': vm.type==\'password\', \'fa-eye\': vm.type==\'text\'}" ' +
                'ng-click="vm.changeShowStatus(vm.id)"></i>');
            $compile(el.contents())(scope);
        }

        function pwdEyeCrtrl() {
            var vm = this;
            vm.type = 'password';

            vm.changeShowStatus = function(id) {
                var el = $('#' + id);
                var type = el.attr('type');
                if(type == 'password') {
                    el.attr('type', 'text');
                    vm.type = 'text';
                } else {
                    el.attr('type', 'password');
                    vm.type = 'password';
                }
            };
        }
    }
})();
