package menu.client;

import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.impl.CreditCardServiceImpl;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import menu.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;


@Slf4j
public class ClientCreditCardMenu implements Menu {
    private User user;
    private CreditCardService service = CreditCardServiceImpl.getInstance();

    public ClientCreditCardMenu(User user) {
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
                        service.getAllClientCards(user.getId()).forEach(System.out::println);
                        break;
                    case 2:
                        System.out.println("Enter card id :");
                        System.out.println(service.getById(user.getId(), scanner.nextLong()).getBalance());
                        break;
                    case 3:
                        System.out.println("Enter initial balance of card :  ");
                        System.out.println(service.create((scanner.nextDouble()), user.getId()));
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
            } catch (CannotCreateEntityException e) {
                System.out.println("Cannot create card");
                log.error("Problem with creating card", e);
            } catch (CannotUpdateEntityException e) {
                System.out.println("cannot update card balance");
                log.error("Cannot update card entity", e);
            } catch (EntityNotFoundException e) {
                System.out.println("Cannot find card");
                log.error("Cannot find card entity", e);
            }
        }
    }


}