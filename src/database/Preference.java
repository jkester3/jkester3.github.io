package database;

/**
 * Created by Joseph on 6/24/2014.
 */
public class Preference {
    private int ID;
    private float minimumRating;
    private String priceCategory;
    private int maxDistance;
    private String preferredFoodType;
    private String preferredAttractionType;

    public Preference() {
        ID = maxDistance = 0;
        priceCategory = preferredFoodType = preferredAttractionType = "";
    }

    public Preference(float minimumRating, String priceCategory,
                      int maxDistance, String preferredAttractionType,
                      String preferredFoodType){
        this.minimumRating = minimumRating;
        this.priceCategory = priceCategory;
        this.maxDistance = maxDistance;
        this.preferredAttractionType = preferredAttractionType;
        this.preferredFoodType = preferredFoodType;
    }

    public int getID(){ return this.ID; }
    public float getMinimumRating() { return this.minimumRating; }
    public String getPriceCategory() { return this.priceCategory; }
    public int getMaxDistance() { return this.maxDistance; }
    public String getPreferredFoodType() { return this.preferredFoodType; }
    public String getPreferredAttractionType() {
        return this.preferredAttractionType;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setMinimumRating(float minimumRating) {
        this.minimumRating = minimumRating;
    }
    public void setPriceCategory(String priceCategory) {
        this.priceCategory = priceCategory;
    }
    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
    public void setPreferredFoodType(String preferredFoodTypee) {
        this.preferredFoodType = preferredFoodType;
    }
    public void setPreferredAttractionType(String preferredAttractionType) {
        this.preferredAttractionType = preferredAttractionType;
    }
}
