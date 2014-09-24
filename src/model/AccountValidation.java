package model;

import database.DataManager;
import database.User;

import java.sql.SQLException;

public class AccountValidation {
    private User account;
    private String username, confirmPassword;
    private Validation operation;

    public AccountValidation(User account, String confirmPassword) {
        this.account = account;
        this.username = account.getUsername();
        this.confirmPassword = confirmPassword;
    }

    public AccountValidation(String username, String confirmPassword)
            throws SQLException {
        this.account = DataManager.fetchUser(username);
        this.username = username;
        this.confirmPassword = confirmPassword;
    }

    public void setOperation(Validation operation) {
        this.operation = operation;
        operation.init(account, username, confirmPassword);
    }

    public void validateCredentials() throws SQLException {
        operation.validateCredentials();
    }
}
