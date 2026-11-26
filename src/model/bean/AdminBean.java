package model.bean;

import java.io.Serializable;

//@ nullable_by_default
public class AdminBean implements Serializable {
    private int adminId;
    private String username;
    private String email;
    private String pswHash;

    /*@ private normal_behavior
      @ ensures adminId == 0 && username == null && email == null && pswHash == null;
      @*/
    public AdminBean() {}

    // Getters
    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == adminId;
      @*/
    public int getAdminId() {
        return adminId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == username;
      @*/
    public String getUsername() {
        return username;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == email;
      @*/
    public String getEmail() {
        return email;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == pswHash;
      @*/
    public String getPswHash() {
        return pswHash;
    }

    // Setters
    /*@ private normal_behavior
      @ assignable this.adminId;
      @ ensures this.adminId == adminId;
      @*/
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    /*@ private normal_behavior
      @ assignable this.username;
      @ ensures this.username == username;
      @*/
    public void setUsername(String username) {
        this.username = username;
    }

    /*@ private normal_behavior
      @ assignable this.email;
      @ ensures this.email == email;
      @*/
    public void setEmail(String email) {
        this.email = email;
    }

    /*@ private normal_behavior
      @ assignable this.pswHash;
      @ ensures this.pswHash == pswHash;
      @*/
    public void setPswHash(String pswHash) {
        this.pswHash = pswHash;
    }


    //TODO: remove toString method
    /*@ also
      @ private normal_behavior
      @ assignable \nothing;
      @ ensures \result != null;
      @*/
    @Override
    public String toString() {
        return "AdminBean{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", pswHash='" + pswHash + '\'' +
                '}';
    }
}
