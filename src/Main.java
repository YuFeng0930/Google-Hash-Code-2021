import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int duration = Integer.parseInt(st.nextToken());
        int intersections = Integer.parseInt(st.nextToken());
        int streets = Integer.parseInt(st.nextToken());
        int cars = Integer.parseInt(st.nextToken());
        int points = Integer.parseInt(st.nextToken());

        HashMap<String, Street> strMap = new HashMap<>();
        ArrayList<Intersection> interList = new ArrayList<>();
        for (int i = 0; i < intersections; i++) {
            interList.add(new Intersection(i, new ArrayList<>(), new ArrayList<>()));
        }

        for (int i = 0; i < streets; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            int time = Integer.parseInt(st.nextToken());
            Street newStr = new Street(start, end, name, time);
            strMap.put(name, newStr);
            interList.get(start).addOut(newStr);
            interList.get(end).addIn(newStr);
        }

        ArrayList<Path> paths = new ArrayList<>();
        for (int i = 0; i < cars; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            ArrayList<Street> path = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                path.add(strMap.get(st.nextToken()));
            }
            Path pathObj = new Path(path);
            paths.add(pathObj);
        }
        roundRobinModPath(interList, paths, strMap, duration);
    }

    public static void roundRobin(ArrayList<Intersection> intersections) {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        pw.println(intersections.size());
        for (Intersection intersection : intersections) {
            pw.println(intersection.id);
            pw.println(intersection.in.size());
            for (Street street : intersection.in) {
                pw.println(street.name + " " + "1");
            }
        }
        pw.close();
    }

    public static void roundRobinModFreq(ArrayList<Intersection> intersections, ArrayList<Path> paths,
            HashMap<String, Street> strMap) {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        HashMap<Street, Integer> freq = new HashMap<>();
        for (Street street : strMap.values()) {
            freq.put(street, 0);
        }
        for (Path path : paths) {
            for (Street street : path) {
                freq.put(street, freq.get(street) + 1);
            }
        }
        int threshold = freq.values().stream().mapToInt(x -> x).sum() / freq.values().size();
        pw.println(intersections.size());
        for (Intersection intersection : intersections) {
            pw.println(intersection.id);
            pw.println(intersection.in.size());
            for (Street street : intersection.in) {
                if (freq.get(street) > threshold) {
                    pw.println(street.name + " " + "2");
                } else {
                    pw.println(street.name + " " + "1");
                }
            }
        }
        pw.close();
    }

    public static void roundRobinModPath(ArrayList<Intersection> intersections, ArrayList<Path> paths,
            HashMap<String, Street> strMap, int duration) {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        pw.println(intersections.size());
        HashMap<Integer, IntersectionPlus> intersectionMap = new HashMap<>();
        for (Intersection intersection : intersections) {
            intersectionMap.put(intersection.id, new IntersectionPlus(intersection));
        }
        for (Path path : paths) {
            int sum = path.stream().mapToInt(x -> x.time).sum();
            int cur = 0;
            for (int i = 0; i < path.size(); i++) {
                cur += path.get(i).time;
                Street str = path.get(i);
                intersectionMap.get(str.end).addGn(cur, str);
                intersectionMap.get(str.end).addHn(sum - cur, str);
            }
        }
        for (IntersectionPlus ip : intersectionMap.values()) {
            float threshold = ip.hn.values().stream().mapToInt(x -> x).sum() / ip.hn.size();
            List<Street> strs = ip.gn.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(x -> x.getKey())
                    .collect(Collectors.toList());
            Collections.reverse(strs);
            pw.println(ip.intersection.id);
            int counter = 0;
            StringBuilder sb = new StringBuilder();
            for (Street street : strs) {
                int sec = (int) (ip.hn.get(street) / threshold);
                if (sec > duration) {
                    sec = duration;
                }
                if (sec != 0) {
                    counter++;
                    sb.append("\n");
                    sb.append(street.name);
                    sb.append(" ");
                    sb.append(sec);
                }
            }
            if (counter == 0) {
                counter++;
                Street street = strs.get(0);
                sb.append("\n");
                sb.append(street.name);
                sb.append(" ");
                sb.append(1);
            }
            sb.append("\b");
            pw.println(counter);
            pw.println(sb.substring(1));
        }
        pw.close();
    }
}
