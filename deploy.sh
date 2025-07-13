#!/usr/bin/env bash

set -x
# fail fast (if a command fails the script will abort)
set -e

if [[ "$OSTYPE" != "darwin"* ]]; then
  echo "THIS SCRIPT IS COMPATIBLE WITH MACOS ONLY"
  exit
fi

# java11 javac
javac11=/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home/bin/javac

# java11 jar
jar11=/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home/bin/jar

rm -rf build
rm -f RollTheTable.war

mkdir -p build/classes
mkdir -p build/war

# shellcheck disable=SC2046
$javac11 -d build/classes -cp "lib/*" $(find src -name "*.java")

cp -r src/web/* build/war

mkdir -p build/war/WEB-INF/classes
cp -r build/classes/* build/war/WEB-INF/classes

mkdir -p build/war/WEB-INF/lib
cp -r lib/*.jar build/war/WEB-INF/lib
rm build/war/WEB-INF/lib/javax.servlet-api-4.0.1.jar

$jar11 cvf RollTheTable.war -C build/war .

cp RollTheTable.war /opt/homebrew/opt/tomcat@9/libexec/webapps

# start tomcat
/opt/homebrew/opt/tomcat@9/bin/catalina run
