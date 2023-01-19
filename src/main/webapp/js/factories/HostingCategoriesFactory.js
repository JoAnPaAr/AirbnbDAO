angular.module('AireCYD')
	.factory("HostingCategoriesFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/AirbnbDAO/rest/HostCat';
		var hostCategoriesInterface = {
			getHostCat: function(id) {
				var urlid = url + '/' + id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			}
		}
		return hostCategoriesInterface;
	}])