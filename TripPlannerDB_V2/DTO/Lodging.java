package database.DTO;

public class Lodging extends DTO {
    private Integer ID;
    private String name;
    private Address address;

    //default Constructor
    public Lodging(){ this.address = new Address(); }

    //getLodgingByID Constructor
    public Lodging(Integer ID){
        this.ID = ID;
    }

    //saveLodging Constructor
    public Lodging(Integer ID, String name, Address address){
        this.ID = ID;
        this.name = name;
        this.address = address;
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public String getName(){
        return this.name;
    }
    public Address getAddress(){
        return this.address;
    }

    //Setters
    public void setID(Integer ID){
        this.ID = ID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAddress(Address address){
        this.address = address;
    }
}
