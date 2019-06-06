package sberteh;

import org.junit.Test;

public class Sber {

    public String qqq(String[] str) {
        String s = null;
        try {
            s = str[0];
            return s;
        } catch(Exception ex) {
            s += 1;
            return s;
        } finally {
            s += 1;
            return s;
        }
    }

    @Test
    public void test1() {
        System.out.println(qqq(new String[0]));
    }

    @Test
    public void test2() {
        System.out.println(5 * 4 % 3);
    }

}
