var q2={id:18,
content:"How would you rate the overall 'impact' that this person's statements had on you during your messaging interaction?",
answers: ["Super negative", "Negative", "Neutral","Positive", "Super positive"]
};

var q3 = { id:15,
	content:"If I had to make a choice right now, I would choose to go on a date with this person.",
	answers:["Disagree strongly 1", "Disagree moderately 2", "Disagree a little 3", "Neither agree nor disagree 4", "Agree a little 5", "Agree moderately 6", "Agree strongly 7"]
};

var q4={id:16,
content:"I am very confident in my answer to the previous question.",
answers:["Disagree strongly 1", "Disagree moderately 2", "Disagree a little 3", "Neither agree nor disagree 4", "Agree a little 5", "Agree moderately 6", "Agree strongly 7"]
};

var q5={id:17,
content:"I was very engaged and interested in the discussion I had with this person in the messaging",
answers:["Disagree strongly 1", "Disagree moderately 2", "Disagree a little 3", "Neither agree nor disagree 4", "Agree a little 5", "Agree moderately 6", "Agree strongly 7"]
};

var q6={id:19,
content:"Quality of the messaging interaction (How pleasant was it?)",
label1:"Unpleasant",
label2:"Very pleasant",
answers:[1,2,3,4,5,6,7]
};

var q7={id:20,
content:"Degree of disagreement/conflict throughout the entire messaging interaction",
label1:"Very little",
label2:"A great deal",
answers:[1,2,3,4,5,6,7]
};

var q8={id:21,
content:"Degree of closeness/camaraderie during the messaging interaction",
label1:"Very little",
label2:"A great deal",
answers:[1,2,3,4,5,6,7]
};

var q9={id:22,
content:"My level of satisfaction with the messaging interaction",
label1:"Dissatisfied",
label2:"Very Satisfied",
answers:[1,2,3,4,5,6,7]
};

var q10={id:23,
content:"I got from this messaging interaction ...",
label1:"Less than expected/hoped for",
label2:"More than expected/hoped for",
answers:[1,2,3,4,5,6,7]
};

function setMatch(matches, $window, $scope, $http){
	$scope.matchIndex = parseInt($window.sessionStorage.getItem("matches_index"));
	var matchAndStoryId = matches[$scope.matchIndex];
	$scope.matchId = matchAndStoryId.match.id;
	$scope.storyId = matchAndStoryId.story_id;
	$scope.matchName = matchAndStoryId.match.profile_name;
	if($scope.matchIndex == matches.length - 1) {
		$scope.buttonLabel = "Go to Event Page";
	} else {
		$scope.buttonLabel = "Next Dater";
	}
}
var q1;
function setQ1($http, $scope) {
	$http.get($scope.erosBaseUrl + "/stories/" + $scope.storyId).then(function(response) {
		var storyType = response.data.story_type;
		if (storyType == "Prompted") {
			q1 = {
				id : 14,
				content : "Regardless of your initial opinion choices, did the conversation you just had end with an agreement of opinion choice?",
				answers: ["Yes, we agreed on an opinion choice", "No, we could not agree on an opinion choice"],
				html: '<md-input>  <h5>{{q1.content}}</h5>  <md-radio-group ng-model="answer1" layout-gt-sm="row">      <md-radio-button ng-repeat="answer in q1.answers" ng-value="answer" aria-label="{{ answer }}">          {{ answer }}      </md-radio-button>  </md-radio-group></md-input>'
			}
		}
	});
}

function saveAnswers($q, $http, $scope, baseURL, daterId, afterAnswersSaved){
    var questionIds = [q2.id, q3.id, q4.id, q5.id, q6.id, q7.id, q8.id, q9.id, q10.id]
    var answers = [$scope.answer2, $scope.answer3, $scope.answer4, $scope.answer5, $scope.answer6, $scope.answer7, $scope.answer8, $scope.answer9, $scope.answer10]
    if(angular.isDefined(q1)){
    	questionIds.push(q1.id)
    	answers.push($scope.answer1)
    }
    var url = baseURL + "/events/" + $scope.eventId + "/daters/" + daterId + "/matches/" + $scope.matchId + "/answers/"
    var promises = []
    for (var i = 0; i < questionIds.length; i++){
    	var payload = {question_id:questionIds[i], answer:answers[i]};
    	var promise = $http.post(url, payload);
        promises.push(promise)
    }
    $q.all(promises).then(function(){
    	afterAnswersSaved();
    })
}


var app = angular.module("surveyMsgEvaluation", ['ngMaterial']); 
app.controller("surveyMsgEvaluationController",function($scope, $http, $window, $q){ 
	$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1'
	$scope.q2 = q2;
	$scope.q3 = q3;
	$scope.q4 = q4;
	$scope.q5 = q5;
	$scope.q6 = q6;
	$scope.q7 = q7;
	$scope.q8 = q8;
	$scope.q9 = q9;
	$scope.q10 = q10;
	$scope.matchName = "";
	$scope.disableContinueButton = false;
	$scope.eventId = $window.sessionStorage.getItem("event_id");
	$scope.daterId = $window.sessionStorage.getItem("dater_id");
	var matches = $window.sessionStorage.getItem("matches");
	if(angular.isUndefined(matches) || matches == null){
		$http.get($scope.erosBaseUrl +"/events/"+ $scope.eventId + "/daters/" + $scope.daterId + "/matches/").then(function(response){
			$window.sessionStorage.setItem("matches", response.data.matches);
			$window.sessionStorage.setItem("matches_index", 0);
			matches = JSON.parse($window.sessionStorage.getItem("matches"));
			setMatch(matches, $window, $scope, $http);
			setQ1($http, $scope);
		});
	} else {
		setMatch(JSON.parse(matches), $window, $scope, $http);
		setQ1($http, $scope);
	}
	
	var afterAnswersSaved = function(){
		$window.sessionStorage.setItem("matches_index", $scope.matchIndex + 1);
		if($scope.buttonLabel == "Go to Event Page") {
			var dater = {messaging_evaluation_completed:true}
			$http.post($scope.erosBaseUrl +"/events/"+ $scope.eventId + "/daters/" + $scope.daterId, dater)
			.then(function(response){
				$window.location.href="../event_info/event_info.html";
			});
		} else {
			$window.location.href="../messaging_evaluation_page/messaging_evaluation_page.html";
		}
	}
	$scope.onContinue=function(){
		if(!$scope.disableContinueButton){
			saveAnswers($q, $http, $scope, $scope.erosBaseUrl, $scope.daterId, afterAnswersSaved);
		}
	}
});