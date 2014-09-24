package model;

import database.User;

import java.sql.SQLException;

public interface Validation {
    void validateCredentials() throws SQLException;
    void init(User account, String username, String password);
}
