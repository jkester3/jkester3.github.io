import database.Place;
import model.AjaxEventForm;
import model.GooglePlaceAPI;
import model.YelpAPI;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestGoogleSearch {
    public static void main(String... args) {
        TestGoogleSearch test = new TestGoogleSearch();
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void testYelpAPI() throws SQLException {
        List<Place> results = new ArrayList<Place>();
        YelpAPI yelpAPI = new YelpAPI();
        try {
            results = yelpAPI.queryAPI("food", "37.788022,-122.399797", 24, 10, 0);
            System.out.println("NUMBER OF RESULTS: " + results.size());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        for (Place p : results) {
            System.out.println(p.getFormattedAddress());
        }
    }

    private void testGoogleNearbySearch() throws IOException, JSONException {
        GooglePlaceAPI googleSearch = new GooglePlaceAPI();
        List<Place> results = googleSearch.getByNearbyPlaceSearch("33.7550,-84.3900", 15, "lodging", "");
        for (Place p : results) {
            System.out.println(p.getRating() + "\t" + p.getName());
        }
        googleSearch.getByDetailSearch(results.get(0), new Place());
        System.out.println(results.get(0).getRating());
    }
}
