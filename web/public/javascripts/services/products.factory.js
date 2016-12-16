angular.module('hogentResto').factory('products', function ($http, auth) {

    var o = {
        products: [],
        getAll: getAll,
        create: create,
        edit: edit,
        get: get,
        deleteProduct: deleteProduct
    };

    function getAll() {
        return $http.get('/products').success(function (data) {
            angular.copy(data, o.products);
        });
    }

    function create(product) {
        return $http.post('/products', product, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function (data) {
            o.products.push(data);
        });
    }

    function edit(id, product){
        return $http.put('/products/' + id, product, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function(data){
            o.products.push(data);
        })
    }

    function get(id) {
        return $http.get('/products/' + id).then(function (res) {
            return res.data;
        });
    }

    function deleteProduct(id) {
        return $http.delete('/products/' + id, {
            headers: {
                Authorization: 'Bearer ' + auth.getToken()
            }
        }).success(function(data){
            for (var key in o.products) {
                if (data._id === o.products[key]._id) {
                    o.products.splice(key, 1);
                    break;
                }
            }
        })
    }

    return o;

});
