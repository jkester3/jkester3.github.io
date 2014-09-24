package database;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int ID;
    private String firstName;
    private String lastName;
    private String userName;
    private int password;
    private String email;
    private List<Itinerary> itineraries;

    public User() {
        itineraries = new ArrayList<Itinerary>();
    }

    public User(String firstName, String lastName, String userName,
                int password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = "";
    }

    public User(int userID, String firstName, String lastName, String userName,
                int password) {
        this.ID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = "";
    }

    public int getID(){
        return this.ID;
    }
    public String getWelcomeName() {
        return this. firstName + " " + this.lastName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getUsername(){
        return this.userName;
    }
    public int getPassword(){
        return this.password;
    }
    public String getEmail() {
        return email;
    }
    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(int password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
    public void deleteItineraryByID(final int itineraryID) {
        for (int i = 0; i < itineraries.size(); i++) {
            if (itineraries.get(i).getID() == itineraryID) {
                itineraries.remove(i);
                return;
            }
        }
    }
}
