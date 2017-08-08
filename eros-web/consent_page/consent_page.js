var app = angular.module("consentPage", ['ngMaterial']); 
app.controller("consentPageController",function($scope, $http, $window){ 
	$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1'
	$scope.onContinue=function(){
		if(angular.isUndefined($scope.firstName) || angular.isUndefined($scope.lastName) || !$scope.agree){
			$window.alert("Please write your first name, last name and agree to consent to continue.")
		} else{
	    	var daterURL = $scope.erosBaseUrl + "/daters/" + $window.sessionStorage.getItem('dater_id');
	    	var dater = {name:$scope.firstName, last_name:$scope.lastName, accepted_consent:true};
	    	$http.post(daterURL, dater).then(function(response){
	    		$window.location.href="../acceptance_question/acceptance_question.html";
	    	});
		}
	}
})