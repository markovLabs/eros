var app = angular.module("home", ['ngMaterial']); 
app.controller("homeController",function($scope, $http, $window){ 
	$scope.gender= "MALE";
    $scope.onSignIn=function(){
    	var datersWithThisEmailAndPwdURL = $scope.erosBaseUrl + "/daters/?email=" + $scope.email + "&pwd=" + $scope.pwd
        $http.get(datersWithThisEmailAndPwdURL).then(function (response) {
            var daters = response.data.daters;
            if (daters.length > 0) {
                var dater = daters[0]; 
                $window.sessionStorage.setItem('dater_id', dater.id);
                if(dater.rejected) {
                    $window.location.href="../rejection/rejection.html";
                } else if (dater.profile_created) {
                    $window.location.href="../profile/profile.html";
                } else if (dater.accepted_profile_questions) {
                    $window.location.href="../profile_creation_page/profile_creation_page.html";
                } else if (dater.accepted_consent){
                    $window.location.href="../acceptance_question/acceptance_question.html";
                } else {
                    $window.location.href="../consent_page/consent_page.html";
                }
            } else {
                $window.alert("Account not found. Please sign up");
                $scope.email="";
                $scope.pwd="";
            }
        });
    }
    $scope.onSignUp=function(){
    	var newDaterURL = $scope.erosBaseUrl + "/daters/"
    	var newDater = {email:$scope.emailForSignUp, pwd:$scope.pwdForSignUp, profile_name:$scope.name, gender:$scope.gender};
    	$http.post(newDaterURL, newDater).then(function(response){
    		$window.sessionStorage.setItem('dater_id', response.data.id);
    		$window.location.href="../consent_page/consent_page.html";
    	});
    }
}); 