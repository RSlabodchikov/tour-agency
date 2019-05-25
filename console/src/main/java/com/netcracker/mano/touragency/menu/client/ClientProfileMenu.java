package com.netcracker.mano.touragency.menu.client;

import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.menu.Menu;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;


@Slf4j
@Component
@Data
public class ClientProfileMenu implements Menu {

    private User user;

    private UserService service;

    @Autowired
    public ClientProfileMenu(UserService service) {
        this.service = service;
    }

    public void printTextMenu() {
        System.out.println("1)Show profile");
        System.out.println("2)Change password");
        System.out.println("3)Change name and surname");
        System.out.println("0)Move back to the client Application.menu");

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
            } catch (AuthorizationException e1) {
                System.out.println("Wrong login or password");
                log.error("Authorization problem", e1);
            } catch (CannotUpdateEntityException e) {
                System.out.println("Cannot update user entity");
                log.error("Cannot update user entity", e);
            }
        }
    }
}
