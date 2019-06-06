package example1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KARPOVVV on 20.07.17.
 */
public class classB {
    private List<Map<String, String>> lst1 = new ArrayList<Map<String, String>>();

    public classB() {
    }

    public classB(List<Map<String, String>> lst1) {
        this.lst1 = lst1;
    }

    public List<Map<String, String>> getLst1() {
        return lst1;
    }

    public void setLst1(List<Map<String, String>> lst1) {
        this.lst1 = lst1;
    }

    public void add(Map<String, String> mp) {
        lst1.add(mp);
    }

    public boolean remove(Map<String, String> mp) {
        return lst1.remove(mp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof classB)) return false;

        classB classB = (classB) o;

        if (lst1 != null ? !lst1.equals(classB.lst1) : classB.lst1 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return lst1 != null ? lst1.hashCode() : 0;
    }

}
