@echo off
cd /d %~dp0
start "run" "%JAVA_HOME%\bin\javaw" -cp "translator-gui\target\translator-gui.jar;translator-engine\target\translator-engine.jar" "ru.ezhov.translator.gui.App" "http://localhost:8080/translator/rest/translate/v1?engine=%%s&lang=%%s&text=%%s"