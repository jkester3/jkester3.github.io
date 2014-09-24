package model;

import controller.BrowserErrorHandling;
import database.City;
import database.DataManager;
import database.Itinerary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityForm {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    public CityForm(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public void createNewCity() throws IOException {
        final City city = gatherFormData();
        try {
            saveCityDatabaseSession(city);
            response.sendRedirect("jsp/index.jsp");
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private City gatherFormData() {
        final String address = request.getParameter("cityAddress");
        final String coordinates = request.getParameter("cityCoordinates");
        final double latitude = parseLatitude(coordinates);
        final double longitude = parseLongitude(coordinates);
        final Itinerary itinerary =
                (Itinerary) session.getAttribute("activeItinerary");
        final int itineraryID = itinerary.getID();
        final City newCity = new City(address, address, latitude, longitude,
                itineraryID);
        return newCity;
    }

    private double parseLatitude(final String coordinates) {
        final int beginIndex = coordinates.indexOf("(") + 1;
        final int endIndex = coordinates.indexOf(",");
        final String latitude =  coordinates.substring(beginIndex, endIndex);
        return Double.parseDouble(latitude);
    }

    private double parseLongitude(final String coordinates) {
        final int beginIndex = coordinates.indexOf(", ") + 1;
        final int endIndex = coordinates.indexOf(")");
        final String longitude = coordinates.substring(beginIndex, endIndex);
        return Double.parseDouble(longitude);
    }

    private void saveCityDatabaseSession(final City city) throws SQLException {
        DataManager.createCity(city);
        int cityID = DataManager.getCityIDByName(city.getName());
        if (cityID == -1) throw new SQLException("Failed to load new city");
        city.setID(cityID);
        List<City> cities = (List<City>) session.getAttribute("cities");
        cities.add(city);
        session.setAttribute("cities", cities);
    }

    public void changeCity() throws IOException {
        HttpSession session = request.getSession();
        List<City> cities = (List<City>) session.getAttribute("cities");
        City changeCity = (City) session.getAttribute("activeCity");
        final int cityNum = parseCityID(request);
        for (City city : cities) {
            if (city.getID() == cityNum) {
                changeCity = city;
                break;
            }
        }
        session.setAttribute("activeCity", changeCity);
        session.setAttribute("changeCityName", changeCity.getName());
        response.sendRedirect("jsp/index.jsp");
    }

    private int parseCityID(final HttpServletRequest request) {
        final String queryString = request.getQueryString();
        final int beginIndex = queryString.indexOf("=") + 1;
        final String cityID = queryString.substring(beginIndex);
        return Integer.parseInt(cityID);
    }
}
