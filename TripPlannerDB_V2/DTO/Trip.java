package database.DTO;
import database.DTO.Enums.TransportationMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip extends DTO {
    private Integer ID;
    private Lodging lodging;
    private TransportationMode transportationMode;
    private Itinerary itinerary;
    private Preference preference;
    private Date startDateTime;
    private Date endDateTime;
    private List<AvailableTime> availableTimes;

    public Trip() {
        lodging = new Lodging();
        transportationMode = TransportationMode.Bike;
        itinerary = new Itinerary();
        preference = new Preference();
        startDateTime = new Date();
        endDateTime = new Date();
        availableTimes = new ArrayList<AvailableTime>();
    }

    //getTripByID Constructor
    public Trip(Integer ID) { this.ID = ID; }

    //saveTrip Constructor
    public Trip(Integer ID, Lodging lodging, Integer transportation,
                Itinerary itinerary, Preference preference, Date startDateTime,
                Date endDateTime, List<AvailableTime> availableTimes){
        this.ID = ID;
        this.lodging = lodging;
        this.transportationMode = TransportationMode.values()[transportation];
        this.itinerary = itinerary;
        this.preference = preference;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.availableTimes = availableTimes;
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public Lodging getLodging(){
        return this.lodging;
    }
    public TransportationMode getTransportationMode(){
        return this.transportationMode;
    }
    public Itinerary getItinerary(){ return this.itinerary; }
    public Preference getPreference() { return this.preference; }
    public Date getStartDateTime(){
        return this.startDateTime;
    }
    public Date getEndDateTime(){
        return this.endDateTime;
    }
    public List<AvailableTime> availableTimes() { return this.availableTimes; }

    //Setters
    public void setID(int ID){
        this.ID = ID;
    }
    public void setLodging(Lodging lodging){
        this.lodging = lodging;
    }
    public void setTransportationMode(String stringValue){
        this.transportationMode = TransportationMode.valueOf(stringValue);
    }
    public void setItinerary(Itinerary itinerary){
        this.itinerary = itinerary;
    }
    public void setPreference(Preference preference) { this.preference = preference; }
    public void setStartDateTime(Date startDateTime){
        this.startDateTime = startDateTime;
    }
    public void setEndDateTime(Date endDateTime){
        this.endDateTime = endDateTime;
    }
    public void setAvailableTimes(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
