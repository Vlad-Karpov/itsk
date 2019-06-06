package sibintek;

import org.junit.Test;

/**
 * Какой будет вывод?
 */
public class test5 {
    class Supper {
        public String name = "Supper";
        public String getName() {
            return name;
        }
    }
    class  Sub extends Supper {
        public String name = "Sub";
    }
    @Test
    public void tst01() {
        Supper s = new Sub();
        System.out.println(s.name + " " + s.getName());
        System.out.println(((Sub)s).name + " " + ((Sub)s).getName());
    }
}
