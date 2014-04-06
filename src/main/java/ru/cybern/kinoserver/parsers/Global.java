package ru.cybern.kinoserver.parsers;

public class Global {

    public static final String IMG_PATH = "kinoost/images/";

    public static final String HOME_PATH = (!System.getenv("OPENSHIFT_DATA_DIR").isEmpty())
            ? System.getenv("OPENSHIFT_DATA_DIR")
            : System.getProperty("user.home");

    public static final String KINOPOISK_PREFIX = "kp_";

    public static final String WHATSONG_PREFIX = "ws_";

}
