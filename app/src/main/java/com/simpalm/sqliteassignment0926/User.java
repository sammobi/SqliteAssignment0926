package com.simpalm.sqliteassignment0926;

/**
 * Created by Simpalm on 9/30/16.
 */

public class User {
    public User(String username, String password, String name, String number, String dob, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.number = number;
        this.dob = dob;
        this.address = address;
    }

    String username, password, name, number, dob, address;

    public String getUsername(String username) {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(String string) {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
