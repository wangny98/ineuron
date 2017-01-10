
ineuronApp.controller('AttributeListController', ['$http', '$scope', '$stateParams', '$rootScope', '$modal', '$location', '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
	function($http, $scope, $stateParams, $rootScope, $modal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	// var selectedProductStr = $stateParams.productStr;
	// var selectedProduct = eval('(' + selectedProductStr + ')');
	
	$http({
		url : '/product/attributelist',
		method : 'GET'
	}).success(function(data) {
		validateApiToken(data, $cookies, $rootScope, $modal);
		vm.attributes = data.value;
		// alert(vm.attributes[0].name+"
		// "+vm.attributes[0].attributeCategory.name);
	}).error(function(data, status) {
		handleSessionOutOfDateError(status, $rootScope, $modal);
		ineuronApp.confirm("提示","获取属性列表失败！", 'sm', $rootScope, $modal);
		console.log("error");
	});

	vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
			'full_numbers');
	vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
			DTColumnDefBuilder.newColumnDef(1).notSortable() ];
	
	vm.updateAttribute=updateAttribute;
	function updateAttribute(index){
		$state.go("createAndUpdateAttribute", {attributeStr: JSON.stringify(vm.attributes[index])});
	}
	
	vm.createAttribute=createAttribute;
	function createAttribute(){
		// alert("index: "+index);
		$state.go("createAndUpdateAttribute", {attributeStr: JSON.stringify(null)});
	}
	
	vm.deleteAttribute=deleteAttribute;
	function deleteAttribute(index){
		ineuronApp.confirm("确认","确定删除吗？", 'sm', $rootScope, $modal).result.then(function(clickok){  
			if(clickok){
				 $http({
					url : '/product/deleteattribute',
					method : 'POST',
					data : {
						name : vm.attributes[index].name
					}
				}).success(function(data) {
					ineuronApp.confirm("提示","删除成功！", 'sm', $rootScope, $modal);
					validateApiToken(data, $cookies, $rootScope, $modal);
					$state.go("attributeList", null, {reload:true});
				}).error(function(data,status) {
					handleSessionOutOfDateError(status, $rootScope, $modal);
					ineuronApp.confirm("提示","删除失败！", 'sm', $rootScope, $modal);
					console.log("error");
				})
			}
		});		
	}
		
}]);


ineuronApp.controller('AttributeCreateAndUpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$modal',
  function($scope, $stateParams, $http, $state, $cookies, $rootScope, $modal) {
	var attribute = eval('(' + $stateParams.attributeStr + ')');
	
	var forCreate=true;
	if (attribute!=null){
		forCreate=false;	
		$scope.attributeName=attribute.name;
		$scope.attributeCode=attribute.code;
		$scope.attributeDescription=attribute.description;
	} 
	
	var vm = this;	
	$scope.existedAttributeName=false;
	
	
	$http({
		url : '/product/attributecategorylist',
		method : 'GET'
	}).success(function(data) {
		vm.attributeCategories = data.value;
		if(!forCreate){
		for (var i in vm.attributeCategories){
			if(vm.attributeCategories[i].id==attribute.attributeCategoryId){
				vm.attributeCategories[i].ticked=true;
				break;
			}
		}
		}
		validateApiToken(data, $cookies, $rootScope, $modal);
	}).error(function(data, status) {
		handleSessionOutOfDateError(status, $rootScope, $modal);
		ineuronApp.confirm("提示","调用属性类型列表失败！", 'sm', $rootScope, $modal);
		console.log("error to get attribute category list ");
	});				

	$scope.CheckAttributeName=function(){
		
		$http({
			url : '/product/getattributebyname',
			method : 'POST',
			data :  $scope.attributeName
		}).success(function(data) {
			// validateApiToken(data, $cookies, $rootScope, $modal);
			var a = data.value;
			if(forCreate){
				if (a==null) $scope.existedAttributeName=false;
				else $scope.existedAttributeName=true;
			}
			else 
			{
				if($scope.attributeName==attribute.name) $scope.existedAttributeName=false;
				else {
					if(a==null) $scope.existedAttributeName=false; 
					else $scope.existedAttributeName=true;
			}
			}
		}).error(function(data, status) {
			handleSessionOutOfDateError(status, $rootScope, $modal);
			ineuronApp.confirm("提示","依据名称调用属性失败！", 'sm', $rootScope, $modal);
			console.log("error to get attribute ");
		});				
	}


	vm.createAndUpdateAttribute = createAndUpdateAttribute;
	function createAndUpdateAttribute() {

		if(forCreate){
			$http({
				url : '/product/createattribute',
				method : 'POST',
				data : {
					name : $scope.attributeName,
					code: $scope.attributeCode,
					description : $scope.attributeDescription,
					attributeCategoryId: $scope.selectedAttributeCategory[0].id
				}
			}).success(function(data) {
				validateApiToken(data, $cookies, $rootScope, $modal);
				ineuronApp.confirm("提示","属性添加成功！", 'sm', $rootScope, $modal);		
				$state.go("attributeList");
			}).error(function(data,status) {
				handleSessionOutOfDateError(status, $rootScope, $modal);
				ineuronApp.confirm("提示","添加属性失败！", 'sm', $rootScope, $modal);
				console.log("error");
		  		})
		}
		else{
		 $http({
			url : '/product/updateattribute',
			method : 'POST',
			data : {
				id : attribute.id,
				name : $scope.attributeName,
				code: $scope.attributeCode,
				description : $scope.attributeDescription,
				attributeCategoryId: $scope.selectedAttributeCategory[0].id
			}
		}).success(function(data) {
			validateApiToken(data, $cookies, $rootScope, $modal);
			ineuronApp.confirm("提示","属性修改成功！", 'sm', $rootScope, $modal);		
			$state.go("attributeList");
		}).error(function(data, status) {
			handleSessionOutOfDateError(status, $rootScope, $modal);
			ineuronApp.confirm("提示","修改失败！", 'sm', $rootScope, $modal);
			console.log("error");
		})
	  }
	}
	
	
	vm.backward = backward;
	function backward() {
		$state.go("attributeList");
	}
	
}]);

