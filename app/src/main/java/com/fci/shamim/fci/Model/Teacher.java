package com.fci.shamim.fci.Model;

public class Teacher {
    String teacherId;
    String name;
    String imageUri;
    String department;
    String designation;
    String phone;
    String email;

    public Teacher(String teacherId,String name,String imageUri, String department, String designation, String phone, String email) {
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.teacherId=teacherId;
        this.imageUri=imageUri;
    }

    public Teacher() {
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
