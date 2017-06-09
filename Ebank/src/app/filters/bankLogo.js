(function() {
    'use strict';

    angular
        .module('EBankProject')
        .filter('bankLogo', bankLogo);

    function bankLogo() {
        return function(url) {
            if(!url) {
                return;
            }
            var location = url.indexOf('.png');
            var urlBig = url.substring(0,location) + '-b' +  url.substr(location, 4);
            return urlBig;
        };
    }
})();
