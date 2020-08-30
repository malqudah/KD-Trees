/* *****************************************************************************
 *  Name:    Mohammad Alqudah
 *  NetID:   malqudah
 *  Precept: P05
 *
 *  Description: uses a left leaning red black binary search tree to model
 * a symbol table of Point2D objects. given a rectangle, keeps track of
 * the points that are within it; in addition, finds the nearest neighbor of a
 * given point on the plane.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class PointST<Value> {

    // red black bst where all the points and their values are stored
    private final RedBlackBST<Point2D, Value> bstOne;

    private int counter;


    // construct an empty symbol table of points
    public PointST() {
        bstOne = new RedBlackBST<>();
        counter = 0;
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
        return bstOne.isEmpty();
    }


    // number of points
    public int size() {
        return bstOne.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        illegalPt(p);
        illegalVal(val);
        bstOne.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        illegalPt(p);
        return bstOne.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        illegalPt(p);
        return bstOne.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return bstOne.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle is null");
        }
        Stack<Point2D> stack = new Stack<>();
        for (Point2D p : bstOne.keys()) {
            if (rect.contains(p)) {
                stack.push(p);
            }
        }
        return stack;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        counter++;
        illegalPt(p);
        if (bstOne.isEmpty()) {
            return null;
        }
        if (bstOne.contains(p)) {
            return p;
        }
        Point2D champion = bstOne.max();
        double nearestDist = p.distanceSquaredTo(champion);
        for (Point2D q : bstOne.keys()) {
            if (q.distanceSquaredTo(p) < nearestDist) {
                champion = q;
                nearestDist = q.distanceSquaredTo(p);
            }
        }
        return champion;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // PointST<Integer> testOne = new PointST<>();
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
        // Point2D zero = new Point2D(0, 0);
        // StdOut.println("Does it contain 0, 0: " + testOne.contains(zero));
        // StdOut.println("Value of first point: " + testOne.get(pointOne));
        // StdOut.println("Is it empty: " + testOne.isEmpty());
        // StdOut.println("Point nearest to origin: " + testOne.nearest(zero));
        //
        // RectHV rect = new RectHV(0.265, 0.19, 0.422, 0.888);
        // StdOut.println("Points inside query rect: " + testOne.range(rect));
        // StdOut.println("points: " + testOne.points());
        // StdOut.println("Size: " + testOne.size());


        PointST<Integer> testOne = new PointST<>();
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
