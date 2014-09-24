package model;

import database.Place;

import java.util.Comparator;

public class EventNameComparator implements Comparator<Place> {
    @Override
    public int compare(Place firstPlace, Place secondPlace) {
        final String firstName = firstPlace.getName();
        final String secondName = secondPlace.getName();
        return firstName.compareToIgnoreCase(secondName);
    }
}
