package xorfile;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XorFile {

    @Test
    public void xorFile() throws IOException {
        String password = "Это очень крутой пароль, едрентать!";
        FileInputStream in = new FileInputStream("c:\\Users\\User\\IdeaProjects\\gitHub\\msa-mock.zip");
        FileOutputStream out = new FileOutputStream("c:\\Users\\User\\IdeaProjects\\gitHub\\msa-mock.dat");
        int r;
        int c = 0;
        while ((r = in.read()) >= 0) {
            r = r ^ password.codePointAt(c);
            c++;
            if (c == password.length()) {
                c = 0;
            }
            out.write(r);
        }
        out.flush();
        out.close();
        in.close();
    }

}
