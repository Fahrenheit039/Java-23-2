import java.util.*;

public class contest_29_09_2023_z2 {

    public static int n, m;

    public static void main(String[] args) {
        contest_29_09_2023_z2 obj = new contest_29_09_2023_z2();

        int[][] arr = obj.in();
//        obj.out(arr);
        maxSubmatrixSum(arr);
    }

    static void maxSubmatrixSum(int [][]A) //gfg
    {
        int r = A.length;
        int c = A[0].length;

        int [][]prefix = new int[r][];

        for (int i = 0; i < r; i++) {
            prefix[i] = new int[Math.max(r, c)];
            for (int j = 0; j < c; j++) {
                prefix[i][j] = 0;
            }
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (j == 0) prefix[i][j] = A[i][j];
                else prefix[i][j] = A[i][j] + prefix[i][j - 1];
            }
        }

        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < c; i++) {
            for (int j = i; j < c; j++) {
                Vector<Integer> v = new Vector<Integer>();
                for (int k = 0; k < r; k++) {
                    int el = 0;
                    if (i == 0) el = prefix[k][j];
                    else el = prefix[k][j] - prefix[k][i - 1];
                    v.add(el);
                }
                maxSum = Math.max(maxSum, kadane(v));
            }
        }

        System.out.print(maxSum+ "\n");
    }
    static int kadane(Vector<Integer> v) {
        int currSum = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < (int)v.size(); i++)
        {
            currSum += v.get(i);
            if (currSum > maxSum) maxSum = currSum;
            if (currSum < 0) currSum = 0;
        }
        return maxSum;
    }

    public int[][] in(){
        Scanner scanner = new Scanner(System.in);
        this.n = scanner.nextInt();
        this.m = scanner.nextInt();

        int[][] arr = new int[this.n][this.m];

        for (int i = 0; i < this.n; i++)
        {
            for (int j = 0; j < this.m; j++)
            {
                arr[i][j] = scanner.nextInt();
            }
        }
        return arr;
    }

    public void out(int[][] arr){
        for (int i = 0; i < this.n; i++)
        {
            for (int j = 0; j < this.m; j++)
            {
                System.out.print(arr[i][j] + " ");
            }
            System.out.printf("\n");
        }
    }

}

