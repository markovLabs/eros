function setMsgProfileImages($scope, $http){
	$scope.profileImgs = new Map()
	$http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/images/").then(function(response){
		$scope.profileImgs.set($scope.matchId, "http://69.164.208.35:8001/imgs/" + response.data.images[0].name);
	});
	$http.get($scope.erosBaseUrl + "/daters/" + $scope.daterId + "/images/").then(function(response){
		$scope.profileImgs.set(parseInt($scope.daterId), "http://69.164.208.35:8001/imgs/" + response.data.images[0].name);
	});
}

function setProfileImages($scope, $http){
	$http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/images/").then(function(response){
		var images = response.data.images;
		$scope.slides.splice(0, $scope.slides.length)
		for (var i = 0; i < images.length; i++) {
			imgSrc = "http://69.164.208.35:8001/imgs/" + images[i].name;
			$scope.slides.push({src : imgSrc, id:i});
		}
		$scope.profileImage = 	$scope.slides[0].src;
	});
}

function setProfile(matches, $window, $scope, $http){
	$scope.matchIndex = parseInt($window.sessionStorage.getItem("matches_index"));
	var matchAndStoryId = matches[$scope.matchIndex];
	$scope.matchId = matchAndStoryId.match.id;
	$scope.storyId = matchAndStoryId.story_id;
	$scope.matchName = matchAndStoryId.match.profile_name;
	setProfileImages($scope, $http);
	setMsgProfileImages($scope, $http);
	$scope.buttonLabel = "Take survey";
}

function setStory($q, $http, $scope) {
	var daterStoryAnswerPromise = $http.get($scope.erosBaseUrl + "/daters/" + $scope.daterId + "/story_answers/?story_id=" + $scope.storyId)
		.then(function(response){
			if(response.data.story_answers.length > 0) {
				$scope.daterStoryAnswer = response.data.story_answers[0].answer 
			}
		});
	var matchStoryAnswerPromise = $http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/story_answers/?story_id=" + $scope.storyId)
	.then(function(response){
		if(response.data.story_answers.length > 0) {
			$scope.matchStoryAnswer = response.data.story_answers[0].answer 	
		}
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

const TIMER_INTERVAL_IN_MS = 5000;//60000;

var app = angular.module("msgEvaluation", ['ngMaterial', 'ngAnimate', 'ngSanitize', 'ui.bootstrap']); 

app.controller("msgEvaluationController",function($scope, $http, $window, $q, $interval, $mdToast){ 
	$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1';
	$scope.slides = [{src:"../imgs/blank.jpg", id:0}]
	$scope.profileImage = "../imgs/blank.jpg" ;
	$scope.matchName = "";
	$scope.disableContinueButton = true;
	$scope.msgs = []
	$scope.eventId = $window.sessionStorage.getItem("event_id");
	$scope.daterId = $window.sessionStorage.getItem("dater_id");
	var matches = $window.sessionStorage.getItem("matches");
	if(angular.isUndefined(matches) || matches == null){
		$http.get($scope.erosBaseUrl +"/events/"+ $scope.eventId + "/daters/" + $scope.daterId + "/matches/").then(function(response){
			$window.sessionStorage.setItem("matches", JSON.stringify(response.data.matches));
			$window.sessionStorage.setItem("matches_index", 0);
			matches = JSON.parse($window.sessionStorage.getItem("matches"));
			setProfile(matches, $window, $scope, $http);
			setStory($q, $http, $scope);
		});
	} else {
		setProfile(JSON.parse(matches), $window, $scope, $http);
		setStory($q, $http, $scope);
	}
	$scope.retrieveMsgsLock = "open"
	$interval(function() {
		if($scope.retrieveMsgsLock == "open") {
			$scope.retrieveMsgsLock = "closed";
			var url = $scope.erosBaseUrl + "/messages/?from=" + $scope.matchId
				+ "&to=" + $scope.daterId + "&messages_received="
				+ $scope.msgs.length + "&event_id=" + $scope.eventId + "&between=true";
			$http.get(url).then(function(response) {
				var msgs = response.data.messages;
				for (var i = 0; i < msgs.length; i++) {
					$scope.msgs.push({msg:msgs[i], id:$scope.msgs.length})
				}
				$scope.$apply();
				$scope.retrieveMsgsLock = "open";
			}, function(err){
				console.log(err.data)
				$scope.retrieveMsgsLock = "open";
			});
		}
	}, 100);
	
	var tick = 0;
	$interval(function(){
		tick = tick + 1;
		if(tick == 2){
			$mdToast.show($mdToast.simple().textContent('1 min left to finish the conversation.').hideDelay(3000));
		}
		if(tick >= 3){
			$scope.disableContinueButton = false
		}
	}, TIMER_INTERVAL_IN_MS);
	
	$scope.onContinue=function(){
		if(!$scope.disableContinueButton){
			$window.location.href="../survey_messaging_evaluation_page/survey_messaging_evaluation_page.html";
		}
	}
	
	$scope.onSendMsg=function(){
		var url = $scope.erosBaseUrl + "/messages/";
		var msgContent = $scope.msgSent;
		$scope.msgSent = "";
		var msg = {from_dater_id:$scope.daterId, to_dater_id:$scope.matchId, event_id:$scope.eventId, content:msgContent}
		$http.post(url, msg).then(function(response){
			console.log(response.data.id)
		}, function(err){
			$mdToast.simple().textContent('Message was not sent. Please try again.').hideDelay(3000)
			console.log(err.data)
		});
	}
})