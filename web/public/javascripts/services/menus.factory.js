angular.module('hogentResto').factory('menus', function ($http, auth) {

    var o = {
        menus: [],
        restaurant: [],
        get: get,
        edit: edit
    };

    function get(restoid, menuid) {
        return $http.get('/restaurants/' + restoid + '/menus/' + menuid ).then(function (res) {
            return res.data;
        });
    }

    function edit(restoid, menuid, menu){
        return $http.put('/restaurants/' + restoid + '/menus/' + menuid, menu, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function(data){
            o.menus.push(data);
        })
    }

    return o;

});
