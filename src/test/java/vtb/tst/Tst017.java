package vtb.tst;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Что будет выведено в консоль
 *          getSalary, getName, getSurname
 *          getSalary, getName, getSurname, все public методы класса Object
 *          getSalary, getSurname, все public методы класса Object
 *          getSalary, getName, getSurname, getName, getSurname
 *          getSalary, getName, getSurname, getName, getSurname, все public методы класса Object
 *
 *
 *  .class.getMethods() - все public методы, в том числе и отнаследованные
 *  .class.getDeclaredMethods() - все продекларированные методы в класса с любым уровнем доступа, но не наследованные
 */
public class Tst017 {

    @Test
    public void tst() {
        //Employee.class.getDeclaredMethods();
        for(Method method : Employee.class.getMethods()) {
            System.out.println(method.getName());
        }
    }

    static class Person {
        protected void getName() {}
        public void getSurname() {}
    }

    static class Employee extends Person {
        public void getSalary() {}
        @Override
        protected void getName() {}
        @Override
        public void getSurname() {}
    }

}
