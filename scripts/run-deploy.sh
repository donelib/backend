#!/usr/bin/bash

LOG_PATH=~/log/back.log
ERROR_LOG_PATH=~/log/back.log
WORK_DIR=/home/ec2-user/build/back
BUILD_DIR=$WORK_DIR/build
RELEASE_PATH=$WORK_DIR/release

FILENAME=$(ls $BUILD_DIR | grep 'jar' | tail -n 1)
BACKUP_PATH=$WORK_DIR/backup/$FILENAME

echo "> copy build file to $BACKUP_PATH"
cp $BUILD_DIR/$FILENAME $BACKUP_PATH

echo "> link release"
ln -sfn $BACKUP_PATH $RELEASE_PATH

echo "> 실행중인 어플리케이션 확인"
CURRENT_PID=$(pgrep -f java)
echo ">> Current pid : $CURRENT_PID"

if [[ -z $CURRENT_PID ]] ; then
  echo ">> 구동 중인 어플리케이션이 없습니다"
else
  echo ">> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 어플 실행"
echo ">> jar file : $BACKUP_PATH"
PROPERTIES=~/build/back/properties/application-prod.yaml
JAVA_OPTION="--spring.profiles.active=prod --spring.config.import=file:$PROPERTIES"
echo ">> java option : $JAVA_OPTION"
nohup java -jar $RELEASE_PATH $JAVA_OPTION 1> $LOG_PATH 2> $ERROR_LOG_PATH &
exit $?