package database.DAL;
import database.DTO.*;

public class Sproc {
    //Read
    public static String Address_ReadByID(int ID){
        String query = "SELECT * FROM address" +
                        " WHERE address.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Attraction_ReadByID(int ID){
        String query = "SELECT * FROM attraction" +
                        " WHERE attraction.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Attraction_Preference_ReadByPreferenceID(int preferenceID){
        String query = "SELECT * FROM attraction_preference" +
                " WHERE attraction_preference.preferenceID = " +
                Integer.toString(preferenceID);
        return query;
    }
    public static String Available_Time_ReadByTripID(int tripID) {
        String query = "SELECT * FROM available_time" +
                        " WHERE available_time.ID = " +
                        Integer.toString(tripID);
        return query;
    }
    public static String Customer_Feedback_ReadByPlaceID(int placeID){
        String query = "SELECT * FROM customer_feedback" +
                        " WHERE customer_feedback.placeID = " +
                        Integer.toString(placeID);
        return query;
    }
    public static String Itinerary_ReadByID(int ID){
        String query = "SELECT * FROM itinerary" +
                        " WHERE itinerary.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Itinerary_ReadByTripID(int tripID){
        String query = "SELECT * FROM itinerary" +
                        " WHERE itinerary.tripID = " +
                        Integer.toString(tripID);
        return query;
    }
    public static String Lodging_ReadByID(int ID){
        String query = "SELECT * FROM lodging" +
                        " WHERE lodging.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Place_ReadByID(int ID){
        String query = "SELECT * FROM place" +
                        " WHERE place.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Preference_ReadByID(int ID){
        String query = "SELECT * FROM preference" +
                        " WHERE preference.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Restaurant_ReadByPlaceID(int placeID){
        String query = "SELECT * FROM restaurant" +
                        " WHERE restaurant.placeID = " +
                        Integer.toString(placeID);
        return query;
    }
    public static String Restaurant_Preference_ReadByPreferenceID(int preferenceID){
        String query = "SELECT * FROM restaurant_preference" +
                        " WHERE restaurant_preference.preferenceID = " +
                        Integer.toString(preferenceID);
        return query;
    }
    public static String Time_Slot_ReadByItineraryID(int itineraryID){
        String query = "SELECT * FROM time_slot" +
                        " WHERE time_slot.itineraryID = " +
                        Integer.toString(itineraryID);
        return query;
    }
    public static String Trip_ReadByID(int ID){
        String query = "SELECT * FROM trip" +
                        " WHERE trip.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Trip_ReadByUserID(int userID){
        String query = "SELECT * FROM trip" +
                        " WHERE trip.userID = " +
                        Integer.toString(userID);
        return query;
    }
    public static String User_ReadAll(){
        String query = "SELECT * FROM user";
        return query;
    }
    public static String User_ReadByID(int ID){
        String query = "SELECT * FROM user" +
                       " WHERE user.ID = " + ID;
        return query;
    }
    public static String User_ReadByUsername(String userName){
        String query = "SELECT * FROM user" +
                       " WHERE userName = \"" + userName + "\";";
        return query;
    }

    //Save
    public static String Address_Create(Address address){
        String query = "INSERT INTO available_time (streetAddress, city, " +
                "state, zipCode) VALUES(" + "'" + address.getStreetAddress() + ", " +
                "'" + address.getCity() + "', " +
                "'" + address.getState() + "', " + address.getZipCode() + ")";
        return query;
    }
    public static String Address_Update(Address address){
        String ID = Integer.toString(address.getID());
        String streetAddress, city, state, zipCode;
        if(address.getStreetAddress() == null){
            streetAddress = "address.streetAddress";
        } else{
            streetAddress = "'" + address.getStreetAddress() + "'";
        }
        if(address.getCity() == null){
            city = "address.city";
        } else{
            city = "'" + address.getCity() + "'";
        }
        if(address.getState() == null){
            state = "address.state";
        } else{
            state = "'" + address.getState() + "'";
        }
        if(address.getZipCode() == null){
            zipCode = "address.zipCode";
        } else{
            zipCode = "'" + address.getZipCode() + "'";
        }
        String query = "UPDATE address SET address.streetAddress = " +
                streetAddress + ", city = " + city + ", state = " +
                ", zipCode = " + zipCode +
                " WHERE address.ID = " + ID;
        return query;
    }
    public static String Available_Time_Create(AvailableTime availableTime, int tripID){
        String query = "INSERT INTO available_time (tripID, startDateTime, " +
                "endDateTime) VALUES(" + tripID + ", " +
                availableTime.getStartDateTime().toString() + ", " +
                availableTime.getEndDateTime().toString() + ")";
        return query;
    }
    public static String Available_Time_Update(AvailableTime availableTime){
        String ID = Integer.toString(availableTime.getID());
        String startDateTime, endDateTime;
        if(availableTime.getStartDateTime() == null){
            startDateTime = "availableTime.startDateTime";
        } else{
            startDateTime = "'" + availableTime.getStartDateTime().toString() + "'";
        }
        if(availableTime.getEndDateTime() == null){
            endDateTime = "availableTime.endDateTime";
        } else{
            endDateTime = "'" + availableTime.getEndDateTime().toString() + "'";
        }
        String query = "UPDATE available_time SET available_time.startDateTime = " +
                startDateTime + ", available_time.endDateTime = " + endDateTime +
                " WHERE available_time.ID = " + ID;
        return query;
    }
    public static String Itinerary_Create(Itinerary itinerary, int tripID){
        String query = "INSERT INTO itinerary (name, tripID) VALUES(" +
                "'" + itinerary.getName() + "', " +
                Integer.toString(tripID) + ")";
        return query;
    }
    public static String Itinerary_Update(Itinerary itinerary){
        String ID = Integer.toString(itinerary.getID());
        String name, addressID;
        if(itinerary.getName() == null){
            name = "itinerary.name";
        } else{
            name = "'" + itinerary.getName() + "'";
        }
        String query = "UPDATE itinerary SET name = " + name +
                " WHERE itinerary.ID = " + ID;
        return query;
    }
    public static String Lodging_Create(Lodging lodging){
        String query = "INSERT INTO lodging (name, addressID) VALUES(" +
                "'" + lodging.getName() + "', " +
                Integer.toString(lodging.getAddress().getID()) + ")";
        return query;
    }
    public static String Lodging_Update(Lodging lodging){
        String ID = Integer.toString(lodging.getID());
        String name, addressID;
        if(lodging.getName() == null){
            name = "lodging.name";
        } else{
            name = "'" + lodging.getName() + "'";
        }
        if(lodging.getAddress().getID() == null){
            addressID = "lodging.addressID";
        } else{
            addressID = Integer.toString(lodging.getAddress().getID());
        }
        String query = "UPDATE lodging SET " + "name = " + name + ", " +
                "addressID = " + addressID + " WHERE lodging.ID = " + ID;
        return query;
    }
    public static String Trip_Create(Trip trip, int userID) {
        String query = "INSERT INTO trip (userID, lodgingID, " +
                "transportationMode, startDateTime, endDateTime, " +
                "preferenceID) VALUES(" + Integer.toString(userID) + ", " +
                null + ", " +
                Integer.toString(trip.getTransportationMode().ordinal()) + ", " +
                "'2014/08/09', "  +
                "'2014/09/09', " +
                null + ")";
        System.out.println(query);
        return query;
    }
    public static String Trip_Update(Trip trip){
        String ID = Integer.toString(trip.getID());
        String lodgingID, transportationMode, startDateTime, endDateTime,
                preferenceID;
        if(trip.getLodging().getID() == null){
            lodgingID = "trip.lodgingID";
        } else{
            lodgingID = Integer.toString(trip.getLodging().getID());
        }
        if(trip.getTransportationMode().ordinal() < 1){
            transportationMode = "trip.transportationMode";
        } else{
            transportationMode = Integer.toString(trip.getLodging().getID());
        }
        if(trip.getStartDateTime() == null){
            startDateTime = "trip.startDateTime";
        } else{
            startDateTime = trip.getStartDateTime().toString();
        }
        if(trip.getEndDateTime() == null){
            endDateTime = "trip.endDateTime";
        } else{
            endDateTime = trip.getEndDateTime().toString();
        }
        if(trip.getPreference().getID() == null){
            preferenceID = "trip.preferenceID";
        } else{
            preferenceID = Integer.toString(trip.getLodging().getID());
        }
        String query = "UPDATE trip SET lodgingID = " + lodgingID +
                ", transportationMode = " + transportationMode +
                ", startDateTime = " + startDateTime + ", endDateTime = " +
                endDateTime + ", preferenceID = " + preferenceID + " " +
                "WHERE trip.ID = " + ID;
        return query;
    }
    public static String Preference_Create(Preference preference){
        String query = "INSERT INTO preference (" +
                "minimumRating, priceCategory, maxDistance) " +
                "VALUES(" + Double.toString(preference.getMinimumRating()) + " " +
                Integer.toString(preference.getPriceCategoryValue()) + " " +
                Integer.toString(preference.getMaxDistance()) + ")";
        return query;
    }
    public static String Preference_Update(Preference preference){
        String ID = Integer.toString(preference.getID());
        String minimumRating, priceCategory, maxDistance;
        if(preference.getMinimumRating() == null){
            minimumRating = "preference.minimumRating";
        } else{
            minimumRating = Double.toString(preference.getMinimumRating());
        }
        if(preference.getPriceCategoryValue() < 1){
            priceCategory = "preference.priceCategory";
        } else{
            priceCategory = Integer.toString(preference.getPriceCategoryValue());
        }
        if(preference.getMaxDistance() == null){
            maxDistance = "preference.maxDistance";
        } else{
            maxDistance = Integer.toString(preference.getMaxDistance());
        }
        String query = "UPDATE preference SET " +
                "minimumRating = " + maxDistance + ", priceCategory = " +
                priceCategory + ", maxDistance = " + maxDistance + " " +
                "WHERE preference.ID = " + ID;
        return query;
    }
    public static String User_Create(User user) {
        String query = "INSERT INTO user (" +
            "firstName, " +
            "lastName, " +
            "userName, " +
            "password," +
            "email," +
            "preferenceID," +
            "userRole) " +
            " VALUES(" +
            "'" + user.getFirstName() + "', " +
            "'" + user.getLastName() + "', " +
            "'" + user.getUsername() + "', " +
            "'" + user.getPassword() + "', " +
            "'" + user.getEmail() + "', " +
            null + ", " +
            Integer.toString(user.getUserRoleValue()) + ");";
        return  query;
    }
    public static String User_Update(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUsername();
        String password = user.getPassword();
        final String query = "UPDATE user " +
            "SET firstName = '" + firstName + "', " +
            "lastName = '" + lastName + "', " +
            "userName = '" + userName + "', " +
            "password = '" + password + "', " +
            "preferenceID = " + null + ", " +
            "email = " + null + " " +
            "WHERE user.userName = '" + userName + "';";
        return query;
    }

    //Delete
    public static String Attraction_Preference_DeleteByPreferenceID(int preferenceID){
        String query = "DELETE FROM attraction_preference" +
                        " WHERE attraction_preference.preferenceID = " +
                        Integer.toString(preferenceID);
        return query;
    }
    public static String Available_Time_Delete(int ID){
        String query = "DELETE FROM available_time" +
                        " WHERE available_time.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Itinerary_Delete(int ID){
        String query = "DELETE FROM itinerary" +
                        " WHERE itinerary.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Lodging_Delete(int ID){
        String query = "DELETE FROM lodging" +
                        " WHERE lodging.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Place_Delete(int ID){
        String query = "DELETE FROM place" +
                        " WHERE place.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Preference_Delete(int ID){
        String query = "DELETE FROM preference" +
                        " WHERE preference.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Restaurant_Preference_DeleteByPreferenceID(int preferenceID){
        String query = "DELETE FROM restaurant_preference" +
                        " WHERE restaurant_preference.preferenceID = " +
                        Integer.toString(preferenceID);
        return query;
    }
    public static String Time_Slot_Delete(int ID){
        String query = "DELETE FROM time_slot" +
                        " WHERE time_slot.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String Trip_Delete(int ID){
        String query = "DELETE FROM trip" +
                        " WHERE trip.ID = " +
                        Integer.toString(ID);
        return query;
    }
    public static String User_Delete(final String username){
        String query = "DELETE FROM user" +
                        " WHERE user.userName = '" + username + "';";
        return query;
    }

    public static String User_Delete(Integer ID){
        String query = "DELETE FROM user" +
                " WHERE user.ID = '" + ID + "';";
        return query;
    }
}
