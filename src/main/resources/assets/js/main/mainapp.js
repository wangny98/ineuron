//define the package as ineuronApp
var ineuronApp = angular.module('ineuronApp', [ 'ui.router', 'ngCookies', 'datatables', 'isteven-multi-select',
                                                'ui.bootstrap','ui.sortable','ngFileUpload','nvd3','tm.pagination', 'ngRadialGauge']);

ineuronApp.config(function($stateProvider) {


	var aboutState = {
		name : 'about',
		url : '/about',
		templateUrl : '/ineuron/frontpage.html'
	}
	
	
	//User Managerment
	
	var userManagementState = {
		name : 'userManagement',
		url : 'userManagement',
		templateUrl : '/ineuron/user/list.html'
	}
	
	var roleManagementState = {
			name : 'roleManagement',
			url : 'roleManagement',	
			templateUrl : '/ineuron/user/rolelist.html'
		}

	var updateUserState = {
		name : 'updateUser',
		url : 'updateUser/:userStr',
		templateUrl : '/ineuron/user/updateUser.html'
		}
	
	var updateRoleState = {
			name : 'updateRole',
			url : 'updateRole/:roleStr',
			templateUrl : '/ineuron/user/updateRole.html'
		}
	
	var createRoleState = {
			name : 'createRole',
			url : 'createRole',
			templateUrl : '/ineuron/user/createRole.html'
		}
	
	var createUserState = {
			name : 'createUser',
			url : 'createUser',
			templateUrl : '/ineuron/user/createUser.html'
		}

	
	
	//Product Management
	
	var createProductCategoryState = {
			name : 'createProductCategory',
			url : 'createProductCategory',
			templateUrl : '/ineuron/product/createProductCategory.html'
		}
	
	var productCategoryManagementState = {
			name : 'productCategoryList',
			url : 'productCategoryList',
			templateUrl : '/ineuron/product/productCategoryList.html'
		}
	
	var updateProductCategoryState = {
			name : 'updateProductCategory',
			url : 'updateProductCategory/:productCategoryStr',
			templateUrl : '/ineuron/product/updateProductCategory.html'
		}
	
	var productManagementState = {
			name : 'productList',
			url : 'productList/:productCategoryStr',
			templateUrl : '/ineuron/product/productList.html'
		}
	
	var allProductManagementState = {
			name : 'allProductList',
			url : 'allProductList',
			templateUrl : '/ineuron/product/allProductList.html'
		}
	
	var createProductState = {
			name : 'createProduct',
			url : 'createProduct',
			templateUrl : '/ineuron/product/createProduct.html'
		}
	
	var updateProductState = {
			name : 'updateProduct',
			url : 'updateProduct/:productStr',
			templateUrl : '/ineuron/product/updateProduct.html'
		}
	
	var updateProductPriceState = {
			name : 'updateProductPrice',
			url : 'updateProductPrice/:productStr',
			templateUrl : '/ineuron/product/updateProductPrice.html'
		}
	
	
	var attributeManagementState = {
			name : 'attributeList',
			url : 'attributeList',
			templateUrl : '/ineuron/product/attributeList.html'
		}
	
	/* combine attribute create and update to one controller
	 * var createAttributeState = {
			name : 'createAttribute',
			url : 'createAttribute',
			templateUrl : '/ineuron/product/createAttribute.html'
		}*/
	
	var createAndUpdateAttributeState = {
			name : 'createAndUpdateAttribute',
			url : 'createAndUpdateAttribute/:attributeStr',
			templateUrl : '/ineuron/product/createAndUpdateAttribute.html'
		}
	
	var productManufacturingProcessState = {
			name : 'productManufacturingProcess',
			url : 'productManufacturingProcess/:productStr',
			templateUrl : '/ineuron/product/manufacturingprocess.html'
		}
	
	var formulaManagementState = {
			name : 'formulaList',
			url : 'formulaList',
			templateUrl : '/ineuron/product/formulaList.html'
		}
	
	var updateFormulaState = {
			name : 'updateFormula',
			url : 'updateFormula/:formulaStr',
			templateUrl : '/ineuron/product/updateFormula.html'
		}
	
	var createFormulaState = {
			name : 'createFormula',
			url : 'createFormula/',
			templateUrl : '/ineuron/product/createFormula.html'
		}

	

	//Order
	var searchForOrderState = {
			name : 'searchForOrder',
			url : 'searchForOrder/',
			templateUrl : '/ineuron/order/searchForOrder.html'
		}
	
	var createOrderState = {
			name : 'createOrder',
			url : 'createOrder/:productStr',
			templateUrl : '/ineuron/order/createOrder.html'
		}
	
	var orderManagementState = {
			name : 'orderList',
			url : 'orderList',
			templateUrl : '/ineuron/order/orderList.html'
		}
	
	var updateOrderState = {
			name : 'updateOrder',
			url : 'updateOrder/:orderStr',
			templateUrl : '/ineuron/order/updateOrder.html'
		}
	

	//Material
	var materialManagementState = {
			name : 'materialList',
			url : 'materialList',
			templateUrl : '/ineuron/material/materialList.html'
		}
	
	var updateMaterialState = {
			name : 'updateMaterial',
			url : 'updateMaterial/:materialStr',
			templateUrl : '/ineuron/material/updateMaterial.html'
		}
	
	var createMaterialState = {
			name : 'createMaterial',
			url : 'createMaterial/',
			templateUrl : '/ineuron/material/createMaterial.html'
		}
	
	$stateProvider.state(userManagementState);
	$stateProvider.state(roleManagementState);
	$stateProvider.state(updateUserState);
	$stateProvider.state(createUserState);
	$stateProvider.state(updateRoleState);
	$stateProvider.state(createRoleState);
	
	$stateProvider.state(productCategoryManagementState);
	$stateProvider.state(createProductCategoryState);
	$stateProvider.state(updateProductCategoryState);
	$stateProvider.state(updateProductState);
	$stateProvider.state(updateProductPriceState);
	$stateProvider.state(productManagementState);
	$stateProvider.state(allProductManagementState);
	$stateProvider.state(createProductState);
	
	$stateProvider.state(attributeManagementState);
	$stateProvider.state(createAndUpdateAttributeState);
	
	$stateProvider.state(productManufacturingProcessState);
	$stateProvider.state(formulaManagementState);
	$stateProvider.state(updateFormulaState);
	$stateProvider.state(createFormulaState);
	
	$stateProvider.state(materialManagementState);
	$stateProvider.state(createMaterialState);
	$stateProvider.state(updateMaterialState);
	
	$stateProvider.state(searchForOrderState);
	$stateProvider.state(createOrderState);
	$stateProvider.state(orderManagementState);
	$stateProvider.state(updateOrderState);
	
	$stateProvider.state(aboutState);

});

ineuronApp.controller('NavMenuController', ['$scope', function($scope) {
	
	$scope.hasPermission = function(funcId) {
		return hasPermission(funcId);
	}
	
}]);

ineuronApp.controller('AccountSettingController', ['$scope', '$cookies', function($scope, $cookies) {
	
	$scope.displayUsername=$cookies.get("INeuron-UserName", {path : "/"});
	
	$scope.logout = function() {
		$cookies.remove("INeuron-ApiToken", {path : "/"});
		$cookies.remove("INeuron-ProductSearchText", {path : "/"});
		$cookies.remove("INeuron-UserId", {path : "/"});
		$cookies.remove("INeuron-UserName", {path : "/"});
		$cookies.remove("INeuron-allPermissions", {path : "/"});
		$cookies.remove("INeuron-roleList", {path : "/"});
		window.location.href = "/ineuron/user/index.html/#/login";
	}
	
}]);


ineuronApp.controller('FrontpageController', ['$scope', '$timeout', function($scope, $timeout) {
	
	$scope.value = 1.5;
    $scope.upperLimit = 100;
    $scope.lowerLimit = 0;
    $scope.unit = "%";
    $scope.precision = 2;
    $scope.ranges = [
        {
            min: 0,
            max: 20,
            color: '#DEDEDE'
        },
        {
            min: 20,
            max: 40,
            color: '#8DCA2F'
        },
        {
            min: 40,
            max: 60,
            color: '#FDC702'
        },
        {
            min: 60,
            max: 80,
            color: '#FF7700'
        },
        {
            min: 80,
            max: 100,
            color: '#C50200'
        }
    ];
	
}]);

