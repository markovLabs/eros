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
		$scope.slides.splice(0, $scope.slides.length)
		for (var i = 0; i < images.length; i++) {
			var imgSrc = "http://69.164.208.35:8001/imgs/" + images[i].name;
			$scope.slides.push({src : imgSrc, id:i});
		}
		$scope.profileImage = 	$scope.slides[0].src;
	});
}

function setProfile(matches, $window, $scope, $http){
	$scope.matchIndex = parseInt($window.sessionStorage.getItem("matches_index"));
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

function saveAnswers($q, $http, $scope, baseURL, daterId, afterAnswersSaved){
    var questionIds = [q1.id, q2.id]
    var answers = [$scope.answer1, $scope.answer2]
    var url = baseURL + "/events/" + $scope.eventId + "/daters/" + daterId + "/matches/" + $scope.matchId + "/answers/"
    var promises = []
    for (var i = 0; i < questionIds.length; i++){
        var promise = $http.post(url, {question_id:questionIds[i], answer:answers[i]});
        promises.push(promise)
    }
    $q.all(promises).then(function(){
    	afterAnswersSaved();
    })
}

function validateAnswers(answers, $window, fn) {
	var invalid = false;
	for(var i = 0; i < answers.length && !invalid; i++) {
		if(angular.isUndefined(answers[i]) || answers[i] == ""){
			$window.alert("All fields must be filled.");
			invalid = true;
		} 
	}
	if(!invalid) {
		fn();
	}
}

function validateAndSave($scope, $window, fn) {
	var allAnswers = [$scope.answer1, $scope.answer2]
    validateAnswers(allAnswers, $window, fn)
}

var app = angular.module("profileEvaluation", ['ngMaterial', 'ngAnimate', 'ngSanitize', 'ui.bootstrap']); 
app.controller("profileEvaluationController",function($scope, $http, $window, $q){ 
	$scope.q1 = q1;
	$scope.q2 = q2;
	$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1';
	$scope.slides = [{src:"../imgs/blank.jpg", id:0}]
	$scope.profileImage = "../imgs/blank.jpg" ;
	$scope.matchName = "";
	$scope.disableContinueButton = false;
	$scope.eventId = $window.sessionStorage.getItem("event_id");
	$scope.daterId = $window.sessionStorage.getItem("dater_id");
	var matches = $window.sessionStorage.getItem("matches");
	if(angular.isUndefined(matches) || matches == null){
		$http.get($scope.erosBaseUrl +"/events/"+ $scope.eventId + "/daters/" + $scope.daterId + "/matches/").then(function(response){
			$window.sessionStorage.setItem("matches", JSON.stringify(response.data.matches));
			$window.sessionStorage.setItem("matches_index", 0);
			matches = JSON.parse($window.sessionStorage.getItem("matches"));
			setProfile(matches, $window, $scope, $http);
		});
	} else {
		setProfile(JSON.parse(matches), $window, $scope, $http);
	}
	
	var afterAnswersSaved = function(response){
		$window.sessionStorage.setItem("matches_index", $scope.matchIndex + 1);
		if($scope.buttonLabel == "Go to Event Page") {
			var dater = {profile_evaluation_completed:true}
			$http.post($scope.erosBaseUrl +"/events/"+ $scope.eventId + "/daters/" + $scope.daterId, dater)
			.then(function(response){
				$window.sessionStorage.removeItem("matches_index");
				$window.sessionStorage.removeItem("matches");
				$window.location.href="../event_info/event_info.html";
			});
		} else {
			$window.location.href="profile_evaluation_page.html";
		}
	}
	
	$scope.onContinue=function(){
		if(!$scope.disableContinueButton){
			var saveAnswersCallback = function(){
				saveAnswers($q, $http, $scope, $scope.erosBaseUrl, $scope.daterId, afterAnswersSaved);
			}
			validateAndSave($scope, $window, saveAnswersCallback)
		}
	}
})