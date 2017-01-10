# eros

## Events API


| Method | URL         | Response                                                  | Payload                                 |
| -------|:-----------:|:---------------------------------------------------------:|----------------------------------------:|
| GET | eros/v1/events | {"events":[{"id":Int,"name":Str,"datetime":Str,"location":Str},..]} | None | 
| GET | eros/v1/events/${id} | {"id":Int,"name":Str, "datetime":Str,"location":Str}| None |
| POST | eros/v1/events/ | {"id":Int,"name":Str,"datetime":Str,"location":Str} | {"name":Str,"datetime":Str,"location":Str} |
| DELETE | eros/v1/events/${id} | None  | None |
| GET | eros/v1/events/${id}/users | {"users":[{"id":Int,"name":Str,"credential_id":Int},..]} | None | 
| GET | eros/v1/events/${id}/users/${user_id} | {"id":Int,"name":Str,"credential_id":Int} | None |
| POST | eros/v1/events/${id}/users | {"id":Int,"name":Str,"credential_id":Int} | {"id":${user_id}} |
| DELETE | eros/v1/events/${id}/users/${user_id} | None  | None |
