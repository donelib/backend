#!/usr/bin/bash

try=0
while :
do
  curl localhost:8080/actuator/health | grep UP
  SUCCESS=$?
  if [ $SUCCESS -eq 0 ]; then
    break
  fi
  if [ $try -gt 4 ]; then
    break
  fi
  ((try++))
  sleep 10
done
exit $SUCCESS