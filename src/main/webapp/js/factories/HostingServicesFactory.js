angular.module('AireCYD')
	.factory("HostingServicesFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/AirbnbDAO/rest/HostServ';
		var hostServicesInterface = {
			getHost: function(id) {
				var urlid = url + '/' + id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			postServices: function(id, ArrayList) {
				var urlid = url + '/' + id;
				return $http.post(urlid, ArrayList)
					.then(function(response) {
						return response.data;
					});
			},
			putServices: function(id, ArrayList) {
				var urlid = url + '/' + id;
				return $http.put(urlid, ArrayList)
					.then(function(response) {
						return response.data;
					});
			},
			deleteServices: function(id) {
				var urlid = url + '/' + id;
				return $http.delete(urlid)
					.then(function(response) {
						return response.data;
					});
			},
		}
		return hostServicesInterface;
	}])