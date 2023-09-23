import java.util.Arrays;
import java.util.stream.IntStream;

public class Lab2_2345678 {

    public static void main(String[] args) {
        Lab2_2345678 obj = new Lab2_2345678();

        System.out.println( Arrays.toString( obj.arrayUnionAndSort_2(new int[]{7, 5, 3, 1},new int[]{8,6,4,2}) ) );
        System.out.println( obj.maxSumSubArray_3(new int[]{7, 5, -20, 8, 5}) );
        System.out.println( Arrays.deepToString( obj.turnToRight_4( new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}} ) ) );
        System.out.println( obj.twoElementsSum_5(new int[]{7, 5, -20, 8, 5}, 10) );
        System.out.println( obj.twoDimentionalArraySum_6( new int[][]{{1, 3, 5}, {1, 3, 4}, {1, 3}} ) );
        System.out.println( Arrays.toString( obj.maxElementInRow_7( new int[][]{{1, 3, 5}, {1, 3, 4}, {1, 3}} ) ) );
        System.out.println( Arrays.deepToString( obj.turnToLeft_8( new int[][]{{9, 5, 1}, {10, 6, 2}, {11, 7, 3}, {12, 8, 4}} ) ) );
    }
    public int[] arrayUnionAndSort_2(int[] arr1, int[] arr2){
        int[] tmp = IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2)).toArray();
        Arrays.sort(tmp);
        return tmp;
    }

    public static int maxSumSubArray_3(int[] arr){ // Алгоритм Кадане
        int maxSoFar = 0; // сохраняет максимальный суммарный подмассив, найденный на данный момент
        int maxEndingHere = 0; // сохраняет максимальную сумму подмассива, заканчивающегося на текущей позиции
        for (int i: arr) // обход заданного массива
        {
            // обновить максимальную сумму подмассива, "заканчивающегося" на индексе "i" (путем добавления
            // текущий элемент до максимальной суммы, заканчивающейся на предыдущем индексе 'i-1')
            maxEndingHere = maxEndingHere + i;

            // если максимальная сумма отрицательна, устанавливаем ее в 0 (что представляет
            // пустой подмассив)
            maxEndingHere = Integer.max(maxEndingHere, 0);

            // обновить результат, если текущая сумма подмассива окажется больше
            maxSoFar = Integer.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    public static int[][] turnToRight_4(int[][] arr){ // method for turn double array on 90 degrees to right.
        int[][] resultArray = new int[arr[0].length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                resultArray[j][arr.length - i - 1] = arr[i][j];
            }
        }
        return resultArray;
    }

    public static String twoElementsSum_5(int[] arr, int target){
        for (int i = 0; i < arr.length; i++)
        {
            for (int j = 0; j < arr.length; j++)
            {
                if ( arr[i] + arr[j] == target && i != j ){
                    String sTmp1 = "arr["+ i +"]="+ String.valueOf(arr[i]) ;
                    String sTmp2 = "arr["+ j +"]="+ String.valueOf(arr[j]) ;
                    return sTmp1.concat("  and  "+sTmp2);
                }
            }
        }
        return "null";
    }

    public static int twoDimentionalArraySum_6(int[][] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            for (int j = 0; j < arr[i].length; j++)
            {
                sum += arr[i][j];
            }
        }
        return sum;
    }

    public static int[] maxElementInRow_7(int[][] arr){
        int tmpRowMax;
        int[] newLinearArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            tmpRowMax = arr[i][0];
            for (int j = 1; j < arr[i].length; j++)
            {
                tmpRowMax = Integer.max(tmpRowMax, arr[i][j]);
            }
            newLinearArr[i] = tmpRowMax;
        }

        return newLinearArr;
    }

    public static int[][] turnToLeft_8(int[][] arr){ //method for turn double array on 90 degrees to left.
        int[][] resultArray = new int[arr[0].length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                resultArray[arr[i].length - j - 1][i] = arr[i][j];
            }
        }
        return resultArray;
    }

}

