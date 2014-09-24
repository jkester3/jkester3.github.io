package model;

import database.Place;
import database.Place;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GooglePlaceTextSearch extends GooglePlaceAPI {
    private String query;

    public GooglePlaceTextSearch(final String query) {
        this.query = query;
    }

    public String getTextSearchURL() throws
            IOException, JSONException {
        final String urlQuery = buildTextSearchQuery(query);
        return urlQuery;
    }

    private String buildTextSearchQuery(String query) {
        query = query.replaceAll("\\s+", "+");
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(PLACES_API_BASE);
        queryBuilder.append(TEXT_SEARCH);
        queryBuilder.append(OUT_JSON);
        queryBuilder.append("?query=" + query);
        queryBuilder.append("&key=" + API_KEY);
        return queryBuilder.toString();
    }

    public ArrayList<Place> parseJsonResults(StringBuilder jsonResults)
            throws JSONException {
        ArrayList<Place> placeResults;
        JSONObject mainJsonObj = new JSONObject(jsonResults.toString());
        JSONArray jsonArray = mainJsonObj.getJSONArray("results");
        placeResults = new ArrayList<Place>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = "", placeID = "", formattedAddress = "";
            int priceLevel = 0;
            double rating = 0;
            boolean openNow = true;
            if (jsonObject.has("name"))
                name = jsonObject.getString("name");
            if (jsonObject.has("place_id"))
                placeID = jsonObject.getString("place_id");
            if (jsonObject.has("formatted_address"))
                formattedAddress = jsonObject.getString("formatted_address");
            if (jsonObject.has("price_level"))
                priceLevel = jsonObject.getInt("price_level");
            if (jsonObject.has("rating"))
                rating = jsonObject.getDouble("rating");
            if (jsonObject.has("opening_hours")) {
                JSONObject hours = jsonObject.getJSONObject("opening_hours");
                if (hours.has("open_now")) {
                    openNow = hours.getBoolean("open_now");
                }
            }
            final Place place = new Place(name, placeID, formattedAddress,
                    priceLevel, rating, openNow);
            placeResults.add(place);
        }
        return placeResults;
    }
}
