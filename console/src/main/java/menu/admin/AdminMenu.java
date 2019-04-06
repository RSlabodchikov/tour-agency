package menu.admin;

import com.netcracker.mano.touragency.entity.User;
import menu.Menu;
import menu.MenuSearch;
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
            String action;
            printTextMenu();
            try {
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Enter int number, please!!!");
                }


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
                        throw new InputMismatchException("Wrong choice! Try again, please!!!");
                }
                Menu menu = new MenuSearch().getAdminMenuByAction(action);
                menu.printMenu();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
