package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class RolColSumTest {

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        int[][] expect = {
                {10, 28},
                {26, 32},
                {42, 36},
                {58, 40},
        };
        int[][] rsl = new int[matrix.length][2];
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        for (int i = 0; i < rsl.length; i++) {
            rsl[i][0] = sums[i].getRowSum();
            rsl[i][1] = sums[i].getColSum();
        }
        assertThat(rsl, is(expect));
    }
}