import java.util.Scanner;

public class Lab1_5 {

    public static void main(String[] args) {
        Lab1_5 obj = new Lab1_5();
        obj.twiceEven();
    }
    public void twiceEven(){

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 3 digitals in number

        int d, tmp = n;
        int sum = 0;
        int mult = 1;
        while (tmp > 0)
        {
            d = tmp % 10;
            sum += d;
            mult *= d;
            tmp /= 10;
        }
        if ((sum % 2 == 0) && (mult % 2 == 0)) System.out.println("your number is even twice!");
        else System.out.println("your number isn't even twice!");
    }

}

