
ineuronApp.controller('ProductCreateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$modal',
    function($scope, $stateParams, $http, $state, $cookies, $rootScope, $modal) {
	
	var vm = this;
	
	$scope.existedProductName=false;
	$scope.CheckProductName=function(){
		// alert("checkproductcategeryname");
		// $scope.existedProductCategoryName=VerifyExistedProductCategoryName($scope.productCategoryName,
		// $http);
		$http({
			url : '/product/productbyname',
			method : 'POST',
			data :  $scope.productName
		}).success(function(data) {
			var pc = data.value;
			if(pc==null) $scope.existedProductName=false; 
			 else $scope.existedProductName=true;
		}).error(function(data) {
			// alert('error');
			console.log("error to get product ");
		});				
	}
	
	// get productcategory list
	$http({
		url : '/product/productcategorylist',
		method : 'GET'
	}).success(function(data) {
		validateApiToken(data, $cookies, $rootScope, $modal);
		vm.productCategories = data.value;
		// alert(vm.productCategories[0].name);
	}).error(function(data) {
		ineuronApp.confirm("提示","调用失败！", 'sm', $rootScope, $modal);
		console.log("error");
	});
	
	// get formula list
	$http({
		url : '/product/formulas',
		method : 'GET'
	}).success(function(data) {
		validateApiToken(data, $cookies, $rootScope, $modal);
		vm.productFormulas = data.value;
		// alert(vm.productFormulas[0].name);
	}).error(function(data) {
		ineuronApp.confirm("提示","调用失败！", 'sm', $rootScope, $modal);
		console.log("error");
	});
	
	
	vm.createProduct = createProduct;
	function createProduct() {
	   // alert("pc id:
		// "+$scope.selectedProductCategory[0].id+$scope.selectedProductCategory[0].code+$scope.selectedProductFormula.id);
		$http({
			url : '/product/createproduct',
			method : 'POST',
			data : {
				name : $scope.productName,
				productCategoryId: $scope.selectedProductCategory[0].id,
				code: $scope.selectedProductCategory[0].code,
				formulaId: $scope.selectedProductFormula[0].id,
				description : $scope.productDescription
			}
		}).success(function(data) {
			validateApiToken(data, $cookies, $rootScope, $modal);
			ineuronApp.confirm("提示","产品添加成功！", 'sm', $rootScope, $modal);		
			$state.go("productList", {productCategoryStr: JSON.stringify($scope.selectedProductCategory[0])});
		}).error(function(data) {
			ineuronApp.confirm("提示","产品添加失败！", 'sm', $rootScope, $modal);
			console.log("error");
		})
	}
		
}]);


ineuronApp.controller('ProductListController', ['$http', '$scope', '$stateParams', '$rootScope', '$modal', '$location', '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
   function($http, $scope, $stateParams, $rootScope, $modal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	var productCategory = eval('(' + $stateParams.productCategoryStr + ')');

	$scope.productCategoryName=productCategory.name;

	$http({
		url : '/product/productlistbycategory',
		method : 'POST',
		data : productCategory.id
	}).success(function(data) {
		validateApiToken(data, $cookies, $rootScope, $modal);
		vm.products = data.value;
	}).error(function(data) {
		// alert('error');
		console.log("error");
	});

	vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers');
	vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
	                    DTColumnDefBuilder.newColumnDef(1),
	                    DTColumnDefBuilder.newColumnDef(2).notSortable(),
	                    DTColumnDefBuilder.newColumnDef(3).notSortable(),
	                    DTColumnDefBuilder.newColumnDef(4).notSortable()];

	vm.updateProduct=updateProduct;
	function updateProduct(index){
		//alert(vm.products[index].productCategory);
		$state.go("updateProduct", {productStr: JSON.stringify(vm.products[index])});		
	}

	vm.productCreate=productCreate;
	function productCreate(){
		// alert("create pro");
		$state.go("createProduct");
	}


	vm.updateManufacturingProcess=updateManufacturingProcess;
	function updateManufacturingProcess(index) {
		$state.go("productManufacturingProcess", {productStr: JSON.stringify(vm.products[index])});
	}
}]);


ineuronApp.controller('ProductUpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$modal',
   function($scope, $stateParams, $http, $state, $cookies, $rootScope, $modal) {

	var product = eval('(' + $stateParams.productStr + ')');
	$scope.productName=product.name;
	$scope.productDescription=product.description;
	$scope.existedProductName=false;
	
	var vm = this;

	$scope.CheckProductName=function(){
		$http({
			url : '/product/getproductbyname',
			method : 'POST',
			data :  $scope.productName
		}).success(function(data) {
			var pc = data.value;
			// did not change the name
			if(product.name==$scope.productName)$scope.existedProductName=false; 
			else{
				if(pc==null) $scope.existedProductName=false; 
				else $scope.existedProductName=true;
			}
		}).error(function(data) {
			ineuronApp.confirm("提示","依据产品名查询产品失败！", 'sm', $rootScope, $modal);
			console.log("error to get productbyname ");
		});				
	}

	$http({
		url : '/product/formulas',
		method : 'GET'
	}).success(function(data) {
		validateApiToken(data, $cookies, $rootScope, $modal);
		vm.productFormulas = data.value;
		for(var i in vm.productFormulas){
			if(vm.productFormulas[i].id==product.formulaId) {
				vm.productFormulas[i].ticked=true;
				break;
			}
		}
	}).error(function(data) {
		ineuronApp.confirm("提示","查询配方列表失败！", 'sm', $rootScope, $modal);
		console.log("error");
	});
	
	var productCategory;
	$http({
		url : '/product/getproductcategorybyid',
		method : 'POST',
		data :  product.productCategoryId
	}).success(function(data) {
		productCategory = data.value;
	}).error(function(data) {
		// alert('error');
		console.log("error to get productcategory ");
	});

	vm.updateProduct = updateProduct;
	function updateProduct() {
		$http({
			url : '/product/updateproduct',
			method : 'POST',
			data : {
				id : product.id,
				name : $scope.productName,
				description : $scope.productDescription,
				formulaId: $scope.selectedProductFormula[0].id
			}
		}).success(function(data) {
			validateApiToken(data, $cookies, $rootScope, $modal);
			ineuronApp.confirm("提示","修改成功！", 'sm', $rootScope, $modal);		
			$state.go("productList",{productCategoryStr: JSON.stringify(productCategory)});
		}).error(function(data) {
			ineuronApp.confirm("提示","修改失败！", 'sm', $rootScope, $modal);
			console.log("error");
		})
	}
		
}]);
