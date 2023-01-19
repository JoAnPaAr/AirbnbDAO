angular.module('AireCYD')
	.controller('buscarCtrl', ['HostingFactory', 'UserFactory', '$routeParams', function(HostingFactory, UserFactory, $routeParams) {
		var buscarViewModel = this;
		buscarViewModel.HostingListBuscado = [];
		buscarViewModel.FilterText = {};
		buscarViewModel.User = {};
		buscarViewModel.FilterMinLikes = { Min: 0 };
		buscarViewModel.FilterReserv = {};
		buscarViewModel.FilterOrder = {};
		buscarViewModel.FilterOrder = {};

		buscarViewModel.functions = {
			getUser: function() {
				UserFactory.getUser()
					.then(function(response) {
						buscarViewModel.User = response;
						buscarViewModel.functions.clear();
					}, function(response) {
						location.replace("../Login");
					})
			},
			iniciar: function() {
				if (buscarViewModel.FilterText === undefined || buscarViewModel.FilterText === null || buscarViewModel.FilterText === "") {
					buscarViewModel.FilterText = "Sharkshuisers";
				}
				if (buscarViewModel.FilterMinLikes.Min === null) {
					buscarViewModel.FilterMinLikes.Min = 999;
				}
				if (buscarViewModel.FilterReserv === null) {
					buscarViewModel.FilterReserv = 2;
				}
				if (buscarViewModel.FilterOrder === null) {
					buscarViewModel.FilterOrder = 1;
				}
			},
			ponerTimeoutBuscar: function() {
				timeout = setTimeout(buscarViewModel.functions.buscar, 1000);
			},
			ponerTimeoutLimpiar: function() {
				timeout = setTimeout(buscarViewModel.functions.clear, 2000);
			},
			clear: function() {
				buscarViewModel.FilterText = null;
				buscarViewModel.FilterMinLikes.Min = 0;
				buscarViewModel.FilterReserv = null;
				buscarViewModel.FilterOrder = null;
			},
			prepararBusq: function() {
				buscarViewModel.functions.iniciar();
				buscarViewModel.functions.buscar();
				buscarViewModel.functions.clear();
			},
			buscar: function() {
				HostingFactory.search(buscarViewModel.FilterText, buscarViewModel.FilterMinLikes.Min, buscarViewModel.FilterReserv, buscarViewModel.FilterOrder)
					.then(function(response) {
						console.log("Obteniendo hosts con id: ", response.id);
						buscarViewModel.HostingListBuscado = response;
					}, function(response) {
						console.log("Error reading host");
					})
			}
		}
		buscarViewModel.functions.getUser();
	}])