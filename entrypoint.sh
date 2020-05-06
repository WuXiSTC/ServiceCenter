#!/bin/ash
if [[ "${@:0:1}" == "-" ]]; then
  exec java -jar services.jar "$@"
else
  exec "$@"
fi