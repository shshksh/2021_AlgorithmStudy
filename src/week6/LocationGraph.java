package week6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LocationGraph {
    private final Map<String, Location> locations = new HashMap<>();

    public LocationGraph() {
        init();
    }

    public void init() {
        File file = new File("res/week6/alabama.txt");

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split("\t");
                locations.put(data[0], new Location(data));
            }
            sc.close();

            linkAllEdges();
        } catch (FileNotFoundException e) {
            System.out.println("No exist such file.");
        }
    }

    private void linkAllEdges() {
        File file = new File("res/week6/roadList2.txt");

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] edge = sc.nextLine().split("\t");
                linkLocation(edge[0], edge[1]);
                linkLocation(edge[1], edge[0]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Edges linking error.");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void linkLocation(String srcName, String dstName) throws CloneNotSupportedException {
        Location src = locations.get(srcName);
        Location dst = locations.get(dstName);
        src.addAdjLocation(dst.clone(src));
    }

    public void bfs(String target) {
        Location src = locations.get(target);
        if (src == null) {
            System.out.println("Wrong location");
            return;
        }
        Queue<Location> q = new LinkedList<>();
        Set<Location> visited = new HashSet<>();
        q.offer(src);
        visited.add(src);

        for (int i = 1; i <= 10 && !q.isEmpty(); i++) {
            System.out.println("Hop " + i + ":");
            int qSize = q.size();
            for (int j = 0; j < qSize; j++) {
                src = q.poll();
                for (Location location : src.getAdj()) {
                    Location next = locations.get(location.getPlace());
                    if (!visited.contains(next)) {
                        q.offer(next);
                        visited.add(next);
                        System.out.println("\t" + next.getPlace());
                    }
                }
            }
        }
    }

    public void dfs(String target) {
        Location location = locations.get(target);
        if (location == null) {
            System.out.println("Wrong location");
            return;
        }
        dfs(location, new HashSet<>());
    }

    private void dfs(Location location, HashSet<Location> visited) {
        visited.add(location);
        System.out.println(location);
        for (Location adj : location.getAdj()) {
            Location next = locations.get(adj.getPlace());
            if (!visited.contains(next)) {
                dfs(next, visited);
            }
        }
    }
}
