package menu.admin;

import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.exceptions.RegistrationException;
import com.netcracker.mano.touragency.impl.UserServiceImpl;
import com.netcracker.mano.touragency.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import menu.Menu;
import utils.InputUser;

import java.util.InputMismatchException;
import java.util.Scanner;


@Slf4j
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
                        service.register(user);
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
            } catch (RegistrationException regExc) {
                log.error("Cannot register user with this credentials :", regExc);
                System.out.println("Cannot register user with this login");
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (CannotUpdateEntityException e) {
                System.out.println("Cannot update entity ");
                log.error("Cannot update user entity", e);
            } catch (EntityNotFoundException e) {
                System.out.println("Cannot find user");
                log.error("Cannot find user", e);
            }
        }
    }
}
