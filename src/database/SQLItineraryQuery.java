package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLItineraryQuery extends SQLQuery {
    public SQLItineraryQuery() {
        super();
    }

    public void createItineraryQuery(Itinerary itinerary) throws SQLException {
        String query = "INSERT INTO ITINERARY (name, userID, transportation, " +
                "creationDate, preferenceID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, itinerary.getName());
        preparedStatement.setInt(2, itinerary.getUserID());
        preparedStatement.setString(3, itinerary.getTransportationMode());
        preparedStatement.setString(4, itinerary.getCreationDate());
        preparedStatement.setInt(5, itinerary.getPreferenceID());
        preparedStatement.executeUpdate();
    }

    public void updateItineraryBudgetQuery(int itineraryID, int budgetID) throws SQLException {
        final String query = "UPDATE itinerary SET budgetID = ? WHERE itinerary.ID = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, budgetID);
        preparedStatement.setInt(2, itineraryID);
        preparedStatement.executeUpdate();
    }

    public void deleteItineraryQuery(String itineraryID) throws SQLException {
        String query = "DELETE FROM ITINERARY WHERE ITINERARY.ID = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, itineraryID);
        preparedStatement.executeUpdate();
    }

    public Itinerary getItineraryByID(final String ID) throws SQLException {
        final String query = "SELECT * FROM itinerary WHERE ID = ?;";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, ID);
        ResultSet results = preparedStatement.executeQuery();
        Itinerary itinerary = new Itinerary();
        while (results.next()) {
            String name = results.getString("name");
            String transportationMode = results.getString("transportation");
            String creationDate = results.getString("creationDate");
            int intID = Integer.parseInt(ID);
            int userID = results.getInt("userID");
            int preferenceID = results.getInt("preferenceID");
            int budgetID = results.getInt("budgetID");
            itinerary = new Itinerary(name, transportationMode, creationDate,
                    intID, userID, preferenceID);
            itinerary.setBudgetID(budgetID);
            break;
        }
        return itinerary;
    }

    public Itinerary getItineraryByName(final String itineraryName)
            throws SQLException {
        Itinerary itinerary = new Itinerary();
        final String query = "SELECT * FROM itinerary " +
                "WHERE name = ?;";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, itineraryName);
        ResultSet results = preparedStatement.executeQuery();
        while(results.next()) {
            int ID = results.getInt("ID");
            int userID = results.getInt("userID");
            int preferenceID = results.getInt("preferenceID");
            int budgetID = results.getInt("budgetID");
            String name = results.getString("name");
            String transportationMode = results.getString("transportation");
            String creationDate = results.getString("creationDate");
            itinerary = new Itinerary(name, transportationMode,
                    creationDate, ID, userID, preferenceID);
            itinerary.setBudgetID(budgetID);
            break;
        }
        return itinerary;
    }

    public List<Itinerary> getItinerariesByUserID(final int userID)
            throws SQLException {
        List<Itinerary> itineraries = new ArrayList<Itinerary>();
        final String query = "SELECT * FROM itinerary " +
                "WHERE userID = ?;";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, userID);
        ResultSet results = preparedStatement.executeQuery();
        while(results.next()) {
            int ID = results.getInt("ID");
            int preferenceID = results.getInt("preferenceID");
            int budgetID = results.getInt("budgetID");
            String name = results.getString("name");
            String transportationMode = results.getString("transportation");
            String creationDate = results.getString("creationDate");
            Itinerary itinerary = new Itinerary(name, transportationMode,
                    creationDate, ID, userID, preferenceID);
            itinerary.setBudgetID(budgetID);
            itineraries.add(itinerary);
        }
        return itineraries;
    }
}
