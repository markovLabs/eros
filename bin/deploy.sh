#!/bin/bash

declare -r ENV="prod"

HOST="localhost"
ARTIFACT="eros*"
ARTIFACT_PATH="target"
USER="root"

function print_help(){
    echo $1
    echo -e "Usage is:\n --host  Set host where its going to be deployed."
    echo "--user  Set user who has access to host"
    echo "--artifact  Set artifact filename"
    echo "--artifact_path  Set dir path to artifact"
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
        --host=*)
            HOST=$(get_param $1)
            ;;
        --user=*)
            USER=$(get_param $1)
            ;;
        --artifact=*)
            ARTIFACT=$(get_param $1)
            ;;
        --artifact_path=*)
            ARTIFACT_PATH=$(get_param $1)
            ;;
            *)           
            echo "Unknown parameter $1. Exiting. Launch with --help for usage"
            exit 1
            ;;
      esac
      shift        
    done
}


function main(){
    set -o nounset
    set -o errexit
    
    set_arguments "$@"
    scp ${ARTIFACT_PATH}/${ARTIFACT} ${USER}@${HOST}:~/
    ssh ${USER}@${HOST} "tar -xvf ${ARTIFACT}"
    ssh ${USER}@${HOST} "tar -xvf ${ARTIFACT}/web/eros* && rm ${ARTIFACT}/web/eros*gz && mv ${ARTIFACT}/web/eros*/* ${ARTIFACT}/web/ && rm -rf ${ARTIFACT}/web/eros*"
    ssh ${USER}@${HOST} "pid=$(ps -o pid,args -C bash | awk 'eros.api { print $1 }') && kill ${pid}"
    wait 10s
    ssh ${USER}@${HOST} "${ARTIFACT}/bin/run.sh"
}

main "$@"
