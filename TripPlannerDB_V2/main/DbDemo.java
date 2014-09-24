package database.main;

import java.sql.SQLException;
import java.util.List;

import database.DAL.*;
import database.DTO.*;

public class DbDemo {
    public static void main(String[] args) throws SQLException {
        User newUser = DataManager.fetchUser("fmontei1");
        Trip trip = new Trip();
        DataManager.saveTrip(trip, newUser.getID());
        List<Trip> trips = DataManager.getTripsByUserID(newUser.getID());
        System.out.println(trips.size());
    }

    private static void getUser() throws SQLException {
        try {
            DataScope dataScope = new DataScope();
            dataScope.user_dataScope.loadPreference = true;
            dataScope.user_dataScope.loadTrips = true;
            dataScope.user_dataScope.trip_dataScope.loadPreference = true;
            dataScope.user_dataScope.trip_dataScope.loadAvailableTimes = true;
            dataScope.user_dataScope.trip_dataScope.loadItinerary = true;
            dataScope.user_dataScope.trip_dataScope.loadLodging = true;

            User user = DataManager.getUserByID(1, dataScope.user_dataScope).get(0);
            displayUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayUser(User user) {
        System.out.println("User ID:" + user.getID());
        System.out.println("User First Name:" + user.getFirstName());
        System.out.println("User Last Name:" + user.getLastName());
        System.out.println("User User Name:" + user.getUsername());
        System.out.println("User Password:" + user.getPassword());
        System.out.println();
    }

    private static void saveUser(User user) throws SQLException {
        try {
            DataManager.saveUser(user);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void deleteUser(String username) throws SQLException {
        try {
            DataManager.deleteUser(username);
            System.out.println("User has been deleted!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
