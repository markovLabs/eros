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
