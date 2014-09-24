package database.DTO;
import database.DTO.Enums.PriceCategory;
import java.util.List;

public class Preference extends DTO {
    private Integer ID;
    private Double minimumRating;
    private PriceCategory priceCategory;
    private Integer maxDistance;
    private List<RestaurantPreference> restaurantPreferences;
    private List<AttractionPreference> attractionPreferences;

    //default Constructor
    public Preference() {
        ID = null;
    }

    //getPreferenceByID Constructor
    public Preference(Integer ID){
        this.ID = ID;
    }

    //savePreference
    public Preference(Integer ID, Double minimumRating, Integer priceCategory,
                      Integer maxDistance, List<RestaurantPreference> restaurantPreferences,
                      List<AttractionPreference> attractionPreferences){
        this.ID = ID;
        this.minimumRating = minimumRating;
        this.priceCategory = PriceCategory.values()[priceCategory];
        this.maxDistance = maxDistance;
        this.restaurantPreferences = restaurantPreferences;
        this.attractionPreferences = attractionPreferences;
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public Double getMinimumRating(){
        return this.minimumRating;
    }
    public PriceCategory getPriceCategory(){
        return this.priceCategory;
    }
    public int getPriceCategoryValue(){
        return this.priceCategory.ordinal();
    }
    public Integer getMaxDistance(){
        return this.maxDistance;
    }
    public List<RestaurantPreference> getRestaurantPreferences(){
        return this.restaurantPreferences;
    }
    public List<AttractionPreference> getAttractionPreferences(){
        return this.attractionPreferences;
    }

    //Setters
    public void setID(int ID){
        this.ID = ID;
    }
    public void setMinimumRating(Double minimumRating){
        this.minimumRating = minimumRating;
    }
    public void setPriceCategory(String stringValue){
        this.priceCategory = PriceCategory.valueOf(stringValue);
    }
    public void setMaxDistance(Integer maxDistance){
        this.maxDistance = maxDistance;
    }
    public void setRestaurantPreferences(List<RestaurantPreference> restaurantPreferences){
        this.restaurantPreferences = restaurantPreferences;
    }
    public void setAttractionPreferences(List<AttractionPreference> attractionPreferences){
        this.attractionPreferences = attractionPreferences;
    }
}
