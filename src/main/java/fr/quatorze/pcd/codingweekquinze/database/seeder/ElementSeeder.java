package fr.quatorze.pcd.codingweekquinze.database.seeder;

import fr.quatorze.pcd.codingweekquinze.dao.ElementDAO;
import fr.quatorze.pcd.codingweekquinze.dao.UserDAO;
import fr.quatorze.pcd.codingweekquinze.model.User;

import java.util.ArrayList;

public class ElementSeeder {

    public void seed() {

        ElementDAO elementDAO = ElementDAO.getInstance();

        UserDAO userDAO = UserDAO.getInstance();
        ArrayList<User> users = (ArrayList<User>) userDAO.getAllUsers();

        // Create elements
        elementDAO.createElement("Bike", 30, "A bike", users.get(0));
        elementDAO.createElement("Car", 25, "A car", users.get(1));
        elementDAO.createElement("Motorbike", 5, "A motorbike", users.get(2));
        elementDAO.createElement("Scooter", 10, "A scooter", users.get(0));
        elementDAO.createElement("Skateboard", 40, "A skateboard", users.get(1));
        elementDAO.createElement("Roller", 30, "A roller", users.get(2));
        elementDAO.createElement("Ski", 80, "A ski", users.get(0));
        elementDAO.createElement("Snowboard", 100, "A snowboard", users.get(1));
        elementDAO.createElement("Surf", 68, "A surf", users.get(2));
    }
}