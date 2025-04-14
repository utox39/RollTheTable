package model.bean;

import java.io.Serializable;

public class AdminBean implements Serializable {
    private int adminId;
    private String username;
    private String email;
    private String pswHash;

    public AdminBean() {}

    // Getters
    public int getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPswHash() {
        return pswHash;
    }

    // Setters
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPswHash(String pswHash) {
        this.pswHash = pswHash;
    }


    //TODO: remove toString method
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
