package ru.ezhov.translator.gui.util.version;

import ru.ezhov.translator.gui.App;

public class VersionInfo {
    public static String version() {
        Class aClass = App.class;
        if (aClass.isAnnotationPresent(Version.class)) {
            Version version = (Version) aClass.getAnnotation(Version.class);
            return version.value();
        } else {
            return "[version not found]";
        }
    }
}
