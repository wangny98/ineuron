
ineuronApp.controller('MaterialListController', ['$http', '$scope', '$stateParams', '$rootScope', '$modal', '$location', '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
	function($http, $scope, $stateParams, $rootScope, $modal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	
	$http({
		url : '/material/list',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.materials = data.value;
		//alert(vm.materials[0].name+" "+vm.materials[0].name);
	}).error(function(data, status) {
		//ineuronApp.confirm("提示","获取列表失败！", 'sm', $rootScope, $modal);
		handleError(status, $rootScope, $modal);
		console.log("material list error");
	});

	vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
			'full_numbers');
	/*vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
			DTColumnDefBuilder.newColumnDef(1).notSortable() ];*/
	
	vm.updateMaterial=updateMaterial;
	function updateMaterial(index){
		$state.go("updateMaterial", {materialStr: JSON.stringify(vm.materials[index])});
	}
	
	vm.createMaterial=createMaterial;
	function createMaterial(){
		// alert("index: "+index);
		$state.go("createMaterial");
	}
	
	vm.deleteMaterial=deleteMaterial;
	function deleteMaterial(index){
		ineuronApp.confirm("确认","确定删除吗？", 'sm', $rootScope, $modal).result.then(function(clickok){  
			if(clickok){
				 $http({
					url : '/material/delete',
					method : 'POST',
					data : {
						name : vm.materials[index].name
					}
				}).success(function(data) {
					ineuronApp.confirm("提示","删除成功！", 'sm', $rootScope, $modal);
					updateApiToken(data, $cookies);
					$state.go("materialList", null, {reload:true});
				}).error(function(data, status) {
					//ineuronApp.confirm("提示","删除失败！", 'sm', $rootScope, $modal);
					handleError(status, $rootScope, $modal);
					console.log("error");
				})
			}
		});		
	}
		
}]);


ineuronApp.controller('MaterialCreateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$modal',
   function($scope, $stateParams, $http, $state, $cookies, $rootScope, $modal) {

	var vm = this;
	$scope.existedMaterialName=false;		

$scope.CheckMaterialName=function(){
	
	$http({
		url : '/material/materialbyname?name' + $scope.materialName,
		method : 'GET'
	}).success(function(data) {
		//updateApiToken(data, $cookies);
		var a = data.value;
		if(a==null) $scope.existedMaterialName=false; 
		 else $scope.existedMaterialName=true;
	}).error(function(data, status) {
		//ineuronApp.confirm("提示","依据名称调用失败！", 'sm', $rootScope, $modal);
		handleError(status, $rootScope, $modal);
		console.log("error to get material ");
	});				
}


vm.createMaterial = createMaterial;
function createMaterial() {
	
	$http({
		url : '/material/create',
		method : 'POST',
		data : {
			name : $scope.materialName,
			description : $scope.materialDescription
		}
	}).success(function(data) {
		updateApiToken(data, $cookies);
		ineuronApp.confirm("提示","原料添加成功！", 'sm', $rootScope, $modal);		
		$state.go("materialList");
	}).error(function(data, status) {
		//ineuronApp.confirm("提示","添加原料失败！", 'sm', $rootScope, $modal);
		handleError(status, $rootScope, $modal);
		console.log("create material error");
  		})
  	}

vm.backward = backward;
function backward() {
	$state.go("materialList");
}

}]);


ineuronApp.controller('MaterialUpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$modal',
  function($scope, $stateParams, $http, $state, $cookies, $rootScope, $modal) {
	var material = eval('(' + $stateParams.materialStr + ')');
	var vm = this;
	
	$scope.existedMaterialName=false;
	$scope.materialName=material.name;
	$scope.materialDescription=material.description;
	
	$scope.CheckMaterialName=function(){
		$http({
			url : '/material/materialbyname?name' + $scope.materialName,
			method : 'GET'
		}).success(function(data) {
			//updateApiToken(data, $cookies);
			var a = data.value;
			if(a==null) $scope.existedMaterialName=false; 
			 else $scope.existedMaterialName=true;
		}).error(function(data, status) {
			//ineuronApp.confirm("提示","依据名称调用失败！", 'sm', $rootScope, $modal);
			handleError(status, $rootScope, $modal);
			console.log("error to get material ");
		});				
	}


	vm.updateMaterial = updateMaterial;
	function updateMaterial() {

		$http({
			url : '/material/update',
			method : 'POST',
			data : {
				id : material.id,
				name : $scope.materialName,
				description : $scope.materialDescription
			}
		}).success(function(data) {
			updateApiToken(data, $cookies);
			ineuronApp.confirm("提示","原料修改成功！", 'sm', $rootScope, $modal);		
			$state.go("materialList");
		}).error(function(data, status) {
			//ineuronApp.confirm("提示","修改失败！", 'sm', $rootScope, $modal);
			handleError(status, $rootScope, $modal);
			console.log("error");
		})
	}
	
	
	vm.backward = backward;
	function backward() {
		$state.go("materialList");
	}
	
}]);

