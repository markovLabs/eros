# eros

## Build
mvn clean install
mvn clean install -Pmodel-gen (build and update db model)

## Run
``` cd bin && ./run.sh ```
``` cd bin && ./run.sh --env=dev (For development) ```

## Admin Operations

Set mapping for given event. num_of_daters_per_gender can be 8, 6, 4.
``` 
cd bin && ./event_mgmt.sh event_id=${event_id} --mapping_for=${num_of_daters_per_gender} 
```

Start event(if it's set to false it will do nothing)
``` 
cd bin && ./event_mgmt.sh event_id=${event_id} --start_event=true 
```

End event(if it's set to false it will do nothing)
``` 
cd bin && ./event_mgmt.sh event_id=${event_id} --end_event=true 
```


