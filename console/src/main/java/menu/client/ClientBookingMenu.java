package menu.client;

import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.impl.BookingServiceImpl;
import com.netcracker.mano.touragency.impl.TourServiceImpl;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.interfaces.TourService;
import menu.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientBookingMenu implements Menu {

    private User user;

    private BookingService service = new BookingServiceImpl();

    public ClientBookingMenu(User user) {

        this.user = user;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1)Get all bookings");
        System.out.println("2)Get booking by id");
        System.out.println("3)Create booking");
        System.out.println("4)Delete booking");
        System.out.println("0)Previous menu");
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
                        service.getAll(user.getId()).forEach(System.out::println);
                        break;
                    case 2:
                        System.out.println(service.findBooking(user.getId(), scanner.nextLong()));
                        break;
                    case 3:
                        if (service.createBooking(createBooking()) == null) {
                            System.out.println("Cannot create booking :(");
                        }
                        break;
                    case 4:
                        System.out.println("Enter id of booking to delete :");
                        service.delete(user.getId(), scanner.nextLong());
                        break;

                    case 0:
                        return;
                    default:
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Booking createBooking() {
        Booking booking = new Booking();
        booking.setUserId(user.getId());
        Scanner scanner = new Scanner(System.in);
        TourService tourService = new TourServiceImpl();
        tourService.getAll().forEach(System.out::println);
        System.out.println("Choose id of tour to create booking : ");
        booking.setTourId(scanner.nextInt());
        System.out.println("Choose amount of people in booking :");
        booking.setNumberOfClients(scanner.nextByte());
        return booking;
    }
}
