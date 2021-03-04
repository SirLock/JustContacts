package model;

import java.util.Map;

public class Contact {
    private String givenName;
    private String surname;
    private String address;
    private String phone;
    private Map<String, String> additionals;

    public Contact() {
    }

    public Contact(String givenName, String surname) {
        this.givenName = givenName;
        this.surname = surname;
    }

    public Contact(String givenName, String surname, String address, String phone) {
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
    }

    public Contact(String givenName, String surname, String address, String phone, Map<String, String> additionals) {
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.additionals = additionals;
    }

    public String toString() {
        return String.format("%s, %s", givenName, surname);
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, String> getAdditionals() {
        return additionals;
    }

    public void setAdditionals(Map<String, String> additionals) {
        this.additionals = additionals;
    }


}
