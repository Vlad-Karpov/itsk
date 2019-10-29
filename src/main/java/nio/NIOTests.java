package nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOTests {
    public static class QQQ0001 {
        public static void main(String[] args) throws IOException {
            Path path = Paths.get(".");
            System.out.println(path.toAbsolutePath().toString());
            System.out.println(path.toAbsolutePath().normalize().toString());
            System.out.println(path.normalize().toUri().toString());
            System.out.println(path.toRealPath().toString());
            System.out.println(path.resolve("src").toString());
            System.out.println(path.resolve("qqqqqqq").toString());
            System.out.println(Paths.get(".").relativize(path.resolve("qqqqqqq").resolve("rrrrr")).toString());

            System.out.println(Files.exists(path.resolve("src")));

        }

    }
}
