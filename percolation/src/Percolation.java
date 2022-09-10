import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class Percolation {
    private WeightedQuickUnionUF unf;
    private WeightedQuickUnionUF unff;
    private int cnt = 0;
    private int N;
    private boolean[][] open;
    private boolean isPercolated;
    private int decode(int row, int col) {
        return (row - 1) * N + col;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        unf = new WeightedQuickUnionUF(n * n + 2);
        unff = new WeightedQuickUnionUF(n * n + 2);
        open = new boolean[n][n];
        N = n;
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N) {
            throw new IllegalArgumentException("outside");
        }
        if (!isOpen(row, col)) {
            ++cnt;
            open[row - 1][col - 1] = true;
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    if (Math.abs(i) + Math.abs(j) == 1) {
                        if (row + i > 0 && row + i <= N && col + j > 0 && col + j <= N) {
                            if (isOpen(row + i, col + j)) {
                                unf.union(decode(row, col), decode(row + i, col + j));
                                unff.union(decode(row, col), decode(row + i, col + j));
                            }
                        }
                    }
                }
            }
            if (row == 1) {
                unf.union(decode(row, col), 0);
                unff.union(decode(row, col), 0);
            }
            if (row == N) {
                unff.union(decode(row, col), N * N + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N) {
            throw new IllegalArgumentException("outside");
        }
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N) {
            throw new IllegalArgumentException("outside");
        }
        int root = unf.find(decode(row, col));
        return unf.connected(root, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return cnt;
    }

    // does the system percolate?
    public boolean percolates() {
        return unff.connected(0, N * N + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}