import java.util.*;

class Intersection {
    int id;
    ArrayList<Street> in;
    ArrayList<Street> out;

    Intersection(int id, ArrayList<Street> in, ArrayList<Street> out) {
        this.id = id;
        this.in = in;
        this.out = out;
    }

    public void addIn(Street street) {
        in.add(street);
    }

    public void addOut(Street street) {
        out.add(street);
    }
}