package menu;


import com.netcracker.mano.touragency.entity.User;
import menu.admin.AdminMenu;
import menu.client.ClientBookingMenu;
import menu.client.ClientCreditCardMenu;
import menu.client.ClientMenu;
import menu.client.ClientProfileMenu;
import utils.Constants;

public class MenuSearch {
    private Menu menu;

    public Menu getMenuByRole(User user) {
        switch (user.getRole()) {
            case ADMIN:
                menu = new AdminMenu(user);
                break;
            case CLIENT:
                menu = new ClientMenu(user);
                break;
        }
        return menu;
    }

    public Menu getMenuByAction(User user, String action) {
        if (Constants.BOOKING.compareTo(action) == 0) {
            menu = new ClientBookingMenu(user);
        } else if (Constants.CREDIT_CARDS.compareTo(action) == 0) {
            menu = new ClientCreditCardMenu(user);
        } else if (Constants.PROFILE.compareTo(action) == 0) {
            menu = new ClientProfileMenu(user);
        }
        return menu;
    }

}
