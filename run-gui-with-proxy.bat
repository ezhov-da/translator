@echo off
cd /d %~dp0
start "run" "%JAVA_HOME%\bin\javaw" -jar -Xmx768m "spring_stage.jar"

        -Dhttp.proxyHost=
        -Dhttp.proxyPort=
        -Dhttp.proxyUser=
        -Dhttp.proxyPassword=
        -Dhttps.proxyHost=
        -Dhttps.proxyPort=
        -Dhttps.proxyUser=
        -Dhttps.proxyPassword=