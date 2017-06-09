(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('myLoandir', myLoandir);

    function myLoandir() {

        var directive = {
            restrict : 'AE',
            scope: {
                product:'='
            },
            templateUrl: 'app/directives/consumerLoan/myLoan/myLoandir.html'
        };

        return directive;

    }
})();
