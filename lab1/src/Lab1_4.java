import java.util.Scanner;

public class Lab1_4 {

    public int[] answer = new int[] {1, -1}; // index, maxHeight

    public static void main(String[] args) {
        Lab1_4 obj = new Lab1_4();
        obj.solve();
    }
    public void solve(){

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int nextTunnel;
        int nextRoadMinHeight;

        for (int i = 1; i <= n; i++) {
            scanner = new Scanner(System.in);
            int m = scanner.nextInt();
            nextRoadMinHeight = -1;
            for (int j = 0; j < m; j++) {
                scanner = new Scanner(System.in);
                nextTunnel = scanner.nextInt();
                if (nextRoadMinHeight == -1)
                {
                    nextRoadMinHeight = nextTunnel;
                    continue;
                } else {
                    if (nextRoadMinHeight > nextTunnel) nextRoadMinHeight = nextTunnel;
                }
            }
            if (answer[1] == -1)
            {
                answer[1] = nextRoadMinHeight;
            } else {
                if (answer[1] < nextRoadMinHeight) {
                    answer[0] = i;
                    answer[1] = nextRoadMinHeight;
                }
            }
        }

        System.out.printf("%d %d\n", answer[0], answer[1]);
    }

}

