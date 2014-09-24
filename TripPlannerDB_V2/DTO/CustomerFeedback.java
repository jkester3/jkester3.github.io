package database.DTO;

public class CustomerFeedback extends DTO {
    private Integer ID;
    private String customerName;
    private Double customerRating;
    private String customerReview;

    //default Constructor
    public CustomerFeedback(){}

    //getCustomerFeedbackByID Constructor
    public CustomerFeedback(Integer ID){
        this.ID = ID;
    }

    //saveCustomerFeedback Constructor
    public CustomerFeedback(Integer ID, String customerName,
                            Double customerRating, String customerReview){
        this.ID = ID;
        this.customerName = customerName;
        this.customerRating = customerRating;
        this.customerReview = customerReview;
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public String getCustomerName(){
        return this.customerName;
    }
    public Double getCustomerRating(){
        return this.customerRating;
    }
    public String getCustomerReview(){
        return this.customerReview;
    }

    //Setters
    public void setID(Integer ID){
        this.ID = ID;
    }
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public void setCustomerRating(Double customerRating){
        this.customerRating = customerRating;
    }
    public void setCustomerReview(String customerReview){
        this.customerReview = customerReview;
    }
}
