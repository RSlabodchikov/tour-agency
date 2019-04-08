package menu.admin;

import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.impl.TourServiceImpl;
import com.netcracker.mano.touragency.interfaces.TourService;
import menu.Menu;
import utils.InputTour;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminTourMenu implements Menu {
    private TourService service = TourServiceImpl.getInstance();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void printTextMenu() {
        System.out.println("1)Get all tours");
        System.out.println("2)Get tour by id");
        System.out.println("3)Delete tour by id");
        System.out.println("4)Change tour");
        System.out.println("5)Create tour");
        System.out.println("0)Move to previous menu");

    }

    @Override
    public void printMenu() {

        while (true) {
            try {
                printTextMenu();
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Please enter number, not string!!!");
                }
                switch (scanner.nextInt()) {
                    case 1:
                        service.getAll().forEach(System.out::println);
                        break;
                    case 2:
                        System.out.println("Enter id of tour to delete");
                        service.getById(scanner.nextLong());
                        break;
                    case 3:
                        System.out.println("Enter id of tour to delete :");
                        service.delete(scanner.nextLong());
                        break;
                    case 4:
                        changeTour();
                        break;
                    case 5:
                        service.create(InputTour.createTour());
                        break;
                    case 0:
                        return;
                    default:
                        throw new InputMismatchException("Incorrect choice, try again, please!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void changeTour() {
        System.out.println("Enter id of tour to delete :");
        Tour tour = service.getById(scanner.nextLong());
        if (tour == null) {
            System.out.println("Cannot find tour :(");
            return;
        }
        System.out.println(tour);
        System.out.println("1)Number of clients in tour");
        System.out.println("2)Price of tour");
        System.out.println("Choose  the field to change :");
        try {
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("Enter new number of clients :");
                    tour.setNumberOfClients(scanner.nextInt());
                    break;
                case 2:
                    System.out.println("Enter new cost :");
                    tour.setPrice(scanner.nextDouble());
                    break;
                default:
                    System.out.println("Wrong choice :(");
                    return;
            }
            service.update(tour);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
