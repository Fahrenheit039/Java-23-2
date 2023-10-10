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
    public String print(){ return this.h +":"+ this.m +":"+ this.s; }
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
//    protected HashMap<Duration, Movie> schedule; // <время начала фильма, фильм>
    protected ArrayList<Session> schedule; // <время начала фильма, фильм>
    protected HallConfiguration hallCfg;
    public CinemaHalls(HallConfiguration hallCfg) {
        this.id = counter++;
//        this.schedule = new HashMap<Duration, Movie>();
        this.schedule = new ArrayList<Session>();
        this.hallCfg = hallCfg;
    }

    private int isAvailableByTime(Duration dToCheck, Movie mToCheck){ // сделать его int и возвращать i?
        int i = 0; // определяет 2 соседних сеанса в отсортированом списке от добавляемого
        while ( i < schedule.size() ) {
//            Session tmp = schedule.get(schedule.indexOf(i)); // TODO: 08.10.2023 у меня тут indexOf(индекс принимает). а должен объект
            Session tmp = schedule.get(i);
            Duration d = tmp.d;
            Movie m = tmp.m;
            if( d.h > mToCheck.d.h ) { break; } //пробег по отсортированному по времени списку
            if( d.h < mToCheck.d.h ) { i++; }
            if( d.h == mToCheck.d.h ) {
                if( d.m > mToCheck.d.m ) { break; } //пробег по отсортированному по времени списку
                if( d.m < mToCheck.d.m ) { i++; }
                if( d.m == mToCheck.d.m ) {
                    if( d.s > mToCheck.d.s ) { break; } //пробег по отсортированному по времени списку
                    if( d.s <= mToCheck.d.s ) { i++; }
                }
            }
        }
        if (i == 0) return i; // if i==0 значит в коллекции 0 фильмов ЛИБО добавляемый фильм раньше .get(0) самого первого в коллекции // TODO: 10.10.2023 чет тут муть какая-то
        if (i == schedule.size())  // if i = .size(), то у добавляемого фильма единственный сосед слева - посдедний элемент списка
            if ( crossingSessions(dToCheck) ) { return i; } else { return -1; }
        //если я дошел до этого момента, то у нужного времени есть 2 соседа: i = правый \ i-1 = левый
        if ( crossingSessions(dToCheck, mToCheck, i) ) { return i; } else { return -1; }
    }
    private boolean crossingSessions(Duration dToCheck){
        Session tmp = schedule.get(schedule.size()-1);
        if ( tmp.d.h + tmp.m.d.h < dToCheck.h ) return true;
        if ( tmp.d.h + tmp.m.d.h > dToCheck.h ) return false;
        if ( tmp.d.h + tmp.m.d.h == dToCheck.h ) {
            if( tmp.d.m + tmp.m.d.m < dToCheck.m ) { return true; }
            if( tmp.d.m + tmp.m.d.m > dToCheck.m ) { return false; }
            if( tmp.d.m + tmp.m.d.m == dToCheck.m ) {
                if( tmp.d.s + tmp.m.d.s < dToCheck.s ) { return true; }
                if( tmp.d.s + tmp.m.d.s >= dToCheck.s ) { return false; }
            }
        }
        return false; // все варианты учтены выше. тут отбивка
    } // true = не пересекаются
    private boolean crossingSessions(Duration dToCheck, Movie mToCheck, int i){
        int flag = 0; // 2 = обе границы в порядке \\ можно будет просто убрать позже вместе с условиями flag++;
//        #1
        Session tmp = schedule.get(i-1);
        if ( tmp.d.h + tmp.m.d.h < dToCheck.h ) flag++;
        if ( tmp.d.h + tmp.m.d.h > dToCheck.h ) return false;
        if ( tmp.d.h + tmp.m.d.h == dToCheck.h ) {
            if( tmp.d.m + tmp.m.d.m < dToCheck.m ) { flag++; }
            if( tmp.d.m + tmp.m.d.m > dToCheck.m ) { return false; }
            if( tmp.d.m + tmp.m.d.m == dToCheck.m ) {
                if( tmp.d.s + tmp.m.d.s < dToCheck.s ) { flag++; }
                if( tmp.d.s + tmp.m.d.s >= dToCheck.s ) { return false; }
            }
        }
//        #2
        tmp = schedule.get(i);
        if ( dToCheck.h + mToCheck.d.h < tmp.d.h ) flag++;
        if ( dToCheck.h + mToCheck.d.h > tmp.d.h ) return false;
        if ( dToCheck.h + mToCheck.d.h == tmp.d.h ) {
            if( dToCheck.m + mToCheck.d.m < tmp.d.m ) { flag++; }
            if( dToCheck.m + mToCheck.d.m > tmp.d.m ) { return false; }
            if( dToCheck.m + mToCheck.d.m == tmp.d.m ) {
                if( dToCheck.s + mToCheck.d.s < tmp.d.s ) { flag++; }
                if( dToCheck.s + mToCheck.d.s >= tmp.d.s ) { return false; }
            }
        }
        if (flag == 2) return true;
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
//        final Map<?, ?> map = this.schedule;
//        for (final Map.Entry<?, ?> entry : map.entrySet()) {
//            Duration d = (Duration) entry.getKey();
//            Movie m = (Movie) entry.getValue();
//            System.out.println( d.print() + " " + m.getName() );
//        }
        for (Session item : schedule) { System.out.println( item.d.print() +" - "+ item.getFinishTime().print() +" "+ item.m.getName() +" ("+ item.m.d.print() +")" ); }
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


// TODO: 08.10.2023 теперь надо в бесконечный цикл. кайнд оф реалтайм програм, ю ноу
public class TicketSystem {
    public static void main(String[] args){
        Test t1 = new Test(new MovieLibrary(), new CinemaChain());
        t1.main();
        t1.nextSession(new Movie("rembo"));



    }

}


