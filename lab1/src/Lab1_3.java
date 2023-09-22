import java.util.Scanner;

public class Lab1_3 {

    public int[] treasureCords;
    public int[] playerCords = new int[] {0, 0}; // x,y

    public int answer = 0; //counter
    public int temp;

    public static void main(String[] args) {
        Lab1_3 obj = new Lab1_3();
        obj.treasureCords = obj.init();
        obj.solve();
        obj.out();
}
    public static int[] init(){
        Scanner scanner = new Scanner(System.in);
        int[] arr = new int[2];
        arr[0] = scanner.nextInt();
        arr[1] = scanner.nextInt();
        return arr;
    }

    public void solve(){
        Scanner scanner = new Scanner(System.in);
        String direction = scanner.nextLine();
//        System.out.printf("Direction: %s \n", direction);
        while (!direction.equals("стоп"))
        {
            temp = scanner.nextInt();
            switch (direction){
                case ("запад"):
                    if (playerCords[1] == treasureCords[1]) { // y
                        if (playerCords[0] > treasureCords[0] && (playerCords[0] - temp) <= treasureCords[0]) { // условие прохода через сокровища
                            answer++;
                            return;
                        } else { // на одной прямой по y, но не дошли по х
                            playerCords[0] -= temp;
                            answer++;
                        }
                    }else{ // не на одной прямой по y
                        playerCords[0] -= temp;
                        answer++;
                    }
                    break;
                case "восток":
                    if (playerCords[1] == treasureCords[1]) { // y
                        if (playerCords[0] < treasureCords[0] && (playerCords[0] + temp) >= treasureCords[0]) { // условие прохода через сокровища
                            answer++;
                            return;
                        } else { // на одной прямой по y, но не дошли по х
                            playerCords[0] += temp;
                            answer++;
                        }
                    }else{ // не на одной прямой по y
                        playerCords[0] += temp;
                        answer++;
                    }
                    break;
                case "север":
                    if (playerCords[0] == treasureCords[0]) { // х
                        if (playerCords[1] < treasureCords[1] && (playerCords[1] + temp) >= treasureCords[1]) { // условие прохода через сокровища
                            answer++;
                            return;
                        } else { // на одной прямой по х, но не дошли по у
                            playerCords[1] += temp;
                            answer++;
                        }
                    }else{ // не на одной прямой по х
                        playerCords[1] += temp;
                        answer++;
                    }
                    break;
                case "юг":
                    if (playerCords[0] == treasureCords[0]) { // х
                        if (playerCords[1] > treasureCords[1] && (playerCords[1] - temp) <= treasureCords[1]) { // условие прохода через сокровища
                            answer++;
                            return;
                        } else { // на одной прямой по х, но не дошли по у
                            playerCords[1] -= temp;
                            answer++;
                        }
                    }else{ // не на одной прямой по х
                        playerCords[1] -= temp;
                        answer++;
                    }
                    break;
                default:
                    break;

            }
            scanner = new Scanner(System.in);
            direction = scanner.nextLine();
        }

    }

    public void out(){
        System.out.println(answer);
    }
}

