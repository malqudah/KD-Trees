/* *****************************************************************************
 *  Name:    Mohammad Alqudah
 *  NetID:   malqudah
 *  Precept: P05
 *
 *  Description: models a kd tree data structure through using a node to rep
 * each point in the tree; each node is comprised of a point, value, bounding
 * rectangle, pointer to the left/bottom subtree, and pointer to the right/top
 * subtree (subtrees are determined on an x or y basis depending on the
 * orientation of a node). points are put in the kd tree similarly, depending
 * on the orientation of a parent node which determines the direction it is
 * added in. given a query rectangle or a query point, kd tree st can find
 * the nearest point neighbor to the given point, as well as all of the points
 * that are inside within the given rectangle.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class KdTreeST<Value> {

    // Node root
    private Node root;

    // size of the kd tree
    private int size;

    private int counter;


    // construct an empty symbol table of points
    public KdTreeST() {
        root = null;
        size = 0;
        counter = 0;
    }

    // a node is comprised of the point, the value of it, the bounding rectangle
    // corresponding to the point, the left/bottom subtree node, and top/right
    // subtree node
    private class Node {

        // the point
        private final Point2D p;
        // the symbol table maps the point to this value
        private Value val;
        // the axis-aligned rectangle corresponding to this node
        private final RectHV rect;
        // the left/bottom subtree
        private Node lb;
        // the right/top subtree
        private Node rt;


        // creates Node object
        public Node(Point2D p, Value val, RectHV rect) {
            this.p = p;
            this.val = val;
            this.rect = rect;
        }
    }


    // private helper method for illegal arguments
    private void illegalPt(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point is null");
        }
    }

    // private helper method for illegal arguments
    private void illegalVal(Value v) {
        if (v == null) {
            throw new IllegalArgumentException("Value is null");
        }
    }


    // is the symbol table empty?
    public boolean isEmpty() {
        return size == 0;
    }


    // number of points
    public int size() {
        if (root == null) {
            return 0;
        }
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        illegalPt(p);
        illegalVal(val);
        double negIn = Double.NEGATIVE_INFINITY;
        double posIn = Double.POSITIVE_INFINITY;
        RectHV rect = new RectHV(negIn, negIn, posIn, posIn);
        if (!contains(p)) {
            size++;
        }
        root = put(root, p, val, true, rect);
    }

    // private helper method for put
    private Node put(Node x, Point2D point, Value val, boolean vertical,
                     RectHV rect) {

        if (x == null) {
            return new Node(point, val, rect);
        }

        if (point.equals(x.p)) {
            x.val = val;
        }
        // if point is to left and parent is vertical
        else if (point.x() < x.p.x() && vertical) {

            RectHV newRect = new RectHV(
                    rect.xmin(), rect.ymin(), x.p.x(), rect.ymax());
            x.lb = put(x.lb, point, val, false, newRect);
        }

        // if point is to the right and parent is vertical
        else if (point.x() >= x.p.x() && vertical) {

            RectHV newRect = new RectHV(
                    x.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            x.rt = put(x.rt, point, val, false, newRect);
        }

        // if point is up and parent is horizontal
        else if (point.y() >= x.p.y() && !vertical) {

            RectHV newRect = new RectHV(
                    rect.xmin(), x.p.y(), rect.xmax(), rect.ymax());
            x.rt = put(x.rt, point, val, true, newRect);
        }

        // if point is down and parent is horizontal
        else if (point.y() < x.p.y() && !vertical) {

            RectHV newRect = new RectHV(
                    rect.xmin(), rect.ymin(), rect.xmax(), x.p.y());
            x.lb = put(x.lb, point, val, true, newRect);
        }
        return x;
    }

    //
    // value associated with point p
    public Value get(Point2D p) {
        illegalPt(p);
        return get(root, p, true);
    }

    // private helper method for get
    private Value get(Node x, Point2D point, boolean vertical) {
        illegalPt(point);
        if (x == null) {
            return null;
        }
        if (x.p.equals(point)) {
            return x.val;
        }
        // if point is to left and parent is vertical
        if (point.x() < x.p.x() && vertical) {
            return get(x.lb, point, false);
        }

        // if point is to the right and parent is vertical
        else if (point.x() >= x.p.x() && vertical) {
            return get(x.rt, point, false);
        }

        // if point is up and parent is horizontal
        else if (point.y() >= x.p.y() && !vertical) {
            return get(x.rt, point, true);
        }

        // if point is down and parent is horizontal
        else {
            return get(x.lb, point, true);
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        illegalPt(p);
        return (get(p) != null);
    }
    //
    // // all points in the symbol table
    // public Iterable<Point2D> points() {
    //     // creates queue to keep track of points
    //     Queue<Point2D> points = new Queue<>();
    //     if (isEmpty()) {
    //         return points;
    //     }
    //     // creates queue to keep track of the nodes
    //     Queue<Node> nodes = new Queue<>();
    //     nodes.enqueue(root);
    //     // while the queue of nodes is not empty, deque a node and add the point
    //     // node dequeued to the points queue; conduct respective null checks.
    //     while (!nodes.isEmpty()) {
    //         Node temp = nodes.dequeue();
    //         points.enqueue(temp.p);
    //         if (temp.lb != null) {
    //             nodes.enqueue(temp.lb);
    //
    //         }
    //         if (temp.rt != null) {
    //             nodes.enqueue(temp.rt);
    //         }
    //     }
    //     return points;
    // }
    //
    //
    // // all points that are inside the rectangle (or on the boundary)
    // public Iterable<Point2D> range(RectHV rect) {
    //     if (rect == null) {
    //         throw new IllegalArgumentException("Rectangle is null");
    //     }
    //     Queue<Point2D> queue = new Queue<>();
    //     range(rect, queue, root);
    //     return queue;
    // }
    //
    // // private helper method for the range function
    // private void range(RectHV rect, Queue<Point2D> queue, Node x) {
    //     if (x == null) {
    //         return;
    //     }
    //     if (!rect.intersects(x.rect)) {
    //         return;
    //     }
    //     if (rect.contains(x.p)) {
    //         queue.enqueue(x.p);
    //     }
    //     range(rect, queue, x.lb);
    //     range(rect, queue, x.rt);
    // }
    //

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        illegalPt(p);
        if (isEmpty()) {
            return null;
        }
        return nearest(p, root.p, root, true);
    }

    // private helper method for the nearest method
    private Point2D nearest(Point2D point, Point2D champion, Node x,
                            boolean vertical) {
        counter++;
        illegalPt(point);
        illegalPt(champion);
        if (x == null) {
            return champion;
        }
        // avoids pruning
        if (x.rect.distanceSquaredTo(point)
                > champion.distanceSquaredTo(point)) {
            return champion;
        }

        if (point.distanceSquaredTo(x.p) <
                champion.distanceSquaredTo(point)) {
            champion = x.p;
        }
        // uses an int cmp to determine whether to go left or right in a given
        // subtree depending on the orientation of a parent node
        int cmp = 0;

        if (vertical && point.x() < x.p.x()) {
            cmp = -1;
        }
        if (vertical && point.x() >= x.p.x()) {
            cmp = 1;
        }
        if (!vertical && point.y() < x.p.y()) {
            cmp = -1;
        }
        if (!vertical && point.y() >= x.p.y()) {
            cmp = 1;
        }

        if (cmp < 0) {
            champion = nearest(point, champion, x.lb, !vertical);
            champion = nearest(point, champion, x.rt, !vertical);
        }

        if (cmp > 0) {
            champion = nearest(point, champion, x.rt, !vertical);
            champion = nearest(point, champion, x.lb, !vertical);
        }

        return champion;
    }

    // unit testing (required)
    public static void main(String[] args) {

        // KdTreeST<Integer> testOne = new KdTreeST<>();
        // Point2D pointOne = new Point2D(0.7, 0.2);
        // Point2D pointTwo = new Point2D(0.5, 0.4);
        // Point2D pointThree = new Point2D(0.2, 0.3);
        // Point2D pointFour = new Point2D(0.4, 0.7);
        // Point2D pointFive = new Point2D(0.9, 0.6);
        //
        // testOne.put(pointOne, 2);
        // testOne.put(pointTwo, 1);
        // testOne.put(pointThree, 5);
        // testOne.put(pointFour, 7);
        // testOne.put(pointFive, 0);
        //
        // Point2D query = new Point2D(0.19, 0.74);
        // StdOut.println("Points: " + testOne.points());
        // StdOut.println("Nearest to query: " + testOne.nearest(query));
        //
        // RectHV rect = new RectHV(0.265, 0.19, 0.422, 0.888);
        // StdOut.println("Points in rect range: " + testOne.range(rect));
        //
        // StdOut.println("Size: " + testOne.size());
        //
        // Point2D zero = new Point2D(0, 0);
        // StdOut.println("Does it contain 0, 0: " + testOne.contains(zero));
        // StdOut.println("is it empty: " + testOne.isEmpty());
        // StdOut.println("Value of first point: " + testOne.get(pointOne));
        //

        // timing
        // double x = StdRandom.uniform(0.0, 1.0);
        // double y = StdRandom.uniform(0.0, 1.0);
        KdTreeST<Integer> testOne = new KdTreeST<>();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D pointOne = new Point2D(x, y);
            int value = StdRandom.uniform(0, 1);
            testOne.put(pointOne, value);
        }
        StopwatchCPU stopOne = new StopwatchCPU();
        double x = StdRandom.uniform(0, 1);
        double y = StdRandom.uniform(0, 1);
        Point2D testPt = new Point2D(x, y);
        testOne.nearest(testPt);
        StdOut.println(stopOne.elapsedTime());
        StdOut.println(testOne.counter);
    }
}

