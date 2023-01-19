angular.module('AireCYD')
	.factory("UserFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/AirbnbDAO/rest/User';
		var usersInterface = {
			getAllUsers: function() {
				var urlall = url + '/todos';
				return $http.get(urlall)
					.then(function(response) {
						return response.data;
					});
			},
			getUser: function() {
				return $http.get(url)
					.then(function(response) {
						return response.data;
					});
			},
			getUserid: function(id) {
				var urlid = url + '/' + id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			putUser: function(user) {
				return $http.put(url, user)
					.then(function(response) {
						return response.data;
					});
			},
			deleteUser: function() {
				return $http.delete(url)
					.then(function(response) {
						return response.data;
					});
			},
			postUser: function(user) {
				return $http.post(url, user)
					.then(function(response) {
						return response.data;
					});
			},
		}
		return usersInterface;
	}])