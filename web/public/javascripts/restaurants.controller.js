angular.module('hogentResto').controller('RestaurantsController',
    function(restaurants, restaurant, auth) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        
        vm.editRestaurant = editRestaurant;
        vm.addMenu = addMenu;

        function editRestaurant() {
            if (!vm.restaurant.name || vm.restaurant.name === '') {
                return;
            }
            
            restaurants.edit({
                name: vm.restaurant.name,
                address: vm.restaurant.address,
                openingHours: vm.restaurant.openingHours,
                lat: vm.restaurant.coordinates.lat,
                long: vm.restaurant.coordinates.long
            });

            // vm.title = '';
            // vm.link = '';

            vm.restaurant.name = '';
        };

        function addMenu() {

            if (!vm.title || vm.title === '') {
                return;
            }

            restaurants.addMenu(restaurant._id, {
                title: vm.title,
                description: vm.description
            }).success(function(menu) {
                vm.restaurant.menus.push(menu);
            });
            vm.title = '';
            vm.description = '';
            alert('test');
        };


        // function incrementUpvotes(comment) {
        //     posts.upvoteComment(post, comment);
        // };
    }
);