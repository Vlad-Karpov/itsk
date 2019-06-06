package iteco;

import org.junit.Test;

public class Iteco {

    @Test
    public void test1() {
        String[] str = {"df", "4", "7.8", "d", "100", "3.456"};
        System.out.println(mn(str));
    }

    private Double mn(String[] args) {
        Double s = 0.0d;
        for (int i = 0; i < args.length; i++) {
            Double darg = 0.0d;
            try {
                darg = Double.parseDouble(args[i]);
            } catch (Exception e) {
            }
            s = s + darg;
        }
        return s;
    }

}
