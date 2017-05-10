package net.ddmax.plantpano.entity;

import studios.codelight.smartloginlibrary.users.SmartUser;

/**
 * @author ddMax
 * @since 2017-05-04 12:42 AM.
 */

public class User extends SmartUser {

    private String password;
    private String token;

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
