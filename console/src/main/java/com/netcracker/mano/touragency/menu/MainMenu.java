package com.netcracker.mano.touragency.menu;

import com.netcracker.mano.touragency.dao.impl.jdbc.ConnectionPool;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.RegistrationException;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.utils.InputUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
@Component
public class MainMenu implements Menu {

    private ConnectionPool connectionPool;

    private MenuSearch menuSearch;

    @Autowired
    public void setMenuSearch(MenuSearch menuSearch) {
        this.menuSearch = menuSearch;
    }

    @Autowired
    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

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
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Enter int number, please!!!");
                }
                switch (scanner.nextInt()) {

                    case 1:
                        user = service.signIn(InputUser.authorizeUser());
                        break;
                    case 2:
                        user = InputUser.createUser();
                        user = service.register(user);
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        throw new InputMismatchException("Wrong choice! Try again, please!");
                }
                if (user.getIsBlocked()) {
                    System.out.println("Your are blocked by admin  :(");
                } else {
                    Menu menu = menuSearch.getMenuByRole(user);
                    menu.printMenu();
                }

            } catch (RegistrationException regExc) {
                System.out.println("Cannot register user with this params ");
                log.error("Cannot create user", regExc);
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (AuthorizationException authExc) {
                System.out.println("Wrong login or password");
                log.error("Authorization problem", authExc);
            }
        }
    }
}
