package design.patterns.structural;

/**
 * Шаблон мост (англ. Bridge) — структурный шаблон проектирования, используемый в проектировании программного
 * обеспечения чтобы «разделять абстракцию и реализацию так, чтобы они могли изменяться независимо». Шаблон мост
 * использует инкапсуляцию, агрегирование и может использовать наследование для того, чтобы разделить ответственность
 * между классами.
 */
public class BridgePattern {

    interface Drawer {
        void drawCircle(int x, int y, int radius);
    }

    static class SmallCircleDrawer implements Drawer{

        static final double radiusMultiplier = 0.25;

        @Override
        public void drawCircle(int x, int y, int radius) {
            System.out.println("Small circle center = " + x + "," + y + " radius = " + radius*radiusMultiplier);
        }

    }

    static class LargeCircleDrawer implements Drawer{

        public static final int radiusMultiplier = 10;

        @Override
        public void drawCircle(int x, int y, int radius) {
            System.out.println("Large circle center = " + x + "," + y + " radius = " + radius*radiusMultiplier);
        }

    }

    static abstract class Shape {

        protected Drawer drawer;

        protected Shape(Drawer drawer){
            this.drawer = drawer;
        }

        public abstract void draw();

        public abstract void enlargeRadius(int multiplier);

    }

    static class Circle extends Shape{

        private int x;

        private int y;

        private int radius;

        public Circle(int x, int y, int radius, Drawer drawer) {
            super(drawer);
            setX(x);
            setY(y);
            setRadius(radius);
        }

        @Override
        public void draw() {
            drawer.drawCircle(x, y, radius);
        }

        @Override
        public void enlargeRadius(int multiplier) {
            radius *= multiplier;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getRadius() {
            return radius;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

    }


    /*
     * Класс, показывающий работу шаблона проектирования "Мост".
     * */
    public static void main (String [] args){
        Shape [] shapes = {
                new Circle(5,10,10, new LargeCircleDrawer()),
                new Circle(20,30,100, new SmallCircleDrawer())};

        for (Shape next : shapes){
            next.draw();
        }
    }


}
