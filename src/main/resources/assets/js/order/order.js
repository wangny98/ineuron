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
			url : '/order/nlpsearchfororder?words=' + $scope.searchObj.productSearchText,
			method : 'GET'
		}).success(function(data) {
			updateApiToken(data, $cookies);
			vm.products = data.value;
		}).error(function(data, status) {
			// ineuronApp.confirm("提示","查询产品失败！", 'sm', $rootScope, $uibModal);
			handleError(status, $rootScope, $uibModal);
			console.log("error to get productbyname ");
		});			
	}
	
	
	// vm.searchProducts=searchProducts;
	// function searchProducts(){
	$scope.searchProducts=function(){
	// $scope.searchSubmitted=true;
		// alert("$scope.productSearchText:
		// "+$scope.searchObj.productSearchText);
		$cookies.put('INeuron-ProductSearchText', $scope.searchObj.productSearchText, {path : "/"});

		$http({
			url : '/order/nlpsearchfororder?words=' + $scope.searchObj.productSearchText,
			method : 'GET'
		}).success(function(data) {
			updateApiToken(data, $cookies);
			vm.products = data.value;
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
			for (var i in vm.allProducts){
				// alert(vm.allProducts[i].order);
				// vm.allProducts[i].order.deliveryDate=new Date();
			}
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


ineuronApp.controller('OrderListController', ['$http', '$scope', '$stateParams', '$rootScope', '$uibModal', '$location', '$cookies', '$state', 
                                              'DTOptionsBuilder', 'DTColumnDefBuilder', 'DTColumnBuilder',
	function($http, $scope, $stateParams, $rootScope, $uibModal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder, DTColumnBuilder) {
	var vm = this;
	$scope.format = "yyyy/MM/dd";
	
	
	/*
	 * vm.orderingOptions = [ { name:"orderDate", label: "下单时间", ticked: true }, {
	 * name:"deliveryDate", label: "交付时间", ticked: false }, { name:"total",
	 * label: "订单金额", ticked: false }, { name:"amount", label: "数量", ticked:
	 * false } ];
	 */	
	var selectedOrderingOption="orderDate";
	
	// init the ordering option
   /*
	 * for (o in vm.orderingOptions){ if(vm.orderingOptions[o].ticked==true)
	 * selectedOrderingOption=vm.orderingOptions[o].name; }
	 */	
    
	// init for list paging
	$scope.paginationConf = {
            currentPage: 1,
            itemsPerPage: 10,
            // totalItems: 100,
            perPageOptions: [10,15]
        };
	
	$http({
		url : '/order/listbypage',
		method : 'POST',
		data : {
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage,
            orderingOption: selectedOrderingOption
		}
	}).success(function(data) {
		updateApiToken(data, $cookies);
		$scope.paginationConf.totalItems = data.value.totalRecords;
		vm.orders = data.value.orders;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("order list error");
	});	  
		
	
	var retreiveOrdersForPagingButton = function(){
		$http({
			url : '/order/listbypage',
			method : 'POST',
			data : {
	            currentPage: $scope.paginationConf.currentPage,
	            itemsPerPage: $scope.paginationConf.itemsPerPage,
	            orderingOption: selectedOrderingOption
			}
		}).success(function(data) {
			updateApiToken(data, $cookies);
			$scope.paginationConf.totalItems = data.value.totalRecords;
			vm.orders = data.value.orders;
		}).error(function(data, status) {
			handleError(status, $rootScope, $uibModal);
			console.log("order list error");
		});	  
    };

    // monitoring the change of currentPage and itemsPerPage
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', retreiveOrdersForPagingButton);

    
    // refresh order list after select ordering option
    $scope.refreshOrdersByOrderingOptionChange = function(){
    	$scope.paginationConf.currentPage=1;
    	selectedOrderingOption=$scope.outputOrderingOption[0].name;
    	$http({
			url : '/order/listbypage',
			method : 'POST',
			data : {
	            currentPage: $scope.paginationConf.currentPage,
	            itemsPerPage: $scope.paginationConf.itemsPerPage,
	            orderingOption: selectedOrderingOption
			}
		}).success(function(data) {
			updateApiToken(data, $cookies);
			$scope.paginationConf.totalItems = data.value.totalRecords;
			vm.orders = data.value.orders;
		}).error(function(data, status) {
			handleError(status, $rootScope, $uibModal);
			console.log("order list error");
		});	  
    }
    
    
    /*
	 * for order list filtering
	 */    
    /*
	 * $http({ url : '/product/productlist', method : 'GET'
	 * }).success(function(data) { updateApiToken(data, $cookies); vm.products =
	 * data.value; }).error(function(data, status) { handleError(status,
	 * $rootScope, $uibModal); console.log("product list error"); }); // refresh
	 * order list after selecting filters
	 * $scope.refreshOrdersAfterFiltering=function(){ var productIdList=new
	 * Array(); var i=0; for(var p in $scope.selectedProducts){
	 * productIdList[i]=$scope.selectedProducts[p].id;
	 * //alert("$scope.selectedProducts[p].id; "+p.id+" productIdList[i]:
	 * "+productIdList[i]); i++; } //alert("productIdList "+productIdList);
	 * $http({ url : '/order/listbyproducts', method : 'GET'
	 * }).success(function(data) { updateApiToken(data, $cookies) vm.orders =
	 * data.value; // alert("orders: "+vm.orders[0].orderNumber);
	 * }).error(function(data, status) { handleError(status, $rootScope,
	 * $uibModal); console.log("order list error"); }); }
	 */
    
    
	vm.updateOrder=updateOrder;
	function updateOrder(index){
		$state.go("updateOrder", {orderStr: JSON.stringify(vm.orders[index])});
	}
	
	// for reports
  $scope.showReport=function(){
	  $scope.options = {
	            chart: {
	                type: 'lineChart',
	                height: 450,
	                width:900,
	                margin : {
	                    top: 20,
	                    right: 20,
	                    bottom: 40,
	                    left: 55
	                },
	                x: function(d){ return d[0]; },
	                y: function(d){ return d[1]; },
	                useInteractiveGuideline: true,
	                color: d3.scale.category10().range(),
	                
	                xAxis: {
	                    axisLabel: '月份'
	                },
	                yAxis: {
	                    axisLabel: '订单量  ',
	                    tickFormat: function(d){
	                        return d3.format('.02f')(d);
	                    },
	                    axisLabelDistance: -10
	                },
	                callback: function(chart){
	                    console.log("!!! lineChart callback !!!");
	                }
	            },
	            title: {
	                enable: true,
	                text: '各个产品2017年月销量统计'
	            },
	            subtitle: {
	                enable: true,
	                text: '',
	                css: {
	                    'text-align': 'center',
	                    'margin': '10px 13px 0px 7px'
	                }
	            },
	            caption: {
	                /*
					 * enable: true, html: '<b>Figure 1.</b> xxx, <span
					 * style="text-decoration: underline;">xx</span> <i>xxx</i> ',
					 * css: { 'text-align': 'justify', 'margin': '10px 13px 0px
					 * 7px' }
					 */
	            }
	        };

		$http({
			url : '/order/ordersbyproductspermonth',
			method : 'GET'
		}).success(function(data) {
			updateApiToken(data, $cookies);
			$scope.data=data.value;
			// alert("total: "+data.value.totalRecords);
			// alert("orders: "+vm.orders[0].orderNumber);
		}).error(function(data, status) {
			handleError(status, $rootScope, $uibModal);
			console.log("order list error");
		});	  
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


ineuronApp.controller('OrderCreateController', ['$scope', '$stateParams', '$http', '$state', '$cookies', 
                                                '$rootScope', '$uibModal', 'Upload', '$timeout','$location',
   function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal, Upload, $timeout,$location) {

	var vm = this;	
	var product = eval('(' + $stateParams.productStr + ')');
	$scope.userName=$cookies.get('INeuron-UserName');
    $scope.productName=product.name;
    
    if(product.productPrice==null){
    	ineuronApp.confirm("提示","产品还未定价，请先给此产品设定价格！", 'sm', $rootScope, $uibModal);		
		$state.go("updateProductPrice",{productStr: $stateParams.productStr});
    }
    else{ 
    	$scope.price=product.productPrice.price;
    	$scope.unit=product.productPrice.unit;
    }
    
    if(product.order!=null){
        $scope.amount=product.order.amount;	
        $scope.productCharge=$scope.amount*$scope.price;
    }
   
    // get product package type list
    var j=0;
    $http({
		url : '/product/productpackagetypelist',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.productPackageTypes = data.value;
		vm.productPackageTypes[0].ticked=true;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("get product package type list error");
	});	
    
    // get label product package type 
    $http({
		url : '/product/labelproductpackagetype',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.labelProductPackageType= data.value;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("get product package type list error");
	});	
    
    $scope.calculatePackageAmount=function(){
    	$scope.packageAmount=Math.ceil($scope.amount/$scope.selectedProductPackageType[0].volume);
    }
    
    // get product's ProductionPeriod
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
    
    if(product.order!=null)
    	$scope.deliveryDate=product.order.deliveryDate;
    else $scope.deliveryDate = new Date();
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
		
	    // calculate the package amount
	    $scope.packageAmount=Math.ceil($scope.amount/$scope.selectedProductPackageType[0].volume);
	    
	    // calculate the total charge including product, package and
		// personalized label package
	    $scope.productCharge=parseFloat($scope.amount*$scope.price).toFixed(2);
	    $scope.packageCharge=parseFloat($scope.packageAmount*$scope.selectedProductPackageType[0].price).toFixed(2);
	    $scope.labelPackageCharge=vm.labelProductPackageType.price;
	    var total=parseFloat($scope.amount*$scope.price+
	                       $scope.packageAmount*$scope.selectedProductPackageType[0].price+
	    				   vm.labelProductPackageType.price).toFixed(2);	
	    $scope.totalCharge=parseFloat(total);
	    
	    // get production period
	    for (var i in vm.productPeriods){
			if(($scope.amount>=vm.productPeriods[i].productionMinVolume)&&($scope.amount<=vm.productPeriods[i].productionMaxVolume)) {
				// return value by unit as "day"
				$scope.productionPeriod=Math.ceil(vm.productPeriods[i].productionPeriod/3600/8);				
				break;
			}
		}   
	}	
	

    vm.createOrder = createOrder;
    function createOrder(file) {
      // generate pic file name
      var now=new Date();	
      var userId=$cookies.get('INeuron-UserId');
      var picFileName = userId + "-" + now.getTime();
      var picSuffix;
      
      if(file!=null){
    	  var tempFilename=file.name;
    	  var tempFilenameSections=tempFilename.split('.');
    	  var picSuffix=tempFilenameSections[tempFilenameSections.length-1];
      }
      
      $http({
		url : '/order/create',
		method : 'POST',
		data : {
			productId : product.id,
			userId: userId,
			customer: $scope.customerName,
			amount: $scope.amount,
			productCharge: $scope.amount*product.productPrice.price,
			productCost: $scope.amount*product.productPrice.cost,
			packageCharge: $scope.packageAmount*$scope.selectedProductPackageType[0].price,
			packageCost: $scope.packageAmount*$scope.selectedProductPackageType[0].cost,
			labelPackageCharge:vm.labelProductPackageType.price,
			labelPackageCost:vm.labelProductPackageType.cost,
			totalCharge: $scope.totalCharge,
			packageAmount:$scope.packageAmount,
			productPackageTypeId:$scope.selectedProductPackageType[0].id,
			productionPeriod:$scope.productionPeriod,
			payment:$scope.payment,
			deliveryDate: $scope.deliveryDate,
			customizedInfo: $scope.customizedInfo,
			picFile: picFileName+"."+picSuffix
	 	}
	 }).success(function(data) {
		updateApiToken(data, $cookies);
		
		// upload pic to the file server
		if(file!=null){
			file.upload = Upload.upload({
				url: '/file/upload',
				data: {file:  Upload.rename(file, picFileName+"."+picSuffix)},
			});
			file.upload.then(function (response) {
				$timeout(function () {
					file.result = response.data;
				});
			}, function (response) {
				if (response.status > 0)
					// $scope.errorMsg = response.status + ': ' + response.data;
					ineuronApp.confirm("提示","上传图片失败！", 'sm', $rootScope, $uibModal);
			}, function (evt) {
				// Math.min is to fix IE which reports 200% sometimes
				file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
			});
		}
		
		ineuronApp.confirm("提示","订单添加成功！", 'sm', $rootScope, $uibModal);		
		$state.go("orderList");
    }).error(function(data, status) {
		ineuronApp.confirm("提示","添加失败！", 'sm', $rootScope, $uibModal);
		// alert(status);
		handleError(status, $rootScope, $uibModal);
		console.log("create order error");
  	})  	    
  	}

    vm.backward = backward;
    function backward() {
    	$state.go("searchForOrder");
    }

}]);


ineuronApp.controller('OrderUpdateController', ['$scope', '$stateParams', '$http', '$state', '$cookies',
                                                '$rootScope', '$uibModal', '$location','Upload',
  function($scope, $stateParams, $http, $state, $cookies, $rootScope, $uibModal,$location,Upload) {
	var order = eval('(' + $stateParams.orderStr + ')');
	var vm = this;
	
	$scope.orderNumber=order.orderNumber;
	$scope.customer=order.customer;
	$scope.amount=order.amount;
	$scope.totalCharge=order.totalCharge;
	$scope.productCharge=order.productCharge;
	$scope.packageCharge=order.packageCharge;
	$scope.labelPackageCharge=order.labelPackageCharge;
	$scope.payment=order.payment;
	$scope.customizedInfo=order.customizedInfo;
	$scope.price=order.product.productPrice.price;
	$scope.unit=order.product.productPrice.unit;
	$scope.packageAmount=order.packageAmount;
	
	// get product package type list
    $http({
		url : '/product/productpackagetypelist',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.productPackageTypes = data.value;
		
		// set the init order package type
		for(var i in vm.productPackageTypes){
			if(vm.productPackageTypes[i].id==order.productPackageTypeId){
				vm.productPackageTypes[i].ticked=true;
				break;
			}
		}
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("get product package type list error");
	});	
	
    // get label product package type 
    $http({
		url : '/product/labelproductpackagetype',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.labelProductPackageType= data.value;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("get product package type list error");
	});	
    
    $scope.calculatePackageAmount=function(){
    	$scope.packageAmount=Math.ceil($scope.amount/$scope.selectedProductPackageType[0].volume);
    }
    
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
	 
    // get orginal pic from nginx
    // alert($location.host());
    $scope.originalPic="http://"+$location.host()+"/images/"+order.picFile;
    
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
		// calculate the package amount
	    $scope.packageAmount=Math.ceil($scope.amount/$scope.selectedProductPackageType[0].volume);
	    
	    // calculate the total charge including product, package and
		// personalized label package
	    $scope.productCharge=parseFloat($scope.amount*$scope.price).toFixed(2);
	    $scope.packageCharge=parseFloat($scope.packageAmount*$scope.selectedProductPackageType[0].price).toFixed(2);
	    $scope.labelPackageCharge=vm.labelProductPackageType.price;
	    var total=parseFloat($scope.amount*$scope.price+
	                       $scope.packageAmount*$scope.selectedProductPackageType[0].price+
	    				   vm.labelProductPackageType.price).toFixed(2);	
	    $scope.totalCharge=parseFloat(total);
	}
	
	vm.updateOrder = updateOrder;
	function updateOrder(file) {
		// generate pic file name
	      var userId=$cookies.get('INeuron-UserId');
	      var newPicFile;
	      
	      if(file==null){
	    	  newPicFile=order.picFile;
	      }
	      else{
	    	  var currentTime=new Date();
	    	  var picFileName = userId + "-" + currentTime.getTime();
	    	  var tempFilename=file.name;
	    	  var tempFilenameSections=tempFilename.split('.');
	    	  var picSuffix=tempFilenameSections[tempFilenameSections.length-1];
	    	  newPicFile=picFileName+"."+picSuffix;
	      }
	      
		$http({
			url : '/order/update',
			method : 'POST',
			data : {
				id:order.id,
				customer: $scope.customer,
				amount: $scope.amount,
				productCharge: $scope.amount*order.product.productPrice.price,
				productCost: $scope.amount*order.product.productPrice.cost,
				packageCharge: $scope.packageAmount*$scope.selectedProductPackageType[0].price,
				packageCost: $scope.packageAmount*$scope.selectedProductPackageType[0].cost,
				labelPackageCharge:vm.labelProductPackageType.price,
				labelPackageCost:vm.labelProductPackageType.cost,
				totalCharge: $scope.totalCharge,
				packageAmount:$scope.packageAmount,
				productPackageTypeId:$scope.selectedProductPackageType[0].id,
				payment:$scope.payment,				
				statusId: $scope.selectedOrderStatus[0].id,
				deliveryDate: $scope.deliveryDate,
				customizedInfo: $scope.customizedInfo,
				picFile: newPicFile
			}
		}).success(function(data) {
			updateApiToken(data, $cookies);
			
			// upload pic to the file server
			if(file!=null){
				file.upload = Upload.upload({
					url: '/file/upload',
					data: {file:  Upload.rename(file, picFileName+"."+picSuffix)},
				});
				file.upload.then(function (response) {
					$timeout(function () {
						file.result = response.data;
					});
				}, function (response) {
					if (response.status > 0)
						// $scope.errorMsg = response.status + ': ' +
						// response.data;
						ineuronApp.confirm("提示","上传图片失败！", 'sm', $rootScope, $uibModal);
				}, function (evt) {
					// Math.min is to fix IE which reports 200% sometimes
					file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
				});
			}
			
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

