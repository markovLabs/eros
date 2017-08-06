var q1 = { 
	id:12,
	content:" If I had to make a choice right now, I would choose to go on a date with this person.",
	answers:["Disagree strongly 1", "Disagree moderately 2", "Disagree a little 3", "Neither agree nor disagree 4", "Agree a little 5", "Agree moderately 6", "Agree strongly 7"]
};

var q2={id:13,
content:" I am very confident in my answer to the previous question.",
answers:["Disagree strongly 1", "Disagree moderately 2", "Disagree a little 3", "Neither agree nor disagree 4", "Agree a little 5", "Agree moderately 6", "Agree strongly 7"]
};

function setProfileImages($scope, $http){
	$http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/images/").then(function(response){
		var images = response.data.images;
		for (var i = 0; i < images.length; i++) {
			imgSrc = "http://69.164.208.35:8001/imgs/" + images[i].name;
			$scope.slides.push({src : imgSrc, id:i});
		}
		$scope.profileImage = 	$scope.slides[0].src;
	});
}

function setProfile(matches, $window, $scope, $http){
	$scope.matchIndex = $window.sessionStorage.getItem("matches_index");
	var matchAndStoryId = matches[$scope.matchIndex];
	$scope.matchId = matchAndStoryId.match.id;
	$scope.matchName = matchAndStoryId.match.profile_name;
	setProfileImages($scope, $http);
	if($scope.matchIndex == matches.length - 1) {
		$scope.buttonLabel = "Go to Event Page";
	} else {
		$scope.buttonLabel = "Next Dater";
	}
}

function saveAnswers($http, $scope, baseURL, daterId){
    var questionIds = [q1.id, q2.id]
    var answers = [$scope.answer1, $scope.answer2]
    var url = baseURL + "/events/" + $scope.eventId + "/daters/" + daterId + "/matches/" + $scope.matchId + "/answers/"
    for (var i = 0; i < questionIds.length; i++){
        var answer = {question_id:questionIds[i], answer:answers[i]};
        $http.post(url, answer);
    }
}

var app = angular.module("profileEvaluation", ['ngMaterial', 'jkAngularCarousel']); 
app.controller("profileEvaluationController",function($scope, $http, $window, $timeout){ 
	$scope.q1 = q1;
	$scope.q2 = q2;
	$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1';
	$scope.slides = [{src:"../imgs/blank.jpg", id:0}]
	$scope.profileImage = "../imgs/blank.jpg";
	$scope.matchName = "";
	$scope.disableContinueButton = true;
	$scope.eventId = $window.sessionStorage.getItem("event_id");
	var daterId = $window.sessionStorage.getItem("dater_id");
	var matches = $window.sessionStorage.getItem("matches");
	if(angular.isUndefined(matches)){
		$http.get($scope.erosBaseUrl +"/events/"+ $scope.eventId + "/daters/" + $scope.daterId + "/matches/").then(function(response){
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
			saveAnswers($http, $scope, $scope.erosBaseUrl, daterId);
			$window.sessionStorage.setItem("matches_index", $scope.matchIndex + 1);
			if($scope.buttonLabel == "Go to Event Page") {
				$window.location.href="../event_info/event_info.html";
			} else {
				$window.location.href="profile_evaluation_page.html";
			}
		}
	}
})