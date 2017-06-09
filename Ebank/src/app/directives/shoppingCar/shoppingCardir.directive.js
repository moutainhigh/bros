(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('shoppingCardirective', shoppingCardirective);

    shoppingCardirective.$inject = ['$compile','CONFIG'];

    function shoppingCardirective($compile,CONFIG) {
        var directive = {
            restrict: 'AE',
            scope: {
                type: '=',
                list:'='
            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, el, attr) {
            scope.$watch('type', function(type) {
                switch (type) {
                    case "000":
                        el.html('<shopping-cardirson list="list"></shopping-cardirson>');
                        $compile(el.contents())(scope);
                        break;
                }
            });
        }
    }
})();
