package com.netcracker.mano.touragency;

import com.netcracker.mano.touragency.menu.MainMenu;
import com.netcracker.mano.touragency.dao.impl.jdbc.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.netcracker.mano.touragency.*"})
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);
    }

    private MainMenu mainMenu;

    @Autowired
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }


    @Override
    public void run(String... strings) {
        ConnectionPool connectionPool = new ConnectionPool();
        mainMenu.printMenu();

    }
}
