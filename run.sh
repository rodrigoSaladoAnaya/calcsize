#!/bin/bash
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED"
mvn compile exec:java -Dexec.mainClass="zxcv.rrrssa.App"
