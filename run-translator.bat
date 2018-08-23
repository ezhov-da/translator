@echo off
cd /d %~dp0
start "run" "%JAVA_HOME%\bin\javaw" -cp "translator-gui.jar" "ru.ezhov.translator.gui.App" "https://prog-tools.ru:8445/translator/rest/translate/v1?engine=%%s&lang=%%s&text=%%s"