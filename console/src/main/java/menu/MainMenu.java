package menu;

import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.impl.UserServiceImpl;
import com.netcracker.mano.touragency.interfaces.UserService;
import utils.InputUser;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu implements Menu {

    @Override
    public void printTextMenu() {
        System.out.println("1)Sign in");
        System.out.println("2)Registration");
        System.out.println("0)Finish work");
        System.out.println("Your choice is:");
    }

    @Override
    public void printMenu() {
        while (true) {
            printTextMenu();
            Scanner scanner = new Scanner(System.in);
            try {
                User user = null;
                UserService service = new UserServiceImpl();
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Enter int number, please!!!");
                }
                switch (scanner.nextInt()) {

                    case 1:
                        user = service.signIn(InputUser.authorizeUser());
                        break;
                    case 2:
                        user = InputUser.createUser();
                        service.registration(user);
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        throw new InputMismatchException("Wrong choice! Try again, please!");
                }
                if (user != null) {
                    Menu menu = new MenuSearch().getMenuByRole(user);
                    menu.printMenu();
                }

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }


        }
    }
}
