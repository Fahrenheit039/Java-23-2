import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class MovieLibrary{
    //    private HashMap<Integer, String> movieLibraryMap;
//    private LinkedHashMap<Character, String> movieLibrary(){
//    LinkedHashMap<Character, String> vMethods = new LinkedHashMap<Character, String>();
    protected ArrayList<Movie> movieLibraryArray;
    protected MovieLibrary(){
        this.movieLibraryArray = new ArrayList<>();
    }
    public void add(Movie m){
        this.movieLibraryArray.add(m);
    }
    public void remove(Movie m){
        this.movieLibraryArray.remove(m);
    }
    public boolean isIn(Movie m){
        return this.movieLibraryArray.contains(m);
    }
    public int isIn(String name) {
        for (Movie item : this.movieLibraryArray)
            if( item.getName().equals(name) ) return this.movieLibraryArray.indexOf(item);
        return -1;
    } // для переданного названия кинотеатра
    public void print(){
        System.out.println( this.movieLibraryArray.toString() );
    }

//    public void printLibrary(){
//        printMap(movieLibraryMap);
//    }
//    private void printMap(final Map<?, ?> map) {
//        for (final Map.Entry<?, ?> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//    }
} // фильмы которые доступны владельцу сети кинотеатров
class CinemaChain{
    protected ArrayList<Cinema> cinemaChainArray;
    protected CinemaChain(){
        this.cinemaChainArray = new ArrayList<>();
    }
    public void add(Cinema c){
        this.cinemaChainArray.add(c);
    }
    public void remove(Cinema c){
        this.cinemaChainArray.remove(c);
    }
//    public boolean isIn(Cinema c) { return cinemaChainArray.contains(c); }
    public int isIn(Cinema c) { return this.cinemaChainArray.indexOf(c); } // -1 = не найден
    public int isIn(String name) {
        for (Cinema item : this.cinemaChainArray)
            if( item.getName().equals(name) ) return this.cinemaChainArray.indexOf(item);
        return -1;
    } // для переданного названия кинотеатра
    public void print(){
        System.out.println( this.cinemaChainArray.toString() );
    }
} // кинотеатры которые есть у владельца сети
class Duration{
    protected int h, m, s;
    public String print(){ return this.h +" "+ this.m +" "+ this.s; }
    protected Duration(int h, int m){
        this.h = h;
        this.m = m;
    }
    protected Duration(int h, int m, int s){
        this.h = h;
        this.m = m;
        this.s = s;
    }
} // время фильма, и время начала сеанса по расписанию
class Movie {
    private String name;
    public String getName(){ return this.name; }
    protected Duration d;
    protected Movie(String name, Duration d){
        this.name = name;
        this.d = d;
    }
} // фильм
class Cinema {
    private String name; //для поиска в списке кинозалов конкретного по имени
    public String getName(){ return this.name; }
    private String address;
    protected ArrayList<CinemaHalls> cinemaHalls;
    protected Cinema(String name){
        this.name = name;
        this.address = "empty";
        this.cinemaHalls = new ArrayList<>();
    }
    protected Cinema(String name, String address){
        this.name = name;
        this.address = address;
        this.cinemaHalls = new ArrayList<>();
    }
    public void addHall(CinemaHalls ch){
        this.cinemaHalls.add(ch);
    }
    public void removeHall(CinemaHalls ch) {
        this.cinemaHalls.remove(ch);
    }
    public boolean isIn(CinemaHalls ch) { return this.cinemaHalls.contains(ch); }
    public void print(){
        System.out.println( this.cinemaHalls.toString() );
    }

} // кинотеатр
class CinemaHalls{
    private int id;
    private static int counter = 0;
    protected HashMap<Duration, Movie> schedule; // <время начала фильма, фильм>
    protected HallConfiguration hallCfg;
    public CinemaHalls(HallConfiguration hallCfg) {
        this.id = counter++;
        this.schedule = new HashMap<Duration, Movie>();
        this.hallCfg = hallCfg;
    }

    public boolean isAvailableByTime(Duration dToCheck, Movie mToCheck){
        final Map<?, ?> map = this.schedule;
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            Duration d = (Duration) entry.getKey();
            Movie m = (Movie) entry.getValue();
//            if (m == mToCheck) {}//совпадение по фильму нужно будет где-то в другом месте

            if( d.h < mToCheck.d.h ){
                Duration tmp = timeShiftCalc();
                if (tmp.h <= mToCheck.d.h) {
                    //TODO я здесь

                }
            }
//            System.out.println( d.print() + " " + m.getName() );
        }
        return true;
    }
    private Duration timeShiftCalc(){
        return new Duration(0, 0);
    }

    public void createSession(Duration d, Movie m){
        if (isAvailableByTime(d, m)) schedule.put(d, m);
    }
    public void printSchedule(){
        final Map<?, ?> map = this.schedule;
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            Duration d = (Duration) entry.getKey();
            Movie m = (Movie) entry.getValue();
            System.out.println( d.print() + " " + m.getName() );
        }
    }
//    public void deleteSession(Duration d, Movie m){
//        if (isAvailable(d, m)) schedule.put(d, m);
//    }
}

class Session{
    private CinemaHalls ch;
    private Duration d;
    private Movie m;
    private int[][] seating; //копируем рассадку при конструкторе, дальше в сессии контролируем купленные места

    public Session(CinemaHalls ch, Duration d, Movie m) {
        this.ch = ch;
        this.d = d;
        this.m = m;
        this.seating = this.ch.hallCfg.matrix; // я по идее чет должен тут зафиксировать, а то сломается. хз
    }
    public void buyASeat(int x, int y){
        if ( (x>0 && x<this.ch.hallCfg.m) && (y>0 && y<this.ch.hallCfg.n) ) {
            if ( this.seating[x][y] == 1 ) {
                this.seating[x][y] = 2;
                System.out.println("you have successfully purchased a seat in the hall");
            } else System.out.println("this seat is unavailable, please choose another one"); // -1 \ 2
        } else System.out.println("your input is out of range, try again");
    }
}

class HallConfiguration {
    private String nameTypeCfg;
    protected int n, m;
    protected int[][] matrix;
    public HallConfiguration(String type, int n, int m){
        this.nameTypeCfg = type;
        this.n = n + 1;
        this.m = m + 1;
        this.matrix = new int[this.n][this.m];

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++){
                if (i == 0 && j > 0) this.matrix[i][j] = j;
                if (i > 0 && j == 0) this.matrix[i][j] = i;
                if (i > 0 && j > 0) this.matrix[i][j] = 1;
            }
        }
    }
    public void changeCfg(int x1, int y1, int x2, int y2, int key){
        if( (Math.min(x1, x2) < 1 || Math.min(y1, y2) < 1) || (Math.max(x1, x2) >= this.m || Math.max(y1, y2) >= this.n ) )
        {
            System.out.println("ur indexes is out of range");
            return;
        }
        switch (key)
        {
            case(0):
                for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++)
                    for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++)
                        this.matrix[i][j] *= -1;
                break;
            case(1):
                for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++)
                    for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++)
                        this.matrix[i][j] = 1;
                break;
            case(-1):
                for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++)
                    for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++)
                        this.matrix[i][j] = -1;
                break;
            default:
                break;
        }
    } //key = 0 - инвертирование \ 1 - все в области становятся доступны \ -1 - все в области становятся не доступны
    public void printCfg(){
        for (int i = 0; i < this.m; i++){
            for (int j = 0; j < this.n; j++){
                if (i > 0 && j > 0) {
                    String c = (this.matrix[i][j] > 0) ? "+" : "-";
                    System.out.printf("%s ", c);
                }
                else System.out.printf("%d ", this.matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
} // название рассадки и размеры



public class TicketSystem {
    static MovieLibrary movies = new MovieLibrary();
    static CinemaChain cinemas = new CinemaChain();

    public void buyATicket(){}
//    public  nextSession(Movie m){}
    public void print(Movie m){}


    public static void main(String[] args){
        movies.add( new Movie("robokop", new Duration(2, 30)) );
        movies.add( new Movie("rembo", new Duration(3, 00)) );
        movies.add( new Movie("film1", new Duration(1, 30)) );

        cinemas.add( new Cinema("kinoteatr_1") );
        cinemas.add( new Cinema("kinoteatr_2", "moscow") );

        int index_cinema = cinemas.isIn("kinoteatr_2"); //в этой и следующей строчке проверка на существование интересующего кинотеатра
        if (index_cinema > -1) cinemas.cinemaChainArray.get(index_cinema).addHall(new CinemaHalls(new HallConfiguration("casual",6,6)));
        if (index_cinema > -1) cinemas.cinemaChainArray.get(index_cinema).addHall(new CinemaHalls(new HallConfiguration("test",7,7)));

        int index_cinemaHall = cinemas.isIn("kinoteatr_2"); //проверка на существование зала
        if (index_cinemaHall > -1) {
//            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.printCfg();
            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.changeCfg(2,2, 4,4, -1);
//            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.printCfg();
            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.changeCfg(1,1, 5,5, 0);
//            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.printCfg();
            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.changeCfg(1,1, 5,1, 1);
            cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).hallCfg.printCfg();
        }

        int index_movie = movies.isIn("rembo"); //в этой и следующей строчке проверка на существование интересующего кинотеатра
        cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).createSession(new Duration(9,0), movies.movieLibraryArray.get(index_movie));

//        int tmpSession = movies.isIn("rembo"); //в этой и следующей строчке проверка на существование интересующего кинотеатра
//        cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall)..get(index_cinemaHall).createSession(new Duration(9,0), movies.movieLibraryArray.get(index_movie));
        cinemas.cinemaChainArray.get(index_cinema).cinemaHalls.get(index_cinemaHall).printSchedule();



    }

}


