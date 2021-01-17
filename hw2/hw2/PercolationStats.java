package hw2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;
import java.util.List;

public class PercolationStats {

    private int N, T;
    private PercolationFactory pf;
    private double probability;
    private int counter;
    private int target;
    private List<Double> samples;
    /**
     * perform T independent experiments on an N-by-N grid
     * @param N     Side length of grid
     * @param T     Experiment times
     * @param pf    Fatory to create Percolation instance
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        this.pf = pf;
        probability = StdRandom.uniform(0.5, 1.0);
        counter = 0;
        target = (int) Math.floor(N * N * probability);
        samples = new LinkedList<>();

        testTTimes();
//        testOnce();
    }

    private void testOnce() {
        int row, col;
        Percolation canvas = pf.make(N);
        while (counter < target && !canvas.percolates()) {
            row = StdRandom.uniform(0, N);
            col = StdRandom.uniform(0, N);
            if (!canvas.isOpen(row, col)) {
                canvas.open(row, col);
                counter += 1;
//                System.out.println(row + " " + col);
            }
        }
        if (canvas.percolates()) {
//            System.out.printf("probability: %s target: %s percolates! \n", probability, target);
            samples.add(probability);
        }
    }

    private void testTTimes() {
        for (int i = 0; i < T; i++) {
            testOnce();
            probability = StdRandom.uniform(0.5, 1.0);
            target = (int) Math.floor(N * N * probability);
            counter = 0;
        }
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        if (samples.size() == 0) {
            return 0.0;
        }
        double total = 0.0;
        for (double tmp : samples) {
            total += tmp;
        }
        return total / samples.size();
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        double avg = mean();
        double sumOfDtSquare = 0.0;
        for (double tmp : samples) {
            sumOfDtSquare += Math.pow(tmp - avg, 2);
        }
        double variance = sumOfDtSquare / (T - 1);
        return Math.sqrt(variance);
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLow() {
        double mean = mean();
        double stdDev = stddev();
        return mean - 1.96 * stdDev / Math.sqrt(T);
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHigh() {
        double mean = mean();
        double stdDev = stddev();
        return mean + 1.96 * stdDev / Math.sqrt(T);
    }

}
