package database.DTO;

/**
 * Created by Joseph on 6/21/2014.
 */
public class DataScope {
    public User_DataScope user_dataScope;
    public Trip_DataScope trip_dataScope;
    public Preference_DataScope preference_dataScope;
    public Itinerary_DataScope itinerary_dataScope;
    public TimeSlot_DataScope timeSlot_dataScope;

    public DataScope() {
        user_dataScope = new User_DataScope(true);
        trip_dataScope = new Trip_DataScope(true);
        preference_dataScope = new Preference_DataScope();
        itinerary_dataScope = new Itinerary_DataScope();
        timeSlot_dataScope = new TimeSlot_DataScope();
    }

    public class User_DataScope {
        public boolean loadTrips;
        public boolean loadPreference;
        public Trip_DataScope trip_dataScope;
        public Preference_DataScope preference_dataScope;

        public User_DataScope(boolean flag){
            loadTrips = flag;
            loadPreference = flag;
            trip_dataScope = new Trip_DataScope(flag);
            preference_dataScope = new Preference_DataScope();
        }
    }
    public class Trip_DataScope {
        public boolean loadItinerary;
        public boolean loadAvailableTimes;
        public boolean loadPreference;
        public boolean loadLodging;
        public Preference_DataScope preference_dataScope;
        public Itinerary_DataScope itinerary_dataScope;

        public Trip_DataScope(boolean flag) {
            loadItinerary = flag;
            loadAvailableTimes = flag;
            loadPreference = flag;
            loadLodging = flag;
            preference_dataScope = new Preference_DataScope();
            itinerary_dataScope = new Itinerary_DataScope();
        }
    }
    public class Preference_DataScope {
        public boolean loadRestaurantPreferences;
        public boolean loadAttractionPreferences;

        public Preference_DataScope(){
            loadAttractionPreferences = false;
            loadAttractionPreferences = false;
        }
    }
    public class Itinerary_DataScope {
        public boolean loadTimeSlots;
        public TimeSlot_DataScope timeSlot_dataScope;

        public Itinerary_DataScope(){
            loadTimeSlots = false;
            timeSlot_dataScope = new TimeSlot_DataScope();
        }
    }
    public class TimeSlot_DataScope {
        public boolean loadPlace;

        public TimeSlot_DataScope(){
            loadPlace = false;
        }
    }
}
