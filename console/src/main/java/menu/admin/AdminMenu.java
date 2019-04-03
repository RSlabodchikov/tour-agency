package menu.admin;

import com.netcracker.mano.touragency.entity.User;
import menu.Menu;
import utils.Constants;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu implements Menu {
    private User user;

    public AdminMenu(User user) {
        this.user = user;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1)Users");
        System.out.println("2)Tours");
        System.out.println("0)Log out");
    }

    @Override
    public void printMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printTextMenu();
            String action;
            try {
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Please enter int, not string !!!");
                }
                switch (scanner.nextInt()) {
                    case 1:
                        action = Constants.USERS;
                        break;
                    case 2:
                        action = Constants.TOURS;
                        break;
                    case 0:
                        return;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
