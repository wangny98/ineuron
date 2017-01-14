

ineuronApp.controller('ProductManufacturingProcessController', [
		'$http',
		'$stateParams',
		'$scope',
		'$location',
		'$cookies',
		'$state',
		'$rootScope', 
		'$uibModal',
		'DTOptionsBuilder',
		'DTColumnDefBuilder',
		function($http, $stateParams, $scope, $location, $cookies, $state, $rootScope, $uibModal,
				DTOptionsBuilder, DTColumnDefBuilder) {
			var vm = this;

			var productStr = $stateParams.productStr;
			var selectedProduct = eval('(' + productStr + ')');
			$scope.productName = selectedProduct.name;
			var productId = selectedProduct.id;
			var hasFormula = true;
			$http({
				url : '/product/productbyid?id=' + productId,
				method : 'GET'
			}).success(function(data) {
				//alert(JSON.stringify(data));
				updateApiToken(data, $cookies);
				var manufacturingProcesses = data.value.manufacturingProcesses
				if(manufacturingProcesses == undefined){
					manufacturingProcesses = {};
				}
				$scope.model = {
						rows : manufacturingProcesses,	
						selected : {}
					}
				$scope.operations = data.value.operations;
				if(data.value.formula == undefined || data.value.formula.materials == undefined){
					ineuronApp.confirm("提示","该产品尚未设置配方", 'sm', $rootScope, $uibModal);
					hasFormula = false;
					$scope.materials = data.value.formula.allMaterials;
				}else{
					$scope.materials = data.value.formula.materials;
					$scope.materialSettings = data.value.formula.materialSettings;
				}
				
			}).error(function(data, status) {
				//ineuronApp.confirm("提示","获取产品工艺流程信息失败！", 'sm', $rootScope, $uibModal);
				handleError(status, $rootScope, $uibModal);
				console.log("error");
			});

			// gets the template to ng-include for a table row / item
			$scope.getTemplate = function(row) {
				
				setRemainingQuantity();
				if (row === $scope.model.selected){
					if(row.materialQuantity == " " && row.materialId != 0){
						setDefaultValue(row);
					}
					if(isNaN(row.materialQuantity)){
						ineuronApp.confirm("提示","该列只能输入数字！", 'sm', $rootScope, $uibModal);
						row.materialQuantity = 0;
					}
					var operationId = $scope.model.selected.operationId;
					var operationTypeId = getOperationTypeId(operationId);
					if(operationTypeId == 1){
						return 'editMaterial';
					}else{
						return 'editOp';
					}
				}
				else
					return 'display';
			};

			function setRemainingQuantity(){
				
				if(hasFormula){
					for(index in $scope.materialSettings){
						var materialId = $scope.materialSettings[index].materialId;
						var materialQuantity = $scope.materialSettings[index].materialQuantity;
						var quantityInRow = 0;
						for(rowIndex in $scope.model.rows){
							if($scope.model.rows[rowIndex].materialId == materialId 
									&& !isNaN($scope.model.rows[rowIndex].materialQuantity)){
								quantityInRow += parseFloat($scope.model.rows[rowIndex].materialQuantity);
							}
							
						}
						for(rowIndex in $scope.model.rows){
							if($scope.model.rows[rowIndex].materialId == materialId){
								$scope.model.rows[rowIndex].remainingQuantity = (materialQuantity - quantityInRow).toFixed(2);
							}
							
						}
						
					}
				}
				
			}
			function setDefaultValue(row){
				if(hasFormula){
					for(index in $scope.materialSettings){
						var materialId = $scope.materialSettings[index].materialId;
						if(materialId == row.materialId){
							var materialQuantity = $scope.materialSettings[index].materialQuantity;
							var quantityInRow = 0;
							for(rowIndex in $scope.model.rows){
								if($scope.model.rows[rowIndex].materialId == materialId 
										&& !isNaN($scope.model.rows[rowIndex].materialQuantity)){
									quantityInRow += parseFloat($scope.model.rows[rowIndex].materialQuantity);
								}
								
							}
							var remaining = materialQuantity - quantityInRow;
							if(remaining > 0){
								row.materialQuantity = remaining;
							}else{
								row.materialQuantity = 0;
							}
							
						}
					}
				}
			}
			
			$scope.editContact = function(index) {
				$scope.model.selected = $scope.model.rows[index];
				
			};

			$scope.saveContact = function(index) {
				console.log("Saving contact");
				$scope.model.rows[index] = angular.copy($scope.model.selected);
				
				var operationTypeId = getOperationTypeId($scope.model.rows[index].operationId);
				if(operationTypeId != 1){
					$scope.model.rows[index].materialId = 0;
				}
				$scope.reset();
			};

			$scope.reset = function() {
				$scope.model.selected = {};
			};

			var emptyProcess = {
					"stepId" : 0,
					"productId" : productId,
					"orderId" : 0,
					"operationId" : 0,
					"materialId" : 0,
					"materialQuantity" : " "
				};
			
			$scope.addRow = addRow;
			$scope.removeRow = removeRow;
			$scope.saveProcesses = saveProcesses;
			
			function addRow() {
				var newProcess = clone(emptyProcess);

				$scope.model.rows.push(newProcess);
				$scope.model.selected = newProcess;
			};

			function removeRow(index) {
				$scope.model.rows.splice(index, 1);
			};
			
			
			function saveProcesses(){
				var message = validateProcesses($scope.model.rows);
				
				if(message == undefined){
					excuteSaveProcesses();
				}else{
					ineuronApp.confirm("确认", message, 'sm', $rootScope, $uibModal).result.then(function(clickok){  
						if(clickok){
							hasFormula = false;
							excuteSaveProcesses();
						}
					})
				}
			}
					
			
			function excuteSaveProcesses(){
				
				cleanProcesses($scope.model.rows);
				$http({
					url : '/product/saveprocesses?hasformula=' + hasFormula,
					method : 'POST',
					data : $scope.model.rows
				}).success(function(data) {
					updateApiToken(data, $cookies);
					ineuronApp.confirm("提示","保存成功！", 'sm', $rootScope, $uibModal);
					//$state.go("productList");
					
				}).error(function(data, status) {
					//ineuronApp.confirm("提示","保存失败！", 'sm', $rootScope, $uibModal);
					handleError(status, $rootScope, $uibModal);
					console.log("error");
				})
				
			}
			
			function getOperationTypeId(operationId){
				for(index in $scope.operations){
					if(operationId == $scope.operations[index].id){
						return $scope.operations[index].typeId; 
					}
				}
				return 0;
			}
			
			function validateProcesses(rows){
				var message;
				if(hasFormula){
					for(index in $scope.materialSettings){
						var materialId = $scope.materialSettings[index].materialId;
						var materialQuantity = $scope.materialSettings[index].materialQuantity;
						var quantityInRow = 0;
						for(rowIndex in rows){
							if(rows[rowIndex].materialId == materialId){
								quantityInRow += parseFloat(rows[rowIndex].materialQuantity);
							}
							
						}
						if(materialQuantity != quantityInRow){
							var materialName = getMaterialName(materialId);
							message = "原材料  " + materialName + " 的质量分數（" +quantityInRow + 
									"）与配方中的（" + materialQuantity + "）不一致 , " +
									"如果继续保存，系统将会为该流程创建新的配方。";
							
							return message;
						}
					}
				}
				return message;
			}
			
			function getMaterialName(materialId){
				for(index in $scope.materials){
					if(materialId == $scope.materials[index].id){
						return $scope.materials[index].name;
					}
				}
				return materialId;
			}
			function cleanProcesses(rows){
				var processes = [];
				for(var index in rows){
					var operationTypeId = getOperationTypeId(rows[index].operationId);
					if(operationTypeId != 1){
						rows[index].materialId = 0;
						
					}
					rows[index].remainingQuantity = undefined;
				}
			}
			
			$scope.backward = backward;		
			function backward(){
				$state.go("allProductList");
			}

		} 
	]);

