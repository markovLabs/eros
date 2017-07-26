function getNextEvent($http, baseURL){
	var event;
	$http.get(baseURL + "/events?next_event=true").then(function(response){
		var events = response.data.events
		if(events.length != 0) {
			event = events[0];
		}
	});
	return event;
}

function setEvent($scope, $http, $window){
	return function(){
		var event = getNextEvent($http, $scope.erosBaseUrl)
		if(angular.isDefined(event)) {
			$window.sessionStorage.setItem("event_id", event.getId());
			$scope.disableContinueButton = false
		} else {
			$scope.disableContinueButton = true
		}
	}
}

var app = angular.module("profile", ['ngMaterial']); 
app.controller("profileController",function($scope, $http, $window, $interval){
	setEvent($scope, $http, $window).apply();
	$interval(setEvent($scope, $http, $window), 3000);
	$scope.onContinue=function(){
		if(!$scope.disableContinueButton){
	    	$window.location.href="../event_info/event_info.html";
		}
	}
})