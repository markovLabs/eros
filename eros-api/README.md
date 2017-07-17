# eros API

## Events API


| Method | URL       | Response                      | Payload     | Query Param |
|-------|:-----------|:----------------------------- |:------------| :-----------|
| GET | eros/v1/events/ | ListTO<Event> | None | None |
| GET | eros/v1/events/ | Event | None | next_event={true|false} |
| GET | eros/v1/events/${event_id} | Event | None | None |
| POST | eros/v1/events/ | Event | Event | None |
| GET | eros/v1/events/${event_id}/daters/ | ListTO<Dater> | None | None |
| POST | eros/v1/events/${event_id}/daters/ | Dater | Dater | None |
| GET | eros/v1/events/${event_id}/daters/${dater_id} | Dater | None | None |
| DELETE | eros/v1/events/${event_id}/daters/${dater_id} | None | None | None |
| GET | eros/v1/events/${event_id}/daters/${dater_id}/matches | ListTO<DaterMatchTO> | None | None |
| GET | eros/v1/events/${event_id}/daters/${dater_id}/matches/${match_id} | DaterMatchTO | None | None |
| GET | eros/v1/events/${event_id}/daters/${dater_id}/matches/${match_id}/answers | ListTO<EvaluationAnswer> | None | None |
| POST | eros/v1/events/${event_id}/daters/${dater_id}/matches/${match_id}/answers | EvaluationAnswer | EvaluationAnswer | None |
| GET | eros/v1/events/${event_id}/daters/${dater_id}/matches/${match_id}/answers/${answer_id} | EvaluationAnswer | None | None |
| GET | eros/v1/daters/ | ListTO<Dater> | None | None |
| GET | eros/v1/daters/${dater_id} | Dater | None | None |
| GET | eros/v1/daters/ | Dater | None | email={true|fale} |
| POST | eros/v1/daters/${dater_id} | Dater | Dater | None |
| POST | eros/v1/daters/ | Dater | Dater | None |
| GET | eros/v1/daters/${dater_id}/images | ListTO<Image> | None | None |
| GET | eros/v1/daters/${dater_id}/images/${image_id} | Image | None | None |
| POST | eros/v1/daters/${dater_id}/images | Image | Image | None |
| DELETE | eros/v1/daters/${dater_id}/images/${image_id} | None | None | None |
| GET | eros/v1/daters/${dater_id}/story_answers/ | ListTO<StoryAnswer> | None | None |
| GET | eros/v1/daters/${dater_id}/story_answers/${answer_id} | StoryAnswer | None | None |
| POST | eros/v1/daters/${dater_id}/story_answers/ | StoryAnswer | StoryAnswer | None |
| GET | eros/v1/daters/${dater_id}/profile_answers/ | ListTO<ProfileAnswer> | None | None |
| GET | eros/v1/daters/${dater_id}/profile_answers/${profile_answer_id} | ProfileAnswer | None | None |
| POST | eros/v1/daters/${dater_id}/profile_answers/ | ProfileAnswer | ProfileAnswer | None |
| GET | eros/v1/matches/mappings | ListTO<MatchMapping> | None | None |
| GET | eros/v1/matches/mappings/${mapping_id} | MatchMapping | None | None |
| POST | eros/v1/matches/mappings | MatchMapping | MatchMapping | None |
| GET | eros/v1/messages | ListTO<Message> | None | None |
| GET | eros/v1/messages | ListTO<Message> | None | from=${from_dater_id}&to=${to_dater_id}|
| GET | eros/v1/messages | ListTO<Message> | None | from=${from_dater_id}&to=${to_dater_id}&$(messagesReceived) |
| GET | eros/v1/messages/${message_id} | Message | None | None |
| POST | eros/v1/messages | Message | Message | None |
| GET | eros/v1/questions | ListTO<Question> | None | None |
| GET | eros/v1/questions/${question_id} | Question | None | None |
| POST | eros/v1/questions/${question_id} | Question | Question | None |
| POST | eros/v1/questions | Question | Question | None |
| GET | eros/v1/stories | ListTO<Story> | None | None |
| GET | eros/v1/stories/${story_id} | Story | None | None |
| POST | eros/v1/stories/${story_id} | Story | Story | None |
| POST | eros/v1/stories | Story | Story | None |
