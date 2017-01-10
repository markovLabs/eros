# eros

## Events API


| Method | URL       | Response                      | Payload                          |
|-------|:-----------|:----------------------------- |:---------------------------------|
| GET | eros/v1/events |{"events": [Event,..]} | None | 
| GET | eros/v1/events/${id} | Event | None |
| POST | eros/v1/events/ | Event | {"name":Str,"datetime":Str,"location":Str} |
| DELETE | eros/v1/events/${id} | None  | None |
| GET | eros/v1/events/${id}/users | {"users": [User,..]} | None | 
| GET | eros/v1/events/${id}/users/${user_id} | User | None |
| POST | eros/v1/events/${id}/users | User | {"id":${user_id}} |
| DELETE | eros/v1/events/${id}/users/${user_id} | None  | None |
