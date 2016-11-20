angular.module('hogentResto').factory('menus', function ($http, auth) {

    var o = {
        menus: [],
        restaurant: [],
        get: get
    };

    function get(id) {
        return $http.get('/restaurants/' + id).then(function (res) {
            return res.data;
        });
    }

    return o;

});
