ineuronApp.controller('SearchForOrderController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$uibModal', 'DTOptionsBuilder', 'DTColumnDefBuilder',
   function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal, DTOptionsBuilder, DTColumnDefBuilder) {

	var vm = this;
	// $scope.searchSubmitted=false;
	// $scope.notFoundProducts=false;
	// $scope.isCollapsed = true;

	$scope.searchObj = {
			productSearchText:''
	};
	
	if ($.fn.dataTable.isDataTable('ng') ) {
	    table = $('ng').DataTable();
	}
	else {
		vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers').withOption('responsive', true);
		vm.dtColumnDefs = [ 
		                    DTColumnDefBuilder.newColumnDef(0),
		                    DTColumnDefBuilder.newColumnDef(1),
		                    DTColumnDefBuilder.newColumnDef(2),
		                    DTColumnDefBuilder.newColumnDef(3),
		                    DTColumnDefBuilder.newColumnDef(4),
		                    DTColumnDefBuilder.newColumnDef(5).withClass('none'),
		                    DTColumnDefBuilder.newColumnDef(6).withClass('none'),
		                    DTColumnDefBuilder.newColumnDef(7).withClass('none'),
		                    DTColumnDefBuilder.newColumnDef(8).withClass('none'),
		                    DTColumnDefBuilder.newColumnDef(9)];
	};
	
	
	// reload the searched result when back from order create
	$scope.searchObj.productSearchText=$cookies.get('INeuron-ProductSearchText');
	if($scope.searchObj.productSearchText!=null){
		$http({
			url : '/product/productsbynlpwords?words=' + $scope.searchObj.productSearchText,
			method : 'GET'
		}).success(function(data) {
			updateApiToken(data, $cookies);
			vm.products = data.value;
			/*
			 * if(vm.products==null){ $scope.notFoundProducts=true; }
			 */
		}).error(function(data, status) {
			// ineuronApp.confirm("提示","查询产品失败！", 'sm', $rootScope, $uibModal);
			handleError(status, $rootScope, $uibModal);
			console.log("error to get productbyname ");
		});			
	}
	
	
	//vm.searchProducts=searchProducts;
	//function searchProducts(){
	$scope.searchProducts=function(){
	// $scope.searchSubmitted=true;
		//alert("$scope.productSearchText: "+$scope.searchObj.productSearchText);
		$cookies.put('INeuron-ProductSearchText', $scope.searchObj.productSearchText, {path : "/"});

		$http({
			url : '/product/productsbynlpwords?words=' + $scope.searchObj.productSearchText,
			method : 'GET'
		}).success(function(data) {
			updateApiToken(data, $cookies);
			vm.products = data.value;
			/*
			 * if(vm.products==null){ $scope.notFoundProducts=true; }
			 */
		}).error(function(data, status) {
			// ineuronApp.confirm("提示","查询产品失败！", 'sm', $rootScope, $uibModal);
			handleError(status, $rootScope, $uibModal);
			console.log("error to get productbyname ");
		});			
	}
    
	$scope.showAllProducts=function(){
		$http({
			url : '/product/productlist',
			method : 'GET'
		}).success(function(data) {
			updateApiToken(data, $cookies);
			vm.allProducts = data.value;
			//alert(vm.products[0].productPrice.price);
		}).error(function(data, status) {
			handleError(status, $rootScope, $uibModal);
			console.log("error in get all products");
		});
	}


	vm.createOrder = createOrder;
	function createOrder(index) {
		$state.go("createOrder",{productStr: JSON.stringify(vm.products[index])});
	}
	
	vm.createOrderForAllProducts = createOrderForAllProducts;
	function createOrderForAllProducts(index) {
		$state.go("createOrder",{productStr: JSON.stringify(vm.allProducts[index])});
	}
	

	vm.backward = backward;
	function backward() {
		$state.go("orderList");
	}

}]);


ineuronApp.controller('OrderListController', ['$http', '$scope', '$stateParams', '$rootScope', '$uibModal', '$location', '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
	function($http, $scope, $stateParams, $rootScope, $uibModal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	$scope.format = "yyyy/MM/dd";
	
/*	
		vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers');
		vm.dtColumnDefs = [ 
		                    DTColumnDefBuilder.newColumnDef(0),
		                    DTColumnDefBuilder.newColumnDef(1),
		                    DTColumnDefBuilder.newColumnDef(2),
		                    DTColumnDefBuilder.newColumnDef(3),
		                    DTColumnDefBuilder.newColumnDef(4),
		                    DTColumnDefBuilder.newColumnDef(5),
		                    DTColumnDefBuilder.newColumnDef(6),
		                    DTColumnDefBuilder.newColumnDef(7),
		                    DTColumnDefBuilder.newColumnDef(8),
	*/
	
	$http({
		url : '/order/list',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.orders = data.value;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("order list error");
	});	
		
	vm.updateOrder=updateOrder;
	function updateOrder(index){
		$state.go("updateOrder", {orderStr: JSON.stringify(vm.orders[index])});
	}
	
	/*
	 * vm.createOrder=createOrder; function createOrder(){ // alert("index:
	 * "+index); $state.go("createOrder"); }
	 */
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
					// ineuronApp.confirm("提示","删除失败！", 'sm', $rootScope,
					// $uibModal);
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
	var product = eval('(' + $stateParams.productStr + ')');
	$scope.userName=$cookies.get('INeuron-UserName');
    $scope.productName=product.name;
    if(product.productPrice==null){
    	ineuronApp.confirm("提示","产品还未定价，请先给此产品设定价格！", 'sm', $rootScope, $uibModal);		
		$state.go("updateProductPrice",{productStr: $stateParams.productStr});
    }
    else $scope.price=product.productPrice.price;
    
    //get product's ProductionPeriod
    $http({
		url : '/production/periodsbyproductid?productId='+product.id,
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.productPeriods = data.value;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("get product's production periods error");
	});	
    
    
	$scope.deliveryDate = new Date();
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
	            
	$scope.calculateTotalAndPeriod=function(){ 
		
		//calculate the total price
	    $scope.total=$scope.amount*$scope.price;
	    
	    //get production period
	    for (var i in vm.productPeriods){
			if(($scope.amount>=vm.productPeriods[i].productionMinVolume)&&($scope.amount<=vm.productPeriods[i].productionMaxVolume)) {
				//return value by unit as "day"
				$scope.productionPeriod=Math.ceil(vm.productPeriods[i].productionPeriod/3600/8);				
				break;
			}
		}   
	}

    vm.createOrder = createOrder;
    function createOrder() {

	var userId=	$cookies.get('INeuron-UserId');
	// var deliveryDateStr=$scope.deliveryDate.toFomatorString("YYYY-MM-DD");
	
	$http({
		url : '/order/create',
		method : 'POST',
		data : {
			productId : product.id,
			userId: userId,
			customer: $scope.customerName,
			amount: $scope.amount,
			total: $scope.total,
			productionPeriod:$scope.productionPeriod,
			payment:$scope.payment,
			deliveryDate: $scope.deliveryDate,
			customizedInfo: $scope.customizedInfo
		}
	}).success(function(data) {
		updateApiToken(data, $cookies);
		ineuronApp.confirm("提示","添加成功！", 'sm', $rootScope, $uibModal);		
		$state.go("orderList");
	}).error(function(data, status) {
		// ineuronApp.confirm("提示","添加失败！", 'sm', $rootScope, $uibModal);
		alert(status);
		handleError(status, $rootScope, $uibModal);
		console.log("create order error");
  		})
  	}

vm.backward = backward;
function backward() {
	$state.go("searchForOrder");
}

}]);


ineuronApp.controller('OrderUpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', '$rootScope', '$uibModal',
  function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal) {
	var order = eval('(' + $stateParams.orderStr + ')');
	var vm = this;
	
	$scope.orderNumber=order.orderNumber;
	$scope.customer=order.customer;
	$scope.amount=order.amount;
	$scope.total=order.total;
	$scope.payment=order.payment;
	$scope.customizedInfo=order.customizedInfo;
	$scope.price=order.product.productPrice.price;
	
    // alert("deliveryDate: "+order.deliveryDate);
	$scope.deliveryDate=order.deliveryDate;
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
	
	// get orderStatus list
	$http({
		url : '/order/orderstatuslist',
		method : 'GET'
	}).success(function(data) {
		// updateApiToken(data, $cookies);
		vm.orderStatus = data.value;
		for (var i in vm.orderStatus){
			if(vm.orderStatus[i].name==order.orderStatus.name) {
				vm.orderStatus[i].ticked=true;
				break;
			}
		}
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("error to get orderstatus ");
	});		
	
	
	$scope.calculateTotal=function(){ 
	    $scope.total=$scope.amount*$scope.price;
	}
	
	vm.updateOrder = updateOrder;
	function updateOrder() {
		
		$http({
			url : '/order/update',
			method : 'POST',
			data : {
				id:order.id,
				customer: $scope.customer,
				amount: $scope.amount,
				total: $scope.total,
				payment:$scope.payment,
				statusId: $scope.selectedOrderStatus[0].id,
				deliveryDate: $scope.deliveryDate,
				customizedInfo: $scope.customizedInfo
			}
		}).success(function(data) {
			updateApiToken(data, $cookies);
			ineuronApp.confirm("提示","修改成功！", 'sm', $rootScope, $uibModal);		
			$state.go("orderList");
		}).error(function(data, status) {
			// ineuronApp.confirm("提示","修改失败！", 'sm', $rootScope, $uibModal);
			handleError(status, $rootScope, $uibModal);
			console.log("error");
		})
	}
	
	
	vm.backward = backward;
	function backward() {
		$state.go("orderList");
	}
	
}]);

