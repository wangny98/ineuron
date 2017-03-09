
ineuronApp.controller('ProductPackageTypeListController', ['$http', '$scope', '$stateParams', '$rootScope', '$uibModal', '$location', 
                                                           '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
	function($http, $scope, $stateParams, $rootScope, $uibModal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	
	$http({
		url : '/product/productpackagetypelist',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.productPackageTypes = data.value;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("error");
	});

	/*vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
			'full_numbers');
	vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
			DTColumnDefBuilder.newColumnDef(1).notSortable() ];*/
	
	vm.updateProductPackageType=updateProductPackageType;
	function updateProductPackageType(index){
		$state.go("createAndUpdateProductPackageType", {productPackageTypeStr: JSON.stringify(vm.productPackageTypes[index])});
	}
	
	vm.createProductPackageType=createProductPackageType;
	function createProductPackageType(){
		// alert("index: "+index);
		$state.go("createAndUpdateProductPackageType", {productPackageTypeStr: JSON.stringify(null)});
	}
	
	vm.deleteProductPackageType=deleteProductPackageType;
	function deleteProductPackageType(index){
		ineuronApp.confirm("确认","确定删除吗？", 'sm', $rootScope, $uibModal).result.then(function(clickok){  
			if(clickok){
				 $http({
					url : '/product/deleteproductpackagetype',
					method : 'POST',
					data : {
						id : vm.productPackageTypes[index].id
					}
				}).success(function(data) {
					ineuronApp.confirm("提示","删除成功！", 'sm', $rootScope, $uibModal);
					updateApiToken(data, $cookies);
					$state.go("productPackageTypeList", null, {reload:true});
				}).error(function(data,status) {
					handleError(status, $rootScope, $uibModal);
					console.log("error");
				})
			}
		});		
	}
		
}]);


ineuronApp.controller('ProductPackageTypeCreateAndUpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$uibModal',
  function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal) {
	var productPackageType = eval('(' + $stateParams.productPackageTypeStr + ')');
	var vm = this;	
	var forCreate=true;
	vm.units = [
	            { name: "升", ticked: true}, 
	            { name: "千克", ticked: false }
	            ]; 
	
	//for update case
	if (productPackageType!=null){
		forCreate=false;	
		$scope.price=productPackageType.price;
		$scope.cost=productPackageType.cost;
		$scope.volume=productPackageType.volume;
		$scope.type=productPackageType.type;
		
		for(var i in vm.units){
			if(vm.units[i].name==productPackageType.unit){
				vm.units[i].ticked=true;
				break;
			}
		}
	} 
		
	$scope.existedProductPackageType=false;
		
	$scope.CheckProductPackageType=function(){
				
	}


	vm.createAndUpdateProductPackageType = createAndUpdateProductPackageType;
	function createAndUpdateProductPackageType() {

		if(forCreate){
			$http({
				url : '/product/createproductpackagetype',
				method : 'POST',
				data : {
					price : $scope.price,
					cost: $scope.cost,
					volume : $scope.volume,
					unit: $scope.selectedUnit[0].name,
					type: $scope.type
				}
			}).success(function(data) {
				updateApiToken(data, $cookies);
				ineuronApp.confirm("提示","添加成功！", 'sm', $rootScope, $uibModal);		
				$state.go("productPackageTypeList");
			}).error(function(data,status) {
				handleError(status, $rootScope, $uibModal);
				console.log("error");
		  		})
		}
		else{
		 $http({
			url : '/product/updateproductpackagetype',
			method : 'POST',
			data : {
				id : productPackageType.id,
				price : $scope.price,
				cost: $scope.cost,
				volume : $scope.volume,
				unit: $scope.selectedUnit[0].name,
				type: $scope.type
			}
		}).success(function(data) {
			updateApiToken(data, $cookies);
			ineuronApp.confirm("提示","修改成功！", 'sm', $rootScope, $uibModal);		
			$state.go("productPackageTypeList");
		}).error(function(data, status) {
			handleError(status, $rootScope, $uibModal);
			console.log("error");
		})
	  }
	}
	
	
	vm.backward = backward;
	function backward() {
		$state.go("productPackageTypeList");
	}
	
}]);

