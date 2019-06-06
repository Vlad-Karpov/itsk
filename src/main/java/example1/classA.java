package example1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KARPOVVV on 20.07.17.
 */
public class classA {

    private Map<String, Map> map1 = new HashMap<String, Map>();

    public classA() {
    }

    public classA(Map<String, Map> map1) {
        this.map1 = map1;
    }

    public Map<String, Map> getMap1() {
        return map1;
    }

    public void setMap1(Map<String, Map> map1) {
        this.map1 = map1;
    }

    public Map put(String str, Map mp) {
        return map1.put(str, mp);
    }

    public Map get(String str) {
        return map1.get(str);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof classA)) return false;

        classA classA = (classA) o;

        if (map1 != null ? !map1.equals(classA.map1) : classA.map1 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return map1 != null ? map1.hashCode() : 0;
    }


}
