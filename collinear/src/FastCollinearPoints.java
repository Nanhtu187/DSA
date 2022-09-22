import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private int count = 0;
    private LineSegment[] lines;

    private final Point[] points;

    private void checkPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        for (int i = 0; i < n; ++ i) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point is null");
            }
            for(int j = i + 1; j < n; ++ j) {
                if(points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }


    private void resize(int capacity) {
        LineSegment[] newLines = new LineSegment[capacity];
        for (int i = 0; i < count; ++ i) {
            newLines[i] = lines[i];
        }
        lines = newLines;

    }
    private void enqueue(LineSegment item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        if(count == lines.length) {
            resize(lines.length * 2);
        }
        lines[count++] = item;

    }

    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        lines = new LineSegment[2];
        Point[] newPoints = Arrays.copyOf(points, points.length);
        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(newPoints);
        for (Point point: newPoints) {
            Arrays.sort(this.points);
            Arrays.sort(this.points, point.slopeOrder());
            double prev = point.slopeTo(this.points[0]);
            LinkedList<Point> lineSeg = new LinkedList<>();
            for (int i = 0; i < this.points.length; ++ i) {
                if(point.slopeTo(this.points[i]) != prev) {
                    if(lineSeg.size() >= 3 && point.compareTo(lineSeg.getFirst()) < 0) {
                        this.enqueue(new LineSegment(point, lineSeg.getLast()));
                    }
                    lineSeg.clear();
                }
                prev = point.slopeTo(this.points[i]);
                lineSeg.add(this.points[i]);
            }
            if(lineSeg.size() >= 3 && point.compareTo(lineSeg.getFirst()) < 0) {
                this.enqueue(new LineSegment(point, lineSeg.getLast()));
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lines, count);
    }
}