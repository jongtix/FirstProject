#!/bin/bash

PROJECT_HOME=/home/ec2-user/app
REPOSITORY=$PROJECT_HOME/step2
PROJECT_NAME=FirstProject

OLD_JAR=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">>> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY

echo ">>> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl FirstProject | awk '{print $1}') #awk '{print $1}': id 찾기

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo ">>> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo ">>> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo ">>> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">>> JAR Name: $JAR_NAME"

echo ">>> $JAR_NAME 에 실행 권한 추가"

chmod +x $JAR_NAME

echo ">>> $JAR_NAME 실행"

if [ ${OLD_JAR} -ne ${JAR_NAME} ]; then
  echo ">>> 이전 배포 파일 삭제"
  rm $OLD_JAR
fi

nohup java -jar \
  -Dspring.config.location=classpath:/application.yml,classpath:/application-real.yml,$PROJECT_HOME/application-oauth.yml,$PROJECT_HOME/application-real-db.yml \
  -Dspring.profiles.active=real \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &  #nohup 실행 시 COdeDeploy는 무한 대기
                                            #이 이슈를 해결하기 위해 nohup.out 파일을 표준 입출력용으로 별도로 사용
                                            #이렇게 하지 않으면 nohup.out 파일이 생기지 않고, CodeDeploy 로그에 표준 입출력이 출력됨
                                            #nohup이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇게 해야 함