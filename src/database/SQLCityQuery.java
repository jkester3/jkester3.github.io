package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCityQuery extends SQLQuery {
    public SQLCityQuery() {
        super();
    }

    public void createCityQuery(final City city) throws SQLException {
        String query = "INSERT INTO CITY (name, address, latitude, longitude, " +
                "itineraryID) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, city.getName());
        preparedStatement.setString(2, city.getAddress());
        preparedStatement.setDouble(3, city.getLatitude());
        preparedStatement.setDouble(4, city.getLongitude());
        preparedStatement.setInt(5, city.getItineraryID());
        preparedStatement.executeUpdate();
    }

    public int getCityIDByName(final String cityName) throws SQLException {
        final String query =
                "SELECT ID FROM CITY WHERE CITY.name = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, cityName);
        ResultSet result = preparedStatement.executeQuery();
        int cityID = -1;
        while (result.next()) {
            cityID  = result.getInt("ID");
            break;
        }
        return cityID;
    }

    public List<City> getCitiesByItineraryID(final int itineraryID)
            throws SQLException {
        final String query =
                "SELECT * FROM CITY WHERE CITY.itineraryID = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, itineraryID);
        ResultSet result = preparedStatement.executeQuery();
        List<City> cities = new ArrayList<City>();
        while (result.next()) {
            int ID = result.getInt("ID");
            String name = result.getString("name");
            String address = result.getString("address");
            double latitude = result.getDouble("latitude");
            double longitude = result.getDouble("longitude");
            final City city = new City(name, address, latitude, longitude,
                    itineraryID);
            city.setID(ID);
            cities.add(city);
        }
        return cities;
    }
}
