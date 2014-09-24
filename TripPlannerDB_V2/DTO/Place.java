package database.DTO;

import java.sql.Time;
import database.DTO.Enums.PlaceType;

public class Place extends DTO {
    protected Integer placeID;
    protected PlaceType placeType;
    protected String name;
    protected String description;
    protected Address address;
    protected Time openTime;
    protected Time closeTime;

    //default Constructor
    public Place(){ this.address = new Address(); }

    //getPlaceByID Constructor
    public Place(Integer placeID){
        this.placeID = placeID;
    }

    //Getters
    public Integer getPlaceID(){
        return this.placeID;
    }
    public PlaceType getPlaceType(){
        return this.placeType;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public Address getAddress(){
        return this.address;
    }
    public Time getOpenTime(){
        return this.openTime;
    }
    public Time getCloseTime(){
        return this.closeTime;
    }

    //Setters
    public void setPlaceID(Integer placeID){
        this.placeID = placeID;
    }
    public void setPlaceType(String stringValue){
        this.placeType = PlaceType.valueOf(stringValue);
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setAddress(Address address){
        this.address = address;
    }
    public void setOpenTime(Time openTime){
        this.openTime = openTime;
    }
    public void setCloseTime(Time closeTime){
        this.closeTime = closeTime;
    }
}


