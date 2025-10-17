@echo off
setlocal
set MVNW_HOME=%~dp0.mvn\wrapper
java -jar "%MVNW_HOME%\maven-wrapper.jar" %*
