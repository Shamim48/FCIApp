package com.fci.shamim.fci.Model;

public class Staff {
    String staffId;
    String imageUri;
    String name;
    String designation;
    String email;
    String phone;


    public Staff(String staffId, String imageUri, String name, String designation, String email, String phone) {
        this.staffId = staffId;
        this.imageUri = imageUri;
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
    }

    public Staff() {
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
