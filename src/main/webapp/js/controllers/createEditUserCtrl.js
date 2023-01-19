angular.module('AireCYD')
	.controller('createEditUserCtrl', ['UserFactory', '$routeParams', '$location', function(UserFactory, $routeParams, $location) {
		var createEditUserViewModel = this;
		createEditUserViewModel.User = {};
		createEditUserViewModel.ConfirmPassword = { pswd: null };
		createEditUserViewModel.functions = {
			where: function(route) {
				return $location.path() == route;
			},
			getUser: function() {
				UserFactory.getUser()
					.then(function(response) {
						createEditUserViewModel.User = response;
					}, function(response) {
						if ($location.path() === '/editUser') {
							location.replace("../Login");
						}
					})
			},
			/*			validarpasswrd: function() {
							var mensaje = "<p>La contrasenya debe contener:</p><ul><li>Un numero</li><li>Una minuscula</li><li>Una mayuscula</li><li>Uno de estos caracteres: @#$%^&-+=()</li><li>Entre 8-20 caracteres</li></ul>";
			
							var pswd = document.getElementById("usernamePassword").value;
							document.getElementById("message").innerHTML = mensaje;
			
							if ((pswd.value !== "") && (pswd !== null)) {
								document.getElementById("message").innerHTML = mensaje;
								return false;
							}
						},*/
			createUser: function() {
				if (createEditUserViewModel.User.password === createEditUserViewModel.ConfirmPassword.pswd && createEditUserViewModel.ConfirmPassword.pswd !== null) {
					UserFactory.postUser(createEditUserViewModel.User)
						.then(function(response) {
							console.log("Creating user", response);
							location.replace("../Login");
						}, function(response) {
							console.log("Error creating user");
							location.replace("../Login");
						})
				} else {
					console.log("Las contrasenyas no son iguales");
				}

			},
			editUser: function() {
				UserFactory.putUser(createEditUserViewModel.User)
					.then(function(response) {
						console.log("Editing user with ID", createEditUserViewModel.User.id);
						$location.path('/');
					}, function(response) {
						console.log("Error editing an user");
					})
			},
			deleteUser: function() {
				UserFactory.deleteUser()
					.then(function(response) {
						location.replace("../Login");
						console.log("Deleting user", response);
					}, function(response) {
						console.log("Error deleting an user");
					})
			},
			createEditUserSwitcher: function() {
				if (createEditUserViewModel.functions.where('/registrar')) {
					createEditUserViewModel.functions.createUser();
				}
				else if (createEditUserViewModel.functions.where('/editUser')) {
					createEditUserViewModel.functions.editUser();
				}
				else {
					console.log($location.path());
				}
			}
		}
		createEditUserViewModel.functions.getUser();
	}])