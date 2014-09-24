package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 7/15/2014.
 */
public class SQLBudgetQuery extends SQLQuery {
    public SQLBudgetQuery() { super(); }

    public Budget getBudgetByID(int budgetID)
            throws SQLException {
        Budget budget = new Budget();
        try {
            CallableStatement callableStatement = super.dbConnection.prepareCall("{call Budget_ReadByID(?)}");
            callableStatement.setInt(1, budgetID);
            ResultSet results = callableStatement.executeQuery();
            if (results.next()) {
                Integer ID = results.getInt("ID");
                Double originalBudget = results.getDouble("originalBudget");
                Double currentBudget = results.getDouble("currentBudget");
                Timestamp lastUpdated = results.getTimestamp("lastUpdated");

                //Initialize nested objects
                List<Expenditure> expenditures = getExpendituresByBudgetID(budgetID);

                budget = new Budget(ID, originalBudget, currentBudget,
                        expenditures, lastUpdated);
            }
            DbUtil.close(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budget;
    }

    public List<Expenditure> getExpendituresByBudgetID(Integer budgetID)
            throws SQLException {
        List<Expenditure> expenditures = new ArrayList<Expenditure>();
        try {
            CallableStatement callableStatement = super.dbConnection.prepareCall
                    ("{call Expenditure_ReadByBudgetID(?)}");
            callableStatement.setInt(1, budgetID);
            ResultSet results = callableStatement.executeQuery();
            while(results.next()) {
                Integer ID = results.getInt("ID");
                String description = results.getString("expenseDescription");
                Double amountSpent = results.getDouble("expenseAmount");
                Timestamp expenseDate = results.getTimestamp("expenseDate");
                budgetID = results.getInt("budgetID");

                Expenditure expenditure = new Expenditure(ID, description,
                        amountSpent, expenseDate, budgetID);
                expenditures.add(expenditure);
            }
            DbUtil.close(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenditures;
    }

    public Integer saveBudget(Budget budget) throws SQLException {
        //If budgetID is null a new row will be added to DB budget table,
        //however if budgetID is not null then row corresponding to ID will be updated.
        Integer budgetID = budget.getID();
        Double originalBudget = budget.getOriginalBudget();
        Double currentBudget = budget.getCurrentBudget();
        try {
            CallableStatement callableStatement = super.dbConnection.prepareCall("{call Budget_Save(?,?,?,?)}");
            if(budgetID == null || budgetID == 0) {
                callableStatement.setNull(1, 0);
            } else {
                callableStatement.setInt(1, budgetID);
            }
            if(originalBudget == null || originalBudget == 0) {
                callableStatement.setNull(2, 0);
            } else {
                callableStatement.setDouble(2, originalBudget);
            }
            if(currentBudget == null || currentBudget == 0) {
                callableStatement.setNull(3, 0);
            } else {
                callableStatement.setDouble(3, currentBudget);
            }
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.executeUpdate();
            budgetID = callableStatement.getInt(4);

            for (Expenditure expenditure : budget.getExpenditures()) {
                if (budgetID != 0) {
                    expenditure.setBudgetID(budgetID);
                }
                saveExpenditure(expenditure);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgetID;
    }

    public Integer saveExpenditure(Expenditure expenditure)
            throws SQLException {
        //If expenditureID is null a new row will be added to DB expenditure table,
        //however if expenditureID is not null then row corresponding to ID will be updated.
        Integer expenditureID = expenditure.getID();
        Integer budgetID = expenditure.getBudgetID();
        try {
            CallableStatement callableStatement = super.dbConnection.prepareCall
                    ("{call Expenditure_Save(?,?,?,?,?,?)}");
            if (expenditureID == null || expenditureID == 0) {
                callableStatement.setNull(1, 0);
            } else {
                callableStatement.setInt(1, expenditureID);
            }
            callableStatement.setString(2, expenditure.getDescription());
            callableStatement.setDouble(3, expenditure.getAmountSpent());
            callableStatement.setTimestamp(4, expenditure.getExpenseDate());
            if (budgetID == null || budgetID == 0) {
                callableStatement.setNull(5, budgetID);
            } else {
                callableStatement.setInt(5, budgetID);
            }
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.executeUpdate();
            expenditureID = callableStatement.getInt(6);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenditureID;
    }

}
