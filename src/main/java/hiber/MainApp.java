package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User userVasya = new User("Vasya", "Petrov", "vas@mail.com");
        User userPetya = new User("Petya", "Vasechkin", "pet@mail.com");

        Car car1 = new Car("Mustang", 555);
        Car car2 = new Car("Viper", 777);

        userVasya.setCar(car1);
        userPetya.setCar(car2);

        userService.add(userVasya);
        userService.add(userPetya);


        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println(user.getCar());
            System.out.println();
        }

        try {
            User us = userService.getUserByModelAndSeries("Viper", 777);
            System.out.println("The requested car is owned by user:");
            System.out.println(("Id: " + us.getId() + ", "
                    + "first Name: = " + us.getFirstName() + ", "
                    + "last name: " + us.getLastName() + ", "
                    + "email = " + us.getEmail()));
        } catch (Exception e) {
            System.out.println("query failed");
            System.out.println(e.getCause());
        }

        context.close();
    }
}
