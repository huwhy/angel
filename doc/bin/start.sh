#!/bin/sh

PWD=`pwd`
cd `dirname $0`

#SERVER="com.comblife.water.server.AdminBootstrap"
SERVER="../lib/water.jar"
PIDS=`ps -ef | grep java | grep "$SERVER" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "start fail! The $SERVER already started!"
    exit 1
fi
DEBUG_ARGS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8001,suspend=n -Djava.ext.dirs=../lib:/usr/java/default/jre/lib/ext"
VM_ARGS="-Xms512m -Xmx512m -Xss512k -Dlog4j.configurationFile=../conf/log4j2.xml -Dlog.path=../logs"
CONF_ARGS="--spring.config.location=../conf/application.yml --spring.config.location=application"
nohup java $VM_ARGS -jar $SERVER $CONF_ARGS > ../logs/nohup.log 2>&1 &

echo "$SERVER start success!"

cd $PWD


