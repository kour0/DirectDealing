package fr.quatorze.pcd.codingweekquinze.database.seeder;

import fr.quatorze.pcd.codingweekquinze.dao.UserDAO;
import fr.quatorze.pcd.codingweekquinze.service.AuthService;

public class UserSeeder {

    public void seed() {

        UserDAO userDAO = UserDAO.getInstance();

        String pass = AuthService.getInstance().getEncoder().encode("p");

        // Create users
        userDAO.createUser("John", "Doe", "john.doe@telecomnancy.eu", pass, 200, false, true);
        userDAO.createUser("Jane", "Doe", "jane.doe@telecomnancy.eu", pass, 200, false, false);
        userDAO.createUser("Michele", "Doe", "mi", pass, 200, false, false);

    }
}