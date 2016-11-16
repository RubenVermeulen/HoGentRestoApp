angular.module('hogentResto').service('alertService', function(){
    var message = '';

    var setMessage = function(newObj){
        message = newObj;
    }

    var resetMessage = function(){
        message = '';
    }

    var getMessage = function(){
        return message;
    }

    return {
        setMessage: setMessage,
        resetMessage: resetMessage,
        getMessage: getMessage
    };

});