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
        }).state('restaurantsNew', {
            url: '/restaurants/create',
            templateUrl: '/createRestaurant.html',
            controller: 'MainController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('home');
                }
            }]
        }).state('restaurants', {
            url: '/restaurants/{id}',
            templateUrl: '/restaurants.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('home');
                }
            }],
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }]
            }
        }).state('menus', {
            url: '/restaurants/{id}/menus',
            templateUrl: '/restaurantDetailsStudents.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }]
            }
        }).state('menusNew', {
            url: '/restaurants/{id}/menus/create',
            templateUrl: '/createMenu.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('home');
                }
            }],
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }],
                getProducts: ['products', function(products) {
                    return products.getAll();
                }]
            }
        }).state('menuEdit', {
            url: '/restaurants/{id}/menus/{id2}',
            templateUrl: '/editMenu.html',
            controller: 'MenusController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('home');
                }
            }],
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }],
                menu: ['$stateParams', 'menus', function($stateParams, menus) {
                    return menus.get($stateParams.id, $stateParams.id2);
                }],
                getProducts: ['products', function(products) {
                    return products.getAll();
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
        }).state('products', {
            url: '/products',
            templateUrl: '/products.html',
            controller: 'ProductsController',
            controllerAs: 'vm',
            resolve: {
                postPromise: ['products', function(products) {
                    return products.getAll();
                }],
                product: function() {
                    return {};
                }
            },
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('home');
                }
            }]
        }).state('productsNew', {
            url: '/products/create',
            templateUrl: '/createProduct.html',
            controller: 'ProductsController',
            controllerAs: 'vm',
            resolve: {
                product: function() {
                    return {};
                }
            },
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('home');
                }
            }]
        }).state('productEdit', {
            url: '/products/{id}',
            templateUrl: '/editProduct.html',
            controller: 'ProductsController',
            controllerAs: 'vm',
            resolve: {
                product: ['$stateParams', 'products', function($stateParams, products) {
                    return products.get($stateParams.id);
                }]
            }
        });

        $urlRouterProvider.otherwise('home');
    }
);
