package database.DAL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryUpdate {
    private String query;
    private Connection dbConnection;
    private Statement statement;

    public SQLQueryUpdate(final String query) throws SQLException {
        this.query = query;
        executeQuery();
    }

    private void executeQuery() throws SQLException {
        try {
            dbConnection = ConnectionManager.getConnection();
            statement = dbConnection.createStatement();
            int updatedRows = statement.executeUpdate(query);
            if (noChangesWereMade(updatedRows)) {
                thenThrowUpdateFailedException();
            }
        } finally {
            DbUtil.close(statement);
            DbUtil.close(dbConnection);
        }
    }

    private boolean noChangesWereMade(final int updatedRows) {
        return updatedRows == 0;
    }

    private void thenThrowUpdateFailedException() throws SQLException {
        if (query.contains("UPDATE")) {
            throw new SQLException("Error: update failed because user " +
                    "does not exist.");
        } else if (query.contains("DELETE")) {
            throw new SQLException("Error: delete failed because user " +
                    "does not exist.");
        }
    }
}