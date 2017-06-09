(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('shoppingcardirson', shoppingcardirson);

    function shoppingcardirson() {

        var directive = {
            restrict : 'AE',
            scope: {
                list:'='
            },
            templateUrl: 'app/directives/shoppingCar/shoppingcarson/shoppingCardir.html'
        };

        return directive;
    }
})();
