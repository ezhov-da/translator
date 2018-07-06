@echo off
cd /d %~dp0
start "run" "%JAVA_HOME%\bin\javaw" ^
-Dhttp.proxyHost= ^
-Dhttp.proxyPort= ^
-Dhttp.proxyUser= ^
-Dhttp.proxyPassword= ^
-Dhttps.proxyHost= ^
-Dhttps.proxyPort= ^
-Dhttps.proxyUser= ^
-Dhttps.proxyPassword= ^
-jar -Xmx768m "target\translator.jar"
