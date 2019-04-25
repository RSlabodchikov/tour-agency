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

    private AdminMenu adminMenu;

    private ClientMenu clientMenu;

    private ClientBookingMenu clientBookingMenu;

    private ClientCreditCardMenu clientCreditCardMenu;

    private ClientProfileMenu clientProfileMenu;

    private AdminTourMenu adminTourMenu;

    private AdminUserMenu adminUserMenu;

    @Autowired
    public void setAdminMenu(AdminMenu adminMenu) {
        this.adminMenu = adminMenu;
    }

    @Autowired
    public void setClientMenu(ClientMenu clientMenu) {
        this.clientMenu = clientMenu;
    }

    @Autowired
    public void setClientBookingMenu(ClientBookingMenu clientBookingMenu) {
        this.clientBookingMenu = clientBookingMenu;
    }

    @Autowired
    public void setClientCreditCardMenu(ClientCreditCardMenu clientCreditCardMenu) {
        this.clientCreditCardMenu = clientCreditCardMenu;
    }

    @Autowired
    public void setClientProfileMenu(ClientProfileMenu clientProfileMenu) {
        this.clientProfileMenu = clientProfileMenu;
    }

    @Autowired
    public void setAdminTourMenu(AdminTourMenu adminTourMenu) {
        this.adminTourMenu = adminTourMenu;
    }

    @Autowired
    public void setAdminUserMenu(AdminUserMenu adminUserMenu) {
        this.adminUserMenu = adminUserMenu;
    }

    Menu getMenuByRole(User user) {
        switch (user.getRole()) {
            case ADMIN:
                menu = adminMenu;
                break;
            case CLIENT:
                clientMenu.setUser(user);
                menu = clientMenu;
                break;
        }
        return menu;
    }

    public Menu getUserMenuByAction(User user, String action) {
        if (Constants.BOOKING.compareTo(action) == 0) {
            clientBookingMenu.setUser(user);
            menu = clientBookingMenu;
        } else if (Constants.CREDIT_CARDS.compareTo(action) == 0) {
            clientCreditCardMenu.setUser(user);
            menu = clientCreditCardMenu;
        } else if (Constants.PROFILE.compareTo(action) == 0) {
            clientProfileMenu.setUser(user);
            menu = clientProfileMenu;
        }
        return menu;
    }

    public Menu getAdminMenuByAction(String action) {
        if (Constants.TOURS.compareTo(action) == 0) {
            menu = adminTourMenu;
        } else menu = adminUserMenu;
        return menu;
    }

}
