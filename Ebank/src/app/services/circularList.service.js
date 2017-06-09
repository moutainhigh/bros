(function() {
    'use strict';

    angular.module('EBankProject')
    .factory('CircularListService', CircularListService);

    CircularListService.$inject = [];

    function CircularListService() {
        var CircularListService = {
            create: create
        };

        function create(capacity) {
            var index, list;

            list = [];
            index = 0;
            return {
                add: function(obj) {
                    list[index] = obj;
                    index = (index + 1) % capacity;
                    return index;
                },
                getAll: function() {
                    var l, _ref, _ref1;
                    l = list.slice(index);
                    [].splice.apply(l, [(_ref = l.length), 9e9].concat(_ref1 = list.slice(0, index)));
                    return l;
                },
                removeAll: function() {
                    list = [];
                    index = 0;
                    return list;
                },
                length:function(){
                    return list.length;
                }
            };
        }

        return CircularListService;
    }
})();
