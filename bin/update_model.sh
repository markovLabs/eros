#!/bin/bash

MYSQL_USER="root"
MYSQL_PWD="MyNewPass"

declare -r EROS_MODEL_DIR="../eros-api/src/generated-sources/java/com/"

function print_help(){
    echo $1
    echo -e "Usage is:\n --mysql_user  Set MySQL user"
    echo " --mysql_pwd Set MySQL pwd"
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
        --mysql_user=*)
            MYSQL_USER=$(get_param $1)
            ;;
        --mysql_pwd=*)
            MYSQL_PWD=$(get_param $1)
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
    ./setup_db.sh --mysql_user=${MYSQL_USER} --mysql_pwd=${MYSQL_PWD}
    rm -rf ${EROS_MODEL_DIR}
    mvn clean install -Pmodel-gen
}

main "$@"



