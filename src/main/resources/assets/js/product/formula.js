
ineuronApp.controller('FormulaListController', ['$http', '$scope', '$rootScope', '$uibModal', '$location', '$cookies', '$state', 'DTOptionsBuilder', 'DTColumnDefBuilder',
	function($http, $scope, $rootScope, $uibModal, $location, $cookies, $state, DTOptionsBuilder, DTColumnDefBuilder) {
	var vm = this;
	
	$http({
		url : '/product/formulas',
		method : 'GET'
	}).success(function(data) {
		updateApiToken(data, $cookies);
		vm.formulas = data.value;
	}).error(function(data, status) {
		handleError(status, $rootScope, $uibModal);
		console.log("error");
	});

	vm.dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
			'full_numbers');
	vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
			DTColumnDefBuilder.newColumnDef(1),
			DTColumnDefBuilder.newColumnDef(2).notSortable() ];
	
	vm.updateFormula=updateFormula;
	vm.createFormula=createFormula;
	vm.deleteFormula=deleteFormula;
	function updateFormula(index){
		$state.go("updateFormula", {formulaStr: JSON.stringify(vm.formulas[index])});
	}
	function createFormula(){
		$state.go("createFormula");
	}
	function deleteFormula(index){
		ineuronApp.confirm("确认","确定删除吗？", 'sm', $rootScope, $uibModal).result.then(function(clickok){  
			if(clickok){
				$http({
					url : '/product/deleteformula?id=' + vm.formulas[index].id,
					method : 'POST'
				}).success(function(data) {
					ineuronApp.confirm("提示","删除成功！", 'sm', $rootScope, $uibModal);
					$state.go("formulaList", null, {reload:true});
				}).error(function(data, status) {
					handleError(status, $rootScope, $uibModal);
					console.log("error");
				});
			}
		})
	}
}]);

ineuronApp.controller('UpdateFormulaController', [
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
		$scope.formula = {};
		
		var formulaStr = $stateParams.formulaStr;
		var selectedFormula = eval('(' + formulaStr + ')');
		$scope.formula.id = selectedFormula.id;
		$scope.formula.name = selectedFormula.name;
		$scope.formula.description = selectedFormula.description;
		var formulaId = selectedFormula.id;
		$http({
			url : '/product/formulabyid?id=' + formulaId,
			method : 'GET'
		}).success(function(data) {
			//alert(JSON.stringify(data));
			updateApiToken(data, $cookies);
			$scope.formula.materialSettings = data.value.materialSettings;
			if($scope.formula.materialSettings == null){
				$scope.formula.materialSettings = [];
			}
			$scope.materials = data.value.allMaterials;
			$scope.formula.selected = {};
		}).error(function(data, status) {
			handleError(status, $rootScope, $uibModal);
			console.log("error");
		});

		// gets the template to ng-include for a table row / item
		$scope.getTemplate = function(row) {
			if (row === $scope.formula.selected)
				return 'edit';
			else
				return 'display';
		};

		$scope.editContact = function(index) {
			$scope.formula.selected = $scope.formula.materialSettings[index];
		};

		$scope.saveContact = function(index) {
			$scope.formula.materialSettings[index] = angular.copy($scope.formula.selected);
			$scope.reset();
		};

		$scope.reset = function() {
			$scope.formula.selected = {};
		};

		var emptyFormulaMaterial = {
				"id" : 0,
				"formulaId" :  formulaId,
				"materialId" : 0,
				"materialQuantity" : 0,
				"materialPercent" : 0
			};
		
		$scope.addRow = addRow;
		$scope.removeRow = removeRow;
		$scope.updateFormula = updateFormula;
		$scope.resetMaterialPercent = resetMaterialPercent;
		
		function addRow() {
			var newMaterial = clone(emptyFormulaMaterial);

			$scope.formula.materialSettings.push(newMaterial);
			$scope.formula.selected = newMaterial;
		};

		function removeRow(index) {
			$scope.formula.materialSettings.splice(index, 1);
			resetMaterialPercent();
		};
		
		function updateFormula(){
			var formula = getFormula();
			var isValid = validateFormula(formula);
			if(!isValid){
				return;
			}
			$http({
				url : '/product/updateformula',
				method : 'POST',
				data : formula
			}).success(function(data) {
				updateApiToken(data, $cookies);
				ineuronApp.confirm("提示","保存成功！", 'sm', $rootScope, $uibModal);
				$state.go("formulaList");
			}).error(function(data, status) {
				//ineuronApp.confirm("提示","保存失败！", 'sm', $rootScope, $uibModal);
				handleError(status, $rootScope, $uibModal);
				console.log("error");
			})
		}
		
		function resetMaterialPercent(){
			var totalQuantity = 0;
			for(var index in  $scope.formula.materialSettings){
				var quantity = $scope.formula.materialSettings[index].materialQuantity;
				if(quantity == undefined){
					quantity = 0;
				}
				totalQuantity += parseFloat(quantity);
			}
			for(var index in  $scope.formula.materialSettings){
				var quantity = $scope.formula.materialSettings[index].materialQuantity;
				if(quantity == undefined){
					quantity = 0;
				}
				quantity = parseFloat(quantity) * 100;
				var percent = quantity/totalQuantity;
				$scope.formula.materialSettings[index].materialPercent = percent.toFixed(2);
			}
		}
		
		function getFormula(){
			var formula = {};
			formula.id = $scope.formula.id;
			formula.name = $scope.formula.name;
			formula.description = $scope.formula.description;
			formula.materialSettings = [];
			for(var index in  $scope.formula.materialSettings){
				var newMaterial = {};
				newMaterial.formulaId = $scope.formula.materialSettings[index].formulaId;
				newMaterial.materialId = $scope.formula.materialSettings[index].materialId;
				newMaterial.materialQuantity = $scope.formula.materialSettings[index].materialQuantity;
				newMaterial.materialPercent = $scope.formula.materialSettings[index].materialPercent;
				formula.materialSettings.push(newMaterial);
			}
			
			return formula;
			
		}
		
		function validateFormula(formula){
			var totalQuantity = 0.0;
			for(var index in formula.materialSettings){
				var materialQuantity = formula.materialSettings[index].materialQuantity;
				var materialId = formula.materialSettings[index].materialId;
				if(isNaN(materialQuantity)){
					ineuronApp.confirm("提示","原料的比例应为数字。", 'sm', $rootScope, $uibModal);
					return false;
				}
				if(materialId == 0){
					ineuronApp.confirm("提示","原料不能为空。", 'sm', $rootScope, $uibModal);
					return false;
				}
				for(var index2 in formula.materialSettings){
					if(index != index2){
						var materialId2 = formula.materialSettings[index2].materialId;
						if(materialId2 == materialId){
							ineuronApp.confirm("提示","原料不能重复。", 'sm', $rootScope, $uibModal);
							return false;
						}
					}
				}
				
			}
			return true;			
		}
		
		$scope.backward = backward;		
		function backward(){
			$state.go("formulaList");
		}

	} ]);

ineuronApp.controller('CreateFormulaController', [
	'$http',
	'$stateParams',
	'$scope',
	'$location',
	'$cookies',
	'$state',
	'$rootScope', 
	'$uibModal',
	function($http, $stateParams, $scope, $location, $cookies, $state, $rootScope, $uibModal) {
		var vm = this;
		$scope.formula = {};
	
		$scope.createFormula = createFormula;
		
		function createFormula(){
			if($scope.formula.name == null){
				ineuronApp.confirm("提示","请输入配方名称", 'sm', $rootScope, $uibModal);
				return;
			}
			$http({
				url : '/product/createformula',
				method : 'POST',
				data : $scope.formula
			}).success(function(data) {
				updateApiToken(data, $cookies);
				ineuronApp.confirm("提示","保存成功！", 'sm', $rootScope, $uibModal);
				$state.go("formulaList");
			}).error(function(data, status) {
				//ineuronApp.confirm("提示","保存失败！", 'sm', $rootScope, $uibModal);
				handleError(status, $rootScope, $uibModal);
				console.log("error");
			})
		}
		
		$scope.backward = backward;		
		function backward(){
			$state.go("formulaList");
		}
	}]);