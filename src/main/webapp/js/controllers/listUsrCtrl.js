angular.module('AireCYD')
	.controller('listUsrCtrl', ['HostingFactory', 'UserFactory', '$routeParams', function(HostingFactory, UserFactory, $routeParams) {
		var listUserViewModel = this;
		listUserViewModel.HostingList = [];
		listUserViewModel.User = {};

		listUserViewModel.functions = {
			readAllHostByUser: function() {
				HostingFactory.getAllHostingByUser()
					.then(function(response) {
						console.log("Reading all the host: ", response);
						listUserViewModel.HostingList = response;
					}, function(response) {
						console.log("Error reading host");
					})
			},
			getUser: function() {
				UserFactory.getUser()
					.then(function(response) {
						listUserViewModel.User = response
						listUserViewModel.functions.readAllHostByUser();
					}, function(response) {
						location.replace("../Login");
					})
			},
			deleteHost: function(id) {
				HostingFactory.deleteHost(id)
					.then(function(response) {
						console.log("Deleting Host: " + id);
						location.reload();
					}, function(response) {
						console.log("Error deleting Host: " + id);
					})
			}
		}
		listUserViewModel.functions.getUser();
	}])