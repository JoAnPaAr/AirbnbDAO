angular.module('AireCYD', ['ngRoute'])
	.config(function($routeProvider) {
		$routeProvider
			.when("/", {
				controller: "listCtrl",
				controllerAs: "listVM",
				templateUrl: "ListaCasas.html",
				resolve: {
					// produce 500 miliseconds (0,5 seconds) of delay that should be enough to allow the server
					//does any requested update before reading the orders.
					// Extracted from script.js used as example on https://docs.angularjs.org/api/ngRoute/service/$route
					delay: function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 500);
						return delay.promise;
					}
				}
			})
			.when("/editCasa/:ID", {

				controller: "createEditHouseCtrl",
				controllerAs: "createEditHouseVM",
				templateUrl: "create_edithouse.html",
				resolve: {
					// produce 500 miliseconds (0,5 seconds) of delay that should be enough to allow the server
					//does any requested update before reading the orders.
					// Extracted from script.js used as example on https://docs.angularjs.org/api/ngRoute/service/$route
					delay: function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 1000);
						return delay.promise;
					}
				}
			})
			.when("/crearCasa", {

				controller: "createEditHouseCtrl",
				controllerAs: "createEditHouseVM",
				templateUrl: "create_edithouse.html",

			}).when("/editUser", {

				controller: "createEditUserCtrl",
				controllerAs: "createEditUserVM",
				templateUrl: "create_edituser.html",

			}).when("/registrar", {

				controller: "createEditUserCtrl",
				controllerAs: "createEditUserVM",
				templateUrl: "create_edituser.html",

			})
			.when("/detalles/:ID", {
				controller: "detailsCtrl",
				controllerAs: "detailsVM",
				templateUrl: "details.html",

			})
			.when("/tusCasas", {
				controller: "listUsrCtrl",
				controllerAs: "listUsrVM",
				templateUrl: "ListaCasaUser.html",

			})
			.when("/buscar", {
				controller: "buscarCtrl",
				controllerAs: "BuscarVM",
				templateUrl: "Buscar.html",

			})
			.when("/404", {
				controller: "headerCtrl",
				templateUrl: "Error404.jsp",
			})
			.when("/500", {
				templateUrl: "Error500.jsp",
			})
			.when("/**", {
				templateUrl: "ErrorGenerico.jsp",
			})
			.otherwise({
				redirectTo: '/**'
			})
	})