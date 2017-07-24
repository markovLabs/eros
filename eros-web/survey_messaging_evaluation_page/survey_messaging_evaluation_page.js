

var q2={id:1,
content:"How would you rate the overall 'impact' that this person's statements had on you during your messaging interaction?",
a:"Super negative",
b:"Negative",
c:"Neutral",
d:"Positive",
e:"Super positive"};

var q3={id:1,
content:"If I had to make a choice right now, I would choose to go on a date with this person.",
a:"Disagree strongly 1", 
b:"Disagree moderately 2",
c:"Disagree a little 3",
d:"Neither agree nor disagree 4",
e:"Agree a little 5",
f:"Agree moderately 6",
g:"Agree strongly 7"
};

var q4={id:1,
content:"I am very confident in my answer to the previous question.",
a:"Disagree strongly 1", 
b:"Disagree moderately 2",
c:"Disagree a little 3",
d:"Neither agree nor disagree 4",
e:"Agree a little 5",
f:"Agree moderately 6",
g:"Agree strongly 7"
};

var q5={id:1,
content:"I was very engaged and interested in the discussion I had with this person in the messaging",
a:"Disagree strongly 1", 
b:"Disagree moderately 2",
c:"Disagree a little 3",
d:"Neither agree nor disagree 4",
e:"Agree a little 5",
f:"Agree moderately 6",
g:"Agree strongly 7"
};

var q6={id:1,
content:"Quality of the messaging interaction (How pleasant was it?)",
label1:"Unpleasant",
label2:"Very pleasant",
"a":1,
"b":2,
"c":3,
"d":4,
"e":5,
"f":6,
"g":7
};

var q7={id:1,
content:"Degree of disagreement/conflict throughout the entire messaging interaction",
label1:"Very little",
label2:"A great deal",
"a":1,
"b":2,
"c":3,
"d":4,
"e":5,
"f":6,
"g":7
};

var q8={id:1,
content:"Degree of closeness/camaraderie during the messaging interaction",
label1:"Very little",
label2:"A great deal",
"a":1,
"b":2,
"c":3,
"d":4,
"e":5,
"f":6,
"g":7
};

var q9={id:1,
content:"My level of satisfaction with the messaging interaction",
label1:"Dissatisfied",
label2:"Very Satisfied",
"a":1,
"b":2,
"c":3,
"d":4,
"e":5,
"f":6,
"g":7
};

var q10={id:1,
content:"I got from this messaging interaction ...",
label1:"Less than expected/hoped for",
label2:"More than expected/hoped for",
"a":1,
"b":2,
"c":3,
"d":4,
"e":5,
"f":6,
"g":7
};


var app = angular.module("surveyMsgEvaluation", ['ngMaterial']); 
app.controller("surveyMsgEvaluationController",function($scope, $http, $window){ 
	
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