# TRANSLATOR

Для работы приложения необходимо:
1. Собрать приложение командой mvn clean package
1. Создать папку с приложением
1. Положить в папку файлы:
    1. translator-gui\target\translator-gui.jar  
    1. В папку с приложеним скопировать файл: run-translator.bat  
**ВАЖНО!** Переменная %JAVA_HOME% должна быть объявлена.

Если Вы обращаетесь через прокси, укажите данные прокси через свойства:  
-Dhttp.proxyHost=  
-Dhttp.proxyPort=  
-Dhttp.proxyUser=  
-Dhttp.proxyPassword=  
-Dhttps.proxyHost=  
-Dhttps.proxyPort=  
-Dhttps.proxyUser=  
-Dhttps.proxyPassword=  
