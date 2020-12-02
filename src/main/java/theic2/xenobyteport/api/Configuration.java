package theic2.xenobyteport.api;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public final class Configuration implements Xeno {
    public static final Configuration INSTANCE = new Configuration();
    public final boolean reflectEvents = false;
    public final LogKind log = LogKind.LOG4J;

    public enum LogKind {
        STDOUT, FD_STDOUT, LOG4J, NULL; // sun.misc.MessageUtils removed in Java 9+, so don`t use it :(
    }

    public File getConfig(Consumer<Boolean> applier) {
        File configDir = new File(new File(System.getProperty("user.home")), Configuration.class.getName()/*Random dir on each reobf. ...LOL...*/);
        final File configFile = new File(configDir, Xeno.class.getName());
        if (!configDir.exists()) {
            configDir.mkdirs();
            applier.accept(true);
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                applier.accept(true);
            } catch (IOException e) {
            }
        }
        return configFile;
    }
}
