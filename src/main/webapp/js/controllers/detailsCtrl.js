angular.module('AireCYD')
	.controller('detailsCtrl', ['HostingFactory', 'UserFactory', 'HostingServicesFactory', '$routeParams', function(HostingFactory, UserFactory, HostingServicesFactory, $routeParams) {
		var detailsViewModel = this;
		detailsViewModel.HostingList = [];
		detailsViewModel.Hosting = {};
		detailsViewModel.User = {};

		detailsViewModel.functions = {
			getUser: function() {
				UserFactory.getUser()
					.then(function(response) {
						console.log("Entering detailsViewModel with $routeParams.ID=", $routeParams.ID);
						detailsViewModel.functions.getHost($routeParams.ID);
						detailsViewModel.functions.readSuggest($routeParams.ID);
					}, function(response) {
						location.replace("../Login");
					})
			},
			getHost: function(id) {
				HostingFactory.getHostid(id)
					.then(function(response) {
						console.log("Obteniendo host con id: ", response.id);
						detailsViewModel.Hosting = response;
						UserFactory.getUserid(detailsViewModel.Hosting.idu).then(function(response) {
							detailsViewModel.User = response;
						})
					}, function(response) {
						console.log("Error reading host");
					})
			},

			readSuggest: function(id) {
				HostingFactory.getSuggest(id)
					.then(function(response) {
						console.log("Reading all the host: ", response);
						detailsViewModel.HostingList = response;
					}, function(response) {
						console.log("Error reading host list");
					})
			}

		}
		detailsViewModel.functions.getUser();
	}])