angular.module('hogentResto').factory('restaurants', function ($http, auth) {

    var o = {
        restaurants: [],
        getAll: getAll,
        create: create,
        edit: edit,
        get: get,
        addMenu: addMenu,
        deleteRestaurant: deleteRestaurant
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
        return $http.put('/restaurants/' + id, restaurant).success(function(data){
            o.restaurants.push(data);
        })
    }

    function get(id) {
        return $http.get('/restaurants/' + id).then(function (res) {
            return res.data;
        });
    }

    function addMenu(id, menu) {
        return $http.post('/restaurants/' + id + '/menus', menu, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        });
    }

    function deleteRestaurant(id, restaurant) {
        return $http.delete('/restaurants/' + id, restaurant).success(function(data){
            o.restaurants.push(data);
        })
    }

    // o.upvoteComment = function(restaurant, comment) {
    //     return $http.put('/restaurants/' + restaurant._id + '/comments/' + comment._id + '/upvote', null, {
    //         headers: {
    //             Authorization: 'Bearer ' + auth.getToken()
    //         }
    //     }).success(function(data) {
    //         comment.upvotes += 1;
    //     });
    // };
    return o;

});