angular.module('hogentResto').config(
    function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: '/home.html',
            controller: 'MainController',
            controllerAs: 'vm',
            resolve: {
                postPromise: ['restaurants', function(restaurants) {
                    return restaurants.getAll();
                }]
            }
        }).state('restaurants', {
            url: '/restaurants/{id}',
            templateUrl: '/restaurants.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }]
            }
        }).state('menus', {
            url: '/menus/{id}',
            templateUrl: '/menus.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }]
            }
        }).state('login', {
            url: '/login',
            templateUrl: '/login.html',
            controller: 'AuthController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (auth.isLoggedIn()) {
                    $state.go('home');
                }
            }]
        }).state('register', {
            url: '/register',
            templateUrl: '/register.html',
            controller: 'AuthController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (auth.isLoggedIn()) {
                    $state.go('home');
                }
            }]
        });

        $urlRouterProvider.otherwise('home');
    }
);






