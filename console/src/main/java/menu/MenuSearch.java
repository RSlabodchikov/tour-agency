package menu;


import com.netcracker.mano.touragency.entity.User;
import menu.admin.AdminMenu;
import menu.admin.AdminTourMenu;
import menu.admin.AdminUserMenu;
import menu.client.ClientBookingMenu;
import menu.client.ClientCreditCardMenu;
import menu.client.ClientMenu;
import menu.client.ClientProfileMenu;
import utils.Constants;

public class MenuSearch {
    private Menu menu;

    Menu getMenuByRole(User user) {
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

    public Menu getUserMenuByAction(User user, String action) {
        if (Constants.BOOKING.compareTo(action) == 0) {
            menu = new ClientBookingMenu(user);
        } else if (Constants.CREDIT_CARDS.compareTo(action) == 0) {
            menu = new ClientCreditCardMenu(user);
        } else if (Constants.PROFILE.compareTo(action) == 0) {
            menu = new ClientProfileMenu(user);
        }
        return menu;
    }

    public Menu getAdminMenuByAction(String action) {
        if (Constants.TOURS.compareTo(action) == 0) {
            menu = new AdminTourMenu();
        } else menu = new AdminUserMenu();
        return menu;
    }

}
