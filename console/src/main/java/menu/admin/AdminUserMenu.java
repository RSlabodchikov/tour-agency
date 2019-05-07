package menu.admin;

import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.impl.UserServiceImpl;
import com.netcracker.mano.touragency.interfaces.UserService;
import menu.Menu;
import utils.InputUser;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminUserMenu implements Menu {

    private UserService service = UserServiceImpl.getInstance();

    @Override
    public void printTextMenu() {
        System.out.println("1)Get all users");
        System.out.println("2)Block user");
        System.out.println("3)Unblock user");
        System.out.println("4)Create user");
        System.out.println("5)Get user by id");
        System.out.println("0)Move to previous menu");
    }

    @Override
    public void printMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                printTextMenu();
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Please enter number, not string!!!");
                }
                switch (scanner.nextInt()) {
                    case 1:
                        service.getAllUsers().forEach(System.out::println);
                        break;
                    case 2:
                        System.out.println("Enter id of user to block:");
                        service.blockUser(scanner.nextLong());
                        break;
                    case 3:
                        System.out.println("Enter id of user to block:");
                        service.unblockUser(scanner.nextLong());
                        break;
                    case 4:
                        User user = InputUser.createUser();
                        service.registration(user);
                        break;
                    case 5:
                        System.out.println("Enter id of user :");
                        System.out.println(service.findById(scanner.nextLong()));
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Incorrect choice, try again, please!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
