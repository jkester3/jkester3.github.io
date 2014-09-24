package model;

import controller.BrowserErrorHandling;
import database.City;
import database.DataManager;
import database.Place;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class AjaxEventForm {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private City activeCity;
    private static int formerLength = 0;

    public AjaxEventForm(HttpServletRequest request,
                         HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.activeCity = (City) session.getAttribute("activeCity");
    }

    public void getEventsAndRedirectToAjax() throws IOException {
        List<Place> ajaxEvents;
        final String eventName = request.getParameter("eventName");
        final String eventType = request.getParameter("eventType");
        final String radiusAsString = request.getParameter("eventRadius");
        final int radiusInMiles = Integer.parseInt(radiusAsString);
        try {
            if (request.getParameter("ajaxGoogleButton") != null) {
                ajaxEvents = queryGoogleForEvents(eventName, eventType, radiusInMiles);
                updateResultsInSession(ajaxEvents);
                returnResultsToAjax(ajaxEvents);
            } else if (request.getParameter("ajaxYelpButton") != null) {
                ajaxEvents = queryYelpForEvents(eventType, radiusInMiles);
                updateResultsInSession(ajaxEvents);
                returnResultsToAjax(ajaxEvents);
            }
        } catch (JSONException ex) {
        } catch (IOException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
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

    private void returnResultsToAjax(final List<Place> results)
            throws IOException, JSONException {
        StringBuilder tableRows = new StringBuilder();
        for (int j = 0, i = formerLength; j < results.size(); j++, i++) {
            final Place result = results.get(j);
            final String url = (result.getURL() != null) ? result.getURL() :
                    "/CS2340Servlet/itinerary?ajax_get_url&place_id=" + i;
            tableRows.append("<tr><td class='table-name'>" + result.getName() +
                    "</td><td class='table-address'>" +
                    result.getFormattedAddress() + "</td><td>" +
                    result.getRating() + "</td>" + "<td>" +
                    "<a href='" + url + "' target='_blank'>" +
                    "See More</a></td>" +
                    "<td><a href='/CS2340Servlet/itinerary?" +
                    "ajax_event_selected&place_id=" + i +
                    "'>Select</a></td></tr>");
        }
        memorizeAjaxEvents(tableRows.toString());
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println(tableRows.toString());
    }

    private void memorizeAjaxEvents(final String currentTableRows) {
        if (eventsAlreadyMemorized()) {
            String formerTableRows = "";
            formerTableRows = (String) session.getAttribute("ajaxEventMemory");
            formerTableRows += currentTableRows;
            session.setAttribute("ajaxEventMemory", formerTableRows);
        } else {
            session.setAttribute("ajaxEventMemory", currentTableRows);
        }
    }

    private boolean eventsAlreadyMemorized() {
        return session.getAttribute("ajaxEventMemory") != null;
    }

    private void updateResultsInSession(List<Place> ajaxEvents) {
        if (ajaxEventResultsNotExist()) {
            createAjaxEventsInSession(ajaxEvents);
        } else {
            updateAjaxEventsInSession(ajaxEvents);
        }
    }

    private boolean ajaxEventResultsNotExist() {
        return session.getAttribute("ajaxEvents") == null;
    }

    private void createAjaxEventsInSession(List<Place> ajaxEvents) {
        session.setAttribute("ajaxEvents", ajaxEvents);
    }

    private void updateAjaxEventsInSession(List<Place> ajaxEvents) {
        List<Place> formerResults;
        formerResults = (List<Place>) session.getAttribute("ajaxEvents");
        formerLength = formerResults.size();
        formerResults.addAll(ajaxEvents);
        session.setAttribute("ajaxEvents", formerResults);
    }

    public void getGoogleURLThenRedirectToURL() throws IOException {
        Place ajaxResult = getEventFromSessionByPlaceID(), dummy = new Place();
        final GooglePlaceAPI googlePlaceAPI = new GooglePlaceAPI();
        try {
            googlePlaceAPI.getByDetailSearch(ajaxResult, dummy);
        } catch (JSONException ignore) {}
        redirectUserToRequestedURL(ajaxResult.getURL());
    }

    private void redirectUserToRequestedURL(String url)
            throws IOException {
        response.sendRedirect(url);
    }

    public void makeSelection() throws IOException {
        City activeCity = (City) session.getAttribute("activeCity");
        final Place chosenEvent = getEventFromSessionByPlaceID();
        try {
            DataManager.createEvent(chosenEvent, activeCity.getID());
            List<Place> chosenEvents = activeCity.getEvents();
            chosenEvents.add(chosenEvent);
            session.setAttribute("activeCity", activeCity);
            clearEventsFromSession();
            response.sendRedirect("jsp/index.jsp");
        } catch (SQLException ex) {
            BrowserErrorHandling.printErrorToBrowser(request, response, ex);
        }
    }

    private void clearEventsFromSession() {
        session.removeAttribute("ajaxEvents");
        session.removeAttribute("ajaxEventMemory");
        formerLength = 0;
    }

    private Place getEventFromSessionByPlaceID() {
        List<Place> ajaxEvents;
        Place chosenEvent = new Place();
        final String queryString = request.getQueryString();
        final int beginIndex = queryString.indexOf("=") + 1;
        final int placeID = Integer.parseInt(queryString.substring(beginIndex));
        ajaxEvents = (List<Place>) session.getAttribute("ajaxEvents");
        if (ajaxEvents != null) {
            chosenEvent = ajaxEvents.get(placeID);
        }
        return chosenEvent;
    }

    public void changeEventPanelView() throws IOException, ServletException {
        final String queryString = request.getQueryString();
        if (queryString.contains("grid"))
            request.getSession().setAttribute("eventPanelView", "Grid");
        else if (queryString.contains("summary"))
            request.getSession().setAttribute("eventPanelView", "Summary");
        request.setAttribute("defaultSection", "event-places-page");
        request.setAttribute("currentSection", "event-places-page");
        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
    }
}
