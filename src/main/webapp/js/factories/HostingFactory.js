angular.module('AireCYD')
	.factory("HostingFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/AirbnbDAO/rest/Hosting';
		var hostInterface = {
			getAllHost: function() {
				var urlid = url + '/todos';
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			getHostid: function(id) {
				var urlid = url + '/' + id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			getSuggest: function(id) {
				var urlid = url + '/recomendacion/' + id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			getAllHostingByUser: function() {
				return $http.get(url)
					.then(function(response) {
						return response.data;
					});
			},
			put: function(Hosting, Category) {
				var urlid = url + '/' + Hosting.id + '/' + Category.id;
				return $http.put(urlid, Hosting)
					.then(function(response) {
						return response.data;
					});
			},
			deleteHost: function(id) {
				var urlid = url + '/' + id;
				return $http.delete(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			search: function(String, int1, int2, int3) {
				var urlid = url + '/' + String + '/' + int1 + '/' + int2 + '/' + int3;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			post: function(Hosting, Category) {
				var urlid = url + '/' + Category.id;
				return $http.post(urlid, Hosting)
					.then(function(response) {
						return response.data;
					});
			},
		}
		return hostInterface;
	}])