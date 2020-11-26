package theic2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Commenter {
    public static void addComment(Path inp, Path outp, String mainClass) throws IOException {
        try (ZipInputStream in = new ZipInputStream(Files.newInputStream(inp));
             ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(outp, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE))) {
            out.setComment(mainClass);
            ZipEntry e;
            while ((e = in.getNextEntry()) != null) {
                if (e.isDirectory()) continue;
                ZipEntry zentr = new ZipEntry(e.getName());
                e.setTime(0);
                out.putNextEntry(zentr);
                transfer(in, out);
            }
        }
    }

    public static long transfer(InputStream in, OutputStream out) throws IOException {
        Objects.requireNonNull(in, "in");
        Objects.requireNonNull(out, "out");
        long transferred = 0;
        byte[] buffer = new byte[8192];
        int read;
        while ((read = in.read(buffer, 0, 8192)) >= 0) {
            out.write(buffer, 0, read);
            transferred += read;
        }
        return transferred;
    }
}
