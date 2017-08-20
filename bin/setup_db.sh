#!/bin/bash

declare -r CREATE_EROS_DB="eros_db_schema.sql"
declare -r CREATE_EROS_DB_BASE_DATA="eros_db_base_data.sql"
declare -r PARAM_REQUIRED=1

MYSQL_USER="root"
MYSQL_PWD="MyNewPass"
HOST="localhost"

function print_help(){
    echo $1
    echo -e "Usage is:\n --mysql_user  Set MySQL user"
    echo "--mysql_pwd Set MySQL pwd"
    echo "--host  Set host where its going to be deployed."
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
        --host=*)
            HOST=$(get_param $1)
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
    ./deploy_sql.sh --sql=${CREATE_EROS_DB} --mysql_user=${MYSQL_USER} --mysql_pwd=${MYSQL_PWD} --host=${HOST}
    ./deploy_sql.sh --sql=${CREATE_EROS_DB_BASE_DATA} --mysql_user=${MYSQL_USER} --mysql_pwd=${MYSQL_PWD} --host=${HOST}
}

main "$@"