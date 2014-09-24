package model;

import controller.BrowserErrorHandling;
import database.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItineraryLoader {

    public void loadItineraryAndAllDependencies(HttpServletRequest request,
                                                HttpServletResponse response)
            throws IOException, ServletException {
        try {
            Itinerary activeItinerary = loadActiveItinerary(request);
            loadActivePreferences(activeItinerary, request);
            request.setAttribute("defaultSection", "event-places-page");
            request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private Itinerary loadActiveItinerary(HttpServletRequest request)
            throws SQLException {
        final HttpSession session = request.getSession();
        final String queryString = request.getQueryString();
        final int startIndex = queryString.indexOf("=") + 1;
        final String itineraryID = queryString.substring(startIndex);
        final int itineraryIntID = Integer.parseInt(itineraryID);
        SQLItineraryQuery query = new SQLItineraryQuery();
        Itinerary activeItinerary = query.getItineraryByID(itineraryID);
        session.setAttribute("activeItinerary", activeItinerary);
        loadCities(request, itineraryIntID);
        return activeItinerary;
    }

    private void loadCities(final HttpServletRequest request,
                            final int itineraryID) throws SQLException {
        final HttpSession session = request.getSession();
        List<City> cities = DataManager.getCitiesAndPlacesByItineraryID(itineraryID);

        if (citiesExist(cities)) {
            storeCitiesIntoSession(request, cities);
        } else {
            removeCitiesFromSession(session);
        }
    }

    private boolean citiesExist(List<City> cities) {
        return cities != null && cities.size() > 0;
    }

    private void storeCitiesIntoSession(HttpServletRequest request, List<City> cities) {
        final HttpSession session = request.getSession();
        session.setAttribute("cities", cities);
        session.setAttribute("activeCity", cities.get(0));
        final String firstCityName = cities.get(0).getName().trim();
        session.setAttribute("changeCityName", firstCityName);
    }

    private void removeCitiesFromSession(HttpSession session) {
        session.removeAttribute("cities");
        session.removeAttribute("activeCity");
    }

    private void loadActivePreferences(Itinerary activeItinerary,
                                       HttpServletRequest request)
            throws SQLException {
        HttpSession session = request.getSession();
        final int preferenceID = activeItinerary.getPreferenceID();
        SQLPreferenceQuery query = new SQLPreferenceQuery();
        Preference activePreferences = query.getPreferencesByID(preferenceID);
        session.setAttribute("activePreferences", activePreferences);
    }
}
