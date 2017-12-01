#!/bin/bash

##############################################################################################################
# Run eros from home dir. Assume eros is not running, eros dirname is eros and the script is being run from ~  
###############################################################################################################

ENV="prod"
declare -r PARAM_REQUIRED=1

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

function run_eros(){
    java -jar ~/eros/lib/eros*api*.jar server ~/eros/conf/${ENV}/eros.api.yml &
    java -jar ~/eros/lib/eros*web*.jar server ~/eros/conf/${ENV}/eros.web.yml &
}

function main(){
    set -o nounset
    set -o errexit
    
    set_arguments "$@"
    run_eros
}

main "$@"