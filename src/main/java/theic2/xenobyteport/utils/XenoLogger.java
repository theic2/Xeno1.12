package theic2.xenobyteport.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theic2.xenobyteport.api.Configuration;
import theic2.xenobyteport.api.Xeno;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class XenoLogger {

    private static Object logStr;

    static {
        init();
    }

    private static void init() {
        Object logger = null;
        switch (Configuration.INSTANCE.log) {
            case LOG4J:
                logger = LogManager.getLogger(Xeno.mod_name);
                break;
            case STDOUT:
                logger = System.out;
                break;
            case FD_STDOUT:
                logger = new PrintStream(new FileOutputStream(FileDescriptor.out), true);
                break;
            case NULL:
            default:
                logger = null;
                break;
        }
        logStr = logger;
    }

    public static void info(Object message) {
        log(Xeno.utils.nullHelper(message).replaceAll("\n", ""));
    }

    private static void log(String msg) {
        switch (Configuration.INSTANCE.log) {
            case LOG4J:
                ((Logger) logStr).info(msg);
                break;
            case STDOUT:
            case FD_STDOUT:
                ((PrintStream) logStr).println(msg);
                break;
            case NULL:
            default:
                break;
        }
    }
}