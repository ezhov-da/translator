# TRANSLATOR
Переведено сервисом «Яндекс.Переводчик» http://translate.yandex.ru/.  

Для работы приложения необходимо:
1. Собрать приложение командой mvn clean package
1. Создать папку с приложением
1. Положить в папку файлы:
    1. translator-gui\target\translator-gui.jar
    1. translator-engine\target\translator-engine.jar  
    **Важно!** Приложение плагиновое и в дальнейшем возможна поддержка разных сервисов перевода.  
    Сейчас доступен перевод только от Яндекса.
1. В папке с приложеним создать .bat файл следующего содержания:  
**ВАЖНО!** Переменная %JAVA_HOME% должна быть объявлена.

@echo off  
cd /d %~dp0  
start "run" "%JAVA_HOME%\bin\javaw" "-Dyandex.key=зарегистрированный ключ яндекса используется для работы яндекса переводчика" "-Dyandex.url=https://translate.yandex.net/api/v1.5/tr.json/translate?key=%%s&text=%%s&lang=%%s" -cp "translator-gui.jar;translator-engine.jar" "ru.ezhov.translator.gui.App" "ru.ezhov.translator.engine.YandexTranslate"


Если Вы обращаетесь через прокси, укажите данные прокси через свойства:  
-Dhttp.proxyHost=  
-Dhttp.proxyPort=  
-Dhttp.proxyUser=  
-Dhttp.proxyPassword=  
-Dhttps.proxyHost=  
-Dhttps.proxyPort=  
-Dhttps.proxyUser=  
-Dhttps.proxyPassword=  
