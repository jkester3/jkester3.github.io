package model;

import controller.BrowserErrorHandling;
import database.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class LodgingForm {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private City activeCity;

    public LodgingForm(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.activeCity = (City) session.getAttribute("activeCity");
    }

    public void getLodgingsAroundLocation() throws IOException{
        session = request.getSession();
        final String name = parseName();
        final double latitude = activeCity.getLatitude();
        final double longitude = activeCity.getLongitude();
        final String coordinates = new Coordinates(latitude, longitude).format();
        final int radius = parseRadius();
        final int limit = parseLimit();
        List<Place> lodgings;
        try {
            YelpAPI yelpAPI = new YelpAPI();
            lodgings = yelpAPI.queryAPI(name, coordinates, radius, limit, 0);
            session.setAttribute("lodgingResults", lodgings);
            session.setAttribute("lastLodgingName", name);
            session.setAttribute("lastLodgingRadius", radius);
            session.setAttribute("lastLodgingLimit", limit);
            request.setAttribute("currentSection", "lodging-page");
            ServletUtilities.forwardRequest(request, response, "/jsp/index.jsp");
        } catch (Exception ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    public void getMoreLodgingResults() {
        final String name = session.getAttribute("lastLodgingName").toString();
        final String radius = session.getAttribute("lastLodgingRadius").toString();
        final String limit = session.getAttribute("lastLodgingLimit").toString();
        final int radiusInt = Integer.parseInt(radius);
        final int limitInt = Integer.parseInt(limit);
        final double latitude = activeCity.getLatitude();
        final double longitude = activeCity.getLongitude();
        final String coordinates = new Coordinates(latitude, longitude).format();
        List<Place> currentResults;
        List<Place> previousResults = (List) session.getAttribute("lodgingResults");
        try {
            YelpAPI yelpAPI = new YelpAPI();
            currentResults = yelpAPI.queryAPI(name, coordinates, radiusInt,
                    limitInt, limitInt);
            List<Place> mergedResults = mergeResults(previousResults, currentResults);
            session.setAttribute("lodgingResults", mergedResults);
        } catch (Exception ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private List mergeResults(List<Place> previousResults, List<Place>currentResults) {
        currentResults.removeAll(previousResults);
        previousResults.addAll(currentResults);
        return previousResults;
    }

    private String parseName() {
        String lodgingName = request.getParameter("lodgingName");
        if (lodgingName == null || lodgingName.isEmpty()) {
            lodgingName = "lodging";
        }
        return lodgingName;
    }

    private int parseLimit() {
        final String limit = request.getParameter("lodgingFilter");
        final int limitInt = (limit != null && !limit.isEmpty())
                ? Integer.parseInt(limit) : 10;
        return limitInt;
    }

    private int parseRadius() {
        final String radius = request.getParameter("lodgingRadius");
        int radiusInt = (radius != null && !radius.isEmpty())
                ? Integer.parseInt(radius) : 15;
        return radiusInt;
    }

    public void saveLodgingSelection() throws IOException {
        final String lodgingURI = request.getQueryString();
        final int begin = lodgingURI.indexOf("=") + 1;
        final String lodgingIDAsString = lodgingURI.substring(begin);
        final int lodgingID = Integer.parseInt(lodgingIDAsString);
        List<Place> results = (List<Place>) session.getAttribute("lodgingResults");
        final Place selection = results.get(lodgingID);
        try {
            DataManager.createLodging(selection, activeCity.getID());
            activeCity.setLodging(selection);
            session.setAttribute("activeCity", activeCity);
            request.setAttribute("currentSection", "lodging-page");
            ServletUtilities.forwardRequest(request, response, "/jsp/index.jsp");
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    public void setLodgingTime() throws IOException {
        final String checkIn = request.getParameter("lodgingCheckIn");
        final String checkOut = request.getParameter("lodgingCheckOut");
        final String reformattedCheckIn = reformatHTMLDateTime(checkIn);
        final String reformattedCheckOut = reformatHTMLDateTime(checkOut);
        try {
            Place lodging = activeCity.getLodging();
            lodging.setCheckIn(reformattedCheckIn);
            lodging.setCheckOut(reformattedCheckOut);
            DataManager.updatePlaceTimeByID(lodging, "lodging");
            updateCurrentSession(lodging);
            request.setAttribute("currentSection", "lodging-page");
            ServletUtilities.forwardRequest(request, response, "/jsp/index.jsp");
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private String reformatHTMLDateTime(final String htmlFormat) {
        final HtmlDateConverter dateConverter = new HtmlDateConverter();
        return dateConverter.convert(htmlFormat);
    }

    private void updateCurrentSession(final Place lodging) {
        activeCity.setLodging(lodging);
        session.setAttribute("activeCity", activeCity);
    }
}
