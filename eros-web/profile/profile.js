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

function getImages($http, baseURL, daterId) {
	var images = [];
	$http.get(baseURL + "/daters/" + daterId + "/images/").then(function(response){
		var images = reponse.data.images;
	});
	return images;
}

function getImageSrcs(images) {
	var imgSrcs = []
	for (var i = 0; i < images.length; i++) {
		imgSrc = "../imgs/" + images[i].name;
		imgSrcs.push({
			src : imgSrc
		});
	}
	if(imgSrcs.length == 0) {
		imgSrcs.push({src:"../imgs/blank.jpg"})
	}
	return imgSrcs;
}

function updateImages($http, $scope){
	$scope.images = getImages($http, $scope.erosBaseUrl, $scope.daterId); 
    $scope.imageSrcs = getImageSrcs($scope.images);
    $scope.profileImage = $scope.imageSrcs[0].src;
}

var app = angular.module("profile", ['ngMaterial','jkAngularCarousel']); 

app.directive("fileread", [function () {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                scope.$apply(function () {
                    var reader = new FileReader();
                    reader.onload = function (loadEvent) {
                        scope.$apply(function () {
                            scope.fileread = loadEvent.target.result;
                        });
                    }
                    reader.readAsBinaryString(changeEvent.target.files[0]);
                });
            });
        }
    }
}]);

app.controller("profileController",function($scope, $http, $window, $interval){
	$scope.curImgIndex = 0;
	$scope.daterId = $window.sessionStorage.getItem('dater_id');
	$scope.file = "";
	updateImages($http, $scope);
	setEvent($scope, $http, $window).apply();
	$interval(setEvent($scope, $http, $window), 3000);
	$interval(function(){
		if($scope.file != ""){
			var img = {content:$scope.file};
			$http.post($scope.erosBaseUrl + "/daters/" + $scope.daterId + "/images/", img);
			updateImages($http, $scope);
			$scope.file = "";
		}
	}, 500);
	$scope.onContinue=function(){
		if(!$scope.disableContinueButton){
	    	$window.location.href="../event_info/event_info.html";
		}
	};
	$scope.onRemoveImage=function(){
		var imageId = $scope.images[$scope.curImgIndex].id;
		$http.delete($scope.erosBaseUrl + "/daters/" + $scope.daterId + "/images/" + id);
		updateImages($http, $scope);
	};
	$scope.onAddImage=function(){
		document.querySelector('#fileInput').click();
	};
})