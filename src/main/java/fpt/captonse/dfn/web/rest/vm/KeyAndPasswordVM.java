package fpt.captonse.dfn.web.rest.vm;

import fpt.captonse.dfn.domain.User;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    private User key;

    private String newPassword;

    public User getKey() {
        return key;
    }

    public void setKey(User key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
