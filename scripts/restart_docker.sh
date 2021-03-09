#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)

source ${ABSDIR}/profile_docker.sh #JAVA의 import 기능과 비슷

PROJECT_HOME=/home/ec2-user/app
REPOSITORY=$PROJECT_HOME/step4
PROJECT_NAME=FirstProject
DOCKER_COMPOSE_NAME=docker-compose.yml

TIME_STAMP=$(date +%Y%m%d%H%M%s)

echo ">>> 기존 Build 파일 백업"
echo ">>> cp -rp $REPOSITORY/jar/$PROJECT_NAME.jar $REPOSITORY/jar/backup/$PROJECT_NAME.$TIME_STAMP.jar"

if [ ! -d $REPOSITORY/jar/backup ]; then
  mkdir $REPOSITORY/jar/backup
fi

cp -rp $REPOSITORY/jar/$PROJECT_NAME.jar $REPOSITORY/jar/backup/$PROJECT_NAME.$TIME_STAMP.jar

echo ">>> Build 파일 복사"
echo ">>> cp $REPOSITORY/zip/*.jar $REPOSITORY/jar/$PROJECT_NAME.jar"

cp -rp $REPOSITORY/zip/*.jar $REPOSITORY/jar/$PROJECT_NAME.jar

echo ">>> 도커 컴포즈 다운"
echo ">>> $REPOSITORY/docker-compose down"

docker-compose down

echo ">>> docker-compose 파일 복사"
echo ">>> cp $REPOSITORY/zip/$DOCKER_COMPOSE_NAME $REPOSITORY/$DOCKER_COMPOSE_NAME"

cp $REPOSITORY/zip/$DOCKER_COMPOSE_NAME $REPOSITORY/$DOCKER_COMPOSE_NAME

echo ">>> 도커 컴포즈 업"
echo ">>> $REPOSITORY/docker-compose up -d"

docker-compose up -d

#echo ">>> 도커 재기동"
#
#IDLE_NAME=spring_boot_1
#
#echo ">>> 이름이 $IDLE_NAME 인 Docker Process의 ID 확인"
#IDLE_ID=$(docker inspect -f '{{.Id}}' $IDLE_NAME)
#
#if [ -z ${IDLE_ID} ]
#then
#  echo ">>> spring_boot_1(${IDLE_ID})이 실행 중이 아닙니다."
#else
#  echo ">>> docker restart $IDLE_ID"
#  docker restart $IDLE_ID
#  sleep 10
#fi
#
#IDLE_NAME=spring_boot_2
#
#echo ">>> 이름이 $IDLE_NAME 인 Docker Process의 ID 확인"
#IDLE_ID=$(docker inspect -f '{{.Id}}' $IDLE_NAME)
#
#if [ -z ${IDLE_ID} ]
#then
#  echo ">>> spring_boot_1(${IDLE_ID})이 실행 중이 아닙니다."
#else
#  echo ">>> docker restart $IDLE_ID"
#  docker restart $IDLE_ID
#  sleep 10
#fi