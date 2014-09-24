package database;

import java.sql.Connection;

public class SQLQuery {
    protected Connection dbConnection;

    public SQLQuery() {
        dbConnection = ConnectionManager.getConnection();
    }

    /*public static String updateItineraryQuery(Itinerary itinerary){
        String query = "UPDATE itinerary SET name = " + itinerary.getName() +", " +
                "address = " + itinerary.getAddress() +", " +
                "transportationMode = " + itinerary.getTransportationMode() +", " +
                " WHERE itinerary.ID = " + itinerary.getID() + ";";
        return query;
    }

    public String readItineraryQuery(int ID){
        String query = "SELECT * FROM itinerary" +
                " WHERE itinerary.userID = " + ID + ";";
        return query;
    }*/
}
