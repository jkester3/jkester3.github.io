package model;

import database.Place;

import java.util.Comparator;

/**
 * Created by Jonathan on 7/22/2014.
 */
public class EventCheckInComparator implements Comparator<Place> {
    @Override
    public int compare(Place firstPlace, Place secondPlace) {
        final String firstCheckIn = formatDate(firstPlace.getCheckIn());
        final String secondCheckIn = formatDate(secondPlace.getCheckIn());
        return firstCheckIn.compareToIgnoreCase(secondCheckIn);
    }

    private String formatDate(String checkIn) {
        String year = checkIn.substring(6, 10);
        checkIn = year + "/" + checkIn.substring(0, 5) + checkIn.substring(10);
        if (checkIn.contains("PM")) {
            int newHour = Integer.parseInt(checkIn.substring(13, 15) + 12);
            checkIn = checkIn.substring(0, 13) + newHour + checkIn.substring((16));
        }
        return checkIn;
    }
}
