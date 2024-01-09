#!/usr/bin/env bash

if [ -z "$1" ]; then
  port="8081"
else
  port="$1"
fi
process_PID="$(lsof -ti tcp:"${port}")"
# If there is any application listening to port 8081 (which is the one used to deploy the app) kill it.
if [ -n "${process_PID}" ] &> /dev/null; then
  # if there is someone running in port 8081
  echo "process with PID ${process_PID} $(ps -p ${process_PID} -o comm=) is listening to port ${port}. Let's kill it"
  echo "${process_PID}" | xargs kill
  #sleep 5
fi