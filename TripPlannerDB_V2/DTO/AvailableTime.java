package database.DTO;
import java.util.Date;

public class AvailableTime extends DTO {
    private Integer ID;
    private Date startDateTime;
    private Date endDateTime;

    //default Constructor
    public AvailableTime(){}

    //getAvailableTimeByID Constructor
    public AvailableTime(Integer ID){
        this.ID = ID;
    }

    //saveAvailableTime Constructor
    public AvailableTime(Integer ID, Date startDateTime, Date endDateTime){
        this.ID = ID;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    //Getters
    public Integer getID(){
        return this.ID;
    }
    public Date getStartDateTime(){
        return this.startDateTime;
    }
    public Date getEndDateTime(){
        return this.endDateTime;
    }

    //Setters
    public void setID(Integer ID){
        this.ID = ID;
    }
    public void setStartDateTime(Date startDateTime){
        this.startDateTime = startDateTime;
    }
    public void setEndDateTime(Date endDateTime){
        this.endDateTime = endDateTime;
    }
}
