//import java.text.DecimalFormat;
//import java.text.NumberFormat;
import java.util.*;
//import java.util.*;
import java.lang.*;
//import java.io.*;
//import java.util.Date;
//import java.util.Random;

public class MatrixMultiplication {

    public static int[][] ThreadedMatrixMultiplication(int[][] m1, int[][] m2, int x, int y, int n, int noOfThreads){
        int[][] ThreadedMatrix = new int[x][n];  //= Matrix.multiply(m1, m2);
        List<Thread> threads = new ArrayList<>(noOfThreads);
        int rows1 = m1.length;
        for (int i = 0; i < rows1; i++) {
            RowMultiplier task = new RowMultiplier(ThreadedMatrix, m1, m2, i);
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
            if (threads.size() % noOfThreads == 0) {
                for (Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                threads.clear();
            }
        }
        return ThreadedMatrix;
    }


    public static int[][] SequentialMatrixMultiplication(int[][] m1,int[][] m2,int x,int y,int n){
        int[][] result = new int[x][n];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                result[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return result;
    }
    private static void printMatrix(int[][] sequentialMatrix) {
        int x = sequentialMatrix.length;
        int y = sequentialMatrix[0].length;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(sequentialMatrix[i][j]+" ");
            }
            System.out.println("");
        }
    }


    public static int[][] GenerateMatrix(int rows, int columns){
        int[][] matrix = new int[rows][columns];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextInt(100) * 10;
            }
        }
        return matrix;
    }

    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter dimension of 1st matrix (space seperated):");
        int x= sc.nextInt();
        int y= sc.nextInt();
        System.out.println("Enter dimension of 2nd matrix (space seperated):");
        int m= sc.nextInt();
        int n= sc.nextInt();
        if(y!=m){
            System.out.println("can not multiply such matrices.");
            return;
        }
//
        int[][] m1;
        int[][] m2;
        m1 = GenerateMatrix(x,y);
        m2 = GenerateMatrix(m,n);

        int[] arr = new int[15];
        int j = 3;
        for(int i = 0; i < 15; i++){
            arr[i] = j;
            j += x/15;
        }
        arr[14] = x;


        // System.out.println("Sequential:"+duration+" milli seconds");
        float[] speedUp;
        speedUp = scriptRunner(m1, m2, x, y, n, arr);
        for (int i = 0; i < speedUp.length; i++) {
            System.out.println(speedUp[i]);
        }
    }

    private static float[] scriptRunner(int[][] m1, int[][] m2, int x, int y, int n, int[] arr) {
//        long[] SequentialResults = new long[arr.length];
//        long[] ThreadedResults = new long[arr.length];
        float [] speedUp = new float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int[][] SequentialMatrix = new int[x][n];
            long startTime = System.nanoTime();
            SequentialMatrix = SequentialMatrixMultiplication(m1,m2,x,y,n);
            float duration = (System.nanoTime() - startTime);
            // printMatrix(SequentialMatrix);

            int[][] ThreadedMatrix = new int[x][n];
            startTime = System.nanoTime();
            ThreadedMatrix = ThreadedMatrixMultiplication(m1,m2,x,y,n,arr[i]);
            float duration1 = (System.nanoTime() - startTime);
            // ThreadedResults[i] = duration;
            // printMatrix(ThreadedMatrix);
            //System.out.println(duration1+" "+duration);
            System.out.println(duration/duration1);
            speedUp[i] = duration/duration1;
            //System.out.println("Threaded:"+duration+" milli seconds");
        }

        return speedUp;
    }

}
