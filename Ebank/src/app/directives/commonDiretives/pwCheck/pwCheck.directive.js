/**
 * @name doubleCheck
 * @description
 * 判断两次输入是否相同
 */
(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('pwCheck', ['$compile', pwCheck]);

    function pwCheck($compile) {
        var directive = {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                /* password: '@'*/
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attr, ngModel) {
            var firstPassword = '#' + attr.pwCheck;

            element.add(firstPassword).on('keyup', function() {
                scope.$apply(function() {

// console.info(element.val() === $(firstPassword).val());
// console.info("element    "+element.val() );
// console.info("element    "+firstPassword);
// console.info("firstPassword      "+$(firstPassword).val() );

                    ngModel.$setValidity('pwmatch', element.val() === $(firstPassword).val());

                    // ngModel.$setValidity('pwmatch', element.val() === $(firstPassword).val());
                });
            });
        }
    }

})();

// angular.module('myApp.directives', [])
//   .directive('pwCheck', [function () {
//     return {
//       require: 'ngModel',
//       link: function (scope, elem, attrs, ctrl) {
//         var firstPassword = '#' + attrs.pwCheck;
//         elem.add(firstPassword).on('keyup', function () {
//           scope.$apply(function () {
//             var v = elem.val()===$(firstPassword).val();
//             ctrl.$setValidity('pwmatch', v);
//           });
//         });
//       }
//     }
//   }]);
