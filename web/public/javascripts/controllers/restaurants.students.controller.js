angular.module('hogentResto').controller('RestaurantsStudentsController',
    function($state, restaurants, restaurant, forecast) {
        var vm = this;

        vm.restaurant = restaurant;
        vm.forecast = forecast.times;
        vm.recommendedHour = new Date(parseInt(forecast.recommendedHour)*1000);

        console.log(window.forecast);
        console.log(vm.recommendedHour);

        initialize();

        vm.toggleMenus = toggleMenus;
        vm.hasMenusThisDay = hasMenusThisDay;
        vm.isDayDisabled = isDayDisabled;
        vm.getTrafficClass = getTrafficClass;
        vm.getTrafficText = getTrafficText;

        function initialize() {
            var day = new Date().getDay();
            toggleMenus(day);

            if (day > 1 && day < 6) {
                for (var i = day - 1; i > 0; i--) {
                    angular.element("#menu-day-" + i).attr('disabled', 'disabled');
                }
            }

            generateChart();

        }

        function getTrafficClass(occupancy) {
            switch (getTrafficGrade(occupancy)) {
                case 0: return 'circle-traffic-green';
                case 1: return 'circle-traffic-orange';
                case 2: return 'circle-traffic-red';
            }
        }

        function getTrafficText(occupancy) {
            switch (getTrafficGrade(occupancy)) {
                case 0: return 'Rustig';
                case 1: return 'Druk';
                case 2: return 'Heel druk';
            }
        }

        function getTrafficGrade(occupancy) {
            if (occupancy < 0.30) {
                return 0
            }
            else if (occupancy < 0.70) {
                return 1
            }
            else {
                return 2;
            }
        }

        function isDayDisabled(day) {
            var attr = angular.element("#menu-day-" + day).attr('disabled');

            console.log(attr);

            if (attr !== undefined && attr !== false) {
                return true;
            }

            return false;
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

        function generateChart(){
            var dataset = vm.forecast;

            var data = [];

            for (var key in dataset) {
                var obj = dataset[key];
                console.log(obj.time);
                data.push([parseInt(obj.time) * 1000, parseFloat(obj.occupancy)]);
            }

            Highcharts.setOptions({
                global: {
                    useUTC: false
                }
            });

            Highcharts.chart('graph', {
                chart: {
                    zoomType: 'x'
                },
                title: {
                    text: null
                },
                subtitle: {
                    text: null
                },
                xAxis: {
                    title: {
                        text: 'Tijdstip'
                    },
                    type: 'datetime',
                    plotLines: [{
                        color: '#0113ff', // Red
                        width: 2,
                        value: 1480937460 * 1000,
                        zIndex: 10
                    }]
                },
                yAxis: {
                    title: {
                        text: 'Bezettingsgraad'
                    },
                    min: 0,
                    max: 1,
                    tickInterval: 0.1
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    area: {
                        fillColor: {
                            linearGradient: {
                                x1: 0,
                                y1: 0.4,
                                x2: 0,
                                y2: 1
                            },
                            stops: [
                                [0, Highcharts.getOptions().colors[3]],
                                [1, Highcharts.Color(Highcharts.getOptions().colors[2]).setOpacity(0.8).get('rgba')]
                            ]
                        },
                        marker: {
                            radius: 2
                        },
                        lineWidth: 1,
                        states: {
                            hover: {
                                lineWidth: 1
                            }
                        },
                        threshold: null
                    }
                },

                series: [{
                    type: 'area',
                    name: 'Bezettingsgraad keuken',
                    data: data
                }]
            });
        }
    }
);
