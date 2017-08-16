#!/bin/bash

EVENT_ID=0
START_EVENT="false"
END_EVENT="false"
MAPPING_FOR="0"

function print_help(){
    echo $1
    echo -e "Usage is:\n event_id=${event_id} --mapping_for=${num_of_daters_per_gender} Set mapping for given event."
    echo "event_id=${event_id}  --start_event=true  Start event(if it's set to false it will do nothing)."
    echo "event_id=${event_id}  --end_event=true  End event(if it's set to false it will do nothing)."
    exit 
}

function get_param(){
    local param=`echo "${1}" | sed -e 's/^[^=]*=//g'`
    if [[ $3 == ${PARAM_REQUIRED} ]]; then
        [ "X" = "X""${param}" ] &&  print_help "missing ${2} !"
    fi
    echo "${param}"
}

function set_arguments() {
    while [ $# -gt 0 ]; do
      case $1 in
        --help)
            print_help
            ;;
        --event_id=*)
            EVENT_ID=$(get_param $1)
            ;;
        --mapping_for=*)
            MAPPING_FOR=$(get_param $1)
            ;;
        --start_event=*)
            START_EVENT=$(get_param $1)
            ;;
        --end_event=*)
            END_EVENT=$(get_param $1)
            ;;                      
            *)           
            echo "Unknown parameter $1. Exiting. Launch with --help for usage"
            exit 1
            ;;
      esac
      shift        
    done
}

function get_mapping_id(){
    if [[ "${MAPPING_FOR}" -eq "8" ]]; then
        echo "3"
    elif [[ ${END_EVENT} -eq "6" ]]; then
        echo "2"
    else
        echo "1"
    fi
}

function main(){
    set_arguments "$@"
    if [[ ${START_EVENT} -eq "true" ]]; then
        curl -H "Content-Type: application/json" -X POST -d '{"started":true}' http://69.164.208.35:17320/eros/v1/events/${EVENT_ID} 
    elif [[ ${END_EVENT} -eq "true" ]]; then
        curl -H "Content-Type: application/json" -X POST -d '{"ended":true}' http://69.164.208.35:17320/eros/v1/events/${EVENT_ID}
    elif [[ ${MAPPING_FOR} -ne "0" ]]; then
        local mapping_id=$(get_mapping_id)
        curl -H "Content-Type: application/json" -X POST -d '{"mapping_id":${mapping_id}}' http://69.164.208.35:17320/eros/v1/events/1/
    fi
}

main "$@"