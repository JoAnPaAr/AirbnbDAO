angular.module('AireCYD')
	.factory("CategoryFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/AirbnbDAO/rest/Category';
		var categoriesInterface = {
			getAllCategories: function() {
				var urlall = url + '/todos';
				return $http.get(urlall)
					.then(function(response) {
						return response.data;
					});
			},
			getCategory: function(id) {
				var urlid = url + '/' + id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
		}
		return categoriesInterface;
	}])