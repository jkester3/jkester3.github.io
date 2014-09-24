package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLPreferenceQuery extends SQLQuery {
    public SQLPreferenceQuery() { super(); }

    public int createPreferenceQuery(Preference preference) throws SQLException {
        String query = "INSERT INTO preference (minimumRating, priceCategory, maxDistance, "
                + "foodType, attractionType) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setFloat(1, preference.getMinimumRating());
        preparedStatement.setString(2, preference.getPriceCategory());
        preparedStatement.setInt(3, preference.getMaxDistance());
        preparedStatement.setString(4, preference.getPreferredAttractionType());
        preparedStatement.setString(5, preference.getPreferredFoodType());
        preparedStatement.executeUpdate();

        ResultSet results = preparedStatement.getGeneratedKeys();
        int lastID = 0;
        while (results.next()) {
            lastID = results.getBigDecimal(1).intValueExact();
        }
        return lastID;
    }

    public Preference getPreferencesByID(int ID)
            throws SQLException {
        final String query = "SELECT * FROM preference " +
                "WHERE ID = ?;";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, ID);
        ResultSet results = preparedStatement.executeQuery();
        Preference preference = new Preference();
        while (results.next()) {
            float minimumRating = results.getFloat("minimumRating");
            String priceCategory = results.getString("priceCategory");
            int maxDistance = results.getInt("maxDistance");
            String foodType = results.getString("foodType");
            String attractionType = results.getString("attractionType");
            preference = new Preference(minimumRating, priceCategory,
                    maxDistance, attractionType, foodType);
            preference.setID(ID);
            break;
        }
        return preference;
    }
}