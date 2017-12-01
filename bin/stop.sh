#!/bin/bash

function stop_service(){
    local service=$1
    local pid=$(ps -ef | grep eros | grep ${service} | awk -F' ' '{ print $2}')
    kill ${pid}
    wait 3s
}

function main(){
    set -o nounset
    set -o errexit
    
    stop_service "web"
    stop_service "api"
}

main "$@"