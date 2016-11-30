angular.module('hogentResto').controller('RestaurantsStudentsController',
    function($state, restaurants, restaurant) {
        var vm = this;

        vm.restaurant = restaurant;

        vm.toggleMenus = toggleMenus;
        vm.hasMenusThisDay = hasMenusThisDay;
        vm.initiateMenuToday = initiateMenuToday;

        function initiateMenuToday() {
            toggleMenus(new Date().getDay());
        }

        function hasMenusThisDay() {
            return vm.menus ? vm.menus.length !== 0 : false;
        }

        function toggleMenus(day) {
            vm.menus = restaurants.menus.filter(function(menu){
                if((new Date(menu.availableAt)).getDay() === day){
                    vm.date = menu.availableAt;
                    return menu;
                }
            });

            var today = new Date();

            if (today.getDay() === 6){
                vm.date = today.setDate(today.getDate() + day + 1);
            }
            else{
                vm.date = today.setDate(today.getDate() + day - today.getDay());
            }

            angular.element("#menu-days a").removeClass('btn-primary');
            angular.element("#menu-day-" + day).addClass('btn-primary');
        }
    }
);
