angular.module('hogentResto').factory('restaurants', function ($http, auth) {

    var o = {
        restaurants: [],
        menus: [],
        getAll: getAll,
        create: create,
        edit: edit,
        get: get,
        createMenu: createMenu,
        deleteRestaurant: deleteRestaurant,
        deleteMenu: deleteMenu
    };

    function getAll() {
        return $http.get('/restaurants').success(function (data) {
            angular.copy(data, o.restaurants);
        });
    }

    function create(restaurant) {
        return $http.post('/restaurants', restaurant, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function (data) {
            o.restaurants.push(data);
        });
    }

    function edit(id, restaurant){
        return $http.put('/restaurants/' + id, restaurant, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function(data){
            o.restaurants.push(data);
        })
    }

    function get(id) {
        return $http.get('/restaurants/' + id).then(function (res) {
            return res.data;
        });
    }

    function createMenu(id, menu) {
        return $http.post('/restaurants/' + id + '/menus', menu, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function(data){
            o.restaurants.push(data);
        });
    }

    function deleteRestaurant(id, restaurant) {
        return $http.delete('/restaurants/' + id, restaurant, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function(data){
            o.restaurants.splice(o.restaurants.indexOf(restaurant), 1)
        })
    }

    function deleteMenu(id, menuid, menu){
        return $http.delete('/restaurants/' + id + '/menus/' + menuid, menu, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function(data){
            // o.menus.splice(o.menus.indexOf(menu), 1)
        })
    }

    return o;

});
