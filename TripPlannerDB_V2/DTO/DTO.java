package database.DTO;

public class DTO {
    protected boolean isDirty;

    //Getters
    public boolean getIsDirtyFlag(){
        return this.isDirty;
    }

    //Setters
    public void setIsDirtyFlag(boolean isDirty){
        this.isDirty = isDirty;
    }
}
