package database;

import java.sql.Timestamp;

/**
 * Created by Joseph on 7/15/2014.
 */
public class Expenditure {
    private Integer ID;
    private String description;
    private Double amountSpent;
    private Timestamp expenseDate;
    private Integer budgetID;

    //Default Constructor
    public Expenditure() {}

    //getExpenditureByID Constructor
    public Expenditure(Integer ID) {
        this.ID = ID;
    }

    //saveExpenditure Constructor
    public Expenditure(Integer ID, String description, Double amountSpent,
                       Timestamp expenseDate, Integer budgetID) {
        this.ID = ID;
        this.description = description;
        this.amountSpent = amountSpent;
        this.expenseDate = expenseDate;
        this.budgetID = budgetID;
    }

    public boolean checkIfObjectIsNull() {
        return this.description == null && this.amountSpent == null &&
                this.expenseDate == null && this.budgetID == null;
    }

    //Getters
    public Integer getID() {
        return this.ID;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getAmountSpent() {
        return this.amountSpent;
    }

    public Timestamp getExpenseDate() {
        return this.expenseDate;
    }

    public Integer getBudgetID() {
        return this.budgetID;
    }

    //Setters
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmountSpent(Double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public void setExpenseDate(Timestamp expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setBudgetID(Integer budgetID) {
        this.budgetID = budgetID;
    }
}
