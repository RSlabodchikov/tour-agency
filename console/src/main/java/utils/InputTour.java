package utils;

import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.entity.Tour;

import java.time.LocalDate;
import java.util.Scanner;

public class InputTour {
    public static Tour createTour() {
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
