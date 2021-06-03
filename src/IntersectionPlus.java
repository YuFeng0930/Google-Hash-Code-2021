import java.util.*;

class IntersectionPlus {
    Intersection intersection;
    HashMap<Street, Integer> gn;
    HashMap<Street, Integer> hn;

    IntersectionPlus(Intersection intersection) {
        this.intersection = intersection;
        gn = new HashMap<>();
        hn = new HashMap<>();
        for (Street street : intersection.in) {
            gn.put(street, 0);
            hn.put(street, 0);
        }
    }

    public void addGn(int n, Street street) {
        gn.put(street, gn.get(street) + n);
    }

    public void addHn(int n, Street street) {
        hn.put(street, hn.get(street) + n);
    }
}