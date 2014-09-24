package model;

import controller.BrowserErrorHandling;
import database.*;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventForm {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private City activeCity;

    public EventForm(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.activeCity = (City) session.getAttribute("activeCity");
    }

    public void createNewEvents() throws IOException {
        final String queryString = request.getQueryString();
        final int startIndex = queryString.indexOf("=") + 1;
        final String numberOfEvents = queryString.substring(startIndex);
        List<Place> events = new ArrayList<Place>();
        if (activeCity.getEvents() != null) {
            events = activeCity.getEvents();
        }
        for (int i = 0; i < Integer.parseInt(numberOfEvents); i++) {
            events.add(new Place());
        }
        activeCity.setEvents(events);
        session.setAttribute("activeCity", activeCity);
        request.setAttribute("currentSection", "event-places-page");
        ServletUtilities.forwardRequest(request, response, "/jsp/index.jsp");
    }

    public void saveSelection() throws IOException {
        int placeNum, eventNum;
        final String eventID = parseEventIDFromQueryString();
        final String placeID = parsePlaceIDFromQueryString();
        eventNum = Integer.parseInt(eventID);
        placeNum = Integer.parseInt(placeID);
        List<Place> events = activeCity.getEvents();
        final String businessID = "businesses" + eventNum;
        List<Place> businesses = (List) session.getAttribute(businessID);
        Place placeToBeSaved = businesses.get(placeNum);
        events.set(eventNum, placeToBeSaved);
        try {
            if (placeToBeSaved.getAPI().equals("google"))
                getDetailedInformationForPlace(false);
            DataManager.createEvent(placeToBeSaved, activeCity.getID());
            activeCity.setEvents(events);
            session.setAttribute("activeCity", activeCity);
            reloadPage();
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    public String parseEventIDFromQueryString() {
        final String queryString = request.getQueryString();
        final int startIndex = queryString.lastIndexOf("=") + 1;
        final String eventID = queryString.substring(startIndex);
        return eventID;
    }

    private String parsePlaceIDFromQueryString() {
        final String queryString = request.getQueryString();
        final int startIndex = queryString.indexOf("=") + 1;
        final int endIndex = queryString.lastIndexOf("&");
        final String placeID = queryString.substring(startIndex, endIndex);
        return placeID;
    }

    public void getDetailedInformationForPlace(boolean foreignPageRequested)
            throws IOException {
        final String eventID = parseEventIDFromQueryString();
        final String placeID = parsePlaceIDFromQueryString();
        final int placeNum = Integer.parseInt(placeID);
        final int eventNum = Integer.parseInt(eventID);
        final String businessID = "businesses" + eventNum;
        List<Place> places = (List) session.getAttribute(businessID);
        final List<Place> events = activeCity.getEvents();
        final Place placeToBeUpdated = places.get(placeNum);
        final Place eventToBeUpdated = events.get(eventNum);
        final GooglePlaceAPI googlePlaceAPI = new GooglePlaceAPI();
        try {
            googlePlaceAPI.getByDetailSearch(placeToBeUpdated, eventToBeUpdated);
            if (foreignPageRequested)
                redirectUserToRequestedURL(eventToBeUpdated.getURL());
        } catch (JSONException ex) {
            returnSearchError(eventID, ex);
        } catch (IOException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private void redirectUserToRequestedURL(String url)
            throws IOException {
        response.sendRedirect(url);
    }

    private void reloadPage() throws  IOException {
        request.setAttribute("currentSection", "event-places-page");
        ServletUtilities.forwardRequest(request, response, "/jsp/index.jsp");
    }

    public void getEventsAroundCentralLocation() throws IOException {
        List<Place> eventResults;
        final String eventID = parseEventIDFromQueryString();
        final String API = getSearchAPIFromRequest();
        final String eventName = request.getParameter("eventName" + eventID);
        final String eventType = request.getParameter("eventType" + eventID);
        final String radiusAsString = request.getParameter("eventRadius" + eventID);
        final int radiusInMiles = Integer.parseInt(radiusAsString);
        putQueryStringForReviewInSession(eventID, eventName, eventType,
                radiusAsString);
        try {
            if (API.equals("google")) {
                eventResults = queryGoogleForEvents(eventName, eventType,
                        radiusInMiles);
            } else {
                eventResults = queryYelpForEvents(eventType, radiusInMiles);
            }
            populateSessionWithEventResults(eventID, eventResults);
            returnQueryResults(eventID, API);
            session.removeAttribute("apiSearchError" + eventID);
        } catch (JSONException ex) {
            returnSearchError(eventID, ex);
        } catch (IOException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private String getSearchAPIFromRequest() {
        String API;
        if (request.getParameter("getEventsWithGoogleButton") != null ) {
            API = "google";
        } else {
            API = "yelp";
        }
        return API;
    }

    private void putQueryStringForReviewInSession(String eventID,
                                                  String eventName,
                                                  String eventType,
                                                  String radius) {
        session.setAttribute("eventQueryString" + eventID, "Place Name = '" +
                eventName + "' | Place Type = '" + eventType + "' | Radius = '" +
                radius + "'.");
    }

    private List<Place> queryGoogleForEvents(String eventName,
                                                   String eventType,
                                                   int radius)
        throws IOException, JSONException {
        final double latitude = activeCity.getLatitude();
        final double longitude = activeCity.getLongitude();
        final String coordinates = new Coordinates(latitude, longitude).format();
        GooglePlaceAPI googleSearch = new GooglePlaceAPI();
        List<Place> eventResults = googleSearch.getByNearbyPlaceSearch
                (coordinates, radius, eventType, eventName);
        return eventResults;
    }

    private List<Place> queryYelpForEvents(final String term, final int radius)
            throws SQLException, JSONException {
        List<Place> results;
        final double latitude = activeCity.getLatitude();
        final double longitude = activeCity.getLongitude();
        final String coordinates = new Coordinates(latitude, longitude).format();
        YelpAPI yelpAPI = new YelpAPI(request, response);
        results = yelpAPI.queryAPI(term, coordinates, radius, 20, 0);
        return results;
    }

    private void populateSessionWithEventResults(String eventID,
                                                 List<Place> eventResults) {
        session.setAttribute("businesses" + eventID, eventResults);
    }


    private void returnQueryResults(final String eventID, final String API)
            throws IOException {
        updateImageIcon(eventID, API);
        final String returnQueryString = "jsp/index.jsp?search=" + API +
                "&event-no=" + eventID;
        request.setAttribute("currentSection", "event-places-page");
        ServletUtilities.forwardRequest(request, response, returnQueryString);
    }

    private void updateImageIcon(final String eventID, final String API) {
        final int eventNum = Integer.parseInt(eventID);
        List<Place> events = activeCity.getEvents();
        events.get(eventNum).setAPI(API);
    }

    private void returnSearchError(String eventID, JSONException ex)
        throws IOException {
        session.removeAttribute("businesses" + eventID);
        session.setAttribute("apiSearchError" + eventID, ex.getMessage());
        response.sendRedirect("jsp/index.jsp?search=google&event-no=" + eventID);
    }

    public void deleteRequestedEvent() throws IOException {
        try {
            List<Place> events = activeCity.getEvents();
            final String eventID = parseEventIDFromQueryString();
            final int eventNum = Integer.parseInt(eventID);
            DataManager.deleteEventByEventAttributes(events.get(eventNum));
            events.remove(eventNum);
            activeCity.setEvents(events);
            session.setAttribute("activeCity", activeCity);
            session.removeAttribute("businesses" + eventID);
            session.removeAttribute("eventQueryString" + eventID);
            reloadPage();
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    public void setEventTime() throws IOException {
        final String startTime = request.getParameter("eventStartTime");
        final String endTime = request.getParameter("eventEndTime");
        final String reformattedStart = reformatHTMLDateTime(startTime);
        final String reformattedEnd = reformatHTMLDateTime(endTime);
        final String eventID = parseEventIDFromQueryString();
        final int eventNum = Integer.parseInt(eventID);
        try {
            List<Place> events = activeCity.getEvents();
            Place event = events.get(eventNum);
            event.setCheckIn(reformattedStart);
            event.setCheckOut(reformattedEnd);
            DataManager.updatePlaceTimeByID(event, "event");
            updateCurrentSession(events, event, eventNum);
            request.setAttribute("currentSection", "event-places-page");
            ServletUtilities.forwardRequest(request, response, "/jsp/index.jsp");
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private String reformatHTMLDateTime(final String htmlFormat) {
        final HtmlDateConverter dateConverter = new HtmlDateConverter();
        return dateConverter.convert(htmlFormat);
    }

    private void updateCurrentSession(final List<Place> events,
                                      final Place event,
                                      final int eventNum) {
        events.set(eventNum, event);
        activeCity.setEvents(events);
        session.setAttribute("activeCity", activeCity);
    }
}
