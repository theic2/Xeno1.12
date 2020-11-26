package theic2.xenobyteport.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theic2.xenobyteport.api.Xeno;

public class XenoLogger {

    private static Logger log = LogManager.getLogger(Xeno.mod_name);

    public static void info(Object message) {
        log.info(Xeno.utils.nullHelper(message).replaceAll("\n", ""));
    }

}