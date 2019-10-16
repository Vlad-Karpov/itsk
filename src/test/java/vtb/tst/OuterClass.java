package vtb.tst;

class OuterClass {
    private int q1;
    private String q2;
    private StaticInerClass staticInerClass = new StaticInerClass();

    String qq() {
        InerClass inerClass = new InerClass();
        inerClass.qqq();
        return q2;
    }
    String qq1() {
        return q2;
    }

    class InerClass {
        String qqq() {
            q2 = "hello" + staticInerClass.qqqq(OuterClass.this);
            return q2;
        }
        String qq1() {
            return OuterClass.this.qq1();
        }
    }

    static class StaticInerClass {
        String qqq() {
            return "static hello";
        }

        public String qqqq(OuterClass outerClass) {
            return outerClass.q2;
        }
//        public String qqqqq() {
//            return OuterClass.this.q2;
//        }
    }
}
