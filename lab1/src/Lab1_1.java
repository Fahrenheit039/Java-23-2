import java.util.Scanner;

public class Lab1_1 {

    public static void main(String[] args) {
        int n = Lab1_1.in();
        int answer = Lab1_1.solve(n);
        Lab1_1.out(answer);
    }
    public static int in(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        if(n>0) return n;
        return 0;
    }
    public static int solve(int n){
        if (n<=1) return 0;

        int answer = 0;
        int tmp = n;
        while(tmp > 1)
        {
            if(tmp % 2 == 0)
            {
                tmp /= 2;
                answer++;
            }else{
                tmp = 3*tmp + 1;
                answer++;
            }
//            System.out.format("%d ",tmp);
        }

        return answer;
    }
    public static void out(int answer){
//        System.out.println("\n"+answer);
        System.out.println(answer);
        }
}

