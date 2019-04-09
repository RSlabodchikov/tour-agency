package utils;

import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.entity.User;

import java.util.Scanner;

public class InputUser {

    public static Credentials authorizeUser() {
        Scanner scanner = new Scanner(System.in);
        Credentials credentials = new Credentials();
        System.out.println("Enter your login:");
        credentials.setLogin(scanner.nextLine());
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        credentials.setPassword(password);
        return credentials;
    }

    public static User createUser() {
        User user = new User();
        user.setIsBlocked(false);
        Scanner scanner = new Scanner(System.in);
        Credentials credentials = authorizeUser();
        System.out.println("Enter your name:");
        user.setName(scanner.nextLine());
        System.out.println("Enter your surname:");
        user.setSurname(scanner.nextLine());
        user.setCredentials(credentials);
        user.setRole(Role.CLIENT);
        return user;
    }
}
