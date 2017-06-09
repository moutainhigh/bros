(function() {
    'use strict';

    angular.module('EBankProject')
    .factory('EventBusService', EventBusService);

    EventBusService.$inject = ['$rootScope', 'CircularListService', '$state'];

    function EventBusService($rootScope, CircularListService, $state) {
        var dataList = [];
        var eventMap = {}
        //_maxEvents = 10;
        //events = CircularListService.create(_maxEvents);


        var EventBusService = {
            publish: publish,
            subscribe: subscribe,
            unsubscribe:unsubscribe,
            set:set,
            get:get
            // getAll: getAll,
            // removeeventAll: removeeventAll,
        };

        return EventBusService;

        function set(key,value){
            dataList.push({
                state:$state.current.name,
                key:key,
                value:value
            });
        }

        function get(state,key){
            if(dataList.length>0){
                dataList.forEach(function(data){
                    if(data.state == state && data.key == key){
                        return data;
                    }
                });
            }
        }

        function publish(channel,topic,data) {
            var eventType = channel+'.'+topic;
            if (eventMap && eventMap[eventType]) {
                for (var i = 0; i < eventMap[eventType].length; i++) {
                    eventMap[eventType][i](data);
                }
            }
        }
        function unsubscribe(channel,topic, handler) {
            var eventType = channel+'.'+topic;
            for (var i = 0; i < eventMap[eventType].length; i++) {
                if (eventMap[eventType][i] === handler) {
                    eventMap[eventType].splice(i, 1);
                    break;
                }
            }
        }
        function subscribe(channel,topic, handler) {
            //multiple event listener
            var eventType = channel+'.'+topic;
            if (!eventMap[eventType]) {
                eventMap[eventType] = [];
            }
            eventMap[eventType].push(handler);
        }

        // 广播需要传递的消息
        // function publish(channel, topic, data) {
        //     events.add({
        //         channel: channel,
        //         topic: topic,
        //         data: data
        //     });
        // }

        // 接收广播消息
        // function subscribe(channel, topic, callback) {
        //     var data = {},sub;
        //     var subscriber = channel+'.'+topic;
        //     sub = subs[subscriber];
        //
        //     if(!sub && events.length() > 0){
        //         var eventArray = events.getAll();
        //         eventArray.forEach(function(event){
        //             if(event.channel == channel && event.topic == topic){
        //                 console.log('load from events');
        //                 callback({},event.data);
        //                 return false;
        //             }
        //         });
        //     }
        //     subs[subscriber] = sub;
        // }

        // 接收广播消息
        // function subscribe(subscriber, name, listener) {
        //     var sub;
        //     sub = subs[subscriber] || {};
        //     if (typeof sub[name] === 'function') {
        //         sub[name]();            // unbind the subscribe event !important
        //     }
        //     //sub[name] = $rootScope.$on(name, listener);  // bind the subscriber
        //     subs[subscriber] = sub;
        //     return subs;
        // }

        // function getAll(){
        //     return events.getAll();
        // }
        //
        // function removeeventAll() {
        //     return events.removeAll();
        // }
    }
})();

//
