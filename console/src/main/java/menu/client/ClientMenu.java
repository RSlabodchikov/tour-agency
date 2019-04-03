package menu.client;


import com.netcracker.mano.touragency.entity.User;
import menu.Menu;
import menu.MenuSearch;
import utils.Constants;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientMenu implements Menu {

    private User user;

    public ClientMenu(User user) {
        this.user = user;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1)Bookings");
        System.out.println("2)Credit cards");
        System.out.println("3)Profile");
        System.out.println("0)Back to main menu");
    }


    @Override
    public void printMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String action;
            try {
                printTextMenu();
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Enter int number, please!!!");
                }
                switch (scanner.nextInt()) {
                    case 1:
                        action = Constants.BOOKING;
                        break;
                    case 2:
                        action = Constants.CREDIT_CARDS;
                        break;
                    case 3:
                        action = Constants.PROFILE;
                        break;
                    case 0:
                        return;
                    default:
                        throw new InputMismatchException("Wrong choice! Try again, please!!!");
                }
                Menu menu = new MenuSearch().getMenuByAction(user, action);
                menu.printMenu();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
