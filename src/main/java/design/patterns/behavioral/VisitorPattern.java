package design.patterns.behavioral;

/**
 * Посетитель (англ. visitor) — поведенческий шаблон проектирования, описывающий операцию, которая выполняется над
 * объектами других классов. При изменении visitor нет необходимости изменять обслуживаемые классы.
 *
 * Шаблон демонстрирует классический приём восстановления информации о потерянных типах, не прибегая к понижающему
 * приведению типов при помощи двойной диспетчеризации.
 *
 * Необходимо сделать какие-то несвязные операции над рядом объектов, но нужно избежать загрязнения их кода.
 * И нет возможности или желания запрашивать тип каждого узла и осуществлять приведение указателя к правильному типу,
 * прежде чем выполнить нужную операцию.
 *
 *  1. Добавьте метод accept(Visitor) в иерархию «элемент».
 *  2. Создайте базовый класс Visitor и определите методы visit() для каждого типа элемента.
 *  3. Создайте производные классы Visitor для каждой операции, исполняемой над элементами.
 *  4. Клиент создаёт объект Visitor и передаёт его в вызываемый метод accept().
 *
 */
public class VisitorPattern {

    interface Visitor {
        void visit ( Point2d p );
        void visit ( Point3d p );
    }

    static abstract class Point {
        public abstract void accept ( Visitor v );
        protected double metric = -1;
    }

    static class Point2d extends Point {

        private double x;
        private double y;

        public Point2d ( double x, double y ) {
            this.x = x;
            this.y = y;
        }

        public void accept ( Visitor v ) {
            v.visit( this );
        }

    }

    static class Point3d extends Point {

        private double x;
        private double y;
        private double z;

        public Point3d ( double x, double y, double z ) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void accept ( Visitor v ) {
            v.visit( this );
        }

    }

    static class Euclid implements Visitor {
        public void visit ( Point2d p ) {
            p.metric = Math.sqrt( p.x*p.x + p.y*p.y );
        }
        public void visit ( Point3d p ) {
            p.metric = Math.sqrt( p.x*p.x + p.y*p.y + p.z*p.z ) ;
        }
    }

    static class Chebyshev implements Visitor {
        public void visit ( Point2d p ) {
            double ax = Math.abs( p.x );
            double ay = Math.abs( p.y );
            p.metric = ax>ay ? ax : ay;
        }
        public void visit ( Point3d p ) {
            double ax = Math.abs( p.x );
            double ay = Math.abs( p.y );
            double az = Math.abs( p.z );
            double max = ax>ay ? ax : ay;
            if ( max<az ) max = az;
            p.metric = max;
        }
    }

    public static void main ( String [] args ) {
        Point p = new Point2d( 1, 2 );
        Visitor v = new Chebyshev();
        p.accept( v );
        System.out.println( p.metric );
        v = new Euclid();
        p.accept( v );
        System.out.println( p.metric );
    }


}
