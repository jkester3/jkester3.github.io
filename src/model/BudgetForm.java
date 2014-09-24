package model;

import database.Budget;
import database.DataManager;
import database.Expenditure;
import database.Itinerary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 7/15/2014.
 */
public class BudgetForm {
    private HttpServletRequest request;
    private HttpSession session;
    private Integer itineraryID;
    private Integer budgetID;
    private Double originalBudget;
    private Double currentBudget;
    private Double totalSpent;
    private String[] amountsSpent, expenseDescriptions, expenseDates;
    private Budget budget;
    private List<Expenditure> expenditures;

    public BudgetForm(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
        this.budget = new Budget();
        this.totalSpent = 0.0;
        this.expenditures = new ArrayList<Expenditure>();
    }

    public void createBudget() {
        gatherNewBudgetInfo();
        saveNewBudget();
    }

    public void createExpenditures() {
        gatherNewExpenditureInfo();
        saveExpendituresAndUpdateBudget();
    }

    private void gatherNewBudgetInfo() {
        itineraryID = Integer.parseInt(request.getParameter("itineraryID"));
        originalBudget = Double.parseDouble(request.getParameter("originalBudget"));
    }

    private void saveNewBudget() {
        budget = new Budget(originalBudget);
        try {
            final int budgetID = DataManager.saveBudget(budget);
            DataManager.updateItineraryBudget(itineraryID, budgetID);
            Itinerary current = (Itinerary) session.getAttribute("activeItinerary");
            current.setBudgetID(budgetID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void gatherNewExpenditureInfo() {
        budgetID = Integer.parseInt(request.getParameter("budgetID"));
        currentBudget = Double.parseDouble(request.getParameter("currentBudget"));
        expenseDescriptions = request.getParameterValues("expenseDescription");
        amountsSpent = request.getParameterValues("amountSpent");
        expenseDates = request.getParameterValues("expenseDate");
        parseNewExpenditureInfo();
    }

    private void parseNewExpenditureInfo() {
        expenditures = new ArrayList<Expenditure>();
        for (int i = 0; i < expenseDescriptions.length; i++) {
            Double amountSpent = Double.parseDouble(amountsSpent[i]);
            totalSpent = totalSpent + amountSpent;
            expenseDates[i] = convertToTimestampFormat(expenseDates[i]);

            Expenditure expenditure = new Expenditure(null,
                    expenseDescriptions[i], amountSpent,
                    new Timestamp(0).valueOf(expenseDates[i]),
                    budgetID);
            expenditures.add(expenditure);
        }
    }

    private void saveExpendituresAndUpdateBudget() {
        updateCurrentBudget();
        budget.setID(budgetID);
        budget.setExpenditures(expenditures);
        budget.setCurrentBudget(currentBudget);
        try {
            DataManager.saveBudget(budget);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentBudget() {
        currentBudget = currentBudget - totalSpent;
    }

    private String convertToTimestampFormat(String strDate) {
        strDate = strDate.replace("/", "-");
        strDate = strDate.concat(":00.0");
        return strDate;
    }
}
