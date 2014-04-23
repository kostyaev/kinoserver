package ru.cybern.kinoserver.parsers;

import java.util.Map;

public class Global {

    public static final String IMG_PATH = "/kinoost/images/";

    private static final Map<String, String> env = System.getenv();

    public static final String HOME_PATH =
            (env.containsKey("OPENSHIFT_DATA_DIR")) ? env.get("OPENSHIFT_DATA_DIR") : System.getProperty("user.home");

    public static final String KINOPOISK_PREFIX = "kp_";

    public static final String WHATSONG_PREFIX = "ws_";

    public static final String SOUNDTRACKNET_PREFIX = "st_";


}
