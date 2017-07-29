var q1 = { 
	id:1,
	content:" If I had to make a choice right now, I would choose to go on a date with this person.",
	answers:["Disagree strongly 1", "Disagree moderately 2", "Disagree a little 3", "Neither agree nor disagree 4", "Agree a little 5", "Agree moderately 6", "Agree strongly 7"]
};

var q2={id:1,
content:" I am very confident in my answer to the previous question.",
answers:["Disagree strongly 1", "Disagree moderately 2", "Disagree a little 3", "Neither agree nor disagree 4", "Agree a little 5", "Agree moderately 6", "Agree strongly 7"]
};

function setProfileImages($scope, $http){
	$http.get($scope.erosBaseUrl + "/daters/" + $scope.matchId + "/images/").then(function(response){
		var images = reponse.data.images;
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
	$scope.disableContinueButton = true;
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