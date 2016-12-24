
ineuronApp.controller('@Xxx@ListController', ['$http', '$scope', '$stateParams', '$rootScope', '$modal', '$location', '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
	function($http, $scope, $stateParams, $rootScope, $modal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	
	$http({
		url : '/product/@xxx@list',
		method : 'GET'
	}).success(function(data) {
		validateApiToken(data, $cookies, $rootScope, $modal);
		vm.@xxx@s = data.value;
	}).error(function(data) {
		ineuronApp.confirm("提示","获取列表失败！", 'sm', $rootScope, $modal);
		console.log("@xxx@ list error");
	});

	vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
			'full_numbers');
	/*vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
			DTColumnDefBuilder.newColumnDef(1).notSortable() ];*/
	
	vm.update@Xxx@=update@Xxx@;
	function update@Xxx@(index){
		$state.go("update@Xxx@", {@xxx@Str: JSON.stringify(vm.@xxx@s[index])});
	}
	
	vm.create@Xxx@=create@Xxx@;
	function create@Xxx@(){
		// alert("index: "+index);
		$state.go("create@Xxx@");
	}
	
	vm.delete@Xxx@=delete@Xxx@;
	function delete@Xxx@(index){
		ineuronApp.confirm("确认","确定删除吗？", 'sm', $rootScope, $modal).result.then(function(clickok){  
			if(clickok){
				 $http({
					url : '/product/delete@xxx@',
					method : 'POST',
					data : {
						name : vm.@xxx@s[index].name
					}
				}).success(function(data) {
					ineuronApp.confirm("提示","删除成功！", 'sm', $rootScope, $modal);
					validateApiToken(data, $cookies, $rootScope, $modal);
					$state.go("@xxx@List", null, {reload:true});
				}).error(function(data) {
					ineuronApp.confirm("提示","删除失败！", 'sm', $rootScope, $modal);
					console.log("error");
				})
			}
		});		
	}
		
}]);


ineuronApp.controller('@Xxx@CreateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$modal',
   function($scope, $stateParams, $http, $state, $cookies, $rootScope, $modal) {

	var vm = this;
	$scope.existed@Xxx@Name=false;		

$scope.Check@Xxx@Name=function(){
	
	$http({
		url : '/product/@xxx@byname?name' + $scope.@xxx@Name,
		method : 'GET'
	}).success(function(data) {
		//validateApiToken(data, $cookies, $rootScope, $modal);
		var a = data.value;
		if(a==null) $scope.existed@Xxx@Name=false; 
		 else $scope.existed@Xxx@Name=true;
	}).error(function(data) {
		ineuronApp.confirm("提示","依据名称调用失败！", 'sm', $rootScope, $modal);
		console.log("error to get @xxx@ ");
	});				
}


vm.create@Xxx@ = create@Xxx@;
function create@Xxx@() {
	
	$http({
		url : '/product/create@xxx@',
		method : 'POST',
		data : {
			name : $scope.@xxx@Name,
			description : $scope.@xxx@Description
		}
	}).success(function(data) {
		validateApiToken(data, $cookies, $rootScope, $modal);
		ineuronApp.confirm("提示","添加成功！", 'sm', $rootScope, $modal);		
		$state.go("@xxx@List");
	}).error(function(data) {
		ineuronApp.confirm("提示","添加失败！", 'sm', $rootScope, $modal);
		console.log("create @xxx@ error");
  		})
  	}

vm.backward = backward;
function backward() {
	$state.go("@xxx@List");
}

}]);


ineuronApp.controller('@Xxx@UpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$modal',
  function($scope, $stateParams, $http, $state, $cookies, $rootScope, $modal) {
	var @xxx@ = eval('(' + $stateParams.@xxx@Str + ')');
	var vm = this;
	
	$scope.existed@Xxx@Name=false;
	$scope.@xxx@Name=@xxx@.name;
	$scope.@xxx@Description=@xxx@.description;
	
	$scope.Check@Xxx@Name=function(){
		$http({
			url : '/product/@xxx@byname?name' + $scope.@xxx@Name,
			method : 'GET'
		}).success(function(data) {
			//validateApiToken(data, $cookies, $rootScope, $modal);
			var a = data.value;
			if(a==null) $scope.existed@Xxx@Name=false; 
			 else $scope.existed@Xxx@Name=true;
		}).error(function(data) {
			ineuronApp.confirm("提示","依据名称调用失败！", 'sm', $rootScope, $modal);
			console.log("error to get @xxx@ ");
		});				
	}


	vm.update@Xxx@ = update@Xxx@;
	function update@Xxx@() {

		$http({
			url : '/product/update@xxx@',
			method : 'POST',
			data : {
				id : @xxx@.id,
				name : $scope.@xxx@Name,
				description : $scope.@xxx@Description
			}
		}).success(function(data) {
			validateApiToken(data, $cookies, $rootScope, $modal);
			ineuronApp.confirm("提示","修改成功！", 'sm', $rootScope, $modal);		
			$state.go("@xxx@List");
		}).error(function(data) {
			ineuronApp.confirm("提示","修改失败！", 'sm', $rootScope, $modal);
			console.log("error");
		})
	}
	
	
	vm.backward = backward;
	function backward() {
		$state.go("@xxx@List");
	}
	
}]);

