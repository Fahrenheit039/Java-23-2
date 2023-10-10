import java.util.ArrayList;
import java.util.Scanner;

public class Test {
//    public static void testMoviesAdd() {
//        if () {
//            throw new AssertionError("Метод работает неверно!");
//        }
//        System.out.println("Все тесты пройдены!");
//    }
    static MovieLibrary movies; // = new MovieLibrary();
    static CinemaChain cinemas; // = new CinemaChain();
    protected static class Closer{
        Cinema c_c;
        CinemaHalls c_ch;
        Session c_s;
        public Closer(){
            this.c_s = new Session(new Duration());
            this.c_s.d.h = -1;
            this.c_s.d.m = -1;
            this.c_s.d.s = -1;
        }
        public Closer(Cinema c_c, CinemaHalls c_ch, Session c_s){
            this.c_c = c_c;
            this.c_ch = c_ch;
            this.c_s = c_s;
        }
    }
    public Closer nextSession(Movie m){
        Session tmp = new Session(m);
        Closer total = new Closer();

        for ( Cinema c : cinemas.cinemaChainArray ) {
            boolean flag = false;
            for ( CinemaHalls ch : c.cinemaHalls ) {
                for ( Session s : ch.schedule ) {
                    if ( s.m.getName().equals(m.getName()) ) {
                        if ( total.c_s.d.comp_duration(s.d) ) total = set_closer(c, ch, s);
                        flag = true;
                        break;
                    }
                }
                if (flag) break;
            }
        }
        if (isEmpty(total)) { System.out.println("Current movie was not found, try another"); return null; } // TODO: 10.10.2023 чет тут очень хотел проверить 
        print(total);
        return total;
    } // поиск интересующего фильма в ближайшее время в любом кинотеатре сети
    private Boolean isEmpty(Closer cl){
        return (cl.c_s.d.h == -1 && cl.c_s.d.m == -1 && cl.c_s.d.s == -1);
    }
    private Closer set_closer(Cinema c, CinemaHalls ch, Session s){
        Closer cl = new Closer();
        cl.c_c = c;
        cl.c_ch = ch;
        cl.c_s = s;
        return cl;
    }
    private void print(Closer cl){
        System.out.println( "\ncinema name : "+ cl.c_c.getName() +" \\\\ hall id : "+ cl.c_ch.getId() );
        cl.c_ch.printSchedule(cl.c_s);
        cl.c_s.printSession();
        System.out.println();
    }
    public void buyATicket(){}
    protected boolean foolTest(Closer struct, int level){
        if ( level > 0 && cinemas.cinemaChainArray.size() < 1) { System.out.println("not enough Cinemas"); return false; } else level--;
        if ( level > 0 && struct.c_c == null ) { System.out.println("first you need set Cinema"); return false; } else level--;
        if ( level > 0 && struct.c_c.cinemaHalls.size() < 1) { System.out.println("not enough Cinema Halls"); return false; } else level--;
        if ( level > 0 && struct.c_ch == null ) { System.out.println("first you need set Cinema Hall"); return false; } else level--;
        if ( level > 0 && struct.c_ch.schedule.size() < 1) { System.out.println("Session List is empty"); return false; } else level--;
        if ( level > 0 && struct.c_s == null) { System.out.println("Session List is empty"); return false; } else level--;

        return true;
    }
    protected String keySetter(Scanner scanner){
        String tmp = scanner.nextLine();;
        int i;
        while( true ) {
            if (tmp.equals("-1")) break;
            if (tmp.equals("0")) break;
            if (tmp.equals("1")) break;
            System.out.println("key input is wrong. try again");
            tmp = scanner.nextLine();
        }
        return tmp;
    }
    protected String intSetter(Scanner scanner, int params){
        String tmp = scanner.nextLine();
        String[] parts = tmp.split(" ");
        int i;
        boolean flag = false;
        while (!flag) {
            for (i = 0; i < params; i++) if (!isNumeric(parts[i])) break;
            if (i == params) break;
            System.out.println("your input is wrong. try again");
            tmp = scanner.nextLine();
            parts = tmp.split(" ");
        }
        return tmp;
    }
    protected int intSetter(Scanner scanner){
        String tmp = scanner.nextLine();
        while ( ! isNumeric(tmp) ) {
            System.out.println("your input is wrong. try again");
            tmp = scanner.nextLine();
        }
        return Integer.parseInt(tmp);
    }
    private static boolean isNegativeNumeric(String str){
        return str != null && str.substring(0, 1).equals("-") && str.matches("[0-9]+");
    }
    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9]+");
    }
    private String admin_login = "admin", admin_pass = "admin";
    private String user_login = "user", user_pass = "";
    protected String auth(Scanner scanner){
        System.out.print("login : ");
        String login = scanner.nextLine();

//        scanner = new Scanner(System.in);
        System.out.print("password : ");
        String password = scanner.nextLine();

        if (admin_login.equals(login) && admin_pass.equals(password)) return "admin";
        else if (user_login.equals(login) && user_pass.equals(password)) return "user";
        else return "try-again";
    }

    public Test(MovieLibrary movies, CinemaChain cinemas){
        this.movies = movies;
        this.cinemas = cinemas;
    }
    public static void main(){
        movies.add( new Movie("robokop", new Duration(2, 30)) );
        movies.add( new Movie("rembo", new Duration(3, 00)) );
        movies.add( new Movie("film1", new Duration(1, 30)) );

        cinemas.add( new Cinema("kinoteatr_1") );
        cinemas.add( new Cinema("kinoteatr_2", "moscow") );

        int index_cinema = cinemas.isIn("kinoteatr_2"); //в этой и следующей строчке проверка на существование интересующего кинотеатра
        Cinema c = cinemas.cinemaChainArray.get(index_cinema);

        if (index_cinema > -1) cinemas.cinemaChainArray.get(index_cinema).addHall(new CinemaHalls(new HallConfiguration("casual",6,6)));
        if (index_cinema > -1) c.addHall(new CinemaHalls(new HallConfiguration("test1",7,7)));
        if (index_cinema > -1) c.addHall(new CinemaHalls(new HallConfiguration("test2",2,5)));


        int index_cinemaHall = c.isIn(1);
        CinemaHalls ch = c.cinemaHalls.get(index_cinemaHall);

        if (index_cinemaHall > -1) {
    //            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.printCfg();
            ch.hallCfg.changeCfg(2,2, 4,4, -1);
    //            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.printCfg();
            ch.hallCfg.changeCfg(1,1, 5,5, 0);
    //            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.printCfg();
            ch.hallCfg.changeCfg(1,1, 5,1, 1);
            ch.hallCfg.printCfg();
        }

        ch = c.cinemaHalls.get(2);
        ch.hallCfg.changeCfg(2,1, 3,2, -1);
        ch.hallCfg.printCfg();
//        int index_movie = movies.isIn("rembo"); //проверка на существование фильма
//        cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).createSession(new Duration(9,0), movies.movieLibraryArray.get(index_movie));

//        int tmpSession = movies.isIn("rembo"); //проверка
//        cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall)..get(index_cinemaHall).createSession(new Duration(9,0), movies.movieLibraryArray.get(index_movie));

        int index_movie = movies.isIn("rembo"); //проверка на существование фильма
        if (index_movie != -1) ch.createSession(new Duration(18,0), movies.movieLibraryArray.get(index_movie));
        if (index_movie != -1) ch.createSession(new Duration(9,0), movies.movieLibraryArray.get(index_movie));
        ch.printSchedule();

        Session s = ch.schedule.get(0);
        s.buyASeat(4, 4);
        s.printSession();
        s.buyASeat(5, 2);
        s.buyASeat(1, 2);
        s.printSession();
        s.buyASeat(1, 2);

        ch.printSchedule(s);



    }

}
