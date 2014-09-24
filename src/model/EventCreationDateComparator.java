package model;

import database.Place;

import java.util.Comparator;

public class EventCreationDateComparator implements Comparator<Place> {
    @Override
    public int compare(Place p, Place q) {
        return p.getCreationDate().compareTo(q.getCreationDate());
    }
}
