package model.bean;

import java.io.Serializable;

//@ nullable_by_default
public class AddressBean implements Serializable {
    private int addressId;
    private int customerId;
    private String name;
    private String surname;
    private String postcode;
    private String city;
    private String province;
    private String country;
    private String streetName;
    private String streetNumber;
    private String notes;
    private String type;

    /*@ private normal_behavior
      @ ensures addressId == 0 && customerId == 0;
      @ ensures name == null && surname == null && postcode == null && city == null;
      @ ensures province == null && country == null && streetName == null;
      @ ensures streetNumber == null && notes == null && type == null;
      @*/
    public AddressBean() {}

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == addressId;
      @*/
    public int getAddressId() {
        return addressId;
    }

    /*@ private normal_behavior
      @ assignable this.addressId;
      @ ensures this.addressId == addressId;
      @*/
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == customerId;
      @*/
    public int getCustomerId() {
        return customerId;
    }

    /*@ private normal_behavior
      @ assignable this.customerId;
      @ ensures this.customerId == customerId;
      @*/
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == name;
      @*/
    public String getName() {
        return name;
    }

    /*@ private normal_behavior
      @ assignable this.name;
      @ ensures this.name == name;
      @*/
    public void setName(String name) {
        this.name = name;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == surname;
      @*/
    public String getSurname() {
        return surname;
    }

    /*@ private normal_behavior
      @ assignable this.surname;
      @ ensures this.surname == surname;
      @*/
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == postcode;
      @*/
    public String getPostcode() {
        return postcode;
    }

    /*@ private normal_behavior
      @ assignable this.postcode;
      @ ensures this.postcode == postcode;
      @*/
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == city;
      @*/
    public String getCity() {
        return city;
    }

    /*@ private normal_behavior
      @ assignable this.city;
      @ ensures this.city == city;
      @*/
    public void setCity(String city) {
        this.city = city;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == province;
      @*/
    public String getProvince() {
        return province;
    }

    /*@ private normal_behavior
      @ assignable this.province;
      @ ensures this.province == province;
      @*/
    public void setProvince(String province) {
        this.province = province;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == country;
      @*/
    public String getCountry() {
        return country;
    }

    /*@ private normal_behavior
      @ assignable this.country;
      @ ensures this.country == country;
      @*/
    public void setCountry(String country) {
        this.country = country;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == streetName;
      @*/
    public String getStreetName() {
        return streetName;
    }

    /*@ private normal_behavior
      @ assignable this.streetName;
      @ ensures this.streetName == streetName;
      @*/
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == streetNumber;
      @*/
    public String getStreetNumber() {
        return streetNumber;
    }

    /*@ private normal_behavior
      @ assignable this.streetNumber;
      @ ensures this.streetNumber == streetNumber;
      @*/
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == notes;
      @*/
    public String getNotes() {
        return notes;
    }

    /*@ private normal_behavior
      @ assignable this.notes;
      @ ensures this.notes == notes;
      @*/
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == type;
      @*/
    public String getType() {
        return type;
    }

    /*@ private normal_behavior
      @ assignable this.type;
      @ ensures this.type == type;
      @*/
    public void setType(String type) {
        this.type = type;
    }

    /*@ also
      @ private normal_behavior
      @ assignable \nothing;
      @ ensures \result != null;
      @*/
    @Override
    public String toString() {
        return "AddressBean{" +
                "addressId=" + addressId +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", notes='" + notes + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
