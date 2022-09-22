import java.util.Arrays;

public class BruteCollinearPoints {

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
        for (int i = 0; i < count; ++ i) newLines[i] = lines[i];
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

    public BruteCollinearPoints(Point[] points){
        checkPoints(points);
        this.points = points.clone();
        Arrays.sort(this.points);
        lines = new LineSegment[2];
        for(int i = 0; i < this.points.length; ++ i) {
            for(int j = i + 1; j < this.points.length; ++ j) {
                for(int k = j + 1; k < this.points.length; ++ k) {
                    for(int t = k + 1; t < this.points.length; ++ t) {
                        if(this.points[i].slopeTo(this.points[j]) == this.points[i].slopeTo(this.points[k]) &&
                                this.points[i].slopeTo(this.points[k]) == this.points[i].slopeTo(this.points[t])) {
                            this.enqueue(new LineSegment(this.points[i], this.points[t]));
                        }
                    }
                }
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