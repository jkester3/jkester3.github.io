package database.DTO;
import database.DTO.Enums.UserRole;
import java.util.List;
import java.util.ArrayList;

public class User extends DTO {
    private Integer ID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private UserRole userRole;
    private Preference defaultPreference;
    private List<Trip> trips;

    //default Constructor
    public User(){ this.defaultPreference = new Preference(); }

    //getUserByID Constructor
    public User(Integer ID){ this.ID = ID; }

    //getUserByUsername Constructor
    public User(String userName) { this.userName = userName; }

    //minimum requirements Constructor
    public User(String firstName, String lastName, String userName,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = null;
        this.setGeneralUser();
        this.defaultPreference = new Preference();
        this.trips = new ArrayList<Trip>();
    }

    //saveUser Constructor
    public User(Integer ID, String firstName, String lastName,
                String userName, String password, String email,
                Integer userRole, Preference preference,
                List<Trip> trips) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userRole = UserRole.values()[userRole];
        this.defaultPreference = preference;
        this.trips = trips;
    }

    //Getters
    public Integer getID(){
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
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public UserRole getUserRole(){
        return this.userRole;
    }
    public int getUserRoleValue(){
        return this.userRole.ordinal();
    }
    public Preference getPreference(){
        return this.defaultPreference;
    }
    public List<Trip> getTrips(){
        return this.trips;
    }

    //Setters
    public void setID(Integer ID){
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
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setGeneralUser() {
        this.userRole = UserRole.General;
    }
    public void setPreference(Preference preference){
        this.defaultPreference = preference;
    }
    public void setTrips(List<Trip> trips){
        this.trips = trips;
    }
}
