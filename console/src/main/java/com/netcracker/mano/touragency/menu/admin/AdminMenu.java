package com.netcracker.mano.touragency.menu.admin;

import com.netcracker.mano.touragency.menu.Menu;
import com.netcracker.mano.touragency.menu.MenuSearch;
import com.netcracker.mano.touragency.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;


@Component
public class AdminMenu implements Menu {

    private MenuSearch menuSearch;

    @Autowired
    public void setMenuSearch(MenuSearch menuSearch) {
        this.menuSearch = menuSearch;
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
                    scanner.nextLine();
                    throw new InputMismatchException("Enter int number, please!!!");
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
                Menu menu = menuSearch.getAdminMenuByAction(action);
                menu.printMenu();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
