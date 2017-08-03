function setProfileImages($scope, $http){
	$http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/images/").then(function(response){
		var images = response.data.images;
		var imgSrcs = []
		for (var i = 0; i < images.length; i++) {
			imgSrc = "../imgs/" + images[i].name;
			imgSrcs.push({ src : imgSrc });
		}
		$scope.imageSrcs = imgSrcs;
		$scope.profileImage = imgSrcs[0].src;
	});
}

function setProfile(matches, $window, $scope, $http){
	$scope.matchIndex = $window.sessionStorage.getItem("matches_index");
	var match = matches[$scope.matchIndex];
	$scope.matchId = match.getMatch().getId();
	$scope.matchName = match.getMatch().getProfileName();
	setProfileImages($scope, $http);
	if($scope.matchIndex == matches.length - 1) {
		$scope.buttonLabel = "Go to Event Page";
	} else {
		$scope.buttonLabel = "Next Dater";
	}
}

var app = angular.module("msgEvaluation", ['ngMaterial', 'jkAngularCarousel']); 
app.controller("msgEvaluationController",function($scope, $http, $window){ 
	$scope.disableContinueButton = true;
	$scope.erosBaseUrl = 'http://localhost:17320/eros/v1'
	$scope.eventId = $window.sessionStorage.getItem("event_id");
	var daterId = $window.sessionStorage.getItem("dater_id");
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
			$window.sessionStorage.setItem("matches_index", $scope.matchIndex + 1);
			if($scope.buttonLabel == "Go to Event Page") {
				$window.location.href="../event_info/event_info.html";
			} else {
				$window.location.href="messaging_evaluation_page.html";
			}
		}
	}
})