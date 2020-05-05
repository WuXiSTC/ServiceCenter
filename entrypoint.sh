#!/bin/ash
groupadd --non-unique --gid ${WXSTC_GROUP_ID:-1000} WXSTC
useradd  --non-unique --uid ${WXSTC_USER_ID:-1000} --no-log-init --create-home --gid WXSTC WXSTC
chown WXSTC:WXSTC /WXSTC
if [[ "${@:0:1}" == "-" ]]; then
  exec su-exec WXSTC:WXSTC java -jar services.jar "$@"
else
  exec su-exec WXSTC:WXSTC "$@"
fi