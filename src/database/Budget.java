package database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 7/15/2014.
 */
public class Budget {
    private Integer ID;
    private Double originalBudget;
    private Double currentBudget;
    private Timestamp lastUpdated;
    private List<Expenditure> expenditures;

    //Default Constructor
    public Budget() {
        this.expenditures = new ArrayList<Expenditure>();
    }

    //getBudgetByID Constructor
    public Budget(Integer ID) {
        this.ID = ID;
    }

    //saveNewBudget Constructor
    public Budget(Double originalBudget) {
        this.originalBudget = originalBudget;
        this.currentBudget = originalBudget;
        this.expenditures = new ArrayList<Expenditure>();
    }

    //saveBudget Constructor
    public Budget(Integer ID, Double originalBudget,
                  Double currentBudget, List<Expenditure> expenditures,
                  Timestamp lastUpdated) {
        this.ID = ID;
        this.originalBudget = originalBudget;
        this.currentBudget = currentBudget;
        this.expenditures = expenditures;
        this.lastUpdated = lastUpdated;
    }

    public boolean checkIfObjectIsNull() {
        return this.originalBudget == null && this.currentBudget == null &&
                this.expenditures.size() < 1 && this.lastUpdated == null;
    }

    //Getters
    public Integer getID() {
        return this.ID;
    }

    public Double getOriginalBudget() {
        return this.originalBudget;
    }

    public Double getCurrentBudget() {
        return this.currentBudget;
    }

    public List<Expenditure> getExpenditures() {
        return this.expenditures;
    }

    public Timestamp getLastUpdated() {
        return this.lastUpdated;
    }

    public Double getPercentageSpent() {
        Double amountSpent = originalBudget - currentBudget;
        return amountSpent/originalBudget * 100;
    }
    //Setters
    public void setID(Integer ID) {
        this.ID = ID;
    }
    public void setOriginalBudget(Double originalBudget) {
        this.originalBudget = originalBudget;
    }
    public void setCurrentBudget(Double currentBudget) {
        this.currentBudget = currentBudget;
    }
    public void setExpenditures(List<Expenditure> expenditures) {
        this.expenditures = expenditures;
    }
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
