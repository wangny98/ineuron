ineuronApp.controller('SearchForOrderController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$uibModal',
   function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal) {

	var vm = this;
	$scope.searchSubmitted=false;	
	$scope.notFoundProducts=false;	

	vm.searchProducts=searchProducts;
	function searchProducts(){
	$scope.searchSubmitted=true;
	$http({
		url : '/product/productsbynlpwords?words=' + $scope.productSearchText,
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.products = data.value;
		if(vm.products==null){
			$scope.notFoundProducts=true;
		}	
	}).error(function(data, status) {
		//ineuronApp.confirm("提示","查询产品失败！", 'sm', $rootScope, $uibModal);
		handleError(status, $rootScope, $uibModal);
		console.log("error to get productbyname ");
	});			
}


vm.createOrder = createOrder;
function createOrder() {
	$state.go("createOrder");
  	}

vm.backward = backward;
function backward() {
	$state.go("orderList");
}

}]);


ineuronApp.controller('OrderListController', ['$http', '$scope', '$stateParams', '$rootScope', '$uibModal', '$location', '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
	function($http, $scope, $stateParams, $rootScope, $uibModal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	
	$http({
		url : '/product/orderlist',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.orders = data.value;
	}).error(function(data, status) {
		//ineuronApp.confirm("提示","获取列表失败！", 'sm', $rootScope, $uibModal);
		handleError(status, $rootScope, $uibModal);
		console.log("order list error");
	});

	vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
			'full_numbers');
	/*
	 * vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
	 * DTColumnDefBuilder.newColumnDef(1).notSortable() ];
	 */
	
	vm.updateOrder=updateOrder;
	function updateOrder(index){
		$state.go("updateOrder", {orderStr: JSON.stringify(vm.orders[index])});
	}
	
	vm.createOrder=createOrder;
	function createOrder(){
		// alert("index: "+index);
		$state.go("createAndUpdateOrder");
	}
	
	vm.deleteOrder=deleteOrder;
	function deleteOrder(index){
		ineuronApp.confirm("确认","确定删除吗？", 'sm', $rootScope, $uibModal).result.then(function(clickok){  
			if(clickok){
				 $http({
					url : '/product/deleteorder',
					method : 'POST',
					data : {
						name : vm.orders[index].name
					}
				}).success(function(data) {
					ineuronApp.confirm("提示","删除成功！", 'sm', $rootScope, $uibModal);
					updateApiToken(data, $cookies);
					$state.go("orderList", null, {reload:true});
				}).error(function(data, status) {
					//ineuronApp.confirm("提示","删除失败！", 'sm', $rootScope, $uibModal);
					handleError(status, $rootScope, $uibModal);
					console.log("error");
				})
			}
		});		
	}
		
}]);


ineuronApp.controller('OrderCreateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$uibModal',
   function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal) {

	var vm = this;
	$scope.existedOrderName=false;		


	 $scope.dat = new Date();
	            $scope.format = "yyyy/MM/dd";
	            $scope.altInputFormats = ['yyyy/M!/d!'];

	            $scope.popup1 = {
	                opened: false
	            };
	            $scope.open1 = function () {
	                $scope.popup1.opened = true;
	            };
	            $scope.dateOptions = {
	                    showWeeks: false
	            };
	            
$scope.CheckOrderName=function(){
	
	$http({
		url : '/product/orderbyname?name' + $scope.orderName,
		method : 'GET'
	}).success(function(data) {
		// updateApiToken(data, $cookies);
		var a = data.value;
		if(a==null) $scope.existedOrderName=false; 
		 else $scope.existedOrderName=true;
	}).error(function(data, status) {
		//ineuronApp.confirm("提示","依据名称调用失败！", 'sm', $rootScope, $uibModal);
		handleError(status, $rootScope, $uibModal);
		console.log("error to get order ");
	});				
}


vm.createOrder = createOrder;
function createOrder() {
	
	$http({
		url : '/product/createorder',
		method : 'POST',
		data : {
			name : $scope.orderName,
			description : $scope.orderDescription
		}
	}).success(function(data) {
		updateApiToken(data, $cookies);
		ineuronApp.confirm("提示","添加成功！", 'sm', $rootScope, $uibModal);		
		$state.go("orderList");
	}).error(function(data, status) {
		//ineuronApp.confirm("提示","添加失败！", 'sm', $rootScope, $uibModal);
		handleError(status, $rootScope, $uibModal);
		console.log("create order error");
  		})
  	}

vm.backward = backward;
function backward() {
	$state.go("orderList");
}

}]);


ineuronApp.controller('OrderUpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$uibModal',
  function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal) {
	var order = eval('(' + $stateParams.orderStr + ')');
	var vm = this;
	
	$scope.existedOrderName=false;
	$scope.orderName=order.name;
	$scope.orderDescription=order.description;
	
	$scope.CheckOrderName=function(){
		$http({
			url : '/product/orderbyname?name' + $scope.orderName,
			method : 'GET'
		}).success(function(data) {
			// updateApiToken(data, $cookies);
			var a = data.value;
			if(a==null) $scope.existedOrderName=false; 
			 else $scope.existedOrderName=true;
		}).error(function(data, status) {
			//ineuronApp.confirm("提示","依据名称调用失败！", 'sm', $rootScope, $uibModal);
			handleError(status, $rootScope, $uibModal);
			console.log("error to get order ");
		});				
	}


	vm.updateOrder = updateOrder;
	function updateOrder() {

		$http({
			url : '/product/updateorder',
			method : 'POST',
			data : {
				id : order.id,
				name : $scope.orderName,
				description : $scope.orderDescription
			}
		}).success(function(data) {
			updateApiToken(data, $cookies);
			ineuronApp.confirm("提示","修改成功！", 'sm', $rootScope, $uibModal);		
			$state.go("orderList");
		}).error(function(data, status) {
			//ineuronApp.confirm("提示","修改失败！", 'sm', $rootScope, $uibModal);
			handleError(status, $rootScope, $uibModal);
			console.log("error");
		})
	}
	
	
	vm.backward = backward;
	function backward() {
		$state.go("orderList");
	}
	
}]);

