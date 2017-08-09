function setProfileImages($scope, $http){
	$http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/images/").then(function(response){
		var images = response.data.images;
		for (var i = 0; i < images.length; i++) {
			imgSrc = "http://69.164.208.35:8001/imgs/" + images[i].name;
			$scope.slides.push({src : imgSrc, id:i});
		}
		$scope.profileImage = $scope.slides[0].src;
	});
}

function setProfile(matches, $window, $scope, $http){
	$scope.matchIndex = $window.sessionStorage.getItem("matches_index");
	var matchAndStoryId = matches[$scope.matchIndex];
	$scope.matchId = matchAndStoryId.match.id;
	$scope.matchName = matchAndStoryId.match.profile_name;
	setProfileImages($scope, $http);
	$scope.buttonLabel = "Take survey";
}

var app = angular.module("msgEvaluation", ['ngMaterial', 'ngAnimate', 'ngSanitize', 'ui.bootstrap']); 
app.controller("msgEvaluationController",function($scope, $http, $window){ 
	$scope.disableContinueButton = true;
	$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1'
	$scope.eventId = $window.sessionStorage.getItem("event_id");
	$scope.daterId = $window.sessionStorage.getItem("dater_id");
	var matches = $window.sessionStorage.getItem("matches");
	if(angular.isUndefined(matches)){
		$http.get($scope.erosBaseUrl + "/daters/" + $scope.daterId + "/matches/").then(function(response){
			$window.sessionStorage.setItem("matches", response.data.matches);
			$window.sessionStorage.setItem("matches_index", 0);
			matches = $window.sessionStorage.getItem("matches");
			setProfile(matches, $window, $scope, $http);
		});
	} else {
		setProfile(matches, $window, $scope, $http);
	}
	
	$timeout(function(){
		$scope.disableContinueButton = false
	}, 180000);
	
	$scope.onContinue=function(){
		if(!$scope.disableContinueButton){
			$window.location.href="survey_messaging_evaluation_page.html";
		}
	}
})