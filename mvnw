#!/usr/bin/env sh
MAVEN_WRAPPER_HOME="$(dirname "$0")/.mvn/wrapper"
java -jar "$MAVEN_WRAPPER_HOME/maven-wrapper.jar" "$@"
#!/usr/bin/env bash
# Minimal mvnw script (may require executable permission on Unix)
basedir=$(dirname "$0")
exec "$basedir/.mvn/wrapper/maven-wrapper.jar" "$@"
