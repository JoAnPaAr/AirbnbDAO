angular.module('AireCYD')
	.controller('createEditHouseCtrl', ['HostingFactory', 'UserFactory', 'HostingServicesFactory', 'ServicesFactory', 'CategoryFactory', 'HostingCategoriesFactory', '$routeParams', '$location', function(HostingFactory, UserFactory, HostingServicesFactory, ServicesFactory, CategoryFactory, HostingCategoriesFactory, $routeParams, $location) {
		var createEditHouseViewModel = this;
		createEditHouseViewModel.Hosting = {};
		createEditHouseViewModel.User = {};
		createEditHouseViewModel.Category = {};
		createEditHouseViewModel.listaServices = [];
		createEditHouseViewModel.listaCategories = [];
		createEditHouseViewModel.listaServicesChecked = [];
		createEditHouseViewModel.listaServicesRecup = [];
		createEditHouseViewModel.listaServicesEdit = [];

		createEditHouseViewModel.functions = {
			where: function(route) {
				return $location.path() == route;
			},
			getUser: function() {
				UserFactory.getUser()
					.then(function(response) {
						createEditHouseViewModel.User = response; 
						console.log("Entering createEditHouseCtrl with $routeParams.ID=", $routeParams.ID);
						if ($routeParams.ID !== undefined) createEditHouseViewModel.functions.readHouse($routeParams.ID);
						createEditHouseViewModel.functions.readServices();
						createEditHouseViewModel.functions.readCategories();
					}, function(response) {
						location.replace("../Login");
					})
			},
			readHouse: function(id) {
				HostingFactory.getHostid(id)
					.then(function(response) {
						createEditHouseViewModel.Hosting = response;
						createEditHouseViewModel.listaServicesChecked = HostingServicesFactory.getHost(id);
						createEditHouseViewModel.functions.readHouseCategory($routeParams.ID);
						console.log("Reading Host with id:", response.id);
					}, function(response) {
						console.log("Error creating host");
					})
			},
			readServices: function() {
				ServicesFactory.getAllServices()
					.then(function(response) {
						createEditHouseViewModel.listaServices = response;
						createEditHouseViewModel.functions.loadCheck($routeParams.ID);
						console.log("Reading services", createEditHouseViewModel.listaServices);
					}, function(response) {
						console.log("Error reading services");
					})
			},
			loadCheck: function(id) {
				if (id === undefined) {
					for (let i = 0; i < 9; i++) {
						createEditHouseViewModel.listaServicesChecked[i] = -1;
					}
				} else {
					HostingServicesFactory.getHost(id).then(function(response) {
						createEditHouseViewModel.listaServicesRecup = response;
						var index = 0;
						for (let i = 0; i < 9; i++) {
							if (index < createEditHouseViewModel.listaServicesRecup.length) {
								if (createEditHouseViewModel.listaServices[i].id === createEditHouseViewModel.listaServicesRecup[index].ids) {
									createEditHouseViewModel.listaServicesChecked[i] = i;
									index++;
								} else {
									createEditHouseViewModel.listaServicesChecked[i] = -1;
								}
							} else {
								createEditHouseViewModel.listaServicesChecked[i] = -1;
							}
						}
					})
				}
			},
			readCategories: function() {
				CategoryFactory.getAllCategories()
					.then(function(response) {
						createEditHouseViewModel.listaCategories = response;
						console.log("Reading categories");
					}, function(response) {
						console.log("Error reading categories");
					})
			},
			readHouseCategory: function(id) {
				HostingCategoriesFactory.getHostCat(id)
					.then(function(response) {
						createEditHouseViewModel.Category.id = response[0].idct;
						console.log("Reading category");
					}, function(response) {
						console.log("Error reading category");
					})
			},
			createHouse: function() {
				var flag = false;
				console.log(createEditHouseViewModel.listaServicesChecked);
				for (let i = 0; i < 9; i++) {
					if (createEditHouseViewModel.listaServicesChecked[i] !== -1) {
						flag = true;
					}
				}
				if (flag) {
					if (createEditHouseViewModel.Category === "") {
						createEditHouseViewModel.Category = 1;
					}
					HostingFactory.post(createEditHouseViewModel.Hosting, createEditHouseViewModel.Category)
						.then(function(response) {
							console.log("Creating host", response);
							HostingServicesFactory.postServices(response, createEditHouseViewModel.listaServicesChecked);
						}, function(response) {
							console.log("Error creating host");
						})
				}
			},
			editHouse: function(id) {
				console.log(createEditHouseViewModel.listaServicesChecked);
				console.log("Editando casa con id", createEditHouseViewModel.Hosting);
				if (createEditHouseViewModel.listaServicesChecked !== undefined || createEditHouseViewModel.listaServicesChecked !== null) {
					HostingFactory.put(createEditHouseViewModel.Hosting, createEditHouseViewModel.Category)
						.then(function(response) {
							HostingServicesFactory.deleteServices(response).then(function(response) {
								for (let i = 0; i < 9; i++) {
									if (createEditHouseViewModel.listaServicesChecked[i] == i) {
										createEditHouseViewModel.listaServicesEdit[i] = createEditHouseViewModel.listaServicesChecked[i];
									} else {
										createEditHouseViewModel.listaServicesEdit[i] = -1;
									}
								}
								console.log(createEditHouseViewModel.listaServicesEdit);
								HostingServicesFactory.postServices(response, createEditHouseViewModel.listaServicesEdit);
							})
						}, function(response) {
							console.log("a");
						})
				}
			},
			createEditHouseSwitcher: function() {
				if (createEditHouseViewModel.functions.where('/crearCasa')) {
					createEditHouseViewModel.functions.createHouse();
				}
				else if (createEditHouseViewModel.functions.where('/editCasa/' + createEditHouseViewModel.Hosting.id)) {
					console.log("Editando casa con id", createEditHouseViewModel.Hosting.id);
					createEditHouseViewModel.functions.editHouse(createEditHouseViewModel.Hosting.id);
				}
				else {
					console.log($location.path());
				}
				$location.path('/tusCasas');
			}
		}
		createEditHouseViewModel.functions.getUser();
	}])