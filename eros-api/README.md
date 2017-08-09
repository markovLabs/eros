# eros API

Base URL: http://${host}:${port}/eros/v1

## Events API


| Method | URL       | Response                      | Payload     | Query       |
|-------|:-----------|:----------------------------- |:------------| :-----------|
| GET | /events/ | ListTO\<Event\> | None | None |
| GET | /events/ | Event | None | next_event={true\|false} |
| GET | /events/${eventId} | Event | None | None |
| POST | /events/ | Event | Event | None |
| GET | /events/${eventId}/daters/ | ListTO\<Dater\> | None | None |
| POST | /events/${eventId}/daters/ | Dater | Dater | None |
| GET | /events/${eventId}/daters/${daterId} | Dater | None | None |
| DELETE | /events/${eventId}/daters/${daterId} | None | None | None |
| GET | /events/${eventId}/daters/${daterId}/matches | ListTO\<DaterMatchTO\> | None | None |
| GET | /events/${eventId}/daters/${daterId}/matches/${match_id} | DaterMatchTO | None | None |
| GET | /events/${eventId}/daters/${daterId}/matches/${match_id}/answers | ListTO\<EvaluationAnswer\> | None | None |
| POST | /events/${eventId}/daters/${daterId}/matches/${match_id}/answers | EvaluationAnswer | EvaluationAnswer | None |
| GET | /events/${eventId}/daters/${daterId}/matches/${match_id}/answers/${answerId} | EvaluationAnswer | None | None |


## Daters API


| Method | URL       | Response                      | Payload     | Query       |
|-------|:-----------|:----------------------------- |:------------| :-----------|
| GET | /daters/ | ListTO\<Dater\> | None | None |
| GET | /daters/${daterId} | Dater | None | None |
| GET | /daters/ | Dater | None | email=${email}&pwd=${pwd} |
| POST | /daters/${daterId} | Dater | Dater | None |
| POST | /daters/ | Dater | Dater | None |
| GET | /daters/${daterId}/images | ListTO\<Image\> | None | None |
| GET | /daters/${daterId}/images/${imageId} | Image | None | None |
| POST | /daters/${daterId}/images | Image | Image | None |
| DELETE | /daters/${daterId}/images/${imageId} | None | None | None |
| GET | /daters/${daterId}/story_answers/ | ListTO\<StoryAnswer\> | None | None |
| GET | /daters/${daterId}/story_answers/${answerId} | StoryAnswer | None | None |
| POST | /daters/${daterId}/story_answers/ | StoryAnswer | StoryAnswer | None |
| GET | /daters/${daterId}/profile_answers/ | ListTO\<ProfileAnswer\> | None | None |
| GET | /daters/${daterId}/profile_answers/${profileAnswerId} | ProfileAnswer | None | None |
| POST | /daters/${daterId}/profile_answers/ | ProfileAnswer | ProfileAnswer | None |


## Matches API


| Method | URL       | Response                      | Payload     | Query       |
|-------|:-----------|:----------------------------- |:------------| :-----------|
| GET | /matches/mappings | ListTO\<MatchMapping\> | None | None |
| GET | /matches/mappings/${id} | MatchMapping | None | None |
| POST | /matches/mappings | MatchMapping | MatchMapping | None |


## Messages API


| Method | URL       | Response                      | Payload     | Query       |
|-------|:-----------|:----------------------------- |:------------| :-----------|
| GET | /messages | ListTO\<Message\> | None | None |
| GET | /messages | ListTO\<Message\> | None | from=${fromDaterId}&to=${toDaterId}|
| GET | /messages | ListTO\<Message\> | None | from=${fromDaterId}&to=${toDaterId}&messagesReceived=${count} |
| GET | /messages/${id} | Message | None | None |
| POST | /messages | Message | Message | None |


## Questions API


| Method | URL       | Response                      | Payload     | Query       |
|-------|:-----------|:----------------------------- |:------------| :-----------|
| GET | /questions | ListTO\<Question\> | None | None |
| GET | /questions/${question_id} | Question | None | None |
| POST | /questions/${question_id} | Question | Question | None |
| POST | /questions | Question | Question | None |


## Stories API


| Method | URL       | Response                      | Payload     | Query       |
|-------|:-----------|:----------------------------- |:------------| :-----------|
| GET | /stories | ListTO\<Story\> | None | None |
| GET | /stories/${story_id} | Story | None | None |
| POST | /stories/${story_id} | Story | Story | None |
| POST | /stories | Story | Story | None |

# Examples

Delete dater 2 from event 1
```
curl -H "Content-Type: application/json" -X DELETE http://69.164.208.35:17320/eros/v1/events/1/daters/2
```

Add dater 3 to event 1
```
curl -H "Content-Type: application/json" -X POST -d '{"id":3}' http://69.164.208.35:17320/eros/v1/events/1/daters/
```

Create event
```
curl -H "Content-Type: application/json" -X POST -d '{"name":"My Bar name", "location":"The bar address", "date":"2017-08-20", "mapping_id":1}' http://69.164.208.35:17320/eros/v1/events/
```

Add a story to event 1
```
curl -H "Content-Type: application/json" -X POST -d '{"story_id":1, "label":"PA"}' http://69.164.208.35:17320/eros/v1/events/1/stories/
```

Set mapping id 1 for event 1
```
curl -H "Content-Type: application/json" -X POST -d '{"mapping_id":1}' http://69.164.208.35:17320/eros/v1/events/1/
```

Add mapping, ${new_mapping} must escape all double quotes.
```
curl -H "Content-Type: application/json" -X POST -d '{"mapping":"${new_mapping}"}' http://69.164.208.35:17320/eros/v1/matches/mappings/
```

Start event 1
```
curl -H "Content-Type: application/json" -X POST -d '{"started":true}' http://69.164.208.35:17320/eros/v1/events/1/
```

End event 1
```
curl -H "Content-Type: application/json" -X POST -d '{"ended":true}' http://69.164.208.35:17320/eros/v1/events/1/
```


