package edu.ua.collegeswap.viewModel;

import java.io.Serializable;

/**
 * Created by Patrick on 3/25/2015.
 */
public class Account implements Serializable {

    private String name;
    private String contactInfo;
    private float rating;

    private String phoneNo;
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phone) { this.phoneNo = phone; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
