angular.module('hogentResto').service('alertService', function(){
    var message = '';
    var type = '';

    var setAlert = function(newObj, specie){
        message = newObj;
        type = specie;
    }

    var resetAlert = function(){
        message = '';
        type = '';
    }

    var getAlert = function(){
        return {
            message: message,
            type: type
        }
    }

    var getMessage = function(){
        return message;
    }

    var getType = function(){
        return type;
    }

    return {
        setAlert: setAlert,
        resetAlert: resetAlert,
        getAlert: getAlert,
        getMessage: getMessage,
        getType: getType
    };

});
