@echo off
cd /d %~dp0
start "run" "%JAVA_HOME%\bin\javaw" -jar -Xmx768m "target\translator.jar"