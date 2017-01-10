# eros

## Events API

| Method | URL         | Response                                                  | Payload                                 |
| -------|:-----------:|:---------------------------------------------------------:|----------------------------------------:|
| GET | eros/v1/events | {"events":[{"id":Int, name":String, "datetime":String, "location":String},..]} | None | 
| GET | eros/v1/events/${id} | {"id":Int, name":String, "datetime":String, "location":String}| None |
| POST | eros/v1/events/ | {"id":Int, name":String, "datetime":String, "location":String} | {"name":String, "datetime":String, "location":String} |
| DELETE | eros/v1/events/${id} | None  | None |
| GET | eros/v1/events/${id}/users | {"users":[{"id":Int,"name":String, "credential_id":Int},..]} | None | 
| GET | eros/v1/events/${id}/users/${user_id} | {"id":Int,"name":String, "credential_id":Int} | None |
| POST | eros/v1/events/${id}/users | {"id":Int,"name":String, "credential_id":Int} | {"id":${user_id}} |
| DELETE | eros/v1/events/${id}/users/${user_id} | None  | None |
