package model;

import database.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class CreateItineraryForm {
    private HttpServletRequest request;
    private HttpSession session;

    public CreateItineraryForm(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
        gatherNewItineraryInfo();
    }

    private void gatherNewItineraryInfo() {
        Itinerary newItinerary = new Itinerary();
        String name = request.getParameter("itineraryName");
        String address = request.getParameter("itineraryAddress");
        Coordinates coordinates = getCoordinates();
        String transportation = request.getParameter("itineraryTransportation");
        float minRating = Float.parseFloat(request.getParameter("minRating").trim());
        String priceCategory = request.getParameter("priceCategory");
        int maxDistance = Integer.parseInt(request.getParameter("maxDistance").trim());
        String foodType = request.getParameter("preferredFoodType");
        String attrType = request.getParameter("preferredAttrType");
        Preference preference = new Preference(minRating, priceCategory, maxDistance,
                foodType, attrType);
        final int preferenceID = savePreference(preference);
        User user = (User) session.getAttribute("currentUser");
        final int userID = user.getID();
        newItinerary = new Itinerary(name, transportation, userID, preferenceID);
        final int newItineraryID = saveItinerary(newItinerary);
        final City newCity = new City(address, address, coordinates.getLat(),
                coordinates.getLng(), newItineraryID);
        saveCity(newCity);
        newItinerary.setID(newItineraryID);
        user.getItineraries().add(newItinerary);
        session.setAttribute("currentUser", user);
    }

    private Coordinates getCoordinates() {
        String coordinates = request.getParameter("coordinates");
        int latBeginIndex = coordinates.indexOf("(") + 1;
        int lngBeginIndex = coordinates.indexOf(",") + 1;
        String latitude = coordinates.substring(latBeginIndex,
                lngBeginIndex - 1);
        String longitude = coordinates.substring(lngBeginIndex,
                coordinates.length() - 1);
        float lat = Float.parseFloat(latitude);
        float lng = Float.parseFloat(longitude);
        return new Coordinates(lat, lng);
    }

    private int savePreference(Preference preference){
        int preferenceID = 0;
        try{
            preferenceID = DataManager.createPreference(preference);
        } catch (SQLException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return preferenceID;
    }

    private int saveItinerary(Itinerary itinerary) {
        try {
            DataManager.createItinerary(itinerary);
            Itinerary loaded = DataManager.getItineraryByName(itinerary.getName());
            return loaded.getID();
        } catch (SQLException ex) {
            request.setAttribute("error", ex.getMessage());
            return -1;
        }
    }

    private void saveCity(City city) {
        try {
            DataManager.createCity(city);
        } catch (SQLException ex) {
            request.setAttribute("error", ex.getMessage());
        }
    }
}