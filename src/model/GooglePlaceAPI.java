package model;

import database.Place;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GooglePlaceAPI {
    protected static final String PLACES_API_BASE =
            "https://maps.googleapis.com/maps/api/place";
    protected static final String TEXT_SEARCH = "/textsearch";
    protected static final String NEARBY_SEARCH = "/nearbysearch";
    protected static final String DETAIL_SEARCH = "/details";
    protected static final String OUT_JSON = "/json";
    protected static final String API_KEY =
            "AIzaSyB5fL-GAeqRAg0eRTf3NGaiQPrKAlR8iGE";

    public List<Place> getByTextSearch(final String query) throws
            IOException, JSONException {
        ArrayList<Place> results;
        GooglePlaceTextSearch placeTextSearch = new GooglePlaceTextSearch(query);
        final String urlQuery = placeTextSearch.getTextSearchURL();
        final StringBuilder jsonResults = queryGoogle(urlQuery);
        results = placeTextSearch.parseJsonResults(jsonResults);
        return results;
    }

    public List<Place> getByNearbyPlaceSearch(final String coords,
                                              final int radius,
                                              final String type,
                                              final String name)
            throws IOException, JSONException {
        String reformattedCoords = reformatCoordinatesForQueryCompliance(coords);
        String reformattedName = reformateNameForQueryCompliance(name);
        GooglePlaceNearbySearch nearbyPlaceSearch = new GooglePlaceNearbySearch
                (reformattedCoords, radius, type, reformattedName);
        final String urlQuery = nearbyPlaceSearch.getSearchURL();
        final StringBuilder jsonResults = queryGoogle(urlQuery);
        List<Place> results = nearbyPlaceSearch.parseJsonResults(jsonResults);
        return results;
    }

    private String reformatCoordinatesForQueryCompliance(final String coords) {
        int begin = coords.indexOf("[") + 1;
        int end = coords.length() - 1;
        String formattedCoords = coords.substring(begin, end);
        formattedCoords = formattedCoords.replaceAll("\\s+", "");
        return formattedCoords;
    }

    private String reformateNameForQueryCompliance(final String name) {
        final String lowerCase = name.toLowerCase();
        final String trimmed = lowerCase.trim();
        final String removedExtraSpaces = trimmed.replaceAll("\\s+", " ");
        final String reformattedName = removedExtraSpaces.replaceAll(" ", "%");
        return reformattedName;
    }

    public void getByDetailSearch(final Place place, final Place event)
            throws IOException, JSONException {
        final GooglePlaceDetailSearch detailSearch =
                new GooglePlaceDetailSearch(place, event);
        final String urlQuery = detailSearch.getSearchURL();
        final StringBuilder jsonResults = queryGoogle(urlQuery);
        detailSearch.parseJsonResults(jsonResults);
    }

    private StringBuilder queryGoogle(final String urlQuery)
            throws IOException {
        InputStreamReader in = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            final URL url = new URL(urlQuery);
            final HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(conn.getInputStream());
            jsonResults = recordResults(in);
        } finally {
            if (in != null) {
                in.close();
            }
            return jsonResults;
        }
    }

    private StringBuilder recordResults(final InputStreamReader in)
            throws IOException {
        int read;
        StringBuilder jsonResults = new StringBuilder();
        char[] buff = new char[1024];
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }
        return jsonResults;
    }

    protected void parsePotentialError(final JSONObject mainJsonObj)
            throws JSONException {
        final String status = mainJsonObj.getString("status");
        if (!status.equals("OK")) {
            throw new JSONException("The search could not be completed. " +
                    "Google returned the following error code: " + status + ".");
        }
    }
}