angular.module('hogentResto').config(
    function ($stateProvider, $urlRouterProvider) {

        // ADMIN

        // Authentication
        $stateProvider.state('login', {
            url: '/admin/login',
            templateUrl: '/login.html',
            controller: 'AuthController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (auth.isLoggedIn()) {
                    $state.go('student-restaurants');
                }
            }]
        }).state('register', {
            url: '/admin/register',
            templateUrl: '/register.html',
            controller: 'AuthController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (auth.isLoggedIn()) {
                    $state.go('student-restaurants');
                }
            }]
        })

        // Restaurant
        .state('admin-restaurants', {
            url: '/admin/restaurants',
            templateUrl: '/admin-restaurants.html',
            controller: 'MainController',
            controllerAs: 'vm',
            resolve: {
                postPromise: ['restaurants', function(restaurants) {
                    return restaurants.getAll();
                }]
            }
        }).state('admin-restaurants-create', {
            url: '/admin/restaurants/create',
            templateUrl: '/createRestaurant.html',
            controller: 'MainController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('student-restaurants');
                }
            }]
        }).state('admin-restaurants-edit', {
            url: '/admin/restaurants/{id}',
            templateUrl: '/restaurants.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('student-restaurants');
                }
            }],
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }]
            }
        })

        // Menus
        .state('admin-menus', {
            url: '/admin/restaurants/{id}/menus',
            templateUrl: '/menus.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }]
            }
        }).state('admin-menus-create', {
            url: '/admin/restaurants/{id}/menus/create',
            templateUrl: '/createMenu.html',
            controller: 'RestaurantsController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('student-restaurants');
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
        }).state('admin-menus-edit', {
            url: '/admin/restaurants/{id}/menus/{id2}',
            templateUrl: '/editMenu.html',
            controller: 'MenusController',
            controllerAs: 'vm',
            onEnter: ['$state', 'auth', function ($state, auth) {
                if (!auth.isLoggedIn()) {
                    $state.go('student-restaurants');
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
        }).state('admin-products', {
            url: '/admin/products',
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
                    $state.go('student-restaurants');
                }
            }]
        }).state('admin-products-new', {
            url: '/admin/products/create',
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
                    $state.go('student-restaurants');
                }
            }]
        }).state('admin-product-edit', {
            url: '/admin/products/{id}',
            templateUrl: '/editProduct.html',
            controller: 'ProductsController',
            controllerAs: 'vm',
            resolve: {
                product: ['$stateParams', 'products', function($stateParams, products) {
                    return products.get($stateParams.id);
                }]
            }
        })

        // STUDENT

        .state('student-restaurants', {
            url: '/restaurants',
            templateUrl: '/student-restaurants.html',
            controller: 'MainController',
            controllerAs: 'vm',
            resolve: {
                postPromise: ['restaurants', function(restaurants) {
                    return restaurants.getAll();
                }]
            }
        }).state('student-menus', {
            url: '/restaurants/{id}',
            templateUrl: '/student-restaurants-show.html',
            controller: 'RestaurantsStudentsController',
            controllerAs: 'vm',
            resolve: {
                restaurant: ['$stateParams', 'restaurants', function($stateParams, restaurants) {
                    return restaurants.get($stateParams.id);
                }],
                forecast: ['$stateParams', 'restaurants', function($stateParams, restaurants){
                    return restaurants.getForecast($stateParams.id);
                }]
            }
        });

        $urlRouterProvider.otherwise('restaurants');
    }
);
