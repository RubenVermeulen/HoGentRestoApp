angular.module('hogentResto').controller('RestaurantsController',
    function(restaurants, restaurant, auth) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        
        vm.editRestaurant = editRestaurant;

        function editRestaurant() {
            if (!vm.restaurant.name || vm.restaurant.name === '') {
                return;
            }
            
            restaurants.edit({
                name: vm.restaurant.name,
                address: vm.restaurant.address
            });

            // vm.title = '';
            // vm.link = '';

            vm.restaurant.name = '';
        };

        // function incrementUpvotes(comment) {
        //     posts.upvoteComment(post, comment);
        // };
    }
);