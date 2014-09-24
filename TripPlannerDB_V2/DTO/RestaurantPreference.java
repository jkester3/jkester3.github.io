package database.DTO;
import database.DTO.Enums.RestaurantType;

public class RestaurantPreference extends DTO {
    private Integer ID;
    private RestaurantType preferredRestaurantType;

    //default Constructor
    public RestaurantPreference(){}

    //getRestaurantPreferenceByID Constructor
    public RestaurantPreference(Integer ID){ this.ID = ID; }

    //saveRestaurantPreference Constructor
    public RestaurantPreference(Integer ID, Integer restaurantPreference){
        this.ID = ID;
        this.preferredRestaurantType = RestaurantType.values()[restaurantPreference];
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public RestaurantType getPreferredRestaurantType(){
        return this.preferredRestaurantType;
    }
    public int getPreferredRestaurantTypeValue(){
        return this.preferredRestaurantType.ordinal();
    }

    //Setters
    public void setID(int ID){
        this.ID = ID;
    }
    public void setPreferredRestaurantType(String stringValue){
        this.preferredRestaurantType = RestaurantType.valueOf(stringValue);
    }
}
