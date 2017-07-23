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
    var daterURL = baseURL + "/daters/" + id;
    $http.post(daterURL, dater).then(function(response){
       loadPage()
    });
}

function saveAnswers($http, $scope, baseURL, id){
    var questionIds = [question1.id, question2.id, question3.id]
    var answers = [$scope.answer1, $scope.answer2, $scope.answer3]
    for (i = 0; i < questionIds.length; i++){
        var url = baseURL + "/daters/" + id + "/profile_answers/"
        var answer = {question_id:questionIds[i], answer:answers[i]}
        $http.post(url, answer)
    }
}

var app = angular.module("acceptanceQuestion", [ 'ngMaterial' ]);
app.controller("acceptanceQuestionController",
        function($scope, $http, $window) {
            $scope.question1 = question1;
            $scope.question2 = question2;
            $scope.question3 = question3;
            $scope.ages = range(21, 65);
            var loadRejectionPage = loadPage($window, "rejection.html");
            var loadProfileCreation = loadPage($window, "../profile_creation_page.html");
            $scope.onContinue=function(){
                var baseURL = $scope.erosBaseUrl
                var id = $window.sessionStorage.getItem('dater_id')
                if($scope.answer1 == question1.answer3 || $scope.answer2 == question2.answer2){
                    var dater = {rejected:true};
                    updateDater($http, baseURL, id, dater, loadRejectionPage);
                } else {
                    saveAnswers($http, $scope, baseURL, id)
                    var dater = {accepted_profile_questions:true};
                    updateDater($http, baseURL, id, dater, loadProfileCreation);
                }
            }

        });