#!/usr/bin/bash

/home/ec2-user/build/back/deploy.sh > /dev/null 2> /dev/null < /dev/null &
exit $?