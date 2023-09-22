import java.util.Scanner;

public class Lab1_2 {

    public static void main(String[] args) {
        Lab1_2.solve();
    }
    public static void solve(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n<0) return;

        int tmp;
        int answer = 0;
        for(int i=0; i<n; i++)
        {
            tmp = scanner.nextInt();
//            System.out.format("%d ",tmp);

            if(i % 2 == 0) {
                answer += tmp;
//                System.out.print("+ ");
            }
            else {
                answer -= tmp;
//                System.out.print("- ");
            }

        }
        System.out.println("\n"+answer);
    }
}

