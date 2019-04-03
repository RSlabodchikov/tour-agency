package menu.client;

import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.impl.CreditCardServiceImpl;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import menu.Menu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientCreditCardMenu implements Menu {
    private User user;
    private CreditCardService service = new CreditCardServiceImpl();

   public  ClientCreditCardMenu(User user) {
        this.user = user;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1)Show all cards");
        System.out.println("2)Get balance of card by it's ID");
        System.out.println("3)Create new card");
        System.out.println("4)Delete credit card");
        System.out.println("5)Get card with greatest balance");
        System.out.println("6)Add money to card");
        System.out.println("0)Move to previous menu");
    }

    @Override
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
                        showAllCards();
                        break;
                    case 2:
                        System.out.println("Enter card id :");
                        getCardBalance(scanner.nextLong());
                        break;
                    case 3:
                        System.out.println("Enter initial balance of card :  ");
                        createCard(scanner.nextDouble());
                        break;
                    case 4:
                        System.out.println("Enter ID of card to delete :");
                        service.delete(scanner.nextLong(), user.getId());
                        break;
                    case 5:
                        System.out.println(service.getByGreatestBalance(user.getId()));
                        break;
                    case 6:
                        System.out.println("Enter money to add : ");
                        double balance = scanner.nextDouble();
                        System.out.println("Enter card id : ");
                        Long id = scanner.nextLong();
                        service.updateBalance(id, balance, user.getId());
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Incorrect choice! Try again, please!");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showAllCards() {
        List<CreditCard> cards = service.getAllClientCards(user.getId());

        System.out.println(cards);
    }

    private void getCardBalance(Long id) {
        if (service.getById(user.getId(), id).isPresent()) {
            System.out.println(service.getById(user.getId(), id));
        }
    }


    private void createCard(Double balance) {
        service.create(balance, user.getId());
    }
}