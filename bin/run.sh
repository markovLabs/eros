#!/bin/bash

ENV="prod"

function print_help(){
    echo $1
    echo -e "Usage is:\n --env  Set app environment. Possible values: prod, dev. Default prod"
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
        --env=*)
            ENV=$(get_param $1)
            ;;
            *)           
            echo "Unknown parameter $1. Exiting. Launch with --help for usage"
            exit 1
            ;;
      esac
      shift        
    done
}

function run_eros_api(){
    cd .. 
    java -jar lib/eros*.jar server conf/${ENV}/eros.api.yml &
    cd web 
    python -m SimpleHTTPServer &
    cd .. 
}

function main(){
    set -o nounset
    set -o errexit
    
    set_arguments "$@"
    run_eros_api
}

main "$@"