package menu.client;

import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.impl.UserServiceImpl;
import com.netcracker.mano.touragency.interfaces.UserService;
import menu.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientProfileMenu implements Menu {
    private User user;

    private UserService service = UserServiceImpl.getInstance();

    public ClientProfileMenu(User user) {
        this.user = user;
    }


    public void printTextMenu() {
        System.out.println("1)Show profile");
        System.out.println("2)Change password");
        System.out.println("3)Change name and surname");
        System.out.println("0)Move back to the client menu");

    }


    public void printMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                printTextMenu();
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Enter int number, please!!!");
                }
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println(user);
                        break;
                    case 2:
                        System.out.println("Enter old password :");
                        String oldPassword = scanner.next();
                        System.out.println("Enter new password :");
                        String newPassword = scanner.next();
                        service.changePassword(user.getCredentials().getLogin(), oldPassword, newPassword);
                        break;
                    case 3:
                        System.out.println("Enter new name :");
                        user.setName(scanner.next());
                        System.out.println("Enter new surname :");
                        user.setSurname(scanner.next());
                        service.update(user);
                        break;
                    case 0:
                        return;
                    default:
                        throw new InputMismatchException("Wrong choice! Please, try again!!!");
                }

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
