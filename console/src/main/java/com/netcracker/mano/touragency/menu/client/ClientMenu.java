package com.netcracker.mano.touragency.menu.client;


import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.menu.Menu;
import com.netcracker.mano.touragency.menu.MenuSearch;
import com.netcracker.mano.touragency.utils.Constants;

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
        System.out.println("0)Back to main Application.menu");
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
                Menu menu = new MenuSearch().getUserMenuByAction(user, action);
                menu.printMenu();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
