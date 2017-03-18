#!/bin/sh

PWD=`pwd`
cd `dirname $0`

cd ../bees.water.server/
git pull
mvn clean
mvn package -DskipTests=true

rm -rf ../lib/*
rm -rf ../templates
mv -f target/classes/templates ../templates/
mv -f target/*.jar ../lib/water.jar

cd $PWD