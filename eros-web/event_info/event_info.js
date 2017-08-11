function setNextEvent($http, baseURL, daterId, onResponse){
	$http.get(baseURL + "/events?next_event=true&dater_id=" + daterId).then(function(response){
		var events = response.data.events
		var event;
		if(events.length != 0) {
			event = events[0];
		}
		onResponse(event)
	});
}

function setEvent($scope, $http, $window){
	return function(){
		setNextEvent($http, $scope.erosBaseUrl,$scope.daterId, function(event){
			if(angular.isDefined(event)) {
				$scope.event = event
				$window.sessionStorage.setItem('event_id', event.id);
				if(event.started) {
					$scope.disableProfileEvaluationButton = false
				} else{
					$scope.disableProfileEvaluationButton = true
				}
			} else {
				$scope.disableProfileEvaluationButton = true
				$scope.event = {name:"Sorry no event scheduled. Please check again later", location:"", date:""}
			}
		})
	}
}

function getEventDaterAndThen($http, baseURL, daterId, eventId, f){
	$http.get(baseURL + "/events/" + eventId + "/daters/" + daterId).then(function(response){
		f(response.data);
	});
}

function setEvaluationFlags($scope, $http){
	return function(){
		if(angular.isDefined($scope.event)){
			getEventDaterAndThen($http, $scope.erosBaseUrl, $scope.daterId, $scope.event.id, function(dater){
		    	$scope.profileEvaluationCompleted = dater.profile_evaluation_completed; 
				$scope.messagingEvaluationCompleted = dater.messaging_evaluation_completed;
			});
		}
	}
}

var app = angular.module("eventInfo", ['ngMaterial']); 
app.controller("eventInfoController",function($scope, $http, $window, $interval){ 
	$scope.erosBaseUrl = 'http://69.164.208.35:17320/eros/v1'
    $scope.disableContinueButton = true
    $scope.profileEvaluationCompleted = false;
	$scope.messagingEvaluationCompleted = false;
	$scope.daterId = $window.sessionStorage.getItem('dater_id');
	setEvent($scope, $http, $window).apply();
	$interval(setEvent($scope, $http, $window), 3000);
	setEvaluationFlags($scope, $http).apply();
	$interval(setEvaluationFlags($scope, $http), 3000);
	$scope.onClickProfileEvaluation=function(){
		if(!$scope.disableProfileEvaluationButton && !$scope.profileEvaluationCompleted){
	    	$window.location.href="../profile_evaluation_page/profile_evaluation_page.html";
		}
	}
	$scope.onClickMessagingEvaluation=function(){
		if(!$scope.disableProfileEvaluationButton && $scope.profileEvaluationCompleted && !$scope.messagingEvaluationCompleted){
	    	$window.location.href="../messaging_evaluation_page/messaging_evaluation_page.html";
		}
	}
})