package menu.client;

import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.impl.BookingServiceImpl;
import com.netcracker.mano.touragency.impl.TourServiceImpl;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.interfaces.TourService;
import lombok.extern.slf4j.Slf4j;
import menu.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;


@Slf4j
public class ClientBookingMenu implements Menu {

    private User user;

    private BookingService service = BookingServiceImpl.getInstance();

    public ClientBookingMenu(User user) {

        this.user = user;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1)Find all bookings");
        System.out.println("2)Find booking by id");
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
                        System.out.println("Enter id of booking :");
                        System.out.println(service.findBooking(user.getId(), scanner.nextLong()));
                        break;
                    case 3:
                        service.create(createBooking());
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
            } catch (CannotCreateEntityException e) {
                System.out.println("Cannot create booking");
                log.error("Can't create booking", e);
            } catch (EntityNotFoundException e) {
                System.out.println("Cannot find booking");
                log.error("Cannot find booking entity", e);
            }
        }
    }

    private Booking createBooking() {
        Booking booking = new Booking();
        booking.setUserId(user.getId());
        Scanner scanner = new Scanner(System.in);
        TourService tourService = TourServiceImpl.getInstance();
        tourService.getAll().forEach(System.out::println);
        System.out.println("Choose id of tour to create booking : ");
        booking.setTourId(scanner.nextInt());
        System.out.println("Choose amount of people in booking :");
        booking.setNumberOfClients(scanner.nextByte());
        return booking;
    }
}
