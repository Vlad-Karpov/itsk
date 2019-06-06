package example3;

/**
 * Created by KARPOVVV on 20.07.17.
 */
public class SomeClass {

    RetObj rto = new RetObj();

    public RetObj createUser(String theStr) {
        try {
            rto.log = theStr;
            if (rto.log.equals("1")) {
                throw new Exception("fffff");
            }
            return rto;
        } catch (Exception e) {
            rto.log = "2";
            return rto;
        }
    }

}
