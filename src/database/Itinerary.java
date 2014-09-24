package database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Itinerary {
    private int ID;
    private int userID;
    private int preferenceID;
    private int budgetID;
    private String name;
    private String transportationMode, creationDate;

    public Itinerary() {
        this.budgetID = this.userID = this.preferenceID = this.ID = 0;
        this.name = this.transportationMode = "";
    }

    public Itinerary(String name, String transportationMode, int userID,
                     int preferenceID) {
        this.userID = userID;
        this.name = name;
        this.transportationMode = transportationMode;
        this.creationDate = generateFormattedCreationDate();
        this.preferenceID = preferenceID;
    }

    public Itinerary(String name, String transportationMode, String creationDate,
                     int ID, int userID, int preferenceID) {
        this.ID = ID;
        this.userID = userID;
        this.preferenceID = preferenceID;
        this.name = name;
        this.transportationMode = transportationMode;
        this.creationDate = creationDate;
    }

    private String generateFormattedCreationDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(new Date());
        return todayDate;
    }

    public String getName() {
        return name;
    }
    public String getTransportationMode() {
        return transportationMode;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public int getID() {
        return ID;
    }
    public int getUserID() {
        return userID;
    }
    public int getPreferenceID() { return preferenceID; }
    public int getBudgetID() { return budgetID; }
    public void setID(final int ID) { this.ID = ID; }
    public void setBudgetID(int budgetID) { this.budgetID = budgetID; }
}

