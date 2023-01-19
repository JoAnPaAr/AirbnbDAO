angular.module('AireCYD')
	.controller('listCtrl', ['HostingFactory', 'UserFactory', 'FavoritoFactory', '$routeParams', function(HostingFactory, UserFactory, FavoritoFactory, $routeParams) {
		var listViewModel = this;
		listViewModel.HostingList = [];
		listViewModel.User = {};
		listViewModel.functions = {
			readAllHost: function() {
				HostingFactory.getAllHost()
					.then(function(response) {
						console.log("Reading all the host: ", response);
						listViewModel.HostingList = response;
					}, function(response) {
						console.log("Error reading host");
					})
			},
			getUser: function() {
				UserFactory.getUser()
					.then(function(response) {
						listViewModel.User = response
						listViewModel.functions.readAllHost();
					}, function(response) {
						location.replace("../Login");
					})
			},
			fav: function(id) {
				console.log("Realizando Fav a casa con id", id);

				FavoritoFactory.put(id)
					.then(function(response) {
						console.log("Fav con exito");
						location.reload();
					}, function(response) {
						console.log("Error favorites");
					})
			}
		}
		listViewModel.functions.getUser();
	}])