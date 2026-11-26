package model.bean;

import java.io.Serializable;

//@ nullable_by_default
public class CustomerBean implements Serializable {
    private int customerId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phone;
    private String pswHash;

    /*@ private normal_behavior
      @ ensures customerId == 0 && name == null && surname == null;
      @ ensures username == null && email == null && phone == null && pswHash == null;
      @*/
    public CustomerBean() {}

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
      @ ensures \result == username;
      @*/
    public String getUsername() {
        return username;
    }

    /*@ private normal_behavior
      @ assignable this.username;
      @ ensures this.username == username;
      @*/
    public void setUsername(String username) {
        this.username = username;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == email;
      @*/
    public String getEmail() {
        return email;
    }

    /*@ private normal_behavior
      @ assignable this.email;
      @ ensures this.email == email;
      @*/
    public void setEmail(String email) {
        this.email = email;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == phone;
      @*/
    public String getPhone() {
        return phone;
    }

    /*@ private normal_behavior
      @ assignable this.phone;
      @ ensures this.phone == phone;
      @*/
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == pswHash;
      @*/
    public String getPswHash() {
        return pswHash;
    }

    /*@ private normal_behavior
      @ assignable this.pswHash;
      @ ensures this.pswHash == pswHash;
      @*/
    public void setPswHash(String pswHash) {
        this.pswHash = pswHash;
    }
}
