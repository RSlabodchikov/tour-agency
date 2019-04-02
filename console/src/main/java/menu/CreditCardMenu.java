package menu;

import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.impl.ClientServiceImpl;
import com.netcracker.mano.touragency.interfaces.ClientService;

import java.util.List;
import java.util.Scanner;

public class CreditCardMenu implements Menu {
    private User user;
    private ClientService service = new ClientServiceImpl();

    CreditCardMenu(User user) {
        this.user = user;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1)Show all cards");
        System.out.println("2)Get balance of card by it's ID");
        System.out.println("3)Create new card");
        System.out.println("4)Delete credit card");
        System.out.println("5)Find card by ID");
        System.out.println("6)Get card with highest balance");
        System.out.println("7)Add money to card");
        System.out.println("0)Move to previous menu");
    }

    @Override
    public void printMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            printTextMenu();
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
                    service.deleteCard(scanner.nextLong());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect choice! Try again, please!");
            }
        }
    }

    private void showAllCards() {
        List<CreditCard> cards = service.getAllClientCards(user.getId());

        System.out.println(cards);
    }

    private void getCardBalance(Long id) {
        if (service.getCardById(user.getId(), id).isPresent()) {
            System.out.println(service.getCardById(user.getId(), id));
        }
    }

    private void createCard(Double balance) {
        service.createCard(balance, user.getId());
    }
}