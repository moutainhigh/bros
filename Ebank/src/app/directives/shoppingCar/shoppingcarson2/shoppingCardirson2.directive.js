(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('shoppingtwoCardirson', shoppingtwoCardirson);

    function shoppingtwoCardirson() {

        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/shoppingCar/shoppingcarson2/shoppingCardir2.html'
        };

        return directive;
    }
})();
