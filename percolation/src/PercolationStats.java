import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

import java.lang.IllegalArgumentException;

public class PercolationStats {
    private double[] tries;
    private double trialsCount;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        trialsCount = trials;
        tries = new double[trials];
        for (int i = 0; i < trials; ++ i) {
            Percolation pco = new Percolation(n);
            while (!pco.percolates()) {
                int u = StdRandom.uniformInt(1, n + 1);
                int v = StdRandom.uniformInt(1, n + 1);
                pco.open(u, v);
            }
            tries[i] = (double) pco.numberOfOpenSites() / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(tries);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(tries);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trialsCount);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trialsCount);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 200, trials = 100;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        PercolationStats perco = new PercolationStats(n, trials);
        StdOut.println("mean = " + perco.mean());
        StdOut.println("stddev = " + perco.stddev());
        StdOut.println("95% confidence interval = " + "[" + perco.confidenceLo() + ", " + perco.confidenceHi() + "]");
    }

}