angular.module('AireCYD')
	.factory("FavoritoFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/AirbnbDAO/rest/Fav';
		var favInterface = {
			put: function(id) {
				var urlid = url + '/' + id;
				return $http.put(urlid)
					.then(function(response) {
						return response.data;
					});
			},
		}
		return favInterface;
	}])