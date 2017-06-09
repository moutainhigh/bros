(function() {
    'use strict';

    angular
        .module('EBankProject')
        .directive('loanServicedir', loanServicedir);

    function loanServicedir() {

        var directive = {
            restrict : 'AE',
            scope: {
                product:'='
            },
            templateUrl: 'app/directives/consumerLoan/loanService/loanServicedir.html'
        };

        return directive;
    }
})();
