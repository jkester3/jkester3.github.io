package database.DAL;
import database.DTO.*;
/**
 * Created by Joseph on 6/16/2014.
 */
public class BusinessService {
    public BusinessService(){}

    //Read
    public static Address getAddressByID(Integer addressID)throws SQLException{
        Address address = new Address();
        try{
            address = DataManager.getAddressByID(addressID).get(0);
        } finally {
            DataManager.closeConnection();
        }
        return address;
    }

    public static List<AttractionPreference>
    getAttractionPreferencesByPreferenceID(Integer preferenceID)
            throws SQLException{
        List<AttractionPreference> attractionPreferences
                = new ArrayList<AttractionPreference>();
        try{
            attractionPreferences
                    = DataManager.getAttractionPreferenceByPreferenceID(preferenceID);
        } finally {
            DataManager.closeConnection();
        }
        return attractionPreferences;
    }

    public static List<AvailableTime> getAvailableTimesByTripID(Integer tripID)
            throws SQLException{
        List<AvailableTime> availableTimes
                = new ArrayList<AvailableTime>();
        try{
            availableTimes = DataManager.getAvailableTimeByTripID(tripID);
        } finally {
            DataManager.closeConnection();
        }
        return availableTimes;
    }

    public static Itinerary
    getItineraryByTripID(Integer tripID, DataScope.Itinerary_DataScope dataScope)
            throws SQLException{
        Itinerary itinerary = new Itinerary();
        try{
            itinerary = DataManager.getItineraryByTripID(tripID, dataScope).get(0);
        } finally {
            DataManager.closeConnection();
        }
        return itinerary;
    }

    public static Lodging getLodgingByID(Integer lodgingID)
            throws SQLException{
        Lodging lodging = new Lodging();
        try{
            lodging = DataManager.getLodgingByID(lodgingID).get(0);
        } finally {
            DataManager.closeConnection();
        }
        return lodging;
    }

    public static Place getPlaceByID(Integer placeID){
        Place place = new Place();
        return place;
    }

    public static Preference
    getPreferenceByID(Integer preferenceID,
                      DataScope.Preference_DataScope dataScope)
            throws SQLException{
        Preference preference = new Preference();
        try {
            preference = DataManager.getPreferenceByID(preferenceID, dataScope).get(0);
        } finally {
            DataManager.closeConnection();
        }
        return preference;
    }

    public static List<RestaurantPreference>
    getRestaurantPreferenceByPreferenceID(Integer preferenceID)
            throws SQLException{
        List<RestaurantPreference> restaurantPreferences
                = new ArrayList<RestaurantPreference>();
        try{
            restaurantPreferences
                    = DataManager.getRestaurantPreferenceByPreferenceID(preferenceID);
        } finally {
            DataManager.closeConnection();
        }
        return restaurantPreferences;
    }

    public static List<TimeSlot>
    getTimeSlotByItineraryID(Integer itineraryID,
                             DataScope.TimeSlot_DataScope dataScope)
            throws SQLException{
        String query = Sproc.Time_Slot_ReadByItineraryID(itineraryID);
        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        try{
            timeSlots = DataManager.getTimeSlotByItineraryID(itineraryID, dataScope);
        } finally {
            DataManager.closeConnection();
        }
        return timeSlots;
    }

    public static List<Trip>
    getTripsByUserID(int userID, DataScope.Trip_DataScope dataScope)
            throws SQLException{
        List<Trip> trips = new ArrayList<Trip>();
        try {
            trips = DataManager.getTripsByUserID(userID);
        } finally {
            DataManager.closeConnection();
        }
        return trips;
    }

    public static User
    getUserByUsername(final String username, DataScope.User_DataScope dataScope)
            throws SQLException {
        User user = new User();
        try {
            user = DataManager.getUserByUsername(username, dataScope).get(0);
        } finally {
            DataManager.closeConnection();
        }
        return user;
    }

    public static User getUserByID(int userID, DataScope.User_DataScope dataScope)
            throws SQLException{
        User user = new User();
        try {
            user = DataManager.getUserByID(userID, dataScope).get(0);
        } finally {
            DataManager.closeConnection();
        }
        return user;
    }

    //Save

    //Delete
}
