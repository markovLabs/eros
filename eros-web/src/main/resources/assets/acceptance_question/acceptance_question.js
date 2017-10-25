var question1 = {
    id:2,
    content : "Select your gender and sexual preference.",
    answer1 : "I am a man attracted to women.",
    answer2 : "I am a woman attracted to men.",
    answer3 : "Other"
}
var question2 = {
    id:3,
    content : "Are you currently single (i.e. not a committed relationship with another person)?",
    answer1 : "Yes",
    answer2 : "No"
}

var question3 = {
    id:4,
    content : "What is your age? (Please be honest; other daters will not see this)"
}

function range(start, end) {
    var lst = [];
    for (var i = start; i <= end; i++) {
        lst.push(i);
    }
    return lst;
}

function loadPage($window, page){
    return function(){
        $window.location.href=page
    }
}

function updateDater($http, baseURL, id, dater, loadPage){
	return function(){
	    var daterURL = baseURL + "/daters/" + id;
	    $http.post(daterURL, dater).then(function(response){
	       loadPage()
	    });
	}
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

function saveAnswers($q, $http, $scope, baseURL, id, afterAnswersSaved, $window){
    var questionIds = [question1.id, question2.id, question3.id]
    var answers = [$scope.answer1, $scope.answer2, $scope.answer3]
    var storeAnswers = function(){
        var url = baseURL + "/daters/" + id + "/profile_answers/"
        var promises = []
        for (var i = 0; i < questionIds.length; i++){
            var answer = {question_id:questionIds[i], answer:answers[i]}
            var promise = $http.post(url, answer)
            promises.push(promise)
        }
        $q.all(promises).then(function(){
        	afterAnswersSaved();
        })
    } 

    validateAnswers(answers, $window, storeAnswers);
}

var app = angular.module("acceptanceQuestion", [ 'ngMaterial' ]);
app.controller("acceptanceQuestionController",
        function($scope, $http, $window, $q) {
			$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1'
            $scope.question1 = question1;
            $scope.question2 = question2;
            $scope.question3 = question3;
            $scope.ages = range(21, 65);
            var loadRejectionPage = loadPage($window, "../rejection/rejection.html");
            var loadProfileCreation = loadPage($window, "../profile_creation_page/profile_creation_page.html");
            $scope.onContinue=function(){
                var baseURL = $scope.erosBaseUrl
                var id = $window.sessionStorage.getItem('dater_id')
                if($scope.answer1 == question1.answer3 || $scope.answer2 == question2.answer2){
                    var dater = {rejected:true};
                    updateDater($http, baseURL, id, dater, loadRejectionPage).apply();
                } else {
                    var dater = {accepted_profile_questions:true};
                    var updateDaterCallback = updateDater($http, baseURL, id, dater, loadProfileCreation);
                    saveAnswers($q, $http, $scope, baseURL, id, updateDaterCallback, $window)
                }
            }

        });