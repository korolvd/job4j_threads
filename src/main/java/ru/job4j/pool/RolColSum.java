package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    private static Sums sumLines(int[][] matrix, int cursor) {
        Sums rsl = new Sums();
        for (int i = 0; i < matrix.length; i++) {
            rsl.rowSum += matrix[cursor][i];
            rsl.colSum += matrix[i][cursor];
        }
        return rsl;
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = sumLines(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int cursor = i;
            sums[i] = CompletableFuture.supplyAsync(() -> sumLines(matrix, cursor)).get();
        }
        return sums;
    }
}
