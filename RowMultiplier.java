public class RowMultiplier implements Runnable {

    private final int[][] ThreadedMatrix;
    private int[][] m1;
    private int[][] m2;
    private final int row;

    public RowMultiplier(int[][] ThreadedMatrix, int[][] m1, int[][] m2, int row) {
        this.ThreadedMatrix = ThreadedMatrix;
        this.m1 = m1;
        this.m2 = m2;
        this.row = row;
    }

    @Override
    public void run() {
//        System.out.println("run is running");
        for (int i = 0; i < m2[0].length; i++) {
            ThreadedMatrix[row][i] = 0;
            for (int j = 0; j < m1[row].length; j++) {
                ThreadedMatrix[row][i] += m1[row][j] * m2[j][i];

            }

        }

    }

}