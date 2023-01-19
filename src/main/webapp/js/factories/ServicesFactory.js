angular.module('AireCYD')
	.factory("ServicesFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/AirbnbDAO/rest/Services';
		var servicesInterface = {
			getAllServices: function() {
				var urlall = url + '/todos';
				return $http.get(urlall)
					.then(function(response) {
						return response.data;
					});
			},
			getServices: function(id) {
				var urlid = url + '/' + id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
		}
		return servicesInterface;
	}])