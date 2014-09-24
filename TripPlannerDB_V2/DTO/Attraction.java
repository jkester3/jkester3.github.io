package database.DTO;
import database.DTO.Enums.AttractionType;

public class Attraction extends Place {
    private Integer ID;
    private AttractionType attractionType;
    private Double cost;

    //default Constructor
    public Attraction(){}

    //getAttractionByID Constructor
    public Attraction(Integer ID){
        this.ID = ID;
    }

    //saveAttraction Constructor
    public Attraction(Integer ID, Integer attractionType, Double cost){
        this.ID = ID;
        this.attractionType = AttractionType.values()[attractionType];
        this.cost = cost;
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public AttractionType getAttractionType(){
        return this.attractionType;
    }
    public Double getCost(){
        return this.cost;
    }

    //Setters
    public void setID(Integer ID){
        this.ID = ID;
    }
    public void setAttractionType(String stringValue){
        this.attractionType = AttractionType.valueOf(stringValue);
    }
    public void setCost(Double cost){
        this.cost = cost;
    }
}
