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

function setStory($q, $http, $scope) {
	var daterStoryAnswerPromise = $http.get($scope.erosBaseUrl + "/daters/" + $scope.daterId + "/story_answers/?story_id=" + $scope.storyId)
		.then(function(response){
			$scope.daterStoryAnswer = response.data.story_answers[0].answer 
		});
	var matchStoryAnswerPromise = $http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/story_answers/?story_id=" + $scope.storyId)
	.then(function(response){
		$scope.matchStoryAnswer = response.data.story_answers[0].answer 
	});
	var storyPromise = $http.get($scope.erosBaseUrl + "/stories/" + $scope.storyId)
		.then(function(response){
			$scope.story = response.data;
		});
	var promises = [daterStoryAnswerPromise, matchStoryAnswerPromise, storyPromise];
    $q.all(promises).then(function(){
    	$scope.s1 = {}
    	$scope.s1.content =  $scope.story.content + $scope.story.question
    	if($scope.story.story_type != "Unprompted") {
        	$scope.s1.answerA =  $scope.story.answer_a + getStoryLabel("A", $scope.daterStoryAnswer, $scope.matchStoryAnswer)
        	$scope.s1.answerB =  $scope.story.answer_b + getStoryLabel("B", $scope.daterStoryAnswer, $scope.matchStoryAnswer)
    	}
    })
}

function getStoryLabel(answerType, daterAnswer, matchAnswer){
	if(answerType == daterAnswer && answerType == matchAnswer){
		return "(Both picked)";
	} else if (answerType == daterAnswer) {
		return "(You picked)";
	} else if (answerType == matchAnswer){
		return "(Your match picked)";
	} else {
		return "";
	}
}

var app = angular.module("msgEvaluation", ['ngMaterial', 'ngAnimate', 'ngSanitize', 'ui.bootstrap']); 
app.controller("msgEvaluationController",function($scope, $http, $window, $q){ 
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