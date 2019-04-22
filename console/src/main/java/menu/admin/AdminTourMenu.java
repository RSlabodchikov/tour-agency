package menu.admin;

import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.impl.TourServiceImpl;
import com.netcracker.mano.touragency.interfaces.TourService;
import lombok.extern.slf4j.Slf4j;
import menu.Menu;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;


@Slf4j
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
                        System.out.println("Enter id of tour :");
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
                        System.out.println(service.create(createTour()));
                        break;
                    case 0:
                        return;
                    default:
                        throw new InputMismatchException("Incorrect choice, try again, please!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (CannotCreateEntityException e) {
                System.out.println("Cannot create tour");
                log.error("Can't create tour entity", e);
            } catch (EntityNotFoundException e){
                System.out.println("Cannot find tour");
                log.error("Cannot find tour entity", e);
            }
        }
    }

    private void changeTour() {
        System.out.println("Enter id of tour to change :");
        try {
            Tour tour = service.getById(scanner.nextLong());
            if (tour == null) {
                System.out.println("Cannot find tour :(");
                return;
            }
            System.out.println(tour);
            System.out.println("1)Number of clients in tour");
            System.out.println("2)Price of tour");
            System.out.println("Choose  the field to change :");

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
        } catch (CannotUpdateEntityException e) {
            System.out.println("Cannot update tour");
            log.error("Cannot update tour entity", e);
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find tour");
            log.error("Tour entity not found", e);
        }
    }

    private Tour createTour() {
        Scanner scanner = new Scanner(System.in);
        Tour tour = new Tour();
        System.out.println("Enter settlement month and day:");
        int m = scanner.nextInt();
        int d = scanner.nextInt();
        tour.setSettlementDate(LocalDate.of(2019, m, d));
        System.out.println("Enter eviction month and day:");
        m = scanner.nextInt();
        d = scanner.nextInt();
        tour.setEvictionDate(LocalDate.of(2019, m, d));
        System.out.println("Enter country name of tour : ");
        tour.setCountry(scanner.next());
        System.out.println("Enter tour description : ");
        tour.setDescription(scanner.next());

        System.out.println("Choose category of tour(1 - family, 2 - sport, 3 - weekend, 4 - gastronomic :");
        switch (scanner.nextInt()) {
            case 1:
                tour.setCategory(Category.FAMILY);
                break;
            case 2:
                tour.setCategory(Category.SPORT);
                break;
            case 3:
                tour.setCategory(Category.WEEKEND);
                break;
            case 4:
                tour.setCategory(Category.GASTRONOMIC);
                break;
            default:
                tour.setCategory(Category.GENERAL);
        }
        System.out.println("Set tour price :");
        tour.setPrice(scanner.nextDouble());
        System.out.println("Enter number of clients in tour :");
        tour.setNumberOfClients(scanner.nextInt());
        return tour;
    }
}
