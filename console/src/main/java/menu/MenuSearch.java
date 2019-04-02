package menu;


import com.netcracker.mano.touragency.entity.User;

class MenuSearch {
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

    Menu getMenuByAction(User user, String action) {
        if ("booking".compareTo(action) == 0) {
            menu = new BookingMenu(user);
        } else if ("credit card".compareTo(action) == 0) {
            menu = new CreditCardMenu(user);
        } else if ("profile".compareTo(action) == 0) {
            menu = new ProfileMenu(user);
        }
        return menu;
    }

}
