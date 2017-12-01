#!/bin/bash

##############################################
# Upload artifact and reestart eros. 
#############################################

declare -r ENV="prod"

HOST="localhost"
ARTIFACT="eros*"
ARTIFACT_PATH="target"
USER="root"

declare -r PARAM_REQUIRED=1

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
    echo "Uploading artifact"
    scp "${ARTIFACT_PATH}/${ARTIFACT}.tar.gz" ${USER}@${HOST}:~/
    echo "Unzipping artifact"
    ssh ${USER}@${HOST} "tar -xvf ${ARTIFACT}.tar.gz && mv ${ARTIFACT} eros"
    echo "Stopping eros"
    ssh ${USER}@${HOST} "eros/bin/stop.sh"
    wait 10s
    echo "Run eros"
    ssh ${USER}@${HOST} "screen -r 11710.pts-2.people && eros/bin/run.sh"
}

main "$@"
