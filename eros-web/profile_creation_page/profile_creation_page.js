var q1={id:8,
content:"How many total months have you been a user of at least one online dating system? (Online dating systems include mobile dating apps and online dating websites).",
a:"0 months (I have never been a user of an online dating system before)",
b:"1-3 months",
c:"4-6 months",
d:"7-12 months",
e:"More than 12 months"
};

var q2={id:9,
content:"What is your highest level of education completed?",
a:"Some High School",
b:"High School Diploma or GED",
c:"Some College",
d:"Vocational School",
e:"Associate's Degree",
f:"Bachelor's Degree",
g:"Master's Degree",
h:"Doctoral Degree or equivalent"
};

var q3={id:10,
content:"What is your ethnicity?",
a:"Asian or Pacific Islander",
b:"Black or African American",
c:"Native American or American Indian",
d:"White",
e:"Other"
};

var q4={id:11,
content:"Are his Hispanic/Latino?",
a:"Yes",
b:"No"
};

var s1={id:1,
content:"1.     Jack and Stephanie are on their first date. They are having a good time together and the hours pass by quickly. It’s getting late and they both need to work tomorrow. They agree they want to go on a second date. Jack suggests this coming Thursday. Stephanie says: sorry I can't; I have a date scheduled with someone else that day. Jack is bothered by this. Is his reaction to Stephanie’s response justified?",
a:"Yes, Jack should be bothered",
b:"No, Jack should not be bothered"
};

var s2={id:2,
content:"2.     Annabelle and Donald are at a bar/restaurant for their first date. They are having an easy-flowing conversation and they discovered that they have a lot in common. They both had one beer so far. Donald gets up to order more drinks at the bar and says, “the next one is on me.” He returns with a beer for Annabelle and a Sprite for himself. Annabelle is bothered by this. Is her reaction to Donald’s behavior justified?",
a:"Yes, Annabelle should be bothered by Donald returning with a beer for her and a Sprite for him",
b:"No, Annabelle should not be bothered by Donald returning with a beer for her and a Sprite for him"
};

var s3={id:3,
content:"3.     Brian and Nancy are at a bar/restaurant for their first date. They are having an easy-flowing conversation and they discovered that they have a lot in common. While Nancy is telling a funny story about her job she hears a beep and takes her phone out of her purse to check a new text message she just received. Brian is bothered by this. Is his reaction to Nancy’s behavior justified?",
a:"Yes, Brian should be bothered by Nancy checking her new message",
b:"No, Brian should not be bothered by Nancy checking her new message"
};

var s4={id:4,
content:"4.     Frank and Lisa are on their first date and they are having a good time together. Near the end of the date they are discussing recent political topics including gay marriage when Lisa asks Frank, “have you ever had sex with another man?” Frank thinks about how he had a couple one-night stands with men in his younger adult years. He decides to answer “no.” Was it okay for Frank to lie in this situation?",
a:"Yes, it was okay for Frank to lie about his homosexual experiences",
b:"No, it was not okay for Frank to lie about his homosexual experiences"
};

var s5={id:5,
content:"5.     Harry and Danielle just finished their first date. They had an easy-flowing conversation and discovered they have a lot of things in common. However, Harry learned that Danielle is friends with his ex-girlfriend who he’s no longer on speaking terms with. This is a deal breaker for Harry—he decides not to go on any more dates with Danielle because she’s friends with his ex. Was this a good reason for Harry to reject Danielle?", 
a:"Yes, Danielle being friends with his ex was a good reason for Harry to reject Danielle",
b:"No, Danielle being friends with his ex was not a good reason for Harry to reject Danielle"
};

var s6={id:6,
content:"6.     Tony and Joan just finished their first date. They had an easy-flowing conversation and discovered they have a lot in common. However, Joan, who is 5’3”, learned that Tony is actually 5’10”—he had told her before the date that he was 6’1”. This is a deal breaker for Joan—she decides to not go on any more dates with Tony because he lied about his height. Was this a good reason for Joan to reject Tony?",
a:"Yes, Tony’s dishonesty about his height was a good reason for Joan to reject Tony",
b:"No, Tony’s dishonesty about his height was not a good reason for Joan to reject Tony"
};

var s7={id:7,
content:"7.     Julia and Mark met on a dating app and they are going on their first date tonight. A few hours before their date, Mark sends Julia a selfie in the mirror with his date outfit. He asks “what do you think?” The picture makes Julia realize that she isn’t physically attracted to Mark at all. This is a deal breaker for Julia, and she decides that she’s going to cancel the date. Did she make the right decision?",
a:"Yes, Julia made the right decision",
b:"No, Julia did not make the right decision"
};
 
var s8={id:8,
content:"8.     Victor and Courtney had their first date a few nights ago. Courtney didn’t feel a connection and she doesn’t want to continue dating Victor. Today they are talking and Courtney plans to tell Victor that she doesn't want to go on a second date. She says, ”I’m sorry Victor, but you’re not my type physically. I think it’s best we stop seeing each other.” Was this a good way for Courtney to reject Victor?",
a:"Yes, this was a good way for Courtney to reject Victor",
b:"No, this was not a good way for Courtney to reject Victor"
};

function saveAnswers($http, $scope, baseURL, id){
    var questionIds = [q1.id, q2.id, q3.id, q4.id]
    var answers = [$scope.answer1, $scope.answer2, $scope.answer3, $scope.answer4]
    for (i = 0; i < questionIds.length; i++){
        var url = baseURL + "/daters/" + id + "/profile_answers/"
        var answer = {question_id:questionIds[i], answer:answers[i]}
        $http.post(url, answer)
    }
}

function saveStories($http, $scope, baseURL, id){
    var storyIds = [s1.id, s2.id, s3.id, s4.id, s5.id, s6.id, s7.id, s8.id]
    var answers = [$scope.answer5, $scope.answer6, $scope.answer7, $scope.answer8, $scope.answer9, $scope.answer10, $scope.answer11, $scope.answer12];
    for (i = 0; i < storyIds.length; i++){
        var url = baseURL + "/daters/" + id + "/story_answers/"
        var answer = {story_id:storyIds[i], answer:answers[i]}
        $http.post(url, answer)
    }
}

function updateDater($http, baseURL, id, $window){
    var daterURL = baseURL + "/daters/" + id;
    var dater = {profile_created:true};
    $http.post(daterURL, dater).then(function(response){
       $window.location.href = "../profile/profile.html";
    });
}

function getEventIdsByEventDescription(events){
    var eventIdsByDesc = new Map();
    for(i = 0; i < events.length; i++){
        event = events[i];
        desc = event.name + "," + event.location + "," + event.date;
        eventIdsByDesc.set(desc, event.id);
    }
    return eventIdsByDesc;
}

function saveEventSelected($http, baseURL, daterId, selectedEventId){
    dater = {id:daterId};
    $http.post(baseURL + "/events/" + selectedEventId + "/daters", dater);
}

var app = angular.module("profileCreation", [ 'ngMaterial' ]);
app.controller("profileCreationController", 
		function($scope, $http, $window) {
	$scope.q1 = q1;
	$scope.q2 = q2;
	$scope.q3 = q3;
	$scope.q4 = q4;
	$scope.s1 = s1;
	$scope.s2 = s2;
	$scope.s3 = s3;
	$scope.s4 = s4;
	$scope.s5 = s5;
	$scope.s6 = s6;
	$scope.s7 = s7;
	$scope.s8 = s8;
	$http.get($scope.baseURL + "/events").then(function(response){
	   $scope.eventIdsByDesc = getEventIdsByEventDescription(response.data.events);
	});
	$scope.onContinue=function(){
	    var baseURL = $scope.erosBaseUrl
        var id = $window.sessionStorage.getItem('dater_id');
        var selectedEventId = $scope.eventIdsByDesc.get($scope.selectedEventDesc);
	    saveAnswers($http, $scope, baseURL, id);
	    saveStories($http, $scope, baseURL, id);
	    saveEventSelected($http, baseURL, id, selectedEventId);
	    updateDater($http, baseURL, id, $window);
	}
});