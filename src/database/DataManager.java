package database;

import java.sql.SQLException;
import java.util.List;

public class DataManager {

    public static Budget fetchBudget(int budgetID) throws SQLException {
        Budget fetchedBudget = new SQLBudgetQuery().getBudgetByID(budgetID);
        return fetchedBudget;
    }

    public static int saveBudget(Budget budget) throws SQLException {
        int budgetID = 0;
        try {
            budgetID = new SQLBudgetQuery().saveBudget(budget);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return budgetID;
    }

    public static void createUser(User user) throws SQLException {
        try {
            new SQLUserQuery().createUserQuery(user);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error: username '" + user.getUsername()
                    + "' already exists. Please try again.");
        }
    }

    public static void deleteUser(final String username) throws SQLException {
        new SQLUserQuery().deleteUserQuery(username);
    }

    public static User fetchUser(String username) throws SQLException {
        User fetchedUser =  new SQLUserQuery().readUserQuery(username);
        return fetchedUser;
    }

    public static void updateUser(User user) throws SQLException {
        new SQLUserQuery().updateUserQuery(user);
    }

    public static void createItinerary(Itinerary itinerary) throws SQLException {
        new SQLItineraryQuery().createItineraryQuery(itinerary);
    }

    public static void updateItineraryBudget(int itineraryID, int budgetID) throws SQLException {
        new SQLItineraryQuery().updateItineraryBudgetQuery(itineraryID, budgetID);
    }

    public static void deleteItinerary(String itineraryID) throws SQLException {
        //DataManager.deleteAllPlacesAssociatedWithItinerary(itineraryID);
        final SQLItineraryQuery itineraryQuery = new SQLItineraryQuery();
        itineraryQuery.deleteItineraryQuery(itineraryID);
    }

    private static void deleteAllPlacesAssociatedWithItinerary(
            final String itineraryID) throws SQLException {
        SQLPlaceQuery placeQuery = new SQLPlaceQuery();
        placeQuery.deleteAllPlacesByCityID(itineraryID);
    }

    public static Itinerary getItineraryByName(final String itineraryName)
            throws SQLException {
        SQLItineraryQuery itineraryQuery = new SQLItineraryQuery();
        return  itineraryQuery.getItineraryByName(itineraryName);
    }


    public static List<Itinerary> getItineraryByUserID(int userID)
            throws SQLException {
        List<Itinerary> fetchedItneraries =
                new SQLItineraryQuery().getItinerariesByUserID(userID);
        return fetchedItneraries;
    }

    public static int createPreference(Preference preference) throws SQLException {
        int lastID = 0;
        try {
            lastID = new SQLPreferenceQuery().createPreferenceQuery(preference);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastID;
    }

    public static void createLodging(final Place lodging, final int cityID)
            throws SQLException {
        SQLPlaceQuery placeQuery = new SQLPlaceQuery();
        placeQuery.createLodgingQuery(lodging, "lodging", cityID);
    }

    public static void createEvent(final Place event, final int cityID)
        throws SQLException {
        SQLPlaceQuery placeQuery = new SQLPlaceQuery();
        placeQuery.createEventQuery(event, "event", cityID);
    }

    public static void deleteEventByEventAttributes(final Place place)
            throws SQLException {
        SQLPlaceQuery sqlQuery = new SQLPlaceQuery();
        sqlQuery.deletePlaceByAttributes(place);
    }

    public static void updatePlaceTimeByID(final Place place,
                                           final String placeType)
        throws SQLException {
        final SQLPlaceQuery placeQuery = new SQLPlaceQuery();
        placeQuery.updatePlaceTime(place, placeType);
    }

    public static void createCity(final City city) throws SQLException {
        SQLCityQuery cityQuery = new SQLCityQuery();
        cityQuery.createCityQuery(city);
    }

    public static int getCityIDByName(final String cityName)
            throws SQLException {
        SQLCityQuery cityQuery = new SQLCityQuery();
        return cityQuery.getCityIDByName(cityName);
    }

    public static List<City> getCitiesByItineraryID(final int itineraryID)
        throws SQLException {
        SQLCityQuery cityQuery = new SQLCityQuery();
        final List<City> cities = cityQuery.getCitiesByItineraryID(itineraryID);
        return cities;
    }

    public static List<City> getCitiesAndPlacesByItineraryID(final int itineraryID)
        throws SQLException {
        List<City> cities = getCitiesByItineraryID(itineraryID);
        for (City city : cities) {
            final int cityID = city.getID();
            final List<Place> events = getEventsByCityID(cityID);
            final Place lodging = getLodgingByCityID(cityID);
            city.setEvents(events);
            city.setLodging(lodging);
        }
        return cities;
    }

    public static List<Place> getEventsByCityID(final int cityID)
            throws SQLException {
        SQLPlaceQuery placeQuery = new SQLPlaceQuery();
        final List<Place> events = placeQuery.getEventsByCityID(cityID);
        return events;
    }

    public static Place getLodgingByCityID(final int cityID)
            throws SQLException {
        SQLPlaceQuery sqlQuery = new SQLPlaceQuery();
        final Place lodging = sqlQuery.getLodgingByCityID(cityID);
        return lodging;
    }
}
