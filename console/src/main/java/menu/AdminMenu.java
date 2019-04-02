package menu;

import com.netcracker.mano.touragency.entity.User;

public class AdminMenu implements Menu {
    private User user;

    public AdminMenu(User user) {
        this.user = user;
    }

    @Override
    public void printTextMenu() {

    }

    @Override
    public void printMenu() {

    }
}
