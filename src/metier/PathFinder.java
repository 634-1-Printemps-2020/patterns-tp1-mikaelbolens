package metier;


import java.util.*;


class PathFinder {
    private Map<Point, Point> tableRoutage;
    private Map<Point, List<Point>> fullMap;

    PathFinder(Map<Point, List<Point>> fullMap, Point position) {
        this.fullMap = fullMap;
        djikstra(position);
    }

    private void djikstra(Point from){
        List<Point> marked = new ArrayList<>();
        marked.add(from);

        PriorityQueue<Point> open = new PriorityQueue<>();
        open.add(from);

        tableRoutage = new HashMap<>();
        tableRoutage.put(from, null);

        while (open.size() > 0){
            Point current = open.poll();
            for (Point neighbour: this.fullMap.get(current)) {
                if (!marked.contains(neighbour)){
                    open.add(neighbour);
                    marked.add(neighbour);
                    tableRoutage.put(neighbour, current);
                }
            }
        }

    }
    boolean passagePossible(Point p){
        return this.tableRoutage.containsKey(p);
    }

    boolean passagePossible(Point from, Point to){
        return this.tableRoutage.containsKey(from) && this.tableRoutage.containsKey(to);
    }
}