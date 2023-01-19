angular.module('AireCYD')
	.controller('headerCtrl', ['UserFactory', '$location', function(UserFactory, $location) {
		var headerViewModel = this;
		headerViewModel.user = {};
		headerViewModel.functions = {
			where: function(route) {
				console.log($location.path());
				return $location.path() == route;
			},
			getUser: function() {
				UserFactory.getUser()
					.then(function(response) {
						headerViewModel.user = response;
					}, function(response) {						
					})
			}
		}
		headerViewModel.functions.getUser();
	}])