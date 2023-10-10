import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class MovieLibrary{
    //    private HashMap<Integer, String> movieLibraryMap;
//    private LinkedHashMap<Character, String> movieLibrary(){
//    LinkedHashMap<Character, String> vMethods = new LinkedHashMap<Character, String>();
    protected ArrayList<Movie> movieLibraryArray;
    protected MovieLibrary(){
        this.movieLibraryArray = new ArrayList<>();
    }
    public void add(Movie m){
        int i;
        if ( (i = this.isIn(m.getName()) ) < 0 ) { // то не найден
            this.movieLibraryArray.add(m);
            System.out.println("Movie is successfully added");
        } else {
            this.movieLibraryArray.set(i, m);
            System.out.println("Movie with same name was replaced with new Duration");
        }
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
//        Object[] movieArray = movieLibraryArray.toArray();
//        for(Object item : movieArray){
//            System.out.println(item);
//        }
        for(Movie item : movieLibraryArray){
            System.out.println( item.getName() +" ("+ item.d.print() +")" );
        }
    }
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
} // сеть кинотеатров
class Duration{
    protected int h, m, s;
    public String print() {
        String tmp = "";
        if (this.h < 10) tmp += "0";
        tmp += this.h + ":";
        if (this.m < 10) tmp += "0";
        tmp += this.m + ":";
        if (this.s < 10) tmp += "0";
        tmp += this.s;
        return tmp;
    }
    protected Duration(){
        this.h = 0;
        this.m = 0;
        this.s = 0;
    }
    protected Duration(int h, int m){
        this.h = h;
        this.m = m;
        this.s = 0;
    }
    protected Duration(int h, int m, int s){
        this.h = h;
        this.m = m;
        this.s = s;
    }
    public boolean comp_duration(Duration d){
        if (this.h == -1 && this.m == -1 && this.s == -1) return true;

        if ( this.h > d.h ) return true;
        if ( this.h < d.h ) return false;
        if ( this.h == d.h ) {
            if( this.m > d.m ) { return true; }
            if( this.m < d.m ) { return false; }
            if( this.m == d.m ) {
                if( this.s > d.s ) { return true; }
                if( this.s <= d.s ) { return false; }
            }
        }
        return false;
    } // true = this. меньше , false = больше либо равно
} // время фильма, и время начала сеанса по расписанию
class Movie {
    private String name;
    public String getName(){ return this.name; }
    protected Duration d;
    protected Movie(String name){
        this.name = name;
    } // для поиска
    protected Movie(String name, Duration d){
        this.name = name;
        this.d = d;
    }
} // фильм
class Cinema {
    private int id;
    private static int counter = 0;
    private String name; //для поиска в списке кинозалов конкретного по имени
    public String getName(){ return this.name; }
    private String address;
    protected ArrayList<CinemaHalls> cinemaHalls;
    protected Cinema(String name){
        this.id = counter++;
        this.name = name;
        this.address = "empty";
        this.cinemaHalls = new ArrayList<>();
    }
    protected Cinema(String name, String address){
        this.id = counter++;
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
    public int isIn(int id) {
        for (CinemaHalls item : cinemaHalls)
            if( item.getId() == id ) return this.cinemaHalls.indexOf(item); //а что мне мешает вернуть сразу объект?
        return -1;
    }
    public void print(){
        System.out.println( this.cinemaHalls.toString() );
    }

} // кинотеатр
class CinemaHalls{
    private int id;
    public int getId(){ return this.id; }
    private static int counter = 0;
    protected ArrayList<Session> schedule; // <время начала фильма, фильм>
    protected HallConfiguration hallCfg;
    public CinemaHalls(HallConfiguration hallCfg) {
        this.id = counter++;
//        this.schedule = new HashMap<Duration, Movie>();
        this.schedule = new ArrayList<Session>();
        this.hallCfg = hallCfg;
    }

    private int isAvailableByTime(Duration dToCheck, Movie mToCheck){ // сделать его int и возвращать i?
        if (schedule.size() == 0) return 0; // if i==0 значит в коллекции 0 фильмов
        int i = 0; // определяет 2 соседних сеанса в отсортированом списке от добавляемого
        while ( i < schedule.size() ) {
//            Session tmp = schedule.get(schedule.indexOf(i)); // TODO: 08.10.2023 у меня тут indexOf(индекс принимает). а должен объект
            Session tmp = schedule.get(i);
            Duration d = tmp.d;
            Movie m = tmp.m;
            if( d.h > dToCheck.h ) { break; } //пробег по отсортированному по времени списку // TODO: 10.10.2023 тут случайно не dToCheck должно быть
            if( d.h < dToCheck.h ) { i++; }
            if( d.h == dToCheck.h ) {
                if( d.m > dToCheck.m ) { break; } //пробег по отсортированному по времени списку
                if( d.m < dToCheck.m ) { i++; }
                if( d.m == dToCheck.m ) {
                    if( d.s > dToCheck.s ) { break; } //пробег по отсортированному по времени списку
                    if( d.s <= dToCheck.s ) { i++; }
                }
            }
        }
//
        if (i == 0)   // if i = 0, то у добавляемого фильма единственный сосед СПРАВА - ПЕРВЫЙ(0) элемент списка
            if ( crossingSessions(dToCheck, mToCheck, i) ) { return i; } else { return -1; }
        if (i == schedule.size())  // if i = .size(), то у добавляемого фильма единственный сосед слева - посдедний элемент списка
                if ( crossingSessions(dToCheck, mToCheck, i) ) { return i; } else { return -1; }
        //если я дошел до этого момента, то у нужного времени есть 2 соседа: i = правый \ i-1 = левый
        if ( crossingSessions(dToCheck, mToCheck, i) ) { return i; } else { return -1; }
    }
    private boolean crossingSessions(Duration dToCheck, Movie mToCheck, String direction){

        return false; // все варианты учтены выше. тут отбивка
    } // true = не пересекаются
    private boolean crossingSessions(Duration dToCheck, Movie mToCheck, int i){
        if ( i == schedule.size() ) { // left from new
            Session tmp = schedule.get(schedule.size()-1);
            if ( tmp.d.h + tmp.m.d.h < dToCheck.h ) return true;
            if ( tmp.d.h + tmp.m.d.h > dToCheck.h ) return false;
            if ( tmp.d.h + tmp.m.d.h == dToCheck.h ) {
                if( tmp.d.m + tmp.m.d.m < dToCheck.m ) return true;
                if( tmp.d.m + tmp.m.d.m > dToCheck.m ) return false;
                if( tmp.d.m + tmp.m.d.m == dToCheck.m ) {
                    if( tmp.d.s + tmp.m.d.s < dToCheck.s ) return true;
                    if( tmp.d.s + tmp.m.d.s >= dToCheck.s ) return false;
                }
            }
        } else if ( i == 0 ) { // first // right from new
            Session tmp = schedule.get(0);
            if ( dToCheck.h + mToCheck.d.h < tmp.d.h ) return true;
            if ( dToCheck.h + mToCheck.d.h > tmp.d.h ) return false;
            if ( dToCheck.h + mToCheck.d.h == tmp.d.h ) {
                if( dToCheck.m + mToCheck.d.m < tmp.d.m ) return true;
                if( dToCheck.m + mToCheck.d.m > tmp.d.m ) return false;
                if( dToCheck.m + mToCheck.d.m == tmp.d.m ) {
                    if( dToCheck.s + mToCheck.d.s < tmp.d.s ) return true;
                    if( dToCheck.s + mToCheck.d.s >= tmp.d.s ) return false;
                }
            }
        } else {
            int flag = 0; // 2 = обе границы в порядке \\ можно будет просто убрать позже вместе с условиями flag++;
            //        #1
            Session tmp = schedule.get(i - 1);
            if (tmp.d.h + tmp.m.d.h < dToCheck.h) flag++;
            if (tmp.d.h + tmp.m.d.h > dToCheck.h) return false;
            if (tmp.d.h + tmp.m.d.h == dToCheck.h) {
                if (tmp.d.m + tmp.m.d.m < dToCheck.m) {
                    flag++;
                }
                if (tmp.d.m + tmp.m.d.m > dToCheck.m) {
                    return false;
                }
                if (tmp.d.m + tmp.m.d.m == dToCheck.m) {
                    if (tmp.d.s + tmp.m.d.s < dToCheck.s) {
                        flag++;
                    }
                    if (tmp.d.s + tmp.m.d.s >= dToCheck.s) {
                        return false;
                    }
                }
            }
            //        #2
            tmp = schedule.get(i);
            if (dToCheck.h + mToCheck.d.h < tmp.d.h) flag++;
            if (dToCheck.h + mToCheck.d.h > tmp.d.h) return false;
            if (dToCheck.h + mToCheck.d.h == tmp.d.h) {
                if (dToCheck.m + mToCheck.d.m < tmp.d.m) {
                    flag++;
                }
                if (dToCheck.m + mToCheck.d.m > tmp.d.m) {
                    return false;
                }
                if (dToCheck.m + mToCheck.d.m == tmp.d.m) {
                    if (dToCheck.s + mToCheck.d.s < tmp.d.s) {
                        flag++;
                    }
                    if (dToCheck.s + mToCheck.d.s >= tmp.d.s) {
                        return false;
                    }
                }
            }
            if (flag == 2) return true;
        }
        return false; // все варианты учтены выше. тут отбивка
    } // true = не пересекаются
    public void createSession(Duration d, Movie m){
        Session tmp = new Session(d, m);
        if (tmp.getFinishTime().h > 23) { System.out.println("Pls, change movie start time. U out of a day range"); tmp = null; return; }

        int i = isAvailableByTime(d, m);
        if ( i < 0 ) { System.out.println("Current time is already reserved"); return; }
        if ( i == schedule.size() ) {
            schedule.add(new Session(d, m, hallCfg));
            System.out.println("Session is successfully created");
            return;
        }
        if ( i >= 0 && i < schedule.size() ) {
            System.out.println("Session is successfully created");
            schedule.add(i, new Session(d, m, hallCfg)); //add и remove сдвигают остальной список по индексам автоматически +rep
        }
    }
    public void printSchedule(){
        for (Session item : schedule) {
            System.out.println( item.d.print() +" - "+ item.getFinishTime().print() +" "+ item.m.getName() +" ("+ item.m.d.print() +")" ); }
    }
    public void printSchedule(Session s){
        for (Session item : schedule) {
            if (item.equals(s))
                System.out.println( item.d.print() +" - "+ item.getFinishTime().print() +" "+ item.m.getName() +" ("+ item.m.d.print() +")" );
        }
    }
} // кинозал
class Session{
    protected Duration d;
    protected Movie m;
    //    private CinemaHalls ch;
    private HallConfiguration hallCfg;
    private int[][] seating; //копируем рассадку при конструкторе, дальше в сессии контролируем купленные места

//    public Session(Duration d, Movie m, CinemaHalls ch) {
    public Session(Movie m) {
    this.m = m;
}
    public Session(Duration d) {
        this.d = d;
    } // для поиска
    public Session(Duration d, Movie m) {
        this.d = d;
        this.m = m;
    }
    public Session(Duration d, Movie m, HallConfiguration hallCfg) {
        this.d = d;
        this.m = m;
//        this.ch = ch;
        this.hallCfg = hallCfg;
        this.seating = hallCfg.matrix; // я по идее чет должен тут зафиксировать, а то сломается. хз
    }
    public Duration getFinishTime(){
        Duration dTmp = new Duration();
        if (this.d.s + this.m.d.s >= 60) {
            dTmp.s = this.d.s + this.m.d.s - 60;
            dTmp.m++;
        } else { dTmp.s = this.d.s + this.m.d.s; }
        if (this.d.m + this.m.d.m + dTmp.m >= 60) {
            dTmp.m += this.d.m + this.m.d.m - 60;
            dTmp.h++;
        } else { dTmp.m += this.d.m + this.m.d.m; }
//        if (this.d.h + this.m.d.h + dTmp.h >= 24) {
//            dTmp.h += this.d.h + this.m.d.h - 24; //TODO: так правильно, но тогда нужны даты, недели и тд.
            dTmp.h += this.d.h + this.m.d.h; //поэтому вот так, для сравнения
//        }
//        dTmp.s = (this.d.s + this.m.d.s >= 60)? (this.d.s + this.m.d.s - 60) : (this.d.s + this.m.d.s);

        return dTmp;
    }
    public void buyASeat(int x, int y){
        System.out.print(x+ " " +y+ " \\ "); // TODO: 08.10.2023  для консоли надо будет убрать
        if ( (x>0 && x<hallCfg.m) && (y>0 && y<hallCfg.n) ) {
            if ( seating[y][x] == 1 ) { // их надо развернуть тут
                seating[y][x] = 2;
                System.out.println("you have successfully purchased a seat in the hall");
            } else System.out.println("this seat is unavailable, please choose another one"); // -1 \ 2
        } else System.out.println("your input is out of range, try again");
    }
    public void printSession(){
        for (int i = 0; i < hallCfg.n; i++){
            for (int j = 0; j < hallCfg.m; j++){
                if (i > 0 && j > 0) {
                    String c;
//                    c = (seating[i][j] == 2) ? "x" : "o";
                    if ( seating[i][j] == 2 ) c = "x";
                    else if ( seating[i][j] == 1 ) c = "o";
                    else c = " ";
                    System.out.printf("%s ", c);
                }
                else System.out.printf("%d ", seating[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
} // сеанс (зал, во сколько, фильм) + рассадка
class HallConfiguration {
    private String nameTypeCfg;
    protected int n, m;
    protected int[][] matrix;
    public HallConfiguration(String type, int n, int m){
        this.nameTypeCfg = type;
        this.n = n + 1;
        this.m = m + 1;
        this.matrix = new int[this.n][this.m];

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++){
                if (i == 0 && j > 0) this.matrix[i][j] = j;
                if (i > 0 && j == 0) this.matrix[i][j] = i;
                if (i > 0 && j > 0) this.matrix[i][j] = 1;
            }
        } // i бежит до n , j до m. твердо и четко
    }
    public void changeCfg(int x1, int y1, int x2, int y2, int key){
        if( (Math.min(x1, x2) < 1 || Math.min(y1, y2) < 1) || (Math.max(x1, x2) >= this.m || Math.max(y1, y2) >= this.n ) )
        {
            System.out.println("ur indexes is out of range");
            return;
        }
        if (key > 0) key = 1;
        else if (key < 0) key = -1;
        else key = 0;

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
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
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
} // рассадка в зале



public class TicketSystem {
//    protected static class Closer1{
//        Cinema c_c;
//        CinemaHalls c_ch;
//        Session c_s;
//        public Closer1(){
//            this.c_s = new Session(new Duration());
//            this.c_s.d.h = -1;
//            this.c_s.d.m = -1;
//            this.c_s.d.s = -1;
//        }
//        public Closer1(Cinema c_c, CinemaHalls c_ch, Session c_s){
//            this.c_c = c_c;
//            this.c_ch = c_ch;
//            this.c_s = c_s;
//        }
//    }

    public static void main(String[] args){
        Test t1 = new Test(new MovieLibrary(), new CinemaChain());
//        t1.main();
//        t1.nextSession(new Movie("rembo"));

        System.out.println("Welcome to the Ticket System Program");

        Scanner scanner = new Scanner(System.in);
        int sa;
        while ( (sa = t1.auth(scanner)) == 0 ) { System.out.println("wrong login or pass. try again"); }

        String cinema_name, hall_type, hall_cfg_change, movie_name, movie_duration, movie_time_start, xy_pos;
        int hall_n, hall_m, cinema_hall_id;
        String[] parts;

        String command = "start";
        Test.Closer struct = new Test.Closer();
        switch (sa) {
            case(1):
                System.out.println("You are entire as a User");
                while( !command.equals("exit") ) {
                    switch (command) {
                        case ("6"): // show movies library
                            if ( t1.movies.movieLibraryArray.size() < 1 ) { System.out.println("Movie Library is empty"); break; }
                            System.out.println("List of available movies:");
                            t1.movies.print();
                            break;
                        case ("a"): // buy a ticket
                            if ( ! t1.foolTest(struct, 6) ) break; // TODO: 10.10.2023 тут чет с проверкой криво. вернуться после сеттеров

                            System.out.print("enter <x, y> pos seating place : ");
                            xy_pos = t1.intSetter(scanner, 2);
                            parts = xy_pos.split(" ");
                            struct.c_s.buyASeat( Integer.parseInt(parts[0]), Integer.parseInt(parts[1] ) );
                            break;
                        case ("s"): // search next closed Movie
                            if ( t1.movies.movieLibraryArray.size() < 1) { System.out.println("Movies Library is empty"); break; }
                            System.out.print("enter movie name for search : ");
                            movie_name = scanner.nextLine();
                            struct = t1.nextSession(new Movie(movie_name));
                            break;
                        default:break;
                    }
                    scanner = new Scanner(System.in);
                    command = scanner.nextLine();
                }
                break; // TODO: 10.10.2023 продоблировать после admin'a : 6, a, s
            case(2):
                System.out.println("You are entire with Admin roots!!");
                while( !command.equals("exit") ) {
                    switch (command) {
                        case ("1"): // add new cinema
                            System.out.print("Enter the name for your next Cinema : ");
                            cinema_name = scanner.nextLine(); // здесь строка любая. абсолютно
                            t1.cinemas.add( new Cinema(cinema_name) );
                            System.out.println("success");
                            break;
                        case ("q"): // set cinema
                            if ( ! t1.foolTest(struct, 1) ) break;

                            System.out.print("set an active Cinema by name : ");
                            cinema_name = scanner.nextLine(); // any string

                            int index_cinema = t1.cinemas.isIn(cinema_name);
                            if (index_cinema > -1) {
                                struct.c_c = t1.cinemas.cinemaChainArray.get(index_cinema);
                                System.out.println("Chosen Cinema : "+ struct.c_c.getName());
                            } else { System.out.println("that name was not found"); }
                            break;
                        case ("2"): // add new cinema hall + configuration
                            if ( ! t1.foolTest(struct, 2) ) break;

                            System.out.println("Enter Hall Configuration");
                            System.out.print("name of type : ");
                            hall_type = scanner.nextLine(); // any str
                            System.out.println("N x M size : ");
                            System.out.print("N = ");
                            hall_n = t1.intSetter(scanner);
                            System.out.print("M = ");
                            hall_m = t1.intSetter(scanner);
                            struct.c_c.addHall(new CinemaHalls(new HallConfiguration(hall_type, hall_n, hall_m)));
                            System.out.println("success");
                            break;
                        case ("w"): // set cinema hall
                            if ( ! t1.foolTest(struct, 3) ) break;

                            System.out.print("set an active Cinema Hall by id : ");
                            cinema_hall_id = t1.intSetter(scanner);

                            int index_cinemaHall = struct.c_c.isIn(cinema_hall_id);
                            if (index_cinemaHall > -1) {
                                struct.c_ch = struct.c_c.cinemaHalls.get(index_cinemaHall);
                                System.out.println("Chosen Cinema Hall : "+ struct.c_ch.getId());
                            } else { System.out.println("that id was not found"); }
                            break;
                        case ("3"): // create session
                            if ( ! t1.foolTest(struct, 4) ) break;

                            System.out.print("enter the Name of movie : ");
                            movie_name = scanner.nextLine();

                            System.out.print("what time does the movie start <hours, minutes> : ");
                            movie_time_start = t1.intSetter(scanner, 2);
                            parts = movie_time_start.split(" ");

                            int index_movie = t1.movies.isIn(movie_name);
                            if (index_movie > -1) {
                                struct.c_ch.createSession(new Duration( Integer.parseInt(parts[0]), Integer.parseInt(parts[1] ) ), t1.movies.movieLibraryArray.get(index_movie));
                            } else { System.out.println("something going wrong"); }
                            break;
                        case ("e"): // set session
                            if ( ! t1.foolTest(struct, 5) ) break;

                            System.out.print("enter id of session : ");
                            int session_id = t1.intSetter(scanner);
                            if ( struct.c_ch.schedule.size() <= session_id ) { System.out.println("your input is out of range"); break; }

                            struct.c_s = struct.c_ch.schedule.get(session_id);
                            System.out.println( "Chosen Session : ");
                            struct.c_ch.printSchedule(struct.c_s);
                            break;
                        case ("4"): // change chosen cinema hall configuration
                            if ( ! t1.foolTest(struct, 4) ) break;

                            System.out.print("changing Hall Configuration.\nbefore: \n");
                            struct.c_ch.hallCfg.printCfg();
                            System.out.println("Enter area <x1, y1, x2, y2, key>, ");
                            System.out.println("key = -1 : everything in area is unavailable \\ key = 1 : everything in area is available \\ key = 0 : reverse ");
                            hall_cfg_change = t1.intSetter(scanner, 5);
                            parts = hall_cfg_change.split(" ");
                            struct.c_ch.hallCfg.changeCfg( Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                            System.out.print("\nafter: \n");
                            struct.c_ch.hallCfg.printCfg();
                            break;
                        case ("5"): // add new movie to library
                            System.out.println("Enter new movie Name and Duration: ");
                            System.out.print("name : ");
                            movie_name = scanner.nextLine();
                            System.out.print("Duration <hours, minutes> : ");
                            movie_duration = t1.intSetter(scanner, 2);
                            parts = movie_duration.split(" ");
                            t1.movies.add( new Movie( movie_name, new Duration( Integer.parseInt(parts[0]), Integer.parseInt(parts[1] )) ) );
                            break;
                        case ("6"): // show movies library
                            if ( t1.movies.movieLibraryArray.size() < 1 ) { System.out.println("Movie Library is empty"); break; }
                            System.out.println("List of available movies:");
                            t1.movies.print();
                            break;
                        case ("7"): // show c.ch schedule
                            if ( ! t1.foolTest(struct, 4) ) break;
                            System.out.println("schedule:");
                            struct.c_ch.printSchedule();
                            break;
                        case ("a"): // buy a ticket
                            if ( ! t1.foolTest(struct, 6) ) break; // TODO: 10.10.2023 тут чет с проверкой криво. вернуться после сеттеров

                            System.out.print("enter <x, y> pos seating place : ");
                            xy_pos = t1.intSetter(scanner, 2);
                            parts = xy_pos.split(" ");
                            struct.c_s.buyASeat( Integer.parseInt(parts[0]), Integer.parseInt(parts[1] ) );
                            break;
                        case ("s"): // search next closed Movie
                            if ( t1.movies.movieLibraryArray.size() < 1) { System.out.println("Movies Library is empty"); break; }
                            System.out.print("enter movie name for search : ");
                            movie_name = scanner.nextLine();
                            struct = t1.nextSession(new Movie(movie_name));
                            break;
                        case ("menu"): // menu
                            System.out.println("1 - add new cinema");
                            System.out.println("q - set cinema");
                            System.out.println("2 - add new cinema hall + configuration");
                            System.out.println("w - set cinema hall");
                            System.out.println("3 - create session");
                            System.out.println("e - set session");
                            System.out.println("4 - change chosen cinema hall configuration");
                            System.out.println("5 - add new movie to library");
                            System.out.println("6 - show movies library");
                            System.out.println("7 - show c.ch schedule");
                            System.out.println("a - buy a ticket");
                            System.out.println("s - search next closed Movie");
                            break;
                        default:break;
                    }
                    System.out.print("next command : ");
                    scanner = new Scanner(System.in);
                    command = scanner.nextLine();
                }
                break;
            default:
                break;
        }

    }

}


