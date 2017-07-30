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

function setEvent($scope, $http){
	return function(){
		var event = getNextEvent($http, $scope.erosBaseUrl)
		if(angular.isDefined(event)) {
			$scope.event = event
			if(event.started) {
				$scope.disableContinueButton = false
			} else{
				$scope.disableContinueButton = true
			}
		} else {
			$scope.disableContinueButton = true
			$scope.event = {name:"Sorry no event scheduled. Please check again later", location:"", date:""}
		}
	}
}

var app = angular.module("eventInfo", ['ngMaterial']); 
app.controller("eventInfoController",function($scope, $http, $window, $interval){ 
	$scope.erosBaseUrl = 'http://localhost:17320/eros/v1'
	setEvent($scope, $http).apply();
	$interval(setEvent($scope, $http), 3000);
	$scope.onContinue=function(){
		if(!$scope.disableContinueButton){
	    	$window.location.href="../profile_evaluation_page/profile_evaluation_page.html";
		}
	}
})