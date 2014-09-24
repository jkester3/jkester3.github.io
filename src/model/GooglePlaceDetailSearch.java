package model;

import database.Place;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GooglePlaceDetailSearch extends GooglePlaceAPI {
    private Place place, event;
    private String placeID;

    public GooglePlaceDetailSearch(Place place, Place event) {
        this.place = place;
        this.event = event;
        this.placeID = place.getPlaceID();
    }

    public String getSearchURL() throws IOException, JSONException {
        final String urlQuery = buildTextSearchQuery();
        return urlQuery;
    }

    private String buildTextSearchQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(PLACES_API_BASE);
        queryBuilder.append(DETAIL_SEARCH);
        queryBuilder.append(OUT_JSON);
        queryBuilder.append("?placeid=" + placeID);
        queryBuilder.append("&key=" + API_KEY);
        return queryBuilder.toString();
    }

    public void parseJsonResults(StringBuilder jsonResults)
            throws JSONException {
        JSONObject mainJsonObj = new JSONObject(jsonResults.toString());
        super.parsePotentialError(mainJsonObj);
        JSONObject result = mainJsonObj.getJSONObject("result");
        if (result.has("url"))
            this.setURL(result);
        if (result.has("formatted_phone_number"))
            this.setPhoneNumber(result);
        if (result.has("rating")) {
            setRating(result);
        }
    }

    private void setURL(final JSONObject result) throws JSONException {
        final String url = result.getString("url");
        place.setURL(url);
        event.setURL(url);
    }

    private void setPhoneNumber(final JSONObject result) throws JSONException {
        final String phoneNumber = result.getString("formatted_phone_number");
        place.setPhoneNumber(phoneNumber);
        event.setPhoneNumber(phoneNumber);
    }

    private void setRating(final JSONObject result) throws JSONException {
        final double rating = result.getDouble("rating");
        place.setRating(rating);
        event.setRating(rating);
    }
}
