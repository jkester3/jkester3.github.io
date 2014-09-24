package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserQuery extends SQLQuery {

    public SQLUserQuery() {
        super();
    }

    public void createUserQuery(User user) throws SQLException {
        String query = "INSERT INTO USER (firstName, lastName, userName, " +
                "password, email, preferenceID, userRole) VALUES" +
                "(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setInt(4, user.getPassword());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, null);
        preparedStatement.setString(7, null);
        preparedStatement.executeUpdate();
    }

    public void deleteUserQuery(final String username) throws SQLException {
        String query = "DELETE FROM USER WHERE USER.userName = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
    }

    public User readUserQuery(final String username) throws SQLException {
        final String query = "SELECT * FROM USER WHERE USER.userName = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet results = preparedStatement.executeQuery();
        User fetchedUser = new User();
        try {
            while (results.next()) {
                final int userID = results.getInt("ID");
                final String firstName = results.getString("firstName");
                final String lastName = results.getString("lastName");
                final String userName = results.getString("userName");
                final int password = results.getInt("password");
                fetchedUser.setID(userID);
                fetchedUser.setFirstName(firstName);
                fetchedUser.setLastName(lastName);
                fetchedUser.setPassword(password);
                fetchedUser.setUserName(userName);
                break;
            }
        } finally {
            DbUtil.close(results);
            DbUtil.close(preparedStatement);
            DbUtil.close(dbConnection);
            return fetchedUser;
        }
    }

    public void updateUserQuery(final User user) throws SQLException {
        final String query = "UPDATE USER SET firstName = ?, lastName = ?, " +
                "userName = ?, password = ?, email = ? WHERE USER.userName = ?";
        PreparedStatement preparedStatement =
                super.dbConnection.prepareStatement(query);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2,user.getLastName());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setInt(4, user.getPassword());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getUsername());
        preparedStatement.executeUpdate();
    }
}
