package database.DAL;
import java.sql.Date;

import database.DTO.*;

public class DataManager {

    private static ResultSet results;
    private static Connection dbConnection;
    private static Statement statement;

    public DataManager() {}

    //Read
    public static List<Address> getAddressByID(Integer addressID)
            throws SQLException{
        String query = Sproc.Address_ReadByID(addressID);
        List<Address> addresses = new ArrayList<Address>();
        try{
            executeRead(query);
            while(results.next()){
                Integer ID = results.getInt("ID");
                String streetAddress = results.getString("streetAddress");
                String city = results.getString("city");
                String state = results.getString("state");
                String zipCode = results.getString("zipCode");

                Address address = new Address(ID, streetAddress, city,
                        state, zipCode);
                addresses.add(address);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return addresses;
    }

    public static List<AttractionPreference>
    getAttractionPreferenceByPreferenceID(Integer preferenceID)
            throws SQLException{
        String query
                = Sproc.Attraction_Preference_ReadByPreferenceID(preferenceID);
        List<AttractionPreference> attractionPreferences
                = new ArrayList<AttractionPreference>();
        try{
            executeRead(query);
            while(results.next()){
                Integer ID = results.getInt("ID");
                Integer attractionType
                        = results.getInt("preferredAttractionType");

                AttractionPreference attractionPreference
                        = new AttractionPreference(ID, attractionType);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return attractionPreferences;
    }

    public static List<AvailableTime> getAvailableTimeByTripID(Integer tripID)
            throws SQLException{
        String query = Sproc.Available_Time_ReadByTripID(tripID);
        List<AvailableTime> availableTimes
                = new ArrayList<AvailableTime>();
        try{
            executeRead(query);
            while(results.next()) {
                Integer ID = results.getInt("ID");
                Date startDateTime = results.getDate("startDateTime");
                Date endDateTime = results.getDate("endDateTime");

                AvailableTime availableTime = new AvailableTime(ID, startDateTime,
                        endDateTime);
                availableTimes.add(availableTime);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return availableTimes;
    }

    public static List<Itinerary>
    getItineraryByTripID(Integer tripID, DataScope.Itinerary_DataScope dataScope)
            throws SQLException{
        String query = Sproc.Itinerary_ReadByTripID(tripID);
        List<Itinerary> itineraries = new ArrayList<Itinerary>();
        try{
            executeRead(query);
            while(results.next()) {
                Integer ID = results.getInt("ID");
                String name = results.getString("name");
                List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
                if (dataScope.loadTimeSlots) {
                    timeSlots = getTimeSlotByItineraryID(ID,
                            dataScope.timeSlot_dataScope);
                }

                Itinerary itinerary = new Itinerary(ID, name, timeSlots);
                itineraries.add(itinerary);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return itineraries;
    }

    public static List<Lodging> getLodgingByID(Integer lodgingID)
            throws SQLException{
        String query = Sproc.Lodging_ReadByID(lodgingID);
        List<Lodging> lodgings = new ArrayList<Lodging>();
        try{
            executeRead(query);
            while(results.next()) {
                Integer ID = results.getInt("ID");
                String name = results.getString("name");
                Integer addressID = results.getInt("addressID");
                Address address = getAddressByID(addressID).get(0);

                Lodging lodging = new Lodging(ID, name, address);
                lodgings.add(lodging);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lodgings;
    }


    public static List<Place> getPlaceByID(Integer placeID){
        List<Place> places = new ArrayList<Place>();
        return places;
    }

    public static List<Preference>
    getPreferenceByID(Integer preferenceID,
                      DataScope.Preference_DataScope dataScope)
            throws SQLException{
        String query = Sproc.Preference_ReadByID(preferenceID);
        List<Preference> preferences = new ArrayList<Preference>();
        try {
            executeRead(query);
            while (results.next()) {
                Integer ID = results.getInt("ID");
                Double minimumRating = results.getDouble("minimumRating");
                Integer priceCategory = results.getInt("priceCategory");
                Integer maxDistance = results.getInt("maxDistance");
                List<RestaurantPreference> restaurantPreferences
                        = new ArrayList<RestaurantPreference>();
                List<AttractionPreference> attractionPreferences
                        = new ArrayList<AttractionPreference>();
                if(dataScope.loadRestaurantPreferences) {
                    restaurantPreferences
                            = getRestaurantPreferenceByPreferenceID(ID);
                }
                if(dataScope.loadAttractionPreferences) {
                    attractionPreferences
                            = getAttractionPreferenceByPreferenceID(ID);
                }

                Preference preference = new Preference(ID, minimumRating,
                        priceCategory, maxDistance, restaurantPreferences,
                        attractionPreferences);
                preferences.add(preference);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return preferences;
    }

    public static List<RestaurantPreference>
    getRestaurantPreferenceByPreferenceID(Integer preferenceID)
            throws SQLException{
        String query
                = Sproc.Restaurant_Preference_ReadByPreferenceID(preferenceID);
        List<RestaurantPreference> restaurantPreferences
                = new ArrayList<RestaurantPreference>();
        try{
            executeRead(query);
            while(results.next()) {
                Integer ID = results.getInt("ID");
                Integer preferenceType = results.getInt("preferredRestaurantType");

                RestaurantPreference restaurantPreference
                        = new RestaurantPreference(ID, preferenceType);
                restaurantPreferences.add(restaurantPreference);
            }
        }catch(SQLException e){
            e.printStackTrace();
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
            executeRead(query);
            while(results.next()) {
                Integer ID = results.getInt("ID");
                Date startTime = results.getDate("startTime");
                Date endTime = results.getDate("endTime");
                Integer placeID = results.getInt("placeID");
                Place place = new Place();
                if (dataScope.loadPlace && placeID > 0) {
                    place = getPlaceByID(placeID).get(0);
                }

                TimeSlot timeSlot = new TimeSlot(ID, startTime, endTime, place);
                timeSlots.add(timeSlot);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return timeSlots;
    }

    public static List<Trip>
    getTripsByUserID(int userID)
            throws SQLException{
        String query = Sproc.Trip_ReadByUserID(userID);
        List<Trip> trips = new ArrayList<Trip>();
        try {
            executeRead(query);
            while (results.next()) {
                Integer ID = results.getInt("ID");
                Integer lodgingID = results.getInt("lodgingID");
                Integer transportation = results.getInt("transportationMode");
                Date startDateTime = results.getDate("startDateTime");
                Date endDateTime = results.getDate("endDateTime");
                Integer preferenceID = results.getInt("preferenceID");
                Preference preference = new Preference();
                Itinerary itinerary = new Itinerary();
                Lodging lodging = new Lodging();
                List<AvailableTime> availableTimes
                        = new ArrayList<AvailableTime>();

                Trip trip = new Trip(ID, lodging, transportation, itinerary,
                        preference, startDateTime, endDateTime, availableTimes);
                trips.add(trip);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }

    public static List<User>
    getUserByUsername(final String username, DataScope.User_DataScope dataScope)
            throws SQLException {
        String query = Sproc.User_ReadByUsername(username);
        List<User> users = new ArrayList<User>();
        try {
            executeRead(query);
            while (results.next()) {
                Integer ID = results.getInt("ID");
                String firstName = results.getString("firstName");
                String lastName = results.getString("lastName");
                String userName = results.getString("userName");
                String password = results.getString("password");
                Integer userRole = results.getInt("userRole");
                Integer preferenceID = results.getInt("preferenceID");
                String email = results.getString("email");
                Preference preference = new Preference();
                List<Trip> trips = new ArrayList<Trip>();

                if(dataScope.loadPreference && preferenceID > 0){
                    preference = getPreferenceByID(preferenceID,
                            dataScope.preference_dataScope).get(0);
                }
                if(dataScope.loadTrips){
                    //trips = getTripsByUserID(ID, dataScope.trip_dataScope);
                }

                User user = new User(ID, firstName, lastName, userName,
                        password, email, userRole, preference, trips);
                users.add(user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<User>
    getUserByID(int userID, DataScope.User_DataScope dataScope)
            throws SQLException{
        String query = Sproc.User_ReadByID(userID);
        List<User> users = new ArrayList<User>();
        try {
            executeRead(query);
            while (results.next()) {
                Integer ID = results.getInt("ID");
                String firstName = results.getString("firstName");
                String lastName = results.getString("lastName");
                String userName = results.getString("userName");
                String password = results.getString("password");
                Integer userRole = results.getInt("userRole");
                Integer preferenceID = results.getInt("preferenceID");
                String email = results.getString("email");
                Preference preference = new Preference();
                List<Trip> trips = new ArrayList<Trip>();

                if(dataScope.loadPreference && preferenceID > 0){
                    preference = getPreferenceByID(preferenceID,
                            dataScope.preference_dataScope).get(0);
                }
                if(dataScope.loadTrips){
                    //trips = getTripsByUserID(ID, dataScope.trip_dataScope);
                }

                User user = new User(ID, firstName, lastName, userName,
                        password, email, userRole, preference, trips);
                users.add(user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    //Save
    public static void saveAddress(Address address){
        String query = null;
        if(address.getID() == null){
            query = Sproc.Address_Create(address);
        } else{
            query = Sproc.Address_Update(address);
        }
        try{
            executeSave(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void saveAvailableTime(AvailableTime availableTime, int tripID) throws SQLException {
        String query = null;
        if(availableTime.getID() == null){
            query = Sproc.Available_Time_Create(availableTime, tripID);
        } else{
            query = Sproc.Available_Time_Update(availableTime);
        }
        try{
            executeSave(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void saveItinerary(Itinerary itinerary, int tripID) throws SQLException {
        String query = null;
        if(itinerary.getID() == null){
            query = Sproc.Itinerary_Create(itinerary, tripID);
        } else{
            query = Sproc.Itinerary_Update(itinerary);
        }
        try{
            executeSave(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void saveLodging(Lodging lodging) throws SQLException {
        String query = null;
        if(lodging.getID() == null){
            query = Sproc.Lodging_Create(lodging);
        } else{
            query = Sproc.Lodging_Update(lodging);
        }
        try{
            executeSave(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void savePreference(Preference preference) throws SQLException {
        String query = null;
        if(preference.getID() == null){
            query = Sproc.Preference_Create(preference);
        } else{
            query = Sproc.Preference_Update(preference);
        }
        try{
            executeSave(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void saveTrip(Trip trip, int userID) throws SQLException {
        String query = null;
        if(trip.getID() == null){
            query = Sproc.Trip_Create(trip, userID);
        } else{
            query = Sproc.Trip_Update(trip);
        }
        try{
            executeSave(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void saveUser(User user) throws SQLException {
        String query = null;
        if(user.getID() == null){
            query = Sproc.User_Create(user);
        } else{
            query = Sproc.User_Update(user);
        }
        try {
            executeSave(query);
            savePreference(user.getPreference());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static User fetchUser(String username) {
        final String query = Sproc.User_ReadByUsername(username);
        SQLUserSearch userSearch = new SQLUserSearch(query);
        return userSearch.getFetchedUser();
    }

    public static void createUser(User user) throws SQLException {
        final String query = Sproc.User_Create(user);
        try {
            new SQLQueryUpdate(query);
        } catch (SQLException ex) {
            throw new SQLException("Error: username '" + user.getUsername()
                    + "' already exists. Please try again.");
        }
    }

    public static void updateUser(User user) throws SQLException {
        final String query = Sproc.User_Update(user);
        new SQLQueryUpdate(query);
    }

    //Delete
    public static void deleteUser(Integer ID) throws SQLException {
        String query = Sproc.User_Delete(ID);
        try {
            executeSave(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteUser(String username) throws SQLException {
        final String query = Sproc.User_Delete(username);
        try {
            executeSave(query);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAttractionPreference(int ID) throws SQLException {
        final String query = Sproc.Attraction_Preference_DeleteByPreferenceID(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deleteAvailableTime(int ID) throws SQLException {
        final String query = Sproc.Available_Time_Delete(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deleteItinerary(int ID) throws SQLException {
        final String query = Sproc.Itinerary_Delete(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deleteLodging(int ID) throws SQLException {
        final String query = Sproc.Lodging_Delete(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deletePlace(int ID) throws SQLException {
        final String query = Sproc.Place_Delete(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deletePreference(int ID) throws SQLException {
        final String query = Sproc.Preference_Delete(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deleteRestaurantPreference(int ID) throws SQLException {
        final String query = Sproc.Restaurant_Preference_DeleteByPreferenceID(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deleteTimeSlot(int ID) throws SQLException {
        final String query = Sproc.Time_Slot_Delete(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    public static void deleteTrip(int ID) throws SQLException {
        final String query = Sproc.Trip_Delete(ID);
        System.out.println(query);
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    //Helper methods

    public static void closeConnection(){
        DbUtil.close(results);
        DbUtil.close(statement);
        DbUtil.close(dbConnection);
    }

    private static void executeRead(String query) throws SQLException {
        try{
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            results = statement.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void executeSave(String query) throws SQLException {
        try{
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
