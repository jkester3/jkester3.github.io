package model;

import database.City;
import database.Place;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class EventSorter {
    private static int nameSortCount = 0;
    private static int creationDateSortCount = 0;
    private static int checkInSortCount = 0;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    public EventSorter(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public void sort() throws IOException {
        final String sortType = determineSortType();
        final City activeCity = (City) session.getAttribute("activeCity");
        List<Place> events = activeCity.getEvents();
        if (nameSortRequested(sortType)) {
            sortByName(events);
        } else if (creationDateSortRequested(sortType)) {
            sortByCreationDate(events);
        } else if (checkInSortRequested(sortType)) {
            sortByCheckIn(events);
        }
        session.setAttribute("events", events);
        request.setAttribute("currentSection", "event-places-page");
        final String returnQueryString = "jsp/index.jsp?sort=" + sortType;
        request.setAttribute("currentSection", "event-places-page");
        ServletUtilities.forwardRequest(request, response, returnQueryString);
    }

    private String determineSortType() {
        final String queryString = request.getQueryString();
        final int beginIndex = queryString.indexOf("=") + 1;
        final String sortType = queryString.substring(beginIndex);
        return sortType;
    }

    private boolean nameSortRequested(final String type) {
        return type.equalsIgnoreCase("name");
    }

    private void sortByName(List<Place> events) {
        if (nameSortRequestedOnce()) {
            Collections.sort(events, new EventNameComparator());
        } else {
            Collections.sort(events, Collections.reverseOrder(
                    new EventNameComparator()));
        }
        nameSortCount++;
        creationDateSortCount = 0;
        checkInSortCount = 0;
    }

    private boolean nameSortRequestedOnce() {
        return nameSortCount % 2 == 0;
    }

    private boolean creationDateSortRequested(final String type) {
        return type.equalsIgnoreCase("creation_date");
    }

    private void sortByCreationDate(List<Place> events) {
        if (creationDateSortRequestedOnce()) {
            Collections.sort(events, new EventCreationDateComparator());
        } else  {
            Collections.sort(events, Collections.reverseOrder(
                    new EventCreationDateComparator()));
        }
        creationDateSortCount++;
        nameSortCount = 0;
        checkInSortCount = 0;
    }

    private boolean creationDateSortRequestedOnce() {
        return creationDateSortCount % 2 == 0;
    }

    private boolean checkInSortRequested(final String type) { return type.equalsIgnoreCase("start_time"); }

    private void sortByCheckIn(List<Place> events) {
        if (checkInSortRequestedOnce()) {
            Collections.sort(events, new EventCheckInComparator());
        } else {
            Collections.sort(events, Collections.reverseOrder(
                    new EventCheckInComparator()));
        }
        checkInSortCount++;
        nameSortCount = 0;
        creationDateSortCount = 0;
    }

    private boolean checkInSortRequestedOnce() { return checkInSortCount % 2 == 0; }
}
