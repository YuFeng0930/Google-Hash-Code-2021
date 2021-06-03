import java.util.*;
import java.util.stream.Stream;

class Path implements Iterable<Street> {
    ArrayList<Street> streets;

    Path(ArrayList<Street> streets) {
        this.streets = streets;
    }

    public Iterator<Street> iterator() {
        return streets.iterator();
    }

    public int size() {
        return streets.size();
    }

    public Street get(int i) {
        return streets.get(i);
    }

    public Stream<Street> stream() {
        return streets.stream();
    }

}