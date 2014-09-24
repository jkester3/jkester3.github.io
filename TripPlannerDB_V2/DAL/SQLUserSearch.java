package database.DAL;

import database.DTO.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserSearch {
    private String query;
    private Connection dbConnection;
    private Statement statement;
    private ResultSet results;
    private User fetchedUser = new User();

    public SQLUserSearch(final String query) {
        this.query = query;
        executeQuery();
    }

    private void executeQuery()  {
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                fetchedUser.setID(results.getInt("ID"));
                fetchedUser.setFirstName(results.getString("firstName"));
                fetchedUser.setLastName(results.getString("lastName"));
                fetchedUser.setUserName(results.getString("userName"));
                fetchedUser.setPassword(results.getString("password"));
                break;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeAllConnections();
        }
    }

    private void closeAllConnections() {
        DbUtil.close(results);
        DbUtil.close(statement);
        DbUtil.close(dbConnection);
    }

    public User getFetchedUser() {
        return fetchedUser;
    }
}