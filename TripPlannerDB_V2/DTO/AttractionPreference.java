package database.DTO;
import database.DTO.Enums.AttractionType;

public class AttractionPreference extends DTO {
    private Integer ID;
    private AttractionType preferredAttractionType;

    //default Constructor
    public AttractionPreference(){}

    //getAttractionPreferenceByID Constructor
    public AttractionPreference(Integer ID){
        this.ID = ID;
    }

    //saveAttractionPreference Constructor
    public AttractionPreference(Integer ID, Integer attractionType){
        this.ID = ID;
        this.preferredAttractionType = AttractionType.values()[attractionType];
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public AttractionType getPreferredAttractionType(){
        return this.preferredAttractionType;
    }

    //Setters
    public void setID(Integer ID){
        this.ID = ID;
    }
    public void setPreferredAttractionType(String stringValue){
        this.preferredAttractionType = AttractionType.valueOf(stringValue);
    }
}
