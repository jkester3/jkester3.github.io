package database.DTO;

public class Address extends DTO {
    private Integer ID;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    //default Constructor
    public Address(){}

    //getAddressByID Constructor
    public Address(Integer ID){
        this.ID = ID;
    }

    //saveAddress Constructor
    public Address(Integer ID, String streetAddress, String city,
                   String state, String zipCode){
        this.ID = ID;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public String getStreetAddress(){
        return this.streetAddress;
    }
    public String getCity(){
        return this.city;
    }
    public String getState(){
        return this.state;
    }
    public String getZipCode(){
        return this.zipCode;
    }

    //Setters
    public void setID(Integer ID){
        this.ID = ID;
    }
    public void setStreetAddress(String streetAddress){
        this.streetAddress = streetAddress;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
}
