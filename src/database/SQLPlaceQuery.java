package database;

import model.Coordinates;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLPlaceQuery extends SQLQuery {
    public SQLPlaceQuery() {
        super();
    }

    public void createEventQuery(final Place event, final String type,
                                 final int cityID)
        throws SQLException {
        createEventOrLodging(event, type, cityID);
    }

    public void createLodgingQuery(final Place lodging, final String type,
                                   final int cityID)
        throws SQLException {
        createEventOrLodging(lodging, type, cityID);
    }

    private void createEventOrLodging(final Place place, final String type,
                                      final int cityID)
        throws SQLException {
        String query = "INSERT INTO PLACE (cityID, placeType, name, " +
                "address, phoneNumber, apiType, priceLevel, rating, latitude," +
                "longitude, url, creationDate) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, cityID);
        preparedStatement.setString(2, type);
        preparedStatement.setString(3, place.getName());
        preparedStatement.setString(4, place.getFormattedAddress());
        preparedStatement.setString(5, place.getPhoneNumber());
        preparedStatement.setString(6, place.getAPI());
        preparedStatement.setInt(7, place.getPriceLevel());
        preparedStatement.setDouble(8, place.getRating());
        preparedStatement.setDouble(9, place.getCoordinates().getLat());
        preparedStatement.setDouble(10, place.getCoordinates().getLng());
        preparedStatement.setString(11, place.getURL());
        preparedStatement.setString(12, place.getCreationDate().toString());
        preparedStatement.executeUpdate();
    }

    public Place getLodgingByCityID(final int cityID) throws SQLException {
        final String query =
                "SELECT * FROM PLACE WHERE PLACE.cityID = ? AND " +
                        "PLACE.placeType = ?;";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, cityID);
        preparedStatement.setString(2, "lodging");
        ResultSet result = preparedStatement.executeQuery();
        Place lodging = null;
        while (result.next()) {
            int ID = result.getInt("ID");
            String name = result.getString("name");
            String address = result.getString("address");
            String phoneNumber = result.getString("phoneNumber");
            int priceLevel = result.getInt("priceLevel");
            double rating = result.getDouble("rating");
            double latitude = result.getDouble("latitude");
            double longitude = result.getDouble("longitude");
            Coordinates coordinates = new Coordinates(latitude, longitude);
            String url = result.getString("url");
            String creationDate = result.getString("creationDate");
            String checkIn = result.getString("checkIn");
            String checkOut = result.getString("checkOut");
            lodging = new Place();
            lodging.setID(ID);
            lodging.setName(name);
            lodging.setFormattedAddress(address);
            lodging.setPhoneNumber(phoneNumber);
            lodging.setPriceLevel(priceLevel);
            lodging.setRating(rating);
            lodging.setCoordinates(coordinates);
            lodging.setURL(url);
            lodging.setCreationDate(creationDate);
            lodging.setCheckIn(checkIn);
            lodging.setCheckOut(checkOut);
            break;
        }
        return lodging;
    }

    protected void updatePlaceTime(final Place place, final String placeType)
        throws SQLException {
        if (placeType.equals("lodging")) {
            updateLodgingTime(place);
        } else if (placeType.equals("event")) {
            updateEventTime(place);
        }
    }

    private void updateLodgingTime(final Place lodging) throws SQLException {
        final String query = "UPDATE PLACE SET checkIn = ?, checkOut = ? " +
                "WHERE name = ? AND placeType = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, lodging.getCheckIn());
        preparedStatement.setString(2, lodging.getCheckOut());
        preparedStatement.setString(3, lodging.getName());
        preparedStatement.setString(4, "lodging");
        preparedStatement.executeUpdate();
    }

    private void updateEventTime(final Place event) throws SQLException {
        final String query = "UPDATE PLACE SET checkIn = ?, checkOut = ? " +
                "WHERE name = ? AND placeType = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, event.getCheckIn());
        preparedStatement.setString(2, event.getCheckOut());
        preparedStatement.setString(3, event.getName());
        preparedStatement.setString(4, "event");
        preparedStatement.executeUpdate();
    }

    public List<Place> getEventsByCityID(final int cityID)
            throws SQLException {
        final String query =
                "SELECT * FROM PLACE WHERE PLACE.cityID = ? AND " +
                        "PLACE.placeType = ?;";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, cityID);
        preparedStatement.setString(2, "event");
        ResultSet results = preparedStatement.executeQuery();
        List<Place> events = null;
        if (results.isBeforeFirst()) events = new ArrayList<Place>();
        while (results.next()) {
            int ID = results.getInt("ID");
            String name = results.getString("name");
            String address = results.getString("address");
            String phoneNumber = results.getString("phoneNumber");
            String apiType = results.getString("apiType");
            int priceLevel = results.getInt("priceLevel");
            double rating = results.getDouble("rating");
            double latitude = results.getDouble("latitude");
            double longitude = results.getDouble("longitude");
            Coordinates coordinates = new Coordinates(latitude, longitude);
            String url = results.getString("url");
            String creationDate = results.getString("creationDate");
            String checkIn = results.getString("checkIn");
            String checkOut = results.getString("checkOut");
            Place event = new Place();
            event.setID(ID);
            event.setName(name);
            event.setFormattedAddress(address);
            event.setPhoneNumber(phoneNumber);
            event.setAPI(apiType);
            event.setPriceLevel(priceLevel);
            event.setRating(rating);
            event.setCoordinates(coordinates);
            event.setURL(url);
            event.setCreationDate(creationDate);
            event.setCheckIn(checkIn);
            event.setCheckOut(checkOut);
            events.add(event);
        }
        return events;
    }

    public void deleteAllPlacesByCityID(final String cityID)
            throws SQLException {
        final String query = "DELETE FROM PLACE WHERE PLACE.cityID = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, cityID);
        preparedStatement.executeUpdate();
    }

    public void deletePlaceByAttributes(final Place place)
        throws SQLException {
        final String query = "DELETE FROM PLACE WHERE PLACE.name = ? " +
                "AND PLACE.address = ? AND PLACE.phoneNumber = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, place.getName());
        preparedStatement.setString(2, place.getFormattedAddress());
        preparedStatement.setString(3, place.getPhoneNumber());
        preparedStatement.executeUpdate();
    }
}
