package com.netcracker.mano.touragency.menu;


import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.menu.admin.AdminMenu;
import com.netcracker.mano.touragency.menu.admin.AdminTourMenu;
import com.netcracker.mano.touragency.menu.admin.AdminUserMenu;
import com.netcracker.mano.touragency.menu.client.ClientBookingMenu;
import com.netcracker.mano.touragency.menu.client.ClientCreditCardMenu;
import com.netcracker.mano.touragency.menu.client.ClientMenu;
import com.netcracker.mano.touragency.menu.client.ClientProfileMenu;
import com.netcracker.mano.touragency.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuSearch {
    private Menu menu;

    private AdminTourMenu adminTourMenu;

    private AdminMenu adminMenu;

    @Autowired
    public void setMenu(AdminMenu adminMenu) {
        this.adminMenu=adminMenu;
    }

    @Autowired
    public void setAdminTourMenu(AdminTourMenu adminTourMenu) {
        this.adminTourMenu = adminTourMenu;
    }


    Menu getMenuByRole(User user) {
        switch (user.getRole()) {
            case ADMIN:
                menu = adminMenu;
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
            menu = adminTourMenu;
        } else menu = new AdminUserMenu();
        return menu;
    }

}
