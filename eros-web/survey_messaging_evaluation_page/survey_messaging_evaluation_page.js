

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


var app = angular.module("surveyMsgEvaluation", ['ngMaterial']); 
app.controller("surveyMsgEvaluationController",function($scope, $http, $window){ 
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
	$scope.onContinue=function(){

	}
});