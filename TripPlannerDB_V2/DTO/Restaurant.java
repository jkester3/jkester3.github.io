package database.DTO;
import database.DTO.Enums.*;

public class Restaurant extends Place {
    private Integer ID;
    private RestaurantType restaurantType;
    private PriceCategory priceCategory;

    //default Constructor
    public Restaurant(){}

    //getRestaurantByID Constructor
    public Restaurant(Integer ID){
        this.ID = ID;
    }

    //saveRestaurant Constructor
    public Restaurant(Integer ID, Integer restaurantType, Integer priceCategory){
        this.ID = ID;
        this.restaurantType = RestaurantType.values()[restaurantType];
        this.priceCategory = PriceCategory.values()[priceCategory];
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public RestaurantType getRestaurantType(){
        return this.restaurantType;
    }
    public int getRestaurantTypeValue(){ return this.restaurantType.ordinal(); }
    public PriceCategory getPriceCategory(){
        return this.priceCategory;
    }
    public int getPriceCategoryValue(){
        return this.priceCategory.ordinal();
    }

    //Setters
    public void setID(int ID){
        this.ID = ID;
    }
    public void setRestaurantType(String stringValue){
        this.restaurantType = RestaurantType.valueOf(stringValue);
    }
    public void setPriceCategory(String stringValue){
        this.priceCategory = PriceCategory.valueOf(stringValue);
    }
}
