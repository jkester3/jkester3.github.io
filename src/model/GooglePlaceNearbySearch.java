package model;

import database.Place;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GooglePlaceNearbySearch extends GooglePlaceAPI {
    private static final int MAX_DISTANCE_IN_METERS = 50000;
    private String coordinates, type, name;
    private int radius;

    public GooglePlaceNearbySearch(String coordinates, int radius,
                                   String type, String name) {
        this.coordinates = coordinates;
        this.radius = convertFromMilesToMeters(radius);
        this.type = type;
        this.name = name;
    }

    private int convertFromMilesToMeters(final int radius) {
        int radiusInMeters = (int) (radius * 1609.34);
        if (radiusInMeters > MAX_DISTANCE_IN_METERS) {
            radiusInMeters = MAX_DISTANCE_IN_METERS;
        }
        return radiusInMeters;
    }

    public String getSearchURL() throws IOException, JSONException {
        final String urlQuery = buildTextSearchQuery();
        return urlQuery;
    }

    private String buildTextSearchQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(PLACES_API_BASE);
        queryBuilder.append(NEARBY_SEARCH);
        queryBuilder.append(OUT_JSON);
        queryBuilder.append("?location=" + coordinates);
        queryBuilder.append("&radius=" + radius);
        queryBuilder.append("&types=" + type);
        queryBuilder.append("&name=" + name);
        queryBuilder.append("&key=" + API_KEY);
        return queryBuilder.toString();
    }

    public List<Place> parseJsonResults(StringBuilder jsonResults)
            throws JSONException {
        ArrayList<Place> results;
        JSONObject mainJsonObj = new JSONObject(jsonResults.toString());
        super.parsePotentialError(mainJsonObj);
        JSONArray jsonArray = mainJsonObj.getJSONArray("results");
        results = new ArrayList<Place>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = "", placeID = "", formattedAddress = "";
            int priceLevel = 0;
            double rating = 0, latitude = 0, longitude = 0;
            boolean openNow = false;
            if (jsonObject.has("name"))
                name = jsonObject.getString("name");
            if (jsonObject.has("place_id"))
                placeID = jsonObject.getString("place_id");
            if (jsonObject.has("vicinity"))
                formattedAddress = jsonObject.getString("vicinity");
            if (jsonObject.has("price_level"))
                priceLevel = jsonObject.getInt("price_level");
            if (jsonObject.has("rating")) {
                rating = jsonObject.getDouble("rating");
            }
            if (jsonObject.has("opening_hours")) {
                JSONObject hours = jsonObject.getJSONObject("opening_hours");
                if (hours.has("open_now")) {
                    openNow = hours.getBoolean("open_now");
                }
            }
            if (jsonObject.has("geometry")) {
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                if (geometry.has("location")) {
                    JSONObject location = geometry.getJSONObject("location");
                    latitude = location.getDouble("lat");
                    longitude = location.getDouble("lng");
                }
            }
            final Place parsedObject = new Place();
            parsedObject.setName(name);
            parsedObject.setPlaceID(placeID);
            parsedObject.setFormattedAddress(formattedAddress);
            parsedObject.setPriceLevel(priceLevel);
            parsedObject.setRating(rating);
            parsedObject.setOpenNow(openNow);
            parsedObject.setAPI("google");
            parsedObject.setCoordinates(new Coordinates(latitude, longitude));
            results.add(parsedObject);
        }
        return results;
    }
}


