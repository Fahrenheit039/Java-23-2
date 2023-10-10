import java.util.ArrayList;

public class Test {
//    public static void testMoviesAdd() {
//        if () {
//            throw new AssertionError("Метод работает неверно!");
//        }
//        System.out.println("Все тесты пройдены!");
//    }
    static MovieLibrary movies; // = new MovieLibrary();
    static CinemaChain cinemas; // = new CinemaChain();
    private class Closer{
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
    public void nextSession(Movie m){
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
        if (isEmpty(total)) { System.out.println("Current movie was not found, try another"); return; }
        print(total);
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
