import java.util.ArrayList;

public class Test {
//    public static void testMoviesAdd() {
//        if () {
//            throw new AssertionError("Метод работает неверно!");
//        }
//        System.out.println("Все тесты пройдены!");
//    }
//static MovieLibrary movies = new MovieLibrary();
//    static CinemaChain cinemas = new CinemaChain();

    static MovieLibrary movies; // = new MovieLibrary();
    static CinemaChain cinemas; // = new CinemaChain();

    public void buyATicket(){}
    //    public  nextSession(Movie m){}
    public void print(Movie m){}

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
        if (index_movie != -1) ch.createSession(new Duration(9,0), movies.movieLibraryArray.get(index_movie));
        ch.printSchedule();

        Session s = ch.schedule.get(0);
        s.buyASeat(4, 4);
        s.printSession();

    }

}
