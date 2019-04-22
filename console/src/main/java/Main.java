import com.netcracker.mano.touragency.dao.impl.jdbc.ConnectionPool;
import menu.MainMenu;
import menu.Menu;

public class Main {
    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        Menu menu = new MainMenu();
        menu.printMenu();
    }
}
